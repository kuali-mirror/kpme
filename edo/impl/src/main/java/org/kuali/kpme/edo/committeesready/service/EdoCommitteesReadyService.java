package org.kuali.kpme.edo.committeesready.service;

import java.util.List;

import org.kuali.kpme.edo.api.committeesready.EdoCommitteesReady;

public interface EdoCommitteesReadyService {
	
	 public boolean saveOrUpdate(EdoCommitteesReady edoCommitteesReady);
	 public EdoCommitteesReady getEdoCommitteesReady(String groupKeyCode);
	 public List<EdoCommitteesReady> getEdoCommitteesReadyList();
	 
}
