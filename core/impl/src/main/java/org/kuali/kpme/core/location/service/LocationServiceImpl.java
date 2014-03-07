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
package org.kuali.kpme.core.location.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.location.Location;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.location.service.LocationService;
import org.kuali.kpme.core.api.paytype.PayType;
import org.kuali.kpme.core.location.LocationBo;
import org.kuali.kpme.core.location.dao.LocationDao;
import org.kuali.kpme.core.api.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.paytype.PayTypeBo;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.role.location.LocationPrincipalRoleMemberBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.role.RoleMember;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.role.RoleMemberBo;

public class LocationServiceImpl implements LocationService {

	private LocationDao locationDao;

    private static final ModelObjectUtils.Transformer<LocationBo, Location> toLocation =
            new ModelObjectUtils.Transformer<LocationBo, Location>() {
                public Location transform(LocationBo input) {
                    return LocationBo.to(input);
                };
            };

	
	@Override
	public Location getLocation(String hrLocationId) {
		return LocationBo.to(getLocationBo(hrLocationId));
	}

    protected LocationBo getLocationBo(String hrLocationId) {
        return locationDao.getLocation(hrLocationId);
    }
	
	@Override
	public int getLocationCount(String location,  LocalDate asOfDate) {
		return locationDao.getLocationCount(location, asOfDate);
	}

    @Override
	public Location getLocation(String location, LocalDate asOfDate) {
		return LocationBo.to(getLocationBo(location, asOfDate));
	}

    protected LocationBo getLocationBo(String location, LocalDate asOfDate) {
        return locationDao.getLocation(location, asOfDate);
    }


    @Override
    public List<Location> searchLocations(String userPrincipalId, String location, String locationDescr, String active, String showHistory) {
    	List<LocationBo> results = new ArrayList<LocationBo>();
    	
    	List<LocationBo> locationObjs = locationDao.searchLocations(location, locationDescr, active, showHistory);
    
    	for (LocationBo locationObj : locationObjs) {
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
    	
    	return ModelObjectUtils.transform(results, toLocation);
    }

    @Override
    public List<Location> getNewerVersionLocation(String location, LocalDate asOfDate) {
        return ModelObjectUtils.transform(locationDao.getNewerVersionLocation(location, asOfDate), toLocation);
    }

    public void setLocationDao(LocationDao locationDao) {
        this.locationDao = locationDao;
    }
}
