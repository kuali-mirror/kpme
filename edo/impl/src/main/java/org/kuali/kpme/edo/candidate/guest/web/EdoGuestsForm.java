package org.kuali.kpme.edo.candidate.guest.web;

import org.kuali.kpme.edo.base.web.EdoUifForm;

public class EdoGuestsForm extends EdoUifForm{

	private static final long serialVersionUID = 1L;

	private String guestName;
	private String guestId;
	
	public EdoGuestsForm() {
		super();
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public String getGuestId() {
		return guestId;
	}

	public void setGuestId(String guestId) {
		this.guestId = guestId;
	}
	
}
