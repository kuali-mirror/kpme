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
package org.kuali.kpme.core.bo.utils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.bo.calendar.Calendar;
import org.kuali.kpme.core.bo.department.Department;
import org.kuali.kpme.core.bo.earncode.EarnCode;
import org.kuali.kpme.core.bo.earncode.group.EarnCodeGroup;
import org.kuali.kpme.core.bo.earncode.group.EarnCodeGroupDefinition;
import org.kuali.kpme.core.bo.earncode.security.EarnCodeSecurity;
import org.kuali.kpme.core.bo.kfs.coa.businessobject.Chart;
import org.kuali.kpme.core.bo.leaveplan.LeavePlan;
import org.kuali.kpme.core.bo.location.Location;
import org.kuali.kpme.core.bo.paygrade.PayGrade;
import org.kuali.kpme.core.bo.paytype.PayType;
import org.kuali.kpme.core.bo.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.bo.salarygroup.SalaryGroup;
import org.kuali.kpme.core.bo.task.Task;
import org.kuali.kpme.core.bo.workarea.WorkArea;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.common.TkConstants;
import org.kuali.kpme.tklm.leave.util.LMConstants;
import org.kuali.kpme.tklm.time.authorization.DepartmentalRule;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;

/**
 * A few methods to assist with various validation tasks.
 */
public class ValidationUtils {

    /**
     * For DepartmentalRule objects, if a work area is defined, you can not
     * leave the department field with a wildcard. Permission for wildcarding
     * will be checked with other methods.
     *
     * @param dr The DepartmentalRule to examine.
     * @return true if valid, false otherwise.
     */
    public static boolean validateWorkAreaDeptWildcarding(DepartmentalRule dr) {
        boolean ret = true;

        if (StringUtils.equals(dr.getDept(), TkConstants.WILDCARD_CHARACTER)) {
            ret = dr.getWorkArea().equals(TkConstants.WILDCARD_LONG);
        }

        return ret;
    }

	/**
	 * Most basic validation: Only checks for presence in the database.
	 */
	public static boolean validateWorkArea(Long workArea) {
		return validateWorkArea(workArea, null);
	}

	/**
	 * Most basic validation: Only checks for presence in the database.
	 */
	public static boolean validateDepartment(String department) {
		return validateDepartment(department, null);
	}

	/**
	 * Most basic validation: Only checks for presence in the database.
	 */
	public static boolean validateAccrualCategory(String accrualCategory) {
		return validateAccrualCategory(accrualCategory, null);
	}


	public static boolean validateSalGroup(String salGroup, LocalDate asOfDate) {
		boolean valid = false;

		if (StringUtils.equals(salGroup, TkConstants.WILDCARD_CHARACTER)) {
			valid = true;
		} else if (asOfDate != null) {
			SalaryGroup sg = HrServiceLocator.getSalaryGroupService().getSalaryGroup(salGroup, asOfDate);
			valid = (sg != null);
		} else {
			int count = HrServiceLocator.getSalaryGroupService().getSalGroupCount(salGroup);
			valid = (count > 0);
		}

		return valid;
	}

	public static boolean validateEarnCode(String earnCode, LocalDate asOfDate) {
		boolean valid = false;

		if (asOfDate != null) {
			EarnCode ec = HrServiceLocator.getEarnCodeService().getEarnCode(earnCode, asOfDate);
			valid = (ec != null);
		} else {
			int count = HrServiceLocator.getEarnCodeService().getEarnCodeCount(earnCode);
			valid = (count > 0);
		}

		return valid;
	}
	
	public static boolean validateLeavePlan(String leavePlan, LocalDate asOfDate) {
		boolean valid = false;
		
		if (asOfDate != null) {
			LeavePlan lp = HrServiceLocator.getLeavePlanService().getLeavePlan(leavePlan, asOfDate);
			valid = (lp != null);
		} else {
			// chen, moved the code that access db to service and dao
			valid = HrServiceLocator.getLeavePlanService().isValidLeavePlan(leavePlan);
		}
		
		return valid;
	}

