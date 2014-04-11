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
package org.kuali.kpme.tklm.time.timesheet.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.kuali.kpme.core.api.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.block.CalendarBlockPermissions;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.api.earncode.EarnCode;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.api.earncode.security.EarnCodeSecurityContract;
import org.kuali.kpme.core.api.job.JobContract;
import org.kuali.kpme.core.api.permission.HRPermissionService;
import org.kuali.kpme.core.api.principal.PrincipalHRAttributesContract;
import org.kuali.kpme.core.batch.BatchJobUtil;
import org.kuali.kpme.core.earncode.security.EarnCodeType;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.common.TkConstants;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.api.leave.timeoff.SystemScheduledTimeOffContract;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.common.LMConstants;
import org.kuali.kpme.tklm.common.WorkflowTagSupport;
import org.kuali.kpme.tklm.leave.block.LeaveBlockAggregate;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.time.flsa.FlsaDay;
import org.kuali.kpme.tklm.time.flsa.FlsaWeek;
import org.kuali.kpme.tklm.time.rules.timecollection.TimeCollectionRule;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.util.TkTimeBlockAggregate;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.WorkflowDocumentFactory;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kew.api.note.Note;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

import java.math.BigDecimal;
import java.util.*;

public class TimesheetServiceImpl implements TimesheetService {

    private static final Logger LOG = Logger.getLogger(TimesheetServiceImpl.class);
    private static final ModelObjectUtils.Transformer<TimeBlock, TimeBlock.Builder> toTimeBlockBuilder =
            new ModelObjectUtils.Transformer<TimeBlock, TimeBlock.Builder>() {
                public TimeBlock.Builder transform(TimeBlock input) {
                    return TimeBlock.Builder.create(input);
                };
            };
    private HRPermissionService hrPermissionService;

    @Override
    public void routeTimesheet(String principalId, TimesheetDocument timesheetDocument) {
        routeTimesheet(principalId, timesheetDocument, HrConstants.DOCUMENT_ACTIONS.ROUTE);
    }

    @Override
    public void routeTimesheet(String principalId, TimesheetDocument timesheetDocument, String action) {
        timesheetAction(action, principalId, timesheetDocument);
    }

    @Override
    public void approveTimesheet(String principalId, TimesheetDocument timesheetDocument) {
        timesheetAction(HrConstants.DOCUMENT_ACTIONS.APPROVE, principalId, timesheetDocument);
    }

    @Override
    public void approveTimesheet(String principalId, TimesheetDocument timesheetDocument, String action) {
        timesheetAction(action, principalId, timesheetDocument);
    }

    @Override
    public void disapproveTimesheet(String principalId, TimesheetDocument timesheetDocument) {
        timesheetAction(HrConstants.DOCUMENT_ACTIONS.DISAPPROVE, principalId, timesheetDocument);
    }

    protected void timesheetAction(String action, String principalId, TimesheetDocument timesheetDocument) {
        WorkflowDocument wd = null;
        if (timesheetDocument != null) {
            String rhid = timesheetDocument.getDocumentId();
            wd = WorkflowDocumentFactory.loadDocument(principalId, rhid);

            if (StringUtils.equals(action, HrConstants.DOCUMENT_ACTIONS.ROUTE)) {
                wd.route("Routing for Approval");
            } else if (StringUtils.equals(action, HrConstants.BATCH_JOB_ACTIONS.BATCH_JOB_ROUTE)) {
            	Note.Builder builder = Note.Builder.create(rhid, principalId);
                builder.setCreateDate(new DateTime());
                builder.setText("Routed via Employee Approval batch job");
            	KewApiServiceLocator.getNoteService().createNote(builder.build());

            	wd.route("Batch job routing timesheet");
            } else if (StringUtils.equals(action, HrConstants.DOCUMENT_ACTIONS.APPROVE)) {
                if (HrServiceLocator.getHRPermissionService().canSuperUserAdministerCalendarDocument(GlobalVariables.getUserSession().getPrincipalId(), timesheetDocument)
                		&& !HrServiceLocator.getHRPermissionService().canApproveCalendarDocument(GlobalVariables.getUserSession().getPrincipalId(), timesheetDocument)) {
                    wd.superUserBlanketApprove("Superuser approving timesheet.");
                } else {
                    wd.approve("Approving timesheet.");
                }
            } else if (StringUtils.equals(action, HrConstants.BATCH_JOB_ACTIONS.BATCH_JOB_APPROVE)) {
            	Note.Builder builder = Note.Builder.create(rhid, principalId);
           	 	builder.setCreateDate(new DateTime());
           	 	builder.setText("Approved via Supervisor Approval batch job");
           	 	KewApiServiceLocator.getNoteService().createNote(builder.build());

            	wd.superUserBlanketApprove("Batch job approving timesheet.");
            } else if (StringUtils.equals(action, HrConstants.DOCUMENT_ACTIONS.DISAPPROVE)) {
                if (HrServiceLocator.getHRPermissionService().canSuperUserAdministerCalendarDocument(GlobalVariables.getUserSession().getPrincipalId(), timesheetDocument)
                		&& !HrServiceLocator.getHRPermissionService().canApproveCalendarDocument(GlobalVariables.getUserSession().getPrincipalId(), timesheetDocument)) {
                    wd.superUserDisapprove("Superuser disapproving timesheet.");
                } else {
                    wd.disapprove("Disapproving timesheet.");
                }
            }
            clearTimesheetTimeblockPermissions(timesheetDocument);
        }
    }

