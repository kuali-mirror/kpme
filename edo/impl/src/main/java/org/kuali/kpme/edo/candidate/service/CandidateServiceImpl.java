package org.kuali.kpme.edo.candidate.service;

import java.util.List;

import org.kuali.kpme.edo.api.candidate.EdoCandidate;
import org.kuali.kpme.edo.candidate.EdoCandidateBo;
import org.kuali.kpme.edo.candidate.dao.EdoCandidateDao;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

public class CandidateServiceImpl implements CandidateService {
	private EdoCandidateDao edoCandidateDao;
	
	public void setEdoCandidateListDao(EdoCandidateDao edoCandidateListDao) {
		this.edoCandidateDao = edoCandidateListDao;
	}

	
    public EdoCandidate getCandidate(String edoCandidateID) {
    	EdoCandidateBo edoCandidateBo = edoCandidateDao.getCandidate(edoCandidateID);
    	
    	if ( edoCandidateBo == null){
    		return null;
    	}
    	
    	EdoCandidate.Builder builder = EdoCandidate.Builder.create(edoCandidateBo);
    	
    	return builder.build();
    }

    public EdoCandidate getCandidateByUsername(String principalName) {
    	EdoCandidateBo edoCandidateBo = edoCandidateDao.getCandidateByUsername(principalName);
    	
    	if ( edoCandidateBo == null){
    		return null;
    	}
    	
    	EdoCandidate.Builder builder = EdoCandidate.Builder.create(edoCandidateBo);
    	
    	return builder.build();
    	
    }
   
    public List<EdoCandidate> getCandidateListByUsername(String principalName) {
    	
    	return ModelObjectUtils.transform(edoCandidateDao.getCandidateListByUsername( principalName ), EdoCandidateBo.toImmutable);
    }
    
    
    public List<EdoCandidate> getCandidateList() {
    	
    	return ModelObjectUtils.transform(edoCandidateDao.getEdoCandidateList(), EdoCandidateBo.toImmutable);
	}

    /*
    public List<EdoCandidate> getCandidateList(String principalName, String groupKeyCode, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory) {
		return ModelObjectUtils.transform(edoCandidateDao.getEdoCandidateList(principalName, groupKeyCode, fromEffdt, toEffdt, active, showHistory), EdoCandidateBo.toImmutable);
	}
	*/
    
}
