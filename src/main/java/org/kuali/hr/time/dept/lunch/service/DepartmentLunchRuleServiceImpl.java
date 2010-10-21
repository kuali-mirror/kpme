package org.kuali.hr.time.dept.lunch.service;

import java.sql.Date;

import org.kuali.hr.time.dept.lunch.DeptLunchRule;
import org.kuali.hr.time.dept.lunch.dao.DepartmentLunchRuleDao;

public class DepartmentLunchRuleServiceImpl implements DepartmentLunchRuleService {
	public DepartmentLunchRuleDao deptLunchRuleDao;
	
	
	@Override
	public DeptLunchRule getDepartmentLunchRule(String dept, Long workArea,
			String principalId, Long jobNumber, Date asOfDate) {
		DeptLunchRule deptLunchRule = deptLunchRuleDao.getDepartmentLunchRule(dept, workArea, principalId, jobNumber, asOfDate);
		if(deptLunchRule!=null){
			return deptLunchRule;
		}
		deptLunchRule = deptLunchRuleDao.getDepartmentLunchRule(dept, workArea, principalId, -1L, asOfDate);
		if(deptLunchRule!=null){
			return deptLunchRule;
		}
		deptLunchRule = deptLunchRuleDao.getDepartmentLunchRule(dept, workArea, "*", -1L, asOfDate);
		
		if(deptLunchRule!=null){
			return deptLunchRule;
		}
		deptLunchRule = deptLunchRuleDao.getDepartmentLunchRule(dept, -1L, "*", -1L, asOfDate);
		
		if(deptLunchRule!=null){
			return deptLunchRule;
		}
		deptLunchRule = deptLunchRuleDao.getDepartmentLunchRule(dept, -1L, principalId, -1L, asOfDate);
		return deptLunchRule;
	}


	public DepartmentLunchRuleDao getDeptLunchRuleDao() {
		return deptLunchRuleDao;
	}


	public void setDeptLunchRuleDao(DepartmentLunchRuleDao deptLunchRuleDao) {
		this.deptLunchRuleDao = deptLunchRuleDao;
	}

}
