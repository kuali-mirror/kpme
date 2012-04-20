package org.kuali.hr.time.paytype.service;

import java.util.Date;
import java.util.List;

import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.paytype.PayType;
import org.kuali.hr.time.paytype.dao.PayTypeDao;
import org.kuali.hr.time.util.TkConstants;

public class PayTypeServiceImpl implements PayTypeService {

	private PayTypeDao payTypeDao;

	@Override
	public void saveOrUpdate(PayType payType) {
		payTypeDao.saveOrUpdate(payType);
	}

	@Override
	public void saveOrUpdate(List<PayType> payTypeList) {
		payTypeDao.saveOrUpdate(payTypeList);
	}

	public void setPayTypeDao(PayTypeDao payTypeDao) {
		this.payTypeDao = payTypeDao;
	}

	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public PayType getPayType(String payType, Date effectiveDate) {
		return payTypeDao.getPayType(payType, effectiveDate);
	}

	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public PayType getPayType(String hrPayTypeId) {
		return payTypeDao.getPayType(hrPayTypeId);
	}
	
	@Override
	public int getPayTypeCount(String payType) {
		return payTypeDao.getPayTypeCount(payType);
	}

}
