package org.kuali.kpme.core.location.service;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.location.LocationBo;
import org.kuali.kpme.core.location.dao.LocationDao;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.role.location.LocationPrincipalRoleMemberBo;
import org.kuali.kpme.core.service.role.KPMERoleService;
import org.kuali.rice.kim.api.role.RoleMember;
import org.kuali.rice.kim.impl.role.RoleMemberBo;

import java.util.HashSet;
import java.util.Set;

public class LocationInternalServiceImpl implements LocationInternalService {

    private LocationDao locationDao;
    private KPMERoleService kpmeRoleService;


    @Override
    public LocationBo getLocationWithRoleData(String hrLocationId) {
        LocationBo locationObj = locationDao.getLocation(hrLocationId);

        if (locationObj != null) {
            populateLocationRoleMembers(locationObj, locationObj.getEffectiveLocalDate());
        }

        return locationObj;
    }

    public LocationBo getLocationWithRoleData(String location, LocalDate asOfDate) {
        LocationBo locationObj = locationDao.getLocation(location, asOfDate);

        if (locationObj != null) {
            populateLocationRoleMembers(locationObj, asOfDate);
        }

        return locationObj;
    }

    protected void populateLocationRoleMembers(LocationBo location, LocalDate asOfDate) {
        if (location != null && asOfDate != null
                && CollectionUtils.isEmpty(location.getRoleMembers()) && CollectionUtils.isEmpty(location.getInactiveRoleMembers())) {
            Set<RoleMember> roleMembers = new HashSet<RoleMember>();
            roleMembers.addAll(kpmeRoleService.getRoleMembersInLocation(KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_LOCATION_VIEW_ONLY.getRoleName(), location.getLocation(), asOfDate.toDateTimeAtStartOfDay(), false));
            roleMembers.addAll(kpmeRoleService.getRoleMembersInLocation(KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), location.getLocation(), asOfDate.toDateTimeAtStartOfDay(), false));
            roleMembers.addAll(kpmeRoleService.getRoleMembersInLocation(KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_LOCATION_VIEW_ONLY.getRoleName(), location.getLocation(), asOfDate.toDateTimeAtStartOfDay(), false));
            roleMembers.addAll(kpmeRoleService.getRoleMembersInLocation(KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), location.getLocation(), asOfDate.toDateTimeAtStartOfDay(), false));

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

    public void setLocationDao(LocationDao locationDao) {
        this.locationDao = locationDao;
    }
    public void setKpmeRoleService(KPMERoleService kpmeRoleService) {
        this.kpmeRoleService = kpmeRoleService;
    }
}
