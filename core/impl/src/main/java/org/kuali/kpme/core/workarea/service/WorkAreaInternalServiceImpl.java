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
package org.kuali.kpme.core.workarea.service;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.role.workarea.WorkAreaPositionRoleMemberBo;
import org.kuali.kpme.core.role.workarea.WorkAreaPrincipalRoleMemberBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.service.role.KPMERoleService;
import org.kuali.kpme.core.task.TaskBo;
import org.kuali.kpme.core.workarea.WorkAreaBo;
import org.kuali.kpme.core.workarea.dao.WorkAreaDao;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.role.RoleMember;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.role.RoleMemberBo;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class WorkAreaInternalServiceImpl implements WorkAreaInternalService {
    private WorkAreaDao workAreaDao;
    private KPMERoleService kpmeRoleService;



    @Override
    public WorkAreaBo getWorkArea(String tkWorkAreaId) {
        WorkAreaBo workAreaObj = workAreaDao.getWorkArea(tkWorkAreaId);

        if (workAreaObj != null) {
            populateWorkAreaTasks(workAreaObj);
            populateWorkAreaRoleMembers(workAreaObj, workAreaObj.getEffectiveLocalDate());
        }

        return workAreaObj;
    }

    @Override
    public WorkAreaBo getWorkArea(Long workArea, LocalDate asOfDate) {
        WorkAreaBo workAreaObj = workAreaDao.getWorkArea(workArea, asOfDate);

        if (workAreaObj != null) {
            populateWorkAreaTasks(workAreaObj);
            populateWorkAreaRoleMembers(workAreaObj, asOfDate);
        }

        return workAreaObj;
    }

    @Override
    public List<WorkAreaBo> getWorkAreas(String userPrincipalId, String dept, String workArea, String workAreaDescr, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory) {
        List<WorkAreaBo> results = new ArrayList<WorkAreaBo>();

        List<WorkAreaBo> workAreaObjs = workAreaDao.getWorkAreas(dept, workArea, workAreaDescr, fromEffdt, toEffdt, active, showHistory);

        //TODO - performance
        for (WorkAreaBo workAreaObj : workAreaObjs) {
            String department = workAreaObj.getDept();
            Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, workAreaObj.getEffectiveLocalDate());
            String location = departmentObj != null ? departmentObj.getLocation() : null;

            Map<String, String> roleQualification = new HashMap<String, String>();
            roleQualification.put(KimConstants.AttributeConstants.PRINCIPAL_ID, userPrincipalId);
            roleQualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
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

    protected void populateWorkAreaTasks(WorkAreaBo workArea) {
        workArea.setTasks(ModelObjectUtils.transform(HrServiceLocator.getTaskService().getTasks(null, null, String.valueOf(workArea.getWorkArea()), workArea.getEffectiveLocalDate(), null), TaskBo.toTaskBo));
    }

    protected void populateWorkAreaRoleMembers(WorkAreaBo workArea, LocalDate asOfDate) {
        if (workArea != null && asOfDate != null
                && CollectionUtils.isEmpty(workArea.getPrincipalRoleMembers()) && CollectionUtils.isEmpty(workArea.getInactivePrincipalRoleMembers())
                && CollectionUtils.isEmpty(workArea.getPositionRoleMembers()) && CollectionUtils.isEmpty(workArea.getInactivePositionRoleMembers())) {
            Set<RoleMember> roleMembers = new HashSet<RoleMember>();

            roleMembers.addAll(kpmeRoleService.getPrimaryRoleMembersInWorkArea(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.REVIEWER.getRoleName(), workArea.getWorkArea(), asOfDate.toDateTimeAtStartOfDay(), false));
            roleMembers.addAll(kpmeRoleService.getPrimaryRoleMembersInWorkArea(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), workArea.getWorkArea(), asOfDate.toDateTimeAtStartOfDay(), false));
            roleMembers.addAll(kpmeRoleService.getPrimaryRoleMembersInWorkArea(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER_DELEGATE.getRoleName(), workArea.getWorkArea(), asOfDate.toDateTimeAtStartOfDay(), false));

            for (RoleMember roleMember : roleMembers) {
                RoleMemberBo roleMemberBo = RoleMemberBo.from(roleMember);
                // position role
                if(roleMember.getAttributes().containsKey(KPMERoleMemberAttribute.POSITION.getRoleMemberAttributeName())) {
                    if (roleMemberBo.isActive()) {
                        workArea.addPositionRoleMember(WorkAreaPositionRoleMemberBo.from(roleMemberBo, roleMember.getAttributes()));
                    } else {
                        workArea.addInactivePositionRoleMember(WorkAreaPositionRoleMemberBo.from(roleMemberBo, roleMember.getAttributes()));
                    }
                } else {
                    if (roleMemberBo.isActive()) {
                        workArea.addPrincipalRoleMember(WorkAreaPrincipalRoleMemberBo.from(roleMemberBo, roleMember.getAttributes()));
                    } else {
                        workArea.addInactivePrincipalRoleMember(WorkAreaPrincipalRoleMemberBo.from(roleMemberBo, roleMember.getAttributes()));
                    }
                }
            }
        }
    }

    public void setWorkAreaDao(WorkAreaDao workAreaDao) {
        this.workAreaDao = workAreaDao;
    }
    public void setKpmeRoleService(KPMERoleService kpmeRoleService) {
        this.kpmeRoleService = kpmeRoleService;
    }
}
