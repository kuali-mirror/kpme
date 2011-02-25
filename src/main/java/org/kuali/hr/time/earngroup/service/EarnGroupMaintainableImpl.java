package org.kuali.hr.time.earngroup.service;

import java.util.HashSet;
import java.util.Set;

import org.kuali.hr.time.earngroup.EarnGroup;
import org.kuali.hr.time.earngroup.EarnGroupDefinition;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.KNSConstants;

public class EarnGroupMaintainableImpl extends KualiMaintainableImpl{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Override
	public void saveBusinessObject() {
		EarnGroup earnGroup = (EarnGroup)this.getBusinessObject();
//		EarnGroup oldEarnGroup = (EarnGroup)KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(
//				EarnGroup.class, earnGroup.getTkEarnGroupId());
//		if(oldEarnGroup!=null){
//			oldEarnGroup.setActive(false);
//			KNSServiceLocator.getBusinessObjectService().save(oldEarnGroup);
//		}
		earnGroup.setTkEarnGroupId(null);
		earnGroup.setTimestamp(null);
		KNSServiceLocator.getBusinessObjectService().save(earnGroup);
	}
	
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
		 
}
