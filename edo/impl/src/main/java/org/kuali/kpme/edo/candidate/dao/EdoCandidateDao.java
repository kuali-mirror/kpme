package org.kuali.kpme.edo.candidate.dao;

import org.joda.time.LocalDate;
import org.kuali.kpme.edo.candidate.EdoCandidateBo;
import java.util.List;

/**
 * Date: 06/07/14
 */
public interface EdoCandidateDao {

    public List<EdoCandidateBo> getEdoCandidateList();

    public EdoCandidateBo getCandidate(String candidateID);
    public List<EdoCandidateBo> getCandidateListByUsername( String userName );
    public EdoCandidateBo getCandidateByUsername(String userName);
    								
    public List<EdoCandidateBo> getEdoCandidateList(String principalName, String groupKeyCode, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory);
}
