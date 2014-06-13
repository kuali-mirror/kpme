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
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.department.DepartmentService;
import org.kuali.kpme.core.api.workarea.service.WorkAreaService;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.core.api.criteria.LookupCustomizer;
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
import org.kuali.rice.kim.impl.common.attribute.AttributeTransform;
import org.kuali.rice.kim.impl.role.RoleMemberBo;

public class KPMERoleServiceImpl implements KPMERoleService {
	
    /*private static final String KPME_PROXIED_ROLE_AS_OF_DATE = "KpmeProxiedRoleAsOfDate";
	private static final String KPME_PROXIED_ROLE_ROLE_NAME = "KpmeProxiedRoleRoleName";
	private static final String KPME_PROXIED_ROLE_NAMESPACE_CODE = "KpmeProxiedRoleNamespaceCode";*/

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

    public List<RoleMember> getRoleMembers(String namespaceCode, String roleName, Map<String, String> qualification, DateTime asOfDate, boolean isActiveOnly) {
        Role role = getRoleService().getRoleByNamespaceCodeAndName(namespaceCode, roleName);

        return getRoleMembers(role, qualification, asOfDate, isActiveOnly);
    }

    public boolean principalHasRole(String principalId, String namespaceCode, String roleName, Map<String, String> qualification, DateTime asOfDate) {
        boolean principalHasRole = false;

        String roleId = getRoleService().getRoleIdByNamespaceCodeAndName(namespaceCode, roleName);

        if(roleId == null) {
            return false;
        }
        if (asOfDate.compareTo(LocalDate.now().toDateTimeAtStartOfDay()) == 0) {
            principalHasRole = getRoleService().principalHasRole(principalId, Collections.singletonList(roleId), qualification);
        } else {
            List<RoleMember> roleMembers = getRoleMembers(namespaceCode, roleName, qualification, asOfDate, true);

            for (RoleMember roleMember : roleMembers) {
                if (MemberType.PRINCIPAL.equals(roleMember.getType())) {
                    if (StringUtils.equals(roleMember.getMemberId(), principalId)) {
                        principalHasRole = true;
                        break;
                    }
                } else if (MemberType.GROUP.equals(roleMember.getType())) {
                    if (HrServiceLocator.getKPMEGroupService().isMemberOfGroupWithId(principalId, roleMember.getMemberId(), asOfDate)){
                    //if (getGroupService().isMemberOfGroup(principalId, roleMember.getMemberId())) {
                        principalHasRole = true;
                        break;
                    }
                } else if (MemberType.ROLE.equals(roleMember.getType())) {
                    Role derivedRole = getRoleService().getRoleByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.DERIVED_ROLE_POSITION.getRoleName());
                    // check if the member represents the (nested) derived role 'position'
                    if(derivedRole != null && roleMember.getMemberId().equals(derivedRole.getId())) {
                        // add custom attributes
                        Map<String, String> qual = new HashMap<String, String>();
                        qual.putAll(roleMember.getAttributes());
                        qual.put("asOfDate", asOfDate.toString());
                        // return true if the principal id is a member of the (nested) derived role 'position'
                        RoleTypeService roleTypeService = getRoleTypeService(derivedRole);
                        if(roleTypeService.hasDerivedRole(principalId, new ArrayList<String>(), derivedRole.getNamespaceCode(), derivedRole.getName(), qual)) {
                            principalHasRole = true;
                            break;
                        }
                    }
                }
            }
        }

