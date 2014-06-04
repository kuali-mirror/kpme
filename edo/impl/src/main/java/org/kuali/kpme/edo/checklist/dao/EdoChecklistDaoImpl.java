package org.kuali.kpme.edo.checklist.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.edo.checklist.EdoChecklist;
import org.kuali.kpme.edo.item.EdoItem;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import java.math.BigDecimal;
import java.util.*;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 11/6/12
 * Time: 10:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class EdoChecklistDaoImpl extends PlatformAwareDaoBaseOjb implements EdoChecklistDao {

    public List<EdoChecklist> getChecklistView( String campusCode, String schoolID, String departmentID ) {

        // check for custom checklist for this dept/school/campus, if not, use default IDs/codes
        boolean isValidChecklist = verifyChecklist(departmentID, schoolID, campusCode );

        if (!isValidChecklist) {
            departmentID = "ALL";
            schoolID     = "ALL";
            campusCode   = "IU";
        }

        List<EdoChecklist> checklist = new LinkedList<EdoChecklist>();

        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("campus_code", campusCode);
        cConditions.addEqualTo("school_id", schoolID);
        cConditions.addEqualTo("department_id", departmentID);

        QueryByCriteria query = QueryFactory.newQuery(EdoChecklist.class, cConditions);

        query.addOrderByAscending("checklist_section_ordinal");
        query.addOrderByAscending("checklist_item_section_ordinal");

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            checklist.addAll(c);
        }
        return checklist;
    }

    public SortedMap<String, List<EdoChecklist>> getChecklistHash( String campusCode, String schoolID, String departmentID ) {

        boolean isValidChecklist = verifyChecklist(departmentID, schoolID, campusCode );
        if (!isValidChecklist) {
            departmentID = "ALL";
            schoolID     = "ALL";
            campusCode   = "IU";
        }

        List<EdoChecklist> checklist = new LinkedList<EdoChecklist>();

        SortedMap<String, List<EdoChecklist>> checklisthash = new TreeMap<String, List<EdoChecklist>>(
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

        QueryByCriteria query = QueryFactory.newQuery(EdoChecklist.class, cConditions);

        query.addOrderByAscending("checklist_section_ordinal");
        query.addOrderByAscending("checklist_item_section_ordinal");

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        Iterator<EdoChecklist> i = c.iterator();

        if (c != null && c.size() != 0) {
            while (i.hasNext()) {
                EdoChecklist tmpCLV = i.next();
                String ordSectionName = tmpCLV.getChecklistSectionOrdinal() + "_" + tmpCLV.getChecklistSectionName();
                if (checklisthash.containsKey(ordSectionName)) {
                    checklisthash.get( ordSectionName ).add(tmpCLV);
                } else {
                    List<EdoChecklist> tmpChecklist = new LinkedList<EdoChecklist>();

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

        QueryByCriteria query = QueryFactory.newQuery(EdoChecklist.class, cConditions);

        query.addOrderByAscending("checklist_section_ordinal");
        query.addOrderByAscending("checklist_item_section_ordinal");

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            return true;
        }
        return false;

    }

    public EdoChecklist getChecklistItemByID( BigDecimal checklistItemID ) {

        EdoChecklist checkList = new EdoChecklist();

        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("checklist_item_id", checklistItemID);

        QueryByCriteria query = QueryFactory.newQuery(EdoChecklist.class, cConditions);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        Iterator<EdoChecklist> i = c.iterator();

        if (c != null && c.size() != 0) {
            while (i.hasNext()) {
                checkList = i.next();
            }
        }

        return checkList;
    }

    @Override
    public EdoChecklist getChecklistItemByName(String name) {

        EdoChecklist checkList = new EdoChecklist();

        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("checklist_item_name", name);

        QueryByCriteria query = QueryFactory.newQuery(EdoChecklist.class, cConditions);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        Iterator<EdoChecklist> i = c.iterator();

        if (c != null && c.size() != 0) {
            while (i.hasNext()) {
                checkList = i.next();
            }
        }

        return checkList;
    }

    @Override
    public void saveOrUpdate(List<EdoItem> edoItems) {
        if (edoItems != null && edoItems.size() > 0 ) {
            for (EdoItem edoItem : edoItems) {
                this.getPersistenceBrokerTemplate().store(edoItem);
            }
        }
    }

    @Override
    public void saveOrUpdate(EdoItem edoItem) {
        this.getPersistenceBrokerTemplate().store(edoItem);
    }
}
