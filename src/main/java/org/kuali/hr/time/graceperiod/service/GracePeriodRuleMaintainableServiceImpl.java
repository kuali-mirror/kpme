package org.kuali.hr.time.graceperiod.service;

import java.sql.Timestamp;

import org.kuali.hr.time.graceperiod.rule.GracePeriodRule;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
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
		if(gracePeriodRule.getTkGracePeriodRuleId()!=null && gracePeriodRule.isActive()){
			GracePeriodRule oldGracePeriodRule = TkServiceLocator.getGracePeriodService().getGracePeriodRule(gracePeriodRule.getTkGracePeriodRuleId());
			if(gracePeriodRule.getEffectiveDate().equals(oldGracePeriodRule.getEffectiveDate())){
				gracePeriodRule.setTimestamp(null);
			} else{
				if(oldGracePeriodRule!=null){
					oldGracePeriodRule.setActive(false);
					//NOTE this is done to prevent the timestamp of the inactive one to be greater than the 
					oldGracePeriodRule.setTimestamp(TKUtils.subtractOneSecondFromTimestamp(new Timestamp(System.currentTimeMillis())));
					oldGracePeriodRule.setEffectiveDate(gracePeriodRule.getEffectiveDate());
					KNSServiceLocator.getBusinessObjectService().save(oldGracePeriodRule);
				}
				gracePeriodRule.setTimestamp(new Timestamp(System.currentTimeMillis()));
				gracePeriodRule.setTkGracePeriodRuleId(null);
			}
		}
		
		KNSServiceLocator.getBusinessObjectService().save(gracePeriodRule);
	}

}
