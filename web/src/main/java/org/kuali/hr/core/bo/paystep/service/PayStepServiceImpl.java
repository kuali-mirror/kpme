package org.kuali.hr.core.bo.paystep.service;

import java.util.List;

import org.kuali.hr.core.bo.paystep.PayStep;
import org.kuali.hr.core.bo.paystep.dao.PayStepDao;

public class PayStepServiceImpl implements PayStepService {

	private PayStepDao payStepDao;
	
	@Override
	public PayStep getPayStepById(String payStepId) {
		return payStepDao.getPayStepById(payStepId);
	}

	public PayStepDao getPayStepDao() {
		return payStepDao;
	}

	public void setPayStepDao(PayStepDao payStepDao) {
		this.payStepDao = payStepDao;
	}

	@Override
	public List<PayStep> getPaySteps(String payStep, String institution,
			String campus, String salaryGroup, String payGrade, String active) {
		// TODO Auto-generated method stub
		return payStepDao.getPaySteps(payStep,institution,campus,salaryGroup,payGrade,active);
	}

}
