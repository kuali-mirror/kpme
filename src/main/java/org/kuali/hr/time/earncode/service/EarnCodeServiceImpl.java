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
package org.kuali.hr.time.earncode.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.job.Job;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.earncodesec.EarnCodeSecurity;
import org.kuali.hr.lm.earncodesec.EarnCodeType;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.collection.rule.TimeCollectionRule;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.earncode.dao.EarnCodeDao;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.roles.TkUserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.krad.util.GlobalVariables;

import com.google.common.collect.Ordering;

public class EarnCodeServiceImpl implements EarnCodeService {

	private EarnCodeDao earnCodeDao;

	public void setEarnCodeDao(EarnCodeDao earnCodeDao) {
		this.earnCodeDao = earnCodeDao;
	}

    public List<EarnCode> getEarnCodesForLeaveAndTime(Assignment a, Date asOfDate, boolean isLeavePlanningCalendar) {
        //  This method combining both leave calendar and timesheet calendar earn codes may never be used, but it is available.
        //  It was specified in kpme-1745, "Implement getEarnCodesForLeaveAndTime and call both of the above methods and return in one collection."
        List<EarnCode> earnCodes = getEarnCodesForTime(a, asOfDate);
        List<EarnCode> leaveEarnCodes = getEarnCodesForLeave(a, asOfDate, isLeavePlanningCalendar);
        //  the following list processing does work as hoped, comparing the objects' data, rather than their references to memory structures.
        earnCodes.removeAll(leaveEarnCodes); //ensures no overlap during the addAll
        earnCodes.addAll(leaveEarnCodes);

        return earnCodes;
    }

    public List<EarnCode> getEarnCodesForTime(Assignment a, Date asOfDate) {
        //getEarnCodesForTime and getEarnCodesForLeave have some overlapping logic, but they were separated so that they could follow their own distinct logic, so consolidation of logic is not desirable.

        if (a == null) throw new RuntimeException("No assignment parameter.");
        Job job = a.getJob();
        if (job == null || job.getPayTypeObj() == null) throw new RuntimeException("Null job or null job pay type on assignment.");

        List<EarnCode> earnCodes = new LinkedList<EarnCode>();
        String earnTypeCode = EarnCodeType.TIME.getCode();

        TimeCollectionRule tcr = a.getTimeCollectionRule();
        
        boolean isClockUser = tcr == null || tcr.isClockUserFl();
        boolean isUsersTimesheet = StringUtils.equals(TKContext.getPrincipalId(),a.getPrincipalId());

        // Reg earn codes will typically not be defined in the earn code security table
        EarnCode regularEarnCode = getEarnCode(job.getPayTypeObj().getRegEarnCode(), asOfDate);
        if (regularEarnCode == null) {
            throw new RuntimeException("No regular earn code defined for job pay type.");
        } else {
            //  if you are a clock user and this is your timesheet and you are processing the reg earn code, do not add this earn code. Use the clock in/out mechanism.
            if (isClockUser && isUsersTimesheet) {
                // do not add reg earn code. use clock.
            } else {
                earnCodes.add(regularEarnCode);
            }
        }

        List<String> listAccrualCategories = new LinkedList<String>();
        String accrualCategory;

        //  first make a list of the accrual categories available to the user's Leave Plan (yes, leave plan), for later comparison.
        PrincipalHRAttributes principalHRAttributes = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(job.getPrincipalId(), asOfDate);
        boolean fmlaEligible = principalHRAttributes.isFmlaEligible();
        boolean workersCompEligible = principalHRAttributes.isWorkersCompEligible();

        String leavePlan = principalHRAttributes.getLeavePlan();
        if (leavePlan != null) {
            for (AccrualCategory accrualCategories : TkServiceLocator.getAccrualCategoryService().getActiveAccrualCategoriesForLeavePlan(leavePlan, asOfDate)) {
                accrualCategory = accrualCategories.getAccrualCategory();
                if(accrualCategory != null) {
                    listAccrualCategories.add(accrualCategory);
                }
            }
        }

        //  get all earn codes by user security, then we'll filter on accrual category first as we process them.
        List<EarnCodeSecurity> decs = TkServiceLocator.getEarnCodeSecurityService().getEarnCodeSecurities(job.getDept(), job.getHrSalGroup(), job.getLocation(), asOfDate);
        for (EarnCodeSecurity dec : decs) {

            boolean addEarnCode = addEarnCodeBasedOnEmployeeApproverSettings(dec, a, asOfDate);
            if (addEarnCode) {

                //  allow types Time AND Both
                if (earnTypeCode.equals(dec.getEarnCodeType()) || EarnCodeType.BOTH.getCode().equals(dec.getEarnCodeType())) {
                    EarnCode ec = getEarnCode(dec.getEarnCode(), asOfDate);

                    //  make sure we got something back from the earn code dao
                    if (ec != null) {

                        //  now that we have a list of security earn codes, compare their accrual categories to the user's accrual category list.
                        //  we also allow earn codes that have no accrual category assigned.
                        if (listAccrualCategories.contains(ec.getAccrualCategory()) || ec.getAccrualCategory() == null) {

                            //  if the user's fmla flag is Yes, that means we are not restricting codes based on this flag, so any code is shown.
                            //    if the fmla flag on a code is yes they can see it.    (allow)
                            //    if the fmla flag on a code is no they should see it.  (allow)
                            //  if the user's fmla flag is No,
                            //    they can see any codes which are fmla=no.             (allow)
                            //    they can not see codes with fmla=yes.                 (exclude earn code)
                            //  the fmla earn codes=no do not require any exclusion
                            //  the only action required is if the fmla user flag=no: exclude those codes with fmla=yes.

                            if ( (fmlaEligible || ec.getFmla().equals("N")) ) {
                                // go on, we are allowing these three combinations: YY, YN, NN

                                //  apply the same logic as FMLA to the Worker Compensation flags.
                                if ( (workersCompEligible || ec.getWorkmansComp().equals("N")) ) {
                                    // go on, we are allowing these three combinations: YY, YN, NN.

                                    //  determine if the holiday earn code should be displayed.
                                    if ( showEarnCodeIfHoliday(ec, dec) ) {
                                        //  non-Holiday earn code will go on, Holiday earn code must meet some requirements in the method.

                                        if ( StringUtils.equals(regularEarnCode.toString(), dec.getEarnCode()) ) {
                                            //  do not add the reg earn code here. the reg earn code has already been processed prior to this decs loop.

                                        } else {
                                            //  add earn code if it is not the reg earn code.
                                            earnCodes.add(ec);
                                        }
                                    } else {
                                        //  the showEarnCodeIfHoliday method determined that we should not add the Holiday earn code.
                                    }
                                } else {
                                    //  do not add this earn code. User WC flag=no and earn code WC flag=yes.
                                }
                            } else {
                                //  do not add this earn code. User FMLA flag=no and earn code FMLA flag=yes.
                            }
                        } else {
                            //  do not add this earn code. accrual category does not allow.
                        }
                    } else {
                        //  skip null earn code
                    }
                } else {
                    //  do not add this earn code. the leave type does not allow.
                }
            } else {
                // do not add this earn code. user and approver settings disallow.
            }
        }

        return earnCodes;
    }

