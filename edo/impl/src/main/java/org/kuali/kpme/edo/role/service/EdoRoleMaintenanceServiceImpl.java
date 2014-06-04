package org.kuali.kpme.edo.role.service;

import org.kuali.kpme.edo.role.EDORole;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.rice.core.api.membership.MemberType;
import org.kuali.rice.kim.api.role.Role;
import org.kuali.rice.kim.api.role.RoleMember;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 3/19/14
 * Time: 10:40 AM
 */
public class EdoRoleMaintenanceServiceImpl implements EdoRoleMaintenanceService {

    public RoleMember assignStaffAdmin(String principalId, Map<String,String> qualifications) {
        Role adminRole = KimApiServiceLocator.getRoleService().getRoleByNamespaceCodeAndName(EdoConstants.EDO_NAME_SPACE, EDORole.EDO_ADMINISTRATOR.getEdoRole());
        RoleMember roleMember = RoleMember.Builder.create(adminRole.getId(), null, principalId, MemberType.PRINCIPAL, null, null, qualifications, null, null).build();
        RoleMember savedRoleMember = KimApiServiceLocator.getRoleService().createRoleMember(roleMember);

        //RoleMember roleMember = KimApiServiceLocator.getRoleService().assignPrincipalToRole(principalId, EdoConstants.EDO_NAME_SPACE, EDORole.EDO_ADMINISTRATOR.getEdoRole(), qualifications);

        return savedRoleMember;
    }

    public Map<String,String> buildAdminQualifications(String unit) {

        Map<String,String> quals = new HashMap<String, String>();

        List<String> departments = EdoServiceLocator.getEdoGroupTrackingService().getDistinctDepartmentList();
        List<String> schools = EdoServiceLocator.getEdoGroupTrackingService().getDistinctSchoolList();
        List<String> campuses = EdoServiceLocator.getEdoGroupTrackingService().getDistinctCampusList();

        quals.put(EdoConstants.ROLE_ADMINISTRATOR_DEPT_ID, "-");
        quals.put(EdoConstants.ROLE_ADMINISTRATOR_SCHOOL_ID, "-");
        quals.put(EdoConstants.ROLE_ADMINISTRATOR_CAMPUS_ID, "-");

        //quals.put("Department", "-");
        //quals.put("School", "-");
        //quals.put("Campus", "-");

        for (String id : departments) {
            if (id.equalsIgnoreCase(unit)) {
                //quals.put("Department", id);
                quals.put(EdoConstants.ROLE_ADMINISTRATOR_DEPT_ID, id);
            }
        }
        for (String id : schools) {
            if (id.equalsIgnoreCase(unit)) {
                //quals.put("School", id);
                quals.put(EdoConstants.ROLE_ADMINISTRATOR_SCHOOL_ID, id);
            }
        }
        for (String id : campuses) {
            if (id.equalsIgnoreCase(unit)) {
                //quals.put("Campus", id);
                quals.put(EdoConstants.ROLE_ADMINISTRATOR_CAMPUS_ID, id);
            }
        }

        // check for no hits on any category
        boolean validQuals = false;
        for (String key : quals.keySet()) {
            if (!quals.get(key).equals("-")) {
                validQuals = true;
            }
        }
        if (!validQuals) {
            // reset the map to empty
            quals = new HashMap<String,String>();
        }

        return quals;
    }
}
