/**
 * Copyright 2004-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.core.position.service;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.job.service.JobService;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.core.api.membership.MemberType;
import org.kuali.rice.kim.api.role.Role;
import org.kuali.rice.kim.api.role.RoleMembership;
import org.kuali.rice.kim.api.role.RoleService;
import org.kuali.rice.kns.kim.role.DerivedRoleTypeServiceBase;

@SuppressWarnings("deprecation")
public class PositionDerivedRoleTypeServiceImpl extends DerivedRoleTypeServiceBase {

	private static final Logger LOG = Logger.getLogger(PositionDerivedRoleTypeServiceImpl.class);
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
        	LOG.error("namespaceCode was null or blank");
        	return null;
//            throw new RiceIllegalArgumentException("namespaceCode was null or blank");
        }
        
        if (roleName == null) {
        	LOG.error("roleName was null");
        	return null;
//            throw new RiceIllegalArgumentException("roleName was null");
        }
        
        List<RoleMembership> roleMembers = new ArrayList<RoleMembership>();
		
		Role role = getRoleService().getRoleByNamespaceCodeAndName(namespaceCode, roleName);
		
        if (role != null) {
	        if (qualification.containsKey(KPMERoleMemberAttribute.POSITION.getRoleMemberAttributeName())) {
				String positionNumber = qualification.get(KPMERoleMemberAttribute.POSITION.getRoleMemberAttributeName());
				
				// get the as-of date and the active flag values
				DateTime asOfDate = LocalDate.now().toDateTimeAtStartOfDay();
				String asOfDateString = qualification.remove("asOfDate");
				if(asOfDateString != null) {
					asOfDate = DateTime.parse(asOfDateString);
				}
				
				@SuppressWarnings("unused")
				boolean activeOnly = true;  // active is currently unused for this derived role , may need it in the future
				String activeOnlyString = qualification.remove("activeOnly");
				if(activeOnlyString != null) {
					activeOnly = Boolean.parseBoolean(activeOnlyString);
				}
				
				
				// delegate the actual work of finding principals to the job service, at some future point we may also pass in the active flag
	            List<String> principalIds = getJobService().getPrincipalIdsInPosition(positionNumber, asOfDate.toLocalDate());
	            
	            for (String principalId : principalIds) {
	            	roleMembers.add(RoleMembership.Builder.create(role.getId(), null, principalId, MemberType.PRINCIPAL, qualification).build());
	            }
			}
        }
        else {
        	LOG.error("Role for role name " + roleName + " with namespace code "  + namespaceCode + " was null");
        }
        
        return roleMembers;
	}
	
    @Override
	public boolean isDerivedRoleType() {
		return true;
	}
    
    @Override
	public boolean dynamicRoleMembership(String namespaceCode, String roleName) {
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


    @Override
    public Map<String, String> convertQualificationForMemberRolesAndMemberAttributes(
             String namespaceCode, String roleName,
            String memberRoleNamespaceCode,
            String memberRoleName, Map<String, String> qualification,
            Map<String, String> memberQualification) throws RiceIllegalArgumentException {
        if (StringUtils.isBlank(namespaceCode)) {
            throw new RiceIllegalArgumentException("namespaceCode was null or blank");
        }

        if (StringUtils.isBlank(roleName)) {
            throw new RiceIllegalArgumentException("roleName was null or blank");
        }

        if (StringUtils.isBlank(memberRoleNamespaceCode)) {
            throw new RiceIllegalArgumentException("memberRoleNamespaceCode was null or blank");
        }

        if (StringUtils.isBlank(memberRoleName)) {
            throw new RiceIllegalArgumentException("memberRoleName was null or blank");
        }

        if (qualification == null) {
            throw new RiceIllegalArgumentException("qualification was null");
        }

        if (memberQualification == null) {
            throw new RiceIllegalArgumentException("memberQualification was null");
        }

        return Collections.unmodifiableMap(new HashMap<String, String>(memberQualification));
    }

}