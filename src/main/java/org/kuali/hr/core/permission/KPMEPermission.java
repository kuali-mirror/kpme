package org.kuali.hr.core.permission;

public enum KPMEPermission {
	
	VIEW_STARTED_TIMESHEET ("View Started Timesheet"),
	VIEW_ENROUTE_TIMESHEET ("View Enroute Timesheet"),
	VIEW_FINAL_TIMESHEET ("View Final Timesheet"),
	EDIT_STARTED_TIMESHEET ("Edit Started Timesheet"),
	EDIT_ENROUTE_TIMESHEET ("Edit Enroute Timesheet"),
	EDIT_FINAL_TIMESHEET ("Edit Final Timesheet"),
	SUBMIT_TIMESHEET ("Submit Timesheet"),
	APPROVE_TIMESHEET ("Approve Timesheet"),
	VIEW_STARTED_LEAVE_CALENDAR ("View Started Leave Calendar"),
	VIEW_ENROUTE_LEAVE_CALENDAR ("View Enroute Leave Calendar"),
	VIEW_FINAL_LEAVE_CALENDAR ("View Final Leave Calendar"),
	EDIT_STARTED_LEAVE_CALENDAR ("Edit Stored Leave Calendar"),
	EDIT_ENROUTE_LEAVE_CALENDAR ("Edit Enroute Leave Calendar"),
	EDIT_FINAL_LEAVE_CALENDAR ("Edit Final Leave Calendar"),
	SUBMIT_LEAVE_CALENDAR ("Submit Leave Calendar"),
	APPROVE_LEAVE_CALENDAR ("Approve Leave Calendar"),
	VIEW_STARTED_LEAVE_REQUEST ("View Started Leave Request"),
	VIEW_ENROUTE_LEAVE_REQUEST ("View Enroute Leave Request"),
	VIEW_FINAL_LEAVE_REQUEST ("View Final Leave Request"),
	EDIT_STARTED_LEAVE_REQUEST ("Edit Started Leave Request"),
	EDIT_ENROUTE_LEAVE_REQUEST ("Edit Enroute Leave Request"),
	EDIT_FINAL_LEAVE_REQUEST ("Edit Final Leave Request"),
	SUBMIT_LEAVE_REQUEST ("Submit Leave Request"),
	APPROVE_LEAVE_REQUEST ("Approve Leave Request"),
	VIEW_TIME_DEPARTMENTAL_RULES_INQUIRIES ("View Time Departmental Rules / Inquiries"),
	EDIT_TIME_DEPARTMENTAL_RULES_INQUIRIES ("Edit Time Departmental Rules / Inquiries"),
	VIEW_LEAVE_DEPARTMENTAL_RULES_INQUIRIES ("View Leave Departmental Rules / Inquiries"),
	EDIT_LEAVE_DEPARTMENTAL_RULES_INQUIRIES ("Edit Leave Departmental Rules / Inquiries"),
	TIME_VIEW_SYSTEM_RULES_INQUIRIES ("Time View System Rules / Inquiries"),
	TIME_EDIT_SYSTEM_RULES_INQUIRIES ("Time Edit System Rules / Inquiries"),
	LEAVE_VIEW_SYSTEM_RULES_INQUIRIES ("Leave View System Rules / Inquiries"),
	LEAVE_EDIT_SYSTEM_RULES_INQUIRIES ("Leave Edit System Rules / Inquiries"),
	SCHEDULE_BATCH_JOBS ("Schedule Batch Jobs");
	
	private String permissionName;
	
	private KPMEPermission(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

}