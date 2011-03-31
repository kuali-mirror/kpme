package org.kuali.hr.time.department.service;

import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.department.dao.DepartmentDao;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;

import java.sql.Date;

public class DepartmentServiceImpl implements DepartmentService {

	private DepartmentDao departmentDao;

	@Override
	public Department getDepartment(String department, Date asOfDate) {
        Department d = departmentDao.getDepartment(department, asOfDate);
        populateDepartmentRoles(d);

		return d;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

    @Override
    public void populateDepartmentRoles(Department department) {
        if (department != null) {
            department.setRoles(
                    TkServiceLocator.getTkRoleService().getDepartmentRoles(
                            department.getDept(),
                            TkConstants.ROLE_TK_ORG_ADMIN,
                            department.getEffectiveDate()
                    )
            );
        }
    }
}
