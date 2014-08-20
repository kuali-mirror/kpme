package org.kuali.kpme.edo.checklist.service;

import java.util.List;

import org.kuali.kpme.edo.api.checklist.EdoChecklistSection;
import org.kuali.kpme.edo.checklist.EdoChecklistSectionBo;
import org.kuali.kpme.edo.checklist.dao.EdoChecklistSectionDao;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

public class EdoChecklistSectionServiceImpl implements EdoChecklistSectionService {
    private EdoChecklistSectionDao edoChecklistSectionDao;
    
    protected List<EdoChecklistSection> convertToImmutable(List<EdoChecklistSectionBo> bos) {
		return ModelObjectUtils.transform(bos, EdoChecklistSectionBo.toImmutable);
	}

    public EdoChecklistSectionDao getEdoChecklistSectionDao() {
        return edoChecklistSectionDao;
    }

    public void setEdoChecklistSectionDao(EdoChecklistSectionDao edoChecklistSectionDao) {
        this.edoChecklistSectionDao = edoChecklistSectionDao;
    }

    public EdoChecklistSection getChecklistSectionById(String edoChecklistSectionId) {
    	return EdoChecklistSectionBo.to(edoChecklistSectionDao.getChecklistSectionById(edoChecklistSectionId));
    }
    
    public List<EdoChecklistSection> getChecklistSectionsByChecklistId(String edoChecklistId) {        
        List<EdoChecklistSectionBo> bos = edoChecklistSectionDao.getChecklistSectionsByChecklistId(edoChecklistId);
		return convertToImmutable(bos);
    }
}
