package org.kuali.kpme.edo.candidate.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.edo.api.candidate.EdoCandidate;

public interface CandidateService {

	public List<EdoCandidate> getCandidateList();

	public EdoCandidate getCandidate(String candidateID);

	public List<EdoCandidate> getCandidateListByUsername(String userName);

	public EdoCandidate getCandidateByUsername(String userName);
	
	//public List<EdoCandidate> getCandidateList(String principalName, String groupKeyCode, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory);

}
