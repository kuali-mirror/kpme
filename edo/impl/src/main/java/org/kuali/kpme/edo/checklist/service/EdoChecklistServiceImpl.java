package org.kuali.kpme.edo.checklist.service;

import org.kuali.kpme.edo.api.checklist.EdoChecklist;
import org.kuali.kpme.edo.checklist.EdoChecklistBo;
import org.kuali.kpme.edo.checklist.dao.EdoChecklistDao;

public class EdoChecklistServiceImpl implements EdoChecklistService {
    private EdoChecklistDao edoChecklistDao;

    public EdoChecklistDao getEdoChecklistDao() {
        return edoChecklistDao;
    }

    public void setEdoChecklistDao(EdoChecklistDao edoChecklistDao) {
        this.edoChecklistDao = edoChecklistDao;
    }

    public EdoChecklist getChecklistByID(String checklistID ) {
    	return EdoChecklistBo.to(edoChecklistDao.getChecklistByID(checklistID));
    }
}
