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
package org.kuali.hr.core.department.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.hr.core.department.Department;
import org.springframework.cache.annotation.Cacheable;

public interface DepartmentService {
	
    /**
     * Fetch department by id
     * @param hrDeptId
     * @return
     */
    @Cacheable(value= Department.CACHE_NAME, key="'hrDeptId=' + #p0")
    Department getDepartment(String hrDeptId);
    
    List<Department> getDepartments(String department, String location, String descr, String active);
    
    /**
	 * get count of department with given department
	 * @param department
	 * @return int
	 */
	int getDepartmentCount(String department);
	
	/**
	 * Get Department as of a particular date passed in
	 * @param department
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= Department.CACHE_NAME, key="'department=' + #p0 + '|' + 'asOfDate=' + #p1")
	Department getDepartment(String department, LocalDate asOfDate);

    /**
     * Fetches a list of Department objects as of the specified date all of which
     * belong to the indicated location.
     *
     * @param location The search criteria
     * @param asOfDate Effective date
     * @return A List<Department> object.
     */
    @Cacheable(value= Department.CACHE_NAME, key="'chart=' + #p0 + '|' + 'asOfDate=' + #p1")
    List<Department> getDepartments(String location, LocalDate asOfDate);

}