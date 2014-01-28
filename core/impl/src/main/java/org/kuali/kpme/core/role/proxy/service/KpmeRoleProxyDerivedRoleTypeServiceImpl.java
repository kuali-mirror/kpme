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
package org.kuali.kpme.core.role.proxy.service;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;
import static org.kuali.rice.core.api.criteria.PredicateFactory.greaterThan;
import static org.kuali.rice.core.api.criteria.PredicateFactory.isNull;
import static org.kuali.rice.core.api.criteria.PredicateFactory.lessThanOrEqual;
import static org.kuali.rice.core.api.criteria.PredicateFactory.or;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.service.role.KPMERoleServiceHelper;
import org.kuali.rice.core.api.criteria.LookupCustomizer;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.membership.MemberType;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.group.Group;
import org.kuali.rice.kim.api.group.GroupMember;
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
import org.kuali.rice.kns.kim.role.DerivedRoleTypeServiceBase;
import org.springframework.cache.annotation.Cacheable;

@SuppressWarnings("deprecation")
public class KpmeRoleProxyDerivedRoleTypeServiceImpl extends DerivedRoleTypeServiceBase implements KPMERoleServiceHelper {
	
	private static final String KPME_PROXIED_ROLE_ROLE_NAME = "KpmeProxiedRoleRoleName";

	private static final String KPME_PROXIED_ROLE_NAMESPACE_CODE = "KpmeProxiedRoleNamespaceCode";

	private static final String KPME_PROXIED_ROLE_IS_ACTIVE_ONLY = "KpmeProxiedRoleIsActiveOnly";

	private static final String KPME_PROXIED_ROLE_AS_OF_DATE = "KpmeProxiedRoleAsOfDate";

	private static final Logger LOG = Logger.getLogger(KpmeRoleProxyDerivedRoleTypeServiceImpl.class);
	
	private RoleService roleService;
	private KimTypeInfoService kimTypeInfoService;
	private GroupService groupService;
	
	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	
	public KimTypeInfoService getKimTypeInfoService() {
		return kimTypeInfoService;
	}

	public void setKimTypeInfoService(KimTypeInfoService kimTypeInfoService) {
		this.kimTypeInfoService = kimTypeInfoService;
	}
	
	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
	
	
	@Override
	public boolean performMatch(Map<String, String> inputAttributes, Map<String, String> storedAttributes) {
		// accept any qualifiers
		return true;
	}
	
	 @Override
	public List<String> getQualifiersForExactMatch() {
		// no pre-specified qualifiers for match
		return Collections.emptyList();
	}
	
	@Override
	public boolean dynamicRoleMembership(String namespaceCode, String roleName) {
		// need membership results to be cached
		return false;
	}
	
	
	
	@Override
	public List<RoleMembership> getRoleMembersFromDerivedRole(String namespaceCode, String roleName, Map<String, String> qualification) {
		List<RoleMembership> returnList = new ArrayList<RoleMembership>();
		
		// get the as-of date and the active flag values
		DateTime asOfDate = LocalDate.now().toDateTimeAtStartOfDay();
		String asOfDateString = qualification.remove(KPME_PROXIED_ROLE_AS_OF_DATE);
		if(asOfDateString != null) {
			asOfDate = DateTime.parse(asOfDateString);
		}
		
		boolean activeOnly = true;
		String activeOnlyString = qualification.remove(KPME_PROXIED_ROLE_IS_ACTIVE_ONLY);
		if(activeOnlyString != null) {
			activeOnly = Boolean.parseBoolean(activeOnlyString);
		}		
		
		String proxiedRoleNamespaceCode =  qualification.remove(KPME_PROXIED_ROLE_NAMESPACE_CODE);
		if(proxiedRoleNamespaceCode == null) {
			// use the hook to get the namespace
			proxiedRoleNamespaceCode = this.getProxiedRoleNamespaceCode();
		}
		
		String proxiedRoleName =  qualification.remove(KPME_PROXIED_ROLE_ROLE_NAME);	
		if(proxiedRoleName == null) {
			// use the hook to get the role name
			proxiedRoleName = this.getProxiedRoleName();
		}
		
		
		// check that the role is valid and if so invoke the (caching) logic for querying the proxied role membership
		if( getRoleService().getRoleByNamespaceCodeAndName(proxiedRoleNamespaceCode, proxiedRoleName) != null ) {
			returnList = convertToRoleMemberships(getRoleMembersCached(proxiedRoleNamespaceCode, proxiedRoleName, qualification, asOfDate, activeOnly));
		}
		else {
        	LOG.error("Role instance for proxied role with name " + proxiedRoleName + " namespace " + proxiedRoleNamespaceCode + " was null");
        }
		
		return returnList;
	}
		
	
	
