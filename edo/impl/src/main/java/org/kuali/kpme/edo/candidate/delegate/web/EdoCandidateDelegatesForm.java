package org.kuali.kpme.edo.candidate.delegate.web;

import org.kuali.kpme.edo.base.web.EdoUifForm;

public class EdoCandidateDelegatesForm extends EdoUifForm{
	
	private static final long serialVersionUID = 1L;

	private String candidateDelegateName;
	private String candidateDelegateId;
	
	public EdoCandidateDelegatesForm() {
		super();
	}

	public String getCandidateDelegateName() {
		return candidateDelegateName;
	}

	public void setCandidateDelegateName(String candidateDelegateName) {
		this.candidateDelegateName = candidateDelegateName;
	}

	public String getCandidateDelegateId() {
		return candidateDelegateId;
	}

	public void setCandidateDelegateId(String candidateDelegateId) {
		this.candidateDelegateId = candidateDelegateId;
	}
}
