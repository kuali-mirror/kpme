package org.kuali.hr.time.graceperiod.service;

import org.kuali.hr.time.graceperiod.rule.GracePeriodRule;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class GracePeriodRuleMaintainableServiceImpl extends
		KualiMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void saveBusinessObject() {
		GracePeriodRule gracePeriodRule = (GracePeriodRule)this.getBusinessObject();
		gracePeriodRule.setTkGracePeriodRuleId(null);
		gracePeriodRule.setTimestamp(null);
		KNSServiceLocator.getBusinessObjectService().save(gracePeriodRule);
	}

}
