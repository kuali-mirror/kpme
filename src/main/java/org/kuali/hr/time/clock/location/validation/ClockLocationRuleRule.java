package org.kuali.hr.time.clock.location.validation;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.authorization.AuthorizationValidationUtils;
import org.kuali.hr.time.authorization.DepartmentalRule;
import org.kuali.hr.time.authorization.DepartmentalRuleAuthorizer;
import org.kuali.hr.time.clock.location.ClockLocationRule;
import org.kuali.hr.time.clock.location.TKIPAddress;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class ClockLocationRuleRule extends MaintenanceDocumentRuleBase {

	private static Logger LOG = Logger.getLogger(ClockLocationRuleRule.class);

	public static boolean validateIpAddress(String ip) {
		LOG.debug("Validating IP address: " + ip);
		if(ip == null) {
			return false;
		}
		if(ip.isEmpty() || ip.length() > 15 || ip.endsWith(TkConstants.IP_SEPERATOR) || ip.startsWith(TkConstants.IP_SEPERATOR)) {
			return false;
		}
		String[] lst =  StringUtils.split(ip, TkConstants.IP_SEPERATOR);
		if(lst.length > 4 || (lst.length <4 && ip.indexOf(TkConstants.WILDCARD_CHARACTER)< 0)) {
			return false;
		}
		for(String str : lst) {
			if(!str.matches(TkConstants.IP_WILDCARD_PATTERN)) {
				return false;
			}
		}
		return true;
	}
	
	boolean validateIpAddresses(List<TKIPAddress> ipAddresses) {
		for(TKIPAddress ip : ipAddresses) {
			if(!validateIpAddress(ip.getIpAddress())) {
				return this.flagIPAddressError(ip.getIpAddress());
			}
		}
		return true;
	}
	
	boolean flagIPAddressError(String ip) {
		this.putFieldError("ipAddresses", "ipaddress.invalid.format", ip);
		return false;
	}

	boolean validateWorkArea(ClockLocationRule clr) {
		boolean valid = true;
		if (clr.getWorkArea() != null
				&& !ValidationUtils.validateWorkArea(clr.getWorkArea(), clr
						.getEffectiveDate())) {
			this.putFieldError("workArea", "error.existence", "workArea '"
					+ clr.getWorkArea() + "'");
			valid = false;
		} else if (clr.getWorkArea() != null
				&& !clr.getWorkArea().equals(TkConstants.WILDCARD_LONG)) {
			Criteria crit = new Criteria();
			crit.addEqualTo("dept", clr.getDept());
			crit.addEqualTo("workArea", clr.getWorkArea());
			Query query = QueryFactory.newQuery(WorkArea.class, crit);
			int count = PersistenceBrokerFactory.defaultPersistenceBroker()
					.getCount(query);
			valid = (count > 0);
			if (!valid) {
				this.putFieldError("workArea", "dept.workarea.invalid.sync",
						clr.getWorkArea() + "");
			}
		}
		return valid;
	}

	protected boolean validateDepartment(ClockLocationRule clr) {
        boolean ret = false;

        if (!StringUtils.isEmpty(clr.getDept())) {
    		if (!ValidationUtils.validateDepartment(clr.getDept(), clr.getEffectiveDate())) {
			    this.putFieldError("dept", "error.existence", "department '" + clr.getDept() + "'");
            } else if (!DepartmentalRuleAuthorizer.hasAccessToWrite(clr)) {
                this.putFieldError("dept", "error.department.permissions", clr.getDept());
            } else {
                ret = true;
            }
        }

        return ret;
	}

	protected boolean validateJobNumber(ClockLocationRule clr) {
		boolean valid = true;
		if (clr.getJobNumber() == null) {
			valid = false;
		} else if (!clr.getJobNumber().equals(TkConstants.WILDCARD_LONG)) {
			Criteria crit = new Criteria();
			crit.addEqualTo("principalId", clr.getPrincipalId());
			crit.addEqualTo("jobNumber", clr.getJobNumber());
			Query query = QueryFactory.newQuery(Job.class, crit);
			int count = PersistenceBrokerFactory.defaultPersistenceBroker()
					.getCount(query);
			valid = (count > 0);
			if (!valid) {
				this.putFieldError("jobNumber", "principalid.job.invalid.sync",
						clr.getJobNumber() + "");
			}
		}
		return valid;
	}

	protected boolean validatePrincipalId(ClockLocationRule clr) {
		boolean valid = false;
		if (clr.getPrincipalId() == null) {
			valid = false;
		} else {
			valid = true;
		}
		return valid;
	}

    /**
     * This method will validate whether the wildcard combination and wild
     * carded areas for this DepartmentalRule are valid or not. Errors are added
     * to the field errors to report back to the user interface as well.
     *
     * @param clr The Departmental rule we are investigating.
     *
     * @return true if wild card setup is correct, false otherwise.
     */
    boolean validateWildcards(DepartmentalRule clr) {
        boolean valid = true;

        if (!ValidationUtils.validateWorkAreaDeptWildcarding(clr)) {
            // add error when work area defined, department is wild carded.
            this.putFieldError("dept", "error.wc.wadef", "department '" + clr.getDept() + "'");
            valid = false;
        }

        if (StringUtils.equals(clr.getDept(), TkConstants.WILDCARD_CHARACTER) &&
                !AuthorizationValidationUtils.canWildcardDepartment(clr)) {
            this.putFieldError("dept", "error.wc.dept.perm", "department '" + clr.getDept() + "'");
            valid = false;
        }

        if (clr!= null && clr.getWorkArea().equals(TkConstants.WILDCARD_LONG) &&
                !AuthorizationValidationUtils.canWildcardWorkArea(clr)) {
            this.putFieldError("dept", "error.wc.wa.perm", "department '" + clr.getDept() + "'");
            valid = false;
        }

        return valid;
    }


	/**
	 * It looks like the method that calls this class doesn't actually care
	 * about the return type.
	 */
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = false;

		PersistableBusinessObject pbo = this.getNewBo();
		if (pbo instanceof ClockLocationRule) {
			ClockLocationRule clr = (ClockLocationRule) pbo;
            valid = this.validateDepartment(clr);
            valid &= this.validateWorkArea(clr);
            valid &= this.validateWildcards(clr);
            valid &= this.validatePrincipalId(clr);
            valid &= this.validateJobNumber(clr);
            valid &= this.validateIpAddresses(clr.getIpAddresses());
		}

		return valid;
	}
}
