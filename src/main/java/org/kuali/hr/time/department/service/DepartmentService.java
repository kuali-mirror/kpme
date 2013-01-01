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
package org.kuali.hr.time.department.service;

import org.kuali.hr.time.department.Department;
import org.springframework.cache.annotation.Cacheable;

import java.sql.Date;
import java.util.List;

public interface DepartmentService {
	/**
	 * Get Department as of a particular date passed in
	 * @param department
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= Department.CACHE_NAME, key="'department=' + #p0 + '|' + 'asOfDate=' + #p1")
	public Department getDepartment(String department, Date asOfDate);

    /**
     * Fetches a list of Department objects as of the specified date all of which
     * belong to the indicated chart.
     *
     * @param chart The search criteria
     * @param asOfDate Effective date
     * @return A List<Department> object.
     */
    @Cacheable(value= Department.CACHE_NAME, key="'chart=' + #p0 + '|' + 'asOfDate=' + #p1")
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
    @Cacheable(value= Department.CACHE_NAME, key="'hrDeptId=' + #p0")
    public Department getDepartment(String hrDeptId);
    /**
     * Fetch department by location
     * @param location
     * @return
     */
    @Cacheable(value= Department.CACHE_NAME, key="'location=' + #p0")
    public List<Department> getDepartmentByLocation(String location);
    /**
	 * get count of department with given department
	 * @param department
	 * @return int
	 */
	public int getDepartmentCount(String department);

    List<Department> getDepartments(String department, String location, String descr, String active);
}
