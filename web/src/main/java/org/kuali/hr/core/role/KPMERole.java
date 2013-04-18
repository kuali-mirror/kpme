package org.kuali.hr.core.role;

public enum KPMERole {
	
	REVIEWER ("Reviewer"),
	APPROVER ("Approver"),
	APPROVER_DELEGATE ("Approver Delegate"),
	TIME_DEPARTMENT_VIEW_ONLY ("Time Department View Only"),
	TIME_DEPARTMENT_ADMINISTRATOR ("Time Department Administrator"),
	LEAVE_DEPARTMENT_VIEW_ONLY ("Leave Department View Only"),
	LEAVE_DEPARTMENT_ADMINISTRATOR ("Leave Department Administrator"),
	TIME_LOCATION_VIEW_ONLY ("Time Location View Only"),
	TIME_LOCATION_ADMINISTRATOR ("Time Location Administrator"),
	LEAVE_LOCATION_VIEW_ONLY ("Leave Location View Only"),
	LEAVE_LOCATION_ADMINISTRATOR ("Leave Location Administrator"),
	TIME_SYSTEM_VIEW_ONLY ("Time System View Only"),
	TIME_SYSTEM_ADMINISTRATOR ("Time System Administrator"),
	LEAVE_SYSTEM_VIEW_ONLY ("Leave System View Only"),
	LEAVE_SYSTEM_ADMINISTRATOR ("Leave System Administrator");
	
	private String roleName;
	
	private KPMERole(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}