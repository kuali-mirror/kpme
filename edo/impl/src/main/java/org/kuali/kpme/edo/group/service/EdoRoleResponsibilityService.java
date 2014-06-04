package org.kuali.kpme.edo.group.service;

import org.kuali.kpme.edo.group.EdoRoleResponsibility;

import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 1/27/14
 * Time: 2:25 PM
 */
public interface EdoRoleResponsibilityService {

    public List<EdoRoleResponsibility> getEdoRoleResponsibilityByRoleName(String roleName);
    public List<EdoRoleResponsibility> getEdoRoleResponsibilityByResponsibilityName(String responsibilityName);
    public List<EdoRoleResponsibility> getEdoRoleResponsibilityByRoleAndResponsibility(String roleName, String responsbilityName);

}
