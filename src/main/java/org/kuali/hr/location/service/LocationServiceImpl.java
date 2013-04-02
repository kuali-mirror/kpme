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
package org.kuali.hr.location.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.kuali.hr.core.role.KPMERole;
import org.kuali.hr.core.role.location.LocationPrincipalRoleMemberBo;
import org.kuali.hr.location.Location;
import org.kuali.hr.location.dao.LocationDao;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kim.api.role.RoleMember;
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
			populateLocationRoleMembers(locationObj, locationObj.getEffectiveDate());
		}
		
		return locationObj;
	}
	
	@Override
	public int getLocationCount(String location) {
		return locationDao.getLocationCount(location);
	}
	
	public Location getLocation(String location, Date asOfDate) {
		Location locationObj = locationDao.getLocation(location, asOfDate);
		
		if (locationObj != null) {
			populateLocationRoleMembers(locationObj, asOfDate);
		}
		
		return locationObj;
	}
	
    private void populateLocationRoleMembers(Location location, Date asOfDate) {
    	Set<RoleMember> roleMembers = new HashSet<RoleMember>();
    	
    	roleMembers.addAll(TkServiceLocator.getTKRoleService().getRoleMembersInLocation(KPMERole.TIME_LOCATION_VIEW_ONLY.getRoleName(), location.getLocation(), new DateTime(asOfDate), false));
    	roleMembers.addAll(TkServiceLocator.getTKRoleService().getRoleMembersInLocation(KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), location.getLocation(), new DateTime(asOfDate), false));
    	roleMembers.addAll(TkServiceLocator.getLMRoleService().getRoleMembersInLocation(KPMERole.LEAVE_LOCATION_VIEW_ONLY.getRoleName(), location.getLocation(), new DateTime(asOfDate), false));
    	roleMembers.addAll(TkServiceLocator.getLMRoleService().getRoleMembersInLocation(KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), location.getLocation(), new DateTime(asOfDate), false));

    	for (RoleMember roleMember : roleMembers) {
    		RoleMemberBo roleMemberBo = RoleMemberBo.from(roleMember);
    		
    		if (roleMemberBo.isActive()) {
    			location.addRoleMember(LocationPrincipalRoleMemberBo.from(roleMemberBo, roleMember.getAttributes()));
    		} else {
    			location.addInactiveRoleMember(LocationPrincipalRoleMemberBo.from(roleMemberBo, roleMember.getAttributes()));
    		}
    	}
    }
	
    @Override
    public List<String> getViewOnlyLocations(String principalId) {
    	Set<String> locations = new HashSet<String>();
    	
    	locations.addAll(TkServiceLocator.getTKRoleService().getLocationsForPrincipalInRole(principalId, KPMERole.TIME_LOCATION_VIEW_ONLY.getRoleName(), new DateTime(), true));
    	locations.addAll(TkServiceLocator.getLMRoleService().getLocationsForPrincipalInRole(principalId, KPMERole.LEAVE_LOCATION_VIEW_ONLY.getRoleName(), new DateTime(), true));
    	
    	return new ArrayList<String>(locations);
    }
    
    @Override
    public List<String> getAdministratorLocations(String principalId) {
    	Set<String> locations = new HashSet<String>();
    	
    	locations.addAll(TkServiceLocator.getTKRoleService().getLocationsForPrincipalInRole(principalId, KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), new DateTime(), true));
    	locations.addAll(TkServiceLocator.getLMRoleService().getLocationsForPrincipalInRole(principalId, KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), new DateTime(), true));
    	
    	return new ArrayList<String>(locations);
    }

    @Override
    public List<Location> searchLocations(String location, String locationDescr, String active, String showHistory) {
        return locationDao.searchLocations(location, locationDescr, active, showHistory);
    }
}
