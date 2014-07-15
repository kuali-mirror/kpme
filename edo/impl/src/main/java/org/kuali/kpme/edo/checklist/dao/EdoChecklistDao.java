package org.kuali.kpme.edo.checklist.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.edo.checklist.EdoChecklistBo;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 5/22/14
 * Time: 10:01 AM
 */
public interface EdoChecklistDao {

    public EdoChecklistBo getChecklistByID(String edoChecklistID);
    
    public List<EdoChecklistBo> getChecklists(String groupKey, String organizationCode, String departmentID, LocalDate asOfDate);
}
