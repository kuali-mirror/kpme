package org.kuali.kpme.edo.checklist.service;

import org.kuali.kpme.edo.checklist.EdoChecklistItem;

import java.math.BigDecimal;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 5/22/14
 * Time: 10:06 AM
 */
public interface EdoChecklistItemService {

    public EdoChecklistItem getChecklistItemByID(BigDecimal checklistItemId);
}
