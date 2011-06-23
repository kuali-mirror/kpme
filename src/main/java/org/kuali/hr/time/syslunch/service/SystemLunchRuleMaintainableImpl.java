package org.kuali.hr.time.syslunch.service;

import org.kuali.hr.time.syslunch.rule.SystemLunchRule;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class SystemLunchRuleMaintainableImpl extends KualiMaintainableImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void saveBusinessObject() {
		SystemLunchRule sysLunchRule = (SystemLunchRule)this.getBusinessObject();
		sysLunchRule.setTkSystemLunchRuleId(null);
		sysLunchRule.setTimestamp(null);
		sysLunchRule.setActive(true);
		KNSServiceLocator.getBusinessObjectService().save(sysLunchRule);
	}
}
