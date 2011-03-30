package org.kuali.hr.time.department.service;

import org.kuali.hr.time.department.Department;

import java.sql.Date;

public interface DepartmentService {
	/**
	 * Get Department as of a particular date passed in
	 * @param department
	 * @param asOfDate
	 * @return
	 */
	public Department getDepartment(String department, Date asOfDate);

    /**
     * A helper method to populate the roles for the given department. This
     * method will be called automatically when calls to getDepartment() are
     * made. Functionality is exposed here to allow the Kuali Lookup / Maint
     * pages to completely populate Department objects.
     *
     * @param department The department for which we need roles populated.
     */
    public void populateDepartmentRoles(Department department);
}
