package org.kuali.hr.time.position.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.core.role.KPMERoleMemberAttribute;
import org.kuali.hr.job.service.JobService;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.core.api.membership.MemberType;
import org.kuali.rice.kim.api.role.Role;
import org.kuali.rice.kim.api.role.RoleMembership;
import org.kuali.rice.kim.api.role.RoleService;
import org.kuali.rice.kns.kim.role.DerivedRoleTypeServiceBase;

@SuppressWarnings("deprecation")
public class PositionDerivedRoleTypeServiceImpl extends DerivedRoleTypeServiceBase {

	private RoleService roleService;
	private JobService jobService;
	
	@Override
	public boolean performMatch(Map<String, String> inputAttributes, Map<String, String> storedAttributes) {
		boolean matches = false;
		
		if (inputAttributes != null && storedAttributes != null) {
			matches = inputAttributes.containsKey(KPMERoleMemberAttribute.POSITION.getRoleMemberAttributeName()) 
					&& StringUtils.equals(inputAttributes.get(KPMERoleMemberAttribute.POSITION.getRoleMemberAttributeName()), 
										  storedAttributes.get(KPMERoleMemberAttribute.POSITION.getRoleMemberAttributeName()));
		}
		
		return matches;
	}
	
	@Override
	public List<RoleMembership> getRoleMembersFromDerivedRole(String namespaceCode, String roleName, Map<String, String> qualification) {
        if (StringUtils.isBlank(namespaceCode)) {
            throw new RiceIllegalArgumentException("namespaceCode was null or blank");
        }

        if (roleName == null) {
            throw new RiceIllegalArgumentException("roleName was null");
        }
        
        List<RoleMembership> roleMembers = new ArrayList<RoleMembership>();
		
		Role role = getRoleService().getRoleByNamespaceCodeAndName(namespaceCode, roleName);
		
        if (role != null) {
	        if (qualification.containsKey(KPMERoleMemberAttribute.POSITION.getRoleMemberAttributeName())) {
				String positionNumber = qualification.get(KPMERoleMemberAttribute.POSITION.getRoleMemberAttributeName());
	            List<String> principalIds = getJobService().getPrincipalIdsInPosition(positionNumber, TKUtils.getCurrentDate());
	            for (String principalId : principalIds) {
	            	roleMembers.add(RoleMembership.Builder.create(role.getId(), null, principalId, MemberType.PRINCIPAL, qualification).build());
	            }
			}
        }
        
        return roleMembers;
	}
	
    @Override
	public boolean isDerivedRoleType() {
		return true;
	}
    
	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public JobService getJobService() {
		return jobService;
	}

	public void setJobService(JobService jobService) {
		this.jobService = jobService;
	}

}