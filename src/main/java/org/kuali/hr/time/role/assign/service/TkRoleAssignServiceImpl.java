package org.kuali.hr.time.role.assign.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.hr.time.role.assign.TkRoleAssign;
import org.kuali.hr.time.role.assign.dao.TkRoleAssignDao;

public class TkRoleAssignServiceImpl implements TkRoleAssignService {
    
    private TkRoleAssignDao tkRoleAssignDao;

    @Override
    public List<TkRoleAssign> getTkRoleAssignments(Long workAreaId) {
	return tkRoleAssignDao.getTkRoleAssignments(workAreaId);
    }
    
    @Override
    public List<TkRoleAssign> getTkRoleAssignments(Long workAreaId, String roleName) {
	return tkRoleAssignDao.getTkRoleAssignments(workAreaId, roleName);
    }
    
    @Override
    public List<TkRoleAssign> getTkRoleAssignments(String principalId) {
	return tkRoleAssignDao.getTkRoleAssignments(principalId);
    }
    
    @Override
    public Map<String, Set<String>> getTkRoleAssignmentsMap(Long workAreaId) {
	Map<String, Set<String>> map = new HashMap<String, Set<String>>();
	List<TkRoleAssign> raList = this.getTkRoleAssignments(workAreaId);
	
	for (TkRoleAssign tra : raList) {
	    Set<String> principals = null;
	    String role = tra.getRoleName();
	    if (map.containsKey(role)) {
		principals = map.get(role);
	    } else {
		principals = new HashSet<String>();
		map.put(role, principals);
	    }
	    principals.add(tra.getPrincipalId());
	}
	
	return map;
    }

    public TkRoleAssignDao getTkRoleAssignDao() {
        return tkRoleAssignDao;
    }

    public void setTkRoleAssignDao(TkRoleAssignDao tkRoleAssignDao) {
        this.tkRoleAssignDao = tkRoleAssignDao;
    }

}
