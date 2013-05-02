/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.tklm.time.rules.clocklocation.validation;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.kpme.core.KPMEConstants;
import org.kuali.kpme.core.authorization.AuthorizationValidationUtils;
import org.kuali.kpme.core.authorization.DepartmentalRule;
import org.kuali.kpme.core.bo.utils.ValidationUtils;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.tklm.time.rules.clocklocation.ClockLocationRule;
import org.kuali.kpme.tklm.time.rules.clocklocation.ClockLocationRuleIpAddress;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

public class ClockLocationRuleRule extends MaintenanceDocumentRuleBase {

	private static Logger LOG = Logger.getLogger(ClockLocationRuleRule.class);

	public static boolean validateIpAddress(String ip) {
		LOG.debug("Validating IP address: " + ip);
		if(ip == null) {
			return false;
		}
		if(ip.isEmpty() || ip.length() > 15 || ip.endsWith(KPMEConstants.IP_SEPARATOR) || ip.startsWith(KPMEConstants.IP_SEPARATOR)) {
			return false;
		}
		String[] lst =  StringUtils.split(ip, KPMEConstants.IP_SEPARATOR);
		if(lst.length > 4 || (lst.length <4 && ip.indexOf(HrConstants.WILDCARD_CHARACTER)< 0)) {
			return false;
		}
		for(String str : lst) {
			if(!str.matches(KPMEConstants.IP_WILDCARD_PATTERN)) {
				return false;
			}
		}
		return true;
	}
	
	boolean validateIpAddresses(List<ClockLocationRuleIpAddress> ipAddresses) {
		for(ClockLocationRuleIpAddress ip : ipAddresses) {
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
						.getEffectiveLocalDate())) {
			this.putFieldError("workArea", "error.existence", "workArea '"
					+ clr.getWorkArea() + "'");
			valid = false;
		} else if (clr.getWorkArea() != null
				&& !clr.getWorkArea().equals(HrConstants.WILDCARD_LONG)) {
			int count = HrServiceLocator.getWorkAreaService().getWorkAreaCount(clr.getDept(), clr.getWorkArea());
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
    		if (!ValidationUtils.validateDepartment(clr.getDept(), clr.getEffectiveLocalDate())) {
			    this.putFieldError("dept", "error.existence", "department '" + clr.getDept() + "'");
            } else if (!AuthorizationValidationUtils.hasAccessToWrite(clr)) {
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
		} else if (!clr.getJobNumber().equals(HrConstants.WILDCARD_LONG)) {
			int count = HrServiceLocator.getJobService().getJobCount(clr.getPrincipalId(), clr.getJobNumber(),null);
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

        if (StringUtils.equals(clr.getDept(), HrConstants.WILDCARD_CHARACTER) &&
                !AuthorizationValidationUtils.canWildcardDepartment(clr)) {
            this.putFieldError("dept", "error.wc.dept.perm", "department '" + clr.getDept() + "'");
            valid = false;
        }

        if (clr!= null && clr.getWorkArea() != null && clr.getWorkArea().equals(HrConstants.WILDCARD_LONG) &&
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

		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();
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
