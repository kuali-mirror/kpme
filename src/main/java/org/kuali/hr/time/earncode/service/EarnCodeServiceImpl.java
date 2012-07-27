package org.kuali.hr.time.earncode.service;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.job.Job;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.earncodesec.EarnCodeSecurity;
import org.kuali.hr.lm.earncodesec.EarnCodeType;
import org.kuali.hr.lm.leavecode.LeaveCode;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.earncode.dao.EarnCodeDao;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workarea.WorkArea;

import com.google.common.collect.Ordering;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EarnCodeServiceImpl implements EarnCodeService {

	private EarnCodeDao earnCodeDao;

	public void setEarnCodeDao(EarnCodeDao earnCodeDao) {
		this.earnCodeDao = earnCodeDao;
	}


	@Override
	public List<EarnCode> getEarnCodes(Assignment a, Date asOfDate) {
		return getEarnCodes(a, asOfDate, null);
	}

    @Override
    public List<EarnCode> getEarnCodes(Assignment a, Date asOfDate, String earnTypeCode) {
        List<EarnCode> earnCodes = new LinkedList<EarnCode>();

        // Note: https://jira.kuali.org/browse/KPME-689
        // We are grabbing a TkUser from the current thread local context here.
        // really, this should probably be passed in..

        TKUser user = TKContext.getUser();
        if (user == null) {
            // TODO: Determine how to fail if there is no TkUser
            throw new RuntimeException("No User on context.");
        }

        if (a == null)
            throw new RuntimeException("Can not get earn codes for null assignment");
        Job job = a.getJob();
        if (job == null || job.getPayTypeObj() == null)
            throw new RuntimeException("Null job/job paytype on assignment!");

        EarnCode regularEc = getEarnCode(job.getPayTypeObj().getRegEarnCode(), asOfDate);
        if (regularEc == null)
            throw new RuntimeException("No regular earn code defined.");
        earnCodes.add(regularEc);
        List<EarnCodeSecurity> decs = TkServiceLocator.getEarnCodeSecurityService().getEarnCodeSecurities(job.getDept(), job.getHrSalGroup(), job.getLocation(), asOfDate);
        for (EarnCodeSecurity dec : decs) {
            if (StringUtils.isBlank(earnTypeCode)
                    || earnTypeCode.equals(dec.getEarnCodeType())
                    || EarnCodeType.BOTH.getCode().equals(dec.getEarnCodeType())) {

                boolean addEc = false;

                // Check employee flag
                if (dec.isEmployee() &&
                        (StringUtils.equals(user.getCurrentTargetPerson().getEmployeeId(), user.getCurrentPerson().getEmployeeId()))) {
                    addEc = true;
                }

                // Check approver flag
                if (!addEc && dec.isApprover()) {
                    Set<Long> workAreas = user.getCurrentRoles().getApproverWorkAreas();
                    for (Long wa : workAreas) {
                        WorkArea workArea = TkServiceLocator.getWorkAreaService().getWorkArea(wa, asOfDate);
                        if (workArea!= null && a.getWorkArea().compareTo(workArea.getWorkArea())==0) {
                            // TODO: All Good, and then Break
                            addEc = true;
                            break;
                        }
                    }
                }

                if (addEc) {
                    EarnCode ec = getEarnCode(dec.getEarnCode(), asOfDate);
                    if(ec!=null){
                        earnCodes.add(ec);
                    }
                }
            }
        }

        return earnCodes;
    }

	public EarnCode getEarnCode(String earnCode, Date asOfDate) {
		EarnCode ec = null;

		ec = earnCodeDao.getEarnCode(earnCode, asOfDate);

		return ec;
	}

    @Override
    @CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
    public String getEarnCodeType(String earnCode, Date asOfDate) {
        EarnCode earnCodeObj = getEarnCode(earnCode, asOfDate);
        return earnCodeObj.getEarnCodeType();
    }

	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public EarnCode getEarnCodeById(String earnCodeId) {
		return earnCodeDao.getEarnCodeById(earnCodeId);
	}
	
	public List<EarnCode> getOvertimeEarnCodes(Date asOfDate){
		return earnCodeDao.getOvertimeEarnCodes(asOfDate);
	}
	
	public List<String> getOvertimeEarnCodesStrs(Date asOfDate){
		List<String> ovtEarnCodeStrs = new ArrayList<String>();
		List<EarnCode> ovtEarnCodes = getOvertimeEarnCodes(asOfDate);
		if(ovtEarnCodes != null){
			for(EarnCode ovtEc : ovtEarnCodes){
				ovtEarnCodeStrs.add(ovtEc.getEarnCode());
			}
		}
		return ovtEarnCodeStrs;
	}
	
	@Override
	public int getEarnCodeCount(String earnCode) {
		return earnCodeDao.getEarnCodeCount(earnCode);
	}
	
	@Override
	public int getNewerEarnCodeCount(String earnCode, Date effdt) {
		return earnCodeDao.getNewerEarnCodeCount(earnCode, effdt);
	}

	@Override
	public BigDecimal roundHrsWithEarnCode(BigDecimal hours, EarnCode earnCode) {
		String roundOption = LMConstants.ROUND_OPTION_MAP.get(earnCode.getRoundingOption());
		BigDecimal fractScale = new BigDecimal(earnCode.getFractionalTimeAllowed());
		if(roundOption == null) {
			throw new RuntimeException("Rounding option of Earn Code " + earnCode.getEarnCode() + " is not recognized.");
		}
		BigDecimal roundedHours = hours;
		if(roundOption.equals("Traditional")) {
			roundedHours = hours.setScale(fractScale.scale(), BigDecimal.ROUND_HALF_EVEN);
		} else if(roundOption.equals("Truncate")) {
			roundedHours = hours.setScale(fractScale.scale(), BigDecimal.ROUND_DOWN);
		}
		return roundedHours;
	}
	
	@Override
    public List<EarnCode> getEarnCodes(String principalId, Date asOfDate) {
    	String leavePlan = null;
        List<EarnCode> earnCodes = new ArrayList<EarnCode>();
        PrincipalHRAttributes hrAttribute = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, asOfDate);
        if(hrAttribute != null) {
        	leavePlan = hrAttribute.getLeavePlan();
            boolean fmla = hrAttribute.isFmlaEligible();
            boolean workmansComp = hrAttribute.isWorkmansCompEligible();
        	if (StringUtils.isBlank(leavePlan)) {
        		throw new RuntimeException("No leave plan defined for " + principalId + " in principal hr attributes");
        	}
        	
            List<EarnCode> unfilteredEarnCodes = earnCodeDao.getEarnCodes(leavePlan, asOfDate);
            TKUser user = TKContext.getUser();

            for (EarnCode earnCode : unfilteredEarnCodes) {
                //if employee add this leave code
                //TODO how do we know this is an approver for them
//                if ((earnCode.getEmployee() && user.getCurrentRoles().isActiveEmployee()) ||
//                        (earnCode.getApprover() && user.isApprover())) {

                boolean addEarnCode = false;
                if ((user.getCurrentRoles().isActiveEmployee()) || (user.isApprover())) {
                    if ((earnCode.getFmla().equals("Y") && fmla)
                          || !earnCode.getFmla().equals("Y"))  {
                        if ((earnCode.getWorkmansComp().equals("Y") && workmansComp)
                                || !earnCode.getWorkmansComp().equals("Y"))  {
                            earnCodes.add(earnCode);
                        }
                    }
                }
            }

        }
        return earnCodes;
    }
	
	@Override
	@CacheResult(secondsRefreshPeriod = TkConstants.DEFAULT_CACHE_TIME)
	public Map<String, String> getEarnCodesForDisplay(String principalId) {
		return getEarnCodesForDisplayWithEffectiveDate(principalId, TKUtils.getCurrentDate());
	}

    @Override
    @CacheResult(secondsRefreshPeriod = TkConstants.DEFAULT_CACHE_TIME)
    public Map<String, String> getEarnCodesForDisplayWithEffectiveDate(String principalId, Date asOfDate) {
        List<EarnCode> earnCodes = this.getEarnCodes(principalId, asOfDate);

        Date currentDate = TKUtils.getCurrentDate();
        boolean futureDate = asOfDate.after(currentDate);
        List<EarnCode> copyList = new ArrayList<EarnCode>();
        copyList.addAll(earnCodes);
        for (EarnCode earnCode : copyList) {
            if ( futureDate
                    && !earnCode.getAllowScheduledLeave().equalsIgnoreCase("Y")) {
                earnCodes.remove(earnCode);
            }
        }
        Comparator<EarnCode> earnCodeComparator = new Comparator<EarnCode>() {
            @Override
            public int compare(EarnCode ec1, EarnCode ec2) {
                return ec1.getEarnCode().compareToIgnoreCase(ec2.getEarnCode());
            }
        };
        // Order by leaveCode ascending
        Ordering<EarnCode> ordering = Ordering.from(earnCodeComparator);

        Map<String, String> earnCodesForDisplay = new LinkedHashMap<String, String>();
        for (EarnCode earnCode : ordering.sortedCopy(earnCodes)) {
            earnCodesForDisplay.put(earnCode.getEarnCodeKeyForDisplay(), earnCode.getEarnCodeValueForDisplay());
        }
        return earnCodesForDisplay;
    }

    /* not using yet, may not be needed
    @Override
    @CacheResult(secondsRefreshPeriod = TkConstants.DEFAULT_CACHE_TIME)
    public Map<String, String> getEarnCodesForDisplayWithAssignment(Assignment assignment, Date asOfDate) {
        List<EarnCode> earnCodes = this.getEarnCodes(assignment, asOfDate);

        Date currentDate = TKUtils.getCurrentDate();
        boolean futureDate = asOfDate.after(currentDate);
        List<EarnCode> copyList = new ArrayList<EarnCode>();
        copyList.addAll(earnCodes);
        for (EarnCode earnCode : copyList) {
            if ( futureDate
                    && !earnCode.getAllowScheduledLeave().equalsIgnoreCase("Y")) {
                earnCodes.remove(earnCode);
            }
        }
        Comparator<EarnCode> earnCodeComparator = new Comparator<EarnCode>() {
            @Override
            public int compare(EarnCode ec1, EarnCode ec2) {
                return ec1.getEarnCode().compareToIgnoreCase(ec2.getEarnCode());
            }
        };
        // Order by leaveCode ascending
        Ordering<EarnCode> ordering = Ordering.from(earnCodeComparator);

        Map<String, String> earnCodesForDisplay = new LinkedHashMap<String, String>();
        for (EarnCode earnCode : ordering.sortedCopy(earnCodes)) {
            earnCodesForDisplay.put(earnCode.getEarnCodeKeyForDisplay(), earnCode.getEarnCodeValueForDisplay());
        }
        return earnCodesForDisplay;
    }

    */
}
