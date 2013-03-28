package org.kuali.hr.pm.salarygroup.service;

import org.kuali.hr.pm.salarygroup.SalaryGroup;
import org.kuali.hr.pm.salarygroup.dao.SalaryGroupDao;

public class SalaryGroupServiceImpl implements SalaryGroupService {
	private SalaryGroupDao salaryGroupDao;
	
	@Override
	public SalaryGroup getSalaryGroupById(String pmSalaryGroupId) {
		return salaryGroupDao.getSalaryGroupById(pmSalaryGroupId);
	}

	public SalaryGroupDao getSalaryGroupDao() {
		return salaryGroupDao;
	}

	public void setSalaryGroupDao(SalaryGroupDao salaryGroupDao) {
		this.salaryGroupDao = salaryGroupDao;
	}
	

}
