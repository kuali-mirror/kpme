package org.kuali.kpme.edo.committeesready.dao;

import java.util.List;

import org.kuali.kpme.edo.committeesready.EdoCommitteesReadyBo;

public interface EdoCommitteesReadyDao {
	
	 public boolean saveOrUpdate(EdoCommitteesReadyBo submitButton );
	 public EdoCommitteesReadyBo getEdoCommitteesReady(String groupKeyCode);
	 public List<EdoCommitteesReadyBo> getEdoCommitteesReadyList();
}
