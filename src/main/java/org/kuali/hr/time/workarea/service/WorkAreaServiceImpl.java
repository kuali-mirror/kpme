package org.kuali.hr.time.workarea.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.hr.time.role.assign.TkRoleAssign;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.hr.time.workarea.dao.WorkAreaDao;
import org.kuali.rice.kim.bo.role.dto.KimRoleInfo;
import org.kuali.rice.kim.bo.role.dto.RoleMembershipInfo;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kim.service.RoleManagementService;

public class WorkAreaServiceImpl implements WorkAreaService {

	private static final Logger LOG = Logger.getLogger(WorkAreaServiceImpl.class);
	
	private WorkAreaDao workAreaDao;
	private static List<String> roleIds = new ArrayList<String>(TkConstants.ROLE_ASSIGNMENT_FOR_WORK_AREA.size());
	static {
		for(String role : TkConstants.ROLE_ASSIGNMENT_FOR_WORK_AREA) {
			roleIds.add(KIMServiceLocator.getRoleService().getRoleIdByName(TkConstants.ROLE_NAMESAPCE, role));
		}
	}
	
	public WorkAreaServiceImpl() {
	}

	@Override
	public WorkArea getWorkArea(Long workAreaId) {
		return workAreaDao.getWorkArea(workAreaId);
	}

	@Override
	public void saveOrUpdate(WorkArea workArea) {
		workAreaDao.saveOrUpdate(workArea);
	}

	public WorkAreaDao getWorkAreaDao() {
		return workAreaDao;
	}

	public void setWorkAreaDao(WorkAreaDao workAreaDao) {
		this.workAreaDao = workAreaDao;
	}

	@Override
	public List<TkRoleAssign> getWorkAreaRoles(Long workArea) {
		List<TkRoleAssign> traList = new ArrayList<TkRoleAssign>();
		AttributeSet qualifiers = new AttributeSet();
		
		// TODO : Probalby need to either load the latest WorkArea, or link these based on the workArea name vs ID.
		Long workAreaId = null;
		// ^^ hackity hack fix this
		
		qualifiers.put(TkConstants.ROLE_WORK_AREA_QUALIFIER_ID, ""+workAreaId);
		List<RoleMembershipInfo> members = KIMServiceLocator.getRoleService().getRoleMembers(roleIds, qualifiers);
		for (RoleMembershipInfo rmi : members) {
			KimRoleInfo kri = KIMServiceLocator.getRoleService().getRole(rmi.getRoleId());
			String roleName = kri.getRoleName();
			String principalId = rmi.getMemberId();
			TkRoleAssign tra = new TkRoleAssign();
			tra.setRoleName(roleName);
			tra.setPrincipalId(principalId);
			tra.setWorkAreaId(workAreaId);
			traList.add(tra);
		}
		return traList;
	}

	@Override
	public void saveWorkAreaRoles(Long workAreaId, List<TkRoleAssign> newRoleList) {
		List<TkRoleAssign> currentRoles = getWorkAreaRoles(workAreaId);
		RoleManagementService rms = KIMServiceLocator.getRoleManagementService();
		for (TkRoleAssign ra : newRoleList) {
			if (!currentRoles.contains(ra)) {
				// add role
				LOG.debug("Adding role: " + ra);
				AttributeSet qual = new AttributeSet();
				qual.put(TkConstants.ROLE_WORK_AREA_QUALIFIER_ID, ""+workAreaId);
				rms.assignPrincipalToRole(ra.getPrincipalId(), TkConstants.ROLE_NAMESAPCE, ra.getRoleName(), qual);
				rms.flushRoleMemberCaches();
			}
		}
		
		for (TkRoleAssign ra : currentRoles) {
			if (!newRoleList.contains(ra)) {
				// remove role
				LOG.debug("Removing role: " + ra);
				AttributeSet qual = new AttributeSet();
				qual.put(TkConstants.ROLE_WORK_AREA_QUALIFIER_ID, ""+ra.getWorkAreaId());
				rms.removePrincipalFromRole(ra.getPrincipalId(), TkConstants.ROLE_NAMESAPCE, ra.getRoleName(), qual);
				rms.flushRoleMemberCaches();
			}
		}
	}
}
