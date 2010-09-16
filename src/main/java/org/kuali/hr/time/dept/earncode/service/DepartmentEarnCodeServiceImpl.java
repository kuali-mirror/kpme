package org.kuali.hr.time.dept.earncode.service;

import java.util.List;

import org.kuali.hr.time.dept.earncode.dao.DepartmentEarnCodeDao;
import org.kuali.hr.time.earncode.EarnCode;

public class DepartmentEarnCodeServiceImpl implements DepartmentEarnCodeService {

    private DepartmentEarnCodeDao deptEarnCodeDao;

	public void setDeptEarnCodeDao(DepartmentEarnCodeDao deptEarnCodeDao) {
		this.deptEarnCodeDao = deptEarnCodeDao;
	}

	@Override
	public List<EarnCode> getDepartmentEarnCodes(String department, String tkSalGroup, java.util.Date asOfDate) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	/*
	 * 
	 * Ignoing Boolean bits for now

  List<earncodes> getEarnCode(Assignment assignment) {
  
   // assume Assignments have Jobs
   Job = assignment.getjob()
   Attach this to the front of the list: earnCodeService.getEarnCode(job.getPayType.getRegularNamlkajdsl)
  	getDepartmentEarnCodes(job.getdept, job.gettksalgroup) attached to bottom of list
  }



list<earncodes> getDepartmentEarncodes(String dept, String tk_sal_group, effdt) {
cached 
wildcards
4 queries, rolling into list once one has values
}



	 *
	 *
	 */
}