        return principalHasRole;
    }
	
	
	public boolean principalHasRoleInWorkArea(String principalId, String namespaceCode, String roleName, Long workArea, DateTime asOfDate) {
		Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), String.valueOf(workArea));
		
		return principalHasRole(principalId, namespaceCode, roleName, qualification, asOfDate);
	}

	public boolean principalHasRoleInDepartment(String principalId, String namespaceCode, String roleName, String department, String groupKeyCode, DateTime asOfDate) {
		Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
		qualification.put(KPMERoleMemberAttribute.GROUP_KEY_CODE.getRoleMemberAttributeName(), groupKeyCode);
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


    /**
     * Helper method to recursively search for role members.
     *
     * @param role The role
     * @param qualification The map of role qualifiers
     * @param asOfDate The effective date of the role
     * @param  activeOnly or not to get only active role members
     *
     * @return the list of role members in {@code role}.
     */
    private List<RoleMember> getRoleMembers(Role role, Map<String, String> qualification, DateTime asOfDate, boolean activeOnly) {
        List<RoleMember> roleMembers = new ArrayList<RoleMember>();

        if (asOfDate == null) {
            asOfDate = LocalDate.now().toDateTimeAtStartOfDay();
        }
        if (role != null) {
            RoleTypeService roleTypeService = getRoleTypeService(role);

            if (roleTypeService == null || !roleTypeService.isDerivedRoleType()) {
                List<RoleMember> primaryRoleMembers = getPrimaryRoleMembers(role, qualification, asOfDate, activeOnly);

                if (CollectionUtils.isNotEmpty(primaryRoleMembers)) {
                    // flatten into constituent group and principal role members
                    for (RoleMember primaryRoleMember : primaryRoleMembers) {
                        if (MemberType.PRINCIPAL.equals(primaryRoleMember.getType())) {
                            roleMembers.add(primaryRoleMember);
                        } else if (MemberType.GROUP.equals(primaryRoleMember.getType())) {
                            roleMembers.add(primaryRoleMember);
                        } else if (MemberType.ROLE.equals(primaryRoleMember.getType())) {
                            // recursive call to get role members
                            Map<String, String> copiedQualification = addCustomDerivedQualifications(primaryRoleMember.getAttributes(), asOfDate, activeOnly);
                            List<RoleMembership> memberships = getRoleService().getRoleMembers(Collections.singletonList(primaryRoleMember.getMemberId()), copiedQualification);
                            for (RoleMembership membership : memberships) {
                                RoleMember roleMember = RoleMember.Builder.create(membership.getRoleId(), membership.getId(), membership.getMemberId(),
                                        membership.getType(), null, null, membership.getQualifier(), "", "").build();

                                roleMembers.add(roleMember);
                            }
                        }
                    }
                }
            } else {
                Map<String, String> qual = addCustomDerivedQualifications(qualification, asOfDate, activeOnly);
                List<RoleMembership> derivedRoleMembers = roleTypeService.getRoleMembersFromDerivedRole(role.getNamespaceCode(), role.getName(), qual);

                if (CollectionUtils.isNotEmpty(derivedRoleMembers)) {
                    for (RoleMembership derivedRoleMember : derivedRoleMembers) {
                        RoleMember roleMember = RoleMember.Builder.create(derivedRoleMember.getRoleId(), derivedRoleMember.getId(), derivedRoleMember.getMemberId(),
                                derivedRoleMember.getType(), null, null, derivedRoleMember.getQualifier(), role.getName(), role.getNamespaceCode()).build();

                        roleMembers.add(roleMember);
                    }
                }
            }
        }

        return roleMembers;
    }

    protected List<RoleMember> getPrimaryRoleMembers(Role role, Map<String, String> qualification, DateTime asOfDate, boolean isActiveOnly) {
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


                //LookupCustomizer<RoleMemberBo> lookupCustomizer = builder.build();
                // guard for default type roles
                if(roleTypeService != null) {
                    // get the keys (name) of the qualifiers needed for membership in this role
                    List<String> attributesForExactMatch = roleTypeService.getQualifiersForExactMatch();
                    if(CollectionUtils.isNotEmpty(attributesForExactMatch)) {
                        if (attributesForExactMatch.size() <= 1) {
                            for (Map.Entry<String, String> qualificationEntry : qualification.entrySet()) {
                                // do not add a qualification predicate for an attribute unless it is required for matching

                                if (attributesForExactMatch.contains(qualificationEntry.getKey())) {
                                    predicates.add(equal("attributes[" + qualificationEntry.getKey() + "]", qualificationEntry.getValue()));
                                }
                            }
                            primaryRoleMembers = getRoleService().findRoleMembers(QueryByCriteria.Builder.fromPredicates(predicates.toArray(new Predicate[predicates.size()]))).getResults();

                        } else {
                            //rice's transformation doesn't work with more than one attribute.
                            // here is a terrible hack
                            List<RoleMember> intersectedMembers = null;
                            for (Map.Entry<String, String> qualificationEntry : qualification.entrySet()) {
                                // do not add a qualification predicate for an attribute unless it is required for matching

                                if (attributesForExactMatch.contains(qualificationEntry.getKey())) {
                                    Predicate attrPredicates = equal("attributes[" + qualificationEntry.getKey() + "]", qualificationEntry.getValue());
                                    Predicate[] tempPredicates = predicates.toArray(new Predicate[predicates.size()+1]);
                                    tempPredicates[predicates.size()] = attrPredicates;
                                    List<RoleMember> tempMembers = new ArrayList<RoleMember>(getRoleService().findRoleMembers(QueryByCriteria.Builder.fromPredicates(tempPredicates)).getResults());
                                    if (intersectedMembers == null) {
                                        intersectedMembers = new ArrayList<>();
                                        intersectedMembers.addAll(tempMembers);
                                    } else {
                                        intersectedMembers = intersect( intersectedMembers, tempMembers);
                                    }
                                }
                            }
                            primaryRoleMembers = intersectedMembers;

                        }

                    }
                }
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

    protected <T> List <T> intersect (Collection <? extends T> a, Collection <? extends T> b){
        List <T> result = new ArrayList <T> ();
        for (T t: a){
            if (b.remove (t)) result.add (t);
        }

        return result;
    }
	
	public List<RoleMember> getRoleMembersInWorkArea(String namespaceCode, String roleName, Long workArea, DateTime asOfDate, boolean isActiveOnly) {
		Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), String.valueOf(workArea));
		
		return getRoleMembers(namespaceCode, roleName, qualification, asOfDate, isActiveOnly);
	}

	public List<RoleMember> getRoleMembersInDepartment(String namespaceCode, String roleName, String department, String groupKeyCode, DateTime asOfDate, boolean isActiveOnly) {
		Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
        qualification.put(KPMERoleMemberAttribute.GROUP_KEY_CODE.getRoleMemberAttributeName(), groupKeyCode);
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
		List<Long> workAreas = new ArrayList<Long>();
		Role role = getRoleService().getRoleByNamespaceCodeAndName(namespaceCode, roleName);
		if(role != null) {
			workAreas = getWorkAreasForPrincipalInRoles(principalId, Collections.singletonList(role.getId()), asOfDate, isActiveOnly);
		}
		return workAreas;       
	}
    
    public List<String> getDepartmentsForPrincipalInRoles(String principalId, List<String> roleIds, DateTime asOfDate, boolean isActiveOnly) {
        Set<String> departments = new HashSet<String>();
        
        Map<String, String> qualifiers = new HashMap<String, String>();
        qualifiers.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), "%");
        qualifiers.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), "%");
        qualifiers.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), "%");
        qualifiers.put(KPMERoleMemberAttribute.GROUP_KEY_CODE.getRoleMemberAttributeName(), "%");
        List<Map<String, String>> roleQualifiers = getRoleQualifiers(principalId, roleIds, qualifiers, asOfDate, isActiveOnly);

        for (Map<String, String> roleQualifier : roleQualifiers) {
            String department = MapUtils.getString(roleQualifier, KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName());
            String groupKeyCode = MapUtils.getString(roleQualifier, KPMERoleMemberAttribute.GROUP_KEY_CODE.getRoleMemberAttributeName());
            if (department != null
                    && groupKeyCode != null) {
                departments.add(groupKeyCode + "|" + department);
            }
        }
        
        List<String> locations = getLocationsForPrincipalInRoles(principalId, roleIds, asOfDate, isActiveOnly);
        departments.addAll(getDepartmentService().getDepartmentValuesWithLocations(locations, asOfDate.toLocalDate()));

        return new ArrayList<String>(departments);
    }


	public List<String> getDepartmentsForPrincipalInRole(String principalId, String namespaceCode, String roleName, DateTime asOfDate, boolean isActiveOnly) {
		List<String> departments = new ArrayList<String>();		
		Role role = getRoleService().getRoleByNamespaceCodeAndName(namespaceCode, roleName);
		if(role != null) {
			departments = getDepartmentsForPrincipalInRoles(principalId, Collections.singletonList(role.getId()), asOfDate, isActiveOnly);
		}
		
		// TODO:
        // Do we want to pass groupKeyCode instead of location to speed up the performance?
		List<String> locations = getLocationsForPrincipalInRole(principalId, namespaceCode, roleName, asOfDate, isActiveOnly);
		departments.addAll(getDepartmentService().getDepartmentValuesWithLocations(locations, asOfDate.toLocalDate()));

		return new ArrayList<String>(departments);
	}

    public List<String> getLocationsForPrincipalInRoles(String principalId, List<String> roleIds, DateTime asOfDate, boolean isActiveOnly) {
        Set<String> locations = new HashSet<String>();

        Map<String, String> qualifiers = new HashMap<String, String>();
        qualifiers.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), "%");
        qualifiers.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), "%");
        qualifiers.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), "%");
        qualifiers.put(KPMERoleMemberAttribute.GROUP_KEY_CODE.getRoleMemberAttributeName(), "%");
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
		List<String> locations = new ArrayList<String>();	
		Role role = getRoleService().getRoleByNamespaceCodeAndName(namespaceCode, roleName);
		if(role != null) {
			locations = getLocationsForPrincipalInRoles(principalId, Collections.singletonList(role.getId()), asOfDate, isActiveOnly);
		}
		return locations;

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
     * Helper method to gather up all qualifiers for the given {@code principalId} in {@code roleIds}.
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

    protected Map<String, String> addCustomDerivedQualifications(Map<String, String> qualificiation, DateTime asOfDate, boolean activeOnly) {
        Map<String, String> qual = new HashMap<String, String>();
        qual.putAll(qualificiation);
        qual.put("asOfDate", asOfDate.toString());
        qual.put("activeOnly", Boolean.toString(activeOnly));

        return qual;
    }

}
