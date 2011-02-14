package org.kuali.hr.paygrade.service;

import java.sql.Date;

import org.kuali.hr.paygrade.PayGrade;
import org.kuali.hr.paygrade.dao.PayGradeDao;

public class PayGradeServiceImpl implements PayGradeService{

	private PayGradeDao payGradeDao;
	@Override
	public PayGrade getPayGrade(String payGrade, Date asOfDate) {
		// TODO Auto-generated method stub
		return payGradeDao.getPayGrade(payGrade, asOfDate);
	}
 
	public void setPayGradeDao(PayGradeDao payGradeDao) {
		this.payGradeDao = payGradeDao;
	}
	
}
