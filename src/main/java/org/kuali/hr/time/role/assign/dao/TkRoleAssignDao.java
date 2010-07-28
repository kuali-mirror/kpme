package org.kuali.hr.time.role.assign.dao;

import java.util.List;

import org.kuali.hr.time.role.assign.TkRoleAssign;

public interface TkRoleAssignDao {

    public List<TkRoleAssign> getTkRoleAssignments(String principalId);
    public List<TkRoleAssign> getTkRoleAssignments(Long workAreaId);
    public List<TkRoleAssign> getTkRoleAssignments(Long workAreaId, String role);
}
