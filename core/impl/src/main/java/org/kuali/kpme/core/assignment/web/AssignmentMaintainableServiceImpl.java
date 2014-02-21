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

import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.assignment.account.AssignmentAccount;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrDataObjectMaintainableImpl;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

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
		return (HrBusinessObject)HrServiceLocator.getAssignmentService().getAssignment(id);
	}

	@Override
	public void customSaveLogic(HrBusinessObject hrObj) {
		Assignment assignment = (Assignment)hrObj;
		for (AssignmentAccount assignAcct : assignment.getAssignmentAccounts()) {
			if(!isOldDataObjectInDocument()){ //prevents duplicate object on edit
				assignAcct.setTkAssignAcctId(null);
			}
			assignAcct.setTkAssignmentId(assignment.getTkAssignmentId());

		}
	}

    //KPME-2624 added logic to save current logged in user to UserPrincipal id for collections
    @Override
    public void prepareForSave() {
    Assignment assignment = (Assignment)this.getDataObject();
        for (AssignmentAccount assignAcct : assignment.getAssignmentAccounts()) {
            assignAcct.setUserPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
        }
        super.prepareForSave();
    }

}
