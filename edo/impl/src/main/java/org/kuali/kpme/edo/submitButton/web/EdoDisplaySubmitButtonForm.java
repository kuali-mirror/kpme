package org.kuali.kpme.edo.submitButton.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.kuali.kpme.edo.base.web.EdoForm;
import org.kuali.kpme.edo.candidate.guest.EdoCandidateGuest;
import org.kuali.kpme.edo.submitButton.EdoSubmitButton;

public class EdoDisplaySubmitButtonForm extends EdoForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7709159313919519090L;
	private String campusCode;
	private Boolean activeFlag;
	private boolean isAdded = false;
	private List<EdoSubmitButton> submitButtonList = new ArrayList<EdoSubmitButton>();
	
   
	 	@Override
	    public void reset(ActionMapping mapping, HttpServletRequest request) {
		 isAdded = false;

	    }
	
	public String getCampusCode() {
		return campusCode;
	}
	public void setCampusCode(String campusCode) {
		this.campusCode = campusCode;
	}
	
	
	public Boolean getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}
	public boolean isAdded() {
		return isAdded;
	}
	public void setAdded(boolean isAdded) {
		this.isAdded = isAdded;
	}

	public List<EdoSubmitButton> getSubmitButtonList() {
		return submitButtonList;
	}

	public void setSubmitButtonList(List<EdoSubmitButton> submitButtonList) {
		this.submitButtonList = submitButtonList;
	}
	
	
	
	
	
	
}
