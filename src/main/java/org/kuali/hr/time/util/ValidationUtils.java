package org.kuali.hr.time.util;

import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.codehaus.plexus.util.StringUtils;
import org.kuali.hr.time.accrual.AccrualCategory;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.earngroup.EarnGroup;
import org.kuali.hr.time.salgroup.SalGroup;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;

import java.sql.Date;

/**
 * A few methods to assist with various validation tasks.
 */
public class ValidationUtils {

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
			Criteria crit = new Criteria();
			crit.addEqualTo("dept", salGroup);
			Query query = QueryFactory.newQuery(SalGroup.class, crit);
			int count = PersistenceBrokerFactory.defaultPersistenceBroker().getCount(query);
			valid = (count > 0);
		}

		return valid;
	}

	public static boolean validateExactEarnCode(String earnCode, Date asOfDate) {
		boolean valid = false;

		if (asOfDate != null)
			valid = (TkServiceLocator.getEarnCodeService().getExactEarnCode(earnCode, asOfDate) != null);

		return valid;
	}

	public static boolean validateEarnCode(String earnCode, Date asOfDate) {
		boolean valid = false;

		if (asOfDate != null) {
			EarnCode ec = TkServiceLocator.getEarnCodeService().getEarnCode(earnCode, asOfDate);
			valid = (ec != null);
		} else {
			Criteria crit = new Criteria();
			crit.addEqualTo("earnCode", earnCode);
			Query query = QueryFactory.newQuery(EarnCode.class, crit);
			int count = PersistenceBrokerFactory.defaultPersistenceBroker().getCount(query);
			valid = (count > 0);
		}

		return valid;
	}

	/**
	 * Checks for row presence of a department, and optionally whether or not
	 * it is active as of the specified date.
	 */
	public static boolean validateDepartment(String department, Date asOfDate) {
		boolean valid = false;

		if (asOfDate != null) {
			Department d = TkServiceLocator.getDepartmentService().getDepartment(department, asOfDate);
		    valid = (d != null);
		} else {
			Criteria crit = new Criteria();
			crit.addEqualTo("dept", department);
			Query query = QueryFactory.newQuery(Department.class, crit);
			int count = PersistenceBrokerFactory.defaultPersistenceBroker().getCount(query);
			valid = (count > 0);
		}

		return valid;
	}

	/**
	 * Checks for row presence of a work area, and optionally whether or not
	 * it is active as of the specified date.
	 */
	public static boolean validateWorkArea(Long workArea, Date asOfDate) {
		boolean valid = false;

		if (workArea == null) {
			valid = false;
		} else if (workArea.equals(TkConstants.WILDCARD_LONG)) {
			valid = true;
		} else if (asOfDate != null) {
			WorkArea wa = TkServiceLocator.getWorkAreaService().getWorkArea(workArea, asOfDate);
			valid = (wa != null);
		} else {
			Criteria crit = new Criteria();
			crit.addEqualTo("workArea", workArea);
			Query query = QueryFactory.newQuery(WorkArea.class, crit);
			int count = PersistenceBrokerFactory.defaultPersistenceBroker().getCount(query);
			valid = (count > 0);
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
			Person p = KIMServiceLocator.getPersonService().getPersonByPrincipalName(principalId);
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
            Criteria crit = new Criteria();
            crit.addEqualTo("task", task);
            Query query = QueryFactory.newQuery(Task.class, crit);
            int count = PersistenceBrokerFactory.defaultPersistenceBroker().getCount(query);
            valid = (count > 0);
        }

        return valid;
    }
    
    /**
     * No wildcarding is accounted for in this method.
     * @param earnGroup EarnGroup
     * @param asOfDate Can be null, if we just want to look for the general case.
     * @return True if the EarnGroup is present / valid.
     */
    public static boolean validateEarnGroup(String earnGroup, Date asOfDate) {
        boolean valid = false;

        if (earnGroup != null && asOfDate != null) {
            EarnGroup eg = TkServiceLocator.getEarnGroupService().getEarnGroup(earnGroup, asOfDate);
            valid = (eg != null);
        } else if (earnGroup != null) {
            Criteria crit = new Criteria();
            crit.addEqualTo("earnGroup", earnGroup);
            Query query = QueryFactory.newQuery(EarnGroup.class, crit);
            int count = PersistenceBrokerFactory.defaultPersistenceBroker().getCount(query);
            valid = (count > 0);
        }

        return valid;
    }
}
