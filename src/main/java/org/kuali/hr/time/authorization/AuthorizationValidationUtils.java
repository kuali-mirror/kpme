package org.kuali.hr.time.authorization;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TkConstants;

public class AuthorizationValidationUtils {

    /**
     * Indicates whether or not the current user can wildcard the work area
     * of the specified DepartmentalRule.
     *
     * @param dr The DepartmentalRule we are investigating.
     *
     * @return true if you can wildcard the WorkArea, false otherwise.
     */
    public static boolean canWildcardWorkArea(DepartmentalRule dr) {
        // Sysadmins and (Departmental OrgAdmins for their Department)
        if (TKContext.getUser().isSystemAdmin())
            return true;

        String dept = dr.getDept();
        if (StringUtils.equals(dept, TkConstants.WILDCARD_CHARACTER)) {
            // Only system administrators can wildcard the work area if the
            // department also has a wildcard.
            return TKContext.getUser().isSystemAdmin();
        } else {
            return TKContext.getUser().getDepartmentAdminAreas().contains(dept);
        }
    }

    /**
     * Can the current user use a wildcard for the department?
     *
     * @param dr The DepartmentalRule we are examining.
     *
     * @return true if so, false otherwise.
     */
    public static boolean canWildcardDepartment(DepartmentalRule dr) {
        return TKContext.getUser().isSystemAdmin();
    }
}
