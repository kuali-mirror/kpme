/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.kpme.core.role.service;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;
import static org.kuali.rice.core.api.criteria.PredicateFactory.greaterThan;
import static org.kuali.rice.core.api.criteria.PredicateFactory.in;
import static org.kuali.rice.core.api.criteria.PredicateFactory.isNull;
import static org.kuali.rice.core.api.criteria.PredicateFactory.lessThanOrEqual;
import static org.kuali.rice.core.api.criteria.PredicateFactory.or;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.kuali.kpme.core.department.Department;
import org.kuali.kpme.core.department.service.DepartmentService;
import org.kuali.kpme.core.workarea.WorkArea;
import org.kuali.kpme.core.workarea.service.WorkAreaService;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.rice.core.api.criteria.LookupCustomizer;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.membership.MemberType;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.KimConstants;
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
import org.kuali.rice.kim.impl.common.attribute.AttributeTransform;
import org.kuali.rice.kim.impl.role.RoleMemberBo;

public abstract class KPMERoleServiceBase {
	
    private static final Logger LOG = Logger.getLogger(KPMERoleServiceBase.class);
    
    private DepartmentService departmentService;
    private GroupService groupService;
	private KimTypeInfoService kimTypeInfoService;
	private RoleService roleService;
	private WorkAreaService workAreaService;
	
	/**
	 * Gets the id for the role {@code roleName}.
	 * 
	 * @param roleName The name of the role
	 * 
	 * @return the id for the role {@code roleName}.
	 */
	public abstract String getRoleIdByName(String roleName);
	
	/**
	 * Gets the role specified by {@code roleName}.
	 * 
	 * @param roleName The name of the role
	 * 
	 * @return the role specified by {@code roleName}.
	 */
	public abstract Role getRoleByName(String roleName);

	/**
	 * Checks whether the given {@code principalId} has the role {@code roleName}.
	 * 
	 * @param principalId The person to check the role for
	 * @param roleName The name of the role
	 * @param asOfDate The effective date of the role
	 * 
	 * @return true if {@code principalId} has the role {@code roleName}, false otherwise.
	 */
	public boolean principalHasRole(String principalId, String roleName, DateTime asOfDate) {
		Map<String, String> qualification = new HashMap<String, String>();
		
		return principalHasRole(principalId, roleName, qualification, asOfDate);
	}
	
	/**
	 * Checks whether the given {@code principalId} has the role {@code roleName} depending on the given role qualifications.
	 * 
	 * @param principalId The person to check the role for
	 * @param roleName The name of the role
	 * @param qualification The map of role qualifiers for the person
	 * @param asOfDate The effective date of the role
	 * 
	 * @return true if {@code principalId} has the role {@code roleName}, false otherwise.
	 */
	public boolean principalHasRole(String principalId, String roleName, Map<String, String> qualification, DateTime asOfDate) {
		boolean principalHasRole = false;
		
		String roleId = getRoleIdByName(roleName);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(equal(KimConstants.PrimaryKeyConstants.SUB_ROLE_ID, roleId));
		predicates.add(or(isNull("activeFromDateValue"), lessThanOrEqual("activeFromDateValue", asOfDate)));
		predicates.add(or(isNull("activeToDateValue"), greaterThan("activeToDateValue", new DateTime())));
		
		LookupCustomizer.Builder<RoleMemberBo> builder = LookupCustomizer.Builder.create();
		builder.setPredicateTransform(AttributeTransform.getInstance());
		LookupCustomizer<RoleMemberBo> lookupCustomizer = builder.build();
		for (Map.Entry<String, String> qualificationEntry : qualification.entrySet()) {
			Predicate predicate = equal("attributes[" + qualificationEntry.getKey() + "]", qualificationEntry.getValue());
			predicates.add(lookupCustomizer.getPredicateTransform().apply(predicate));
		}
		
		List<RoleMember> roleMembers = getRoleMembers(roleName, qualification, asOfDate, true);

		for (RoleMember roleMember : roleMembers) {
			if (MemberType.PRINCIPAL.equals(roleMember.getType())) {
				if (StringUtils.equals(roleMember.getMemberId(), principalId)) {
					principalHasRole = true;
					break;
				}
			} else if (MemberType.GROUP.equals(roleMember.getType())) {
				if (getGroupService().isMemberOfGroup(principalId, roleMember.getMemberId())) {
					principalHasRole = true;
					break;
				}
			}
		}
				
		return principalHasRole;
	}

