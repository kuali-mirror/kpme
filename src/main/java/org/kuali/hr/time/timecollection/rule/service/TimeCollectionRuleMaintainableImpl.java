package org.kuali.hr.time.timecollection.rule.service;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.collection.rule.TimeCollectionRule;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;

import java.util.Map;

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
//		TimeCollectionRule oldTimeCollectionRule = (TimeCollectionRule) KNSServiceLocator
//				.getBusinessObjectService().findBySinglePrimaryKey(
//						TimeCollectionRule.class,
//						timeCollectionRule.getTkTimeCollectionRuleId());
//		if (oldTimeCollectionRule != null) {
//			oldTimeCollectionRule.setActive(false);
//			KNSServiceLocator.getBusinessObjectService().save(
//					oldTimeCollectionRule);
//		}
        timeCollectionRule.setTkTimeCollectionRuleId(null);
        timeCollectionRule.setTimeStamp(null);
        KNSServiceLocator.getBusinessObjectService().save(timeCollectionRule);
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
