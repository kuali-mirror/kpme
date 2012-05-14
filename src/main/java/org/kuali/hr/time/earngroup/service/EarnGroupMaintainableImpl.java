package org.kuali.hr.time.earngroup.service;

import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.earngroup.EarnGroup;
import org.kuali.hr.time.earngroup.EarnGroupDefinition;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.KNSConstants;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EarnGroupMaintainableImpl extends HrBusinessObjectMaintainableImpl{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
    public void addNewLineToCollection(String collectionName) {
        if (collectionName.equals("earnGroups")) {
        	EarnGroupDefinition definition = (EarnGroupDefinition)newCollectionLines.get(collectionName );
            if ( definition != null ) {
            	EarnGroup earnGroup = (EarnGroup)this.getBusinessObject();
            	Set<String> earnCodes = new HashSet<String>();
            	for(EarnGroupDefinition earnGroupDef : earnGroup.getEarnGroups()){
            		earnCodes.add(earnGroupDef.getEarnCode());
            	}
            	if(earnCodes.contains(definition.getEarnCode())){
            		GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KNSConstants.MAINTENANCE_NEW_MAINTAINABLE +"earnGroups", 
            				"earngroup.duplicate.earncode",definition.getEarnCode());
            		return;
    			} 
            	if (!ValidationUtils.validateEarnCode(definition.getEarnCode().toUpperCase(), earnGroup.getEffectiveDate())) {
    				GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KNSConstants.MAINTENANCE_NEW_MAINTAINABLE +"earnGroups", 
    							"error.existence", "Earncode '" + definition.getEarnCode()+ "'");
    				return;
    			} 
            }
        }
       super.addNewLineToCollection(collectionName);
    }
	
	@Override
    public void processAfterEdit( MaintenanceDocument document, Map<String,String[]> parameters ) {
		EarnGroup earnGroup = (EarnGroup)this.getBusinessObject();
		int count = TkServiceLocator.getEarnGroupService().getNewerEarnGroupCount(earnGroup.getEarnGroup(), earnGroup.getEffectiveDate());
		if(count > 0) {
			GlobalVariables.getMessageMap().putWarningWithoutFullErrorPath(KNSConstants.MAINTENANCE_NEW_MAINTAINABLE + "effectiveDate", 
					"earngroup.effectiveDate.newr.exists");
		}
	}

	@Override
	public HrBusinessObject getObjectById(String id) {
		return TkServiceLocator.getEarnGroupService().getEarnGroup(id);
	} 
	
}
