package org.kuali.hr.time.authorization;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kns.bo.BusinessObject;

/**
 * Implements Authorization logic for the "Departmental Rules":
 *
 * ClockLocationRule
 * TimeCollectionRule
 * DeptLunchRule
 * WorkArea
 *
 * See:
 * https://wiki.kuali.org/display/KPME/Role+Security+Grid
 */
public class DepartmentalRuleAuthorizer extends TkMaintenanceDocumentAuthorizerBase {

      private static final Logger LOG = Logger.getLogger(DepartmentalRuleAuthorizer.class);

    @Override
    public boolean rolesIndicateGeneralReadAccess() {
        return getRoles().isSystemAdmin() ||
                getRoles().getOrgAdminCharts().size() > 0 ||
                getRoles().getOrgAdminDepartments().size() > 0 ||
                getRoles().getDepartmentViewOnlyDepartments().size() > 0 ||
                getRoles().isAnyApproverActive() ||
                getRoles().getProcessorDepartments().size() > 0 || getRoles().getProcessorWorkAreas().size() > 0;
    }

    @Override
    public boolean rolesIndicateGeneralWriteAccess() {
        return getRoles().isSystemAdmin() ||
                getRoles().getOrgAdminCharts().size() > 0 ||
                getRoles().getOrgAdminDepartments().size() > 0;
    }

    @Override
    public boolean rolesIndicateWriteAccess(BusinessObject bo) {
        return bo instanceof DepartmentalRule && DepartmentalRuleAuthorizer.hasAccessToWrite((DepartmentalRule)bo);
    }

    @Override
    public boolean rolesIndicateReadAccess(BusinessObject bo) {
        return bo instanceof DepartmentalRule && DepartmentalRuleAuthorizer.hasAccessToRead((DepartmentalRule)bo);
    }

    public static boolean hasAccessToWrite(DepartmentalRule dr) {
        boolean ret = false;
        UserRoles roles = TKContext.getUser().getCurrentRoles();
        if (roles.isSystemAdmin())
            return true;

        if (dr != null && roles.getOrgAdminDepartments().size() > 0) {
            String dept = dr.getDept();
            if (StringUtils.equals(dept, TkConstants.WILDCARD_CHARACTER)) {
                // Must be system administrator
                ret = false;
            } else {
                // Must have parent Department
                ret = roles.getOrgAdminDepartments().contains(dr.getDept());
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
        UserRoles roles = TKContext.getUser().getCurrentRoles();
        if (roles.isSystemAdmin() || roles.isGlobalViewOnly())
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


            if (StringUtils.equals(dr.getDept(), TkConstants.WILDCARD_CHARACTER) &&
                    dr.getWorkArea().equals(TkConstants.WILDCARD_LONG)) {
                // case 1
                ret = roles.getApproverWorkAreas().size() > 0 || roles.getProcessorDepartments().size() > 0 ||
                        roles.getProcessorWorkAreas().size() > 0 || roles.getOrgAdminCharts().size() > 0 ||
                        roles.getOrgAdminDepartments().size() > 0;
            } else if (StringUtils.equals(dr.getDept(), TkConstants.WILDCARD_CHARACTER)) {
                // case 2 *
                // Should not encounter this case.
                LOG.error("Invalid case encountered while scanning business objects: Wildcard Department & Defined workArea.");
            } else if (dr.getWorkArea().equals(TkConstants.WILDCARD_LONG)) {
                // case 3
                ret = roles.getProcessorDepartments().contains(dr.getDept()) ||
                        roles.getOrgAdminDepartments().contains(dr.getDept());
            } else {
                ret = roles.getApproverWorkAreas().contains(dr.getWorkArea()) ||
                    roles.getProcessorDepartments().contains(dr.getDept()) ||
                    roles.getOrgAdminDepartments().contains(dr.getDept()) ||
                    roles.getProcessorWorkAreas().contains(dr.getWorkArea());
            }
        }

        return ret;
    }

}