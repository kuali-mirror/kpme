package org.kuali.kpme.edo.checklist.service;

import org.kuali.kpme.edo.checklist.EdoChecklistV;
import org.kuali.kpme.edo.item.EdoItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.SortedMap;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 11/6/12
 * Time: 10:04 AM
 * To change this template use File | Settings | File Templates.
 */
public interface EdoChecklistVService {

    public List<EdoChecklistV> getCheckListView(String campusCode, String schoolID, String departmentID);
    public SortedMap<String, List<EdoChecklistV>> getCheckListHash(String campusCode, String schoolID, String departmentID);
    public EdoChecklistV getChecklistItemByID( BigDecimal checklistItemID );
    public EdoChecklistV getChecklistItemByName( String name );
    public void saveOrUpdate(List<EdoItem> edoItems);
    public void saveOrUpdate(EdoItem edoItem);
}