	/**
	 * Checks whether the given {@code principalId} has the role {@code roleName} depending on the given work area.
	 * 
	 * @param principalId The person to check the role for
	 * @param roleName The name of the role
	 * @param workArea The work area qualifier
	 * @param asOfDate The effective date of the role
	 * 
	 * @return true if {@code principalId} has the role {@code roleName} for the given work area, false otherwise.
	 */
	public boolean principalHasRoleInWorkArea(String principalId, String roleName, Long workArea, DateTime asOfDate) {
		Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), String.valueOf(workArea));
		
		return principalHasRole(principalId, roleName, qualification, asOfDate);
	}

	/**
	 * Checks whether the given {@code principalId} has the role {@code roleName} depending on the given department.
	 * 
	 * @param principalId The person to check the role for
	 * @param roleName The name of the role
	 * @param department The department qualifier
	 * @param asOfDate The effective date of the role
	 * 
	 * @return true if {@code principalId} has the role {@code roleName} for the given department, false otherwise.
	 */
	public boolean principalHasRoleInDepartment(String principalId, String roleName, String department, DateTime asOfDate) {
		Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
		
		return principalHasRole(principalId, roleName, qualification, asOfDate);
	}

	/**
	 * Checks whether the given {@code principalId} has the role {@code roleName} depending on the given location.
	 * 
	 * @param principalId The person to check the role for
	 * @param roleName The name of the role
	 * @param location The location qualifier
	 * @param asOfDate The effective date of the role
	 * 
	 * @return true if {@code principalId} has the role {@code roleName} for the given location, false otherwise.
	 */
	public boolean principalHasRoleInLocation(String principalId, String roleName, String location, DateTime asOfDate) {
		Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
		
		return principalHasRole(principalId, roleName, qualification, asOfDate);
	}
	
	/**
	 * Gets the members of the role {@code roleName}.
	 * 
	 * @param roleName The name of the role
	 * @param asOfDate The effective date of the role
	 * @param getActiveOnly Whether or not to get only active role members
	 * 
	 * @return the list of role members in the role {@code roleName}.
	 */
	public List<RoleMember> getRoleMembers(String roleName, DateTime asOfDate, boolean getActiveOnly) {
		Map<String, String> qualification = new HashMap<String, String>();
		
		return getRoleMembers(roleName, qualification, asOfDate, getActiveOnly);
	}
	
	/**
	 * Gets the members of the role {@code roleName} for the given role qualifiers.
	 * 
	 * @param roleName The name of the role
	 * @param qualification The map of role qualifiers
	 * @param asOfDate The effective date of the role
	 * @param getActiveOnly Whether or not to get only active role members
	 * 
	 * @return the list of role members in the role {@code roleName}.
	 */
	public List<RoleMember> getRoleMembers(String roleName, Map<String, String> qualification, DateTime asOfDate, boolean getActiveOnly) {
		Role role = getRoleByName(roleName);
		
		return getRoleMembers(role, qualification, asOfDate, getActiveOnly);
	}
	
	/**
	 * Helper method to recursively search for role members.
	 * 
	 * @param role The role
	 * @param qualification The map of role qualifiers
	 * @param asOfDate The effective date of the role
	 * @param getActiveOnly Whether or not to get only active role members
	 * 
	 * @return the list of role members in {@code role}.
	 */
	private List<RoleMember> getRoleMembers(Role role, Map<String, String> qualification, DateTime asOfDate, boolean getActiveOnly) {
		List<RoleMember> roleMembers = new ArrayList<RoleMember>();
		
		if (role != null) {
			RoleTypeService roleTypeService = getRoleTypeService(role);
			
			if (roleTypeService == null || !roleTypeService.isDerivedRoleType()) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				predicates.add(equal(KimConstants.PrimaryKeyConstants.SUB_ROLE_ID, role.getId()));
				if (getActiveOnly) {
					predicates.add(or(isNull("activeFromDateValue"), lessThanOrEqual("activeFromDateValue", asOfDate)));
					predicates.add(or(isNull("activeToDateValue"), greaterThan("activeToDateValue", new DateTime())));
				}
				
				LookupCustomizer.Builder<RoleMemberBo> builder = LookupCustomizer.Builder.create();
				builder.setPredicateTransform(AttributeTransform.getInstance());
				LookupCustomizer<RoleMemberBo> lookupCustomizer = builder.build();
				for (Map.Entry<String, String> qualificationEntry : qualification.entrySet()) {
					Predicate predicate = equal("attributes[" + qualificationEntry.getKey() + "]", qualificationEntry.getValue());
					predicates.add(lookupCustomizer.getPredicateTransform().apply(predicate));
				}
				
				List<RoleMember> primaryRoleMembers = getRoleService().findRoleMembers(QueryByCriteria.Builder.fromPredicates(predicates.toArray(new Predicate[] {}))).getResults();
			
				for (RoleMember primaryRoleMember : primaryRoleMembers) {
					if (MemberType.PRINCIPAL.equals(primaryRoleMember.getType())) {
						roleMembers.add(primaryRoleMember);
					} else if (MemberType.ROLE.equals(primaryRoleMember.getType())) {
						Role nestedRole = getRoleService().getRole(primaryRoleMember.getMemberId());
						
						roleMembers.addAll(getRoleMembers(nestedRole, primaryRoleMember.getAttributes(), asOfDate, getActiveOnly));
					}
				}
			} else {
				List<RoleMembership> derivedRoleMembers = roleTypeService.getRoleMembersFromDerivedRole(role.getNamespaceCode(), role.getName(), qualification);
				
				for (RoleMembership derivedRoleMember : derivedRoleMembers) {
					RoleMember roleMember = RoleMember.Builder.create(derivedRoleMember.getRoleId(), derivedRoleMember.getId(), derivedRoleMember.getMemberId(), 
							derivedRoleMember.getType(), null, null, derivedRoleMember.getQualifier(), role.getName(), role.getNamespaceCode()).build();
				                        
					roleMembers.add(roleMember);
				}
			}
		}
		
		return roleMembers;
	}
	
	/**
	 * Gets the members of the role {@code roleName} for the given work area.
	 * 
	 * @param roleName The name of the role
	 * @param workArea The work area qualifier
	 * @param asOfDate The effective date of the role
	 * @param getActiveOnly Whether or not to get only active role members
	 * 
	 * @return the list of role members in the role {@code roleName} for the given work area.
	 */
	public List<RoleMember> getRoleMembersInWorkArea(String roleName, Long workArea, DateTime asOfDate, boolean getActiveOnly) {
		Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), String.valueOf(workArea));
		
		return getRoleMembers(roleName, qualification, asOfDate, getActiveOnly);
	}

	/**
	 * Gets the members of the role {@code roleName} for the given department.
	 * 
	 * @param roleName The name of the role
	 * @param department The department qualifier
	 * @param asOfDate The effective date of the role
	 * @param getActiveOnly Whether or not to get only active role members
	 * 
	 * @return the list of role members in the role {@code roleName} for the given department.
	 */
	public List<RoleMember> getRoleMembersInDepartment(String roleName, String department, DateTime asOfDate, boolean getActiveOnly) {
		Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
		
		return getRoleMembers(roleName, qualification, asOfDate, getActiveOnly);
	}

	/**
	 * Gets the members of the role {@code roleName} for the given location.
	 * 
	 * @param roleName The name of the role
	 * @param location The location qualifier
	 * @param asOfDate The effective date of the role
	 * @param getActiveOnly Whether or not to get only active role members
	 * 
	 * @return the list of role members in the role {@code roleName} for the given location.
	 */
	public List<RoleMember> getRoleMembersInLocation(String roleName, String location, DateTime asOfDate, boolean getActiveOnly) {
		Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
		
		return getRoleMembers(roleName, qualification, asOfDate, getActiveOnly);
	}
	
	/**
	 * Gets the work areas for the given {@code principalId} in the role {@code roleName}.
	 * 
	 * @param principalId The person to check the role for
	 * @param roleName The name of the role
	 * @param asOfDate The effective date of the role
	 * @param getActiveOnly Whether or not to get only active role members
	 * 
	 * @return the list of work areas for the given {@code principalId} in the role {@code roleName}.
	 */
	public List<Long> getWorkAreasForPrincipalInRole(String principalId, String roleName, DateTime asOfDate, boolean getActiveOnly) {
		Set<Long> workAreas = new HashSet<Long>();
		
		Role role = getRoleByName(roleName);

		List<Map<String, String>> roleQualifiers = getRoleQualifiers(principalId, role, asOfDate, getActiveOnly);
		
		for (Map<String, String> roleQualifier : roleQualifiers) {
			Long workArea = MapUtils.getLong(roleQualifier, KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName());
			if (workArea != null) {
				workAreas.add(workArea);
			}
		}
		
		List<String> departments = getDepartmentsForPrincipalInRole(principalId, roleName, asOfDate, getActiveOnly);
		
		for (String department : departments) {
			List<WorkArea> workAreaObjs = getWorkAreaService().getWorkAreas(department, asOfDate.toLocalDate());
			
			for (WorkArea workAreaObj : workAreaObjs) {
				workAreas.add(workAreaObj.getWorkArea());
			}
		}
		
		return new ArrayList<Long>(workAreas);
	}
	
	/**
	 * Gets the departments for the given {@code principalId} in the role {@code roleName}.
	 * 
	 * @param principalId The person to check the role for
	 * @param roleName The name of the role
	 * @param asOfDate The effective date of the role
	 * @param getActiveOnly Whether or not to get only active role members
	 * 
	 * @return the list of departments for the given {@code principalId} in the role {@code roleName}.
	 */
	public List<String> getDepartmentsForPrincipalInRole(String principalId, String roleName, DateTime asOfDate, boolean getActiveOnly) {
		Set<String> departments = new HashSet<String>();
		
		Role role = getRoleByName(roleName);
		
		List<Map<String, String>> roleQualifiers = getRoleQualifiers(principalId, role, asOfDate, getActiveOnly);
		
		for (Map<String, String> roleQualifier : roleQualifiers) {
			String department = MapUtils.getString(roleQualifier, KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName());
			if (department != null) {
				departments.add(department);
			}
		}
		
		List<String> locations = getLocationsForPrincipalInRole(principalId, roleName, asOfDate, getActiveOnly);
		
		for (String location : locations) {
			List<Department> departmentObjs = getDepartmentService().getDepartments(location,asOfDate.toLocalDate());
			
			for (Department departmentObj : departmentObjs) {
				departments.add(departmentObj.getDept());
			}
		}
		
		return new ArrayList<String>(departments);
	}
	
	/**
	 * Gets the locations for the given {@code principalId} in the role {@code roleName}.
	 * 
	 * @param principalId The person to check the role for
	 * @param roleName The name of the role
	 * @param asOfDate The effective date of the role
	 * @param getActiveOnly Whether or not to get only active role members
	 * 
	 * @return the list of locations for the given {@code principalId} in the role {@code roleName}.
	 */
	public List<String> getLocationsForPrincipalInRole(String principalId, String roleName, DateTime asOfDate, boolean getActiveOnly) {
		Set<String> locations = new HashSet<String>();
		
		Role role = getRoleByName(roleName);
		
		List<Map<String, String>> roleQualifiers = getRoleQualifiers(principalId, role, asOfDate, getActiveOnly);
		
		for (Map<String, String> roleQualifier : roleQualifiers) {
			String location = MapUtils.getString(roleQualifier, KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName());
			
			if (location != null) {
				locations.add(location);
			}
		}
		
		return new ArrayList<String>(locations);
	}
	
	/**
	 * Helper method to gather up all qualifiers for the given {@code principalId} in {@code role}.
	 *
	 * @param principalId The person get the role qualifiers for
	 * @param role The role
	 * @param asOfDate The effective date of the role
	 * @param getActiveOnly Whether or not to consider only active role members
	 * 
	 * @return the map of qualifiers for the given {@code principalId} in {@code role}.
	 */
	private List<Map<String, String>> getRoleQualifiers(String principalId, Role role, DateTime asOfDate, boolean getActiveOnly) {
		List<Map<String, String>> roleQualifiers = new ArrayList<Map<String, String>>();

		if (role != null) {
			List<RoleMember> principalIdRoleMembers = getPrincipalIdRoleMembers(principalId, role, asOfDate, getActiveOnly);
			
	        for (RoleMember principalIdRoleMember : principalIdRoleMembers) {
	        	roleQualifiers.add(principalIdRoleMember.getAttributes());
	        }
		}
        
        return roleQualifiers;
	}
	
	/**
	 * Helper method to get the role member objects.
	 * 
	 * @param principalId The person to get the role for
	 * @param role The role
	 * @param asOfDate The effective date of the role
	 * @param getActiveOnly Whether or not to consider only active role members
	 * 
	 * @return the list of role member objects
	 */
	private List<RoleMember> getPrincipalIdRoleMembers(String principalId, Role role, DateTime asOfDate, boolean getActiveOnly) {
		List<String> groupIds = getGroupService().getGroupIdsByPrincipalId(principalId);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(equal(KimConstants.PrimaryKeyConstants.SUB_ROLE_ID, role.getId()));
		
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
		
		if (getActiveOnly) {
			predicates.add(or(isNull("activeFromDateValue"), lessThanOrEqual("activeFromDateValue", asOfDate)));
			predicates.add(or(isNull("activeToDateValue"), greaterThan("activeToDateValue", new DateTime())));
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

}