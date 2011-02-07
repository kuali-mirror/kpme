package org.kuali.hr.time.earncode.service;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import org.kuali.hr.job.Job;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.earncode.dao.EarnCodeDao;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;

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
		if (job == null || job.getPayTypeObj() == null)
			throw new RuntimeException("Null job/job paytype on assignment!");
		
		EarnCode regularEc = getEarnCode(job.getPayTypeObj().getRegEarnCode(), job.getEffectiveDate());
		if (regularEc == null) 
			throw new RuntimeException("No regular earn code defined.");
		earnCodes.add(regularEc);
		//TODO - Kenneth change from current date to document begin date for ALL effective date fetches 
		List<DepartmentEarnCode> decs = TkServiceLocator.getDepartmentEarnCodeService().getDepartmentEarnCodes(job.getDept(), job.getTkSalGroup(), TKUtils.getCurrentDate());
		for (DepartmentEarnCode dec : decs) {
			// Iterating over these one by one, running a query because each earn code has effective dating/time stamp/active
			EarnCode ec = getEarnCode(dec.getEarnCode(), dec.getEffectiveDate());
			earnCodes.add(ec);
		}
		
		return earnCodes;
	}
	
	public EarnCode getEarnCode(String earnCode, Date asOfDate) {
		EarnCode ec = null;
		
		ec = earnCodeDao.getEarnCode(earnCode, asOfDate);
		
		return ec;
	}
	
	public EarnCode getExactEarnCode(String earnCode, Date asOfDate) {
		return earnCodeDao.getExactEarnCode(earnCode, asOfDate);
	}
	
}
