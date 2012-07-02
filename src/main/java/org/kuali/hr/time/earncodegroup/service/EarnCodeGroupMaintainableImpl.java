package org.kuali.hr.time.earncodegroup.service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.earncodegroup.EarnCodeGroup;
import org.kuali.hr.time.earncodegroup.EarnCodeGroupDefinition;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;

public class EarnCodeGroupMaintainableImpl extends HrBusinessObjectMaintainableImpl{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
    public void addNewLineToCollection(String collectionName) {
        if (collectionName.equals("earnCodeGroups")) {
        	EarnCodeGroupDefinition definition = (EarnCodeGroupDefinition)newCollectionLines.get(collectionName );
            if ( definition != null ) {
            	EarnCodeGroup earnGroup = (EarnCodeGroup)this.getBusinessObject();
            	Set<String> earnCodes = new HashSet<String>();
            	for(EarnCodeGroupDefinition earnGroupDef : earnGroup.getEarnCodeGroups()){
            		earnCodes.add(earnGroupDef.getEarnCode());
            	}
            	if(earnCodes.contains(definition.getEarnCode())){
            		GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRADConstants.MAINTENANCE_NEW_MAINTAINABLE +"earnCodeGroups", 
            				"earngroup.duplicate.earncode",definition.getEarnCode());
            		return;
    			} 
            	if (!ValidationUtils.validateEarnCode(definition.getEarnCode().toUpperCase(), earnGroup.getEffectiveDate())) {
    				GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRADConstants.MAINTENANCE_NEW_MAINTAINABLE +"earnCodeGroups", 
    							"error.existence", "Earncode '" + definition.getEarnCode()+ "'");
    				return;
    			} 
            }
        }
       super.addNewLineToCollection(collectionName);
    }
	
	@Override
    public void processAfterEdit( MaintenanceDocument document, Map<String,String[]> parameters ) {
		EarnCodeGroup earnGroup = (EarnCodeGroup)this.getBusinessObject();
		int count = TkServiceLocator.getEarnCodeGroupService().getNewerEarnCodeGroupCount(earnGroup.getEarnCodeGroup(), earnGroup.getEffectiveDate());
		if(count > 0) {
			GlobalVariables.getMessageMap().putWarningWithoutFullErrorPath(KRADConstants.MAINTENANCE_NEW_MAINTAINABLE + "effectiveDate", 
					"earngroup.effectiveDate.newr.exists");
		}
	}

	@Override
	public HrBusinessObject getObjectById(String id) {
		return TkServiceLocator.getEarnCodeGroupService().getEarnCodeGroup(id);
	} 
	
}
