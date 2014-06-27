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
package org.kuali.kpme.core.api.department;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.department.DepartmentContract;
import org.springframework.cache.annotation.Cacheable;

public interface DepartmentService {
	
    /**
     * Fetch department by id
     * @param hrDeptId
     * @return
     */
    @Cacheable(value=DepartmentContract.CACHE_NAME, key="'hrDeptId=' + #p0")
    Department getDepartment(String hrDeptId);
    

    /**
	 * get count of department with given department
	 * @param department
	 * @param groupKeyCode
	 * @return int
	 */
	int getDepartmentCount(String department, String groupKeyCode);

    /**
     * Fetch department by dept, groupKeyCode and asOfDate without sub kim role member data
     * @param department
     * @param groupKeyCode
     * @param asOfDate
     * @return Department
     */
    @Cacheable(value=DepartmentContract.CACHE_NAME, key="'{getDepartment}' + 'department=' + #p0 + '|' + 'groupKeyCode=' + #p1 + '|' + 'asOfDate=' + #p2")
    Department getDepartment(String department, String groupKeyCode, LocalDate asOfDate);

    /**
     * Fetches a list of Department objects as of the specified date all of which
     * belong to the indicated location.
     *
     * @param location The search criteria
     * @param asOfDate Effective date
     * @return A List<Department> object.
     */
    @Cacheable(value=DepartmentContract.CACHE_NAME, key="'location=' + #p0 + '|' + 'asOfDate=' + #p1")
    List<Department> getDepartmentsWithLocation(String location, LocalDate asOfDate);

    /**
     * Fetches a list of Department objects as of the specified date all of which
     * belong to the indicated location.
     *
     * @param location The search criteria
     * @param asOfDate Effective date
     * @return A List<Department> object.
     */
    @Cacheable(value=DepartmentContract.CACHE_NAME, key="'groupKeyCode=' + #p0 + '|' + 'asOfDate=' + #p1")
    List<Department> getDepartmentsWithGroupKey(String groupKeyCode, LocalDate asOfDate);


    /**
     * Fetches a list of departments as of the specified date all of which
     * belong to the indicated location.
     *
     * @param location The search criteria
     * @param asOfDate Effective date
     * @return A List<String> object.
     */
    @Cacheable(value=DepartmentContract.CACHE_NAME, key="'{getDepartmentsForLocation}' + 'location=' + #p0 + '|' + 'asOfDate=' + #p1")
    List<String> getDepartmentValuesWithLocation(String location, LocalDate asOfDate);

    /**
     * Fetches a list of departments as of the specified date all of which
     * belong to the indicated locations.
     *
     * @param locations The search criteria
     * @param asOfDate Effective date
     * @return A List<String> object.
     */
    @Cacheable(value=DepartmentContract.CACHE_NAME, key="'{getDepartmentsForLocations}' + 'location=' + T(org.kuali.rice.core.api.cache.CacheKeyUtils).key(#p0) + '|' + 'asOfDate=' + #p1")
    List<String> getDepartmentValuesWithLocations(List<String> locations, LocalDate asOfDate);
    
    /**
     * Fetch a list of Department objects with given department and groupKeyCode
     * 
     * @param department
     * @param groupKeyCode
     * @return A List<Department> object
     */
    //@Cacheable(value=DepartmentContract.CACHE_NAME, key="'{getDepartments}' + 'department=' + #p0 + '|' + 'groupKeyCode=' + #p1")
    //List<Department> getDepartments(String department, String groupKeyCode);
    
    /**
     * Fetch a list of Department objects as of the specified date all of which
     * match the indicated department and location.
     * 
     * @param department
     * @param location
     * @param asOfDate
     * @return A List<Department> object
     */
    @Cacheable(value=DepartmentContract.CACHE_NAME, key="'{getDepartments}' + 'department=' + #p0 + '|' + 'location=' + #p1 + '|' + 'asOfDate=' + #p2")
    List<Department> getDepartments(String department, String location, LocalDate asOfDate);


    /**
     * Fetch a list of Department objects as of the specified date all of which
     * match the indicated department and location.
     *
     * @param department
     * @param location
     * @param asOfDate
     * @return A List<Department> object
     */
    @Cacheable(value=DepartmentContract.CACHE_NAME, key="'{getDepartments}' + 'asOfDate=' + #p0")
    List<Department> getDepartments(LocalDate asOfDate);
}