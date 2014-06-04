package org.kuali.kpme.edo.checklist.service;

import org.kuali.kpme.edo.checklist.EdoChecklistItem;
import org.kuali.kpme.edo.checklist.dao.EdoChecklistItemDao;

import java.math.BigDecimal;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 5/22/14
 * Time: 10:07 AM
 */
public class EdoChecklistItemServiceImpl implements EdoChecklistItemService {
    private EdoChecklistItemDao edoChecklistItemDao;

    public EdoChecklistItemDao getEdoChecklistItemDao() {
        return edoChecklistItemDao;
    }

    public void setEdoChecklistItemDao(EdoChecklistItemDao edoChecklistItemDao) {
        this.edoChecklistItemDao = edoChecklistItemDao;
    }

    public EdoChecklistItem getChecklistItemByID( BigDecimal checklistItemID ) {
        return edoChecklistItemDao.getChecklistItemByID( checklistItemID );
    }

}
