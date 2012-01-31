package org.kuali.hr.time.department.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.department.dao.DepartmentDao;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;

public class DepartmentServiceImpl implements DepartmentService {

	private DepartmentDao departmentDao;

    @Override
    @CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
    public List<Department> getDepartments(String chart, Date asOfDate) {
        List<Department> ds = departmentDao.getDepartments(chart, asOfDate);

        for (Department d : ds) {
            populateDepartmentRoles(d);
        }

        return ds;
    }

    @Override
    @CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
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
        	List<TkRole> deptAdminRoles = TkServiceLocator.getTkRoleService().getDepartmentRoles(
                    department.getDept(),
                    TkConstants.ROLE_TK_DEPT_ADMIN,
                    department.getEffectiveDate()); 
        	List<TkRole> deptViewOnlyRoles = TkServiceLocator.getTkRoleService().getDepartmentRoles(department.getDept(),
                    TkConstants.ROLE_TK_DEPT_VO,
                    department.getEffectiveDate());
        	List<TkRole> deptAdminInactiveRoles = TkServiceLocator.getTkRoleService().getDepartmentInactiveRoles(
                    department.getDept(),
                    TkConstants.ROLE_TK_DEPT_ADMIN,
                    department.getEffectiveDate()); 
        	List<TkRole> deptViewOnlyInactiveRoles = TkServiceLocator.getTkRoleService().getDepartmentInactiveRoles(department.getDept(),
                    TkConstants.ROLE_TK_DEPT_VO,
                    department.getEffectiveDate());
        	
        	department.getRoles().addAll(deptAdminRoles);
        	department.getRoles().addAll(deptViewOnlyRoles);
        	department.getInactiveRoles().addAll(deptAdminInactiveRoles);
        	department.getInactiveRoles().addAll(deptViewOnlyInactiveRoles);
        }
    }

	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public Department getDepartment(String hrDeptId) {
		return departmentDao.getDepartment(hrDeptId);
	}
	
	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public List<Department> getDepartmentByLocation(String location) {
		return departmentDao.getDepartmentByLocation(location);
	}
}
