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
package org.kuali.kpme.core.service.role;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;
import static org.kuali.rice.core.api.criteria.PredicateFactory.greaterThan;
import static org.kuali.rice.core.api.criteria.PredicateFactory.in;
import static org.kuali.rice.core.api.criteria.PredicateFactory.isNull;
import static org.kuali.rice.core.api.criteria.PredicateFactory.lessThanOrEqual;
import static org.kuali.rice.core.api.criteria.PredicateFactory.or;

import java.util.*;

import javax.xml.namespace.QName;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.department.service.DepartmentService;
import org.kuali.kpme.core.api.workarea.service.WorkAreaService;
import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.membership.MemberType;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.group.Group;
import org.kuali.rice.kim.api.group.GroupService;
import org.kuali.rice.kim.api.role.Role;
import org.kuali.rice.kim.api.role.RoleMember;
import org.kuali.rice.kim.api.role.RoleMembership;
import org.kuali.rice.kim.api.role.RoleService;
import org.kuali.rice.kim.api.type.KimType;
import org.kuali.rice.kim.api.type.KimTypeInfoService;
import org.kuali.rice.kim.framework.role.RoleTypeService;
import org.kuali.rice.kim.framework.type.KimTypeService;
import org.kuali.rice.kim.impl.KIMPropertyConstants;

public class KPMERoleServiceImpl implements KPMERoleService {
	
    private static final String KPME_PROXIED_ROLE_AS_OF_DATE = "KpmeProxiedRoleAsOfDate";
	private static final String KPME_PROXIED_ROLE_ROLE_NAME = "KpmeProxiedRoleRoleName";
	private static final String KPME_PROXIED_ROLE_NAMESPACE_CODE = "KpmeProxiedRoleNamespaceCode";

	private static final Logger LOG = Logger.getLogger(KPMERoleServiceImpl.class);
    
    private DepartmentService departmentService;
    private GroupService groupService;
	private KimTypeInfoService kimTypeInfoService;
	private RoleService roleService;
	private WorkAreaService workAreaService;
	private KPMERoleServiceHelper roleServiceHelper;

	public boolean principalHasRole(String principalId, String namespaceCode, String roleName, DateTime asOfDate) {
		Map<String, String> qualification = new HashMap<String, String>();
		
		return principalHasRole(principalId, namespaceCode, roleName, qualification, asOfDate);
	}

	public boolean principalHasRole(String principalId, String namespaceCode, String roleName, Map<String, String> qualification, DateTime asOfDate) {
		// the return value
		boolean principalHasRole = false;

		// insert the role name and name-space code and the other method parameters into the qualification map.
		// "KpmeProxiedRole" is used as the prefix for each method parameter's key name in the qualification.
		qualification.put(KPME_PROXIED_ROLE_NAMESPACE_CODE, namespaceCode);
		qualification.put(KPME_PROXIED_ROLE_ROLE_NAME, roleName);
		if(asOfDate != null) {
			// reset to start of the day
			asOfDate = asOfDate.toLocalDate().toDateTimeAtStartOfDay();
		}
		else {
			// default to start of today
			asOfDate = LocalDate.now().toDateTimeAtStartOfDay();
		}
		qualification.put(KPME_PROXIED_ROLE_AS_OF_DATE, asOfDate.toString());

		// get the id for the KPME proxy role instance. This instance should contain only one member without 
		// any membership qualification: a derived role whose service methods interact with the actual proxied role
		// via KIM role services
		String roleId = getRoleService().getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(),
																				 KPMERole.KPME_PROXY_ROLE.getRoleName());

		if(roleId != null) {
			// call the KPME role proxy's method to check if the principal has the proxied role; this will in turn will call the 
			// derived role member's method for checking that. The derived role member's method will interact with the actual 
			// proxied role to verify membership. 
			principalHasRole = getRoleService().principalHasRole(principalId, Collections.singletonList(roleId), qualification);
			
		}
		else {
        	LOG.error("Role id for role name " + KPMERole.KPME_PROXY_ROLE.getRoleName() + " with namespace code " + 
        														KPMENamespace.KPME_HR.getNamespaceCode() + " was null");
        }

