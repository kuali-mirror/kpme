/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.roles;

import org.kuali.hr.core.document.calendar.CalendarDocumentContract;
import org.kuali.hr.time.timesheet.TimesheetDocument;

import java.util.Set;

/**
 * Class to set the contract between Users and Roles.
 */
public interface UserRoles {

    public boolean isLocationAdmin();

    public boolean isDepartmentAdmin();

    public String getPrincipalId();

    public boolean isTimesheetReviewer();
    
    /**
     * Is the current user a system administrator?
     * @return true if yes, false otherwise
     */
    public boolean isSystemAdmin();

    /**
     * Does the current user have any active assignments?
     * @return true if yes, false otherwise.
     */
    public boolean isActiveEmployee();

    /**
     * Does the current user have any active approver roles?
     *
     * Approver roles attach at the WorkArea level.
     *
     * @return true if yes, false otherwise.
     */
    public boolean isAnyApproverActive();


    /**
     * Does the current user have any role that grants permission to approve
     * timesheets?
     * @return true if yes, false otherwise.
     */
    public boolean isTimesheetApprover();

    /**
     * Does the current user have the Global View Only role?
     *
     * @return true if yes, false otherwise.
     */
    public boolean isGlobalViewOnly();

    /**
     * Does the current user have an assignment that is marked synchronous?
     *
     * @return true if yes, false otherwise.
     */
    public boolean isSynchronous();

    /**
     * Provides a set of WorkArea names that this user is an approver for.
     * @return a Set<Long> of workarea names.
     */
    public Set<Long> getApproverWorkAreas();

    /**
     * Provides a Set of WorkArea names that this user is a reviewer for.
     * @return a Set<Long> of workarea names.
     */
    public Set<Long> getReviewerWorkAreas();

    /**
     * Provides a Set of Assignment ids that this user is active under.
     * @return a Set<Long> of assignment object ids.
     */
    public Set<String> getActiveAssignmentIds();

    /**
     * Provides a Set<String> of Department names that this user is a
     * departmental view only for.
     * @return a Set<String> of Department names.
     */
    public Set<String> getDepartmentViewOnlyDepartments();

    /**
     * Provides a Set<String> of Department names that this user is a chart
     * administrator for.
     * @return a Set<String> of Department names.
     */
    public Set<String> getOrgAdminDepartments();

    /**
     * Provides a Set<String> of chart names that this user is a chart
     * administrator for.
     * @return a Set<String> of Chart names.
     */
    public Set<String> getOrgAdminCharts();
    
    public boolean isDeptViewOnly();

    /**
     * Indicates whether or not the current can approve the provided timesheet.
     * @param doc The TimesheetDocument in question.
     * @return true if the doc can be approved by the current user.
     */
    public boolean isApproverForTimesheet(CalendarDocumentContract doc);
    public boolean isApproverForTimesheet(String docId);

    public boolean canSubmitTimesheet(CalendarDocumentContract doc);
    public boolean canSubmitTimesheet(String docId);

    public boolean isDocumentReadable(CalendarDocumentContract document);
    public boolean isDocumentReadable(String documentId);

    public boolean isDocumentWritable(CalendarDocumentContract document);
    public boolean isDocumentWritable(String documentId);
    
    public boolean isApproverForPerson(String principalId);

    boolean isDepartmentAdminForPerson(String principalId);

    boolean isDeptViewOnlyForPerson(String principalId);

    boolean isLocationAdminForPerson(String principalId);

    boolean isTimesheetReviewerForPerson(String principalId);
}
