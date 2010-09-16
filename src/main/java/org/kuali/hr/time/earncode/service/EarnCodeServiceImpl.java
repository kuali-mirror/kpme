package org.kuali.hr.time.earncode.service;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import org.kuali.hr.job.Job;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.earncode.dao.EarnCodeDao;
import org.kuali.hr.time.service.base.TkServiceLocator;

public class EarnCodeServiceImpl implements EarnCodeService {

	private EarnCodeDao earnCodeDao;

	public void setEarnCodeDao(EarnCodeDao earnCodeDao) {
		this.earnCodeDao = earnCodeDao;
	}

	@Override
	public List<EarnCode> getEarnCodes(Assignment a) {
		List<EarnCode> earnCodes = new LinkedList<EarnCode>();
		
		if (a == null) 
			throw new RuntimeException("Can not get earn codes for null assignment");
		Job job = a.getJob();
		if (job == null)
			throw new RuntimeException("Null job on assignment!");
		earnCodes.add(getEarnCode(job.getPayType().getRegEarnCode(), job.getEffectiveDate())); 
		earnCodes.addAll(TkServiceLocator.getDepartmentEarnCodeService().getDepartmentEarnCodes(job.getDept(), job.getTkSalGroup(), job.getEffectiveDate()));
		
		return earnCodes;
	}
	
	public EarnCode getEarnCode(String earnCode, Date asOfDate) {
		EarnCode ec = null;
		
		earnCodeDao.getEarnCode(earnCode, asOfDate);
		
		return ec;
	}
	
}