	protected String getProxiedRoleNamespaceCode() {
		return "";
	}
	
	protected String getProxiedRoleName() {
		return "";
	}
	

	private List<RoleMembership> convertToRoleMemberships(List<RoleMember> roleMembers) {
		List<RoleMembership> roleMemberships = new ArrayList<RoleMembership>();
		for(RoleMember roleMember: roleMembers) {
			RoleMembership roleMembership = RoleMembership.Builder.create(
                	roleMember.getRoleId(),
                	roleMember.getId(),
                	roleMember.getMemberId(),
                	roleMember.getType(),
                	roleMember.getAttributes()).build();
			roleMemberships.add(roleMembership);
		}
		return roleMemberships;
	}
	
	
	
	
	
	
	@Override
	@Cacheable(value = "http://rice.kuali.org/kim/v2_0/RoleMemberType", key = "'{getRoleMembersCached}' + 'namespace=' + #p0 + '|' + 'roleName=' + #p1 + '|' + 'qualification=' + T(org.kuali.rice.core.api.cache.CacheKeyUtils).mapKey(#p2)  + '|' + 'asOfDate=' + #p3  + '|' + 'isActiveOnly=' + #p4")
	public List<RoleMember> getRoleMembersCached(String namespaceCode, String roleName, Map<String, String> qualification, DateTime asOfDate, boolean isActiveOnly) {
		
		// the return value
		List<RoleMember> roleMembers = new ArrayList<RoleMember>();
		Role role = getRoleService().getRoleByNamespaceCodeAndName(namespaceCode, roleName);		
		if(role != null) {		
			if ( (asOfDate.toLocalDate().toDateTimeAtStartOfDay().equals(LocalDate.now().toDateTimeAtStartOfDay())) && isActiveOnly ) {
				// invoke the non-recursive helper that delegates to KIM
			    roleMembers = getActiveRoleMembersToday(role, qualification);
			}
			else {
				// invoke the recursive helper
				roleMembers = getRoleMembers(role, qualification, asOfDate, isActiveOnly);
			}
		}
		else {
			LOG.error("Role instance for proxied role with name " + roleName + " namespace " + namespaceCode + " was null");
		}
				
		return roleMembers;		
	}
	
	
	
