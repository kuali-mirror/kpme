package org.kuali.kpme.edo.group.dao;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.edo.group.EdoGroupTracking;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

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

    public EdoGroupTracking getEdoGroupTracking(Integer groupTrackingId) {
        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("group_tracking_id", groupTrackingId);

        Query query = QueryFactory.newQuery(EdoGroupTracking.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            return (EdoGroupTracking)c.toArray()[0];
        }
        return null;
    }

    public EdoGroupTracking getEdoGroupTrackingByGroupName(String groupName) {
        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("groupName", groupName);

        Query query = QueryFactory.newQuery(EdoGroupTracking.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            return (EdoGroupTracking)c.toArray()[0];
        }
        return null;
    }

    public List<EdoGroupTracking> getEdoGroupTrackingByDepartmentId(String departmentId) {
        List<EdoGroupTracking> groupList = new ArrayList<EdoGroupTracking>();

        Criteria cConditions = new Criteria();
        cConditions.addEqualTo("department_id", departmentId);

        Query query = QueryFactory.newQuery(EdoGroupTracking.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            groupList.addAll(c);
        }
        return groupList;

    }

    public List<EdoGroupTracking> getEdoGroupTrackingBySchoolId(String schoolId) {
        List<EdoGroupTracking> groupList = new ArrayList<EdoGroupTracking>();

        Criteria cConditions = new Criteria();
        cConditions.addEqualTo("school_id", schoolId);

        Query query = QueryFactory.newQuery(EdoGroupTracking.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            groupList.addAll(c);
        }
        return groupList;
    }

    public List<EdoGroupTracking> getEdoGroupTrackingByCampusId(String campusId) {
        List<EdoGroupTracking> groupList = new ArrayList<EdoGroupTracking>();

        Criteria cConditions = new Criteria();
        cConditions.addEqualTo("campus_id", campusId);

        Query query = QueryFactory.newQuery(EdoGroupTracking.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            groupList.addAll(c);
        }
        return groupList;
    }

    public void saveOrUpdate(EdoGroupTracking groupTracking) {
        this.getPersistenceBrokerTemplate().store(groupTracking);
    }

    public List<EdoGroupTracking> findEdoGroupTrackingEntries(String departmentId, String schoolId, String campusId) {
        List<EdoGroupTracking> groupTrackingList = new ArrayList<EdoGroupTracking>();

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
        critSchool1.addLike("school_id", schoolId.toUpperCase());
        if (schoolId.equals("*") || schoolId.equals("%")) {
            Criteria critSchool2 = new Criteria();
            critSchool2.addIsNull("school_id");
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

        Query query = QueryFactory.newQuery(EdoGroupTracking.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            groupTrackingList.addAll(c);
        }
        return groupTrackingList;
    }

    public List<EdoGroupTracking> getGroupTrackingEntries() {

        List<EdoGroupTracking> results = new LinkedList<EdoGroupTracking>();
        Criteria criteria = new Criteria();

        Query query = QueryFactory.newQuery(EdoGroupTracking.class, criteria, true);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0 )  {
            results.addAll(c);
        }

        return results;
    }
}
