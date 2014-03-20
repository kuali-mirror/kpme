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
package org.kuali.kpme.core.location.web;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.kuali.kpme.core.api.location.Location;
import org.kuali.kpme.core.api.location.LocationContract;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrBusinessObjectMaintainableImpl;
import org.kuali.kpme.core.location.LocationBo;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.role.location.LocationPrincipalRoleMemberBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.service.HrServiceLocatorInternal;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.role.Role;
import org.kuali.rice.kim.api.role.RoleMember;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.role.RoleMemberBo;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;

@SuppressWarnings("deprecation")
public class LocationMaintainableImpl extends HrBusinessObjectMaintainableImpl {

	private static final long serialVersionUID = 130620652923528916L;

	@Override
	public HrBusinessObject getObjectById(String id) {
		return HrServiceLocatorInternal.getLocationInternalService().getLocationWithRoleData(id);
	}
	
    @Override
    public void processAfterEdit(MaintenanceDocument document, Map<String, String[]> parameters) {
        LocationBo oldMaintainableObject = (LocationBo) document.getOldMaintainableObject().getDataObject();
        LocationBo newMaintainableObject = (LocationBo) document.getNewMaintainableObject().getDataObject();
        
        LocationBo oldLocation = oldMaintainableObject;
        if(StringUtils.isNotBlank(oldMaintainableObject.getHrLocationId())) {
        	oldLocation = HrServiceLocatorInternal.getLocationInternalService().getLocationWithRoleData(oldMaintainableObject.getHrLocationId());
        } else {
        	oldLocation = HrServiceLocatorInternal.getLocationInternalService().getLocationWithRoleData(oldMaintainableObject.getLocation(), oldMaintainableObject.getEffectiveLocalDate());
        }

        //KPME-3312: reinitiate all collection lists so old and new collections are unique
        List<LocationPrincipalRoleMemberBo> oldRoleMembers = new ArrayList<LocationPrincipalRoleMemberBo>();
        oldRoleMembers.addAll(oldLocation.getRoleMembers());
        oldMaintainableObject.setRoleMembers(oldRoleMembers);

        List<LocationPrincipalRoleMemberBo> oldInactiveRoleMembers = new ArrayList<LocationPrincipalRoleMemberBo>();
        oldInactiveRoleMembers.addAll(oldLocation.getInactiveRoleMembers());
        oldMaintainableObject.setInactiveRoleMembers(oldInactiveRoleMembers);        
        
        LocationBo newLocation = newMaintainableObject;
        if(StringUtils.isNotBlank(newMaintainableObject.getHrLocationId())) {
        	newLocation = HrServiceLocatorInternal.getLocationInternalService().getLocationWithRoleData(newMaintainableObject.getHrLocationId());
        } else {
        	newLocation = HrServiceLocatorInternal.getLocationInternalService().getLocationWithRoleData(newMaintainableObject.getLocation(), newMaintainableObject.getEffectiveLocalDate());
        }

        List<LocationPrincipalRoleMemberBo> newRoleMembers = new ArrayList<LocationPrincipalRoleMemberBo>();
        newRoleMembers.addAll(newLocation.getRoleMembers());
        newMaintainableObject.setRoleMembers(newRoleMembers);

        List<LocationPrincipalRoleMemberBo> newInactiveRoleMembers = new ArrayList<LocationPrincipalRoleMemberBo>();
        newInactiveRoleMembers.addAll(newLocation.getInactiveRoleMembers());
        newMaintainableObject.setInactiveRoleMembers(newInactiveRoleMembers);

        List<Location> locationList = HrServiceLocator.getLocationService().getNewerVersionLocation(newLocation.getLocation(), newLocation.getEffectiveLocalDate());
        if (locationList.size() > 0) {
            GlobalVariables.getMessageMap().putWarningForSectionId(
                    "Location Maintenance",
                    "location.newer.exists", null);
        }
        super.processAfterEdit(document, parameters);
    }

