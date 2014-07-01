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
package org.kuali.kpme.pm.position.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.lookup.KpmeHrGroupKeyedBusinessObjectLookupableImpl;
import org.kuali.kpme.core.position.authorization.PositionBaseAuthorizer;
import org.kuali.kpme.pm.api.position.PositionContract;
import org.kuali.kpme.pm.api.positiondepartment.PositionDepartmentContract;
import org.kuali.kpme.pm.position.PositionBo;
import org.kuali.kpme.pm.position.authorization.PositionDocumentAuthorizer;
import org.kuali.rice.krad.document.DocumentAuthorizer;
import org.kuali.rice.krad.maintenance.MaintenanceDocumentAuthorizer;
import org.kuali.rice.krad.service.DataDictionaryService;
import org.kuali.rice.krad.service.DataObjectAuthorizationService;
import org.kuali.rice.krad.service.DocumentDictionaryService;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.uif.element.Action;
import org.kuali.rice.krad.uif.view.LookupView;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.LookupForm;

public class PositionLookupableImpl extends KpmeHrGroupKeyedBusinessObjectLookupableImpl {

	private static final long serialVersionUID = 8658536323175048980L;

	@SuppressWarnings("unchecked")
	@Override
    protected List<?> getSearchResults(LookupForm form, Map<String, String> searchCriteria, boolean unbounded) {
		List<PositionContract> retVal = new ArrayList<PositionContract>();
		// read and remove the primary department from the field map
		String primaryDepartment = searchCriteria.remove("primaryDepartment");  // KPME-3189
        List<PositionContract> posContracts = (List<PositionContract>) super.getSearchResults(form, searchCriteria, unbounded);
		
        if (StringUtils.isEmpty(primaryDepartment)) {
        	retVal = posContracts;
        }
        else {
	        // clean out wildcards from user entered value for primary department - KPME-3189
			// TODO: shouldnt this be doing wilcard based matching (using regexes perhaps) instead? 
			primaryDepartment = StringUtils.remove(StringUtils.remove(primaryDepartment, "*"), "%");
			// check for each position if its primary department matches the user entered primary department 
	        for (PositionContract posContract: posContracts) {
	        	List<? extends PositionDepartmentContract> posDepartments = posContract.getDepartmentList();
	        	for (PositionDepartmentContract posDepartment: posDepartments) {
	        		if (posDepartment.getDeptAfflObj().isPrimaryIndicator()) {        			
	        			if (posDepartment.getDepartment().toUpperCase().equals(primaryDepartment.toUpperCase())) {
	        				retVal.add(posContract);
	        				break;
	        			}
	        		}
	        	}
	        }
        }
        return retVal;
    }  
	

    @Override
    public void initSuppressAction(LookupForm lookupForm) {
    /*
     * lookupAuthorizer.canInitiateDocument(lookupForm, user) returns false in this instance, because no
     * documentTypeName can be obtained within LookupViewAuthorizeBase.canInitiateDocument(LookupForm, Person).
     */
        ((LookupView) lookupForm.getView()).setSuppressActions(false);
    }


    @Override
    public void getMaintenanceActionLink(Action actionLink, Object model, String maintenanceMethodToCall)
    {
        super.getMaintenanceActionLink(actionLink, model, maintenanceMethodToCall);
        if (StringUtils.equals(actionLink.getActionLabel(), "copy"))
        {
            DocumentDictionaryService documentDictionaryService = KRADServiceLocatorWeb.getDocumentDictionaryService();
            Map<String, Object> context = actionLink.getContext();
            PositionBo bo = ((PositionBo)context.get((String)"line"));
            if (!(((PositionDocumentAuthorizer)(documentDictionaryService.getDocumentAuthorizer(this.getMaintenanceDocumentTypeName()))).canCopy(bo,GlobalVariables.getUserSession().getPerson())))
            {
                actionLink.setRender(false);
                return;
            }
        }
    }
}