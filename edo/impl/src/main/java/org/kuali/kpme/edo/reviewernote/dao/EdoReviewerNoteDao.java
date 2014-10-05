package org.kuali.kpme.edo.reviewernote.dao;

import java.util.List;

import org.kuali.kpme.edo.reviewernote.EdoReviewerNoteBo;

public interface EdoReviewerNoteDao {

    public EdoReviewerNoteBo getReviewerNoteById(String edoReviewerNoteId);
    public List<EdoReviewerNoteBo> getReviewerNotesByDossierId(String edoDossierId);
    public List<EdoReviewerNoteBo> getReviewerNotesByUserPrincipalId(String userPrincipalId);
    public void saveOrUpdate(EdoReviewerNoteBo itemType);
}
