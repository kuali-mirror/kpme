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
package org.kuali.kpme.edo.candidate.maintenance;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrBusinessObjectMaintainableImpl;
import org.kuali.kpme.edo.candidate.EdoCandidateBo;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

public class EdoCandidateMaintainableImpl extends HrBusinessObjectMaintainableImpl {
	
	private static final long serialVersionUID = 1L;

	@Override
	public HrBusinessObject getObjectById(String id) {
		return EdoCandidateBo.from(EdoServiceLocator.getCandidateService().getCandidate(id));
	}
	
	//attribute query, populates firstName and lastName when principalName is selected, for new edoCandidate
    public EdoCandidateBo getEdoCandidateInfo(String principalName) {
    	EdoCandidateBo aNewEdoCandidateBo = new EdoCandidateBo();

    	Person person = KimApiServiceLocator.getPersonService().getPersonByPrincipalName(principalName);
    	
        if (person != null && person.getFirstName() != null) {
        	aNewEdoCandidateBo.setFirstName(person.getFirstName());
        }else{
        	aNewEdoCandidateBo.setFirstName("");
        }

        if (person != null && person.getLastName() != null) {
        	aNewEdoCandidateBo.setLastName(person.getLastName());
        }else{
        	aNewEdoCandidateBo.setLastName("");
        }
        
        if (person != null && person.getPrimaryDepartmentCode() != null) {
        	aNewEdoCandidateBo.setPrimaryDeptID(person.getPrimaryDepartmentCode());
        }else{
        	aNewEdoCandidateBo.setPrimaryDeptID("");
        }

        if (person != null && person.getPrimaryDepartmentCode() != null) {
        	aNewEdoCandidateBo.setTnpDeptID(person.getPrimaryDepartmentCode());
        }else{
        	aNewEdoCandidateBo.setTnpDeptID("");
        }
        
        return aNewEdoCandidateBo;
    }

}
