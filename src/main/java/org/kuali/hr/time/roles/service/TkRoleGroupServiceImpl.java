package org.kuali.hr.time.roles.service;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.roles.TkRoleGroup;
import org.kuali.hr.time.roles.dao.TkRoleGroupDao;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;

public class TkRoleGroupServiceImpl implements TkRoleGroupService {

    private static final Logger LOG = Logger.getLogger(TkRoleGroupServiceImpl.class);

	private TkRoleGroupDao tkRoleGroupDao;

	public void setTkRoleGroupDao(TkRoleGroupDao tkRoleGroupDao) {
		this.tkRoleGroupDao = tkRoleGroupDao;
	}

	@Override
	public void saveOrUpdate(List<TkRoleGroup> roleGroups) {
		this.tkRoleGroupDao.saveOrUpdateRoleGroups(roleGroups);
	}

	@Override
	public void saveOrUpdate(TkRoleGroup roleGroup) {
		this.tkRoleGroupDao.saveOrUpdateRoleGroup(roleGroup);
	}

	@Override
	public TkRoleGroup getRoleGroup(String principalId) {
		return tkRoleGroupDao.getRoleGroup(principalId);
	}

	@Override
	public void populateRoles(TkRoleGroup tkRoleGroup) {
		if (tkRoleGroup != null) {
			List<TkRole> tkRoles = TkServiceLocator.getTkRoleService().getRoles(tkRoleGroup.getPrincipalId(), TKUtils.getCurrentDate());
			List<TkRole> tkInActiveRoles = TkServiceLocator.getTkRoleService().getInActiveRoles(tkRoleGroup.getPrincipalId(), TKUtils.getCurrentDate());
			Iterator<TkRole> itr = tkRoles.iterator();
			while (itr.hasNext()) {
				TkRole tkRole = (TkRole) itr.next();
				if(tkRoleGroup.getPositionRoles()!=null && tkRoleGroup.getPositionRoles().contains(tkRole)){
					itr.remove();
				}
			}
			itr = tkInActiveRoles.iterator();
			while (itr.hasNext()) {
				TkRole tkRole = (TkRole) itr.next();
				if(tkRoleGroup.getPositionRoles()!=null && tkRoleGroup.getPositionRoles().contains(tkRole)){
					itr.remove();
				}
			}
			tkRoleGroup.setRoles(tkRoles);
			tkRoleGroup.setInactiveRoles(tkInActiveRoles);
		}
	}
}
