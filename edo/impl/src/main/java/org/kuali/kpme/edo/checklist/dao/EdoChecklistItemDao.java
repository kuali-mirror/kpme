package org.kuali.kpme.edo.checklist.dao;

import org.kuali.kpme.edo.checklist.EdoChecklistItem;

import java.math.BigDecimal;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 5/22/14
 * Time: 10:01 AM
 */
public interface EdoChecklistItemDao {

    public EdoChecklistItem getChecklistItemByID(BigDecimal checklistItemID);
}
