package org.kuali.kpme.edo.checklist.dao;

import org.kuali.kpme.edo.checklist.EdoChecklist;
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
 * Time: 10:03 AM
 * To change this template use File | Settings | File Templates.
 */
public interface EdoChecklistDao {

    public List<EdoChecklist> getChecklistView( String campusCode, String schoolID, String departmentID );
    public SortedMap<String, List<EdoChecklist>> getChecklistHash( String campusCode, String schoolID, String departmentID );
    public EdoChecklist getChecklistItemByID( BigDecimal checklistItemID );
    public EdoChecklist getChecklistItemByName( String name );
    public void saveOrUpdate(List<EdoItem> edoItems);
 	public void saveOrUpdate(EdoItem edoItem);

}
