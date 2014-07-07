package org.kuali.kpme.edo.checklist.service;

import org.kuali.kpme.edo.checklist.EdoChecklistV;
import org.kuali.kpme.edo.item.EdoItemBo;
import org.kuali.kpme.edo.checklist.dao.EdoChecklistVDao;

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
public class EdoChecklistVServiceImpl implements EdoChecklistVService {

    private EdoChecklistVDao edoChecklistVDao;

    public List<EdoChecklistV> getCheckListView(String campusCode, String schoolID, String departmentID) {
        return edoChecklistVDao.getChecklistView(campusCode, schoolID, departmentID);
    }

    public SortedMap<String, List<EdoChecklistV>> getCheckListHash(String campusCode, String schoolID, String departmentID) {
        return edoChecklistVDao.getChecklistHash(campusCode, schoolID, departmentID);
    }
    public EdoChecklistVDao getEdoChecklistVDao() {
        return edoChecklistVDao;
    }

    public void setEdoChecklistVDao(EdoChecklistVDao edoChecklistVDao) {
        this.edoChecklistVDao = edoChecklistVDao;
    }

    public EdoChecklistV getChecklistItemByID(String edoChecklistItemID) {
        return edoChecklistVDao.getChecklistItemByID(edoChecklistItemID);
    }

    @Override
    public EdoChecklistV getChecklistItemByName(String name) {

        return edoChecklistVDao.getChecklistItemByName(name);
    }
    
    // These should be in EdoItem service
    /*
    @Override
    public void saveOrUpdate(List<EdoItemBo> edoItems) {
    	edoChecklistVDao.saveOrUpdate(edoItems);
    }

    @Override
    public void saveOrUpdate(EdoItemBo edoItem) {
    	edoChecklistVDao.saveOrUpdate(edoItem);
    }*/
}
