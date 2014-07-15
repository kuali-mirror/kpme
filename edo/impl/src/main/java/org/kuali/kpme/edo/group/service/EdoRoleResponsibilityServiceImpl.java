package org.kuali.kpme.edo.group.service;

import org.apache.log4j.Logger;
import org.kuali.kpme.edo.group.EdoRoleResponsibility;
import org.kuali.kpme.edo.group.dao.EdoRoleResponsibilityDao;

import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 1/27/14
 * Time: 2:25 PM
 */
public class EdoRoleResponsibilityServiceImpl implements EdoRoleResponsibilityService {

    private static final Logger LOG = Logger.getLogger(EdoRoleResponsibilityService.class);
    private EdoRoleResponsibilityDao edoRoleResponsibilityDao;

    public EdoRoleResponsibilityDao getEdoRoleResponsibilityDao() {
        return edoRoleResponsibilityDao;
    }

    public void setEdoRoleResponsibilityDao(EdoRoleResponsibilityDao edoRoleResponsibilityDao) {
        this.edoRoleResponsibilityDao = edoRoleResponsibilityDao;
    }

    public List<EdoRoleResponsibility> getEdoRoleResponsibilityByRoleName(String roleName) {
        return this.edoRoleResponsibilityDao.getEdoRoleResponsibilityByRoleName(roleName);
    }

    public List<EdoRoleResponsibility> getEdoRoleResponsibilityByResponsibilityName(String responsibilityName) {
        return this.edoRoleResponsibilityDao.getEdoRoleResponsibilityByResponsibilityName(responsibilityName);
    }

    public List<EdoRoleResponsibility> getEdoRoleResponsibilityByRoleAndResponsibility(String roleName, String responsibilityName) {
        return this.edoRoleResponsibilityDao.getEdoRoleResponsibilityByRoleAndResponsibility(roleName, responsibilityName);
    }
}
