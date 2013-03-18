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
package org.kuali.hr.time.department;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.Maintainable;
import org.kuali.rice.kns.web.ui.Section;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DepartmentMaintainableImpl extends HrBusinessObjectMaintainableImpl {

	private static final long serialVersionUID = -330523155799598560L;

    @Override
	protected void setNewCollectionLineDefaultValues(String arg0,
			PersistableBusinessObject arg1) {
    	if(arg1 instanceof TkRole){
    		TkRole role = (TkRole)arg1;
    		Department dept = (Department) this.getBusinessObject();
    		role.setEffectiveDate(dept.getEffectiveDate());
    	}
		super.setNewCollectionLineDefaultValues(arg0, arg1);
	}

	@Override
    public void processAfterEdit( MaintenanceDocument document, Map<String,String[]> parameters ) {
        Department dOld = (Department)document.getOldMaintainableObject().getBusinessObject();
        Department dNew = (Department)document.getNewMaintainableObject().getBusinessObject();

        TkServiceLocator.getDepartmentService().populateDepartmentRoles(dOld);
        TkServiceLocator.getDepartmentService().populateDepartmentRoles(dNew);
        super.processAfterEdit(document, parameters);
    }
	
	@Override
    public void addNewLineToCollection(String collectionName) {
        if (collectionName.equals("roles")) {
        	TkRole aRole = (TkRole)newCollectionLines.get(collectionName );
            if ( aRole != null ) {
            	if(!aRole.getPrincipalId().isEmpty()) {
            		Principal aPerson = KimApiServiceLocator.getIdentityService().getPrincipal(aRole.getPrincipalId());
            		if(aPerson == null) {
            			GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRADConstants.MAINTENANCE_NEW_MAINTAINABLE +"roles", 
                				"dept.role.person.notExist",aRole.getPrincipalId());
                		return;
            		}
            	}
            }
        }
        super.addNewLineToCollection(collectionName);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List getSections(MaintenanceDocument document,
			Maintainable oldMaintainable) {
		List sections = super.getSections(document, oldMaintainable);
		for (Object obj : sections) {
			Section sec = (Section) obj;
			if (document.isOldBusinessObjectInDocument()
					&& sec.getSectionId().equals("inactiveRoles")) {
				sec.setHidden(false);
			} else if (!document.isOldBusinessObjectInDocument()
					&& sec.getSectionId().equals("inactiveRoles")) {
				sec.setHidden(true);
			}
		}
		return sections;
	}

	@Override
	public HrBusinessObject getObjectById(String id) {
		return TkServiceLocator.getDepartmentService().getDepartment(id);
	}

    @Override
	public void customSaveLogic(HrBusinessObject hrObj) {
		Department department = (Department) hrObj;
		
		List<TkRole> roles = new ArrayList<TkRole>();
		roles.addAll(department.getRoles());
		roles.addAll(department.getInactiveRoles());
		roles.addAll(createInactiveRoles(department.getRoles()));

		for (TkRole role : roles) {
			role.setDepartmentObj(department);
			role.setUserPrincipalId(TKContext.getPrincipalId());
		}
		department.setRoles(roles);
		
		TkServiceLocator.getTkRoleService().saveOrUpdate(roles);
	}
    
    private List<TkRole> createInactiveRoles(List<TkRole> activeRoles) {
    	List<TkRole> inactiveRoles = new ArrayList<TkRole>();
    	
        List<TkRole> oldRoles = new ArrayList<TkRole>();
        List<TkRole> newRoles = new ArrayList<TkRole>();
        
        for (TkRole activeRole : activeRoles) {
		  	if (!StringUtils.isEmpty(activeRole.getHrRolesId())) {
		  		oldRoles.add(activeRole);
		  	} else {
		  		newRoles.add(activeRole);
		  	}
        }
        
        for (TkRole newRole : newRoles) {
        	for (TkRole oldRole : oldRoles) {
        		if (StringUtils.equals(newRole.getRoleName(), oldRole.getRoleName()) 
       	      	 && StringUtils.equals(newRole.getPrincipalId(), oldRole.getPrincipalId())) {
        			TkRole newInactiveRole = new TkRole();
        			newInactiveRole.setPrincipalId(oldRole.getPrincipalId());
        			newInactiveRole.setRoleName(oldRole.getRoleName());
        			newInactiveRole.setWorkArea(oldRole.getWorkArea());
        			newInactiveRole.setDepartment(oldRole.getDepartment());
        			newInactiveRole.setChart(oldRole.getChart());
        			newInactiveRole.setHrDeptId(oldRole.getHrDeptId());
        			newInactiveRole.setPositionNumber(oldRole.getPositionNumber());
        			newInactiveRole.setExpirationDate(oldRole.getExpirationDate());
        			newInactiveRole.setEffectiveDate(newRole.getEffectiveDate());
        			newInactiveRole.setTimestamp(new Timestamp(System.currentTimeMillis()));
			  		newInactiveRole.setUserPrincipalId(TKContext.getPrincipalId());
        			newInactiveRole.setActive(false);
        			
        			inactiveRoles.add(newInactiveRole);
        		}
        	}
        }
        
        return inactiveRoles;
    }
}
