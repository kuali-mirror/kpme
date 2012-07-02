package org.kuali.hr.time.paytype.dao;

import org.kuali.hr.time.paytype.PayType;

import java.util.Date;
import java.util.List;

public interface PayTypeDao {

	public void saveOrUpdate(PayType payType);

	public void saveOrUpdate(List<PayType> payTypeList);

	public PayType getPayType(String payType, Date effectiveDate);
	
	public PayType getPayType(String hrPayTypeId);
	
	public int getPayTypeCount(String payType);

}
