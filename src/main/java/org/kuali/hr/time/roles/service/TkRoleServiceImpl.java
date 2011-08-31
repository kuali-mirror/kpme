package org.kuali.hr.time.roles.service;

import org.apache.log4j.Logger;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.roles.dao.TkRoleDao;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TkConstants;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TkRoleServiceImpl implements TkRoleService {

    private static final Logger LOG = Logger.getLogger(TkRoleServiceImpl.class);

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
	 * Returns all active roles for the given principal as of the indi
	 */
	public List<TkRole> getInActiveRoles(String principalId, Date asOfDate) {
		return tkRoleDao.findInActiveRoles(principalId, asOfDate, null, null, null, null);
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

	public List<TKUser> getEmployeesForWorkArea(Long workArea, Date asOfDate){
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

    @Override
    public Set<Long> getWorkAreasForApprover(String principalId, Date asOfDate) {
        Set<Long> workAreas = new HashSet<Long>();

        List<TkRole> roles = this.getRoles(principalId, TkConstants.ROLE_TK_APPROVER, asOfDate);
        for (TkRole role : roles) {
            Long wa = role.getWorkArea();
            if (wa != null)
                workAreas.add(wa);
            else
                LOG.warn(TkConstants.ROLE_TK_APPROVER + " found without WorkArea number, ignoring roleId: " + role.getHrRolesId());
        }

        return workAreas;
    }



    @Override
    public Set<String> getActivePrinciaplsForWorkAreas(Set<Long> workAreas, Date asOfDate) {
        Set<String> principals = new HashSet<String>();

        for (Long workArea : workAreas) {
            List<Assignment> assignments = TkServiceLocator.getAssignmentService().getActiveAssignmentsForWorkArea(workArea, asOfDate);
            for (Assignment assignment : assignments) {
                principals.add(assignment.getPrincipalId());
            }
        }

        return principals;
    }

	@Override
	public TkRole getRole(Long tkRoleId) {
		return tkRoleDao.getRole(tkRoleId);
	}
	
	@Override
	public List<TkRole> getRolesByPosition(String positionNumber) {
		return tkRoleDao.getRolesByPosition(positionNumber);
	}

	@Override
	public List<TkRole> getPositionRolesForWorkArea(Long workArea, Date asOfDate) {
		return tkRoleDao.getPositionRolesForWorkArea(workArea, asOfDate);
	}
}