    @Override
	protected void setNewCollectionLineDefaultValues(String collectionName, PersistableBusinessObject addLine) {
    	LocationBo location = (LocationBo) this.getBusinessObject();
    	
    	if (location.getEffectiveDate() != null) {
	    	if (addLine instanceof RoleMemberBo) {
	        	RoleMemberBo roleMember = (RoleMemberBo) addLine;
	        	roleMember.setActiveFromDateValue(new Timestamp(location.getEffectiveDate().getTime()));
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
            		Person person = KimApiServiceLocator.getPersonService().getPerson(roleMember.getMemberId());
            		if (person == null) {
            			GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRADConstants.MAINTENANCE_NEW_MAINTAINABLE +"roleMembers", 
                				"location.role.person.notExist",roleMember.getMemberId());
                		return;
            		}
            	}
            }
        }
		
        super.addNewLineToCollection(collectionName);
	}

    @Override
	public void customSaveLogic(HrBusinessObject hrObj) {
		LocationBo location = (LocationBo) hrObj;
		
		List<LocationPrincipalRoleMemberBo> newInactiveRoleMembers = createInactiveRoleMembers(location.getRoleMembers());
		List<LocationPrincipalRoleMemberBo> roleList = new ArrayList<LocationPrincipalRoleMemberBo> ();
		roleList.addAll(location.getRoleMembers());
		
    	for (LocationPrincipalRoleMemberBo newInactiveRoleMember : newInactiveRoleMembers) {
    		location.addInactiveRoleMember(newInactiveRoleMember);
    		List<LocationPrincipalRoleMemberBo> tempRoleList = location.getRoleMembers();
    		for(LocationPrincipalRoleMemberBo role : tempRoleList) {
    			if(StringUtils.isNotEmpty(role.getId())
    					&& StringUtils.isNotEmpty(newInactiveRoleMember.getId())
    					&& StringUtils.equals(role.getId(), newInactiveRoleMember.getId())) {
    				roleList.remove(role);
    			}
    		}
    	}
		    	
    	for (LocationPrincipalRoleMemberBo roleMember : roleList) {
    		RoleMember.Builder builder = RoleMember.Builder.create(roleMember);
    		builder.setAttributes(Collections.singletonMap(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location.getLocation()));
    		
    		if (StringUtils.isBlank(roleMember.getId())) {
    			KimApiServiceLocator.getRoleService().createRoleMember(builder.build());
    		} else {
    			KimApiServiceLocator.getRoleService().updateRoleMember(builder.build());
    		}
    	}
    	for (LocationPrincipalRoleMemberBo inactiveRoleMember : location.getInactiveRoleMembers()) {
    		RoleMember.Builder builder = RoleMember.Builder.create(inactiveRoleMember);
    		builder.setAttributes(Collections.singletonMap(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(),  location.getLocation()));
    		
    		if (StringUtils.isBlank(inactiveRoleMember.getId())) {
    			KimApiServiceLocator.getRoleService().createRoleMember(builder.build());
    		} else {
    			KimApiServiceLocator.getRoleService().updateRoleMember(builder.build());
    		}
    	}
	}
    
    private List<LocationPrincipalRoleMemberBo> createInactiveRoleMembers(List<LocationPrincipalRoleMemberBo> roleMembers) {
    	List<LocationPrincipalRoleMemberBo> inactiveRoleMembers = new ArrayList<LocationPrincipalRoleMemberBo>();
    	
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
    		Role newRole = KimApiServiceLocator.getRoleService().getRole(newRoleMember.getRoleId());
        	for (RoleMemberBo oldRoleMember : oldRoleMembers) {
        		Role oldRole = KimApiServiceLocator.getRoleService().getRole(oldRoleMember.getRoleId());
			  	
        		if (StringUtils.equals(newRole.getName(), oldRole.getName()) && StringUtils.equals(newRoleMember.getMemberId(), oldRoleMember.getMemberId())) {
    				LocationPrincipalRoleMemberBo.Builder builder = LocationPrincipalRoleMemberBo.Builder.create(oldRoleMember);
    				builder.setActiveToDate(new DateTime());
    				
			  		inactiveRoleMembers.add(builder.build());
			  	}
        	}
        }
        
        return inactiveRoleMembers;
    }
        
}