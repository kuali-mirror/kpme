package org.kuali.kpme.edo.checklist.dao;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.edo.checklist.EdoChecklistV;
import org.kuali.kpme.edo.item.EdoItemBo;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 11/6/12
 * Time: 10:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class EdoChecklistVDaoImpl extends PlatformAwareDaoBaseOjb implements EdoChecklistVDao {

    public List<EdoChecklistV> getChecklistView( String campusCode, String schoolID, String departmentID ) {

        // check for custom checklist for this dept/school/campus, if not, use default IDs/codes
        boolean isValidChecklist = verifyChecklist(departmentID, schoolID, campusCode );

        if (!isValidChecklist) {
            departmentID = "ALL";
            schoolID     = "ALL";
            campusCode   = "IU";
        }

        List<EdoChecklistV> checklist = new LinkedList<EdoChecklistV>();

        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("campus_code", campusCode);
        cConditions.addEqualTo("school_id", schoolID);
        cConditions.addEqualTo("department_id", departmentID);

        QueryByCriteria query = QueryFactory.newQuery(EdoChecklistV.class, cConditions);

        query.addOrderByAscending("checklist_section_ordinal");
        query.addOrderByAscending("checklist_item_section_ordinal");

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            checklist.addAll(c);
        }
        return checklist;
    }

    public SortedMap<String, List<EdoChecklistV>> getChecklistHash( String campusCode, String schoolID, String departmentID ) {

        boolean isValidChecklist = verifyChecklist(departmentID, schoolID, campusCode );
        if (!isValidChecklist) {
            departmentID = "ALL";
            schoolID     = "ALL";
            campusCode   = "IU";
        }

        List<EdoChecklistV> checklist = new LinkedList<EdoChecklistV>();

        SortedMap<String, List<EdoChecklistV>> checklisthash = new TreeMap<String, List<EdoChecklistV>>(
                new Comparator<String>() {
                    @Override
                    public int compare(String s1, String s2) {
                        return s1.compareTo(s2);
                    }
                }
        );

        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("campus_code", campusCode);
        cConditions.addEqualTo("school_id", schoolID);
        cConditions.addEqualTo("department_id", departmentID);

        QueryByCriteria query = QueryFactory.newQuery(EdoChecklistV.class, cConditions);

        query.addOrderByAscending("checklist_section_ordinal");
        query.addOrderByAscending("checklist_item_section_ordinal");

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        Iterator<EdoChecklistV> i = c.iterator();

        if (c != null && c.size() != 0) {
            while (i.hasNext()) {
                EdoChecklistV tmpCLV = i.next();
                String ordSectionName = tmpCLV.getChecklistSectionOrdinal() + "_" + tmpCLV.getChecklistSectionName();
                if (checklisthash.containsKey(ordSectionName)) {
                    checklisthash.get( ordSectionName ).add(tmpCLV);
                } else {
                    List<EdoChecklistV> tmpChecklist = new LinkedList<EdoChecklistV>();

                    tmpChecklist.add( tmpCLV );

                    checklisthash.put( ordSectionName, tmpChecklist );
                }
            }
        }

        return checklisthash;
    }

    private boolean verifyChecklist( String departmentID, String schoolID, String campusCode) {

        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("campus_code", campusCode);
        cConditions.addEqualTo("school_id", schoolID);
        cConditions.addEqualTo("department_id", departmentID);

        QueryByCriteria query = QueryFactory.newQuery(EdoChecklistV.class, cConditions);

        query.addOrderByAscending("checklist_section_ordinal");
        query.addOrderByAscending("checklist_item_section_ordinal");

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            return true;
        }
        return false;

    }

    public EdoChecklistV getChecklistItemByID(String edoChecklistItemID) {

        EdoChecklistV checkList = new EdoChecklistV();

        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("edo_checklist_item_id", edoChecklistItemID);

        QueryByCriteria query = QueryFactory.newQuery(EdoChecklistV.class, cConditions);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        Iterator<EdoChecklistV> i = c.iterator();

        if (c != null && c.size() != 0) {
            while (i.hasNext()) {
                checkList = i.next();
            }
        }

        return checkList;
    }

    @Override
    public EdoChecklistV getChecklistItemByName(String name) {

        EdoChecklistV checkList = new EdoChecklistV();

        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("checklist_item_name", name);

        QueryByCriteria query = QueryFactory.newQuery(EdoChecklistV.class, cConditions);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        Iterator<EdoChecklistV> i = c.iterator();

        if (c != null && c.size() != 0) {
            while (i.hasNext()) {
                checkList = i.next();
            }
        }

        return checkList;
    }

    @Override
    public void saveOrUpdate(List<EdoItemBo> edoItems) {
        if (edoItems != null && edoItems.size() > 0 ) {
            for (EdoItemBo edoItem : edoItems) {
                this.getPersistenceBrokerTemplate().store(edoItem);
            }
        }
    }

    @Override
    public void saveOrUpdate(EdoItemBo edoItem) {
        this.getPersistenceBrokerTemplate().store(edoItem);
    }
}
