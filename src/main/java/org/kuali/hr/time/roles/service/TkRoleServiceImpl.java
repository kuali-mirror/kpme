package org.kuali.hr.time.roles.service;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.roles.dao.TkRoleDao;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TkConstants;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TkRoleServiceImpl implements TkRoleService {

	private TkRoleDao tkRoleDao;

	@Override
	public List<TkRole> getDepartmentRoles(String department, Date asOfDate) {
		return tkRoleDao.findRoles(null, asOfDate, null, null, department, null);
	}

	@Override
	public List<TkRole> getDepartmentRoles(String department, String roleName, Date asOfDate) {
		return tkRoleDao.findRoles(null, asOfDate, roleName, null, department, null);
	}

	@Override
	public List<TkRole> getWorkAreaRoles(Long workArea, Date asOfDate) {
		return tkRoleDao.findRoles(null, asOfDate, null, workArea, null, null);
	}

	@Override
	public List<TkRole> getWorkAreaRoles(Long workArea, String roleName, Date asOfDate) {
		return tkRoleDao.findRoles(null, asOfDate, roleName, workArea, null, null);
	}
	
	@Override
	public List<TkRole> getInActiveWorkAreaRoles(Long workArea, String roleName, Date asOfDate) {
		return tkRoleDao.findInActiveRoles(null, asOfDate, roleName, workArea, null, null);
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
		return tkRoleDao.findRoles(principalId, asOfDate, null, null, null, null);
	}

	/**
	 * Return a List of TkRoles that match the principal ID and roleName.
	 *
	 * ex:
	 *
	 * admin,TK_APPROVER will return all TK_APPROVER roles for the user admin.
	 */
	public List<TkRole> getRoles(String principalId, String roleName, Date asOfDate) {
		return this.tkRoleDao.findRoles(principalId, asOfDate, roleName, null, null, null);
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

    @Override
    public List<String> getResponsibleParties(Assignment a, String roleName, Date asOfDate) {
        List<String> users = new ArrayList<String>();

        List<TkRole> roles = this.getWorkAreaRoles(a.getWorkArea(), roleName, asOfDate);
        for (TkRole role: roles) {
            users.add(role.getPrincipalId());
        }

        return users;
    }

}
