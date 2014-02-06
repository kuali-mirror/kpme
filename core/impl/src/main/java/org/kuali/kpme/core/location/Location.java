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
package org.kuali.kpme.core.location;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import org.kuali.kpme.core.api.location.LocationContract;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.role.location.LocationPrincipalRoleMemberBo;
import org.kuali.kpme.core.util.HrConstants;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class Location extends HrBusinessObject implements LocationContract {

	private static final String LOCATION = "location";
	
	private static final long serialVersionUID = 9015089510044249197L;
	//KPME-2273/1965 Primary Business Keys List.	
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
	            .add(LOCATION)
	            .build();
	public static final String CACHE_NAME = HrConstants.CacheNamespace.NAMESPACE_PREFIX + "Location";

	private String hrLocationId;
	private String location;
	private String timezone;
	private String description;

	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
    	return  new ImmutableMap.Builder<String, Object>()
			.put(LOCATION, this.getLocation())
			.build();
	}
	
	
    @Transient
    private List<LocationPrincipalRoleMemberBo> roleMembers = new ArrayList<LocationPrincipalRoleMemberBo>();
    
    @Transient
    private List<LocationPrincipalRoleMemberBo> inactiveRoleMembers = new ArrayList<LocationPrincipalRoleMemberBo>();

	@Override
	public String getUniqueKey() {
		return location;
	}
	
	@Override
	public String getId() {
		return getHrLocationId();
	}

	@Override
	public void setId(String id) {
		setHrLocationId(id);
	}

	public String getHrLocationId() {
		return hrLocationId;
	}

	public void setHrLocationId(String hrLocationId) {
		this.hrLocationId = hrLocationId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<LocationPrincipalRoleMemberBo> getRoleMembers() {
		return roleMembers;
	}
	
	public void addRoleMember(LocationPrincipalRoleMemberBo roleMemberBo) {
		roleMembers.add(roleMemberBo);
	}
	
	public void removeRoleMember(LocationPrincipalRoleMemberBo roleMemberBo) {
		roleMembers.remove(roleMemberBo);
	}

	public void setRoleMembers(List<LocationPrincipalRoleMemberBo> roleMembers) {
		this.roleMembers = roleMembers;
	}

	public List<LocationPrincipalRoleMemberBo> getInactiveRoleMembers() {
		return inactiveRoleMembers;
	}
	
	public void addInactiveRoleMember(LocationPrincipalRoleMemberBo inactiveRoleMemberBo) {
		inactiveRoleMembers.add(inactiveRoleMemberBo);
	}
	
	public void removeInactiveRoleMember(LocationPrincipalRoleMemberBo inactiveRoleMemberBo) {
		inactiveRoleMembers.remove(inactiveRoleMemberBo);
	}

	public void setInactiveRoleMembers(List<LocationPrincipalRoleMemberBo> inactiveRoleMembers) {
		this.inactiveRoleMembers = inactiveRoleMembers;
	}

}