	// non-recursive helper that delegates most of the processing to KIM services 
	private List<RoleMember> getActiveRoleMembersToday(Role proxiedRole, Map<String, String> qualification) {
		// define the return value
		List<RoleMember> flattenedRoleMembers = new ArrayList<RoleMember>();
		
		// get the primary role members first and then call KIM services to get memberships for nested roles
		List<RoleMember> primaryRoleMembers = getPrimaryRoleMembers(proxiedRole, qualification, LocalDate.now().toDateTimeAtStartOfDay(), true);
		
		// flatten into constituent group and principal role members
		for (RoleMember primaryRoleMember : primaryRoleMembers) {
			if (MemberType.PRINCIPAL.equals(primaryRoleMember.getType())) {
				flattenedRoleMembers.add(primaryRoleMember);
			} else if (MemberType.GROUP.equals(primaryRoleMember.getType())) {
				flattenedRoleMembers.add(primaryRoleMember);
			} else if (MemberType.ROLE.equals(primaryRoleMember.getType())) {
				// recursive call to get role members
				Map<String, String> copiedQualification = new HashMap<String, String>(qualification);
				copiedQualification.putAll(primaryRoleMember.getAttributes());
				List<RoleMembership> memberships = getRoleService().getRoleMembers(Collections.singletonList(primaryRoleMember.getMemberId()), copiedQualification);
				for (RoleMembership membership : memberships) {
                    RoleMember roleMember = RoleMember.Builder.create(membership.getRoleId(), membership.getId(), membership.getMemberId(),
                            membership.getType(), null, null, membership.getQualifier(), "", "").build();

                    flattenedRoleMembers.add(roleMember);
                }
			}
		}

		return flattenedRoleMembers;	
	}
		
		
	/**
	 * Recursive helper method to search for role members.
	 * 
	 * @param role The role
	 * @param qualification The map of role qualifiers
	 * @param asOfDate The effective date of the role
	 * @param  activeOnly or not to get only active role members
	 * 
	 * @return the list of role members in {@code role}.
	 */
	private List<RoleMember> getRoleMembers(Role role, Map<String, String> qualification, DateTime asOfDate, boolean activeOnly) {
		
		// get the primary role members first and then recursively flatten them
		List<RoleMember> primaryRoleMembers = getPrimaryRoleMembers(role, qualification, asOfDate, activeOnly);
		// now recursively flatten any nested roles in the primary members into their 
		// constituent group and principal role members
		List<RoleMember> flattenedRoleMembers = new ArrayList<RoleMember>();
		for (RoleMember primaryRoleMember : primaryRoleMembers) {
			if (MemberType.PRINCIPAL.equals(primaryRoleMember.getType())) {
				flattenedRoleMembers.add(primaryRoleMember);
			} 
			else if (MemberType.GROUP.equals(primaryRoleMember.getType())) {
				flattenedRoleMembers.add(primaryRoleMember);
			} 
			else if (MemberType.ROLE.equals(primaryRoleMember.getType())) {
				Role nestedRole = getRoleService().getRole(primaryRoleMember.getMemberId());
				// recursive call to get role members
				Map<String, String> copiedQualification = new HashMap<String, String>(qualification);
				copiedQualification.putAll(primaryRoleMember.getAttributes());
				flattenedRoleMembers.addAll(getRoleMembers(nestedRole, copiedQualification, asOfDate, activeOnly));
			}
		}
		
		return flattenedRoleMembers;
	}
	
	
	// gets the primary (first-level) members of a role, i.e. may contain nested group and role members along with the principal members
	@Override
	public List<RoleMember> getPrimaryRoleMembers(Role role, Map<String, String> qualification, DateTime asOfDate, boolean isActiveOnly) {
		// define the return value
		List<RoleMember> primaryRoleMembers = new ArrayList<RoleMember>();
		
		if (role != null) {
			RoleTypeService roleTypeService = getRoleTypeService(role);
			// use predicate based filtering only on non-derived role.
			if (roleTypeService == null || !roleTypeService.isDerivedRoleType()) {
				List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(equal(KimConstants.PrimaryKeyConstants.SUB_ROLE_ID, role.getId()));
                if (isActiveOnly) {
                    predicates.add(or(isNull("activeFromDateValue"), lessThanOrEqual("activeFromDateValue", asOfDate)));
                    predicates.add(or(isNull("activeToDateValue"), greaterThan("activeToDateValue", asOfDate)));
                }

                LookupCustomizer.Builder<RoleMemberBo> builder = LookupCustomizer.Builder.create();
                builder.setPredicateTransform(AttributeTransform.getInstance());
                LookupCustomizer<RoleMemberBo> lookupCustomizer = builder.build();
                // guard for default type roles
                if(roleTypeService != null) {
                	// get the keys (name) of the qualifiers needed for membership in this role
                	List<String> attributesForExactMatch = roleTypeService.getQualifiersForExactMatch();
	                if(CollectionUtils.isNotEmpty(attributesForExactMatch)) { 
		                for (Map.Entry<String, String> qualificationEntry : qualification.entrySet()) {
		                	// do not add a qualification predicate for an attribute unless it is required for matching
		                	if(attributesForExactMatch.contains(qualificationEntry.getKey())) {
			                    Predicate predicate = equal("attributes[" + qualificationEntry.getKey() + "]", qualificationEntry.getValue());
			                    predicates.add(lookupCustomizer.getPredicateTransform().apply(predicate));
		                	}
		                }
	                }
                }
                primaryRoleMembers = getRoleService().findRoleMembers(QueryByCriteria.Builder.fromPredicates(predicates.toArray(new Predicate[] {}))).getResults();
			}
			else {
				// for derived roles just add the as-of date and active only flag to a copy of the qualification
				Map<String, String> derivedRoleQualification = new HashMap<String, String>(qualification);
				derivedRoleQualification.put("asOfDate", asOfDate.toString());
				derivedRoleQualification.put("activeOnly", String.valueOf(isActiveOnly));
				List<RoleMembership> derivedRoleMembers = roleTypeService.getRoleMembersFromDerivedRole(role.getNamespaceCode(), role.getName(), derivedRoleQualification);
				// convert the role memberships into role members
				for (RoleMembership derivedRoleMember : derivedRoleMembers) {
					RoleMember roleMember = RoleMember.Builder.create(derivedRoleMember.getRoleId(), derivedRoleMember.getId(), derivedRoleMember.getMemberId(), 
							derivedRoleMember.getType(), null, null, derivedRoleMember.getQualifier(), role.getName(), role.getNamespaceCode()).build();
				                        
					primaryRoleMembers.add(roleMember);
				}
			}
		}
		
		return primaryRoleMembers;
	}
	
	
	
