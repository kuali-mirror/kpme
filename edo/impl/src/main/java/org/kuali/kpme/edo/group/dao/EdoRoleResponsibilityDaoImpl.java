package org.kuali.kpme.edo.group.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.edo.group.EdoRoleResponsibilityBo;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import java.util.*;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 1/27/14
 * Time: 2:24 PM
 */
public class EdoRoleResponsibilityDaoImpl  extends PlatformAwareDaoBaseOjb implements EdoRoleResponsibilityDao {

    public List<EdoRoleResponsibilityBo> getEdoRoleResponsibilityByRoleName(String roleName) {
        List<EdoRoleResponsibilityBo> edoRoleResponsibilityList = new ArrayList<EdoRoleResponsibilityBo>();

        Criteria cConditions = new Criteria();
        cConditions.addEqualTo("kim_role_name", roleName);

        Query query = QueryFactory.newQuery(EdoRoleResponsibilityBo.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            edoRoleResponsibilityList.addAll(c);
        }
        return edoRoleResponsibilityList;
    }

    public List<EdoRoleResponsibilityBo> getEdoRoleResponsibilityByResponsibilityName(String responsibilityName) {
        List<EdoRoleResponsibilityBo> edoRoleResponsibilityList = new ArrayList<EdoRoleResponsibilityBo>();

        Criteria cConditions = new Criteria();
        cConditions.addEqualTo("kim_responsibility_name", responsibilityName);

        Query query = QueryFactory.newQuery(EdoRoleResponsibilityBo.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            edoRoleResponsibilityList.addAll(c);
        }
        return edoRoleResponsibilityList;
    }

    public List<EdoRoleResponsibilityBo> getEdoRoleResponsibilityByRoleAndResponsibility(String roleName, String responsibilityName) {
        List<EdoRoleResponsibilityBo> edoRoleResponsibilityList = new ArrayList<EdoRoleResponsibilityBo>();

        Criteria cConditions = new Criteria();
        cConditions.addEqualTo("kim_responsibility_name", responsibilityName);
        cConditions.addEqualTo("kim_role_name", roleName);

        Query query = QueryFactory.newQuery(EdoRoleResponsibilityBo.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            edoRoleResponsibilityList.addAll(c);
        }
        return edoRoleResponsibilityList;
    }
}
