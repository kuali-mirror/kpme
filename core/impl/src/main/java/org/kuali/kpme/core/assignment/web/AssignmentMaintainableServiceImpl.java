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
package org.kuali.kpme.core.assignment.web;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.assignment.AssignmentBo;
import org.kuali.kpme.core.assignment.AssignmentBo;
import org.kuali.kpme.core.assignment.account.AssignmentAccountBo;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrDataObjectMaintainableImpl;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;

/**
 * Override the Maintenance page behavior for Assignment object
 * 
 * 
 */
public class AssignmentMaintainableServiceImpl extends HrDataObjectMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public HrBusinessObject getObjectById(String id) {
		return AssignmentBo.from(HrServiceLocator.getAssignmentService().getAssignment(id));
	}

	@Override
	public void customSaveLogic(HrBusinessObject hrObj) {
		AssignmentBo assignment = (AssignmentBo)hrObj;
		for (AssignmentAccountBo assignAcct : assignment.getAssignmentAccounts()) {
			if(!isOldDataObjectInDocument()){ //prevents duplicate object on edit
				assignAcct.setTkAssignAcctId(null);
			}
			assignAcct.setTkAssignmentId(assignment.getTkAssignmentId());

		}
	}

    //KPME-2624 added logic to save current logged in user to UserPrincipal id for collections
    @Override
    public void prepareForSave() {
    AssignmentBo assignment = (AssignmentBo)this.getDataObject();
        for (AssignmentAccountBo assignAcct : assignment.getAssignmentAccounts()) {
            assignAcct.setUserPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
        }
        super.prepareForSave();
    }

	@Override
	protected boolean performAddLineValidation(View view,
			CollectionGroup collectionGroup, Object model, Object addLine) {
        boolean isValid = super.performAddLineValidation(view, collectionGroup, model, addLine);
        if (model instanceof MaintenanceDocumentForm) {
	        MaintenanceDocumentForm maintenanceForm = (MaintenanceDocumentForm) model;
	        MaintenanceDocument document = maintenanceForm.getDocument();
	        if (document.getNewMaintainableObject().getDataObject() instanceof AssignmentBo) {
	        	AssignmentBo assignment = (AssignmentBo) document.getNewMaintainableObject().getDataObject();
	        	// Duty line validation
		        if (addLine instanceof AssignmentAccountBo) {
		        	AssignmentAccountBo assignmentAccount = (AssignmentAccountBo) addLine;
		        	boolean results = this.validateAssignmentAccount(assignmentAccount, assignment);
		        	if(!results) {
		        		return false;
		        	}
		        }
	        }
        }
		return isValid;
	}
	
	private boolean validateAssignmentAccount(AssignmentAccountBo assignmentAccount, AssignmentBo assignmentObj) {
		boolean valid = false;
		
		if(StringUtils.isNotEmpty(assignmentAccount.getEarnCode())) {
			valid = ValidationUtils.validateEarnCode(assignmentAccount.getEarnCode(), assignmentObj.getEffectiveLocalDate());
			if(!valid) {
				GlobalVariables.getMessageMap().putError("newCollectionLines['document.newMaintainableObject.dataObject.assignmentAccounts'].earnCode","error.existence", "earn code '"+ assignmentAccount.getEarnCode() + "'");
				return valid;
			}
		}
		if(StringUtils.isNotEmpty(assignmentAccount.getAccountNbr())) {
			valid = ValidationUtils.validateAccount(assignmentAccount.getFinCoaCd(),assignmentAccount.getAccountNbr());
			if(!valid) {
				GlobalVariables.getMessageMap().putError("newCollectionLines['document.newMaintainableObject.dataObject.assignmentAccounts'].accountNbr","error.existence", "Account Number '"+ assignmentAccount.getAccountNbr() + "'");
				return valid;
			}
		}
		if(StringUtils.isNotEmpty(assignmentAccount.getFinObjectCd())) {
			valid = ValidationUtils.validateObjectCode(assignmentAccount.getFinObjectCd(),assignmentAccount.getFinCoaCd(),null);
			if (!valid) {
				GlobalVariables.getMessageMap().putError("newCollectionLines['document.newMaintainableObject.dataObject.assignmentAccounts'].finObjectCd","error.existence", "Object Code '"+ assignmentAccount.getFinObjectCd() + "'");
				return valid;
			}			
		}
		if (StringUtils.isNotEmpty(assignmentAccount.getFinSubObjCd())) {
			valid = ValidationUtils.validateSubObjectCode(String.valueOf(assignmentObj.getEffectiveLocalDate().getYear()),assignmentAccount.getFinCoaCd(),
					assignmentAccount.getAccountNbr(), assignmentAccount.getFinObjectCd(), assignmentAccount.getFinSubObjCd());
			if (!valid) {
				GlobalVariables.getMessageMap().putError("newCollectionLines['document.newMaintainableObject.dataObject.assignmentAccounts'].finSubObjCd","error.existence", "SubObject Code '"+ assignmentAccount.getFinSubObjCd() + "'");
				return valid;
			}
		} 
		if(assignmentAccount.getSubAcctNbr() != null && StringUtils.isNotEmpty(assignmentAccount.getSubAcctNbr())) {
			valid = ValidationUtils.validateSubAccount(assignmentAccount.getSubAcctNbr(),assignmentAccount.getAccountNbr(), assignmentAccount.getFinCoaCd());
			if (!valid) {
				GlobalVariables.getMessageMap().putError("newCollectionLines['document.newMaintainableObject.dataObject.assignmentAccounts'].subAcctNbr", "error.existence", "Sub-Account Number '"+ assignmentAccount.getSubAcctNbr() + "'");
				return valid;
			}
		}
		
		return valid;
	}
    
}
