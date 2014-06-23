package org.kuali.kpme.edo.checklist.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.edo.api.checklist.EdoChecklistItem;
import org.kuali.kpme.edo.checklist.EdoChecklistItemBo;
import org.kuali.kpme.edo.checklist.dao.EdoChecklistItemDao;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

public class EdoChecklistItemServiceImpl implements EdoChecklistItemService {
    private EdoChecklistItemDao edoChecklistItemDao;
    
    protected List<EdoChecklistItem> convertToImmutable(List<EdoChecklistItemBo> bos) {
		return ModelObjectUtils.transform(bos, EdoChecklistItemBo.toImmutable);
	}

    public EdoChecklistItemDao getEdoChecklistItemDao() {
        return edoChecklistItemDao;
    }

    public void setEdoChecklistItemDao(EdoChecklistItemDao edoChecklistItemDao) {
        this.edoChecklistItemDao = edoChecklistItemDao;
    }

    public EdoChecklistItem getChecklistItemByID(String edoChecklistItemID) {
    	return EdoChecklistItemBo.to(edoChecklistItemDao.getChecklistItemByID(edoChecklistItemID));
    }
    
    public List<EdoChecklistItem> getChecklistItemsBySectionID(String edoChecklistSectionID, LocalDate asOfDate) {        
        List<EdoChecklistItemBo> bos = edoChecklistItemDao.getChecklistItemsBySectionID(edoChecklistSectionID, asOfDate);
		return convertToImmutable(bos);
    }
}
