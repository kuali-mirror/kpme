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
package org.kuali.kpme.core.location.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.location.Location;
import org.kuali.kpme.core.location.dao.LocationDao;
import org.kuali.kpme.core.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.role.location.LocationPrincipalRoleMemberBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.role.RoleMember;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.role.RoleMemberBo;

public class LocationServiceImpl implements LocationService {

	private LocationDao locationDao;
	
	public LocationDao getLocationDao() {
		return locationDao;
	}
	
	public void setLocationDao(LocationDao locationDao) {
		this.locationDao = locationDao;
	}
	
	@Override
	public Location getLocation(String hrLocationId) {
		Location locationObj = locationDao.getLocation(hrLocationId);
		
		if (locationObj != null) {
			populateLocationRoleMembers(locationObj, locationObj.getEffectiveLocalDate());
		}
		
		return locationObj;
	}
	
	@Override
	public int getLocationCount(String location) {
		return locationDao.getLocationCount(location);
	}
	
	public Location getLocation(String location, LocalDate asOfDate) {
		Location locationObj = locationDao.getLocation(location, asOfDate);
		
		if (locationObj != null) {
			populateLocationRoleMembers(locationObj, asOfDate);
		}
		
		return locationObj;
	}
	
    private void populateLocationRoleMembers(Location location, LocalDate asOfDate) {
    	Set<RoleMember> roleMembers = new HashSet<RoleMember>();
    	
    	if (location != null && asOfDate != null) {
	    	roleMembers.addAll(HrServiceLocator.getHRRoleService().getRoleMembersInLocation(KPMERole.TIME_LOCATION_VIEW_ONLY.getRoleName(), location.getLocation(), asOfDate.toDateTimeAtStartOfDay(), false));
	    	roleMembers.addAll(HrServiceLocator.getHRRoleService().getRoleMembersInLocation(KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), location.getLocation(), asOfDate.toDateTimeAtStartOfDay(), false));
	    	roleMembers.addAll(HrServiceLocator.getHRRoleService().getRoleMembersInLocation(KPMERole.LEAVE_LOCATION_VIEW_ONLY.getRoleName(), location.getLocation(), asOfDate.toDateTimeAtStartOfDay(), false));
	    	roleMembers.addAll(HrServiceLocator.getHRRoleService().getRoleMembersInLocation(KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), location.getLocation(), asOfDate.toDateTimeAtStartOfDay(), false));
	
	    	for (RoleMember roleMember : roleMembers) {
	    		RoleMemberBo roleMemberBo = RoleMemberBo.from(roleMember);
	    		
	    		if (roleMemberBo.isActive()) {
	    			location.addRoleMember(LocationPrincipalRoleMemberBo.from(roleMemberBo, roleMember.getAttributes()));
	    		} else {
	    			location.addInactiveRoleMember(LocationPrincipalRoleMemberBo.from(roleMemberBo, roleMember.getAttributes()));
	    		}
	    	}
    	}
    }

    @Override
    public List<Location> searchLocations(String userPrincipalId, String location, String locationDescr, String active, String showHistory) {
    	List<Location> results = new ArrayList<Location>();
    	
    	List<Location> locationObjs = locationDao.searchLocations(location, locationDescr, active, showHistory);
    
    	for (Location locationObj : locationObjs) {
        	Map<String, String> roleQualification = new HashMap<String, String>();
        	roleQualification.put(KimConstants.AttributeConstants.PRINCIPAL_ID, userPrincipalId);
        	roleQualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), locationObj.getLocation());
        	
        	if (!KimApiServiceLocator.getPermissionService().isPermissionDefinedByTemplate(KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>())
    		  || KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(userPrincipalId, KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				  KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>(), roleQualification)) {
        		results.add(locationObj);
        	}
    	}
    	
    	return results;
    }
}
