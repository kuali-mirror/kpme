/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.core.bo.earncode.group.service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrBusinessObjectMaintainableImpl;
import org.kuali.kpme.core.bo.earncode.group.EarnCodeGroup;
import org.kuali.kpme.core.bo.earncode.group.EarnCodeGroupDefinition;
import org.kuali.kpme.core.bo.utils.ValidationUtils;
import org.kuali.kpme.core.service.HrServiceLocator;
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
            	if (!ValidationUtils.validateEarnCode(definition.getEarnCode().toUpperCase(), earnGroup.getEffectiveLocalDate())) {
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
		int count = HrServiceLocator.getEarnCodeGroupService().getNewerEarnCodeGroupCount(earnGroup.getEarnCodeGroup(), earnGroup.getEffectiveLocalDate());
		if(count > 0) {
			GlobalVariables.getMessageMap().putWarningWithoutFullErrorPath(KRADConstants.MAINTENANCE_NEW_MAINTAINABLE + "effectiveDate", 
					"earngroup.effectiveDate.newr.exists");
		}
	}

	@Override
	public HrBusinessObject getObjectById(String id) {
		return HrServiceLocator.getEarnCodeGroupService().getEarnCodeGroup(id);
	} 

    @Override
    public void customSaveLogic(HrBusinessObject hrObj){
        EarnCodeGroup ecg = (EarnCodeGroup)hrObj;
        for (EarnCodeGroupDefinition definition : ecg.getEarnCodeGroups()) {
            definition.setHrEarnCodeGroupDefId(null);
            definition.setHrEarnCodeGroupId(ecg.getHrEarnCodeGroupId());
        }
    }
}
