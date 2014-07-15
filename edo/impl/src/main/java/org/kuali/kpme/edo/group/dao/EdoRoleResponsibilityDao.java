package org.kuali.kpme.edo.group.dao;

import org.kuali.kpme.edo.group.EdoRoleResponsibility;
import java.util.*;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 1/27/14
 * Time: 2:24 PM
 */
public interface EdoRoleResponsibilityDao {

    public List<EdoRoleResponsibility> getEdoRoleResponsibilityByRoleName(String roleName);
    public List<EdoRoleResponsibility> getEdoRoleResponsibilityByResponsibilityName(String responsibilityName);
    public List<EdoRoleResponsibility> getEdoRoleResponsibilityByRoleAndResponsibility(String roleName, String responsibilityName);

}
