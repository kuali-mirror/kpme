package org.kuali.kpme.edo.base.web;

import org.kuali.kpme.core.util.HrContext;
import org.kuali.rice.krad.web.form.UifFormBase;

public class EdoUifForm extends UifFormBase {
	
	private static final long serialVersionUID = 1L;
	
	private String employeeName;
	private String employeeId;
	private String userName;
	private String userDept;
	private String targetEmployeeName;
	private String targetEmployeeId;
	
	private boolean targetFlag;
	private boolean myDossierVisible;
	private boolean candidateDelegatesVisible;
	private boolean guestVisible;
	
	public EdoUifForm() {
		super();
		
		this.setEmployeeId(HrContext.getPrincipalId());
		this.setEmployeeName(HrContext.getPrincipalName());
		this.setUserName(HrContext.getPrincipalName());
		this.setUserDept("test");
		this.setTargetEmployeeName(HrContext.getTargetName());
		this.setTargetEmployeeId(HrContext.getTargetPrincipalId());
		
		this.setTargetFlag(HrContext.isTargetingUser());
		this.setMyDossierVisible(true);
		this.setCandidateDelegatesVisible(true);
		this.setGuestVisible(true);
	}
	
	
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserDept() {
		return userDept;
	}
	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}

	public void setTargetFlag(boolean targetFlag) {
		this.targetFlag = targetFlag;
	}


	public String getTargetEmployeeName() {
		return targetEmployeeName;
	}


	public void setTargetEmployeeName(String targetEmployeeName) {
		this.targetEmployeeName = targetEmployeeName;
	}


	public String getTargetEmployeeId() {
		return targetEmployeeId;
	}


	public void setTargetEmployeeId(String targetEmployeeId) {
		this.targetEmployeeId = targetEmployeeId;
	}
	
	public boolean isTargetFlag() {
		return targetFlag;
	}


	public boolean isMyDossierVisible() {
		return myDossierVisible;
	}


	public void setMyDossierVisible(boolean myDossierVisible) {
		this.myDossierVisible = myDossierVisible;
	}


	public boolean isCandidateDelegatesVisible() {
		return candidateDelegatesVisible;
	}


	public void setCandidateDelegatesVisible(boolean candidateDelegatesVisible) {
		this.candidateDelegatesVisible = candidateDelegatesVisible;
	}


	public boolean isGuestVisible() {
		return guestVisible;
	}


	public void setGuestVisible(boolean guestVisible) {
		this.guestVisible = guestVisible;
	}

}