		return principalHasRole;
	}
	
	
	public boolean principalHasRoleInWorkArea(String principalId, String namespaceCode, String roleName, Long workArea, DateTime asOfDate) {
		Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), String.valueOf(workArea));
		
		return principalHasRole(principalId, namespaceCode, roleName, qualification, asOfDate);
	}

	public boolean principalHasRoleInDepartment(String principalId, String namespaceCode, String roleName, String department, DateTime asOfDate) {
		Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
		
		return principalHasRole(principalId, namespaceCode, roleName, qualification, asOfDate);
	}

	public boolean principalHasRoleInLocation(String principalId, String namespaceCode, String roleName, String location, DateTime asOfDate) {
		Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
		
		return principalHasRole(principalId, namespaceCode, roleName, qualification, asOfDate);
	}

	public List<RoleMember> getRoleMembers(String namespaceCode, String roleName, DateTime asOfDate, boolean isActiveOnly) {
		Map<String, String> qualification = new HashMap<String, String>();
		
		return getRoleMembers(namespaceCode, roleName, qualification, asOfDate, isActiveOnly);
	}

	
	public List<RoleMember> getRoleMembers(String namespaceCode, String roleName, Map<String, String> qualification, DateTime asOfDate, boolean isActiveOnly) {
		// define the return value
		List<RoleMember> returnList = new ArrayList<RoleMember>();
		
		// set defaults for various parameters
		if(qualification == null) {
			qualification = new HashMap<String, String>();
		}		
		if(asOfDate != null) {
			// reset to start of the date specified
			asOfDate = asOfDate.toLocalDate().toDateTimeAtStartOfDay();
		}
		else {
			// default to start of today
			asOfDate = LocalDate.now().toDateTimeAtStartOfDay();
		}		
		// invoke the helper's (caching) logic to query the role membership
		returnList = getRoleServiceHelper().getRoleMembersCached(namespaceCode, roleName, qualification, asOfDate, isActiveOnly);
		
		return returnList;
	}
	
	
	public List<RoleMember> getRoleMembersInWorkArea(String namespaceCode, String roleName, Long workArea, DateTime asOfDate, boolean isActiveOnly) {
		Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), String.valueOf(workArea));
		
		return getRoleMembers(namespaceCode, roleName, qualification, asOfDate, isActiveOnly);
	}

	public List<RoleMember> getRoleMembersInDepartment(String namespaceCode, String roleName, String department, DateTime asOfDate, boolean isActiveOnly) {
		Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
		
		return getRoleMembers(namespaceCode, roleName, qualification, asOfDate, isActiveOnly);
	}

	public List<RoleMember> getRoleMembersInLocation(String namespaceCode, String roleName, String location, DateTime asOfDate, boolean isActiveOnly) {
		Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
		
		return getRoleMembers(namespaceCode, roleName, qualification, asOfDate, isActiveOnly);
	}

    @Override
    public List<Long> getWorkAreasForPrincipalInRoles(String principalId, List<String> roleIds, DateTime asOfDate, boolean isActiveOnly) {
    	
        Set<Long> workAreas = new HashSet<Long>();

        // iterate through the role ids getting the work areas for each one
        for(String roleId: roleIds) {
        	// get the role for the role id from the role service
        	Role role = getRoleService().getRole(roleId);
        	if(role != null) {
        		String roleName = role.getName();
        		String namespaceCode = role.getNamespaceCode();
        		
        		Map<String, String> qualifiers = new HashMap<String, String>();        
                // empty qualifier map will match any attribute in the predicate query, i.e. will work like wildcarded entries
                List<Map<String, String>> roleQualifiers = new ArrayList<Map<String, String>>();
            	List<RoleMember> principalAndGroupRoleMembers = getRoleMembers(namespaceCode, roleName, qualifiers, asOfDate, isActiveOnly);
            	for(RoleMember roleMember : principalAndGroupRoleMembers){
            		// check for principals or groups
            		if (MemberType.PRINCIPAL.equals(roleMember.getType())) {
                		if(roleMember.getMemberId().equals(principalId)) {
                			roleQualifiers.add(roleMember.getAttributes());
                		}
            		}
            		else if( MemberType.GROUP.equals(roleMember.getType()) ) {
            			// query the helper to see if the principal is a member of this nested member group
            			Group nestedGroup = getGroupService().getGroup(roleMember.getMemberId());
            			if(getRoleServiceHelper().isMemberOfGroup(principalId, nestedGroup, asOfDate, isActiveOnly)) {
            				roleQualifiers.add(roleMember.getAttributes());
            			}
            		}
            	}
        		
        		
        		for (Map<String, String> roleQualifier : roleQualifiers) {
        			Long workArea = MapUtils.getLong(roleQualifier, KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName());
        			if(workArea != null) {
        				workAreas.add(workArea);
        			}
        		}
        	}      	
        }

        List<String> departments = getDepartmentsForPrincipalInRoles(principalId, roleIds, asOfDate, isActiveOnly);
        workAreas.addAll(getWorkAreaService().getWorkAreasForDepartments(departments, asOfDate.toLocalDate()));

        return new ArrayList<Long>(workAreas);
    }

    public List<Long> getWorkAreasForPrincipalInRole(String principalId, String namespaceCode, String roleName, DateTime asOfDate, boolean isActiveOnly) {
		Set<Long> workAreas = new HashSet<Long>();

        Map<String, String> qualifiers = new HashMap<String, String>();        
        // empty qualifier map will match any attribute in the predicate query, i.e. will work like wildcarded entries
        List<Map<String, String>> roleQualifiers = new ArrayList<Map<String, String>>();
    	List<RoleMember> principalAndGroupRoleMembers = getRoleMembers(namespaceCode, roleName, qualifiers, asOfDate, isActiveOnly);
    	for(RoleMember roleMember : principalAndGroupRoleMembers){
    		// check for principals or groups
    		if (MemberType.PRINCIPAL.equals(roleMember.getType())) {
        		if(roleMember.getMemberId().equals(principalId)) {
        			roleQualifiers.add(roleMember.getAttributes());
        		}
    		}
    		else if( MemberType.GROUP.equals(roleMember.getType()) ) {
    			// query the helper to see if the principal is a member of this nested member group
    			Group nestedGroup = getGroupService().getGroup(roleMember.getMemberId());
    			if(getRoleServiceHelper().isMemberOfGroup(principalId, nestedGroup, asOfDate, isActiveOnly)) {
    				roleQualifiers.add(roleMember.getAttributes());
    			}
    		}
    	}
		
		
		for (Map<String, String> roleQualifier : roleQualifiers) {
			Long workArea = MapUtils.getLong(roleQualifier, KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName());
			if(workArea != null) {
				workAreas.add(workArea);
			}
		}
		
		List<String> departments = getDepartmentsForPrincipalInRole(principalId, namespaceCode, roleName, asOfDate, isActiveOnly);
		workAreas.addAll(getWorkAreaService().getWorkAreasForDepartments(departments, asOfDate.toLocalDate()));

		return new ArrayList<Long>(workAreas);
	}
    
    
    
    
    

    public List<String> getDepartmentsForPrincipalInRoles(String principalId, List<String> roleIds, DateTime asOfDate, boolean isActiveOnly) {
        Set<String> departments = new HashSet<String>();

        //Role role = getRoleService().getRoleByNamespaceCodeAndName(namespaceCode, roleName);

        Map<String, String> qualifiers = new HashMap<String, String>();
        qualifiers.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), "%");
        qualifiers.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), "%");
        qualifiers.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), "%");
        List<Map<String, String>> roleQualifiers = getRoleQualifiers(principalId, roleIds, qualifiers, asOfDate, isActiveOnly);

        for (Map<String, String> roleQualifier : roleQualifiers) {
            String department = MapUtils.getString(roleQualifier, KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName());
            if (department != null) {
                departments.add(department);
            }
        }

        List<String> locations = getLocationsForPrincipalInRoles(principalId, roleIds, asOfDate, isActiveOnly);
        departments.addAll(getDepartmentService().getDepartmentsForLocations(locations, asOfDate.toLocalDate()));

        return new ArrayList<String>(departments);
    }

	public List<String> getDepartmentsForPrincipalInRole(String principalId, String namespaceCode, String roleName, DateTime asOfDate, boolean isActiveOnly) {
		Set<String> departments = new HashSet<String>();
		
		Role role = getRoleService().getRoleByNamespaceCodeAndName(namespaceCode, roleName);

        Map<String, String> qualifiers = new HashMap<String, String>();
        qualifiers.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), "%");
        qualifiers.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), "%");
        qualifiers.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), "%");
        List<Map<String, String>> roleQualifiers = getRoleQualifiers(principalId, role, qualifiers, asOfDate, isActiveOnly);
		
		for (Map<String, String> roleQualifier : roleQualifiers) {
			String department = MapUtils.getString(roleQualifier, KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName());
			if (department != null) {
				departments.add(department);
			}
		}
		
		List<String> locations = getLocationsForPrincipalInRole(principalId, namespaceCode, roleName, asOfDate, isActiveOnly);
		departments.addAll(getDepartmentService().getDepartmentsForLocations(locations, asOfDate.toLocalDate()));

		return new ArrayList<String>(departments);
	}

    public List<String> getLocationsForPrincipalInRoles(String principalId, List<String> roleIds, DateTime asOfDate, boolean isActiveOnly) {
        Set<String> locations = new HashSet<String>();

        Map<String, String> qualifiers = new HashMap<String, String>();
        qualifiers.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), "%");
        qualifiers.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), "%");
        qualifiers.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), "%");
        List<Map<String, String>> roleQualifiers = getRoleQualifiers(principalId, roleIds, qualifiers, asOfDate, isActiveOnly);

        for (Map<String, String> roleQualifier : roleQualifiers) {
            String location = MapUtils.getString(roleQualifier, KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName());

            if (location != null) {
                locations.add(location);
            }
        }

        return new ArrayList<String>(locations);
    }

	public List<String> getLocationsForPrincipalInRole(String principalId, String namespaceCode, String roleName, DateTime asOfDate, boolean isActiveOnly) {
		Set<String> locations = new HashSet<String>();
		
		Role role = getRoleService().getRoleByNamespaceCodeAndName(namespaceCode, roleName);

        Map<String, String> qualifiers = new HashMap<String, String>();
        qualifiers.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), "%");
        qualifiers.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), "%");
        qualifiers.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), "%");
		List<Map<String, String>> roleQualifiers = getRoleQualifiers(principalId, role, qualifiers, asOfDate, isActiveOnly);
		
		for (Map<String, String> roleQualifier : roleQualifiers) {
			String location = MapUtils.getString(roleQualifier, KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName());
			
			if (location != null) {
				locations.add(location);
			}
		}
		
		return new ArrayList<String>(locations);
	}
	
	
	@Override
	public List<RoleMember> getPrimaryRoleMembersInWorkArea(String namespaceCode, String roleName, Long workArea, DateTime asOfDate, boolean isActiveOnly) {
		// the return value
		List<RoleMember> primaryRoleMembers = new ArrayList<RoleMember>();
		
		Role role = getRoleService().getRoleByNamespaceCodeAndName(namespaceCode, roleName);
		if(role != null) {
			Map<String, String> qualification = new HashMap<String, String>();
			qualification.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), String.valueOf(workArea));
			primaryRoleMembers = getRoleServiceHelper().getPrimaryRoleMembers(role, qualification, asOfDate, isActiveOnly);
		}
		else {
			LOG.error("Role instance for role with name " + roleName + " namespace " + namespaceCode + " was null");
		}
				
		return primaryRoleMembers;
	}
	
	/**
	 * Helper method to gather up all qualifiers for the given {@code principalId} in {@code role}.
	 *
	 * @param principalId The person get the role qualifiers for
	 * @param role The role
	 * @param asOfDate The effective date of the role
	 * @param activeOnly Whether or not to consider only active role members
	 * 
	 * @return the map of qualifiers for the given {@code principalId} in {@code role}.
	 */
	private List<Map<String, String>> getRoleQualifiers(String principalId, Role role, Map<String, String> qualifiers, DateTime asOfDate, boolean activeOnly) {
		List<Map<String, String>> roleQualifiers = new ArrayList<Map<String, String>>();
		
		if (role != null) {
			List<String> roleIds = Collections.singletonList(role.getId());
			roleQualifiers = getRoleQualifiers(principalId, roleIds, qualifiers, asOfDate, activeOnly);
		}

        return roleQualifiers;
	}

    /**
     * Helper method to gather up all qualifiers for the given {@code principalId} in {@code role}.
     *
     * @param principalId The person get the role qualifiers for
     * @param roleIds The roleIds
     * @param asOfDate The effective date of the role
     * @param activeOnly Whether or not to consider only active role members
     *
     * @return the map of qualifiers for the given {@code principalId} in {@code role}.
     */
	// TODO the else pathway below will return only the top level members and not resolve the nested roles + groups.
    private List<Map<String, String>> getRoleQualifiers(String principalId, List<String> roleIds, Map<String, String> qualifiers, DateTime asOfDate, boolean activeOnly) {
        List<Map<String, String>> roleQualifiers = new ArrayList<Map<String, String>>();

        if(CollectionUtils.isNotEmpty(roleIds)) {
	        if (asOfDate.equals(LocalDate.now().toDateTimeAtStartOfDay()) && activeOnly) {
	        	List<String> groupIds = getGroupService().getGroupIdsByPrincipalId(principalId);
	        	List<RoleMembership> principalAndGroupRoleMembers = getRoleService().getRoleMembers(roleIds, qualifiers);
	        	for(RoleMembership roleMember : principalAndGroupRoleMembers){
	        		// check for principals or groups
	        		if (MemberType.PRINCIPAL.equals(roleMember.getType())) {
		        		if(roleMember.getMemberId().equals(principalId)) {
		        			roleQualifiers.add(roleMember.getQualifier());
		        		}
	        		}
	        		else if( (MemberType.GROUP.equals(roleMember.getType())) && (CollectionUtils.isNotEmpty(groupIds)) ) {
	        			if(groupIds.contains(roleMember.getMemberId())) {
	        				roleQualifiers.add(roleMember.getQualifier());
	        			}
	        		}    		
	        	}
	        }
	        else {
	            List<RoleMember> principalIdRoleMembers = getPrincipalIdRoleMembers(principalId, roleIds, asOfDate, activeOnly);
	            for(RoleMember principalIdRoleMember : principalIdRoleMembers) {
	                roleQualifiers.add(principalIdRoleMember.getAttributes());
	            }
	        }
        }
        
        return roleQualifiers;
    }


    /**
     * Helper method to get the role member objects.
     *
     * @param principalId The person to get the role for
     * @param roleIds The role
     * @param asOfDate The effective date of the role
     * @param activeOnly Whether or not to consider only active role members
     *
     * @return the list of role member objects
     */
    private List<RoleMember> getPrincipalIdRoleMembers(String principalId, List<String> roleIds, DateTime asOfDate, boolean activeOnly) {
        List<String> groupIds = getGroupService().getGroupIdsByPrincipalId(principalId);

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(in(KimConstants.PrimaryKeyConstants.SUB_ROLE_ID, roleIds.toArray(new String[roleIds.size()])));

        List<Predicate> principalPredicates = new ArrayList<Predicate>();
        principalPredicates.add(equal(KIMPropertyConstants.RoleMember.MEMBER_TYPE_CODE, MemberType.PRINCIPAL.getCode()));
        if (principalId != null) {
            principalPredicates.add(equal(KIMPropertyConstants.RoleMember.MEMBER_ID, principalId));
        }
        Predicate principalPredicate = and(principalPredicates.toArray(new Predicate[] {}));

        Predicate rolePredicate = equal(KIMPropertyConstants.RoleMember.MEMBER_TYPE_CODE, MemberType.ROLE.getCode());

        List<Predicate> groupPredicates = new ArrayList<Predicate>();
        groupPredicates.add(equal(KIMPropertyConstants.RoleMember.MEMBER_TYPE_CODE, MemberType.GROUP.getCode()));
        if (CollectionUtils.isNotEmpty(groupIds)) {
            groupPredicates.add(in(KIMPropertyConstants.RoleMember.MEMBER_ID, groupIds.toArray(new String[] {})));
        }
        Predicate groupPredicate = and(groupPredicates.toArray(new Predicate[] {}));

        predicates.add(or(principalPredicate, rolePredicate, groupPredicate));

        if (activeOnly) {
            predicates.add(or(isNull("activeFromDateValue"), lessThanOrEqual("activeFromDateValue", asOfDate)));
            predicates.add(or(isNull("activeToDateValue"), greaterThan("activeToDateValue", asOfDate)));
        }

        return getRoleService().findRoleMembers(QueryByCriteria.Builder.fromPredicates(predicates.toArray(new Predicate[] {}))).getResults();
    }

    
    
	/**
	 * Gets the derived role service for {@code role}.
	 * 
	 * @param role the role
	 * 
	 * @return the derived role service name for {@code role}.
	 */
    protected RoleTypeService getRoleTypeService(Role role) {
    	RoleTypeService roleTypeService = null;
    	
        if (role != null) {
        	String serviceName = getDerivedRoleServiceName(role.getKimTypeId());
        	
        	if (serviceName != null) {
                try {
                    KimTypeService service = (KimTypeService) GlobalResourceLoader.getService(QName.valueOf(serviceName));
                    if (service != null && service instanceof RoleTypeService) {
                        return (RoleTypeService) service;
                    }
                } catch (Exception ex) {
                    LOG.error("Unable to find role type service with name: " + serviceName, ex);
                }
            }
        }
        
        return roleTypeService;
    }

	/**
	 * Gets the derived role service name for {@code kimTypeId}.
	 * 
	 * @param kimTypeId the KIM type id
	 * 
	 * @return the derived role service name for {@code kimTypeId}.
	 */
	protected String getDerivedRoleServiceName(String kimTypeId) {
		KimType kimType = getKimTypeInfoService().getKimType(kimTypeId);
		
		return kimType != null ? kimType.getServiceName() : null;
	}
    
    public DepartmentService getDepartmentService() {
    	return departmentService;
    }
    
    public void setDepartmentService(DepartmentService departmentService) {
    	this.departmentService = departmentService;
    }

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public KimTypeInfoService getKimTypeInfoService() {
		return kimTypeInfoService;
	}

	public void setKimTypeInfoService(KimTypeInfoService kimTypeInfoService) {
		this.kimTypeInfoService = kimTypeInfoService;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
    public WorkAreaService getWorkAreaService() {
    	return workAreaService;
    }
    
    public void setWorkAreaService(WorkAreaService workAreaService) {
    	this.workAreaService = workAreaService;
    }

	public KPMERoleServiceHelper getRoleServiceHelper() {
		return roleServiceHelper;
	}

	public void setRoleServiceHelper(KPMERoleServiceHelper roleServiceHelper) {
		this.roleServiceHelper = roleServiceHelper;
	}

}