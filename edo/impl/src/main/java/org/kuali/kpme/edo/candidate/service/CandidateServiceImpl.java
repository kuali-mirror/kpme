package org.kuali.kpme.edo.candidate.service;

import java.math.BigDecimal;
import java.util.List;

import org.kuali.kpme.edo.candidate.EdoCandidate;
import org.kuali.kpme.edo.candidate.dao.EdoCandidateDao;

public class CandidateServiceImpl implements CandidateService {
	private EdoCandidateDao edoCandidateListDao;

	public List<EdoCandidate> getCandidateList() {
		return edoCandidateListDao.getEdoCandidateList();
	}

    public EdoCandidate getCandidate(BigDecimal candidateID) {
        return edoCandidateListDao.getCandidate(candidateID);
    }

    public void setEdoCandidateListDao(EdoCandidateDao edoCandidateListDao) {
        this.edoCandidateListDao = edoCandidateListDao;
    }

    public List<EdoCandidate> getCandidateListByUsername( String userName ) {
        return edoCandidateListDao.getCandidateListByUsername( userName );
    }
    public EdoCandidate getCandidateByUsername(String userName) {
    	return edoCandidateListDao.getCandidateByUsername(userName);
    	
    }
}
