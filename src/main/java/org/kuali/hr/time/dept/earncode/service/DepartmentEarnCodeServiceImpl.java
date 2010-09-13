package org.kuali.hr.time.dept.earncode.service;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.kuali.hr.time.dept.earncode.dao.DepartmentEarnCodeDao;

public class DepartmentEarnCodeServiceImpl implements DepartmentEarnCodeService {

    private DepartmentEarnCodeDao deptEarnCodeDao;

    public List<DepartmentEarnCode> getDepartmentEarnCodeList(Long salGroup) {
        return deptEarnCodeDao.getDepartmentEarnCodeList(salGroup);
    }

	public void setDeptEarnCodeDao(DepartmentEarnCodeDao deptEarnCodeDao) {
		this.deptEarnCodeDao = deptEarnCodeDao;
	}

	@Override
	public List<DepartmentEarnCode> getDepartmentEarnCodes(Long salGroupId, Date payPeriodEndDate) {
		List<DepartmentEarnCode> list = new LinkedList<DepartmentEarnCode>();

		
		
		return list;
	}


}
