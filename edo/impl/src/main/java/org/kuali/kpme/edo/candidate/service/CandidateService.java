package org.kuali.kpme.edo.candidate.service;

import java.math.BigDecimal;
import java.util.List;

import org.kuali.kpme.edo.candidate.EdoCandidate;

public interface CandidateService {

    public List<EdoCandidate> getCandidateList();
    public EdoCandidate getCandidate(BigDecimal candidateID);
    public List<EdoCandidate> getCandidateListByUsername( String userName );
    public EdoCandidate getCandidateByUsername(String userName);

}
