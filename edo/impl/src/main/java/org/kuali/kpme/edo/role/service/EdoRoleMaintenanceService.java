package org.kuali.kpme.edo.role.service;

import org.kuali.rice.kim.api.role.RoleMember;

import java.util.Map;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 3/19/14
 * Time: 10:38 AM
 */
public interface EdoRoleMaintenanceService {

    public RoleMember assignStaffAdmin( String principalId, Map<String,String> qualifications);
    public Map<String,String> buildAdminQualifications(String unit);
}