    @Override
    public TimesheetDocument openTimesheetDocument(String principalId, CalendarEntry calendarDates) throws WorkflowException {
        TimesheetDocument timesheetDocument = null;

        DateTime begin = calendarDates.getBeginPeriodFullDateTime();
        DateTime end = calendarDates.getEndPeriodFullDateTime();

        TimesheetDocumentHeader header = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(principalId, begin, end);

        if (header == null) {
            List<Assignment> activeAssignments = HrServiceLocator.getAssignmentService().getAllAssignmentsByCalEntryForTimeCalendar(principalId, calendarDates);
            //HrServiceLocator.getAssignmentService().getAssignmentMap(principalId, TKUtils.getTimelessDate(payCalendarDates.getEndPeriodDate()));
            if (CollectionUtils.isEmpty(activeAssignments)) {
                LOG.warn("No active assignments for " + principalId + " for " + calendarDates.getEndPeriodFullDateTime());
                return null;
                //throw new RuntimeException("No active assignments for " + principalId + " for " + calendarDates.getEndPeriodDate());
            }

            EntityNamePrincipalName person = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(principalId);
            String principalName = person != null && person.getDefaultName() != null ? person.getDefaultName().getCompositeName() : StringUtils.EMPTY;
            String endDateString = TKUtils.formatDate(end.toLocalDate());
            String timesheetDocumentTitle = TimesheetDocument.TIMESHEET_DOCUMENT_TYPE + " - " + principalName + " (" + principalId + ") - " + endDateString;

            timesheetDocument = this.initiateWorkflowDocument(principalId, begin, end, calendarDates, TimesheetDocument.TIMESHEET_DOCUMENT_TYPE, timesheetDocumentTitle);
            //timesheetDocument.setPayCalendarEntry(calendarDates);
            //this.loadTimesheetDocumentData(timesheetDocument, principalId, calendarDates);
            //TODO switch this to scheduled time offs
            //this.loadHolidaysOnTimesheet(timesheetDocument, principalId, begin, end);
        } else {
            timesheetDocument = this.getTimesheetDocument(header.getDocumentId());
            if (timesheetDocument != null) {
            	timesheetDocument.setCalendarEntry(calendarDates);
            }
        }

        //if (timesheetDocument != null) {
        //	timesheetDocument.setTimeSummary(TkServiceLocator.getTimeSummaryService().getTimeSummary(timesheetDocument));
        //}

        return timesheetDocument;
    }

