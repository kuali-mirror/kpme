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
package org.kuali.kpme.core.department.web;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrBusinessObjectMaintainableImpl;
import org.kuali.kpme.core.department.DepartmentBo;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.role.department.DepartmentPrincipalRoleMemberBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.service.HrServiceLocatorInternal;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.role.RoleMember;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.role.RoleMemberBo;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;

@SuppressWarnings("deprecation")
public class DepartmentMaintainableImpl extends HrBusinessObjectMaintainableImpl {

	private static final long serialVersionUID = -330523155799598560L;
	
	@Override
	public HrBusinessObject getObjectById(String id) {
		return (HrBusinessObject) HrServiceLocatorInternal.getDepartmentInternalService().getDepartmentWithRoleData(id);
	}

    @Override
    public void processAfterEdit(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        DepartmentBo oldMaintainableObject = (DepartmentBo) document.getOldMaintainableObject().getDataObject();
        DepartmentBo newMaintainableObject = (DepartmentBo) document.getNewMaintainableObject().getDataObject();
        
        DepartmentBo oldDepartment = oldMaintainableObject;
        if(StringUtils.isNotBlank(oldMaintainableObject.getHrDeptId())) {
        	oldDepartment = HrServiceLocatorInternal.getDepartmentInternalService().getDepartmentWithRoleData(oldMaintainableObject.getHrDeptId());
        } else {
        	oldDepartment = HrServiceLocatorInternal.getDepartmentInternalService().getDepartmentWithRoleData(oldMaintainableObject.getDept(), oldMaintainableObject.getEffectiveLocalDate());
        }
        
        oldMaintainableObject.setRoleMembers(oldDepartment.getRoleMembers());
        oldMaintainableObject.setInactiveRoleMembers(oldDepartment.getInactiveRoleMembers());
        
        DepartmentBo newDepartment = newMaintainableObject;
        if(StringUtils.isNotBlank(newMaintainableObject.getHrDeptId())) {
        	newDepartment = HrServiceLocatorInternal.getDepartmentInternalService().getDepartmentWithRoleData(newMaintainableObject.getHrDeptId());
        } else {
        	newDepartment = HrServiceLocatorInternal.getDepartmentInternalService().getDepartmentWithRoleData(newMaintainableObject.getDept(), newMaintainableObject.getEffectiveLocalDate());
        }
        
        newMaintainableObject.setRoleMembers(newDepartment.getRoleMembers());
        newMaintainableObject.setInactiveRoleMembers(newDepartment.getInactiveRoleMembers());
        
        super.processAfterEdit(document, requestParameters);
    }

    @Override
	protected void setNewCollectionLineDefaultValues(String collectionName, PersistableBusinessObject addLine) {
    	DepartmentBo department = (DepartmentBo) this.getBusinessObject();
    	
    	if (department.getEffectiveDate() != null) {
	    	if (addLine instanceof RoleMemberBo) {
	        	RoleMemberBo roleMember = (RoleMemberBo) addLine;
	        	roleMember.setActiveFromDateValue(new Timestamp(department.getEffectiveDate().getTime()));
	    	}
    	}
    	
    	super.setNewCollectionLineDefaultValues(collectionName, addLine);
	}
	
	@Override
    public void addNewLineToCollection(String collectionName) {
		if (collectionName.equals("roleMembers")) {
			RoleMemberBo roleMember = (RoleMemberBo) newCollectionLines.get(collectionName);
            if (roleMember != null) {
            	if(!StringUtils.isEmpty(roleMember.getMemberId())) {
            		Principal person = KimApiServiceLocator.getIdentityService().getPrincipal(roleMember.getMemberId());
            		if (person == null) {
            			GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRADConstants.MAINTENANCE_NEW_MAINTAINABLE +"roleMembers", 
                				"dept.role.person.notExist",roleMember.getMemberId());
                		return;
            		}
            	}
            }
        }
		
        super.addNewLineToCollection(collectionName);
	}

    @Override
	public void customSaveLogic(HrBusinessObject hrObj) {
		DepartmentBo department = (DepartmentBo) hrObj;
		
		List<DepartmentPrincipalRoleMemberBo> newInactiveRoleMembers = createInactiveRoleMembers(department.getRoleMembers());
		List<DepartmentPrincipalRoleMemberBo> roleList = new ArrayList<DepartmentPrincipalRoleMemberBo> ();
		roleList.addAll(department.getRoleMembers());
		
    	for (DepartmentPrincipalRoleMemberBo newInactiveRoleMember : newInactiveRoleMembers) {
    		department.addInactiveRoleMember(newInactiveRoleMember);
    		List<DepartmentPrincipalRoleMemberBo> tempRoleList = department.getRoleMembers();
    		for(DepartmentPrincipalRoleMemberBo role : tempRoleList) {
    			if(StringUtils.isNotEmpty(role.getId())
    					&& StringUtils.isNotEmpty(newInactiveRoleMember.getId())
    					&& StringUtils.equals(role.getId(), newInactiveRoleMember.getId())) {
    				roleList.remove(role);
    			}
    		}
    	}
    	
    	for (DepartmentPrincipalRoleMemberBo roleMember : roleList) {
    		RoleMember.Builder builder = RoleMember.Builder.create(roleMember);
    		builder.setAttributes(Collections.singletonMap(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department.getDept()));
    		
    		if (StringUtils.isBlank(roleMember.getId())) {
                KimApiServiceLocator.getRoleService().createRoleMember(builder.build());
    		} else {
    			KimApiServiceLocator.getRoleService().updateRoleMember(builder.build());
    		}
    	}
    	for (DepartmentPrincipalRoleMemberBo inactiveRoleMember : department.getInactiveRoleMembers()) {
    		RoleMember.Builder builder = RoleMember.Builder.create(inactiveRoleMember);
    		builder.setAttributes(Collections.singletonMap(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(),  department.getDept()));
    		
    		if (StringUtils.isBlank(inactiveRoleMember.getId())) {
    			KimApiServiceLocator.getRoleService().createRoleMember(builder.build());

    		} else {
    			KimApiServiceLocator.getRoleService().updateRoleMember(builder.build());
    		}
    	}
	}
    
    private List<DepartmentPrincipalRoleMemberBo> createInactiveRoleMembers(List<DepartmentPrincipalRoleMemberBo> roleMembers) {
    	List<DepartmentPrincipalRoleMemberBo> inactiveRoleMembers = new ArrayList<DepartmentPrincipalRoleMemberBo>();
    	
        List<RoleMemberBo> oldRoleMembers = new ArrayList<RoleMemberBo>();
        List<RoleMemberBo> newRoleMembers = new ArrayList<RoleMemberBo>();
        for (RoleMemberBo roleMember : roleMembers) {
		  	if (!StringUtils.isEmpty(roleMember.getId())) {
		  		oldRoleMembers.add(roleMember);
		  	} else {
		  		newRoleMembers.add(roleMember);
		  	}
        }
        
        for (RoleMemberBo newRoleMember : newRoleMembers) {
        	for (RoleMemberBo oldRoleMember : oldRoleMembers) {
        		if (StringUtils.equals(newRoleMember.getRoleId(), oldRoleMember.getRoleId()) 
                           && StringUtils.equals(newRoleMember.getMemberId(), oldRoleMember.getMemberId()) ) {
    				DepartmentPrincipalRoleMemberBo.Builder builder = DepartmentPrincipalRoleMemberBo.Builder.create(oldRoleMember);
    				builder.setActiveToDate(DateTime.now());
    				
			  		inactiveRoleMembers.add(builder.build());
			  	}
        	}
        }
        
        return inactiveRoleMembers;
    }


}
