package org.kuali.hr.time.paytype.dao;

import java.util.List;

import org.kuali.hr.time.paytype.PayType;

public interface PayTypeDao {

	public void saveOrUpdate(PayType payType);

	public void saveOrUpdate(List<PayType> payTypeList);

	public PayType getPayType(Long payTypeId);

}
