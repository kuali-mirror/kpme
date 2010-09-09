package org.kuali.hr.time.paytype.service;

import java.util.List;

import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paycalendar.service.PayCalendarService;
import org.kuali.hr.time.paytype.PayType;
import org.kuali.hr.time.paytype.dao.PayTypeDao;

public class PayTypeServiceImpl implements PayTypeService {

	private PayCalendarService payCalendarService;
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
	public PayType getPayType(Long payTypeId) {
		PayType payType = null;
		payType = payTypeDao.getPayType(payTypeId);
		if (payType != null) {
			PayCalendar payCalendar = payCalendarService.getPayCalendarByGroup(payType.getCalendarGroup());
			payType.setPayCalendar(payCalendar);
		}
		return payType;
	}

	public void setPayCalendarService(PayCalendarService payCalendarService) {
		this.payCalendarService = payCalendarService;
	}

}