    public List<EarnCode> getEarnCodesForLeave(Assignment a, Date asOfDate, boolean isLeavePlanningCalendar) {
        //getEarnCodesForTime and getEarnCodesForLeave have some overlapping logic, but they were separated so that they could follow their own distinct logic, so consolidation of logic is not desirable.

        if (a == null) throw new RuntimeException("No assignment parameter.");
        Job job = a.getJob();
        if (job == null || job.getPayTypeObj() == null) throw new RuntimeException("Null job or null job pay type on assignment.");

        List<EarnCode> earnCodes = new LinkedList<EarnCode>();
        String earnTypeCode = EarnCodeType.LEAVE.getCode();
        // skip getting the regular earn code for Leave Calendar

        List<String> listAccrualCategories = new LinkedList<String>();
        String accrualCategory;

        //  first make a list of the accrual categories available to the user's leave plan, for later comparison.
        PrincipalHRAttributes principalHRAttributes = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(job.getPrincipalId(), asOfDate);
        boolean fmlaEligible = principalHRAttributes.isFmlaEligible();
        boolean workersCompEligible = principalHRAttributes.isWorkersCompEligible();

        String leavePlan = principalHRAttributes.getLeavePlan();
        if (leavePlan != null) {
            for (AccrualCategory accrualCategories : TkServiceLocator.getAccrualCategoryService().getActiveAccrualCategoriesForLeavePlan(leavePlan, asOfDate)) {
                accrualCategory = accrualCategories.getAccrualCategory();
                if(accrualCategory != null) {
                    listAccrualCategories.add(accrualCategory);
                }
            }
        }

        //  get all earn codes by user security, then we'll filter on accrual category first as we process them.
        List<EarnCodeSecurity> decs = TkServiceLocator.getEarnCodeSecurityService().getEarnCodeSecurities(job.getDept(), job.getHrSalGroup(), job.getLocation(), asOfDate);
        for (EarnCodeSecurity dec : decs) {

            boolean addEarnCode = addEarnCodeBasedOnEmployeeApproverSettings(dec, a, asOfDate);
            if (addEarnCode) {

                //  allow types Leave AND Both
                if (earnTypeCode.equals(dec.getEarnCodeType()) || EarnCodeType.BOTH.getCode().equals(dec.getEarnCodeType())) {
                    EarnCode ec = getEarnCode(dec.getEarnCode(), asOfDate);

                    //  make sure we got something back from the earn code dao
                    if (ec != null) {

                        //  now that we have a list of security earn codes, compare their accrual categories to the user's accrual category list.
                        //  we also allow earn codes that have no accrual category assigned.
                        if (listAccrualCategories.contains(ec.getAccrualCategory()) || ec.getAccrualCategory() == null) {

                            //  if the user's fmla flag is Yes, that means we are not restricting codes based on this flag, so any code is shown.
                            //    if the fmla flag on a code is yes they can see it.    (allow)
                            //    if the fmla flag on a code is no they should see it.  (allow)
                            //  if the user's fmla flag is No,
                            //    they can see any codes which are fmla=no.             (allow)
                            //    they can not see codes with fmla=yes.                 (exclude earn code)
                            //  the fmla earn codes=no do not require any exclusion
                            //  the only action required is if the fmla user flag=no: exclude those codes with fmla=yes.

                            if ( (fmlaEligible || ec.getFmla().equals("N")) ) {
                                // go on, we are allowing these three combinations: YY, YN, NN

                                //  Apply the same logic as FMLA to the Worker Compensation flags.
                                if ( (workersCompEligible || ec.getWorkmansComp().equals("N")) ) {
                                    // go on, we are allowing these three combinations: YY, YN, NN.

                                    //  now process the scheduled leave flag, but only for the Planning Calendar, not for the Reporting Calendar.
                                    //  determine if the planning calendar is in effect.
                                    if (isLeavePlanningCalendar) {

                                        //  if the allow_schd_leave flag=yes, add the earn code
                                        if (ec.getAllowScheduledLeave().equals("Y")) {
                                            earnCodes.add(ec);
                                        } else {
                                            //  do not add this earn code. Earn code allowed scheduled leave flag=no.
                                        }

                                    } else {
                                        //  this is a reporting calendar, so ignore scheduled leave flag, and add this earn code.
                                        earnCodes.add(ec);
                                    }

                                } else {
                                    //  do not add this earn code. User WC flag=no and earn code WC flag=yes.
                                }
                            } else {
                                //  do not add this earn code. User FMLA flag=no and earn code FMLA flag=yes.
                            }
                        } else {
                            //  do not add this earn code. accrual category does not allow.
                        }
                    } else {
                        //  skip null earn code
                    }
                } else {
                    //  do not add this earn code. the leave type does not allow.
                }
            } else {
                // do not add this earn code. user and approver settings disallow.
            }
        }   //  end of decs loop

        return earnCodes;
    }

