/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.time.permissions;

import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.time.authorization.DepartmentalRule;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;



public interface TkPermissionsService {

	public boolean canAddTimeBlock();
	   public boolean canEditTimeBlockAllFields(TimeBlock tb);
	   public boolean canEditTimeBlock(TimeBlock tb);
	   public boolean canDeleteTimeBlock(TimeBlock tb);
       public boolean canEditLeaveBlock(LeaveBlock lb);
       public boolean canDeleteLeaveBlock(LeaveBlock lb);
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
	   public boolean canViewTimeTabs();
	   public boolean canViewLeaveTabsWithEStatus();
	   public boolean canViewLeaveTabsWithNEStatus();
}
