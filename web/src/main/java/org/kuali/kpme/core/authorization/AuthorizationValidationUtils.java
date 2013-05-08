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
package org.kuali.kpme.core.authorization;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.department.Department;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
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
    public static boolean canWildcardWorkArea(DepartmentalRule departmentalRule) {
    	boolean canWildcardWorkArea = false;
    	
    	if (HrContext.isSystemAdmin()) {
        	return true;
    	}
    	
    	if (departmentalRule != null) {
	    	String principalId = GlobalVariables.getUserSession().getPrincipalId();
	    	String department = departmentalRule.getDept();
	    	Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, LocalDate.now());
			String location = departmentObj != null ? departmentObj.getLocation() : null;
	    	
	        if (!HrConstants.WILDCARD_CHARACTER.equals(department)) {
	        	canWildcardWorkArea = TkServiceLocator.getTKRoleService().principalHasRoleInDepartment(principalId, KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, new DateTime())
	        			|| LmServiceLocator.getLMRoleService().principalHasRoleInDepartment(principalId, KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, new DateTime())
	        			|| TkServiceLocator.getTKRoleService().principalHasRoleInLocation(principalId, KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), location, new DateTime())
	        			|| LmServiceLocator.getLMRoleService().principalHasRoleInLocation(principalId, KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), location, new DateTime());

	        	//canWildcardWorkArea = HrServiceLocator.getHRRoleService().principalHasRoleInDepartment(principalId, KPMERole.DEPARTMENT_ADMINISTRATOR.getRoleName(), department, new DateTime())
	        	//		|| HrServiceLocator.getHRRoleService().principalHasRoleInLocation(principalId, KPMERole.LOCATION_ADMINISTRATOR.getRoleName(), location, new DateTime());
	        	
	        }
    	}
        
        return canWildcardWorkArea;
    }

    /**
     * Can the current user use a wildcard for the department?
     *
     * @param dr The DepartmentalRule we are examining.
     *
     * @return true if so, false otherwise.
     */
    public static boolean canWildcardDepartment(DepartmentalRule departmentalRule) {
        return HrContext.isSystemAdmin();
    }
    
    public static boolean hasAccessToWrite(DepartmentalRule departmentalRule) {
        boolean hasAccessToWrite = false;
        
        if (HrContext.isSystemAdmin()) {
        	return true;
    	}
        
        if (departmentalRule != null) {
	    	String principalId = GlobalVariables.getUserSession().getPrincipalId();
	    	String department = departmentalRule.getDept();
	    	Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, LocalDate.now());
			String location = departmentObj != null ? departmentObj.getLocation() : null;
	        
	        if (!HrConstants.WILDCARD_CHARACTER.equals(department)) {
	        	hasAccessToWrite = TkServiceLocator.getTKRoleService().principalHasRoleInDepartment(principalId, KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, new DateTime())
	        			|| LmServiceLocator.getLMRoleService().principalHasRoleInDepartment(principalId, KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, new DateTime())
	        			|| TkServiceLocator.getTKRoleService().principalHasRoleInLocation(principalId, KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), location, new DateTime())
	        			|| LmServiceLocator.getLMRoleService().principalHasRoleInLocation(principalId, KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), location, new DateTime());
	        	
	        	//hasAccessToWrite = HrServiceLocator.getHRRoleService().principalHasRoleInDepartment(principalId, KPMERole.DEPARTMENT_ADMINISTRATOR.getRoleName(), department, new DateTime())
	        	//		|| HrServiceLocator.getHRRoleService().principalHasRoleInLocation(principalId, KPMERole.LOCATION_ADMINISTRATOR.getRoleName(), location, new DateTime());
	        	
	        	
	        }
        }

        return hasAccessToWrite;
    }
    
    /**
     * Static helper method to provide a single point of access for both Kuali
     * Rice maintenance page hooks as well as Lookupable filtering.
     *
     * @param dr The business object under investigation.
     *
     * @return true if readable by current context user, false otherwise.
     */
    public static boolean hasAccessToRead(DepartmentalRule departmentalRule) {
        boolean hasAccessToRead = false;
        
        if (HrContext.isSystemAdmin() || HrContext.isGlobalViewOnly())
            return true;

        if (departmentalRule != null) {
            //    dept     | workArea   | meaning
            //    ---------|------------|
            // 1: %        ,  -1        , any dept/work area valid roles
            //*2: %        ,  <defined> , must have work area <-- *
            // 3: <defined>, -1         , must have dept, any work area
            // 4: <defined>, <defined>  , must have work area or department defined
            //
            // * Not permitted.

        	String principalId = GlobalVariables.getUserSession().getPrincipalId();
        	Long workArea = departmentalRule.getWorkArea();
        	String department = departmentalRule.getDept();
        	Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, LocalDate.now());
    		String location = departmentObj != null ? departmentObj.getLocation() : null;
            
            if (HrConstants.WILDCARD_CHARACTER.equals(department) && HrConstants.WILDCARD_LONG.equals(workArea)) {
                // case 1
            	hasAccessToRead = HrContext.isAnyApprover() || HrContext.isDepartmentAdmin() || HrContext.isLocationAdmin();
            } else if (HrConstants.WILDCARD_CHARACTER.equals(department)) {
                // case 2 *
                // Should not encounter this case.
                LOG.error("Invalid case encountered while scanning business objects: Wildcard Department & Defined workArea.");
            } else if (HrConstants.WILDCARD_LONG.equals(workArea)) {
                // case 3
            	hasAccessToRead = TkServiceLocator.getTKRoleService().principalHasRoleInDepartment(principalId, KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, new DateTime())
            			|| LmServiceLocator.getLMRoleService().principalHasRoleInDepartment(principalId, KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, new DateTime())
            			|| TkServiceLocator.getTKRoleService().principalHasRoleInLocation(principalId, KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), location, new DateTime())
            			|| LmServiceLocator.getLMRoleService().principalHasRoleInLocation(principalId, KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), location, new DateTime());
            	
	        	//hasAccessToRead = HrServiceLocator.getHRRoleService().principalHasRoleInDepartment(principalId, KPMERole.DEPARTMENT_ADMINISTRATOR.getRoleName(), department, new DateTime())
	        	//		|| HrServiceLocator.getHRRoleService().principalHasRoleInLocation(principalId, KPMERole.LOCATION_ADMINISTRATOR.getRoleName(), location, new DateTime());
	        	
            	
            } else {
            	hasAccessToRead = HrServiceLocator.getHRRoleService().principalHasRoleInWorkArea(principalId, KPMERole.APPROVER_DELEGATE.getRoleName(), workArea, new DateTime())
                		|| HrServiceLocator.getHRRoleService().principalHasRoleInWorkArea(principalId, KPMERole.APPROVER.getRoleName(), workArea, new DateTime())
                		|| TkServiceLocator.getTKRoleService().principalHasRoleInDepartment(principalId, KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, new DateTime())
            			|| LmServiceLocator.getLMRoleService().principalHasRoleInDepartment(principalId, KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, new DateTime())
            			|| TkServiceLocator.getTKRoleService().principalHasRoleInLocation(principalId, KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), location, new DateTime())
            			|| LmServiceLocator.getLMRoleService().principalHasRoleInLocation(principalId, KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), location, new DateTime());
            	
	        	//hasAccessToRead = HrServiceLocator.getHRRoleService().principalHasRoleInDepartment(principalId, KPMERole.DEPARTMENT_ADMINISTRATOR.getRoleName(), department, new DateTime())
	        	//		|| HrServiceLocator.getHRRoleService().principalHasRoleInLocation(principalId, KPMERole.LOCATION_ADMINISTRATOR.getRoleName(), location, new DateTime());
            	
            }
        }

        return hasAccessToRead;
    }
}