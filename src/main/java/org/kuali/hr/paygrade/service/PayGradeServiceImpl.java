package org.kuali.hr.paygrade.service;

import org.kuali.hr.paygrade.PayGrade;
import org.kuali.hr.paygrade.dao.PayGradeDao;

import java.sql.Date;

public class PayGradeServiceImpl implements PayGradeService{

	private PayGradeDao payGradeDao;
	@Override
	public PayGrade getPayGrade(String payGrade, Date asOfDate) {
		return payGradeDao.getPayGrade(payGrade, asOfDate);
	}
 
	public void setPayGradeDao(PayGradeDao payGradeDao) {
		this.payGradeDao = payGradeDao;
	}

	@Override
	public PayGrade getPayGrade(String hrPayGradeId) {
		return payGradeDao.getPayGrade(hrPayGradeId);
	}
	@Override
	public int getPayGradeCount(String payGrade) {
		return payGradeDao.getPayGradeCount(payGrade);
	}
}
