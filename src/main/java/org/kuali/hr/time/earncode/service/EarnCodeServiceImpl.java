package org.kuali.hr.time.earncode.service;

import com.google.common.collect.Ordering;
import org.apache.commons.lang.StringUtils;
import org.kuali.hr.job.Job;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.earncodesec.EarnCodeSecurity;
import org.kuali.hr.lm.earncodesec.EarnCodeType;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.earncode.dao.EarnCodeDao;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.roles.TkUserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.krad.util.GlobalVariables;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;

public class EarnCodeServiceImpl implements EarnCodeService {

	private EarnCodeDao earnCodeDao;

	public void setEarnCodeDao(EarnCodeDao earnCodeDao) {
		this.earnCodeDao = earnCodeDao;
	}

    public List<EarnCode> getEarnCodesForLeaveAndTime(Assignment a, Date asOfDate) {
        List<EarnCode> earnCodes = getEarnCodesForTime(a, asOfDate);
        List<EarnCode> leaveEarnCodes = getEarnCodesForLeave(a, asOfDate);
        earnCodes.removeAll(leaveEarnCodes); //ensures no overlap during the addAll
        earnCodes.addAll(leaveEarnCodes);

        return earnCodes;
    }

    public List<EarnCode> getEarnCodesForTime(Assignment a, Date asOfDate) {
        if (a == null) throw new RuntimeException("No assignment parameter.");
        Job job = a.getJob();
        if (job == null || job.getPayTypeObj() == null) throw new RuntimeException("Null job or null job pay type on assignment.");

        List<EarnCode> earnCodes = new LinkedList<EarnCode>();
        String earnTypeCode = EarnCodeType.TIME.getCode();

        EarnCode regularEarnCode = getEarnCode(job.getPayTypeObj().getRegEarnCode(), asOfDate);
        if (regularEarnCode == null) {
            throw new RuntimeException("No regular earn code defined for job pay type.");
        } else {
            earnCodes.add(regularEarnCode);
        }
        List<EarnCodeSecurity> decs = TkServiceLocator.getEarnCodeSecurityService().getEarnCodeSecurities(job.getDept(), job.getHrSalGroup(), job.getLocation(), asOfDate);
        for (EarnCodeSecurity dec : decs) {
            if (earnTypeCode.equals(dec.getEarnCodeType())
                    || EarnCodeType.BOTH.getCode().equals(dec.getEarnCodeType())) {

                boolean addEarnCode = false;
                // Check employee flag
                if (dec.isEmployee() &&
                        (StringUtils.equals(TKUser.getCurrentTargetPerson().getEmployeeId(), GlobalVariables.getUserSession().getPerson().getEmployeeId()))) {
                    addEarnCode = true;
                }
                // Check approver flag
                if (!addEarnCode && dec.isApprover()) {
                    Set<Long> workAreas = TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).getApproverWorkAreas();
                    for (Long wa : workAreas) {
                        WorkArea workArea = TkServiceLocator.getWorkAreaService().getWorkArea(wa, asOfDate);
                        if (workArea!= null && a.getWorkArea().compareTo(workArea.getWorkArea())==0) {
                            addEarnCode = true;
                            break;
                        }
                    }
                }
                if (addEarnCode) {
                    EarnCode ec = getEarnCode(dec.getEarnCode(), asOfDate);
                    if(ec!=null){
                        earnCodes.add(ec);
                    }
                }
            }
        }

