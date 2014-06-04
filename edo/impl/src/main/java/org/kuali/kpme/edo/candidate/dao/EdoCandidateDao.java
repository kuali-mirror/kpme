package org.kuali.kpme.edo.candidate.dao;

import org.kuali.kpme.edo.candidate.EdoCandidate;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tcbradley
 * Date: 10/25/12
 * Time: 1:45 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EdoCandidateDao {

    public List<EdoCandidate> getEdoCandidateList();

    public EdoCandidate getCandidate(BigDecimal candidateID);
    public List<EdoCandidate> getCandidateListByUsername( String userName );
    public EdoCandidate getCandidateByUsername(String userName);
}
