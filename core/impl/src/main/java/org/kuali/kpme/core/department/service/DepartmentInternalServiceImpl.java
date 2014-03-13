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
package org.kuali.kpme.core.department.service;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.department.DepartmentBo;
import org.kuali.kpme.core.department.dao.DepartmentDao;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.role.department.DepartmentPrincipalRoleMemberBo;
import org.kuali.kpme.core.service.role.KPMERoleService;
import org.kuali.rice.kim.api.role.RoleMember;
import org.kuali.rice.kim.impl.role.RoleMemberBo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class DepartmentInternalServiceImpl implements DepartmentInternalService {

    private DepartmentDao departmentDao;
    private KPMERoleService kpmeRoleService;

    @Override
    public DepartmentBo getDepartmentWithRoleData(String hrDeptId) {
        DepartmentBo departmentObj = departmentDao.getDepartment(hrDeptId);

        if (departmentObj != null) {
            populateDepartmentRoleMembers(departmentObj, departmentObj.getEffectiveLocalDate());
        }

        return departmentObj;
    }

    private void populateDepartmentRoleMembers(DepartmentBo department, LocalDate asOfDate) {
        if (department != null && asOfDate != null
                && CollectionUtils.isEmpty(department.getRoleMembers()) && CollectionUtils.isEmpty(department.getInactiveRoleMembers())) {
            Set<RoleMember> roleMembers = new HashSet<RoleMember>();

            roleMembers.addAll(kpmeRoleService.getRoleMembersInDepartment(KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_DEPARTMENT_VIEW_ONLY.getRoleName(), department.getDept(), asOfDate.toDateTimeAtStartOfDay(), false));
            roleMembers.addAll(kpmeRoleService.getRoleMembersInDepartment(KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), department.getDept(), asOfDate.toDateTimeAtStartOfDay(), false));
            roleMembers.addAll(kpmeRoleService.getRoleMembersInDepartment(KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_DEPARTMENT_VIEW_ONLY.getRoleName(), department.getDept(), asOfDate.toDateTimeAtStartOfDay(), false));
            roleMembers.addAll(kpmeRoleService.getRoleMembersInDepartment(KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), department.getDept(), asOfDate.toDateTimeAtStartOfDay(), false));

            roleMembers.addAll(kpmeRoleService.getRoleMembersInDepartment(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR.getRoleName(), department.getDept(), asOfDate.toDateTimeAtStartOfDay(), false));
            roleMembers.addAll(kpmeRoleService.getRoleMembersInDepartment(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR_DELEGATE.getRoleName(), department.getDept(), asOfDate.toDateTimeAtStartOfDay(), false));

            for (RoleMember roleMember : roleMembers) {
                RoleMemberBo roleMemberBo = RoleMemberBo.from(roleMember);

                if (roleMemberBo.isActive()) {
                    department.addRoleMember(DepartmentPrincipalRoleMemberBo.from(roleMemberBo, roleMember.getAttributes()));
                } else {
                    department.addInactiveRoleMember(DepartmentPrincipalRoleMemberBo.from(roleMemberBo, roleMember.getAttributes()));
                }
            }
        }
    }

    @Override
    public List<DepartmentBo> getDepartmentsWithRoleData(String location, LocalDate asOfDate) {
        List<DepartmentBo> departmentObjs = departmentDao.getDepartments(location, asOfDate);

        for (DepartmentBo departmentObj : departmentObjs) {
            populateDepartmentRoleMembers(departmentObj, departmentObj.getEffectiveLocalDate());
        }

        return departmentObjs;
    }

    @Override
    public DepartmentBo getDepartmentWithRoleData(String department, String location, LocalDate asOfDate) {
        DepartmentBo departmentObj = departmentDao.getDepartment(department, location, asOfDate);

        if (departmentObj != null) {
            populateDepartmentRoleMembers(departmentObj, asOfDate);
        }

        return departmentObj;
    }

    @Override
    public DepartmentBo getDepartmentWithRoleData(String department, LocalDate asOfDate) {
        DepartmentBo departmentObj = departmentDao.getDepartment(department, asOfDate);

        if (departmentObj != null) {
            populateDepartmentRoleMembers(departmentObj, asOfDate);
        }

        return departmentObj;
    }

    public void setDepartmentDao(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    public void setKpmeRoleService(KPMERoleService kpmeRoleService) {
        this.kpmeRoleService = kpmeRoleService;
    }


}
