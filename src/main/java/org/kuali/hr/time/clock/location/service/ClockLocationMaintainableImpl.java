package org.kuali.hr.time.clock.location.service;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.clock.location.ClockLocationRule;
import org.kuali.rice.kns.document.MaintenanceDocument;
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
//		ClockLocationRule oldClockLocationRule = (ClockLocationRule)KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(
//													ClockLocationRule.class, clockLocationRule.getTkClockLocationRuleId());
//		if(oldClockLocationRule!=null){
//			oldClockLocationRule.setActive(false);
//			KNSServiceLocator.getBusinessObjectService().save(oldClockLocationRule);
//		}
		clockLocationRule.setTkClockLocationRuleId(null);
		clockLocationRule.setTimestamp(null);
		KNSServiceLocator.getBusinessObjectService().save(clockLocationRule);
	}


	@Override
	public Map populateBusinessObject(Map<String, String> fieldValues,
			MaintenanceDocument maintenanceDocument, String methodToCall) {
		if(fieldValues.containsKey("workArea") && StringUtils.equals(fieldValues.get("workArea"),"%")){
			fieldValues.put("workArea", "-1");
		}
		if(fieldValues.containsKey("jobNumber") && StringUtils.equals(fieldValues.get("jobNumber"),"%")){
			fieldValues.put("jobNumber", "-1");
		}
		return super.populateBusinessObject(fieldValues, maintenanceDocument,
				methodToCall);
	}
    
    
    

}
