package org.kuali.hr.time.clock.location.service;

import org.kuali.hr.time.clock.location.ClockLocationRule;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class ClockLocationMaintainableImpl extends KualiMaintainableImpl {

    /**
     * 
     */
    private static final long serialVersionUID = -93577754706987067L;


	@Override
	public void saveBusinessObject() {
		ClockLocationRule clockLocationRule = (ClockLocationRule)this.getBusinessObject();
		ClockLocationRule oldClockLocationRule = (ClockLocationRule)KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(
													ClockLocationRule.class, clockLocationRule.getTkClockLocationRuleId());
		oldClockLocationRule.setActive(false);
		KNSServiceLocator.getBusinessObjectService().save(oldClockLocationRule);
		clockLocationRule.setTkClockLocationRuleId(null);
		KNSServiceLocator.getBusinessObjectService().save(clockLocationRule);
	}
    
    
    

}