        return earnCodes;
    }


   public List<EarnCode> getEarnCodesForLeave(Assignment a, Date asOfDate) {
       if (a == null) throw new RuntimeException("No assignment parameter.");
       Job job = a.getJob();
       if (job == null || job.getPayTypeObj() == null) throw new RuntimeException("Null job or null job pay type on assignment.");

       List<EarnCode> earnCodes = new LinkedList<EarnCode>();
       String earnTypeCode = EarnCodeType.LEAVE.getCode();
       // skip getting the regular earn code for Leave Calendar

       List<String> listLeavePlans = new LinkedList<String>();
       List<String> listAccrualCategories = new LinkedList<String>();

       boolean fmlaEligible = false;
       boolean workersCompEligible = false;
       String leavePlan;
       String accrualCategory;

       for (PrincipalHRAttributes principalHRAttributes : TkServiceLocator.getPrincipalHRAttributeService().getAllActivePrincipalHrAttributesForPrincipalId(job.getPrincipalId(), asOfDate)) {
           if (principalHRAttributes.isFmlaEligible()) fmlaEligible = true;
           if (principalHRAttributes.isWorkersCompEligible()) workersCompEligible = true;
           leavePlan = principalHRAttributes.getLeavePlan();
           if(leavePlan != null){
               listLeavePlans.add(leavePlan);
               for (AccrualCategory accrualCategories : TkServiceLocator.getAccrualCategoryService().getActiveAccrualCategoriesForLeavePlan(leavePlan, asOfDate)) {
                   accrualCategory = accrualCategories.getAccrualCategory();
                   if(accrualCategory != null) {
                       listAccrualCategories.add(accrualCategory);
                   }
               }
           }
       }

       List<EarnCodeSecurity> decs = TkServiceLocator.getEarnCodeSecurityService().getEarnCodeSecurities(job.getDept(), job.getHrSalGroup(), job.getLocation(), asOfDate);
       for (EarnCodeSecurity dec : decs) {
           if (earnTypeCode.equals(dec.getEarnCodeType())
                   || EarnCodeType.BOTH.getCode().equals(dec.getEarnCodeType())) {

               EarnCode ec = getEarnCode(dec.getEarnCode(), asOfDate);
               if(ec!=null){
                   if (listAccrualCategories.contains(ec.getAccrualCategory())
                       || ec.getLeavePlan() == null) {

                   if ( (fmlaEligible && ec.getFmla().equals("Y"))
                           || (!fmlaEligible && !ec.getFmla().equals("Y")) ) {

                       if ( (workersCompEligible && ec.getWorkmansComp().equals("Y"))
                               || (!workersCompEligible && !ec.getWorkmansComp().equals("Y")) ){

                               earnCodes.add(ec);
                           }
                       }
                   }
               }
           }
       }

       return earnCodes;
    }

    @Override
    public List<EarnCode> getEarnCodesForPrincipal(String principalId, Date asOfDate) {
        List<EarnCode> earnCodes = new LinkedList<EarnCode>();
        List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(principalId, asOfDate);
        for (Assignment assignment : assignments) {
            List<EarnCode> assignmentEarnCodes = getEarnCodesForLeaveAndTime(assignment, asOfDate);
            earnCodes.removeAll(assignmentEarnCodes); //ensures no overlap during the addAll
            earnCodes.addAll(assignmentEarnCodes);
        }

        return earnCodes;
    }
    /* The following code is redundant and could end up differing as changes are made, so call the base method instead, see first few lines of this method.
        String leavePlan = null;
        List<EarnCode> earnCodes = new ArrayList<EarnCode>();
        PrincipalHRAttributes hrAttribute = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, asOfDate);
        if(hrAttribute != null) {
            leavePlan = hrAttribute.getLeavePlan();
            boolean fmla = hrAttribute.isFmlaEligible();
            boolean workmansComp = hrAttribute.isWorkmansCompEligible();
            if (StringUtils.isBlank(leavePlan)) throw new RuntimeException("No leave plan defined for " + principalId + " in principal hr attributes");
            List<EarnCode> unfilteredEarnCodes = earnCodeDao.getEarnCod e s (leavePlan, asOfDate);
            for (EarnCode earnCode : unfilteredEarnCodes) {
                //if employee add this leave code
                //TO DO how do we know this is an approver for them
                //if ((earnCode.getEmployee() && TKUser.getCurrentRoles().isActiveEmployee()) ||
                //(earnCode.getApprover() && TKUser.isApprover())) {
                boolean addEarnCode = false;
                if ((TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).isActiveEmployee()) || (TKUser.isApprover())) {
                    if ((earnCode.getFmla().equals("Y") && fmla)
                            || !earnCode.getFmla().equals("Y"))  {
                        if ((earnCode.getWorkmansComp().equals("Y") && workmansComp)
                                || !earnCode.getWorkmansComp().equals("Y"))  {
                            earnCodes.add(earnCode);
        }}}}}
        return earnCodes;
    }
    */

    public EarnCode getEarnCode(String earnCode, Date asOfDate) {
		return earnCodeDao.getEarnCode(earnCode, asOfDate);
	}

    @Override
    public String getEarnCodeType(String earnCode, Date asOfDate) {
        EarnCode earnCodeObj = getEarnCode(earnCode, asOfDate);
        return earnCodeObj.getEarnCodeType();
    }

	@Override
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
	public Map<String, String> getEarnCodesForDisplay(String principalId) {
		return getEarnCodesForDisplayWithEffectiveDate(principalId, TKUtils.getCurrentDate());
	}

    @Override
    public Map<String, String> getEarnCodesForDisplayWithEffectiveDate(String principalId, Date asOfDate) {
        List<EarnCode> earnCodes = this.getEarnCodesForPrincipal(principalId, asOfDate);

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
    public Map<String, String> getEarnCod e s ForDisplayWithAssignment(Assignment assignment, Date asOfDate) {
        List<EarnCode> earnCodes = this.getEarnCod e s ( assignment, asOfDate);

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
