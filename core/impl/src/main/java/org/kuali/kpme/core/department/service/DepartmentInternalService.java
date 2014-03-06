package org.kuali.kpme.core.department.service;


import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.department.DepartmentBo;

import java.util.List;

public interface DepartmentInternalService {
    DepartmentBo getDepartmentWithRoleData(String hrDeptId);
    List<DepartmentBo> getDepartmentsWithRoleData(String location, LocalDate asOfDate);
    DepartmentBo getDepartmentWithRoleData(String department, String location, LocalDate asOfDate);
    DepartmentBo getDepartmentWithRoleData(String department, LocalDate asOfDate);
}
