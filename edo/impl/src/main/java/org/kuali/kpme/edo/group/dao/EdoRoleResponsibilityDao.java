package org.kuali.kpme.edo.group.dao;

import org.kuali.kpme.edo.group.EdoRoleResponsibilityBo;
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

    public List<EdoRoleResponsibilityBo> getEdoRoleResponsibilityByRoleName(String roleName);
    public List<EdoRoleResponsibilityBo> getEdoRoleResponsibilityByResponsibilityName(String responsibilityName);
    public List<EdoRoleResponsibilityBo> getEdoRoleResponsibilityByRoleAndResponsibility(String roleName, String responsibilityName);

}
