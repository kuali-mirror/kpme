package org.kuali.hr.time.authorization;

import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.util.TKContext;
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
        boolean ret = false;

        if (getRoles().isSystemAdmin()) {
            return true;
        }

        if (bo instanceof DepartmentalRule) {
            DepartmentalRule dr = (DepartmentalRule) bo;
            if (getRoles().getOrgAdminDepartments().contains(dr.getDept())) {
                return true;
            }
        }

        return ret;
    }

    @Override
    public boolean rolesIndicateReadAccess(BusinessObject bo) {
        return DepartmentalRuleAuthorizer.hasAccessToRead(bo);
    }

    /**
     * Static helper method to provide a single point of access for both Kuali
     * Rice maintenance page hooks as well as Lookupable filtering.
     *
     * @param bo The business object under investigation.
     *
     * @return true if readable by current context user, false otherwise.
     */
    public static boolean hasAccessToRead(BusinessObject bo) {
        boolean ret = false;

        UserRoles roles = TKContext.getUser().getCurrentRoles();
        if (roles.isSystemAdmin() || roles.isGlobalViewOnly())
            return true;

        if (bo instanceof DepartmentalRule) {
            DepartmentalRule dr = (DepartmentalRule) bo;
            if (roles.getApproverWorkAreas().contains(dr.getWorkArea()) ||
                    roles.getProcessorDepartments().contains(dr.getDept()) ||
                    roles.getOrgAdminDepartments().contains(dr.getDept())) {
                return true;
            } else if (roles.getProcessorWorkAreas().contains(dr.getWorkArea())) {
                return true;
            }
        }

        return ret;
    }

}