package org.kuali.hr.time.timecollection.rule.service;

import java.sql.Timestamp;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.core.cache.CacheUtils;
import org.kuali.hr.time.collection.rule.TimeCollectionRule;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

public class TimeCollectionRuleMaintainableImpl extends KualiMaintainableImpl {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void processAfterNew(MaintenanceDocument document, Map<String, String[]> parameters) {
        super.processAfterNew(document, parameters);
    }

    @Override
    public void processAfterPost(MaintenanceDocument document, Map<String, String[]> parameters) {
        TimeCollectionRule timeCollectionRule = (TimeCollectionRule) document.getNewMaintainableObject().getBusinessObject();
        timeCollectionRule.setUserPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
        super.processAfterPost(document, parameters);
    }

    @Override
    public void processAfterEdit(MaintenanceDocument document, Map<String, String[]> parameters) {
        TimeCollectionRule timeCollectionRule = (TimeCollectionRule) document.getNewMaintainableObject().getBusinessObject();
        timeCollectionRule.setUserPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
        super.processAfterEdit(document, parameters);
    }

    @Override
    public void saveBusinessObject() {
        TimeCollectionRule timeCollectionRule = (TimeCollectionRule) this.getBusinessObject();

		//Inactivate the old time collection rule as of the effective date of new time collection rule
		if(timeCollectionRule.getTkTimeCollectionRuleId()!=null && timeCollectionRule.isActive()){
			TimeCollectionRule oldTimeCollectRule = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule(timeCollectionRule.getTkTimeCollectionRuleId());
			if(timeCollectionRule.getEffectiveDate().equals(oldTimeCollectRule.getEffectiveDate())){
				timeCollectionRule.setTimestamp(null);
			} else{
				if(oldTimeCollectRule!=null){
					oldTimeCollectRule.setActive(false);
					//NOTE this is done to prevent the timestamp of the inactive one to be greater than the 
					oldTimeCollectRule.setTimestamp(TKUtils.subtractOneSecondFromTimestamp(new Timestamp(System.currentTimeMillis())));
					oldTimeCollectRule.setEffectiveDate(timeCollectionRule.getEffectiveDate());
					KRADServiceLocator.getBusinessObjectService().save(oldTimeCollectRule);
				}
				timeCollectionRule.setTimestamp(new Timestamp(System.currentTimeMillis()));
				timeCollectionRule.setTkTimeCollectionRuleId(null);
			}
		}
		
		KRADServiceLocator.getBusinessObjectService().save(timeCollectionRule);
        CacheUtils.flushCache(TimeCollectionRule.CACHE_NAME);
    }


    @Override
    public Map populateBusinessObject(Map<String, String> fieldValues, MaintenanceDocument maintenanceDocument, String methodToCall) {
        if (fieldValues.containsKey("workArea") && StringUtils.equals(fieldValues.get("workArea"), "%")) {
            fieldValues.put("workArea", "-1");
        }
        if (fieldValues.containsKey("jobNumber") && StringUtils.equals(fieldValues.get("jobNumber"), "%")) {
            fieldValues.put("jobNumber", "-1");
        }
        return super.populateBusinessObject(fieldValues, maintenanceDocument, methodToCall);
    }
}
