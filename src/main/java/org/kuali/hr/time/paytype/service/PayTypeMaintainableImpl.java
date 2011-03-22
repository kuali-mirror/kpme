package org.kuali.hr.time.paytype.service;

import org.kuali.hr.time.paytype.PayType;
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
		payType.setHrPayTypeId(null);
		payType.setTimestamp(null);
		KNSServiceLocator.getBusinessObjectService()
				.save(payType);
	}

}
