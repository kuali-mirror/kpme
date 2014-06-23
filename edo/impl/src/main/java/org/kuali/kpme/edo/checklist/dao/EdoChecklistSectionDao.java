package org.kuali.kpme.edo.checklist.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.edo.checklist.EdoChecklistSectionBo;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 5/22/14
 * Time: 10:01 AM
 */
public interface EdoChecklistSectionDao {

    public EdoChecklistSectionBo getChecklistSectionByID(String edoChecklistSectionID);
    public List<EdoChecklistSectionBo> getChecklistSectionsByChecklistID(String edoChecklistID, LocalDate asOfDate);
}
