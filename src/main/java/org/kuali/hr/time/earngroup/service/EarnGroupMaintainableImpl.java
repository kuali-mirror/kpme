package org.kuali.hr.time.earngroup.service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.kuali.hr.time.earngroup.EarnGroup;
import org.kuali.hr.time.earngroup.EarnGroupDefinition;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
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

		//Inactivate the old earn group as of the effective date of new earn group
		if(earnGroup.getTkEarnGroupId()!=null && earnGroup.isActive()){
			EarnGroup oldEarnGroup = TkServiceLocator.getEarnGroupService().getEarnGroup(earnGroup.getTkEarnGroupId());
			if(earnGroup.getEffectiveDate().equals(oldEarnGroup.getEffectiveDate())){
				oldEarnGroup.setActive(false);
				earnGroup.setTimestamp(new Timestamp(System.currentTimeMillis()));
				earnGroup.setTkEarnGroupId(null);
			} else{
				if(oldEarnGroup!=null){
					oldEarnGroup.setActive(false);
					//NOTE this is done to prevent the timestamp of the inactive one to be greater than the 
					oldEarnGroup.setTimestamp(TKUtils.subtractOneSecondFromTimestamp(new Timestamp(System.currentTimeMillis())));
					oldEarnGroup.setEffectiveDate(earnGroup.getEffectiveDate());
					KNSServiceLocator.getBusinessObjectService().save(oldEarnGroup);
				}
				earnGroup.setTimestamp(new Timestamp(System.currentTimeMillis()));
				earnGroup.setTkEarnGroupId(null);
			}
		}
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
	
	@Override
    public void processAfterEdit( MaintenanceDocument document, Map<String,String[]> parameters ) {
		EarnGroup earnGroup = (EarnGroup)this.getBusinessObject();
		if(ValidationUtils.newerVersionExists(EarnGroup.class, "earnGroup", earnGroup.getEarnGroup(), earnGroup.getEffectiveDate())) {
			GlobalVariables.getMessageMap().putWarningWithoutFullErrorPath(KNSConstants.MAINTENANCE_NEW_MAINTAINABLE + "effectiveDate", 
					"earngroup.effectiveDate.newr.exists");
		}
	} 
	
}
