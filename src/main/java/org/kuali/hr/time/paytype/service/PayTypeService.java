package org.kuali.hr.time.paytype.service;

import java.util.List;

import org.kuali.hr.time.paytype.PayType;

public interface PayTypeService {

	public void saveOrUpdate(PayType payType);
	public void saveOrUpdate(List<PayType> payTypeList);
	public PayType getPayType(Long payTypeId);
}
