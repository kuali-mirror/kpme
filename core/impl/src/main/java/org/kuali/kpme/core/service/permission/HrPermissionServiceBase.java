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
package org.kuali.kpme.core.service.permission;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.department.DepartmentContract;
import org.kuali.kpme.core.api.department.service.DepartmentService;
import org.kuali.kpme.core.api.workarea.WorkAreaContract;
import org.kuali.kpme.core.api.workarea.service.WorkAreaService;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.common.template.Template;
import org.kuali.rice.kim.api.permission.Permission;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.rice.kim.api.role.Role;
import org.kuali.rice.kim.api.role.RoleService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.api.type.KimType;
import org.kuali.rice.kim.api.type.KimTypeInfoService;
import org.kuali.rice.kim.framework.permission.PermissionTypeService;
import org.kuali.rice.kns.kim.permission.PermissionTypeServiceBase;

import javax.xml.namespace.QName;
import java.util.*;

@SuppressWarnings("deprecation")
public abstract class HrPermissionServiceBase {
	
	private DepartmentService departmentService;
	private WorkAreaService workAreaService;
	private PermissionService permissionService;
	
	private KimTypeInfoService kimTypeInfoService;
	private RoleService roleService;
	
	/**
	 * Checks whether the given {@code principalId} is authorized to perform {@code permissionName} for the given role qualifications.
	 * 
	 * @param principalId The person to check the permission for
	 * @param permissionName The name of the permission
	 * @param qualification The map of role qualifiers for the person
	 * @param asOfDate The effective date of the permission
	 * 
	 * @return true if {@code principalId} is authorized to perform {@code permissionName}, false otherwise.
	 */
	public abstract boolean isAuthorized(String principalId, String permissionName, Map<String, String> qualification, DateTime asOfDate);
	
	/**
	 * Checks whether the given {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given permission details and role qualifications.
	 * 
	 * @param principalId The person to check the permission for
	 * @param namespaceCode The namespace for the permission template
	 * @param permissionTemplateName The name of the permission template
	 * @param permissionDetails The map of permission details for the permission
	 * @param qualification The map of role qualifiers for the person
	 * @param asOfDate The effective date of the permission
	 * 
	 * @return true if {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName}, false otherwise.
	 */
	public abstract boolean isAuthorizedByTemplate(String principalId, String namespaceCode, String permissionTemplateName, Map<String, String> permissionDetails, Map<String, String> qualification, DateTime asOfDate);
	
