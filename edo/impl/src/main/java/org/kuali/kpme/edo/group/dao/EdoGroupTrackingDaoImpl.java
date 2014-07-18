package org.kuali.kpme.edo.group.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.edo.group.EdoGroupTrackingBo;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 1/14/14
 * Time: 3:31 PM
 */
public class EdoGroupTrackingDaoImpl  extends PlatformAwareDaoBaseOjb implements EdoGroupTrackingDao {

    private static final Logger LOG = Logger.getLogger(EdoGroupTrackingDaoImpl.class);

    public EdoGroupTrackingBo getEdoGroupTracking(String edoGroupTrackingId) {
        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("edoGroupTrackingId", edoGroupTrackingId);

        Query query = QueryFactory.newQuery(EdoGroupTrackingBo.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            return (EdoGroupTrackingBo)c.toArray()[0];
        }
        return null;
    }

    public EdoGroupTrackingBo getEdoGroupTrackingByGroupName(String groupName) {
        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("groupName", groupName);

        Query query = QueryFactory.newQuery(EdoGroupTrackingBo.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            return (EdoGroupTrackingBo)c.toArray()[0];
        }
        return null;
    }

    public List<EdoGroupTrackingBo> getEdoGroupTrackingByDepartmentId(String departmentId) {
        List<EdoGroupTrackingBo> groupList = new ArrayList<EdoGroupTrackingBo>();

        Criteria cConditions = new Criteria();
        cConditions.addEqualTo("departmentId", departmentId);

        Query query = QueryFactory.newQuery(EdoGroupTrackingBo.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            groupList.addAll(c);
        }
        return groupList;

    }

    public List<EdoGroupTrackingBo> getEdoGroupTrackingBySchoolId(String schoolId) {
        List<EdoGroupTrackingBo> groupList = new ArrayList<EdoGroupTrackingBo>();

        Criteria cConditions = new Criteria();
        cConditions.addEqualTo("organizationCode", schoolId);

        Query query = QueryFactory.newQuery(EdoGroupTrackingBo.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            groupList.addAll(c);
        }
        return groupList;
    }

    public List<EdoGroupTrackingBo> getEdoGroupTrackingByCampusId(String campusId) {
        List<EdoGroupTrackingBo> groupList = new ArrayList<EdoGroupTrackingBo>();

        Criteria cConditions = new Criteria();
        cConditions.addEqualTo("campus_id", campusId);

        Query query = QueryFactory.newQuery(EdoGroupTrackingBo.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            groupList.addAll(c);
        }
        return groupList;
    }

    public void saveOrUpdate(EdoGroupTrackingBo groupTracking) {
        this.getPersistenceBrokerTemplate().store(groupTracking);
    }

    public List<EdoGroupTrackingBo> findEdoGroupTrackingEntries(String departmentId, String schoolId, String campusId) {
        List<EdoGroupTrackingBo> groupTrackingList = new ArrayList<EdoGroupTrackingBo>();

        //String sqlCondition = "(department_id like '%" + departmentId + "%' or department_id is null)";
        //sqlCondition = sqlCondition.concat(" AND (school_id like '%" + schoolId + "%' or school_id is null)");
        //sqlCondition = sqlCondition.concat(" AND (campus_id like '%" + campusId + "%' or campus_id is null)");

        Criteria cConditions = new Criteria();

        Criteria critDept1 = new Criteria();
        critDept1.addLike("department_id", departmentId.toUpperCase());
        if (departmentId.equals("*") || departmentId.equals("%")) {
            Criteria critDept2 = new Criteria();
            critDept2.addIsNull("department_id");
            critDept1.addOrCriteria(critDept2);
        }

        Criteria critSchool1 = new Criteria();
        critSchool1.addLike("organizationCode", schoolId.toUpperCase());
        if (schoolId.equals("*") || schoolId.equals("%")) {
            Criteria critSchool2 = new Criteria();
            critSchool2.addIsNull("organizationCode");
            critSchool1.addOrCriteria(critSchool2);
        }

        Criteria critCampus1 = new Criteria();
        critCampus1.addLike("campus_id", campusId.toUpperCase());
        if (campusId.equals("*") || campusId.equals("%")) {
            Criteria critCampus2 = new Criteria();
            critCampus2.addIsNull("campus_id");
            critCampus1.addOrCriteria(critCampus2);
        }

        cConditions.addAndCriteria(critDept1);
        cConditions.addAndCriteria(critSchool1);
        cConditions.addAndCriteria(critCampus1);
        //cConditions.addSql(sqlCondition);

        LOG.info("Query conditions: " + cConditions.toString());

        Query query = QueryFactory.newQuery(EdoGroupTrackingBo.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            groupTrackingList.addAll(c);
        }
        return groupTrackingList;
    }

    public List<EdoGroupTrackingBo> getGroupTrackingEntries() {

        List<EdoGroupTrackingBo> results = new LinkedList<EdoGroupTrackingBo>();
        Criteria criteria = new Criteria();

        Query query = QueryFactory.newQuery(EdoGroupTrackingBo.class, criteria, true);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0 )  {
            results.addAll(c);
        }

        return results;
    }
}