	public static boolean validateEarnCodeOfAccrualCategory(String earnCode, String accrualCategory, LocalDate asOfDate) {
		boolean valid = false;
		
		if (asOfDate != null) {
			AccrualCategory accrualCategoryObj = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualCategory, asOfDate);
			if (accrualCategoryObj != null) {
				if (StringUtils.equals(earnCode, accrualCategoryObj.getEarnCode())) {
					valid = true;
				}
			}
		} else {
			Map<String, String> fieldValues = new HashMap<String, String>();
			fieldValues.put("earnCode", earnCode);
			int matches = KRADServiceLocator.getBusinessObjectService().countMatching(EarnCode.class, fieldValues);
			
			valid = matches > 0;
		}
		
		return valid;
	}
	
	public static boolean validateAccCategory(String accrualCategory, LocalDate asOfDate) {
		boolean valid = false;
		
		if (asOfDate != null) {
			AccrualCategory ac = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualCategory, asOfDate);
			valid = (ac != null);
		} else {
			Map<String, String> fieldValues = new HashMap<String, String>();
			fieldValues.put("accrualCategory", accrualCategory);
			int matches = KRADServiceLocator.getBusinessObjectService().countMatching(AccrualCategory.class, fieldValues);
			
			valid = matches > 0;
		}
		
		return valid;
	}
	
	public static boolean validateAccCategory(String accrualCategory, String principalId, LocalDate asOfDate) {
		boolean valid = false;
		
		if (asOfDate != null) {
			AccrualCategory ac = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualCategory, asOfDate);
			if(ac != null && ac.getLeavePlan() != null) {
				// fetch leave plan users
				if(principalId != null) {
					PrincipalHRAttributes principalHRAttributes = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, asOfDate);
					if(principalHRAttributes != null && principalHRAttributes.getLeavePlan() != null) {
						valid = StringUtils.equals(ac.getLeavePlan().trim(), principalHRAttributes.getLeavePlan().trim());
					}
				} else {
					valid = true;
				}
			} 
		} else {
			Map<String, String> fieldValues = new HashMap<String, String>();
			fieldValues.put("accrualCategory", accrualCategory);
			int matches = KRADServiceLocator.getBusinessObjectService().countMatching(AccrualCategory.class, fieldValues);
			
			valid = matches > 0;
		}
		return valid;
	}
	
	public static boolean validateLocation(String location, LocalDate asOfDate) {
		boolean valid = false;

		if (asOfDate != null) {
			Location l = HrServiceLocator.getLocationService().getLocation(location, asOfDate);
			valid = (l != null);
		} else {
			int count = HrServiceLocator.getLocationService().getLocationCount(location);
			valid = (count > 0);
		}

		return valid;
	}

	public static boolean validatePayType(String payType, LocalDate asOfDate) {
		boolean valid = false;

		if (asOfDate != null) {
			PayType pt = HrServiceLocator.getPayTypeService().getPayType(payType, asOfDate);
			valid = (pt != null);
		} else {
			int count = HrServiceLocator.getPayTypeService().getPayTypeCount(payType);
			valid = (count > 0);
		}

		return valid;
	}


	public static boolean validatePayGrade(String payGrade, String salGroup, LocalDate asOfDate) {
		boolean valid = false;

		if (asOfDate != null) {
			PayGrade pg = HrServiceLocator.getPayGradeService().getPayGrade(payGrade, salGroup, asOfDate);
			valid = (pg != null);
		} else {
			int count = HrServiceLocator.getPayGradeService().getPayGradeCount(payGrade);
			valid = (count > 0);
		}

		return valid;
	}

    /**
     *
     * @param earnCode
     * @param otEarnCode If true, earn code is valid ONLY if it is an overtime earn code.
     * @param asOfDate
     * @return
     */
    public static boolean validateEarnCode(String earnCode, boolean otEarnCode, LocalDate asOfDate) {
        boolean valid = false;

        if (asOfDate != null) {
            EarnCode ec = HrServiceLocator.getEarnCodeService().getEarnCode(earnCode, asOfDate);
            valid = (ec != null) && (otEarnCode ? ec.getOvtEarnCode().booleanValue() : true);
        }

        return valid;
    }

	/**
	 * Checks for row presence of a department, and optionally whether or not
	 * it is active as of the specified date.
	 */
	public static boolean validateDepartment(String department, LocalDate asOfDate) {
		boolean valid = false;

        if (StringUtils.isEmpty(department)) {
          // do nothing, let false be returned.
        } else if (asOfDate != null) {
			Department d = HrServiceLocator.getDepartmentService().getDepartment(department, asOfDate);
		    valid = (d != null);
		} else {
			int count = HrServiceLocator.getDepartmentService().getDepartmentCount(department);
			valid = (count > 0);
		}

		return valid;
	}

    public static boolean validateChart(String chart) {
        boolean valid = false;

        if (!StringUtils.isEmpty(chart)) {
            Object o = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(Chart.class, chart);
            valid = (o instanceof Chart);
        }

        return valid;
    }

	/**
	 * Checks for row presence of a work area, and optionally whether or not
	 * it is active as of the specified date.
	 */
    public static boolean validateWorkArea(Long workArea, LocalDate asOfDate) {
        return ValidationUtils.validateWorkArea(workArea, null, asOfDate);
    }

	public static boolean validateWorkArea(Long workArea, String dept, LocalDate asOfDate) {
		boolean valid = false;

		if (workArea == null) {
			valid = false;
		} else if (workArea.equals(TkConstants.WILDCARD_LONG)) {
			valid = true;
		} else if (asOfDate != null) {
			WorkArea wa = HrServiceLocator.getWorkAreaService().getWorkArea(workArea, asOfDate);
            if (wa != null && dept != null) {
                valid = StringUtils.equalsIgnoreCase(dept, wa.getDept());
            } else {
			    valid = (wa != null);
            }
		} else {
            // Not valid if no date is passed.
		}

		return valid;
	}
	/**
	 * Checks for row presence of a Accrual Category, and optionally whether or not
	 * it is active as of the specified date.
	 */
	public static boolean validateAccrualCategory(String accrualCategory, LocalDate asOfDate) {
		boolean valid = false;

		if (StringUtils.equals(accrualCategory, TkConstants.WILDCARD_CHARACTER)) {
			valid = true;
		} else if (asOfDate != null) {
			AccrualCategory ac = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualCategory, asOfDate);
			valid = (ac != null);
		}

		return valid;
	}

	/**
	 * Checks for row presence of a principal Id, and optionally whether or not
	 * it is active as of the specified date.
	 */
	public static boolean validatePrincipalId(String principalId) {
		boolean valid = false;
		if (principalId != null) {
			Principal p = KimApiServiceLocator.getIdentityService().getPrincipal(principalId);
		    valid = (p != null);
		}
		return valid;
	}

    /**
     * No wildcarding is accounted for in this method.
     * @param task Task "Long Name"
     * @param asOfDate Can be null, if we just want to look for the general case.
     * @return True if the task is present / valid.
     */
    public static boolean validateTask(Long task, LocalDate asOfDate) {
        boolean valid = false;

        if (task != null && asOfDate != null) {
            Task t = HrServiceLocator.getTaskService().getTask(task, asOfDate);
            valid = (t != null);
        } else if (task != null) {
        	int count = HrServiceLocator.getTaskService().getTaskCount(task);
            valid = (count > 0);
        }

        return valid;
    }

    /**
     * No wildcarding is accounted for in this method.
     * @param earnGroup EarnCodeGroup
     * @param asOfDate Can be null, if we just want to look for the general case.
     * @return True if the EarnCodeGroup is present / valid.
     */
    public static boolean validateEarnGroup(String earnGroup, LocalDate asOfDate) {
        boolean valid = false;

        if (earnGroup != null && asOfDate != null) {
            EarnCodeGroup eg = HrServiceLocator.getEarnCodeGroupService().getEarnCodeGroup(earnGroup, asOfDate);
            valid = (eg != null);
        } else if (earnGroup != null) {
        	int count = HrServiceLocator.getEarnCodeGroupService().getEarnCodeGroupCount(earnGroup);
            valid = (count > 0);
        }

        return valid;
    }
    
    /**
     * @param earnGroup EarnCodeGroup
     * @param asOfDate
     * @return True if the EarnCodeGroup has overtime earn codes
     */
    public static boolean earnGroupHasOvertimeEarnCodes(String earnGroup, LocalDate asOfDate) {
         if (earnGroup != null && asOfDate != null) {
             EarnCodeGroup eg = HrServiceLocator.getEarnCodeGroupService().getEarnCodeGroup(earnGroup, asOfDate);
             if(eg != null) {
            	for(EarnCodeGroupDefinition egd : eg.getEarnCodeGroups()) {
            		if(egd.getEarnCode() != null) {
            			EarnCode ec = HrServiceLocator.getEarnCodeService().getEarnCode(egd.getEarnCode(), asOfDate);
            			if(ec != null && ec.getOvtEarnCode()) {
            				return true;
            			}
            		}
            	}
             }
         }

        return false;
    }


	/**
	 * Checks for row presence of a pay calendar
	 */
	public static boolean validateCalendar(String calendarName) {
		Map<String, String> fieldValues = new HashMap<String, String>();
		fieldValues.put("calendarName", calendarName);
		int matches = KRADServiceLocator.getBusinessObjectService().countMatching(Calendar.class, fieldValues);

        return matches > 0;
	}

   public static boolean duplicateDeptEarnCodeExists(EarnCodeSecurity deptEarnCode) {
	   boolean valid = false;
	   int count = HrServiceLocator.getEarnCodeSecurityService().getEarnCodeSecurityCount
               (deptEarnCode.getDept(), deptEarnCode.getHrSalGroup(), deptEarnCode.getEarnCode(), deptEarnCode.isEmployee() ? "1" : "0",
                       deptEarnCode.isApprover() ? "1" : "0", deptEarnCode.getLocation(), deptEarnCode.isActive() ? "Y" : "N", deptEarnCode.getEffectiveLocalDate(), null);
       if(count == 1) {
    	   valid = true;
    	   count = HrServiceLocator.getEarnCodeSecurityService().getEarnCodeSecurityCount
                   (deptEarnCode.getDept(), deptEarnCode.getHrSalGroup(), deptEarnCode.getEarnCode(), deptEarnCode.isEmployee() ? "1" : "0",
                           deptEarnCode.isApprover() ? "1" : "0", deptEarnCode.getLocation(), deptEarnCode.isActive() ? "Y" : "N", deptEarnCode.getEffectiveLocalDate(), deptEarnCode.getHrEarnCodeSecurityId());
    	   if(count == 1) {
    		   valid = false;
    	   }
       } else if(count > 1) {
    	   valid = true;
       }

	   return valid;
   }
   
    /**
    * Checks for date not more than one year in the future from accrualDAte
    * 
    */

   public static boolean validateOneYearFutureDateFromAccrualDate(Date date, Date accrualDate){
	   java.util.Calendar startDate = java.util.Calendar.getInstance();
	   startDate.setTime(accrualDate);
	   startDate.add(java.util.Calendar.DATE, -1);
	   startDate.set(java.util.Calendar.SECOND, 0);
	   startDate.set(java.util.Calendar.MINUTE, 0);
	   startDate.set(java.util.Calendar.HOUR_OF_DAY, 0);
	   java.util.Calendar endDate = java.util.Calendar.getInstance();
	   endDate.setTime(accrualDate);
	   endDate.add(java.util.Calendar.YEAR, 1); // One year after the current date
	   return date.compareTo(startDate.getTime()) * date.compareTo(endDate.getTime()) <= 0;
   }
   
   
   /**
    * Checks for date not more than one year in the future or current date
    * 
    */

   public static boolean validateOneYearFutureDate(LocalDate date){
	   java.util.Calendar startDate = java.util.Calendar.getInstance();
	   startDate.add(java.util.Calendar.DATE, -1);
	   startDate.set(java.util.Calendar.SECOND, 0);
	   startDate.set(java.util.Calendar.MINUTE, 0);
	   startDate.set(java.util.Calendar.HOUR_OF_DAY, 0);
	   java.util.Calendar endDate = java.util.Calendar.getInstance();
	   endDate.add(java.util.Calendar.YEAR, 1); // One year after the current date
	   return date.toDate().compareTo(startDate.getTime()) * date.toDate().compareTo(endDate.getTime()) <= 0;
   }
   
   /**
    * Checks for date not more than one year in the future and does not consider past date
    * 
    */

   public static boolean validateOneYearFutureEffectiveDate(LocalDate date){
	   java.util.Calendar startDate = java.util.Calendar.getInstance();
	   startDate.set(java.util.Calendar.MILLISECOND, 0);
	   startDate.set(java.util.Calendar.SECOND, 0);
	   startDate.set(java.util.Calendar.MINUTE, 0);
	   startDate.set(java.util.Calendar.HOUR_OF_DAY, 0);
	   startDate.add(java.util.Calendar.YEAR, 1); // One year after the current date
	   return date.toDate().compareTo(startDate.getTime()) <= 0;
   }
   
   /**
    * Checks for date in the future
    * 
    */
   
   public static boolean validateFutureDate(LocalDate date){
	   java.util.Calendar startDate = java.util.Calendar.getInstance();
	   startDate.add(java.util.Calendar.DATE, 0);
	   startDate.set(java.util.Calendar.SECOND, 0);
	   startDate.set(java.util.Calendar.MINUTE, 0);
	   startDate.set(java.util.Calendar.HOUR_OF_DAY, 0);
	   return date.toDate().compareTo(startDate.getTime()) > 0;
   }

	/**
	 * Checks for row presence of a pay calendar by calendar type
	 */
	public static boolean validateCalendarByType(String calendarName, String calendarType) {
		Map<String, String> fieldValues = new HashMap<String, String>();
		fieldValues.put("calendarName", calendarName);
		fieldValues.put("calendarTypes", calendarType);
		int matches = KRADServiceLocator.getBusinessObjectService().countMatching(Calendar.class, fieldValues);
		
		return matches > 0;
	}
	
	public static boolean validateRecordMethod(String recordMethod, String accrualCategory, LocalDate asOfDate) {
		boolean valid = false;
		if (asOfDate != null) {
			AccrualCategory ac = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualCategory, asOfDate);
			if (ac != null
                    && ac.getUnitOfTime() != null) {
                if (LMConstants.RECORD_METHOD.HOUR.equals(ac.getUnitOfTime())
                        && (LMConstants.RECORD_METHOD.HOUR.equals(recordMethod))
                            || LMConstants.RECORD_METHOD.TIME.equals(recordMethod)) {
                    valid = true;
                } else {
                    valid = StringUtils.equalsIgnoreCase(ac.getUnitOfTime(), recordMethod);
                }

            }
		}
		return valid;
	}
	
	public static boolean validateEarnCodeFraction(String earnCode, BigDecimal amount, LocalDate asOfDate) {
		boolean valid = true;
		 EarnCode ec = HrServiceLocator.getEarnCodeService().getEarnCode(earnCode, asOfDate);
		 if(ec != null && ec.getFractionalTimeAllowed() != null) {
			 BigDecimal fracAllowed = new BigDecimal(ec.getFractionalTimeAllowed());
			 if(amount.scale() > fracAllowed.scale()) {
				 valid = false;
			 }
		 }
		return valid;
	}
}
