package org.kuali.hr.paygrade.service;

import org.kuali.hr.paygrade.PayGrade;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class PayGradeMaintainableServiceImpl extends KualiMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void saveBusinessObject() {
		PayGrade payGrade = (PayGrade)this.getBusinessObject();	
		payGrade.setHrPayGradeId(null);
		payGrade.setTimestamp(null);
		KNSServiceLocator.getBusinessObjectService().save(payGrade);
	}
}
