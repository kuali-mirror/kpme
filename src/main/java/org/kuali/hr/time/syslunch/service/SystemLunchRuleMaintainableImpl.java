package org.kuali.hr.time.syslunch.service;

import java.sql.Timestamp;

import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.syslunch.rule.SystemLunchRule;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.krad.service.KRADServiceLocator;

public class SystemLunchRuleMaintainableImpl extends KualiMaintainableImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void saveBusinessObject() {
		SystemLunchRule sysLunchRule = (SystemLunchRule)this.getBusinessObject();
		if(sysLunchRule.getTkSystemLunchRuleId()!=null && sysLunchRule.isActive()){
			SystemLunchRule oldSystemLunchRule = TkServiceLocator.getSystemLunchRuleService().getSystemLunchRule(sysLunchRule.getTkSystemLunchRuleId());
			if(sysLunchRule.getEffectiveDate().equals(oldSystemLunchRule.getEffectiveDate())){
				sysLunchRule.setTimestamp(null);
			} else{
				if(oldSystemLunchRule!=null){
					oldSystemLunchRule.setActive(false);
					//NOTE this is done to prevent the timestamp of the inactive one to be greater than the 
					oldSystemLunchRule.setTimestamp(TKUtils.subtractOneSecondFromTimestamp(new Timestamp(System.currentTimeMillis())));
					oldSystemLunchRule.setEffectiveDate(sysLunchRule.getEffectiveDate());
					KRADServiceLocator.getBusinessObjectService().save(oldSystemLunchRule);
				}
				sysLunchRule.setTimestamp(new Timestamp(System.currentTimeMillis()));
				sysLunchRule.setTkSystemLunchRuleId(null);
			}
		}
		
		KRADServiceLocator.getBusinessObjectService().save(sysLunchRule);
	}
}
