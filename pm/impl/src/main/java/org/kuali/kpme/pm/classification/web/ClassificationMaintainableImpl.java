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
package org.kuali.kpme.pm.classification.web;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrDataObjectMaintainableImpl;
import org.kuali.kpme.pm.api.positionflag.PositionFlagContract;
import org.kuali.kpme.pm.api.pstnqlfrtype.PstnQlfrTypeContract;
import org.kuali.kpme.pm.classification.Classification;
import org.kuali.kpme.pm.classification.duty.ClassificationDuty;
import org.kuali.kpme.pm.classification.flag.ClassificationFlag;
import org.kuali.kpme.pm.classification.qual.ClassificationQualification;
import org.kuali.kpme.pm.positionflag.PositionFlag;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;



public class ClassificationMaintainableImpl extends HrDataObjectMaintainableImpl {
	private static final long serialVersionUID = 1L;

	@Override
	public HrBusinessObject getObjectById(String id) {
		return (HrBusinessObject) PmServiceLocator.getClassificationService().getClassificationById(id);
	}
	
	@Override
	public void customSaveLogic(HrBusinessObject hrObj){
		Classification aClss = (Classification) hrObj;
		for(ClassificationQualification aQual : aClss.getQualificationList()) {
			aQual.setPmPositionClassId(aClss.getPmPositionClassId());
			aQual.setPmClassificationQualificationId(null);
		}
		for(ClassificationDuty aDuty : aClss.getDutyList()) {
			aDuty.setPmPositionClassId(aClss.getPmPositionClassId());
			aDuty.setPmDutyId(null);
		}
		for(ClassificationFlag aFlag : aClss.getFlagList()) {
			aFlag.setPmPositionClassId(aClss.getPmPositionClassId());
			aFlag.setPmFlagId(null);
		}
		
	}

	@Override
	protected boolean performAddLineValidation(View view,
		CollectionGroup collectionGroup, Object model, Object addLine) {
		
		boolean isValid = super.performAddLineValidation(view, collectionGroup, model, addLine);
        if (model instanceof MaintenanceDocumentForm) {
	        MaintenanceDocumentForm maintenanceForm = (MaintenanceDocumentForm) model;
	        MaintenanceDocument document = maintenanceForm.getDocument();
	        LocalDate asOfDate = LocalDate.now();
	        if (document.getNewMaintainableObject().getDataObject() instanceof Classification) {
	        	Classification classificationObj = (Classification) document.getNewMaintainableObject().getDataObject();
	        	if(classificationObj.getEffectiveDate() != null) {
	        		asOfDate = classificationObj.getEffectiveLocalDate();
	        	}
	        	if(addLine instanceof ClassificationQualification) {
		        	ClassificationQualification cQualification = (ClassificationQualification) addLine;
		        	PstnQlfrTypeContract qType = PmServiceLocator.getPstnQlfrTypeService().getPstnQlfrTypeById(cQualification.getQualificationType());
		        	if(qType == null || !qType.isActive()) {
		        		GlobalVariables.getMessageMap().putError("newCollectionLines['document.newMaintainableObject.dataObject.qualificationList'].qualificationType","error.existence", "Qualification Type '"+ cQualification.getQualificationValue() + "'");
		        		isValid = false;
		        		return isValid;
		        	}
	        	} else if (addLine instanceof ClassificationFlag) {
	        		ClassificationFlag classificationFlag = (ClassificationFlag) addLine;
	        		List<String> flagNames = classificationFlag.getNames();
	        		String categoryNm = classificationFlag.getCategory();
	        		for(String flagName : flagNames) {
		        		List<? extends PositionFlagContract> pFlags = PmServiceLocator.getPositionFlagService().getAllActivePositionFlags(categoryNm, flagName, asOfDate);
		        		if(pFlags == null || CollectionUtils.isEmpty(pFlags)){
			        		GlobalVariables.getMessageMap().putError("newCollectionLines['document.newMaintainableObject.dataObject.flagList'].names","error.existence", "Flag '"+ flagName + "'");
			        		isValid = false;
			        		return isValid;
		        		}
		        		
	        		}
	        	}
	        } 
        }
        return isValid;
	}
	
}
