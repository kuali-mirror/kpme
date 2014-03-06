/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.core.earncode.service;

import com.google.common.collect.Ordering;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.accrualcategory.AccrualCategoryContract;
import org.kuali.kpme.core.api.assignment.AssignmentContract;
import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.api.earncode.EarnCode;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.api.earncode.security.EarnCodeSecurityContract;
import org.kuali.kpme.core.api.earncode.service.EarnCodeService;
import org.kuali.kpme.core.api.job.JobContract;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.principal.PrincipalHRAttributesContract;
import org.kuali.kpme.core.api.workarea.WorkAreaContract;
import org.kuali.kpme.core.earncode.EarnCodeBo;
import org.kuali.kpme.core.earncode.dao.EarnCodeDao;
import org.kuali.kpme.core.earncode.security.EarnCodeType;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.kim.api.role.RoleService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

import java.math.BigDecimal;
import java.util.*;

public class EarnCodeServiceImpl implements EarnCodeService {

	private EarnCodeDao earnCodeDao;
    private static final ModelObjectUtils.Transformer<EarnCodeBo, EarnCode> toEarnCode =
            new ModelObjectUtils.Transformer<EarnCodeBo, EarnCode>() {
                public EarnCode transform(EarnCodeBo input) {
                    return EarnCodeBo.to(input);
                };
            };
	private static final Logger LOG = Logger.getLogger(EarnCodeServiceImpl.class);

	public void setEarnCodeDao(EarnCodeDao earnCodeDao) {
		this.earnCodeDao = earnCodeDao;
	}

    @Override
    public List<EarnCode> getEarnCodesForLeave(AssignmentContract a, LocalDate asOfDate, boolean isLeavePlanningCalendar) {
        return ModelObjectUtils.transform(getEarnCodeBosForLeave(a, asOfDate, isLeavePlanningCalendar), toEarnCode);
    }

	//Move to LeaveCalendarDocumentService
    protected List<EarnCodeBo> getEarnCodeBosForLeave(AssignmentContract a, LocalDate asOfDate, boolean isLeavePlanningCalendar) {
        //getEarnCodesForTime and getEarnCodesForLeave have some overlapping logic, but they were separated so that they could follow their own distinct logic, so consolidation of logic is not desirable.

        if (a == null){
        	LOG.error("No assignment parameter.");
        	return null;
//        	throw new RuntimeException("No assignment parameter.");
        }
        
        JobContract job = a.getJob();
        if (job == null || job.getPayTypeObj() == null) { 
//        	throw new RuntimeException("Null job or null job pay type on assignment.");
        	LOG.error("Null job or null job pay type on assignment.");
        	return null;
        }

        List<EarnCodeBo> earnCodes = new LinkedList<EarnCodeBo>();
        String earnTypeCode = EarnCodeType.LEAVE.getCode();
        // skip getting the regular earn code for Leave Calendar

        List<String> listAccrualCategories = new LinkedList<String>();
        String accrualCategory;

        //  first make a list of the accrual categories available to the user's leave plan, for later comparison.
        PrincipalHRAttributesContract principalHRAttributes = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(job.getPrincipalId(), asOfDate);
        boolean fmlaEligible = principalHRAttributes.isFmlaEligible();
        boolean workersCompEligible = principalHRAttributes.isWorkersCompEligible();

        String leavePlan = principalHRAttributes.getLeavePlan();
        if (leavePlan != null) {
            for (AccrualCategoryContract accrualCategories : HrServiceLocator.getAccrualCategoryService().getActiveAccrualCategoriesForLeavePlan(leavePlan, asOfDate)) {
                accrualCategory = accrualCategories.getAccrualCategory();
                if(accrualCategory != null) {
                    listAccrualCategories.add(accrualCategory);
                }
            }
        }

        //  get all earn codes by user security, then we'll filter on accrual category first as we process them.
        List<? extends EarnCodeSecurityContract> decs = HrServiceLocator.getEarnCodeSecurityService().getEarnCodeSecurities(job.getDept(), job.getHrSalGroup(), job.getLocation(), asOfDate);

        for (EarnCodeSecurityContract dec : decs) {

            boolean addEarnCode = addEarnCodeBasedOnEmployeeApproverSettings(dec, a, asOfDate);
            if (addEarnCode) {

                //  allow types Leave AND Both
                if (earnTypeCode.equals(dec.getEarnCodeType()) || EarnCodeType.BOTH.getCode().equals(dec.getEarnCodeType())) {
                    EarnCodeBo ec = getEarnCodeBo(dec.getEarnCode(), asOfDate);

                    //  make sure we got something back from the earn code dao
                    if (ec != null) {
                    	// make sure the earn code's leave plan matches the user's leave plan
                    	if((StringUtils.isNotBlank(leavePlan) && StringUtils.isNotBlank(ec.getLeavePlan()) && leavePlan.equals(ec.getLeavePlan()))
    							|| (StringUtils.isBlank(leavePlan) && StringUtils.isBlank(ec.getLeavePlan()))) {
	                        //  if the user's fmla flag is Yes, that means we are not restricting codes based on this flag, so any code is shown.
	                        //    if the fmla flag on a code is yes they can see it.    (allow)
	                        //    if the fmla flag on a code is no they should see it.  (allow)
	                        //  if the user's fmla flag is No,
	                        //    they can see any codes which are fmla=no.             (allow)
	                        //    they can not see codes with fmla=yes.                 (exclude earn code)
	                        //  the fmla earn codes=no do not require any exclusion
	                        //  the only action required is if the fmla user flag=no: exclude those codes with fmla=yes.
	
	                        if ( (fmlaEligible || ec.getFmla().equals("N")) ) {
	                            if (listAccrualCategories.contains(ec.getAccrualCategory()) || ec.getAccrualCategory() == null) {
	                                if (StringUtils.equals(ec.getAccrualBalanceAction(), HrConstants.ACCRUAL_BALANCE_ACTION.USAGE)) {
	                                	// go on, we are allowing these three combinations: YY, YN, NN.
	                                	
	                                	//  Apply the same logic as FMLA to the Worker Compensation flags.
	                                	if ( (workersCompEligible || ec.getWorkmansComp().equals("N")) ) {
	                                		// go on, we are allowing these three combinations: YY, YN, NN.
	
		                                    //  now process the scheduled leave flag, but only for the Planning Calendar, not for the Reporting Calendar.
		                                    //  determine if the planning calendar is in effect.
		                                    if (isLeavePlanningCalendar) {
		
		                                        //  if the allow_schd_leave flag=yes, add the earn code
		                                        if (ec.getAllowScheduledLeave().equals("Y")) {
		                                                earnCodes.add(ec);
		                                        }
		
		                                    } else {
		                                        //  this is a reporting calendar, so ignore scheduled leave flag, and add this earn code.
		                                        earnCodes.add(ec);
		                                    }
		                                }
	                                }
	                            }
	                        }
                    	}
                    }
                }
            }
        }   //  end of decs loop

        return earnCodes;
    }