	// This method provides a 'smarter' way to check for derived role membership than the default logic of getting all the
	// role memberships first and iterating through then. It is smarter because it will first check for a match on the first-level
	// principals and nested group members before descending into any nested roles. It will return as soon as a match is found.
	// Although its worst-case performance is similar to the default, its average-case performance is much better due to returning
	// immediately on finding the first match and not waiting to accumulate all the members.
	//
	// The groupIds parameter below is the complete list of groups to which the principal belongs as of today.
	@Override
	public boolean hasDerivedRole(String principalId, List<String> groupIds, String namespaceCode, String roleName, Map<String, String> qualification) {
		boolean retVal = false;
		
		String proxiedRoleNamespaceCode =  qualification.remove(KPME_PROXIED_ROLE_NAMESPACE_CODE);
		if(proxiedRoleNamespaceCode == null) {
			// use the hook to get the namespace
			proxiedRoleNamespaceCode = this.getProxiedRoleNamespaceCode();
		}
		
		String proxiedRoleName =  qualification.remove(KPME_PROXIED_ROLE_ROLE_NAME);	
		if(proxiedRoleName == null) {
			// use the hook to get the role name
			proxiedRoleName = this.getProxiedRoleName();
		}
		
		// get the as-of date and the active flag values
		DateTime asOfDate = LocalDate.now().toDateTimeAtStartOfDay();
		String asOfDateString = qualification.remove(KPME_PROXIED_ROLE_AS_OF_DATE);
		if(asOfDateString != null) {
			asOfDate = DateTime.parse(asOfDateString);
		}
		
		boolean activeOnly = true;
		String activeOnlyString = qualification.remove(KPME_PROXIED_ROLE_IS_ACTIVE_ONLY);
		if(activeOnlyString != null) {
			activeOnly = Boolean.parseBoolean(activeOnlyString);
		}
		
		Role proxiedRole = getRoleService().getRoleByNamespaceCodeAndName(proxiedRoleNamespaceCode, proxiedRoleName);
		
		if(proxiedRole != null) {
			if ( (asOfDate.toLocalDate().toDateTimeAtStartOfDay().equals(LocalDate.now().toDateTimeAtStartOfDay())) && activeOnly ) {
				// invoke the non-recursive helper that delegates to KIM
	            retVal = isActiveMemberOfRoleToday(principalId, groupIds, proxiedRole, qualification);
	        }
			else {
				// invoke the recursive helper
				retVal = isMemberOfRole(principalId, proxiedRole, qualification, asOfDate, activeOnly);
			}
		}
		else {
        	LOG.error("Role for role name " + proxiedRoleName + " with namespace code "  + proxiedRoleNamespaceCode + " was null");
        }
		
		return retVal;
	}
	
	
	
