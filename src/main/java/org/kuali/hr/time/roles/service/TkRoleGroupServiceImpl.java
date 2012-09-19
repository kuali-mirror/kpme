package org.kuali.hr.time.roles.service;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.roles.TkRoleGroup;
import org.kuali.hr.time.roles.dao.TkRoleGroupDao;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
            List<TkRole> tkInActiveRoles = TkServiceLocator.getTkRoleService().getInactiveRoles(tkRoleGroup.getPrincipalId(), TKUtils.getCurrentDate());
            Iterator<TkRole> itr = tkRoles.iterator();
            while (itr.hasNext()) {
                TkRole tkRole = (TkRole) itr.next();
                if (tkRoleGroup.getPositionRoles() != null && tkRoleGroup.getPositionRoles().contains(tkRole)) {
                    itr.remove();
                }
            }
            itr = tkInActiveRoles.iterator();
            while (itr.hasNext()) {
                TkRole tkRole = (TkRole) itr.next();
                if (tkRoleGroup.getPositionRoles() != null && tkRoleGroup.getPositionRoles().contains(tkRole)) {
                    itr.remove();
                }
            }
            tkRoleGroup.setRoles(tkRoles);
            tkRoleGroup.setInactiveRoles(tkInActiveRoles);
        }
    }

    @Override
    public List<TkRoleGroup> getRoleGroups(String principalId, String principalName, String workArea, String dept, String roleName) {

        List<TkRoleGroup> tkRoleGroups = new ArrayList<TkRoleGroup>();
        String principalIdToQuery = "";
        /**
         * There are three different wasys to search for the roles :
         * 1) through principalId
         * 2) through principalName
         * 3) search for all the roles / role groups
         */
        if (StringUtils.isNotBlank(principalId)) {
            Person person = KimApiServiceLocator.getPersonService().getPerson(principalId);
            if (person != null && isAuthorizedToEditUserRole(person.getPrincipalId())) {
                principalIdToQuery = person.getPrincipalId();
            } else {
            	principalIdToQuery = principalId;
            }
        } else if (StringUtils.isNotBlank(principalName)) {
            Person person = KimApiServiceLocator.getPersonService().getPersonByPrincipalName(principalName);
            if (person != null && isAuthorizedToEditUserRole(person.getPrincipalId())) {
                principalIdToQuery = person.getPrincipalId();
            } else {
            	principalIdToQuery = null;
            }
        } else {

        }

        Long workAreaToQuery = StringUtils.isEmpty(workArea) ? null : Long.parseLong(workArea);
        if(principalIdToQuery != null) {
	        List<TkRole> tkRoles  = TkServiceLocator.getTkRoleService().getRoles(principalIdToQuery, TKUtils.getCurrentDate(), roleName, workAreaToQuery, dept);
	
	        for (TkRole tkRole : tkRoles) {
	        	if (StringUtils.isEmpty(tkRole.getPositionNumber())) {
	        		TkRoleGroup tkRoleGroup = new TkRoleGroup();
	            	if (isAuthorizedToEditUserRole(tkRole.getPrincipalId())) {
	            		tkRoleGroup.setPerson(tkRole.getPerson());
	                	tkRoleGroup.setPrincipalId(tkRole.getPrincipalId());
	                	tkRoleGroups.add(tkRoleGroup);
	            	}
	            	if (StringUtils.isNotEmpty(principalIdToQuery)) {
	            		break;
	            	}
	        	} else {
	        		List<Job> listRolePositionActiveJobs = TkServiceLocator.getJobService().getActiveJobsForPosition(tkRole.getPositionNumber(), TKUtils.getCurrentDate());
	        		for (Job rolePositionJob : listRolePositionActiveJobs) {
	        			String rolePositionJobPrincipalId = rolePositionJob.getPrincipalId();
	        			TkRoleGroup tkRoleGroup = new TkRoleGroup();
	        			if (isAuthorizedToEditUserRole(rolePositionJobPrincipalId)) {
	        				if (((StringUtils.isNotEmpty(dept) && StringUtils.equals(tkRole.getDepartment(), dept)) || StringUtils.isEmpty(dept)) &&
	            				((StringUtils.isNotEmpty(roleName) && StringUtils.equals(tkRole.getRoleName(), roleName)) || StringUtils.isEmpty(roleName)) &&
	            				((StringUtils.isNotEmpty(workArea) && StringUtils.equals(tkRole.getWorkArea().toString(), workArea)) || StringUtils.isEmpty(workArea)) ) {
	        						tkRoleGroup.setPerson(KimApiServiceLocator.getPersonService().getPerson(rolePositionJobPrincipalId));
	        						tkRoleGroup.setPrincipalId(rolePositionJobPrincipalId);
	        						tkRoleGroups.add(tkRoleGroup);
	        				}
	        			}
	        		}
	        	}
	        }
        }
        return tkRoleGroups;
    }

    private boolean isAuthorizedToEditUserRole(String principalId) {
        boolean isAuthorized = false;
        //System admin can do anything
        if (TKContext.getUser().isSystemAdmin()) {
            return true;
        }

        List<Job> lstJobs = TkServiceLocator.getJobService().getJobs(principalId, TKUtils.getCurrentDate());
        Set<String> locationAdminAreas = TKContext.getUser().getLocationAdminAreas();
        //Confirm if any job matches this users location admin roles
        for (String location : locationAdminAreas) {
            for (Job job : lstJobs) {
                if (StringUtils.equals(location, job.getLocation())) {
                    return true;
                }
            }
        }

        Set<String> departmentAdminAreas = TKContext.getUser().getDepartmentAdminAreas();
        //Confirm if any job matches this users department admin roles
        for (String dept : departmentAdminAreas) {
            for (Job job : lstJobs) {
                if (StringUtils.equals(dept, job.getDept())) {
                    return true;
                }
            }
        }
        return isAuthorized;
    }

}
