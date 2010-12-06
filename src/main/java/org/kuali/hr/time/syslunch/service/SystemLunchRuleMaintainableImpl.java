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
		SystemLunchRule oldSysLunchRule = (SystemLunchRule)KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(
				SystemLunchRule.class, sysLunchRule.getTkSystemLunchRuleId());
		if(oldSysLunchRule!=null){
			oldSysLunchRule.setActive(false);
			KNSServiceLocator.getBusinessObjectService().save(oldSysLunchRule);
		}
		sysLunchRule.setTkSystemLunchRuleId(null);
		sysLunchRule.setTimeStamp(null);
		KNSServiceLocator.getBusinessObjectService().save(sysLunchRule);
	}
}