	/**
	 * Checks whether the given {@code principalId} is authorized to perform {@code permissionName} for the given work area.
	 * 
	 * @param principalId The person to check the permission for
	 * @param permissionName The name of the permission
	 * @param workArea The work area qualifier
	 * @param asOfDate The effective date of the permission
	 * 
	 * @return true if {@code principalId} is authorized to perform {@code permissionName} for the given work area, false otherwise.
	 */
    public boolean isAuthorizedInWorkArea(String principalId, String permissionName, Long workArea, DateTime asOfDate) {
    	Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), String.valueOf(workArea));
    	
		return isAuthorized(principalId, permissionName, qualification, asOfDate);
    }
    
	/**
	 * Checks whether the given {@code principalId} is authorized to perform {@code permissionName} for the given department.
	 * 
	 * @param principalId The person to check the permission for
	 * @param permissionName The name of the permission
	 * @param department The department qualifier
	 * @param asOfDate The effective date of the permission
	 * 
	 * @return true if {@code principalId} is authorized to perform {@code permissionName} for the given department, false otherwise.
	 */
    public boolean isAuthorizedInDepartment(String principalId, String permissionName, String department, DateTime asOfDate) {
    	Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
    	
		return isAuthorized(principalId, permissionName, qualification, asOfDate);
    }
    
	/**
	 * Checks whether the given {@code principalId} is authorized to perform {@code permissionName} for the given location.
	 * 
	 * @param principalId The person to check the permission for
	 * @param permissionName The name of the permission
	 * @param location The location qualifier
	 * @param asOfDate The effective date of the permission
	 * 
	 * @return true if {@code principalId} is authorized to perform {@code permissionName} for the given location, false otherwise.
	 */
    public boolean isAuthorizedInLocation(String principalId, String permissionName, String location, DateTime asOfDate) {
    	Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
    	
		return isAuthorized(principalId, permissionName, qualification, asOfDate);
    }
    
	/**
	 * Checks whether the given {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given work area.
	 * 
	 * @param principalId The person to check the permission for
	 * @param namespaceCode The namespace for the permission template
	 * @param permissionTemplateName The name of the permission template
	 * @param workArea The work area qualifier
	 * @param asOfDate The effective date of the permission
	 * 
	 * @return true if {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given work area, false otherwise.
	 */
	public boolean isAuthorizedByTemplateInWorkArea(String principalId, String namespaceCode, String permissionTemplateName, Long workArea, DateTime asOfDate) {
		Map<String, String> permissionDetails = new HashMap<String, String>();
		
		Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), String.valueOf(workArea));
		
		return isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, permissionDetails, qualification, asOfDate);
    }

	/**
	 * Checks whether the given {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given department.
	 * 
	 * @param principalId The person to check the permission for
	 * @param namespaceCode The namespace for the permission template
	 * @param permissionTemplateName The name of the permission template
	 * @param department The department qualifier
	 * @param asOfDate The effective date of the permission
	 * 
	 * @return true if {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given department, false otherwise.
	 */
	public boolean isAuthorizedByTemplateInDepartment(String principalId, String namespaceCode, String permissionTemplateName, String department, DateTime asOfDate) {
		Map<String, String> permissionDetails = new HashMap<String, String>();
		
		Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
		
		return isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, permissionDetails, qualification, asOfDate);
	}

	/**
	 * Checks whether the given {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given location.
	 * 
	 * @param principalId The person to check the permission for
	 * @param namespaceCode The namespace for the permission template
	 * @param permissionTemplateName The name of the permission template
	 * @param location The location qualifier
	 * @param asOfDate The effective date of the permission
	 * 
	 * @return true if {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given location, false otherwise.
	 */
	public boolean isAuthorizedByTemplateInLocation(String principalId, String namespaceCode, String permissionTemplateName, String location, DateTime asOfDate) {
		Map<String, String> permissionDetails = new HashMap<String, String>();
		
    	Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
    	
		return isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, permissionDetails, qualification, asOfDate);
	}
    
	/**
	 * Checks whether the given {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given document information.
	 * 
	 * @param principalId The person to check the permission for
	 * @param namespaceCode The namespace for the permission template
	 * @param permissionTemplateName The name of the permission template
	 * @param documentTypeName The type of the document
	 * @param documentId The id of the document
	 * @param documentStatus The status of the document
	 * @param assignments The list of assignments associated with the document
	 * 
	 * @return true if {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given document information, false otherwise.
	 */
    protected boolean isAuthorizedByTemplate(String principalId, String namespaceCode, String permissionTemplateName, String documentTypeName, String documentId, DocumentStatus documentStatus, List<Assignment> assignments, DateTime asOfDate) {
    	boolean isAuthorized = false;
        if (asOfDate == null) {
            asOfDate = DateTime.now();
        }
    	
    	for (Assignment assignment : assignments) {
            if (isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, documentTypeName, documentId, documentStatus, assignment, asOfDate)) {
            	isAuthorized = true;
            	break;
            }
        }

        return isAuthorized;
    }
    
	/**
	 * Checks whether the given {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given document information.
	 * 
	 * @param principalId The person to check the permission for
	 * @param namespaceCode The namespace for the permission template
	 * @param permissionTemplateName The name of the permission template
	 * @param documentTypeName The type of the document
	 * @param documentId The id of the document
	 * @param documentStatus The status of the document
	 * @param assignment The assignment associated with the document
	 * 
	 * @return true if {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given document information, false otherwise.
	 */
    protected boolean isAuthorizedByTemplate(String principalId, String namespaceCode, String permissionTemplateName, String documentTypeName, String documentId, DocumentStatus documentStatus, Assignment assignment, DateTime asOfDate) {
    	boolean isAuthorized = false;
    	
		Long workArea = assignment.getWorkArea();
    	WorkAreaContract workAreaObj = getWorkAreaService().getWorkAreaWithoutRoles(workArea, asOfDate.toLocalDate());

		String department = workAreaObj != null ? workAreaObj.getDept() : null;
    	DepartmentContract departmentObj = getDepartmentService().getDepartmentWithoutRoles(department, asOfDate.toLocalDate());
    	
    	String location = departmentObj != null ? departmentObj.getLocation() : null;
    	
        if (isAuthorizedByTemplateInDepartment(principalId, namespaceCode, permissionTemplateName, department, documentTypeName, documentId, documentStatus, asOfDate)
            	|| 
            isAuthorizedByTemplateInLocation(principalId, namespaceCode, permissionTemplateName, location, documentTypeName, documentId, documentStatus, asOfDate)
            	|| 
            isAuthorizedByTemplateInWorkArea(principalId, namespaceCode, permissionTemplateName, workArea, documentTypeName, documentId, documentStatus, asOfDate)) {
        	
        	isAuthorized = true;
        }
        
        return isAuthorized;
    }
    
	/**
	 * Checks whether the given {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given document information.
	 * 
	 * @param principalId The person to check the permission for
	 * @param namespaceCode The namespace for the permission template
	 * @param permissionTemplateName The name of the permission template
	 * @param documentTypeName The type of the document
	 * @param documentId The id of the document
	 * @param documentStatus The status of the document
	 * @param asOfDate The effective date of the permission
	 * 
	 * @return true if {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given document information, false otherwise.
	 */
    protected boolean isAuthorizedByTemplate(String principalId, String namespaceCode, String permissionTemplateName, String documentTypeName, String documentId, DocumentStatus documentStatus, DateTime asOfDate) {
    	Map<String, String> qualification = new HashMap<String, String>();

    	return isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, documentTypeName, documentId, documentStatus, qualification, asOfDate);
    }
    
	/**
	 * Checks whether the given {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given work area and document information.
	 * 
	 * @param principalId The person to check the permission for
	 * @param namespaceCode The namespace for the permission template
	 * @param permissionTemplateName The name of the permission template
	 * @param workArea The work area qualifier
	 * @param documentTypeName The type of the document
	 * @param documentId The id of the document
	 * @param documentStatus The status of the document
	 * @param asOfDate The effective date of the permission
	 * 
	 * @return true if {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given work area and document information, false otherwise.
	 */
    protected boolean isAuthorizedByTemplateInWorkArea(String principalId, String namespaceCode, String permissionTemplateName, Long workArea, String documentTypeName, String documentId, DocumentStatus documentStatus, DateTime asOfDate) {
    	Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), String.valueOf(workArea));
    	
    	return isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, documentTypeName, documentId, documentStatus, qualification, asOfDate) 
    			||
    		   isAuthorizedByTemplateInWorkArea(principalId, namespaceCode, permissionTemplateName, documentTypeName, documentId, documentStatus, workArea, asOfDate);
    }
    
	

	/**
	 * Checks whether the given {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given department and document information.
	 * 
	 * @param principalId The person to check the permission for
	 * @param namespaceCode The namespace for the permission template
	 * @param permissionTemplateName The name of the permission template
	 * @param department The department qualifier
	 * @param documentTypeName The type of the document
	 * @param documentId The id of the document
	 * @param documentStatus The status of the document
	 * @param asOfDate The effective date of the permission
	 * 
	 * @return true if {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given department and document information, false otherwise.
	 */
    protected boolean isAuthorizedByTemplateInDepartment(String principalId, String namespaceCode, String permissionTemplateName, String department, String documentTypeName, String documentId, DocumentStatus documentStatus, DateTime asOfDate) {
    	Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
    	
    	return isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, documentTypeName, documentId, documentStatus, qualification, asOfDate);
    }
    
	/**
	 * Checks whether the given {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given location and document information.
	 * 
	 * @param principalId The person to check the permission for
	 * @param namespaceCode The namespace for the permission template
	 * @param permissionTemplateName The name of the permission template
	 * @param location The location qualifier
	 * @param documentTypeName The type of the document
	 * @param documentId The id of the document
	 * @param documentStatus The status of the document
	 * @param asOfDate The effective date of the permission
	 * 
	 * @return true if {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given location and document information, false otherwise.
	 */
    protected boolean isAuthorizedByTemplateInLocation(String principalId, String namespaceCode, String permissionTemplateName, String location, String documentTypeName, String documentId, DocumentStatus documentStatus, DateTime asOfDate) {
    	Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
    	
    	return isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, documentTypeName, documentId, documentStatus, qualification, asOfDate);
    }
    
	/**
	 * Checks whether the given {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given document information and role qualifiers.
	 * 
	 * @param principalId The person to check the permission for
	 * @param namespaceCode The namespace for the permission template
	 * @param permissionTemplateName The name of the permission template
	 * @param documentTypeName The type of the document
	 * @param documentId The id of the document
	 * @param documentStatus The status of the document
	 * @param qualification The map of role qualifiers for the person
	 * @param asOfDate The effective date of the permission
	 * 
	 * @return true if {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given document information and role qualifiers, false otherwise.
	 */
    protected boolean isAuthorizedByTemplate(String principalId, String namespaceCode, String permissionTemplateName, String documentTypeName, String documentId, DocumentStatus documentStatus, Map<String, String> qualification, DateTime asOfDate) {
		qualification.put(KimConstants.AttributeConstants.DOCUMENT_NUMBER, documentId);
    	
    	Map<String, String> permissionDetails = new HashMap<String, String>();
		permissionDetails.put(KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME, documentTypeName);
		permissionDetails.put(KimConstants.AttributeConstants.ROUTE_STATUS_CODE, documentStatus.getCode());
    	
    	return isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, permissionDetails, qualification, asOfDate);
    }
    
    public DepartmentService getDepartmentService() {
    	return departmentService;
    }
    
    public void setDepartmentService(DepartmentService departmentService) {
    	this.departmentService = departmentService;
    }
    
    public WorkAreaService getWorkAreaService() {
    	return workAreaService;
    }
    
    public void setWorkAreaService(WorkAreaService workAreaService) {
    	this.workAreaService = workAreaService;
    }
    
    public PermissionService getPermissionService() {
		return permissionService;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	
	
	
	
	
	private boolean isAuthorizedByTemplateInWorkArea(String principalId, String namespaceCode, String permissionTemplateName, String documentTypeName, String documentId, DocumentStatus documentStatus, Long workArea, DateTime asOfDate) {
		boolean retVal = false;
		Map<String, String> permissionDetails = new HashMap<String, String>();
		permissionDetails.put(KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME, documentTypeName);
		permissionDetails.put(KimConstants.AttributeConstants.ROUTE_STATUS_CODE, documentStatus.getCode());
		
		// get the permissions that match the template
		List<Permission> permissionsByTemplate = getPermissionService().findPermissionsByTemplate(namespaceCode, permissionTemplateName);
		// now, filter the full list by the details created above
    	List<Permission> applicablePermissions = getMatchingPermissions(permissionsByTemplate, permissionDetails);
    	Set<String> roleIds = new HashSet<String>();
    	// add the role ids for each of the permissions to the set
    	for(Permission applicablePermission: applicablePermissions) {
    		roleIds.addAll(getPermissionService().getRoleIdsForPermission(applicablePermission.getNamespaceCode(), applicablePermission.getName()));
    	}
    	
    	// finally iterate thru the role ids checking if principal has membership in any of them
    	for(String roleId: roleIds) {
    		Role role = getRoleService().getRole(roleId);
    		if(role != null) {
                KimType kimType = KimApiServiceLocator.getKimTypeInfoService().getKimType(role.getKimTypeId());
                if (kimType != null
                    &&  KPMENamespace.KPME_WKFLW.getNamespaceCode().equals(kimType.getNamespaceCode())
                    && "Work Area".equals(kimType.getName())) {
                    if(HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(principalId, role.getNamespaceCode(), role.getName(), workArea, asOfDate)) {
                        retVal = true;
                        break;
                    }
                }
    		}
    	}
    	return retVal;
	}
	
	
	
	
	
	/**
     * Compare each of the passed in permissions with the given permissionDetails.  Those that
     * match are added to the result list.
     */
    protected List<Permission> getMatchingPermissions( List<Permission> permissions, Map<String, String> permissionDetails ) {
        List<String> permissionIds = new ArrayList<String>(permissions.size());
        for (Permission permission : permissions) {
            permissionIds.add(permission.getId());
        }

    	List<Permission> applicablePermissions = new ArrayList<Permission>();    	
    	if ( permissionDetails == null || permissionDetails.isEmpty() ) {
    		// if no details passed, assume that all match
    		for ( Permission perm : permissions ) {
    			applicablePermissions.add(perm);
    		}
    	} else {
    		// otherwise, attempt to match the permission details
    		// build a map of the template IDs to the type services
    		Map<String,PermissionTypeService> permissionTypeServices = getPermissionTypeServicesByTemplateId(permissions);
    		// build a map of permissions by template ID
    		Map<String, List<Permission>> permissionMap = groupPermissionsByTemplate(permissions);
    		// loop over the different templates, matching all of the same template against the type
    		// service at once
    		for ( Map.Entry<String,List<Permission>> entry : permissionMap.entrySet() ) {
    			PermissionTypeService permissionTypeService = permissionTypeServices.get( entry.getKey() );
    			List<Permission> permissionList = entry.getValue();
				applicablePermissions.addAll( permissionTypeService.getMatchingPermissions( permissionDetails, permissionList ) );    				
    		}
    	}
        applicablePermissions = Collections.unmodifiableList(applicablePermissions);
        return applicablePermissions;
    }
    
    protected Map<String,PermissionTypeService> getPermissionTypeServicesByTemplateId( Collection<Permission> permissions ) {
    	Map<String,PermissionTypeService> permissionTypeServices = new HashMap<String, PermissionTypeService>( permissions.size() );
    	for (Permission perm : permissions) {
            if(!permissionTypeServices.containsKey(perm.getTemplate().getId())) {
                permissionTypeServices.put(perm.getTemplate().getId(), getPermissionTypeService(perm.getTemplate()));
            }
    	}
    	return permissionTypeServices;
    }
    
    protected PermissionTypeService getPermissionTypeService(Template permissionTemplate) {
    	if ( permissionTemplate == null ) {
    		throw new IllegalArgumentException( "permissionTemplate may not be null" );
    	}
    	KimType kimType = getKimTypeInfoService().getKimType( permissionTemplate.getKimTypeId() );
    	String serviceName = kimType.getServiceName();
    	// if no service specified, return a default implementation
    	if ( StringUtils.isBlank( serviceName ) ) {
    		// return an instance of default service TODO: direct instantiation!
    		return new PermissionTypeServiceBase();
    	}
    	try {
	    	Object service = GlobalResourceLoader.getService(QName.valueOf(serviceName));
	    	// if we have a service name, it must exist
	    	if ( service == null ) {
				throw new RuntimeException("null returned for permission type service for service name: " + serviceName);
	    	}
	    	// whatever we retrieved must be of the correct type
	    	if ( !(service instanceof PermissionTypeService)  ) {
	    		throw new RuntimeException( "Service " + serviceName + " was not a PermissionTypeService.  Was: " + service.getClass().getName() );
	    	}
	    	return (PermissionTypeService)service;
    	} catch( Exception ex ) {
    		// sometimes service locators throw exceptions rather than returning null, handle that
    		throw new RuntimeException( "Error retrieving service: " + serviceName + " from the KimImplServiceLocator.", ex );
    	}
    }
	
    protected Map<String,List<Permission>> groupPermissionsByTemplate(Collection<Permission> permissions) {
    	Map<String,List<Permission>> results = new HashMap<String,List<Permission>>();
    	for (Permission perm : permissions) {
    		List<Permission> perms = results.get(perm.getTemplate().getId());
    		if (perms == null) {
    			perms = new ArrayList<Permission>();
    			results.put(perm.getTemplate().getId(), perms);
    		}
    		perms.add(perm);
    	}
    	return results;
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