    public boolean addEarnCodeBasedOnEmployeeApproverSettings(EarnCodeSecurityContract security, AssignmentContract a, LocalDate asOfDate) {
        boolean addEarnCode = false;
        if (security.isEmployee() &&
                (StringUtils.equals(HrContext.getTargetPrincipalId(), GlobalVariables.getUserSession().getPrincipalId())
                		|| HrServiceLocator.getKPMEGroupService().isMemberOfSystemAdministratorGroup(GlobalVariables.getUserSession().getPrincipalId(), asOfDate.toDateTimeAtCurrentTime()))) {
            addEarnCode = true;
        }

        RoleService roleService = KimApiServiceLocator.getRoleService();
        if (!addEarnCode && (security.isEmployee() || security.isApprover() || security.isPayrollProcessor())) {
            String principalId = GlobalVariables.getUserSession().getPrincipalId();
            List<String> roleIds = new ArrayList<String>();
            roleIds.add(roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName()));
            roleIds.add(roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_SYSTEM_ADMINISTRATOR.getRoleName()));
            roleIds.add(roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_SYSTEM_ADMINISTRATOR.getRoleName()));
            roleIds.add(roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName()));
            List<Long> workAreas = HrServiceLocator.getKPMERoleService().getWorkAreasForPrincipalInRoles(principalId, roleIds, asOfDate.toDateTimeAtStartOfDay(), true);

            for (Long wa : workAreas) {
                WorkAreaContract workArea = HrServiceLocator.getWorkAreaService().getWorkAreaWithoutRoles(wa, asOfDate);
                if (workArea!= null && a.getWorkArea().compareTo(workArea.getWorkArea())==0) {
                    addEarnCode = true;
                    break;
                }
            }
        }
        // Check approver flag
        if (!addEarnCode && security.isApprover()) {
        	String principalId = GlobalVariables.getUserSession().getPrincipalId();

            List<String> roleIds = new ArrayList<String>();
            roleIds.add(roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName()));
            roleIds.add(roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER_DELEGATE.getRoleName()));
            roleIds.add(roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.REVIEWER.getRoleName()));
            List<Long> workAreas = HrServiceLocator.getKPMERoleService().getWorkAreasForPrincipalInRoles(principalId, roleIds, asOfDate.toDateTimeAtStartOfDay(), true);

            for (Long wa : workAreas) {
                WorkAreaContract workArea = HrServiceLocator.getWorkAreaService().getWorkAreaWithoutRoles(wa, asOfDate);
                if (workArea!= null && a.getWorkArea().compareTo(workArea.getWorkArea())==0) {
                    addEarnCode = true;
                    break;
                }
            }
        }

        if (!addEarnCode && security.isPayrollProcessor()) {
        	String principalId = GlobalVariables.getUserSession().getPrincipalId();
            List<String> roleIds = new ArrayList<String>();
            roleIds.add(roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR.getRoleName()));
            roleIds.add(roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR_DELEGATE.getRoleName()));
            List<String> depts = HrServiceLocator.getKPMERoleService().getDepartmentsForPrincipalInRoles(principalId, roleIds, asOfDate.toDateTimeAtStartOfDay(), true);


            for (String dept : depts) {
                Department department = HrServiceLocator.getDepartmentService().getDepartment(dept, asOfDate);
                if (department!= null && a.getDept().equalsIgnoreCase(department.getDept())) {
                    addEarnCode = true;
                    break;
                }
            }

        }
        return addEarnCode;
    }

    @Override
    public List<EarnCode> getEarnCodesForPrincipal(String principalId, LocalDate asOfDate, boolean isLeavePlanningCalendar) {
        Set<EarnCodeBo> earnCodes = new HashSet<EarnCodeBo>();
        List<? extends AssignmentContract> assignments = HrServiceLocator.getAssignmentService().getAssignments(principalId, asOfDate);
        for (AssignmentContract assignment : assignments) {
            List<EarnCodeBo> assignmentEarnCodes = getEarnCodeBosForLeave(assignment, asOfDate, isLeavePlanningCalendar);
            //  the following list processing does work as hoped, comparing the objects' data, rather than their references to memory structures.
            earnCodes.addAll(assignmentEarnCodes);
        }

        return ModelObjectUtils.transform(new ArrayList<EarnCodeBo>(earnCodes), toEarnCode);
    }

    @Override
    public EarnCode getEarnCode(String earnCode, LocalDate asOfDate) {
		return EarnCodeBo.to(getEarnCodeBo(earnCode, asOfDate));
	}

    protected EarnCodeBo getEarnCodeBo(String earnCode, LocalDate asOfDate) {
        return earnCodeDao.getEarnCode(earnCode, asOfDate);
    }

    @Override
    public String getEarnCodeType(String earnCode, LocalDate asOfDate) {
        EarnCodeBo earnCodeObj = getEarnCodeBo(earnCode, asOfDate);
        return earnCodeObj != null ? earnCodeObj.getEarnCodeType() : "";       
    }

	@Override
	public EarnCode getEarnCodeById(String earnCodeId) {
		return EarnCodeBo.to(getEarnCodeBoById(earnCodeId));
	}

    public EarnCodeBo getEarnCodeBoById(String earnCodeId) {
        return earnCodeDao.getEarnCodeById(earnCodeId);
    }

	public List<EarnCode> getOvertimeEarnCodes(LocalDate asOfDate){
		return ModelObjectUtils.transform(earnCodeDao.getOvertimeEarnCodes(asOfDate), toEarnCode);
	}

	public List<String> getOvertimeEarnCodesStrs(LocalDate asOfDate){
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
	public int getNewerEarnCodeCount(String earnCode, LocalDate effdt) {
		return earnCodeDao.getNewerEarnCodeCount(earnCode, effdt);
	}

	@Override
	public BigDecimal roundHrsWithEarnCode(BigDecimal hours, EarnCodeContract earnCode) {
		String roundOption = HrConstants.ROUND_OPTION_MAP.get(earnCode.getRoundingOption());
		BigDecimal fractScale = new BigDecimal(earnCode.getFractionalTimeAllowed());
		if(roundOption == null) {
			LOG.error("Rounding option of Earn Code " + earnCode.getEarnCode() + " is not recognized.");
        	return null;
//			throw new RuntimeException("Rounding option of Earn Code " + earnCode.getEarnCode() + " is not recognized.");
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
		return getEarnCodesForDisplayWithEffectiveDate(principalId, LocalDate.now(), isLeavePlanningCalendar);
	}

    public List<EarnCode> getEarnCodes(String earnCode, String ovtEarnCode, String descr, String leavePlan, String accrualCategory, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHist) {
        return ModelObjectUtils.transform(earnCodeDao.getEarnCodes(earnCode, ovtEarnCode, descr, leavePlan, accrualCategory, fromEffdt, toEffdt, active, showHist), toEarnCode);
    }

    @Override
    public Map<String, String> getEarnCodesForDisplayWithEffectiveDate(String principalId, LocalDate asOfDate, boolean isLeavePlanningCalendar) {
        List<EarnCode> earnCodes = this.getEarnCodesForPrincipal(principalId, asOfDate, isLeavePlanningCalendar);

        boolean futureDate = asOfDate.isAfter(LocalDate.now());
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
            earnCodesForDisplay.put(getEarnCodeKeyForDisplay(earnCode), getEarnCodeValueForDisplay(earnCode));
        }
        return earnCodesForDisplay;
    }

    protected String getEarnCodeKeyForDisplay(EarnCode earnCode) {
        return earnCode.getHrEarnCodeId();
    }

    protected String getEarnCodeValueForDisplay(EarnCode earnCode) {
        return earnCode.getEarnCode() + " : " + earnCode.getDescription();
    }

}
