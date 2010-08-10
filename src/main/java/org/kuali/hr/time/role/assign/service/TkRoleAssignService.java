package org.kuali.hr.time.role.assign.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.hr.time.role.assign.TkRoleAssign;

public interface TkRoleAssignService {

    public List<TkRoleAssign> getTkRoleAssignments(Long workAreaId);
    public List<TkRoleAssign> getTkRoleAssignments(String principalId);
    public List<TkRoleAssign> getTkRoleAssignments(Long workAreaId, String roleName);
    
    /** Role Name to Set of PrincipalID mapping */
    public Map<String, Set<String>> getTkRoleAssignmentsMap(Long workAreaId);
}
