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
package org.kuali.kpme.core.department.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.department.DepartmentBo;

public interface DepartmentDao {
	public void saveOrUpdate(DepartmentBo dept);
	public DepartmentBo getDepartment(String department, String groupKeyCode, LocalDate asOfDate);
    public List<DepartmentBo> getDepartments(LocalDate asOfDate);
    public DepartmentBo getDepartment(String hrDeptId);
    public int getDepartmentCount(String department, String groupKeyCode);
    public List<DepartmentBo> getDepartments(String groupKeyCode, String department, LocalDate asOfDate);
    //public List<DepartmentBo> getDepartments(String department, String groupKeyCode);
    public List<DepartmentBo> getDepartmentsWithGroupKeys(List<String> groupKeyCodes, LocalDate asOfDate);

    List<DepartmentBo> getDepartmentsWithDepartmentAndGroupKeys(String department, List<String> groupKeyCodes, LocalDate asOfDate);
    // This is not used anywhere
    //List<DepartmentBo> getDepartments(String department, String location, String descr, String active, String showHistory, String payrollApproval);
}
