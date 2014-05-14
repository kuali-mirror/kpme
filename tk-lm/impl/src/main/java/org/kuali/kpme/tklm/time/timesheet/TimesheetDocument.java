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
package org.kuali.kpme.tklm.time.timesheet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.api.job.Job;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.document.calendar.CalendarDocument;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.api.time.timesheet.TimesheetDocumentContract;
import org.kuali.kpme.tklm.time.rules.timecollection.TimeCollectionRule;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesummary.TimeSummary;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.krad.util.GlobalVariables;

import java.util.*;


public class TimesheetDocument extends CalendarDocument implements TimesheetDocumentContract {

	private static final long serialVersionUID = 405213065253263185L;

	/**
	 * This static member is needed by document search, to trigger the correct calendar document
	 * opening when clicking on a doc id link in the search results.
	 * It is distinguished from "HrConstants.LEAVE_CALENDAR_TYPE".
	 */
	public static final String TIMESHEET_DOCUMENT_TYPE = "TimesheetDocument";

	private List<Job> jobs = new LinkedList<Job>();
	private List<TimeBlock> timeBlocks = new LinkedList<TimeBlock>();
	private Map<Long, Job> jobNumberToJobMap = new HashMap<Long,Job>();
    private TimesheetDocumentHeader documentHeader;

	public TimesheetDocument(TimesheetDocumentHeader documentHeader) {
		this.documentHeader = documentHeader;
		setCalendarType(TIMESHEET_DOCUMENT_TYPE);
	}

	@Override
	public TimesheetDocumentHeader getDocumentHeader() {
		return documentHeader;
	}

	public void setDocumentHeader(TimesheetDocumentHeader documentHeader) {
		this.documentHeader = documentHeader;
	}

	public List<Job> getJobs() {
		return jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
		jobNumberToJobMap.clear();
		if(jobs!=null){
			for(Job job : jobs){
				jobNumberToJobMap.put(job.getJobNumber(), job);
			}
		}
	}

	public List<TimeBlock> getTimeBlocks() {
		return timeBlocks;
	}

	public void setTimeBlocks(List<TimeBlock> timeBlocks) {
		this.timeBlocks = timeBlocks;
	}

	public TimeSummary getTimeSummary() {
        return (TimeSummary)TkServiceLocator.getTimeSummaryService().getTimeSummary(getPrincipalId(), getTimeBlocks(), getCalendarEntry(), getAssignmentMap());
	}

    @Override
	public String getPrincipalId(){
		return getDocumentHeader() == null ? null : getDocumentHeader().getPrincipalId();
	}

	public Job getJob(Long jobNumber){
		return jobNumberToJobMap.get(jobNumber);
	}



    @Override
	public String getDocumentId(){
		return getDocumentHeader() == null ? null : getDocumentHeader().getDocumentId();
	}

    public Map<String, List<LocalDate>> getEarnCodeMap() {
        Map<String, List<LocalDate>> earnCodeMap = new HashMap<String, List<LocalDate>>();
        for(TimeBlock tb : getTimeBlocks()) {
            if(!earnCodeMap.containsKey(tb.getEarnCode())) {
                List<LocalDate> lst = new ArrayList<LocalDate>();
                lst.add(tb.getBeginDateTime().toLocalDate());
                earnCodeMap.put(tb.getEarnCode(), lst);
            } else {
                earnCodeMap.get(tb.getEarnCode()).add(tb.getBeginDateTime().toLocalDate());
            }
        }
        return earnCodeMap;
    }
	
    public Map<String, String> getAssignmentDescriptions(boolean clockOnlyAssignments, LocalDate date) {
        Map<String, String> assignmentDescriptions = new LinkedHashMap<String, String>();
        List<Assignment> dayAssignments = getAssignmentMap().get(date);
        if (CollectionUtils.isNotEmpty(dayAssignments)) {
            for (Assignment assignment : dayAssignments) {
                String principalId = GlobalVariables.getUserSession().getPrincipalId();

                if (HrServiceLocator.getHRPermissionService().canViewCalendarDocumentAssignment(principalId, this, assignment)) {
                    TimeCollectionRule tcr = null;
                    if (assignment.getJob() != null) {
                        tcr = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule(assignment.getJob().getDept(), assignment.getWorkArea(), assignment.getJob().getHrPayType(), assignment.getGroupKeyCode(), LocalDate.now());
                    }
                    boolean isSynchronous = tcr == null || tcr.isClockUserFl();
                    if (!clockOnlyAssignments || isSynchronous) {
                        assignmentDescriptions.putAll(TKUtils.formatAssignmentDescription(assignment));
                    }
                }
            }
        }
        
        return assignmentDescriptions;
    }
    
    public Map<String, String> getAssignmentDescriptionsOfApprovals(boolean clockOnlyAssignments) {
        Map<String, String> assignmentDescriptions = new LinkedHashMap<String, String>();
        List<Assignment> allAssignments = getAllAssignments();
        for (Assignment assignment : allAssignments) {
        	String principalId = GlobalVariables.getUserSession().getPrincipalId();

        	if (HrServiceLocator.getHRPermissionService().canViewCalendarDocumentAssignment(principalId, this, assignment)) {
        		TimeCollectionRule tcr = null;
        		if(assignment.getJob() != null)
        			tcr = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule(assignment.getJob().getDept(), assignment.getWorkArea(), assignment.getJob().getHrPayType(), assignment.getGroupKeyCode(), LocalDate.now());
        		boolean isSynchronous = tcr == null || tcr.isClockUserFl();
                if (!clockOnlyAssignments || isSynchronous) {
    				Long workArea = assignment.getWorkArea();
                    String dept = assignment.getJob() == null ? StringUtils.EMPTY : assignment.getJob().getDept();
        			DateTime startOfToday = LocalDate.now().toDateTimeAtStartOfDay();
                    boolean isApproverOrReviewerForCurrentAssignment =
                            HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), workArea, startOfToday)
        					|| HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER_DELEGATE.getRoleName(), workArea, startOfToday)
        					|| HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.REVIEWER.getRoleName(), workArea, startOfToday)
                            || HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR.getRoleName(), dept, startOfToday)
                            || HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR_DELEGATE.getRoleName(), dept, startOfToday);
                    if(isApproverOrReviewerForCurrentAssignment) {
                    	assignmentDescriptions.putAll(TKUtils.formatAssignmentDescription(assignment));
                    }
                }
            }
        }
        
        return assignmentDescriptions;
    }
}
