package org.kuali.hr.pm.paystep.service;

import org.kuali.hr.pm.paystep.PayStep;
import org.kuali.hr.pm.paystep.dao.PayStepDao;

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

}
