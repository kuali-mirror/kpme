package org.kuali.kpme.edo.candidate.guest;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

public class EdoCandidateGuest {
	private String guestPrincipalId;
	private String guestPrincipalName;
	private String guestFullName;
	private DateTime startDate;
	private DateTime endDate;
	private Map<String, String> guestDossierId = new HashMap<String, String>();
	private String dossierStatus;
	private String dossierType;
	
	public String getGuestPrincipalId() {
		return guestPrincipalId;
	}
	public void setGuestPrincipalId(String guestPrincipalId) {
		this.guestPrincipalId = guestPrincipalId;
	}
	public String getGuestPrincipalName() {
		return guestPrincipalName;
	}
	public void setGuestPrincipalName(String guestPrincipalName) {
		this.guestPrincipalName = guestPrincipalName;
	}
	public String getGuestFullName() {
		return guestFullName;
	}
	public void setGuestFullName(String guestFullName) {
		this.guestFullName = guestFullName;
	}
	public DateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
	}
	public DateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(DateTime endDate) {
		this.endDate = endDate;
	}
	public Map<String, String> getGuestDossierId() {
		return guestDossierId;
	}
	public void setGuestDossierId(Map<String, String> guestDossierId) {
		this.guestDossierId = guestDossierId;
	}
	public String getDossierStatus() {
		return dossierStatus;
	}
	public void setDossierStatus(String dossierStatus) {
		this.dossierStatus = dossierStatus;
	}
	public String getDossierType() {
		return dossierType;
	}
	public void setDossierType(String dossierType) {
		this.dossierType = dossierType;
	}
	
	
	

}
