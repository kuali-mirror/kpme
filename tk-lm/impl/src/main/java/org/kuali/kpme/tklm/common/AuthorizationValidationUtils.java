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
package org.kuali.kpme.tklm.common;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.tklm.time.rules.clocklocation.ClockLocationRule;
import org.kuali.kpme.tklm.time.util.TkContext;
import org.kuali.rice.krad.util.GlobalVariables;

public class AuthorizationValidationUtils {
	
    private static final Logger LOG = Logger.getLogger(AuthorizationValidationUtils.class);

    /**
     * Indicates whether or not the current user can wildcard the work area
     * of the specified DepartmentalRule.
     *
     * @param clockLocationRule The ClockLocationRule we are investigating.
     *
     * @return true if you can wildcard the WorkArea, false otherwise.
     */
    public static boolean canWildcardWorkArea(ClockLocationRule clockLocationRule) {
    	boolean canWildcardWorkArea = false;
    	
    	if (HrContext.isSystemAdmin()) {
        	return true;
    	}
    	
    	if (clockLocationRule != null) {
	    	String principalId = GlobalVariables.getUserSession().getPrincipalId();
	    	String department = clockLocationRule.getDept();
	    	String groupKeyCode = clockLocationRule.getGroupKeyCode();
	    	Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, groupKeyCode, LocalDate.now());
			String location = departmentObj != null ? departmentObj.getGroupKey().getLocationId() : null;
			
	        if (!HrConstants.WILDCARD_CHARACTER.equals(department)) {
	        	canWildcardWorkArea = HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(principalId, KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, groupKeyCode, LocalDate.now().toDateTimeAtStartOfDay())
	        			|| HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(principalId, KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, groupKeyCode, LocalDate.now().toDateTimeAtStartOfDay())
	        			|| HrServiceLocator.getKPMERoleService().principalHasRoleInLocation(principalId, KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), location, LocalDate.now().toDateTimeAtStartOfDay())
	        			|| HrServiceLocator.getKPMERoleService().principalHasRoleInLocation(principalId, KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), location, LocalDate.now().toDateTimeAtStartOfDay());
	        }
    	}
        
        return canWildcardWorkArea;
    }

    /**
     * Can the current user use a wildcard for the department?
     *
     * @param clockLocationRule The ClockLocationRule we are examining.
     *
     * @return true if so, false otherwise.
     */
    public static boolean canWildcardDepartment(ClockLocationRule clockLocationRule) {
        return HrContext.isSystemAdmin();
    }
    
    /**
     * Static helper method to provide a single point of access for both Kuali
     * Rice maintenance page hooks as well as Lookupable filtering.
     *
     * @param clockLocationRule The business object under investigation.
     * @return true if writable by current context user, false otherwise.
     * @deprecated - doesn't seem to be used anywhere
     */
    public static boolean hasAccessToWrite(ClockLocationRule clockLocationRule) {
        boolean hasAccessToWrite = false;
        
        if (HrContext.isSystemAdmin()) {
        	return true;
    	}
        
        if (clockLocationRule != null) {
	    	String principalId = GlobalVariables.getUserSession().getPrincipalId();
	    	String department = clockLocationRule.getDept();
	    	String groupKeyCode = clockLocationRule.getGroupKeyCode();
	    	Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, groupKeyCode, LocalDate.now());
			String location = departmentObj != null ? departmentObj.getGroupKey().getLocationId() : null;
	        
	        if (!HrConstants.WILDCARD_CHARACTER.equals(department)) {
	        	hasAccessToWrite = HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(principalId, KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, groupKeyCode, LocalDate.now().toDateTimeAtStartOfDay())
	        			|| HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(principalId, KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, groupKeyCode, LocalDate.now().toDateTimeAtStartOfDay())
	        			|| HrServiceLocator.getKPMERoleService().principalHasRoleInLocation(principalId, KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), location, LocalDate.now().toDateTimeAtStartOfDay())
	        			|| HrServiceLocator.getKPMERoleService().principalHasRoleInLocation(principalId, KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), location, LocalDate.now().toDateTimeAtStartOfDay());
	        }
        }

        return hasAccessToWrite;
    }
    
    /**
     * Static helper method to provide a single point of access for both Kuali
     * Rice maintenance page hooks as well as Lookupable filtering.
     *
     * @param clockLocationRule The business object under investigation.
     * @return true if readable by current context user, false otherwise.
     * @deprecated - doesn't seem to be used anywhere
     */
    public static boolean hasAccessToRead(ClockLocationRule clockLocationRule) {
        boolean hasAccessToRead = false;
        
        if (HrContext.isSystemAdmin() || HrContext.isGlobalViewOnly())
            return true;

        if (clockLocationRule != null) {
            //    dept     | workArea   | meaning
            //    ---------|------------|
            // 1: %        ,  -1        , any dept/work area valid roles
            //*2: %        ,  <defined> , must have work area <-- *
            // 3: <defined>, -1         , must have dept, any work area
            // 4: <defined>, <defined>  , must have work area or department defined
            //
            // * Not permitted.

        	String principalId = GlobalVariables.getUserSession().getPrincipalId();
        	Long workArea = clockLocationRule.getWorkArea();
        	String department = clockLocationRule.getDept();
	    	String groupKeyCode = clockLocationRule.getGroupKeyCode();
        	Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, groupKeyCode, LocalDate.now());
    		String location = departmentObj != null ? departmentObj.getGroupKey().getLocationId() : null;
            DateTime date = LocalDate.now().toDateTimeAtStartOfDay();
            if (HrConstants.WILDCARD_CHARACTER.equals(department) && HrConstants.WILDCARD_LONG.equals(workArea)) {
                // case 1
            	hasAccessToRead = HrContext.isAnyApprover() || TkContext.isDepartmentAdmin() || TkContext.isLocationAdmin();
            } else if (HrConstants.WILDCARD_CHARACTER.equals(department)) {
                // case 2 *
                // Should not encounter this case.
                LOG.error("Invalid case encountered while scanning business objects: Wildcard Department & Defined workArea.");
            } else if (HrConstants.WILDCARD_LONG.equals(workArea)) {
                // case 3
            	hasAccessToRead = HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(principalId, KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, groupKeyCode, date)
            			|| HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(principalId, KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, groupKeyCode, date)
            			|| HrServiceLocator.getKPMERoleService().principalHasRoleInLocation(principalId, KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), location, date)
            			|| HrServiceLocator.getKPMERoleService().principalHasRoleInLocation(principalId, KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), location, date);
            } else {
            	hasAccessToRead = HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER_DELEGATE.getRoleName(), workArea, date)
                		|| HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), workArea, date)
                		|| HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(principalId, KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, groupKeyCode, date)
            			|| HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(principalId, KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, groupKeyCode, date)
            			|| HrServiceLocator.getKPMERoleService().principalHasRoleInLocation(principalId, KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), location, date)
            			|| HrServiceLocator.getKPMERoleService().principalHasRoleInLocation(principalId, KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), location, date);
            }
        }

        return hasAccessToRead;
    }
    
    /**
     * For ClockLocationRule object, if a work area is defined, you can not
     * leave the department field with a wildcard. Permission for wildcarding
     * will be checked with other methods.
     *
     * @param clr The ClockLocationRule to examine.
     * @return true if valid, false otherwise.
     */
    public static boolean validateWorkAreaDeptWildcarding(ClockLocationRule clr) {
        boolean ret = true;

        if (StringUtils.equals(clr.getDept(), HrConstants.WILDCARD_CHARACTER)) {
            ret = clr.getWorkArea().equals(HrConstants.WILDCARD_LONG);
        }

        return ret;
    }
}
