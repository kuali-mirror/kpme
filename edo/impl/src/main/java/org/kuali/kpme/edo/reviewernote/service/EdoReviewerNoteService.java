package org.kuali.kpme.edo.reviewernote.service;

import java.util.List;

import org.kuali.kpme.edo.api.reviewernote.EdoReviewerNote;


public interface EdoReviewerNoteService {
	
	public EdoReviewerNote getReviewerNoteById(String edoReviewerNoteId);
	public List<EdoReviewerNote> getReviewerNotesByDossierId(String edoDossierId);
	public List<EdoReviewerNote> getReviewerNotesByUserPrincipalId(String userPrincipalId);
	public void saveOrUpdate(EdoReviewerNote edoReviewerNote);
}