	// non-recursive helper method that is called only for checking current active membership, delegates most of the work to KIM services
	// after extracting the primary role members
	private boolean isActiveMemberOfRoleToday(String principalId, List<String> groupIds, Role proxiedRole, Map<String, String> qualification) {
		// define the return value
		boolean isActiveMemberOfRoleToday = false;
		
		// get the primary role members and iterate thru the principals, groups and then roles (in that order)
		List<RoleMember> primaryRoleMembers = getPrimaryRoleMembers(proxiedRole, qualification, LocalDate.now().toDateTimeAtStartOfDay(), true);
		List<RoleMember> nestedGroups = new ArrayList<RoleMember>();
		List<RoleMember> nestedRoles = new ArrayList<RoleMember>();
		
		// first check principals
		for (RoleMember primaryRoleMember : primaryRoleMembers) {
			if (MemberType.PRINCIPAL.equals(primaryRoleMember.getType())) {
				if (StringUtils.equals(primaryRoleMember.getMemberId(), principalId)) {
					isActiveMemberOfRoleToday = true;
					break;
				}
			} 
			else if(MemberType.GROUP.equals(primaryRoleMember.getType())) {
				// put the nested group members into a different list for faster processing below
				nestedGroups.add(primaryRoleMember);
			}
			else if(MemberType.ROLE.equals(primaryRoleMember.getType())) {
				// put the nested role members into a different list for faster processing below
				nestedRoles.add(primaryRoleMember);
			}
		}
		
		// then check each of the nested groups with the passed-in principal's group ids
		if(!isActiveMemberOfRoleToday) {
			// check for identity only since group ids is transitive closure of all parent groups
			outerLoop: {
				for (RoleMember nestedGroupMember : nestedGroups) {
					for(String groupId : groupIds) {
						if( StringUtils.equals(nestedGroupMember.getMemberId(), groupId) ){
							isActiveMemberOfRoleToday = true;
							break outerLoop;
						}
					}
				}
			}
		}
		
		// finally check the nested roles
		if(!isActiveMemberOfRoleToday) {
			for (RoleMember nestedRoleMember : nestedRoles) {
				// push the membership attributes into the qualifiers
				Map<String, String> copiedQualification = new HashMap<String, String>(qualification);
				copiedQualification.putAll(nestedRoleMember.getAttributes());
				if(getRoleService().principalHasRole(principalId, Collections.singletonList(nestedRoleMember.getMemberId()), copiedQualification)) {
					isActiveMemberOfRoleToday = true;
					break;
				}
			}
		}
		
		return isActiveMemberOfRoleToday;

	}

	
	// recursive helper method 
	private boolean isMemberOfRole(String principalId, Role role, Map<String, String> qualification, DateTime asOfDate, boolean activeOnly) {
		boolean isMemberOfRole = false;
		
		// get the primary role members and first check all 
		// the principal members or any nested group members before checking any nested role members
		List<RoleMember> primaryRoleMembers = getPrimaryRoleMembers(role, qualification, asOfDate, activeOnly);
		List<RoleMember> nestedGroups = new ArrayList<RoleMember>();
		List<RoleMember> nestedRoles = new ArrayList<RoleMember>();
		
				
		// first check principals
		for (RoleMember primaryRoleMember : primaryRoleMembers) {
			if (MemberType.PRINCIPAL.equals(primaryRoleMember.getType())) {
				if (StringUtils.equals(primaryRoleMember.getMemberId(), principalId)) {
					isMemberOfRole = true;
					break;
				}
			} else if (MemberType.GROUP.equals(primaryRoleMember.getType())) {
				// put the nested group members into a different list for faster
				// processing below
				nestedGroups.add(primaryRoleMember);
			} else if (MemberType.ROLE.equals(primaryRoleMember.getType())) {
				// put the nested role members into a different list for faster
				// processing below
				nestedRoles.add(primaryRoleMember);
			}
		}

		// then check principal's membership in each of the nested groups 
		if (!isMemberOfRole) {
			for (RoleMember nestedGroupMember : nestedGroups) {
				Group nestedGroup = getGroupService().getGroup(nestedGroupMember.getMemberId());
				if(isMemberOfGroup(principalId, nestedGroup, asOfDate, activeOnly)) {
					isMemberOfRole = true;
					break;
				}				
			}
		}

		// finally check membership in the nested roles
		if (!isMemberOfRole) {
			for (RoleMember nestedRoleMember : nestedRoles) {
				Role nestedRole = getRoleService().getRole(nestedRoleMember.getMemberId());
				// push the membership attributes into the qualifiers
				Map<String, String> copiedQualification = new HashMap<String, String>(qualification);
				copiedQualification.putAll(nestedRoleMember.getAttributes());
				// recursive call
				if (isMemberOfRole(principalId, nestedRole, copiedQualification, asOfDate, activeOnly) ) {
					isMemberOfRole = true;
					break;
				}
			}
		}
				
		return isMemberOfRole;
	}
	
	
	
