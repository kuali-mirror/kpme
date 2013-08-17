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
package org.kuali.kpme.core.location.validation;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.department.Department;
import org.kuali.kpme.core.location.Location;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.role.location.LocationPrincipalRoleMemberBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.kim.api.role.Role;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.role.RoleMemberBo;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

import java.util.List;
import java.util.ListIterator;

public class LocationValidation extends MaintenanceDocumentRuleBase {

    @Override
    protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {

        boolean valid = true;

        PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();

        if (pbo instanceof Location) {
            Location location = (Location) pbo;
            valid &= validateLocation(location);
            valid &= validateRolePresent(location.getRoleMembers(), location.getEffectiveLocalDate());
        }

        return valid;
    }

    protected boolean validateLocation(Location newLocation) {
        boolean valid = true;

        if (newLocation.getLocation() != null && newLocation.getEffectiveDate() != null) {
            List<Location> locations = HrServiceLocator.getLocationService().getLocations(newLocation.getLocation());
            if (locations != null && locations.size() > 0) {
                for(Location location : locations) {
                    if(!location.getHrLocationId().equalsIgnoreCase(newLocation.getHrLocationId())) {
                        this.putFieldError("location", "error.location.duplicate.exists", location.getLocation());
                        valid = false;
                        break;
                    }
                }
            }
        }

        return valid;
    }

    boolean validateRolePresent(List<LocationPrincipalRoleMemberBo> roleMembers, LocalDate effectiveDate) {
        boolean valid = true;
        boolean activeFlag = false;

        for (ListIterator<LocationPrincipalRoleMemberBo> iterator = roleMembers.listIterator(); iterator.hasNext(); ) {
            int index = iterator.nextIndex();
            RoleMemberBo roleMember = iterator.next();
            Role role = KimApiServiceLocator.getRoleService().getRole(roleMember.getRoleId());
            activeFlag |= roleMember.isActive();

            String prefix = "roleMembers[" + index + "].";

            if (roleMember.getActiveToDateValue() != null) {
                if (effectiveDate.compareTo(roleMember.getActiveToDate().toLocalDate()) >= 0
                        || roleMember.getActiveFromDateValue().compareTo(roleMember.getActiveToDateValue()) >= 0) {
                    this.putFieldError(prefix + "expirationDate", "error.role.expiration");
                    valid = false;
                }
            }
        }

        if (!activeFlag) {
            this.putGlobalError("role.required");
        }

        return valid & activeFlag;
    }

}
