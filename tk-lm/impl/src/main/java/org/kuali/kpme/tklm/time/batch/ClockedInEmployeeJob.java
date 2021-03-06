/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.tklm.time.batch;


import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.calendar.Calendar;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.batch.BatchJob;
import org.kuali.kpme.core.batch.BatchJobUtil;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.common.TkConstants;
import org.kuali.kpme.tklm.api.time.clocklog.ClockLog;
import org.kuali.kpme.tklm.time.clocklog.ClockLogBo;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.note.Note;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.role.RoleMember;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class ClockedInEmployeeJob extends BatchJob {


    public void execute(JobExecutionContext context) throws JobExecutionException {
        //Get Configuration Settings
        BigDecimal hourLimit = getHourLimit();
        String jobAction = getJobAction();
        String batchJobPrincipalId = BatchJobUtil.getBatchUserPrincipalId();


        // code to send one email per approver
        // Map<String, Map<String, String>> notificationMap = new HashMap<String, Map<String, String>>();

        DateTime asOfDate = new LocalDate().toDateTimeAtStartOfDay();
        List<CalendarEntry> calendarEntries = HrServiceLocator.getCalendarEntryService().getCurrentCalendarEntriesNeedsScheduled(30, asOfDate);

        for (CalendarEntry calendarEntry : calendarEntries) {
            Calendar calendar = HrServiceLocator.getCalendarService().getCalendar(calendarEntry.getHrCalendarId());
            if (StringUtils.equals(calendar.getCalendarTypes(), "Pay")) {
                DateTime beginDate = calendarEntry.getBeginPeriodFullDateTime();
                DateTime endDate = calendarEntry.getEndPeriodFullDateTime();

                List<TimesheetDocumentHeader> timesheetDocumentHeaders = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeaders(beginDate, endDate);

                for (TimesheetDocumentHeader timesheetDocumentHeader : timesheetDocumentHeaders) {
                    String principalId = timesheetDocumentHeader.getPrincipalId();
                    List<Assignment> assignments = HrServiceLocator.getAssignmentService().getAllAssignmentsByCalEntryForTimeCalendar(principalId, calendarEntry);
                    for (Assignment assignment : assignments) {
                    	String groupKeyCode = assignment.getGroupKeyCode();
                        String jobNumber = String.valueOf(assignment.getJobNumber());
                        String workArea = String.valueOf(assignment.getWorkArea());
                        String task = String.valueOf(assignment.getTask());
                        ClockLog lastClockLog = TkServiceLocator.getClockLogService().getLastClockLog(groupKeyCode, principalId, jobNumber, workArea, task, calendarEntry);
                        if (lastClockLog != null && TkConstants.ON_THE_CLOCK_CODES.contains(lastClockLog.getClockAction())) {
                            DateTime lastClockLogDateTime = lastClockLog.getClockDateTime();
                            DateTime currentDate = new DateTime();

                            Period p = new Period(lastClockLogDateTime, currentDate);
                            BigDecimal hoursBetween = new BigDecimal(p.getHours());
                            BigDecimal dayHours = new BigDecimal(p.getDays() * 24);
                            hoursBetween = hoursBetween.add(dayHours);

                            if (hoursBetween.compareTo(hourLimit) > 0) {
                                if (jobAction.equals("NOTIFY")) {

                                    //code to send one notification per employee to all approvers of employee
                                    for (Person approver : getApprovers(assignment.getWorkArea())) {
                                        EntityNamePrincipalName employee = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(principalId);
                                        String approverSubject = "Employee Clocked In Over " + hourLimit.toString() + " Hours Notification";
                                        StringBuilder approverNotification = new StringBuilder();
                                        approverNotification.append(employee.getPrincipalName() + " (" + principalId + ") has been clocked in since ");
                                        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM d yyyy HH:mm a");
                                        String dateTime = sdf.format(new java.sql.Date(lastClockLog.getClockDateTime().getMillis()));
                                        approverNotification.append(dateTime);
                                        approverNotification.append(" for work area " + assignment.getWorkAreaObj().getDescription());
                                        HrServiceLocator.getKPMENotificationService().sendNotification(approverSubject, approverNotification.toString(), approver.getPrincipalId());
                                    }

                                /* Code to send one email per approver - Create notification
                                Map<String, String> hourInfo = new HashMap<String, String>();
                                hourInfo.put(principalId, hoursBetween.toString());
                                for (Person person : getApprovers(lastClockLog.getWorkArea())) {
                                    if (notificationMap.containsKey(person.getPrincipalId())) {
                                        notificationMap.get(person.getPrincipalId()).put(principalId, hoursBetween.toString());
                                    } else {
                                        notificationMap.put(person.getPrincipalId(), hourInfo);
                                    }
                                } */
                                } else if (jobAction.equals("CLOCK_OUT")) {
                                    //Clock User Out
                                    ClockLog clockOut = TkServiceLocator.getClockLogService().processClockLog(principalId, timesheetDocumentHeader.getDocumentId(), currentDate, assignment, calendarEntry, TKUtils.getIPNumber(),
                                            currentDate.toLocalDate(), "CO", true, batchJobPrincipalId);

                                    TkServiceLocator.getClockLogService().saveClockLog(clockOut);

                                    // Notify User
                                    String employeeSubject = "You have been clocked out of " + assignment.getAssignmentDescription();
                                    StringBuilder employeeNotification = new StringBuilder();
                                    employeeNotification.append("You have been Clocked out of " + assignment.getAssignmentDescription() + " on " + clockOut.getClockDateTime());
                                    HrServiceLocator.getKPMENotificationService().sendNotification(employeeSubject, employeeNotification.toString(), principalId);


                                    //add Note to time sheet
                                    Note.Builder builder = Note.Builder.create(timesheetDocumentHeader.getDocumentId(), batchJobPrincipalId);
                                    builder.setCreateDate(new DateTime());
                                    builder.setText("Clock out from " + assignment.getAssignmentDescription() + " on " + clockOut.getClockDateTime() + " was initiated by the Clocked In Employee Batch Job");
                                    KewApiServiceLocator.getNoteService().createNote(builder.build());
                                }


                            }
                        }
                    }
                }
            }
        }

        /* code to send one email per approver
        for (Map.Entry<String, Map<String, String>> approverEntry : notificationMap.entrySet()) {
            String subject = "Users clocked in over " + hourLimit.toString() + " hours";
            StringBuilder notification = new StringBuilder();
            notification.append("The following users have been clocked in for over " + hourLimit.toString() + " hours:");
            notification.append(SystemUtils.LINE_SEPARATOR);
            for (Map.Entry<String, String> employeeEntry : approverEntry.getValue().entrySet()) {
                notification.append(KimApiServiceLocator.getPersonService().getPerson(employeeEntry.getKey()).getPrincipalName());
                notification.append(" (" + employeeEntry.getKey() + ") : " + employeeEntry.getValue() + " hours");
                notification.append(SystemUtils.LINE_SEPARATOR);
            }
            HrServiceLocator.getKPMENotificationService().sendNotification(subject, notification.toString(), approverEntry.getKey());
        }
        */

    }

    private Set<Person> getApprovers(Long workArea) {
        Set<Person> approvers = new HashSet<Person>();
        DateTime date = LocalDate.now().toDateTimeAtStartOfDay();
        List<RoleMember> roleMembers = HrServiceLocator.getKPMERoleService().getRoleMembersInWorkArea(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), workArea, date, true);

        for (RoleMember roleMember : roleMembers) {
            Person person = KimApiServiceLocator.getPersonService().getPerson(roleMember.getMemberId());
            if (person != null) {
                approvers.add(person);
            }
        }

        return approvers;
    }

    private BigDecimal getHourLimit() {
        String hourLimitString = ConfigContext.getCurrentContextConfig().getProperty("kpme.batch.clockedInEmployee.hourLimit");
        BigDecimal hourLimit = new BigDecimal(hourLimitString);
        return hourLimit;
    }

    private String getJobAction() {
        return ConfigContext.getCurrentContextConfig().getProperty("kpme.batch.clockedInEmployee.jobAction");
    }
}
