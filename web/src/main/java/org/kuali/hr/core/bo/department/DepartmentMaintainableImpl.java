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
package org.kuali.hr.core.bo.department;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.kuali.hr.core.bo.HrBusinessObject;
import org.kuali.hr.core.bo.HrBusinessObjectMaintainableImpl;
import org.kuali.hr.core.role.KPMERoleMemberAttribute;
import org.kuali.hr.core.role.department.DepartmentPrincipalRoleMemberBo;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.role.Role;
import org.kuali.rice.kim.api.role.RoleMember;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.role.RoleMemberBo;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.Maintainable;
import org.kuali.rice.kns.web.ui.Section;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;

@SuppressWarnings("deprecation")
public class DepartmentMaintainableImpl extends HrBusinessObjectMaintainableImpl {

	private static final long serialVersionUID = -330523155799598560L;
	
	@Override
	public HrBusinessObject getObjectById(String id) {
		return HrServiceLocator.getDepartmentService().getDepartment(id);
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public List getSections(MaintenanceDocument document, Maintainable oldMaintainable) {
		List sections = super.getSections(document, oldMaintainable);
		
		for (Object obj : sections) {
			Section sec = (Section) obj;
			if (sec.getSectionId().equals("inactiveRoleMembers")) {
            	sec.setHidden(!document.isOldBusinessObjectInDocument());
            }
		}
		
		return sections;
	}
	
    @Override
    public void processAfterEdit(MaintenanceDocument document, Map<String, String[]> parameters) {
        Department oldMaintainableObject = (Department) document.getOldMaintainableObject().getBusinessObject();
        Department newMaintainableObject = (Department) document.getNewMaintainableObject().getBusinessObject();
        
        Department oldDepartment = HrServiceLocator.getDepartmentService().getDepartment(oldMaintainableObject.getDept(), oldMaintainableObject.getEffectiveLocalDate());

        oldMaintainableObject.setRoleMembers(oldDepartment.getRoleMembers());
        oldMaintainableObject.setInactiveRoleMembers(oldDepartment.getInactiveRoleMembers());
        
        Department newDepartment = HrServiceLocator.getDepartmentService().getDepartment(newMaintainableObject.getDept(), newMaintainableObject.getEffectiveLocalDate());

        newMaintainableObject.setRoleMembers(newDepartment.getRoleMembers());
        newMaintainableObject.setInactiveRoleMembers(newDepartment.getInactiveRoleMembers());
        
        super.processAfterEdit(document, parameters);
    }

    @Override
	protected void setNewCollectionLineDefaultValues(String collectionName, PersistableBusinessObject addLine) {
    	Department department = (Department) this.getBusinessObject();
    	
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
		Department department = (Department) hrObj;
		
		List<DepartmentPrincipalRoleMemberBo> newInactiveRoleMembers = createInactiveRoleMembers(department.getRoleMembers());
		
    	for (DepartmentPrincipalRoleMemberBo newInactiveRoleMember : newInactiveRoleMembers) {
    		department.addInactiveRoleMember(newInactiveRoleMember);
    	}
    	
    	for (DepartmentPrincipalRoleMemberBo roleMember : department.getRoleMembers()) {
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
        		Role newRole = KimApiServiceLocator.getRoleService().getRole(newRoleMember.getRoleId());
        		Role oldRole = KimApiServiceLocator.getRoleService().getRole(newRoleMember.getRoleId());
			  	
        		if (StringUtils.equals(newRole.getName(), oldRole.getName()) && StringUtils.equals(newRoleMember.getMemberId(), oldRoleMember.getMemberId())) {
    				DepartmentPrincipalRoleMemberBo.Builder builder = DepartmentPrincipalRoleMemberBo.Builder.create(oldRoleMember);
    				builder.setActiveToDate(new DateTime());
    				
			  		inactiveRoleMembers.add(builder.build());
			  	}
        	}
        }
        
        return inactiveRoleMembers;
    }
}
