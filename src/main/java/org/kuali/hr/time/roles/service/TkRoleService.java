package org.kuali.hr.time.roles.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.roles.TkRole;

public interface TkRoleService {

	public List<TkRole> getRoles(String principalId, Date asOfDate);
	public List<TkRole> getRoles(String principalId, String roleName, Date asOfDate);
	
	public List<TkRole> getWorkAreaRoles(Long workArea, String roleName, Date asOfDate);
	public List<TkRole> getWorkAreaRoles(Long workArea, Date asOfDate);
	
	public List<TkRole> getDepartmentRoles(String department, String roleName, Date asOfDate);
	public List<TkRole> getDepartmentRoles(String department, Date asOfDate);
	
	public void saveOrUpdate(TkRole role);
	public void saveOrUpdate(List<TkRole> roles);
}
