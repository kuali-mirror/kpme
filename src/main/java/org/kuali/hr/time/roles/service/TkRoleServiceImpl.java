package org.kuali.hr.time.roles.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.roles.dao.TkRoleDao;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUser;

public class TkRoleServiceImpl implements TkRoleService {

	private TkRoleDao tkRoleDao;
	
	@Override
	public List<TkRole> getDepartmentRoles(String department, Date asOfDate) {
		return tkRoleDao.findRoles(null, department, null, asOfDate, null);
	}

	@Override
	public List<TkRole> getDepartmentRoles(String department, String roleName, Date asOfDate) {
		return tkRoleDao.findRoles(null, department, roleName, asOfDate, null);
	}

	@Override
	public List<TkRole> getWorkAreaRoles(Long workArea, Date asOfDate) {
		return tkRoleDao.findRoles(workArea, null, null, asOfDate, null);
	}

	@Override
	public List<TkRole> getWorkAreaRoles(Long workArea, String roleName, Date asOfDate) {
		return tkRoleDao.findRoles(workArea, null, roleName, asOfDate, null);
	}

	public void setTkRoleDao(TkRoleDao tkRoleDao) {
		this.tkRoleDao = tkRoleDao;
	}

	@Override
	public void saveOrUpdate(List<TkRole> roles) {
		this.tkRoleDao.saveOrUpdateRoles(roles);
	}

	@Override
	public void saveOrUpdate(TkRole role) {
		this.tkRoleDao.saveOrUpdateRole(role);
	}
	
	/**
	 * Returns all active roles for the given principal as of the indi
	 */
	public List<TkRole> getRoles(String principalId, Date asOfDate) {
		return this.tkRoleDao.findRoles(null, null, null, asOfDate, principalId);
	}
	
	/**
	 * Return a List of TkRoles that match the principal ID and roleName.
	 * 
	 * ex:
	 * 
	 * admin,TK_APPROVER will return all TK_APPROVER roles for the user admin.
	 */
	public List<TkRole> getRoles(String principalId, String roleName, Date asOfDate) {
		return this.tkRoleDao.findRoles(null, null, roleName, asOfDate, principalId);
	}
	
	public List<TKUser> getEmployeesForWorkArea(String workArea, Date asOfDate){
		List<TKUser> lstEmployees = new ArrayList<TKUser>();
		List<Assignment> lstActiveAssignments = TkServiceLocator.getAssignmentService().getActiveAssignmentsForWorkArea(workArea, asOfDate);
		for(Assignment assign: lstActiveAssignments){
			TKUser tkUser = TkServiceLocator.getUserService().buildTkUser(assign.getPrincipalId(), assign.getEffectiveDate());
			lstEmployees.add(tkUser);
		}
		return lstEmployees;
	}

}
