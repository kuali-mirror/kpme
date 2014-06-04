package org.kuali.kpme.edo.candidate.delegate.web;

import org.apache.struts.action.ActionMapping;
import org.kuali.kpme.edo.base.web.EdoForm;
import org.kuali.kpme.edo.candidate.delegate.EdoCandidateDelegate;
import org.kuali.rice.kim.api.role.RoleMembership;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class EdoAssignCandidateDelegateForm extends EdoForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;
	private String emplToAdd;
	private String emplToDelete;
	private String name;
	private String emplId;
	private boolean isCandidateDelegateAdded = false;
	private boolean isAlreadyCandidateDelegate = false;
	private boolean isCandidateDelegateDeleted = false;
	private Map<String, String> candidateDelegates = new HashMap<String, String>();
	private Date startDate;
	private Date endDate;
	//edo-114
	private List<RoleMembership> candidateDelegatesInfo = new ArrayList<RoleMembership>();
	//edo candidate delegate object
	private List<EdoCandidateDelegate> edoCandidateDelegatesInfo = new ArrayList<EdoCandidateDelegate>();
	private List<String> validDelegateRoles = new ArrayList<String>();
	
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		isCandidateDelegateAdded = false;
		isCandidateDelegateDeleted = false;
		isAlreadyCandidateDelegate = false;
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

	public boolean isCandidateDelegateAdded() {
		return isCandidateDelegateAdded;
	}

	public void setCandidateDelegateAdded(boolean isCandidateDelegateAdded) {
		this.isCandidateDelegateAdded = isCandidateDelegateAdded;
	}

	public boolean isAlreadyCandidateDelegate() {
		return isAlreadyCandidateDelegate;
	}

	public void setAlreadyCandidateDelegate(boolean isAlreadyCandidateDelegate) {
		this.isAlreadyCandidateDelegate = isAlreadyCandidateDelegate;
	}
	
	public boolean isCandidateDelegateDeleted() {
		return isCandidateDelegateDeleted;
	}

	public void setCandidateDelegateDeleted(boolean isCandidateDelegateDeleted) {
		this.isCandidateDelegateDeleted = isCandidateDelegateDeleted;
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

	

	public Map<String, String> getCandidateDelegates() {
		return candidateDelegates;
	}

	public void setCandidateDelegates(Map<String, String> candidateDelegates) {
		this.candidateDelegates = candidateDelegates;
	}

	public Date getStartDate() {
		return startDate;
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

	public List<RoleMembership> getCandidateDelegatesInfo() {
		return candidateDelegatesInfo;
	}

	public void setCandidateDelegatesInfo(
			List<RoleMembership> candidateDelegatesInfo) {
		this.candidateDelegatesInfo = candidateDelegatesInfo;
	}

	public List<EdoCandidateDelegate> getEdoCandidateDelegatesInfo() {
		return edoCandidateDelegatesInfo;
	}

	public void setEdoCandidateDelegatesInfo(
			List<EdoCandidateDelegate> edoCandidateDelegatesInfo) {
		this.edoCandidateDelegatesInfo = edoCandidateDelegatesInfo;
	}

    public List<String> getValidDelegateRoles() {
        return validDelegateRoles;
    }

    public void setValidDelegateRoles(List<String> validDelegateRoles) {
        this.validDelegateRoles = validDelegateRoles;
    }
}
