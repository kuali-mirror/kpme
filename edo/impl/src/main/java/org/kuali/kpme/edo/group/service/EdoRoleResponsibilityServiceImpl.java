package org.kuali.kpme.edo.group.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.kpme.edo.api.group.EdoRoleResponsibility;
import org.kuali.kpme.edo.group.EdoRoleResponsibilityBo;
import org.kuali.kpme.edo.group.dao.EdoRoleResponsibilityDao;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

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
    
    protected List<EdoRoleResponsibility> convertToImmutable(List<EdoRoleResponsibilityBo> bos) {
		return ModelObjectUtils.transform(bos, EdoRoleResponsibilityBo.toImmutable);
	}

    public EdoRoleResponsibilityDao getEdoRoleResponsibilityDao() {
        return edoRoleResponsibilityDao;
    }

    public void setEdoRoleResponsibilityDao(EdoRoleResponsibilityDao edoRoleResponsibilityDao) {
        this.edoRoleResponsibilityDao = edoRoleResponsibilityDao;
    }

    public List<EdoRoleResponsibility> getEdoRoleResponsibilityByRoleName(String roleName) {
    	List<EdoRoleResponsibilityBo> bos = this.edoRoleResponsibilityDao.getEdoRoleResponsibilityByRoleName(roleName);
    	return convertToImmutable(bos);
    	
    }

    public List<EdoRoleResponsibility> getEdoRoleResponsibilityByResponsibilityName(String responsibilityName) {
    	List<EdoRoleResponsibilityBo> bos = this.edoRoleResponsibilityDao.getEdoRoleResponsibilityByResponsibilityName(responsibilityName);
        return convertToImmutable(bos);
    }

    public List<EdoRoleResponsibility> getEdoRoleResponsibilityByRoleAndResponsibility(String roleName, String responsibilityName) {
    	List<EdoRoleResponsibilityBo> bos = this.edoRoleResponsibilityDao.getEdoRoleResponsibilityByRoleAndResponsibility(roleName, responsibilityName);
        return convertToImmutable(bos);
    }
}
