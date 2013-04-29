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
package org.kuali.kpme.core.bo.assignment.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrBusinessObjectMaintainableImpl;
import org.kuali.kpme.core.bo.assignment.Assignment;
import org.kuali.kpme.core.bo.assignment.account.AssignmentAccount;
import org.kuali.kpme.core.bo.job.Job;
import org.kuali.kpme.core.bo.kfs.coa.businessobject.Account;
import org.kuali.kpme.core.bo.paytype.PayType;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.service.KRADServiceLocator;

/**
 * Override the Maintenance page behavior for Assignment object
 * 
 * 
 */
public class AssignmentMaintainableServiceImpl extends HrBusinessObjectMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Map populateBusinessObject(Map<String, String> fieldValues,
			MaintenanceDocument maintenanceDocument, String methodToCall) {
		if (fieldValues.containsKey("principalId")
				&& StringUtils.isNotEmpty(fieldValues.get("principalId"))) {
            EntityNamePrincipalName name = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(fieldValues.get("principalId"));
			if (name != null) {
				fieldValues.put("name", name.getDefaultName().getCompositeName());
			} else {
				fieldValues.put("name", "");
			}
		}

        // KPME-1139
		// Commented due to KPME-1226 
      /* if (!fieldValues.containsKey("effectiveDate")) {
            fieldValues.put("effectiveDate", new DateTime().toString(TkConstants.DT_BASIC_DATE_FORMAT));
        }*/
       
		return super.populateBusinessObject(fieldValues, maintenanceDocument,
				methodToCall);
	}

	@Override
	public Map<String, String> populateNewCollectionLines(
			Map<String, String> fieldValues,
			MaintenanceDocument maintenanceDocument, String methodToCall) {
		if (fieldValues.containsKey("assignmentAccounts.accountNbr")
				&& StringUtils.isNotEmpty(fieldValues
						.get("assignmentAccounts.accountNbr"))) {
			Map<String, String> fields = new HashMap<String, String>();
			fields.put("accountNumber", fieldValues
					.get("assignmentAccounts.accountNbr"));
			Collection account = KRADServiceLocator.getBusinessObjectService()
					.findMatching(Account.class, fields);
			if (account.size() > 0) {
				Account acc = (Account) account.iterator().next();
				fieldValues.put("assignmentAccounts.finCoaCd", acc
						.getChartOfAccountsCode());
			}
		}
		if ( !fieldValues.containsKey("assignmentAccounts.earnCode")
				|| StringUtils.isEmpty(fieldValues.get("assignmentAccounts.earnCode"))) {
			Assignment assignment = (Assignment) maintenanceDocument.getDocumentBusinessObject();
			if(assignment != null 
				&& assignment.getPrincipalId() != null 
				&& assignment.getJobNumber() != null 
				&& assignment.getEffectiveDate() != null) {
			  Job job = HrServiceLocator.getJobService().getJob(assignment.getPrincipalId(), assignment.getJobNumber(), assignment.getEffectiveLocalDate(), false);
			  if(job != null) {
					PayType payType = HrServiceLocator.getPayTypeService().getPayType(job.getHrPayType(), assignment.getEffectiveLocalDate());
					fieldValues.put("assignmentAccounts.earnCode", (payType != null) ? payType.getRegEarnCode() : "");
				}
			}
		}

		return super.populateNewCollectionLines(fieldValues,
				maintenanceDocument, methodToCall);
	}

	@Override
	public void processAfterEdit(MaintenanceDocument document,
			Map<String, String[]> parameters) {
		super.processAfterEdit(document, parameters);
	}


    @Override
	protected void setNewCollectionLineDefaultValues(String arg0,
			PersistableBusinessObject arg1) {
    	if(arg1 instanceof AssignmentAccount){
    		AssignmentAccount assignmentAccount = (AssignmentAccount)arg1;
    		Assignment assignment = (Assignment) this.getBusinessObject();
    		assignmentAccount.setActive(assignment.isActive());
    	}
		super.setNewCollectionLineDefaultValues(arg0, arg1);
	}

	@Override
	public HrBusinessObject getObjectById(String id) {
		return HrServiceLocator.getAssignmentService().getAssignment(id);
	}

	@Override
	public void customSaveLogic(HrBusinessObject hrObj) {
		Assignment assignment = (Assignment)hrObj;
		for (AssignmentAccount assignAcct : assignment.getAssignmentAccounts()) {
			if(!isOldBusinessObjectInDocument()){ //prevents duplicate object on edit
				assignAcct.setTkAssignAcctId(null);
			}
			assignAcct.setTkAssignmentId(assignment.getTkAssignmentId());
		}
	}

}
