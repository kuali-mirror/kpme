package org.kuali.hr.paygrade.service;

import java.sql.Date;

import org.kuali.hr.paygrade.PayGrade;
import org.kuali.hr.paygrade.dao.PayGradeDao;
import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.util.TkConstants;

public class PayGradeServiceImpl implements PayGradeService{

	private PayGradeDao payGradeDao;
	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public PayGrade getPayGrade(String payGrade, Date asOfDate) {
		return payGradeDao.getPayGrade(payGrade, asOfDate);
	}
 
	public void setPayGradeDao(PayGradeDao payGradeDao) {
		this.payGradeDao = payGradeDao;
	}

	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public PayGrade getPayGrade(String hrPayGradeId) {
		return payGradeDao.getPayGrade(hrPayGradeId);
	}
	@Override
	public int getPayGradeCount(String payGrade) {
		return payGradeDao.getPayGradeCount(payGrade);
	}
}
