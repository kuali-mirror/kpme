package org.kuali.hr.time.department.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.department.Department;

public interface DepartmentService {
	/**
	 * Get Department as of a particular date passed in
	 * @param department
	 * @param asOfDate
	 * @return
	 */
	public Department getDepartment(String department, Date asOfDate);

    /**
     * Fetches a list of Department objects as of the specified date all of which
     * belong to the indicated chart.
     *
     * @param chart The search criteria
     * @param asOfDate Effective date
     * @return A List<Department> object.
     */
    public List<Department> getDepartments(String chart, Date asOfDate);

    /**
     * A helper method to populate the roles for the given department. This
     * method will be called automatically when calls to getDepartment() are
     * made. Functionality is exposed here to allow the Kuali Lookup / Maint
     * pages to completely populate Department objects.
     *
     * @param department The department for which we need roles populated.
     */
    public void populateDepartmentRoles(Department department);
    
    /**
     * Fetch department by id
     * @param hrDeptId
     * @return
     */
    public Department getDepartment(String hrDeptId);
    /**
     * Fetch department by location
     * @param location
     * @return
     */
    public List<Department> getDepartmentByLocation(String location);
}
