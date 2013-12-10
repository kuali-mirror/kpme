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
package org.kuali.kpme.core.util;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.api.accrualcategory.AccrualCategoryContract;
import org.kuali.kpme.core.api.calendar.CalendarContract;
import org.kuali.kpme.core.api.department.DepartmentContract;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.api.earncode.group.EarnCodeGroupContract;
import org.kuali.kpme.core.api.earncode.group.EarnCodeGroupDefinitionContract;
import org.kuali.kpme.core.api.institution.InstitutionContract;
import org.kuali.kpme.core.api.leaveplan.LeavePlanContract;
import org.kuali.kpme.core.api.location.LocationContract;
import org.kuali.kpme.core.api.paygrade.PayGradeContract;
import org.kuali.kpme.core.api.paytype.PayTypeContract;
import org.kuali.kpme.core.api.principal.PrincipalHRAttributesContract;
import org.kuali.kpme.core.api.salarygroup.SalaryGroupContract;
import org.kuali.kpme.core.api.task.TaskContract;
import org.kuali.kpme.core.api.workarea.WorkAreaContract;
import org.kuali.kpme.core.authorization.DepartmentalRule;
import org.kuali.kpme.core.calendar.Calendar;
import org.kuali.kpme.core.earncode.EarnCode;
import org.kuali.kpme.core.earncode.security.EarnCodeSecurity;
import org.kuali.kpme.core.kfs.coa.businessobject.Account;
import org.kuali.kpme.core.kfs.coa.businessobject.Chart;
import org.kuali.kpme.core.kfs.coa.businessobject.ObjectCode;
import org.kuali.kpme.core.kfs.coa.businessobject.Organization;
import org.kuali.kpme.core.kfs.coa.businessobject.SubAccount;
import org.kuali.kpme.core.kfs.coa.businessobject.SubObjectCode;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.location.api.campus.Campus;
import org.kuali.rice.location.api.services.LocationApiServiceLocator;

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

        if (StringUtils.equals(dr.getDept(), HrConstants.WILDCARD_CHARACTER)) {
            ret = dr.getWorkArea().equals(HrConstants.WILDCARD_LONG);
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

		if (StringUtils.equals(salGroup, HrConstants.WILDCARD_CHARACTER)) {
			valid = true;
		} else if (asOfDate != null) {
			SalaryGroupContract sg = HrServiceLocator.getSalaryGroupService().getSalaryGroup(salGroup, asOfDate);
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
			EarnCodeContract ec = HrServiceLocator.getEarnCodeService().getEarnCode(earnCode, asOfDate);
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
			LeavePlanContract lp = HrServiceLocator.getLeavePlanService().getLeavePlan(leavePlan, asOfDate);
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
			AccrualCategoryContract accrualCategoryObj = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualCategory, asOfDate);
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
			AccrualCategoryContract ac = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualCategory, asOfDate);
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
			AccrualCategoryContract ac = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualCategory, asOfDate);
			if(ac != null && ac.getLeavePlan() != null) {
				// fetch leave plan users
				if(principalId != null) {
					PrincipalHRAttributesContract principalHRAttributes = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, asOfDate);
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
		if(StringUtils.isNotEmpty(location)) {
			if (asOfDate != null) {
				if(ValidationUtils.isWildCard(location)) {
					int count = HrServiceLocator.getLocationService().getLocationCount(location, asOfDate);
					valid = (count > 0);
				} else {
					LocationContract l = HrServiceLocator.getLocationService().getLocation(location, asOfDate);
					valid = (l != null);
				}
			}
		}

		return valid;
	}

	public static boolean validatePayType(String payType, LocalDate asOfDate) {
		boolean valid = false;

		if (asOfDate != null) {
			PayTypeContract pt = HrServiceLocator.getPayTypeService().getPayType(payType, asOfDate);
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
			PayGradeContract pg = HrServiceLocator.getPayGradeService().getPayGrade(payGrade, salGroup, asOfDate);
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
            EarnCodeContract ec = HrServiceLocator.getEarnCodeService().getEarnCode(earnCode, asOfDate);
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
			DepartmentContract d = HrServiceLocator.getDepartmentService().getDepartmentWithoutRoles(department, asOfDate);
		    valid = (d != null);
		} else {
			int count = HrServiceLocator.getDepartmentService().getDepartmentCount(department);
			valid = (count > 0);
		}

		return valid;
	}

	/**
	 * Check for row existence of the specified chart and also, if found, that the chart is active.
	 * 
	 * @param chart
	 * @return
	 */
    public static boolean validateChart(String chart) {
        boolean valid = false;

        if (!StringUtils.isEmpty(chart)) {
            Object o = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(Chart.class, chart);
            if(o instanceof Chart) {
            	Chart chartObj = (Chart) o;
            	valid = chartObj.isActive();
            }
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
		} else if (workArea.equals(HrConstants.WILDCARD_LONG)) {
			valid = true;
		} else if (asOfDate != null) {
			WorkAreaContract wa = HrServiceLocator.getWorkAreaService().getWorkAreaWithoutRoles(workArea, asOfDate);
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

		if (StringUtils.equals(accrualCategory, HrConstants.WILDCARD_CHARACTER)) {
			valid = true;
		} else if (asOfDate != null) {
			AccrualCategoryContract ac = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualCategory, asOfDate);
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
            TaskContract t = HrServiceLocator.getTaskService().getTask(task, asOfDate);
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
            EarnCodeGroupContract eg = HrServiceLocator.getEarnCodeGroupService().getEarnCodeGroup(earnGroup, asOfDate);
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
             EarnCodeGroupContract eg = HrServiceLocator.getEarnCodeGroupService().getEarnCodeGroup(earnGroup, asOfDate);
             if(eg != null) {
            	for(EarnCodeGroupDefinitionContract egd : eg.getEarnCodeGroups()) {
            		if(egd.getEarnCode() != null) {
            			EarnCodeContract ec = HrServiceLocator.getEarnCodeService().getEarnCode(egd.getEarnCode(), asOfDate);
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
		CalendarContract calendar = HrServiceLocator.getCalendarService().getCalendarByName(calendarName);
		System.out.println("Calendar: "  + calendarName);
		if(calendar!=null){
			return true;
		}else{
			return false;
		}
	}

   public static boolean duplicateDeptEarnCodeExists(EarnCodeSecurity deptEarnCode) {
	   boolean valid = false;
	   int count = HrServiceLocator.getEarnCodeSecurityService().getEarnCodeSecurityCount
               (deptEarnCode.getDept(), deptEarnCode.getHrSalGroup(), deptEarnCode.getEarnCode(), deptEarnCode.isEmployee() ? "1" : "0",
                       deptEarnCode.isApprover() ? "1" : "0", deptEarnCode.isPayrollProcessor() ? "1" : "0", deptEarnCode.getLocation(), deptEarnCode.isActive() ? "Y" : "N", deptEarnCode.getEffectiveLocalDate(), null);
       if(count == 1) {
    	   valid = true;
    	   count = HrServiceLocator.getEarnCodeSecurityService().getEarnCodeSecurityCount
                   (deptEarnCode.getDept(), deptEarnCode.getHrSalGroup(), deptEarnCode.getEarnCode(), deptEarnCode.isEmployee() ? "1" : "0",
                           deptEarnCode.isApprover() ? "1" : "0", deptEarnCode.isPayrollProcessor() ? "1" : "0", deptEarnCode.getLocation(), deptEarnCode.isActive() ? "Y" : "N", deptEarnCode.getEffectiveLocalDate(), deptEarnCode.getHrEarnCodeSecurityId());
    	   if(count == 1) {
    		   valid = false;
    	   }
       } else if(count > 1) {
    	   valid = true;
       }

	   return valid;
   }
   
   /**
    * Checks for date not more than one year in the future or current date
    * 
    */

   public static boolean validateOneYearFutureDate(LocalDate date){
	   LocalDate startDate = LocalDate.now().minusDays(1);
	   LocalDate endDate = LocalDate.now().plusYears(1); // One year after the current date
	   return date.compareTo(startDate) * date.compareTo(endDate) <= 0;
   }
   
   /**
    * Checks for date not more than one year in the future and does not consider past date
    * 
    */

   public static boolean validateOneYearFutureEffectiveDate(LocalDate date){
	   LocalDate startDate = LocalDate.now().plusYears(1); // One year after the current date
	   return date.compareTo(startDate) <= 0;
   }
   
   /**
    * Checks for date in the future
    * 
    */
   
   public static boolean validateFutureDate(LocalDate date){
	   LocalDate startDate = LocalDate.now();
	   return date.compareTo(startDate) > 0;
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
			AccrualCategoryContract ac = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualCategory, asOfDate);
			if (ac != null
                    && ac.getUnitOfTime() != null) {
                if (HrConstants.RECORD_METHOD.HOUR.equals(ac.getUnitOfTime())
                        && (HrConstants.RECORD_METHOD.HOUR.equals(recordMethod))
                            || HrConstants.RECORD_METHOD.TIME.equals(recordMethod)) {
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
		 EarnCodeContract ec = HrServiceLocator.getEarnCodeService().getEarnCode(earnCode, asOfDate);
		 if(ec != null && ec.getFractionalTimeAllowed() != null) {
			 BigDecimal fracAllowed = new BigDecimal(ec.getFractionalTimeAllowed());
			if(amount == null) {
				amount=BigDecimal.ZERO;
			}
			 if(amount.scale() > fracAllowed.scale()) {
				 valid = false;
			 }
			
		 }
		return valid;
	}
	
	public static boolean validatePayGradeWithSalaryGroup(String salaryGroup, String payGrade, LocalDate asOfDate) {
		if (asOfDate != null) {
			PayGradeContract grade = HrServiceLocator.getPayGradeService().getPayGrade(payGrade, salaryGroup, asOfDate);
			if(grade != null && StringUtils.isNotBlank(grade.getSalGroup())) 
				return StringUtils.equals(grade.getSalGroup(), salaryGroup);
		}
		return false;
	}
	
	// From PmValidationUtils
	public static boolean validateInstitution(String institutionCode, LocalDate asOfDate) {
		boolean valid = false;
		if(StringUtils.isNotEmpty(institutionCode)) {
			if (asOfDate != null) {
				if(ValidationUtils.isWildCard(institutionCode)) {
					int count =  HrServiceLocator.getInstitutionService().getInstitutionCount(institutionCode, asOfDate);
					valid = (count > 0);
				} else {
					InstitutionContract inst = HrServiceLocator.getInstitutionService().getInstitution(institutionCode, asOfDate);
					valid = (inst != null);
				}
			}
		}
		return valid;
	}
	
	// PmValidationUtils
	public static boolean validateCampus(String campusCode) {
		boolean valid = false;
		if (ValidationUtils.isWildCard(campusCode)) {
			valid = true;
		} else {
			Campus campusObj = LocationApiServiceLocator.getCampusService().getCampus(campusCode);
			valid = (campusObj != null);
		}
		return valid;
	}
	
	public static boolean isWildCard(String aString) {
		return (StringUtils.equals(aString, HrConstants.WILDCARD_CHARACTER) ||
					StringUtils.equals(aString, "*"));
	}
	
	public static boolean wildCardMatch(String string1, String string2) {
		if(ValidationUtils.isWildCard(string1) || ValidationUtils.isWildCard(string2))
			return true;
		
		return string1.equals(string2);
	}
	
	/**
	 * validates an open account exists matching the chart of accounts and account number.
	 * @param chartOfAccountsCode
	 * @param accountNumber
	 * @return
	 */
	public static boolean validateAccount(String chartOfAccountsCode, String accountNumber) {
		Map<String, String> fields = new HashMap<String, String>();
		fields.put("accountNumber", accountNumber);
		fields.put("chartOfAccountsCode", chartOfAccountsCode);
		Account account = (Account) KRADServiceLocator.getBusinessObjectService().findByPrimaryKey(Account.class, fields);
		if(account != null) {
			return !account.isClosed();
		}
		return false;
	}
	
	/**
	 * validates existence of an active sub account matching the supplied params.
	 * @param subAccountNumber
	 * @param accountNumber
	 * @param chartOfAccountsCode
	 * @return
	 */
	public static boolean validateSubAccount(String subAccountNumber, String accountNumber, String chartOfAccountsCode) {
		Map<String, String> fields = new HashMap<String, String>();
		fields.put("subAccountNumber", subAccountNumber);
		fields.put("accountNumber", accountNumber);
		fields.put("chartOfAccountsCode", chartOfAccountsCode);
		SubAccount subAccount = (SubAccount) KRADServiceLocator.getBusinessObjectService().findByPrimaryKey(SubAccount.class, fields);
		if(subAccount != null) {
			return subAccount.isActive();
		}
		return false;
	}
	
	/**
	 * validates an active object code exists matching the supplied params.
	 */
	public static boolean validateObjectCode(String financialObjectCode, String chartOfAccountsCode, Integer universityFiscalYear) {
		Map<String, String> fields = new HashMap<String, String>();

		fields.put("financialObjectCode", financialObjectCode);
		fields.put("chartOfAccountsCode", chartOfAccountsCode);
		if(universityFiscalYear != null) {
			fields.put("universityFiscalYear", universityFiscalYear.toString());
		}
		fields.put("active", "true");
		Collection<ObjectCode> objectCodes = KRADServiceLocator.getBusinessObjectService().findMatching(ObjectCode.class, fields);
		if(objectCodes != null && objectCodes.size() >0) {
			return true;
		}
		return false;
	}
	
	/**
	 * validates active sub object code existence matching the supplied params.
	 * 
	 * @param universityFiscalYear
	 * @param chartOfAccountsCode
	 * @param accountNumber
	 * @param financialObjectCode
	 * @param financialSubObjectCode
	 * @return
	 */
	public static boolean validateSubObjectCode(String universityFiscalYear,
												String chartOfAccountsCode,
												String accountNumber,
												String financialObjectCode,
												String financialSubObjectCode) {
		Map<String, String> fields = new HashMap<String, String>();
		fields.put("financialSubObjectCode", financialSubObjectCode);
		fields.put("chartOfAccountsCode", chartOfAccountsCode);
		fields.put("accountNumber", accountNumber);
		fields.put("financialObjectCode", financialObjectCode);
		if(universityFiscalYear != null) {
			fields.put("universityFiscalYear", universityFiscalYear.toString());
		}
		fields.put("active", "true");
		Collection<SubObjectCode> subObjectCodes = KRADServiceLocator.getBusinessObjectService().findMatching(SubObjectCode.class, fields);
		if(subObjectCodes != null && subObjectCodes.size() > 0) {
			return true;
		}
		return false;
	}
	/**
	 * validates an active organization matching organizationCode exists, whose chart of accounts code is chartOfAccountsCode
	 * 
	 * @param organizationCode
	 * @param chartOfAccountsCode
	 * @return
	 */
	public static boolean validateOrganization(String organizationCode, String chartOfAccountsCode) {
		Map<String, String> fields = new HashMap<String, String>();
		
		fields.put("organizationCode", organizationCode);
		fields.put("chartOfAccountsCode", chartOfAccountsCode);
		
		Organization org = (Organization) KRADServiceLocator.getBusinessObjectService().findByPrimaryKey(Organization.class, fields);
		if(org != null) {
			return org.isActive();
		}
		return false;
	}
	
	// KPME-2635
	/**
	 * validates location against location of salary group
	 * 
	 * @param salaryGroup
	 * @param location
	 * @param asOfDate
	 * @return
	 */
	public static boolean validateLocationWithSalaryGroup(String salaryGroup, String location, LocalDate asOfDate) {
		if (asOfDate != null) {
			SalaryGroupContract salGroup = HrServiceLocator.getSalaryGroupService().getSalaryGroup(salaryGroup, asOfDate);
			if (salGroup != null && StringUtils.isNotBlank(salGroup.getLocation())) {
				if (ValidationUtils.isWildCard(salGroup.getLocation())) {
					return true;
				} else {
					return StringUtils.equals(salGroup.getLocation(), location);	
				}
			}	
		}
		return false;
	}
}
