package org.kuali.hr.time.permissions;

import org.kuali.hr.time.authorization.DepartmentalRule;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;



public interface TkPermissionsService {

	public boolean canAddTimeBlock();
	   public boolean canEditTimeBlockAllFields(TimeBlock tb);
	   public boolean canEditTimeBlock(TimeBlock tb);
	   public boolean canDeleteTimeBlock(TimeBlock tb);
	   public boolean canViewAdminTab();
	   public boolean canViewClockTab();
	   public boolean canViewApproverTab();
	   public boolean canViewTimeDetailTab();
	   public boolean canViewBatchJobsTab();
	   public boolean canViewPersonInfoTab();
	   public boolean canViewLeaveAccrualTab();
	   public boolean canViewTimesheet(TimesheetDocument doc);
	   public boolean canViewTimesheet(String documentId);
	   public boolean canEditTimesheet(TimesheetDocument doc);
	   public boolean canEditTimesheet(String documentId);
	   public boolean canSubmitTimesheet(TimesheetDocument doc);
	   public boolean canSubmitTimesheet(String docId);
	   public boolean canApproveTimesheet(TimesheetDocument doc);
	   public boolean canViewLinkOnMaintPages();
	   public boolean canViewDeptMaintPages();
	   public boolean canViewDeptMaintPages(DepartmentalRule dr);
	   public boolean canEditDeptMaintPages();
	   public boolean canEditDeptMaintPages(DepartmentalRule dr);
	   public boolean canWildcardWorkAreaInDeptRule(DepartmentalRule dr);
	   public boolean canWildcardDeptInDeptRule(DepartmentalRule dr);
	   public boolean canEditOvertimeEarnCode(TimeBlock tb);
	   public boolean canEditRegEarnCode(TimeBlock tb);
	   public boolean canDeleteDeptLunchDeduction();
	   public boolean canAddSystemLevelRole();
	   public boolean canAddLocationLevelRoles();
	   public boolean canAddDepartmentLevelRoles();
	   public boolean canAddWorkareaLevelRoles();
}
