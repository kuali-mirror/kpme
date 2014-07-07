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
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.assignment.AssignmentBo;
import org.kuali.kpme.core.lookup.KpmeHrGroupKeyedBusinessObjectLookupableImpl;
import org.kuali.kpme.core.position.authorization.PositionBaseAuthorizer;
import org.kuali.kpme.pm.api.position.Position;
import org.kuali.kpme.pm.api.position.PositionContract;
import org.kuali.kpme.pm.api.positiondepartment.PositionDepartmentContract;
import org.kuali.kpme.pm.position.PositionBo;
import org.kuali.kpme.pm.position.authorization.PositionDocumentAuthorizer;
import org.kuali.rice.krad.lookup.LookupForm;
import org.kuali.rice.krad.lookup.LookupView;
import org.kuali.rice.krad.service.DocumentDictionaryService;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.uif.element.Action;
import org.kuali.rice.krad.uif.element.Link;
import org.kuali.rice.krad.util.GlobalVariables;

public class PositionLookupableImpl extends KpmeHrGroupKeyedBusinessObjectLookupableImpl {

	private static final long serialVersionUID = 8658536323175048980L;

    protected List<PositionContract> filterLookupPositions(List<PositionContract> rawResults)
    {
        DocumentDictionaryService documentDictionaryService = KRADServiceLocatorWeb.getDocumentDictionaryService();

        List<PositionContract> results = new ArrayList<PositionContract>();
        for (PositionContract positionObj : rawResults)
        {
            if  ( ((PositionDocumentAuthorizer)(documentDictionaryService.getDocumentAuthorizer(this.getMaintenanceDocumentTypeName()))).canView(positionObj, GlobalVariables.getUserSession().getPerson()) )
            {
                results.add(positionObj);
            }
        }

        return results;
    }
    
    @Override
    protected Collection<?> executeSearch(Map<String, String> searchCriteria, List<String> wildcardAsLiteralSearchCriteria, boolean bounded, Integer searchResultsLimit) {
		List<PositionContract> retVal = new ArrayList<PositionContract>();
		// read and remove the primary department from the field map
		String primaryDepartment = searchCriteria.remove("primaryDepartment");  // KPME-3189
        List<PositionContract> posContracts = (List<PositionContract>) super.executeSearch(searchCriteria, wildcardAsLiteralSearchCriteria, bounded, searchResultsLimit);
		
        if (StringUtils.isEmpty(primaryDepartment)) {
        	retVal = posContracts;
        }
        else {
	        // clean out wildcards from user entered value for primary department - KPME-3189
			// TODO: shouldn't this be doing wildcard based matching (using regexes perhaps) instead?
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
        List<PositionContract> results = filterLookupPositions(retVal);

        return results;
    }


    @Override
    public boolean allowsMaintenanceNewOrCopyAction() {
        return false;
    }

    @Override
    public void buildMaintenanceActionLink(Link actionLink, Object model, String maintenanceMethodToCall) {
        super.buildMaintenanceActionLink(actionLink, model, maintenanceMethodToCall);
        if (StringUtils.equals(actionLink.getLinkText(), "copy"))
        {
            DocumentDictionaryService documentDictionaryService = KRADServiceLocatorWeb.getDocumentDictionaryService();
            Map<String, Object> context = actionLink.getContext();
            PositionBo bo = ((PositionBo)context.get((String)"line"));
            if ( ! (((PositionDocumentAuthorizer)(documentDictionaryService.getDocumentAuthorizer(this.getMaintenanceDocumentTypeName()))).canCopy(bo, GlobalVariables.getUserSession().getPerson()))  )
            {
                actionLink.setRender(false);
                return;
            }
        }
    }
}