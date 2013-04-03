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
package org.kuali.hr.time.authorization;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.kuali.hr.core.role.KPMERole;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.krad.util.GlobalVariables;

public class AuthorizationValidationUtils {
	
    private static final Logger LOG = Logger.getLogger(AuthorizationValidationUtils.class);

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
        if (TKContext.isSystemAdmin())
            return true;

        String dept = dr.getDept();
        if (StringUtils.equals(dept, TkConstants.WILDCARD_CHARACTER)) {
            // Only system administrators can wildcard the work area if the
            // department also has a wildcard.
            return TKContext.isSystemAdmin();
        } else {
            return TkServiceLocator.getDepartmentService().getAdministratorDepartments(GlobalVariables.getUserSession().getPrincipalId()).contains(dept);
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
        return TKContext.isSystemAdmin();
    }
    
    public static boolean hasAccessToWrite(DepartmentalRule dr) {
        boolean ret = false;
        if (TKContext.isSystemAdmin())
            return true;

        if (dr != null) {
            String dept = dr.getDept();
            if (StringUtils.equals(dept, TkConstants.WILDCARD_CHARACTER)) {
                // Must be system administrator
                ret = false;
            } else {
                // Must have parent Department
                ret = TkServiceLocator.getDepartmentService().getAdministratorDepartments(GlobalVariables.getUserSession().getPrincipalId()).contains(dr.getDept());
            }
        }

        return ret;
    }

    /**
     * Static helper method to provide a single point of access for both Kuali
     * Rice maintenance page hooks as well as Lookupable filtering.
     *
     * @param dr The business object under investigation.
     *
     * @return true if readable by current context user, false otherwise.
     */
    public static boolean hasAccessToRead(DepartmentalRule dr) {
        boolean ret = false;
        if (TKContext.isSystemAdmin() || TKContext.isGlobalViewOnly())
            return true;

        if (dr != null) {
            //    dept     | workArea   | meaning
            //    ---------|------------|
            // 1: %        ,  -1        , any dept/work area valid roles
            //*2: %        ,  <defined> , must have work area <-- *
            // 3: <defined>, -1         , must have dept, any work area
            // 4: <defined>, <defined>  , must have work area or department defined
            //
            // * Not permitted.

        	String principalId = GlobalVariables.getUserSession().getPrincipalId();
        	Long workArea = dr.getWorkArea();
        	String department = dr.getDept();
        	
            if (TkConstants.WILDCARD_CHARACTER.equals(department) && TkConstants.WILDCARD_LONG.equals(workArea)) {
                // case 1
                ret = TKContext.isAnyApprover() || TKContext.isDepartmentAdmin() || TKContext.isLocationAdmin();
            } else if (TkConstants.WILDCARD_CHARACTER.equals(department)) {
                // case 2 *
                // Should not encounter this case.
                LOG.error("Invalid case encountered while scanning business objects: Wildcard Department & Defined workArea.");
            } else if (TkConstants.WILDCARD_LONG.equals(workArea)) {
                // case 3
                ret = TkServiceLocator.getDepartmentService().getAdministratorDepartments(principalId).contains(department);
            } else {
                ret = TkServiceLocator.getHRRoleService().principalHasRoleInWorkArea(principalId, KPMERole.APPROVER_DELEGATE.getRoleName(), workArea, new DateTime())
                		|| TkServiceLocator.getHRRoleService().principalHasRoleInWorkArea(principalId, KPMERole.APPROVER.getRoleName(), workArea, new DateTime())
                		|| TkServiceLocator.getDepartmentService().getAdministratorDepartments(principalId).contains(department);
            }
        }

        return ret;
    }
}
