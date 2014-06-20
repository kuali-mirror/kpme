package org.kuali.kpme.edo.checklist.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.edo.checklist.EdoChecklistItemBo;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 5/22/14
 * Time: 10:01 AM
 */
public interface EdoChecklistItemDao {

    public EdoChecklistItemBo getChecklistItemByID(String checklistItemID);
    public List<EdoChecklistItemBo> getChecklistItemsBySectionID(String sectionID, LocalDate asOfDate);
}
