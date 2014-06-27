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
package org.kuali.kpme.core.workarea.web;

import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.lookup.KpmeHrGroupKeyedBusinessObjectLookupableImpl;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.workarea.WorkAreaBo;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.lookup.LookupForm;
import org.kuali.rice.krad.lookup.LookupUtils;
import org.kuali.rice.krad.util.GlobalVariables;

import java.util.*;

public class WorkAreaLookupableImpl extends KpmeHrGroupKeyedBusinessObjectLookupableImpl {
    private static final long serialVersionUID = 774015772672806415L;


    protected List<WorkAreaBo> filterLookupResults(List<WorkAreaBo> rawResults, String userPrincipalId) {
        List<WorkAreaBo> results = new ArrayList<WorkAreaBo>();
        for (WorkAreaBo workAreaObj : rawResults) {

            String department = workAreaObj.getDept();
            String groupKeyCode = workAreaObj.getGroupKeyCode();
            Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, groupKeyCode, workAreaObj.getEffectiveLocalDate());
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
                results.add(workAreaObj);
            }
        }

        return results;
    }

    @Override
    protected Collection<?> executeSearch(Map<String, String> searchCriteria, List<String> wildcardAsLiteralSearchCriteria, boolean bounded, Integer searchResultsLimit) {

        String userPrincipalId = GlobalVariables.getUserSession().getPrincipalId();

        List<WorkAreaBo> results = (List<WorkAreaBo>) super.executeSearch(searchCriteria, wildcardAsLiteralSearchCriteria, bounded, searchResultsLimit);
        List<WorkAreaBo> filteredResults = filterLookupResults(results, userPrincipalId);

        return filteredResults;
    }
}