    public void loadHolidaysOnTimesheet(TimesheetDocument timesheetDocument, String principalId, LocalDate beginDate, LocalDate endDate) {
        PrincipalHRAttributesContract principalCalendar = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, beginDate);
        if (principalCalendar != null && StringUtils.isNotEmpty(principalCalendar.getLeavePlan())) {
        	List<? extends SystemScheduledTimeOffContract> sstoList = LmServiceLocator.getSysSchTimeOffService()
        		.getSystemScheduledTimeOffForPayPeriod(principalCalendar.getLeavePlan(), beginDate, endDate);
            Assignment sstoAssign = getAssignmentToApplyScheduledTimeOff(timesheetDocument.getPrincipalId(), timesheetDocument.getAllAssignments(), endDate);
        	if (sstoAssign != null) {
        		for(SystemScheduledTimeOffContract ssto : sstoList) {
                  BigDecimal sstoCalcHours = LmServiceLocator.getSysSchTimeOffService().calculateSysSchTimeOffHours(sstoAssign.getJob(), ssto.getAmountofTime());
                  TimeBlock timeBlock = TkServiceLocator.getTimeBlockService().createTimeBlock(timesheetDocument.getPrincipalId(), timesheetDocument.getDocumentId(), ssto.getScheduledTimeOffLocalDate().toDateTimeAtStartOfDay(),
                          ssto.getScheduledTimeOffLocalDate().toDateTimeAtStartOfDay(), sstoAssign, HrConstants.HOLIDAY_EARN_CODE, sstoCalcHours, BigDecimal.ZERO, false, false, HrContext.getPrincipalId());
                  timesheetDocument.getTimeBlocks().add(timeBlock);
              }
	            //If system scheduled time off are loaded will need to save them to the database
		        if (CollectionUtils.isNotEmpty(sstoList)) {
		           TkServiceLocator.getTimeBlockService().saveOrUpdateTimeBlocks(Collections.<TimeBlock>emptyList(), timesheetDocument.getTimeBlocks(), HrContext.getPrincipalId());
		        }
        	}
        }
    }

    private Assignment getAssignmentToApplyScheduledTimeOff(String principalId, List<Assignment> assignments, LocalDate endDate) {
		JobContract primaryJob = HrServiceLocator.getJobService().getPrimaryJob(principalId, endDate);
		for(Assignment assign : assignments){
			if(assign.getJobNumber().equals(primaryJob.getJobNumber())){
				return assign;
			}
		}
		return null;
	}

	protected TimesheetDocument initiateWorkflowDocument(String principalId, DateTime payBeginDate,  DateTime payEndDate, CalendarEntry calendarEntry, String documentType, String title) throws WorkflowException {
        TimesheetDocument timesheetDocument = null;
        WorkflowDocument workflowDocument = null;

        workflowDocument = WorkflowDocumentFactory.createDocument(principalId, documentType, title);

        String status = workflowDocument.getStatus().getCode();
        TimesheetDocumentHeader documentHeader = new TimesheetDocumentHeader(workflowDocument.getDocumentId(), principalId, payBeginDate.toDate(), payEndDate.toDate(), status);

        documentHeader.setDocumentId(workflowDocument.getDocumentId());
        documentHeader.setDocumentStatus("I");

        TkServiceLocator.getTimesheetDocumentHeaderService().saveOrUpdate(documentHeader);
        timesheetDocument = new TimesheetDocument(documentHeader);
        timesheetDocument.setCalendarEntry(calendarEntry);
        loadTimesheetDocumentData(timesheetDocument, principalId, calendarEntry);
        TkServiceLocator.getTkSearchableAttributeService().updateSearchableAttribute(timesheetDocument, payEndDate.toLocalDate());

        if (LmServiceLocator.getLeaveApprovalService().isActiveAssignmentFoundOnJobFlsaStatus(principalId, HrConstants.FLSA_STATUS_NON_EXEMPT, true)) {
        	deleteNonApprovedLeaveBlocks(principalId, calendarEntry.getBeginPeriodFullDateTime().toLocalDate(), calendarEntry.getEndPeriodFullDateTime().toLocalDate());
        }

        return timesheetDocument;
    }

    private void deleteNonApprovedLeaveBlocks(String principalId, LocalDate beginDate, LocalDate endDate) {
    	String batchUserPrincipalId = BatchJobUtil.getBatchUserPrincipalId();

        if (batchUserPrincipalId != null) {
	    	List<LeaveBlock> leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, beginDate, endDate);

	    	for (LeaveBlock leaveBlock : leaveBlocks) {
	    		if (!StringUtils.equals(leaveBlock.getRequestStatus(), HrConstants.REQUEST_STATUS.APPROVED)) {
                    LmServiceLocator.getLeaveRequestDocumentService().suCancelLeave(
                            leaveBlock.getLeaveRequestDocumentId(), batchUserPrincipalId);
                    LmServiceLocator.getLeaveBlockService().deleteLeaveBlock(leaveBlock.getLmLeaveBlockId(), batchUserPrincipalId);
	    		}
	    	}
        } else {
        	String principalName = BatchJobUtil.getBatchUserPrincipalName();
        	LOG.error("Could not delete leave request blocks due to missing batch user " + principalName);
        }
    }


    public List<TimeBlock> getPrevDocumentTimeBlocks(String principalId, DateTime payBeginDate) {
        TimesheetDocumentHeader prevTdh = TkServiceLocator.getTimesheetDocumentHeaderService().getPreviousDocumentHeader(principalId, payBeginDate);
        if (prevTdh == null) {
            return new ArrayList<TimeBlock>();
        }
        return TkServiceLocator.getTimeBlockService().getTimeBlocks(prevTdh.getDocumentId());
    }

    @Override
    public TimesheetDocument getTimesheetDocument(String documentId) {
        TimesheetDocument timesheetDocument = null;
        TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(documentId);

        if (tdh != null) {
            timesheetDocument = new TimesheetDocument(tdh);
            CalendarEntry pce =  HrServiceLocator.getCalendarEntryService().getCalendarDatesByPayEndDate(tdh.getPrincipalId(), tdh.getEndDateTime(), HrConstants.PAY_CALENDAR_TYPE);
            loadTimesheetDocumentData(timesheetDocument, tdh.getPrincipalId(), pce);

            timesheetDocument.setCalendarEntry(pce);
        }

        return timesheetDocument;
    }

    protected void loadTimesheetDocumentData(TimesheetDocument tdoc, String principalId, CalendarEntry payCalEntry) {
    	//tdoc.setAssignments(HrServiceLocator.getAssignmentService().getAssignmentsByCalEntryForTimeCalendar(principalId, payCalEntry));
    	tdoc.setAssignments(HrServiceLocator.getAssignmentService().getAssignmentHistoryForCalendarEntry(principalId, payCalEntry));
        if (payCalEntry != null) {
    		tdoc.setJobs(HrServiceLocator.getJobService().getJobs(principalId, payCalEntry.getEndPeriodFullDateTime().toLocalDate()));
    	}
    	tdoc.setTimeBlocks(TkServiceLocator.getTimeBlockService().getTimeBlocks(tdoc.getDocumentHeader().getDocumentId()));
    }

    public boolean isSynchronousUser() {
        List<Assignment> assignments = HrServiceLocator.getAssignmentService().getAssignments(HrContext.getTargetPrincipalId(), LocalDate.now());
        boolean isSynchronousUser = true;
        for (Assignment assignment : assignments) {
        	if(assignment.getJob() != null) {
	        	TimeCollectionRule tcr = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule(assignment.getDept(), assignment.getWorkArea(), assignment.getJob().getHrPayType(), LocalDate.now());
	            isSynchronousUser &= tcr == null || tcr.isClockUserFl();
        	}
        }
        return isSynchronousUser;
    }

    //this is an admin function used for testing
    public void deleteTimesheet(String documentId) {
        TkServiceLocator.getTimeBlockService().deleteTimeBlocksAssociatedWithDocumentId(documentId);
        TkServiceLocator.getTimesheetDocumentHeaderService().deleteTimesheetHeader(documentId);
    }

    protected void resetWorkedHours(TimeBlock.Builder previousTimeBlock, TimeBlock.Builder timeBlock, LocalDate asOfDate) {
    	EarnCodeContract earnCodeObj = HrServiceLocator.getEarnCodeService().getEarnCode(timeBlock.getEarnCode(), asOfDate);
        if (timeBlock.getBeginTime() != null && timeBlock.getEndTime() != null && StringUtils.equals(timeBlock.getEarnCodeType(), HrConstants.EARN_CODE_TIME)) {
            BigDecimal hours = TKUtils.getHoursBetween(timeBlock.getBeginDateTime().getMillis(), timeBlock.getEndDateTime().getMillis());

            //If earn code has an inflate min hours check if it is greater than zero
            //and compare if the hours specified is less than min hours awarded for this
            //earn code
            if (earnCodeObj.getInflateMinHours() != null) {
            	if ((earnCodeObj.getInflateMinHours().compareTo(BigDecimal.ZERO) != 0) &&
            			earnCodeObj.getInflateMinHours().compareTo(hours) > 0) {
                    //if previous timeblock has no gap then assume its one block if the same earn code and divide inflated hours accordingly
                    if(previousTimeBlock != null && StringUtils.equals(earnCodeObj.getEarnCode(),previousTimeBlock.getEarnCode()) &&
                            (timeBlock.getBeginDateTime().getMillis() - previousTimeBlock.getEndDateTime().getMillis() == 0L)) {
                        BigDecimal prevTimeBlockHours = TKUtils.getHoursBetween(previousTimeBlock.getBeginDateTime().getMillis(), previousTimeBlock.getEndDateTime().getMillis());
                        previousTimeBlock.setHours(prevTimeBlockHours);
                    }
                }
            }

            timeBlock.setHours(hours);
        }
    }

    @Override
    public List<TimeBlock> resetTimeBlock(List<TimeBlock> timeBlocks, LocalDate asOfDate) {
        TimeBlock.Builder previous = null;
        List<TimeBlock.Builder> builders = ModelObjectUtils.transform(timeBlocks, toTimeBlockBuilder);
        for (TimeBlock.Builder tb : builders) {
            resetWorkedHours(previous, tb, asOfDate);
            previous = tb;
        }
        return TkServiceLocator.getTimeBlockService().resetTimeHourDetail(timeBlocks);
    }

	@Override
	public boolean isReadyToApprove(TimesheetDocument document) {
        if (document == null) {
            return false;
        }
        List<LeaveBlock> leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocksWithType(document.getPrincipalId(),
        		document.getCalendarEntry().getBeginPeriodFullDateTime().toLocalDate(), document.getCalendarEntry().getEndPeriodFullDateTime().toLocalDate(), LMConstants.LEAVE_BLOCK_TYPE.BALANCE_TRANSFER);
        leaveBlocks.addAll(LmServiceLocator.getLeaveBlockService().getLeaveBlocksWithType(document.getPrincipalId(),
        		document.getCalendarEntry().getBeginPeriodFullDateTime().toLocalDate(), document.getCalendarEntry().getEndPeriodFullDateTime().toLocalDate(), LMConstants.LEAVE_BLOCK_TYPE.LEAVE_PAYOUT));
        for(LeaveBlock lb : leaveBlocks) {
        	if(!StringUtils.equals(lb.getRequestStatus(),HrConstants.REQUEST_STATUS.APPROVED) &&
        			!StringUtils.equals(lb.getRequestStatus(), HrConstants.REQUEST_STATUS.DISAPPROVED))
        		return false;
        }
        return true;
	}

    @Override
    public List<EarnCode> getEarnCodesForTime(Assignment a, LocalDate asOfDate, boolean includeRegularEarnCode) {
        //getEarnCodesForTime and getEarnCodesForLeave have some overlapping logic, but they were separated so that they could follow their own distinct logic, so consolidation of logic is not desirable.

        if (a == null) {
        	LOG.error("No assignment parameter.");
        	return null;
//        	throw new RuntimeException("No assignment parameter.");
        }
        JobContract job = a.getJob();
        if (job == null || job.getPayTypeObj() == null) {
        	LOG.error("Null job or null job pay type on assignment.");
        	return null;
//        	throw new RuntimeException("Null job or null job pay type on assignment.");
        }

        List<EarnCode> earnCodes = new LinkedList<EarnCode>();
        String earnTypeCode = EarnCodeType.TIME.getCode();

        TimeCollectionRule tcr = null;
        if(a.getJob() != null)
        	tcr = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule(a.getDept(), a.getWorkArea(), a.getJob().getHrPayType(), asOfDate);

        boolean isClockUser = tcr == null || tcr.isClockUserFl();
        boolean isUsersTimesheet = StringUtils.equals(HrContext.getPrincipalId(),a.getPrincipalId());

        // Reg earn codes will typically not be defined in the earn code security table
        EarnCode regularEarnCode = HrServiceLocator.getEarnCodeService().getEarnCode(job.getPayTypeObj().getRegEarnCode(), asOfDate);
        if (regularEarnCode == null) {
        	LOG.error("No regular earn code defined for job pay type.");
        	return null;
//            throw new RuntimeException("No regular earn code defined for job pay type.");
        } else {
            //  if you are a clock user and this is your timesheet and you are processing the reg earn code, do not add this earn code. Use the clock in/out mechanism.
        	if (!isClockUser || !isUsersTimesheet || includeRegularEarnCode) {
                earnCodes.add(regularEarnCode);
            }
        }

        List<String> listAccrualCategories = new LinkedList<String>();
        String accrualCategory;

        //  first make a list of the accrual categories available to the user's Leave Plan (yes, leave plan), for later comparison.
        PrincipalHRAttributesContract principalHRAttributes = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(job.getPrincipalId(), asOfDate);
        boolean fmlaEligible = principalHRAttributes.isFmlaEligible();
        boolean workersCompEligible = principalHRAttributes.isWorkersCompEligible();

        String leavePlan = principalHRAttributes.getLeavePlan();
        if (leavePlan != null) {
            for (AccrualCategory accrualCategories : HrServiceLocator.getAccrualCategoryService().getActiveAccrualCategoriesForLeavePlan(leavePlan, asOfDate)) {
                accrualCategory = accrualCategories.getAccrualCategory();
                if(accrualCategory != null) {
                    listAccrualCategories.add(accrualCategory);
                }
            }
        }

        //  get all earn codes by user security, then we'll filter on accrual category first as we process them.
        List<? extends EarnCodeSecurityContract> decs = HrServiceLocator.getEarnCodeSecurityService().getEarnCodeSecurities(job.getDept(), job.getHrSalGroup(), job.getLocation(), asOfDate);
        for (EarnCodeSecurityContract dec : decs) {

            boolean addEarnCode = HrServiceLocator.getEarnCodeService().addEarnCodeBasedOnEmployeeApproverSettings(dec, a, asOfDate);
            if (addEarnCode) {

                //  allow types Time AND Both
                if (earnTypeCode.equals(dec.getEarnCodeType()) || EarnCodeType.BOTH.getCode().equals(dec.getEarnCodeType())) {
                    EarnCode ec = HrServiceLocator.getEarnCodeService().getEarnCode(dec.getEarnCode(), asOfDate);

                    //  make sure we got something back from the earn code dao
                    if (ec != null) {
                    	// make sure the earn code's leave plan matches the user's leave plan
                    	// if user has a leave plan, we show earn codes with a matching leave plan and all earn codes without a leave plan
                    	// if user doe not have a leave plan, we show earn codes that don't have a leave plan
                    	if( (StringUtils.isNotBlank(leavePlan) && StringUtils.isBlank(ec.getLeavePlan()))
                    			|| (StringUtils.isNotBlank(leavePlan) && StringUtils.isNotBlank(ec.getLeavePlan()) && StringUtils.equals(leavePlan, ec.getLeavePlan()))
    							|| (StringUtils.isBlank(leavePlan) && StringUtils.isBlank(ec.getLeavePlan()))) {
	                        //  if the user's fmla flag is Yes, that means we are not restricting codes based on this flag, so any code is shown.
	                        //    if the fmla flag on a code is yes they can see it.    (allow)
	                        //    if the fmla flag on a code is no they should see it.  (allow)
	                        //  if the user's fmla flag is No,
	                        //    they can see any codes which are fmla=no.             (allow)
	                        //    they can not see codes with fmla=yes.                 (exclude earn code)
	                        //  the fmla earn codes=no do not require any exclusion
	                        //  the only action required is if the fmla user flag=no: exclude those codes with fmla=yes.
	                        if ( (fmlaEligible || ec.getFmla().equals("N")) ) {
	                        	if (ec.getAccrualCategory() == null
	                        		|| (listAccrualCategories.contains(ec.getAccrualCategory())
	                         	 		&& HrConstants.ACCRUAL_BALANCE_ACTION.USAGE.equals(ec.getAccrualBalanceAction()))) {
	                            // go on, we are allowing these three combinations: YY, YN, NN

	                                //  apply the same logic as FMLA to the Worker Compensation flags.
	                                if ( (workersCompEligible || ec.getWorkmansComp().equals("N")) ) {
	                                    // go on, we are allowing these three combinations: YY, YN, NN.

	                                    //  determine if the holiday earn code should be displayed.
	                                    if ( showEarnCodeIfHoliday(ec, dec) ) {
	                                        //  non-Holiday earn code will go on, Holiday earn code must meet some requirements in the method.
	                                    	// KPME-2556
	                                        //if ( !StringUtils.equals(regularEarnCode.toString(), dec.getEarnCode()) ) {
	                                    	if (!StringUtils.equals(regularEarnCode.getEarnCode(), dec.getEarnCode()) ) {
	                                            //  add earn code if it is not the reg earn code.
	                                            earnCodes.add(ec);
	                                        }
	                                    }
	                                }
	                            }
	                        }
                    	}
                    }
                }
            }
        }

        return earnCodes;
    }

    public List<EarnCode> getEarnCodesForTime(Assignment a, LocalDate asOfDate) {
    	return getEarnCodesForTime(a, asOfDate, false);
	}

    private boolean showEarnCodeIfHoliday(EarnCode earnCode, EarnCodeSecurityContract security) {
        if (earnCode.getEarnCode().equals(HrConstants.HOLIDAY_EARN_CODE)) {
            if (security.isApprover() || HrContext.isSystemAdmin()) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public HRPermissionService getHRPermissionService() {
        if (hrPermissionService == null) {
            hrPermissionService = HrServiceLocator.getHRPermissionService();
        }
        return hrPermissionService;
    }

    private void clearTimesheetTimeblockPermissions(TimesheetDocument doc) {
        for (TimeBlock tb : doc.getTimeBlocks()) {
            getHRPermissionService().updateTimeBlockPermissions(CalendarBlockPermissions.newInstance(tb.getTkTimeBlockId()));
        }
    }




    public boolean isTimesheetValid(TimesheetDocument td) {
        boolean isTimeSheetValid = true;

        if (WorkflowTagSupport.isTimesheetApprovalButtonsDisplaying(td.getDocumentId())) {
            if ((validateHours(td) != null && !validateHours(td).isEmpty()) || (validateTimeBlock(td) != null && !validateTimeBlock(td).isEmpty())) {
                    isTimeSheetValid = false ;
                }
        }
        return isTimeSheetValid;
    }

    public List<String> validateTimeBlock(TimesheetDocument td) {
        List<String> errors = new ArrayList<String>();
        if (td != null) {

            Map<String, String> earnCodeTypeMap = new HashMap<String, String>();


            for (TimeBlock timeBlock : td.getTimeBlocks()) {
                String earnCode = timeBlock.getEarnCode();
                if (earnCodeTypeMap.containsKey(earnCode)) {
                    continue;
                } else {
                    EarnCodeContract earnCodeObj = HrServiceLocator.getEarnCodeService().getEarnCode(earnCode, td.getAsOfDate());
                    if (earnCodeObj != null) {
                        earnCodeTypeMap.put(earnCodeObj.getEarnCode(),earnCodeObj.getEarnCodeType());
                    }
                }
            }

            for (TimeBlock timeBlock : td.getTimeBlocks()) {
                DateTime beginDate = timeBlock.getBeginDateTime();
                String earnCodeType = earnCodeTypeMap.get(timeBlock.getEarnCode());
                if (earnCodeType != null && HrConstants.EARN_CODE_TIME.equals(earnCodeType)) {
                    String timeBlockDesc = "TimeBlock (" + timeBlock.getTkTimeBlockId() + ") on " + DateTimeFormat.forPattern("EEE MMM d").print(timeBlock.getBeginDateTime()) + " from " + timeBlock.getBeginTimeDisplayTimeOnlyString() + " - " + timeBlock.getEndTimeDisplayTimeOnlyString();
                    for (TimeBlock compareTimeBlock : td.getTimeBlocks()) {
                    	if(compareTimeBlock.getTkTimeBlockId()!=null && timeBlock.getTkTimeBlockId()!=null){
                    		if (compareTimeBlock.getTkTimeBlockId().equals(timeBlock.getTkTimeBlockId())) {
                    			continue;
                    		}
                    	}
                        String compareEarnCodeType = earnCodeTypeMap.get(compareTimeBlock.getEarnCode());
                        if (compareEarnCodeType != null && HrConstants.EARN_CODE_TIME.equals(compareEarnCodeType)) {
                            String compareTimeBlockDesc = "TimeBlock (" + compareTimeBlock.getTkTimeBlockId() + ") on " + DateTimeFormat.forPattern("EEE MMM d").print(compareTimeBlock.getBeginDateTime()) + " from " + compareTimeBlock.getBeginTimeDisplayTimeOnlyString() + " - " + compareTimeBlock.getEndTimeDisplayTimeOnlyString();
                            Interval compareTimeBlockInterval = new Interval(compareTimeBlock.getBeginDateTime(), compareTimeBlock.getEndDateTime());
                            if (compareTimeBlockInterval.contains(beginDate.getMillis())) {
                                errors.add("Error : [" + timeBlockDesc + " overlaps with " + compareTimeBlockDesc + ".]" );
                            }
                        }
                    }

                    List<String> assignmentKeyList = new ArrayList<String>();


                    for (Assignment assignment : td.getAssignmentMap().get(timeBlock.getBeginDateTime().toLocalDate())) {
                        assignmentKeyList.add(assignment.getAssignmentKey());
                    }
                    if (!assignmentKeyList.contains(timeBlock.getAssignmentKey())) {
                        errors.add("Error: [" + timeBlockDesc + " contains an invalid assignment.]");
                    }
                }
            }
        }
        return errors;
    }

    public List<String> validateHours(TimesheetDocument timesheetDocument) {
        List<String> errors = new ArrayList<String>();

        DateTimeZone userTimeZone = DateTimeZone.forID(HrServiceLocator.getTimezoneService().getUserTimezone(timesheetDocument.getPrincipalId()));

        if (userTimeZone == null) {
            userTimeZone = HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback();
        }
        if (timesheetDocument != null && WorkflowTagSupport.isTimesheetApprovalButtonsDisplaying(timesheetDocument.getDocumentId())) {

            String assignmentDesc = "";
            for (Assignment assignment : timesheetDocument.getAllAssignments()) {
                //get standard hours for job on assignment
                BigDecimal standardHours = assignment.getJob().getStandardHours();
                LocalDate jobStartDate = assignment.getJob().getEffectiveLocalDate();
                //if standard hours is 0 no validation is needed.
                if (standardHours.compareTo(new BigDecimal(0)) == 0) {
                    continue;
                }

                //create a aggregate of timeblocks for current assignment in the loop
                List<TimeBlock> assignmentTimeBlocks = new ArrayList<TimeBlock>();
                for (TimeBlock timeBlock : timesheetDocument.getTimeBlocks()) {
                    if (timeBlock.getAssignmentKey().equals(assignment.getAssignmentKey())) {
                        assignmentTimeBlocks.add(timeBlock);
                    }
                }

                TkTimeBlockAggregate tkTimeBlockAggregate = new TkTimeBlockAggregate(assignmentTimeBlocks, timesheetDocument.getCalendarEntry(), HrServiceLocator.getCalendarService().getCalendar(timesheetDocument.getCalendarEntry().getHrCalendarId()), true);

                //create an aggregate of leaveblocks for current assignment in the loop
                List<String> assigmentKeyList = new ArrayList<String>();
                assigmentKeyList.add(assignment.getAssignmentKey());

                List<LeaveBlock> leaveBlocks =  LmServiceLocator.getLeaveBlockService().getLeaveBlocksForTimeCalendar(timesheetDocument.getPrincipalId(),
                        timesheetDocument.getCalendarEntry().getBeginPeriodFullDateTime().toLocalDate(), timesheetDocument.getCalendarEntry().getEndPeriodFullDateTime().toLocalDate(), assigmentKeyList);
                LeaveBlockAggregate leaveBlockAggregate = new LeaveBlockAggregate(leaveBlocks, timesheetDocument.getCalendarEntry());

                //combine timeBlocks and leave blocks for selected assignment
                tkTimeBlockAggregate = TkTimeBlockAggregate.combineTimeAndLeaveAggregates(tkTimeBlockAggregate, leaveBlockAggregate);

                Map<String, BigDecimal> flsaWeekTotal =  getAssignmentHoursToFlsaWeekMap(tkTimeBlockAggregate, timesheetDocument.getPrincipalId(), assignment.getAssignmentKey(), jobStartDate, userTimeZone);

                for (Map.Entry<String, BigDecimal> entry : flsaWeekTotal.entrySet()) {
                    if (standardHours.compareTo(entry.getValue()) > 0) {
                        errors.add("Error: [" + assignment.getAssignmentDescription() + " expected " + standardHours + " hours for " + entry.getKey() + " only " + entry.getValue() + " hours were entered.]");
                    }
                }
            }

        }
        return errors;
    }

    private Map<String, BigDecimal> getAssignmentHoursToFlsaWeekMap(TkTimeBlockAggregate tkTimeBlockAggregate, String principalId, String assignmentKey, LocalDate jobStartDate, DateTimeZone userTimeZone) {

        Map<String, BigDecimal> hoursToFlsaWeekMap = new LinkedHashMap<String, BigDecimal>();
        List<List<FlsaWeek>> flsaWeeks = tkTimeBlockAggregate.getFlsaWeeks(userTimeZone, principalId);


        int weekCount = 1;
        for (List<FlsaWeek> flsaWeekParts : flsaWeeks) {
            boolean printWeek = true;
            BigDecimal weekTotal = new BigDecimal(0.00);
            for (FlsaWeek flsaWeekPart : flsaWeekParts) {

                //if flsa week doesn't end during this pay period do not validate.
                if (flsaWeekPart.equals(flsaWeekParts.get(flsaWeekParts.size() - 1))) {
                    Integer lastFlsaDayOfWeek = flsaWeekPart.getFlsaDays().get(flsaWeekPart.getFlsaDays().size() - 1).getFlsaDate().getDayOfWeek();

                    Integer flsaWeekEndDayOfWeek = TkConstants.FLSA_WEEK_END_DAY.get(tkTimeBlockAggregate.getPayCalendar().getFlsaBeginDay());

                    if (lastFlsaDayOfWeek.compareTo(flsaWeekEndDayOfWeek) != 0) {
                        printWeek = false;
                        weekCount++;
                        continue;
                    }
                }

                //if flsa week starts before effective date of the job on the assignment do not validate.
                if (flsaWeekPart.getFlsaDays().get(0).getFlsaDate().toLocalDate().isBefore(jobStartDate) ) {
                    printWeek = false;
                    weekCount++;
                    continue;
                }

                for (FlsaDay flsaDay : flsaWeekPart.getFlsaDays()) {

                    for (TimeBlock timeBlock : flsaDay.getAppliedTimeBlocks()) {
                        if (assignmentKey != null) {
                            if (timeBlock.getAssignmentKey().compareTo(assignmentKey) == 0) {
                                weekTotal = weekTotal.add(timeBlock.getHours(), HrConstants.MATH_CONTEXT);
                            } else {
                                weekTotal = weekTotal.add(new BigDecimal("0"), HrConstants.MATH_CONTEXT);
                            }
                        } else {
                            weekTotal = weekTotal.add(timeBlock.getHours(), HrConstants.MATH_CONTEXT);
                        }
                    }
                }
            }

            if (printWeek) {
                hoursToFlsaWeekMap.put("Week " + weekCount++, weekTotal);
            }
        }

        return hoursToFlsaWeekMap;
    }
}