	@Override
	public boolean isMemberOfGroup(String principalId, Group group, DateTime asOfDate, boolean activeOnly) {
		// the return value
		boolean isMemberOfGroup = false;
		
		if ( (asOfDate.toLocalDate().toDateTimeAtStartOfDay().equals(LocalDate.now().toDateTimeAtStartOfDay())) && activeOnly ) {
			// use the group service to get all the group ids in which the principal is a member.
			List<String> groupIds = getGroupService().getGroupIdsByPrincipalId(principalId);
			isMemberOfGroup = groupIds.contains(group.getId());
        }
		else {
			List<GroupMember> primaryGroupMembers = new ArrayList<GroupMember>();
			if(group != null) {
				//get the primary group members via a predicate-based query
				List<Predicate> predicates = new ArrayList<Predicate>();
	            predicates.add(equal(KIMPropertyConstants.GroupMember.GROUP_ID, group.getId()));
	            if (activeOnly) {
	                predicates.add(or(isNull("activeFromDateValue"), lessThanOrEqual("activeFromDateValue", asOfDate)));
	                predicates.add(or(isNull("activeToDateValue"), greaterThan("activeToDateValue", asOfDate)));
	            }
	            primaryGroupMembers = getGroupService().findGroupMembers(QueryByCriteria.Builder.fromPredicates(predicates.toArray(new Predicate[] {}))).getResults();
			}
			
			// iterate through the primary group members, first checking all principal type members before 
			// descending recursively into any nested group members.
			List<GroupMember> nestedGroups = new ArrayList<GroupMember>();
			for(GroupMember groupMember: primaryGroupMembers) {
				if(KimConstants.KimGroupMemberTypes.PRINCIPAL_MEMBER_TYPE.equals(groupMember.getType())) {
					if(StringUtils.equals(groupMember.getMemberId(), principalId)) {
						isMemberOfGroup = true;
						break;
					}
				}
				else if(KimConstants.KimGroupMemberTypes.GROUP_MEMBER_TYPE.equals(groupMember.getType())) {
					// put the nested group members into a different list for faster processing below
					nestedGroups.add(groupMember);
				}
			}
			
			// check nested groups recursively if not found amongst primary principal type members
			if(!isMemberOfGroup) {
				for(GroupMember nestedGroupMember: nestedGroups) {
					Group nestedGroup = getGroupService().getGroup(nestedGroupMember.getMemberId());
					// recursive call to check nested group
					if(isMemberOfGroup(principalId, nestedGroup, asOfDate, activeOnly)) {
						isMemberOfGroup = true;
						break;
					}
				}
			}
		}		
		
		return isMemberOfGroup;
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

	
	
		
}
