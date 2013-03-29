package org.kuali.hr.core.role.service;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;
import static org.kuali.rice.core.api.criteria.PredicateFactory.greaterThan;
import static org.kuali.rice.core.api.criteria.PredicateFactory.in;
import static org.kuali.rice.core.api.criteria.PredicateFactory.isNull;
import static org.kuali.rice.core.api.criteria.PredicateFactory.lessThanOrEqual;
import static org.kuali.rice.core.api.criteria.PredicateFactory.or;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.kuali.hr.core.role.KPMERoleMemberAttribute;
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
    
    private GroupService groupService;
	private KimTypeInfoService kimTypeInfoService;
	private RoleService roleService;
	
	public abstract String getRoleIdByName(String roleName);
	
	public abstract Role getRoleByName(String roleName);

	public boolean principalHasRole(String principalId, String roleName, DateTime asOfDate) {
		Map<String, String> qualification = new HashMap<String, String>();
		
		return principalHasRole(principalId, roleName, qualification, asOfDate);
	}
	
	public boolean principalHasRole(String principalId, String roleName, Map<String, String> qualification, DateTime asOfDate) {
		boolean principalHasRole = false;
		
		String roleId = getRoleIdByName(roleName);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(equal(KimConstants.PrimaryKeyConstants.SUB_ROLE_ID, roleId));
		predicates.add(or(isNull("activeFromDateValue"), lessThanOrEqual("activeFromDateValue", asOfDate)));
		predicates.add(or(isNull("activeToDateValue"), greaterThan("activeToDateValue", new Date())));
		
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

	public boolean principalHasRoleInWorkArea(String principalId, String roleName, Long workArea, DateTime asOfDate) {
		Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), String.valueOf(workArea));
		
		return principalHasRole(principalId, roleName, qualification, asOfDate);
	}

	public boolean principalHasRoleInDepartment(String principalId, String roleName, String department, DateTime asOfDate) {
		Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
		
		return principalHasRole(principalId, roleName, qualification, asOfDate);
	}

	public boolean principalHasRoleInLocation(String principalId, String roleName, String location, DateTime asOfDate) {
		Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
		
		return principalHasRole(principalId, roleName, qualification, asOfDate);
	}
	
	public List<RoleMember> getRoleMembers(String roleName, DateTime asOfDate, boolean getActiveOnly) {
		Map<String, String> qualification = new HashMap<String, String>();
		
		return getRoleMembers(roleName, qualification, asOfDate, getActiveOnly);
	}
	
	public List<RoleMember> getRoleMembers(String roleName, Map<String, String> qualification, DateTime asOfDate, boolean getActiveOnly) {
		Role role = getRoleByName(roleName);
		
		return getRoleMembers(role, qualification, asOfDate, getActiveOnly);
	}
	
	private List<RoleMember> getRoleMembers(Role role, Map<String, String> qualification, DateTime asOfDate, boolean getActiveOnly) {
		List<RoleMember> roleMembers = new ArrayList<RoleMember>();
		
		if (role != null) {
			RoleTypeService roleTypeService = getRoleTypeService(role);
			
			if (roleTypeService == null || !roleTypeService.isDerivedRoleType()) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				predicates.add(equal(KimConstants.PrimaryKeyConstants.SUB_ROLE_ID, role.getId()));
				if (getActiveOnly) {
					predicates.add(or(isNull("activeFromDateValue"), lessThanOrEqual("activeFromDateValue", asOfDate)));
					predicates.add(or(isNull("activeToDateValue"), greaterThan("activeToDateValue", new Date())));
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
	
	public List<RoleMember> getRoleMembersInWorkArea(String roleName, Long workArea, DateTime asOfDate, boolean getActiveOnly) {
		Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), String.valueOf(workArea));
		
		return getRoleMembers(roleName, qualification, asOfDate, getActiveOnly);
	}

	public List<RoleMember> getRoleMembersInDepartment(String roleName, String department, DateTime asOfDate, boolean getActiveOnly) {
		Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
		
		return getRoleMembers(roleName, qualification, asOfDate, getActiveOnly);
	}

	public List<RoleMember> getRoleMembersInLocation(String roleName, String location, DateTime asOfDate, boolean getActiveOnly) {
		Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
		
		return getRoleMembers(roleName, qualification, asOfDate, getActiveOnly);
	}
	
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
		
		return new ArrayList<Long>(workAreas);
	}
	
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
		
		return new ArrayList<String>(departments);
	}
	
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
	
	private List<Map<String, String>> getRoleQualifiers(String principalId, Role role, DateTime asOfDate, boolean getActiveOnly) {
		List<Map<String, String>> roleQualifiers = new ArrayList<Map<String, String>>();

		if (role != null) {
			RoleTypeService roleTypeService = getRoleTypeService(role);

			if (roleTypeService == null || !roleTypeService.isDerivedRoleType()) {
				List<RoleMember> principalIdRoleMembers = getPrincipalIdRoleMembers(principalId, role, asOfDate, getActiveOnly);
				
		        for (RoleMember principalIdRoleMember : principalIdRoleMembers) {
		            if (MemberType.PRINCIPAL.equals(principalIdRoleMember.getType()) || MemberType.GROUP.equals(principalIdRoleMember.getType())) {
		            	roleQualifiers.add(principalIdRoleMember.getAttributes());
		            } else if (MemberType.ROLE.equals(principalIdRoleMember.getType())) {
		            	Role nestedRole = getRoleService().getRole(principalIdRoleMember.getMemberId());
		                
		            	roleQualifiers.addAll(getRoleQualifiers(principalId, nestedRole, asOfDate, getActiveOnly));
		            }
		        }
			} else {
				Map<String, String> qualification = new HashMap<String, String>();
	        	List<RoleMembership> derivedRoleMembers = roleTypeService.getRoleMembersFromDerivedRole(role.getNamespaceCode(), role.getName(), qualification);
	            
				for (RoleMembership derivedRoleMember : derivedRoleMembers) {
					if (MemberType.PRINCIPAL.equals(derivedRoleMember.getType())) {
						if (StringUtils.equals(derivedRoleMember.getMemberId(), principalId)) {
							roleQualifiers.add(derivedRoleMember.getQualifier());
						}
					} else if (MemberType.GROUP.equals(derivedRoleMember.getType())) {
						if (getGroupService().isMemberOfGroup(principalId, derivedRoleMember.getMemberId())) {
							roleQualifiers.add(derivedRoleMember.getQualifier());
						}
					}
				}
			}
		}
        
        return roleQualifiers;
	}
	
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
			predicates.add(or(isNull("activeToDateValue"), greaterThan("activeToDateValue", new Date())));
		}
		
		return getRoleService().findRoleMembers(QueryByCriteria.Builder.fromPredicates(predicates.toArray(new Predicate[] {}))).getResults();
	}
	
	protected String getDerivedRoleServiceName(String kimTypeId) {
		KimType kimType = getKimTypeInfoService().getKimType(kimTypeId);
		
		return kimType != null ? kimType.getServiceName() : null;
	}
	
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

}