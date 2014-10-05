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

import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.lookup.KPMELookupableImpl;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.tklm.time.clocklog.ClockLogBo;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

import java.util.*;

public class ClockLogLookupableImpl extends KPMELookupableImpl {


	@Override
    public boolean allowsMaintenanceNewOrCopyAction() {
        return false;
	}

    @Override
    public boolean allowsMaintenanceEditAction(Object dataObject) {
        return false;
    }

    @Override
    public boolean allowsMaintenanceDeleteAction(Object dataObject) {
        return false;
    }

    @Override
    protected Collection<?> executeSearch(Map<String, String> searchCriteria, List<String> wildcardAsLiteralSearchCriteria, boolean bounded, Integer searchResultsLimit) {

        List<ClockLogBo> results = new ArrayList<ClockLogBo>();
        Collection<?> searchResults = super.executeSearch(searchCriteria, wildcardAsLiteralSearchCriteria, bounded, searchResultsLimit);
        for (Object searchResult : searchResults) {
            if(searchResult != null) {
                ClockLogBo aClockLog = (ClockLogBo) searchResult;
                aClockLog.setClockedByMissedPunch(TkServiceLocator.getClockLogService().isClockLogCreatedByMissedPunch(aClockLog.getTkClockLogId()));
                results.add(aClockLog);
            }
        }

        return filterLookupResults(results, HrContext.getPrincipalId()) ;
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