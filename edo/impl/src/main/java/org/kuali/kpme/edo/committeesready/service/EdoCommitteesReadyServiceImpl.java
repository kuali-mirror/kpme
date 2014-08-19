package org.kuali.kpme.edo.committeesready.service;

import java.util.List;

import org.kuali.kpme.edo.api.committeesready.EdoCommitteesReady;
import org.kuali.kpme.edo.committeesready.EdoCommitteesReadyBo;
import org.kuali.kpme.edo.committeesready.dao.EdoCommitteesReadyDao;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

public class EdoCommitteesReadyServiceImpl implements EdoCommitteesReadyService {
	
	private EdoCommitteesReadyDao edoCommitteesReadyDao;
	
	protected List<EdoCommitteesReady> convertToImmutable(List<EdoCommitteesReadyBo> bos) {
		return ModelObjectUtils.transform(bos, EdoCommitteesReadyBo.toImmutable);
	}
	 
	public EdoCommitteesReadyDao getEdoCommitteesReadyDao() {
		return edoCommitteesReadyDao;
	}
	
	public void setEdoCommitteesReadyDao(EdoCommitteesReadyDao edoCommitteesReadyDao) {
		this.edoCommitteesReadyDao = edoCommitteesReadyDao;
	}
	
	public boolean saveOrUpdate(EdoCommitteesReady edoCommitteesReady ) {
		EdoCommitteesReadyBo bo = EdoCommitteesReadyBo.from(edoCommitteesReady);    
		return this.edoCommitteesReadyDao.saveOrUpdate(bo);
	}
	
	public EdoCommitteesReady getEdoCommitteesReady(String groupKeyCode) {
		 return EdoCommitteesReadyBo.to(this.edoCommitteesReadyDao.getEdoCommitteesReady(groupKeyCode));		 
	}
	
	public List<EdoCommitteesReady> getEdoCommitteesReadyList() {		 
		List<EdoCommitteesReadyBo> bos = this.edoCommitteesReadyDao.getEdoCommitteesReadyList();
	    return convertToImmutable(bos);
	}
	
}
