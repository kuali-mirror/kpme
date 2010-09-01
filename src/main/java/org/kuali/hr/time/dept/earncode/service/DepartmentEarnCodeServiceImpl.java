package org.kuali.hr.time.dept.earncode.service;

import java.util.List;

import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.kuali.hr.time.dept.earncode.dao.DepartmentEarnCodeDao;

public class DepartmentEarnCodeServiceImpl implements DepartmentEarnCodeService {

    private DepartmentEarnCodeDao deptEarnCodeDao;

    public List<DepartmentEarnCode> getDepartmentEarnCodeList(String salGroup) {
        return deptEarnCodeDao.getDepartmentEarnCodeList(salGroup);
    }

	public void setDeptEarnCodeDao(DepartmentEarnCodeDao deptEarnCodeDao) {
		this.deptEarnCodeDao = deptEarnCodeDao;
	}


}
