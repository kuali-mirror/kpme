package org.kuali.kpme.edo.reviewernote.service;

import java.util.List;

import org.kuali.kpme.edo.api.reviewernote.EdoReviewerNote;
import org.kuali.kpme.edo.reviewernote.EdoReviewerNoteBo;
import org.kuali.kpme.edo.reviewernote.dao.EdoReviewerNoteDao;
import org.kuali.rice.core.api.mo.ModelObjectUtils;


public class EdoReviewerNoteServiceImpl implements EdoReviewerNoteService {

    private EdoReviewerNoteDao edoReviewerNoteDao;
    
    public void setEdoReviewerNoteDao(EdoReviewerNoteDao edoReviewerNoteDao) {
        this.edoReviewerNoteDao = edoReviewerNoteDao;
    }

    protected List<EdoReviewerNote> convertToImmutable(List<EdoReviewerNoteBo> bos) {
		return ModelObjectUtils.transform(bos, EdoReviewerNoteBo.toImmutable);
	}
    
    public EdoReviewerNote getReviewerNoteById(String edoReviewerNoteId){
    	return EdoReviewerNoteBo.to(edoReviewerNoteDao.getReviewerNoteById(edoReviewerNoteId));
    }
    
    public List<EdoReviewerNote> getReviewerNotesByDossierId(String edoDossierId) {
    	List<EdoReviewerNoteBo> bos = edoReviewerNoteDao.getReviewerNotesByDossierId(edoDossierId);
        return convertToImmutable(bos);
    	
    }
    
    public List<EdoReviewerNote> getReviewerNotesByUserPrincipalId(String userPrincipalId) {
    	List<EdoReviewerNoteBo> bos = edoReviewerNoteDao.getReviewerNotesByUserPrincipalId(userPrincipalId);
        return convertToImmutable(bos);	
    }
    
    @Override
    public void saveOrUpdate(EdoReviewerNote edoReviewerNote) {
    	EdoReviewerNoteBo bo = EdoReviewerNoteBo.from(edoReviewerNote);
    	
        this.edoReviewerNoteDao.saveOrUpdate(bo);
    }
}
