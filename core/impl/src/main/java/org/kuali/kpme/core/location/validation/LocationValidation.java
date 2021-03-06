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
package org.kuali.kpme.core.location.validation;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.location.LocationBo;
import org.kuali.kpme.core.role.location.LocationPrincipalRoleMemberBo;
import org.kuali.rice.kim.impl.role.RoleMemberBo;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

import java.util.List;
import java.util.ListIterator;

public class LocationValidation extends MaintenanceDocumentRuleBase {

    @Override
    protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {

        boolean valid = true;

        PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewDataObject();

        if (pbo instanceof LocationBo) {
            LocationBo location = (LocationBo) pbo;
//            valid &= validateLocation(location);
            valid &= validateRolePresent(location.getRoleMembers(), location.getEffectiveLocalDate());
        }

        return valid;
    }

//    protected boolean validateLocation(Location newLocation) {
//        boolean valid = true;
//
//        if (newLocation.getLocation() != null && newLocation.getEffectiveDate() != null) {
//            List<Location> locations = HrServiceLocator.getLocationService().getLocations(newLocation.getLocation());
//            if (locations != null && locations.size() > 0) {
//                for(Location location : locations) {
//                    if(!location.getHrLocationId().equalsIgnoreCase(newLocation.getHrLocationId())) {
//                        this.putFieldError("location", "error.location.duplicate.exists", location.getLocation());
//                        valid = false;
//                        break;
//                    }
//                }
//            }
//        }
//
//        return valid;
//    }

    boolean validateRolePresent(List<LocationPrincipalRoleMemberBo> roleMembers, LocalDate effectiveDate) {
        boolean valid = true;
        boolean activeFlag = false;

        for (ListIterator<LocationPrincipalRoleMemberBo> iterator = roleMembers.listIterator(); iterator.hasNext(); ) {
            int index = iterator.nextIndex();
            RoleMemberBo roleMember = iterator.next();
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

	@Override
	public boolean processCustomAddCollectionLineBusinessRules(MaintenanceDocument document, String collectionName,
			Object line) {
		boolean valid = true;
		
		//TODO: Do we really need to use member type, id, role id? If there are duplicate role names listed in the drop downs, this is just going to cause confusion...
		if(line instanceof LocationPrincipalRoleMemberBo) {
			LocationPrincipalRoleMemberBo roleMember = (LocationPrincipalRoleMemberBo) line;
			LocationBo location = (LocationBo) document.getNewMaintainableObject().getDataObject();
			List<LocationPrincipalRoleMemberBo> existingRoleMembers = location.getRoleMembers();
			for(ListIterator<LocationPrincipalRoleMemberBo> iter = existingRoleMembers.listIterator(); iter.hasNext(); ) {
				int index = iter.nextIndex();
	            String prefix = "roleMembers[" + index + "].";
				LocationPrincipalRoleMemberBo existingRoleMember = iter.next();
				if(StringUtils.equals(existingRoleMember.getPrincipalId(),roleMember.getPrincipalId())) {
					if(StringUtils.equals(existingRoleMember.getRoleName(),roleMember.getRoleName())) {
						if(existingRoleMember.getActiveToDate() != null) {
							if(roleMember.getActiveFromDate().compareTo(existingRoleMember.getActiveToDate()) < 0) {
								valid &= false;
								this.putFieldError(prefix + "effectiveDate", "error.role.active.existence");
								this.putFieldError("add.roleMembers.effectiveDate", "error.role.active.duplicate");
							}
						}
						else {
							valid &= false;
							this.putFieldError(prefix + "effectiveDate", "error.role.active.existence");
							this.putFieldError("add.roleMembers.effectiveDate", "error.role.active.duplicate");
						}
					}
				}
			}
			existingRoleMembers = location.getInactiveRoleMembers();
			for(ListIterator<LocationPrincipalRoleMemberBo> iter = existingRoleMembers.listIterator(); iter.hasNext(); ) {
				int index = iter.nextIndex();
	            String prefix = "inactiveRoleMembers[" + index + "].";
				LocationPrincipalRoleMemberBo existingRoleMember = iter.next();
				if(StringUtils.equals(existingRoleMember.getPrincipalId(),roleMember.getPrincipalId())) {
					if(StringUtils.equals(existingRoleMember.getRoleName(),roleMember.getRoleName())) {
						if(existingRoleMember.getActiveToDate() != null) {
							if(roleMember.getActiveFromDate().compareTo(existingRoleMember.getActiveToDate()) < 0) {
								valid &= false;
								this.putFieldError(prefix + "effectiveDate", "error.role.inactive.existence");
								this.putFieldError("add.roleMembers.effectiveDate", "error.role.inactive.duplicate");
							}
						}
						else {
							valid &= false;
							this.putFieldError(prefix + "effectiveDate", "error.role.inactive.existence");
							this.putFieldError("add.roleMembers.effectiveDate", "error.role.inactive.duplicate");
						}
					}
				}
			}
		}
		
		return valid;
	}

}
