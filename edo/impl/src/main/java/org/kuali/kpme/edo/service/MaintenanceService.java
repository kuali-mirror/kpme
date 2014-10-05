package org.kuali.kpme.edo.service;

//import java.sql.Date;

import org.kuali.kpme.edo.candidate.delegate.EdoCandidateDelegate;
import org.kuali.kpme.edo.candidate.delegate.EdoChairDelegate;
import org.kuali.kpme.edo.candidate.guest.EdoCandidateGuest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;



public interface MaintenanceService {

	public boolean saveDelegateForCandidate(String emplid, Date startDate, Date endDate, String facultyId, String roleName);
    public boolean updateDelegateForCandidate(String emplid, Date startDate, Date endDate, String facultyId, String roleName);
    public boolean expireAllDelegatesForCandidate(String facultyId);
    public boolean deleteCandidateDelegate(String emplId, String facultyId, String roleName);
    public boolean deleteGuestForCandidateDossier(String guestId,  String dossierId);
    public boolean saveGuestForDossierId(String emplid, Date startDate, Date endDate, BigDecimal dossierId);
    public boolean hasCandidateRole(String emplid);
    public boolean hasChairRole(String emplid);
    public boolean hasSuperUserRole(String emplid);
    public boolean hasCandidateRole_W(String emplid);
    public boolean hasChairRole_W(String emplid);
    public boolean hasSuperUserRole_W(String emplid);

    public List<String> getDelegatesCandidateList(String delegateEmplid);
	public Map<String, String> getCandidateDelegates(String emplid, String roleName);
	public List<String> getCandidateDelegatesEmplIds(String emplid, String roleName);
	public List<EdoCandidateGuest> getCandidateGuests(String emplid);
	public List<String> getCandidateExistingGuests(BigDecimal dossierId);
	public List<String> getGuestDossierList(String guestEmplid);

	public List<EdoCandidateDelegate> getCandidateDelegateInfo(String emplid, String roleName);
    public List<EdoChairDelegate> getChairDelegateInfo(String emplid, String roleName);
    //check to see the existing delegates for the logged in chair
    public List<String> getChairDelegatesEmplIds(String emplid, String roleName);
    public List<String> getChairListForDelegate(String chairDelegateEmplid);
    public boolean saveDelegateForChair(String emplid, Date startDate, Date endDate, String facultyId, String roleName);
    public boolean deleteChairDelegate(String emplId, String facultyId, String roleName);
    public List<String> getSecondUnitReviewerDossierList(String secondUnitRevEmplid);
    public List<String> getCandidateSecondaryUnitReviewers(BigDecimal dossierId);


}
