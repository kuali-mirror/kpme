package org.kuali.kpme.edo.checklist.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.edo.api.checklist.EdoChecklist;
import org.kuali.kpme.edo.checklist.EdoChecklistBo;
import org.kuali.kpme.edo.checklist.dao.EdoChecklistDao;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

public class EdoChecklistServiceImpl implements EdoChecklistService {
    private EdoChecklistDao edoChecklistDao;
    
    protected List<EdoChecklist> convertToImmutable(List<EdoChecklistBo> bos) {
		return ModelObjectUtils.transform(bos, EdoChecklistBo.toImmutable);
	}

    public EdoChecklistDao getEdoChecklistDao() {
        return edoChecklistDao;
    }

    public void setEdoChecklistDao(EdoChecklistDao edoChecklistDao) {
        this.edoChecklistDao = edoChecklistDao;
    }

    public EdoChecklist getChecklistById(String edoChecklistId) {
    	return EdoChecklistBo.to(edoChecklistDao.getChecklistById(edoChecklistId));
    }

    public List<EdoChecklist> getChecklists(String groupKey, String organizationCode, String departmentId, LocalDate asOfDate) {
    	List<EdoChecklistBo> bos = edoChecklistDao.getChecklists(groupKey, organizationCode, departmentId, asOfDate);
    	return convertToImmutable(bos);
	}
}
