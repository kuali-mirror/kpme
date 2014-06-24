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
package org.kuali.kpme.tklm.time.clocklog.web;

import java.util.*;

import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.lookup.KPMELookupableImpl;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.workarea.WorkAreaBo;
import org.kuali.kpme.tklm.time.clocklog.ClockLogBo;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.lookup.LookupUtils;
import org.kuali.rice.krad.uif.view.LookupView;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.LookupForm;

public class ClockLogLookupableImpl extends KPMELookupableImpl {

	@Override
	public void initSuppressAction(LookupForm lookupForm) {
		((LookupView) lookupForm.getView()).setSuppressActions(false);
	}

	@Override
	public List<?> getSearchResults(LookupForm form, Map<String, String> searchCriteria, boolean unbounded) {
        String userPrincipalId = GlobalVariables.getUserSession().getPrincipalId();

        Integer searchResultsLimit = null;

        Collection<?> rawSearchResults;

        // removed blank search values and decrypt any encrypted search values
        Map<String, String> nonBlankSearchCriteria = processSearchCriteria(form, searchCriteria);

        if (nonBlankSearchCriteria == null) {
            return new ArrayList<Object>();
        }

        if (!unbounded) {
            searchResultsLimit = LookupUtils.getSearchResultsLimit(getDataObjectClass(), form);
        }

        rawSearchResults = getLookupService().findCollectionBySearchHelper(getDataObjectClass(),
                nonBlankSearchCriteria, unbounded, searchResultsLimit);

        if (rawSearchResults == null) {
            rawSearchResults = new ArrayList<Object>();
        } else {
            sortSearchResults(form, (List<?>) rawSearchResults);
        }

        List<ClockLogBo> filteredResults = filterLookupResults((List<ClockLogBo>)rawSearchResults, userPrincipalId);

        generateLookupResultsMessages(form, nonBlankSearchCriteria, filteredResults, unbounded);

		for (ClockLogBo searchResult : filteredResults) {
			if(searchResult != null) {
                searchResult.setClockedByMissedPunch(TkServiceLocator.getClockLogService().isClockLogCreatedByMissedPunch(searchResult.getTkClockLogId()));
			}
		}

		return filteredResults;
	}

    protected List<ClockLogBo> filterLookupResults(List<ClockLogBo> rawResults, String userPrincipalId) {
        List<ClockLogBo> results = new ArrayList<ClockLogBo>();
        for (ClockLogBo clockLogObj : rawResults) {


            String department = clockLogObj.getDept();
            String groupKeyCode = clockLogObj.getGroupKeyCode();
            Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, groupKeyCode, clockLogObj.getClockDateTime().toLocalDate());
            String location = departmentObj != null ? departmentObj.getGroupKey().getLocationId() : null;

            Map<String, String> roleQualification = new HashMap<String, String>();

            roleQualification.put(KimConstants.AttributeConstants.PRINCIPAL_ID, userPrincipalId);
            roleQualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
            roleQualification.put(KPMERoleMemberAttribute.GROUP_KEY_CODE.getRoleMemberAttributeName(), groupKeyCode);
            roleQualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);

            if (!KimApiServiceLocator.getPermissionService().isPermissionDefinedByTemplate(KPMENamespace.KPME_WKFLW.getNamespaceCode(),
                    KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>())
                    || KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(userPrincipalId, KPMENamespace.KPME_WKFLW.getNamespaceCode(),
                    KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>(), roleQualification)) {
                results.add(clockLogObj);
            }
        }

        return results;
    }

}