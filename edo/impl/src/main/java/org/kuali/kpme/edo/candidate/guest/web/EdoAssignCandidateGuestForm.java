package org.kuali.kpme.edo.candidate.guest.web;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.kuali.kpme.edo.base.web.EdoForm;
import org.kuali.kpme.edo.candidate.guest.EdoCandidateGuest;
import org.kuali.kpme.edo.dossier.EdoDossierBo;

public class EdoAssignCandidateGuestForm extends EdoForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4632416851384142209L;
	private String userId;
	private String emplToAdd;
	private String emplToDelete;
	private String name;
	private String emplId;
	private boolean isCandidateGuestAdded = false;
	private boolean isAlreadyCandidateGuest = false;
	private boolean isCandidateGuestDeleted = false;
	//private Map<String, String> candidateGuests = new HashMap<String, String>();
	//edo candidate delegate object
	private List<EdoCandidateGuest> candidateGuests = new ArrayList<EdoCandidateGuest>();
	private Date startDate;
	private Date endDate;
	//create a drop down for allowing candidated pick which eDossier they have to assign guest
	 private BigDecimal dossierId;
	 private List<EdoDossierBo> dossierDropDown = new ArrayList<EdoDossierBo>();
	 private String dossierIdToDelete;
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		isCandidateGuestAdded = false;
		isAlreadyCandidateGuest = false;
		isCandidateGuestDeleted = false;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmplToAdd() {
		return emplToAdd;
	}

	public void setEmplToAdd(String emplToAdd) {
		this.emplToAdd = emplToAdd;
	}
	
	public String getEmplToDelete() {
		return emplToDelete;
	}

	public void setEmplToDelete(String emplToDelete) {
		this.emplToDelete = emplToDelete;
	}

	public boolean isCandidateGuestAdded() {
		return isCandidateGuestAdded;
	}

	public void setCandidateGuestAdded(boolean isCandidateGuestAdded) {
		this.isCandidateGuestAdded = isCandidateGuestAdded;
	}

	public boolean isAlreadyCandidateGuest() {
		return isAlreadyCandidateGuest;
	}

	public void setAlreadyCandidateGuest(boolean isAlreadyCandidateGuest) {
		this.isAlreadyCandidateGuest = isAlreadyCandidateGuest;
	}

	public boolean isCandidateGuestDeleted() {
		return isCandidateGuestDeleted;
	}

	public void setCandidateGuestDeleted(boolean isCandidateGuestDeleted) {
		this.isCandidateGuestDeleted = isCandidateGuestDeleted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmplId() {
		return emplId;
	}


	public void setEmplId(String emplId) {
		this.emplId = emplId;
	}

	/*public Map<String, String> getCandidateGuests() {
		return candidateGuests;
	}

	public void setCandidateGuests(Map<String, String> candidateGuests) {
		this.candidateGuests = candidateGuests;
	}*/
	
	public Date getStartDate() {
		return startDate;
	}

	public List<EdoCandidateGuest> getCandidateGuests() {
		return candidateGuests;
	}

	public void setCandidateGuests(List<EdoCandidateGuest> candidateGuests) {
		this.candidateGuests = candidateGuests;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	

	public BigDecimal getDossierId() {
		return dossierId;
	}

	public void setDossierId(BigDecimal dossierId) {
		this.dossierId = dossierId;
	}

	public List<EdoDossierBo> getDossierDropDown() {
		return dossierDropDown;
	}

	public void setDossierDropDown(List<EdoDossierBo> dossierDropDown) {
		this.dossierDropDown = dossierDropDown;
	}

	public String getDossierIdToDelete() {
		return dossierIdToDelete;
	}

	public void setDossierIdToDelete(String dossierIdToDelete) {
		this.dossierIdToDelete = dossierIdToDelete;
	}

	
	
}
