package org.kuali.kpme.edo.checklist.service;

import org.kuali.kpme.edo.checklist.EdoChecklist;
import org.kuali.kpme.edo.item.EdoItem;
import org.kuali.kpme.edo.checklist.dao.EdoChecklistDao;

import java.math.BigDecimal;
import java.util.List;
import java.util.SortedMap;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 11/6/12
 * Time: 10:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class EdoChecklistServiceImpl implements EdoChecklistService {

    private EdoChecklistDao edoChecklistDao;

    public List<EdoChecklist> getCheckListView(String campusCode, String schoolID, String departmentID) {
        return edoChecklistDao.getChecklistView(campusCode, schoolID, departmentID);
    }

    public SortedMap<String, List<EdoChecklist>> getCheckListHash(String campusCode, String schoolID, String departmentID) {
        return edoChecklistDao.getChecklistHash(campusCode, schoolID, departmentID);
    }
    public EdoChecklistDao getEdoChecklistDao() {
        return edoChecklistDao;
    }

    public void setEdoChecklistDao(EdoChecklistDao checklistDao) {
        this.edoChecklistDao = checklistDao;
    }

    public EdoChecklist getChecklistItemByID( BigDecimal checklistItemID ) {
        return edoChecklistDao.getChecklistItemByID( checklistItemID );
    }

    @Override
    public EdoChecklist getChecklistItemByName(String name) {

        return edoChecklistDao.getChecklistItemByName(name);
    }

    @Override
    public void saveOrUpdate(List<EdoItem> edoItems) {
        edoChecklistDao.saveOrUpdate(edoItems);
    }

    @Override
    public void saveOrUpdate(EdoItem edoItem) {
        edoChecklistDao.saveOrUpdate(edoItem);
    }
}
