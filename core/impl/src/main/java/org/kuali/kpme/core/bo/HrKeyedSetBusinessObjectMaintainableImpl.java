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
package org.kuali.kpme.core.bo;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.derived.HrBusinessObjectKey;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;

import java.util.ArrayList;

public abstract class HrKeyedSetBusinessObjectMaintainableImpl<O extends HrKeyedSetBusinessObject<O, K>, K extends HrBusinessObjectKey<O, K>> extends HrDataObjectMaintainableImpl {

	private static final long serialVersionUID = -1831580725621204948L;
	
	private static final String DUPLICATE_GROUP_KEY_CODE_ERROR_KEY = "keyedSet.duplicate.groupKeyCode";
	private static final String ERROR_EXISTENCE_KEY = "error.existence";
	private static final String ADDED_KEY_PROPERTY_PATH = "newCollectionLines['document.newMaintainableObject.dataObject.effectiveKeyList'].groupKeyCode";
	private static final String EFFECTIVE_KEY_LIST = "effectiveKeyList";

	@SuppressWarnings("unchecked")
	@Override
    public void customSaveLogic(HrBusinessObject hrObj){
		O owner = (O) hrObj;
        for (K key : owner.getEffectiveKeyList()) {
            key.setId(null);
            key.setOwnerId(owner.getId());
        }
    }

    @Override
    public void customInactiveSaveLogicNewEffective(HrBusinessObject oldHrObj) {
        O bo = (O) oldHrObj;
        bo.setEffectiveKeyList(new ArrayList<K>());
    }

    @Override
	protected boolean performAddLineValidation(View view, CollectionGroup collectionGroup, Object model, Object addLine) {
		boolean retVal = super.performAddLineValidation(view, collectionGroup, model, addLine);
		if(retVal && EFFECTIVE_KEY_LIST.equals(collectionGroup.getPropertyName()) && (addLine instanceof HrBusinessObjectKey) ) {
			HrBusinessObjectKey<?,?> addedKeyBo = (HrBusinessObjectKey<?,?>) addLine;
			if (model instanceof MaintenanceDocumentForm) {
		        MaintenanceDocumentForm maintenanceForm = (MaintenanceDocumentForm) model;
		        MaintenanceDocument document = maintenanceForm.getDocument();
		        if (document.getNewMaintainableObject().getDataObject() instanceof HrKeyedSetBusinessObject) {
		        	HrKeyedSetBusinessObject<?,?> keyOwnerObj = (HrKeyedSetBusinessObject<?,?>) document.getNewMaintainableObject().getDataObject();
		        	LocalDate asOfDate = LocalDate.fromDateFields(keyOwnerObj.getRelativeEffectiveDate());
		        	String groupKeyCode = addedKeyBo.getGroupKeyCode();
		        	// validate the existence of the added grp key code for the given date
		        	if(!ValidationUtils.validateGroupKey(groupKeyCode, asOfDate)) {
		        		GlobalVariables.getMessageMap().putError(ADDED_KEY_PROPERTY_PATH, ERROR_EXISTENCE_KEY, "Group Key '"+ groupKeyCode + "'");
		        		retVal = false;
		        	}
		        	// validate that duplicates not entered
		        	if(retVal && keyOwnerObj.getGroupKeyCodeSet().contains(groupKeyCode)) {
		        		GlobalVariables.getMessageMap().putError(ADDED_KEY_PROPERTY_PATH, DUPLICATE_GROUP_KEY_CODE_ERROR_KEY, groupKeyCode);
		        		retVal = false;
		        	}
		        }
			}
		}
		return retVal;
	}

}