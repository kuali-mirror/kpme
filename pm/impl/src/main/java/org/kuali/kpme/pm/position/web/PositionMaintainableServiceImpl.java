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
package org.kuali.kpme.pm.position.web;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrBusinessObjectMaintainableImpl;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.pm.position.Position;
import org.kuali.kpme.pm.position.funding.PositionFunding;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;

public class PositionMaintainableServiceImpl extends HrBusinessObjectMaintainableImpl {

	private static final long serialVersionUID = 1L;

	@Override
	public HrBusinessObject getObjectById(String id) {
		return PmServiceLocator.getPositionService().getPosition(id);
	}
	
//	@Override
//    protected boolean performAddLineValidation(View view, CollectionGroup collectionGroup, Object model,
//            Object addLine) {
//        boolean isValid = true;
//        if (model instanceof MaintenanceDocumentForm) {
//	        MaintenanceDocumentForm maintenanceForm = (MaintenanceDocumentForm) model;
//	        MaintenanceDocument document = maintenanceForm.getDocument();
//	        if (document.getNewMaintainableObject().getDataObject() instanceof Position) {
//	        	Position aPosition = (Position) document.getNewMaintainableObject().getDataObject();
//	        	// Funding line validation
//		        if (addLine instanceof PositionFunding) {
//		        	PositionFunding pf = (PositionFunding) addLine;
//		        	boolean results = this.validateAddFundingLine(pf, aPosition);
//		        	if(!results) {
//		        		GlobalVariables.getMessageMap().addToErrorPath("document");
//		        		return false;
//		        	}
//		        }
//	        }
//        }
//
//        return isValid;
//    }
	
	protected boolean validateAddFundingLine(PositionFunding pf, Position aPosition) {
    	if(pf.getEffectiveDate() != null && aPosition.getEffectiveDate() != null) {
    		if(pf.getEffectiveDate().compareTo(aPosition.getEffectiveDate()) < 0) {
    			String[] parameters = new String[2];
    			parameters[0] = pf.getEffectiveDate().toString();
    			parameters[1] = aPosition.getEffectiveDate().toString();
    			GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRADConstants.MAINTENANCE_NEW_MAINTAINABLE + "dataObject.fundingList","error.funding.effdt.invalid", parameters);
   			 	return false;
    		}
    	}
    	if(StringUtils.isNotEmpty(pf.getAccount())) {
    		boolean results = ValidationUtils.validateAccount(pf.getAccount());
    		if(!results) {
//    			GlobalVariables.getMessageMap().putError(KRADConstants.MAINTENANCE_NEW_MAINTAINABLE + "dataObject","error.funding.account.notExist", pf.getAccount());
    			 
    			GlobalVariables.getMessageMap().addToErrorPath(KRADConstants.MAINTENANCE_NEW_MAINTAINABLE + "dataObject.fundingList");
    			GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRADConstants.MAINTENANCE_NEW_MAINTAINABLE + "dataObject.fundingList", "error testing");
    			return results;
    		}
    	}
    	if(StringUtils.isNotEmpty(pf.getSubAccount())) {
    		boolean results = ValidationUtils.validateSubAccount(pf.getSubAccount());
    		if(!results) {
	   			 GlobalVariables.getMessageMap().putError("dataObject.fundingList","error.funding.subAccount.notExist", pf.getSubAccount());
	   			 return results;
    		}
    	}
    	if(StringUtils.isNotEmpty(pf.getObjectCode())) {
    		boolean results = ValidationUtils.validateObjectCode(pf.getObjectCode());
    		if(!results) {
      			 GlobalVariables.getMessageMap().putError("dataObject.fundingList","error.funding.objectCode.notExist", pf.getObjectCode());
      			 return results;
    		}
    	}
    	if(StringUtils.isNotEmpty(pf.getSubObjectCode())) {
    		boolean results = ValidationUtils.validateSubObjectCode(pf.getSubObjectCode());
    		if(!results) {
      			 GlobalVariables.getMessageMap().putError("dataObject.fundingList","error.funding.subObjectCode.notExist", pf.getSubObjectCode());
      			 return results;
    		}
    	}
    	return true;
    
	}

}
