/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.core.earncode.group.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrBusinessObjectMaintainableImpl;
import org.kuali.kpme.core.bo.HrDataObjectMaintainableImpl;
import org.kuali.kpme.core.earncode.group.EarnCodeGroupBo;
import org.kuali.kpme.core.earncode.group.EarnCodeGroupDefinitionBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;

public class EarnCodeGroupMaintainableImpl extends HrDataObjectMaintainableImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
    protected boolean performAddLineValidation(View view,
                                 CollectionGroup collectionGroup, Object model, Object addLine) {
        boolean isValid = super.performAddLineValidation(view, collectionGroup, model, addLine);
        //if (collectionName.equals("earnCodeGroups")) {
        if (model instanceof MaintenanceDocumentForm) {
            MaintenanceDocumentForm maintenanceForm = (MaintenanceDocumentForm) model;
            MaintenanceDocument document = maintenanceForm.getDocument();
            if (document.getNewMaintainableObject().getDataObject() instanceof EarnCodeGroupBo) {
                EarnCodeGroupBo earnCodeGroupBo = (EarnCodeGroupBo) document.getNewMaintainableObject().getDataObject();

                if (addLine instanceof EarnCodeGroupDefinitionBo) {
                    EarnCodeGroupDefinitionBo definition = (EarnCodeGroupDefinitionBo) addLine;
                    if (definition != null) {
                        EarnCodeGroupBo earnGroup = (EarnCodeGroupBo) this.getDataObject();
                        Set<String> earnCodes = new HashSet<String>();
                        for (EarnCodeGroupDefinitionBo earnGroupDef : earnGroup.getEarnCodeGroups()) {
                            earnCodes.add(earnGroupDef.getEarnCode());
                        }
                        if (earnCodes.contains(definition.getEarnCode())) {
                            GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRADConstants.MAINTENANCE_NEW_MAINTAINABLE + "earnCodeGroups",
                                    "earngroup.duplicate.earncode", definition.getEarnCode());
                            return false;
                        }
                        if (!ValidationUtils.validateEarnCode(definition.getEarnCode().toUpperCase(), earnGroup.getEffectiveLocalDate())) {
                            GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRADConstants.MAINTENANCE_NEW_MAINTAINABLE + "earnCodeGroups",
                                    "error.existence", "Earncode '" + definition.getEarnCode() + "'");
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
	
//	@Override
//    public void processAfterEdit( MaintenanceDocument document, Map<String,String[]> parameters ) {
//		EarnCodeGroup earnGroup = (EarnCodeGroup)this.getBusinessObject();
//		int count = HrServiceLocator.getEarnCodeGroupService().getNewerEarnCodeGroupCount(earnGroup.getEarnCodeGroup(), earnGroup.getEffectiveLocalDate());
//		if(count > 0) {
//			GlobalVariables.getMessageMap().putWarningWithoutFullErrorPath(KRADConstants.MAINTENANCE_NEW_MAINTAINABLE + "effectiveDate", 
//					"earngroup.effectiveDate.newr.exists");
//		}
//	}

	@Override
	public HrBusinessObject getObjectById(String id) {
		return EarnCodeGroupBo.from(HrServiceLocator.getEarnCodeGroupService().getEarnCodeGroup(id));
	} 

    @Override
    public void customSaveLogic(HrBusinessObject hrObj){
        EarnCodeGroupBo ecg = (EarnCodeGroupBo)hrObj;
        for (EarnCodeGroupDefinitionBo definition : ecg.getEarnCodeGroups()) {
            definition.setHrEarnCodeGroupDefId(null);
            definition.setHrEarnCodeGroupId(ecg.getHrEarnCodeGroupId());
        }
    }

    @Override
    public void customInactiveSaveLogicNewEffective(HrBusinessObject oldHrObj) {
        EarnCodeGroupBo bo = (EarnCodeGroupBo)oldHrObj;
        bo.setEarnCodeGroups(new ArrayList<EarnCodeGroupDefinitionBo>());
    }
}
