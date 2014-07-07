package org.kuali.kpme.edo.checklist.dao;

import org.kuali.kpme.edo.checklist.EdoChecklistV;
import org.kuali.kpme.edo.item.EdoItemBo;

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
public interface EdoChecklistVDao {

    public List<EdoChecklistV> getChecklistView( String campusCode, String schoolID, String departmentID );
    public SortedMap<String, List<EdoChecklistV>> getChecklistHash( String campusCode, String schoolID, String departmentID );
    public EdoChecklistV getChecklistItemByID(String edoChecklistItemID);
    public EdoChecklistV getChecklistItemByName( String name );
    public void saveOrUpdate(List<EdoItemBo> edoItems);
 	public void saveOrUpdate(EdoItemBo edoItem);

}
