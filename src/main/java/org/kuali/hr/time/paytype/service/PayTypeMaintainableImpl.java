package org.kuali.hr.time.paytype.service;

import java.sql.Timestamp;

import org.kuali.hr.time.overtime.daily.rule.DailyOvertimeRule;
import org.kuali.hr.time.paytype.PayType;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class PayTypeMaintainableImpl extends KualiMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void saveBusinessObject() {
		PayType payType = (PayType) this
				.getBusinessObject();
		if(payType.getHrPayTypeId()!=null && payType.isActive()){
			PayType oldPayType = TkServiceLocator.getPayTypeSerivce().getPayType(payType.getHrPayTypeId());
			if(payType.getEffectiveDate().equals(oldPayType.getEffectiveDate())){
				payType.setTimestamp(null);
			} else{
				if(oldPayType!=null){
					oldPayType.setActive(false);
					//NOTE this is done to prevent the timestamp of the inactive one to be greater than the 
					oldPayType.setTimestamp(TKUtils.subtractOneSecondFromTimestamp(new Timestamp(System.currentTimeMillis())));
					oldPayType.setEffectiveDate(payType.getEffectiveDate());
					KNSServiceLocator.getBusinessObjectService().save(oldPayType);
				}
				payType.setTimestamp(new Timestamp(System.currentTimeMillis()));
				payType.setHrPayTypeId(null);
			}
		}
		KNSServiceLocator.getBusinessObjectService()
				.save(payType);
	}

}
