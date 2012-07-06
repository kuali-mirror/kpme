package org.kuali.hr.time.util;

import java.sql.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.earncodesec.EarnCodeSecurity;
import org.kuali.hr.lm.leavecode.LeaveCode;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.location.Location;
import org.kuali.hr.paygrade.PayGrade;
import org.kuali.hr.time.accrual.TimeOffAccrual;
import org.kuali.hr.time.authorization.DepartmentalRule;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.earncodegroup.EarnCodeGroup;
import org.kuali.hr.time.earncodegroup.EarnCodeGroupDefinition;
import org.kuali.hr.time.paytype.PayType;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.salgroup.SalGroup;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.kfs.coa.businessobject.Chart;
import org.kuali.rice.kim.api.identity.Person;
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


	public static boolean validateSalGroup(String salGroup, Date asOfDate) {
		boolean valid = false;

		if (StringUtils.equals(salGroup, TkConstants.WILDCARD_CHARACTER)) {
			valid = true;
		} else if (asOfDate != null) {
			SalGroup sg = TkServiceLocator.getSalGroupService().getSalGroup(salGroup, asOfDate);
			valid = (sg != null);
		} else {
			int count = TkServiceLocator.getSalGroupService().getSalGroupCount(salGroup);
			valid = (count > 0);
		}

		return valid;
	}

	public static boolean validateEarnCode(String earnCode, Date asOfDate) {
		boolean valid = false;

		if (asOfDate != null) {
			EarnCode ec = TkServiceLocator.getEarnCodeService().getEarnCode(earnCode, asOfDate);
			valid = (ec != null);
		} else {
			int count = TkServiceLocator.getEarnCodeService().getEarnCodeCount(earnCode);
			valid = (count > 0);
		}

		return valid;
	}
	
	public static boolean validateLeaveCode(String leaveCode, Date asOfDate) {
		boolean valid = false;
		
		if (asOfDate != null) {
			LeaveCode lc = TkServiceLocator.getLeaveCodeService().getLeaveCode(leaveCode, asOfDate);
			valid = (lc != null);
		} else {
			Criteria crit = new Criteria();
			crit.addEqualTo("leaveCode", leaveCode);
			Query query = QueryFactory.newQuery(LeaveCode.class, crit);
			int count = PersistenceBrokerFactory.defaultPersistenceBroker().getCount(query);
			valid = (count > 0);
		}
		
		return valid;
	}
	
	public static boolean validateLeavePlan(String leavePlan, Date asOfDate) {
		boolean valid = false;
		
		if (asOfDate != null) {
			LeavePlan lp = TkServiceLocator.getLeavePlanService().getLeavePlan(leavePlan, asOfDate);
			valid = (lp != null);
		} else {
			// chen, moved the code that access db to service and dao
			valid = TkServiceLocator.getLeavePlanService().isValidLeavePlan(leavePlan);
		}
		
		return valid;
	}

	public static boolean validateLeaveCode(String leaveCode, String principalId, Date asOfDate) {
		boolean valid = false;
		
		if (asOfDate != null) {
			List<LeaveCode> leaveCodes = TkServiceLocator.getLeaveCodeService().getLeaveCodes(principalId, asOfDate);
			if(leaveCodes != null && !leaveCodes.isEmpty()) {
				for(LeaveCode leaveCodeObj : leaveCodes) {
					if(leaveCodeObj.getLeaveCode() != null) {
						if(StringUtils.equals(leaveCodeObj.getLeaveCode().trim(), leaveCode.trim())){
							valid = true;
							break;
						}
					}
				}
			}
//			valid = (leaveCodes != null);
		} else {
			Criteria crit = new Criteria();
			crit.addEqualTo("leaveCode", leaveCode);
			Query query = QueryFactory.newQuery(LeaveCode.class, crit);
			int count = PersistenceBrokerFactory.defaultPersistenceBroker().getCount(query);
			valid = (count > 0);
		}
		
		return valid;
	}
	
	public static boolean validateEarnCodeOfAccrualCategory(String earnCode, String accrualCategory, String principalId, Date asOfDate) {
		boolean valid = false;
		
		if (asOfDate != null) {
			if(validateAccCategory(accrualCategory, principalId, asOfDate)){
				List<EarnCode> earnCodes = TkServiceLocator.getEarnCodeService().getEarnCodes(principalId, asOfDate);
				if(earnCodes != null && !earnCodes.isEmpty()) {
					for(EarnCode earnCodeObj : earnCodes) {
						if(earnCodeObj.getEarnCode() != null) {
							if(StringUtils.equals(earnCodeObj.getEarnCode().trim(), earnCode.trim()) && StringUtils.equals(earnCodeObj.getAccrualCategory(), accrualCategory)){
								valid = true;
								break;
							}
						}
					}
				}
			}
//			valid = (leaveCodes != null);
		} else {
			Criteria crit = new Criteria();
			crit.addEqualTo("earnCode", earnCode);
			Query query = QueryFactory.newQuery(EarnCode.class, crit);
			int count = PersistenceBrokerFactory.defaultPersistenceBroker().getCount(query);
			valid = (count > 0);
		}
		
		return valid;
	}
	
	public static boolean validateAccCategory(String accrualCategory, Date asOfDate) {
		boolean valid = false;
		
		if (asOfDate != null) {
			AccrualCategory ac = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualCategory, asOfDate);
			valid = (ac != null);
		} else {
			Criteria crit = new Criteria();
			crit.addEqualTo("accrualCategory", accrualCategory);
			Query query = QueryFactory.newQuery(AccrualCategory.class, crit);
			int count = PersistenceBrokerFactory.defaultPersistenceBroker().getCount(query);
			valid = (count > 0);
		}
		
		return valid;
	}
	
	public static boolean validateAccCategory(String accrualCategory, String principalId, Date asOfDate) {
		boolean valid = false;
		
		if (asOfDate != null) {
			AccrualCategory ac = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualCategory, asOfDate);
			if(ac != null && ac.getLeavePlan() != null) {
				// fetch leave plan users
				if(principalId != null) {
					PrincipalHRAttributes principalHRAttributes = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, asOfDate);
					if(principalHRAttributes != null && principalHRAttributes.getLeavePlan() != null) {
						valid = StringUtils.equals(ac.getLeavePlan().trim(), principalHRAttributes.getLeavePlan().trim());
					}
				} else {
					valid = true;
				}
			} 
		} else {
			Criteria crit = new Criteria();
			crit.addEqualTo("accrualCategory", accrualCategory);
			Query query = QueryFactory.newQuery(AccrualCategory.class, crit);
			int count = PersistenceBrokerFactory.defaultPersistenceBroker().getCount(query);
			valid = (count > 0);
		}
		return valid;
	}
	
	public static boolean validateLocation(String location, Date asOfDate) {
		boolean valid = false;

		if (asOfDate != null) {
			Location l = TkServiceLocator.getLocationService().getLocation(location, asOfDate);
			valid = (l != null);
		} else {
			int count = TkServiceLocator.getLocationService().getLocationCount(location);
			valid = (count > 0);
		}

		return valid;
	}

	public static boolean validatePayType(String payType, Date asOfDate) {
		boolean valid = false;

		if (asOfDate != null) {
			PayType pt = TkServiceLocator.getPayTypeService().getPayType(payType, asOfDate);
			valid = (pt != null);
		} else {
			int count = TkServiceLocator.getPayTypeService().getPayTypeCount(payType);
			valid = (count > 0);
		}

		return valid;
	}


	public static boolean validatePayGrade(String payGrade, Date asOfDate) {
		boolean valid = false;

		if (asOfDate != null) {
			PayGrade pg = TkServiceLocator.getPayGradeService().getPayGrade(payGrade, asOfDate);
			valid = (pg != null);
		} else {
			int count = TkServiceLocator.getPayGradeService().getPayGradeCount(payGrade);
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
    public static boolean validateEarnCode(String earnCode, boolean otEarnCode, Date asOfDate) {
        boolean valid = false;

        if (asOfDate != null) {
            EarnCode ec = TkServiceLocator.getEarnCodeService().getEarnCode(earnCode, asOfDate);
            valid = (ec != null) && (otEarnCode ? ec.getOvtEarnCode().booleanValue() : true);
        }

        return valid;
    }

	/**
	 * Checks for row presence of a department, and optionally whether or not
	 * it is active as of the specified date.
	 */
	public static boolean validateDepartment(String department, Date asOfDate) {
		boolean valid = false;

        if (StringUtils.isEmpty(department)) {
          // do nothing, let false be returned.
        } else if (asOfDate != null) {
			Department d = TkServiceLocator.getDepartmentService().getDepartment(department, asOfDate);
		    valid = (d != null);
		} else {
			int count = TkServiceLocator.getDepartmentService().getDepartmentCount(department);
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
    public static boolean validateWorkArea(Long workArea, Date asOfDate) {
        return ValidationUtils.validateWorkArea(workArea, null, asOfDate);
    }

	public static boolean validateWorkArea(Long workArea, String dept, Date asOfDate) {
		boolean valid = false;

		if (workArea == null) {
			valid = false;
		} else if (workArea.equals(TkConstants.WILDCARD_LONG)) {
			valid = true;
		} else if (asOfDate != null) {
			WorkArea wa = TkServiceLocator.getWorkAreaService().getWorkArea(workArea, asOfDate);
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
	public static boolean validateAccrualCategory(String accrualCategory, Date asOfDate) {
		boolean valid = false;

		if (StringUtils.equals(accrualCategory, TkConstants.WILDCARD_CHARACTER)) {
			valid = true;
		} else if (asOfDate != null) {
			AccrualCategory ac = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualCategory, asOfDate);
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
			Person p = KimApiServiceLocator.getPersonService().getPerson(principalId);
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
    public static boolean validateTask(Long task, Date asOfDate) {
        boolean valid = false;

        if (task != null && asOfDate != null) {
            Task t = TkServiceLocator.getTaskService().getTask(task, asOfDate);
            valid = (t != null);
        } else if (task != null) {
        	int count = TkServiceLocator.getTaskService().getTaskCount(task);
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
    public static boolean validateEarnGroup(String earnGroup, Date asOfDate) {
        boolean valid = false;

        if (earnGroup != null && asOfDate != null) {
            EarnCodeGroup eg = TkServiceLocator.getEarnCodeGroupService().getEarnCodeGroup(earnGroup, asOfDate);
            valid = (eg != null);
        } else if (earnGroup != null) {
        	int count = TkServiceLocator.getEarnCodeGroupService().getEarnCodeGroupCount(earnGroup);
            valid = (count > 0);
        }

        return valid;
    }
    
    /**
     * @param earnGroup EarnCodeGroup
     * @param asOfDate
     * @return True if the EarnCodeGroup has overtime earn codes
     */
    public static boolean earnGroupHasOvertimeEarnCodes(String earnGroup, Date asOfDate) {
         if (earnGroup != null && asOfDate != null) {
             EarnCodeGroup eg = TkServiceLocator.getEarnCodeGroupService().getEarnCodeGroup(earnGroup, asOfDate);
             if(eg != null) {
            	for(EarnCodeGroupDefinition egd : eg.getEarnCodeGroups()) {
            		if(egd.getEarnCode() != null) {
            			EarnCode ec = TkServiceLocator.getEarnCodeService().getEarnCode(egd.getEarnCode(), asOfDate);
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
		boolean valid = false;
		Criteria crit = new Criteria();
        crit.addEqualTo("calendarName", calendarName);
        Query query = QueryFactory.newQuery(Calendar.class, crit);
        int count = PersistenceBrokerFactory.defaultPersistenceBroker().getCount(query);
        valid = (count > 0);
        return valid;
	}

   public static boolean duplicateDeptEarnCodeExists(EarnCodeSecurity deptEarnCode) {
	   boolean valid = false;
	   int count = TkServiceLocator.getEarnCodeSecurityService().getEarnCodeSecurityCount
               (deptEarnCode.getDept(), deptEarnCode.getHrSalGroup(), deptEarnCode.getEarnCode(), deptEarnCode.isEmployee() ? "1" : "0",
                       deptEarnCode.isApprover() ? "1" : "0", deptEarnCode.getLocation(), deptEarnCode.getActive() ? "Y" : "N", deptEarnCode.getEffectiveDate(), null);
       if(count == 1) {
    	   valid = true;
    	   count = TkServiceLocator.getEarnCodeSecurityService().getEarnCodeSecurityCount
                   (deptEarnCode.getDept(), deptEarnCode.getHrSalGroup(), deptEarnCode.getEarnCode(), deptEarnCode.isEmployee() ? "1" : "0",
                           deptEarnCode.isApprover() ? "1" : "0", deptEarnCode.getLocation(), deptEarnCode.getActive() ? "Y" : "N", deptEarnCode.getEffectiveDate(), deptEarnCode.getHrEarnCodeSecurityId());
    	   if(count == 1) {
    		   valid = false;
    	   }
       } else if(count > 1) {
    	   valid = true;
       }

	   return valid;
   }
   
   public static boolean duplicateTimeOffAccrual (TimeOffAccrual timeOffAccrual) {
	   boolean valid = false;
	   int count = TkServiceLocator.getTimeOffAccrualService().getTimeOffAccrualCount
               (timeOffAccrual.getAccrualCategory(), timeOffAccrual.getEffectiveDate(), timeOffAccrual.getPrincipalId(), null);
	   if(count == 1) {
    	   valid = true;
    	   count = TkServiceLocator.getTimeOffAccrualService().getTimeOffAccrualCount
                   (timeOffAccrual.getAccrualCategory(), timeOffAccrual.getEffectiveDate(), timeOffAccrual.getPrincipalId(), timeOffAccrual.getLmAccrualId());
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

   public static boolean validateOneYearFutureDate(Date date){
	   java.util.Calendar startDate = java.util.Calendar.getInstance();
	   startDate.add(java.util.Calendar.DATE, -1);
	   startDate.set(java.util.Calendar.SECOND, 0);
	   startDate.set(java.util.Calendar.MINUTE, 0);
	   startDate.set(java.util.Calendar.HOUR_OF_DAY, 0);
	   java.util.Calendar endDate = java.util.Calendar.getInstance();
	   endDate.add(java.util.Calendar.YEAR, 1); // One year after the current date
	   return date.compareTo(startDate.getTime()) * date.compareTo(endDate.getTime()) <= 0;
   }
   
   /**
    * Checks for date not more than one year in the future and does not consider past date
    * 
    */

   public static boolean validateOneYearFutureEffectiveDate(Date date){
	   java.util.Calendar startDate = java.util.Calendar.getInstance();
	   startDate.set(java.util.Calendar.MILLISECOND, 0);
	   startDate.set(java.util.Calendar.SECOND, 0);
	   startDate.set(java.util.Calendar.MINUTE, 0);
	   startDate.set(java.util.Calendar.HOUR_OF_DAY, 0);
	   startDate.add(java.util.Calendar.YEAR, 1); // One year after the current date
	   return date.compareTo(startDate.getTime()) <= 0;
   }
   
   /**
    * Checks for date in the future
    * 
    */
   
   public static boolean validateFutureDate(Date date){
	   java.util.Calendar startDate = java.util.Calendar.getInstance();
	   startDate.add(java.util.Calendar.DATE, 0);
	   startDate.set(java.util.Calendar.SECOND, 0);
	   startDate.set(java.util.Calendar.MINUTE, 0);
	   startDate.set(java.util.Calendar.HOUR_OF_DAY, 0);
	   return date.compareTo(startDate.getTime()) > 0;
   }

	/**
	 * Checks for row presence of a pay calendar by calendar type
	 */
	public static boolean validateCalendarByType(String calendarName, String calendarType) {
		boolean valid = false;
		Criteria crit = new Criteria();
		crit.addEqualTo("calendarName", calendarName);
		if(StringUtils.equalsIgnoreCase(calendarType, "Pay")){
			crit.addNotEqualTo("calendarTypes", "Leave");	
		}else if(StringUtils.equalsIgnoreCase(calendarType, "Leave")){
			crit.addNotEqualTo("calendarTypes", "Pay");
		}
		Query query = QueryFactory.newQuery(Calendar.class, crit);
		int count = PersistenceBrokerFactory.defaultPersistenceBroker().getCount(query);
		valid = (count > 0);
		return valid;
	}
	
	public static boolean validateRecordMethod(String recordMethod, String accrualCategory, Date asOfDate) {
		boolean valid = false;
		if (asOfDate != null) {
			AccrualCategory ac = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualCategory, asOfDate);
			valid = ac!=null & ac.getUnitOfTime()!=null & StringUtils.equalsIgnoreCase(ac.getUnitOfTime(), recordMethod);
		}
		return valid;
	}
}
