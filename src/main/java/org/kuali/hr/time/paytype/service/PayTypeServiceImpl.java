package org.kuali.hr.time.paytype.service;

import java.util.Date;
import java.util.List;

import org.kuali.hr.time.paytype.PayType;
import org.kuali.hr.time.paytype.dao.PayTypeDao;

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
	public PayType getPayType(String payType, Date effectiveDate) {
		return payTypeDao.getPayType(payType, effectiveDate);
	}

}