    private boolean addEarnCodeBasedOnEmployeeApproverSettings(EarnCodeSecurity security, Assignment a, Date asOfDate) {
        boolean addEarnCode = false;
        if (security.isEmployee() &&
                (StringUtils.equals(TKUser.getCurrentTargetPerson().getEmployeeId(), GlobalVariables.getUserSession().getPerson().getEmployeeId()))) {
            addEarnCode = true;
        }
        // Check approver flag
        if (!addEarnCode && security.isApprover()) {
            Set<Long> workAreas = TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).getApproverWorkAreas();
            for (Long wa : workAreas) {
                WorkArea workArea = TkServiceLocator.getWorkAreaService().getWorkArea(wa, asOfDate);
                if (workArea!= null && a.getWorkArea().compareTo(workArea.getWorkArea())==0) {
                    addEarnCode = true;
                    break;
                }
            }
        }
        return addEarnCode;
    }

    private boolean showEarnCodeIfHoliday(EarnCode earnCode, EarnCodeSecurity security) {
        if (earnCode.getEarnCode().equals(TkConstants.HOLIDAY_EARN_CODE)) {
            if (security.isApprover() || TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).isSystemAdmin()) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public List<EarnCode> getEarnCodesForPrincipal(String principalId, Date asOfDate, boolean isLeavePlanningCalendar) {
        List<EarnCode> earnCodes = new LinkedList<EarnCode>();
        List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(principalId, asOfDate);
        for (Assignment assignment : assignments) {
            List<EarnCode> assignmentEarnCodes = getEarnCodesForLeave(assignment, asOfDate, isLeavePlanningCalendar);
            //  the following list processing does work as hoped, comparing the objects' data, rather than their references to memory structures.
            earnCodes.removeAll(assignmentEarnCodes); //ensures no overlap during the addAll
            earnCodes.addAll(assignmentEarnCodes);
        }

        return earnCodes;
    }

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
	public Map<String, String> getEarnCodesForDisplay(String principalId, boolean isLeavePlanningCalendar) {
		return getEarnCodesForDisplayWithEffectiveDate(principalId, TKUtils.getCurrentDate(), isLeavePlanningCalendar);
	}

    public List<EarnCode> getEarnCodes(String earnCode, String ovtEarnCode, String descr, String leavePlan, String accrualCategory, Date fromEffdt, Date toEffdt, String active, String showHist) {
        return earnCodeDao.getEarnCodes(earnCode, ovtEarnCode, descr, leavePlan, accrualCategory, fromEffdt, toEffdt, active, showHist);
    }

    @Override
    public Map<String, String> getEarnCodesForDisplayWithEffectiveDate(String principalId, Date asOfDate, boolean isLeavePlanningCalendar) {
        List<EarnCode> earnCodes = this.getEarnCodesForPrincipal(principalId, asOfDate, isLeavePlanningCalendar);

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

}
