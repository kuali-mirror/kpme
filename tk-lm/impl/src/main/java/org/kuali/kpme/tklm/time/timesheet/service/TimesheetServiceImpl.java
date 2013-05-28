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
package org.kuali.kpme.tklm.time.timesheet.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.earncode.EarnCode;
import org.kuali.kpme.core.earncode.security.EarnCodeSecurity;
import org.kuali.kpme.core.earncode.security.EarnCodeType;
import org.kuali.kpme.core.job.Job;
import org.kuali.kpme.core.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.common.LMConstants;
import org.kuali.kpme.tklm.common.TkConstants;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.timeoff.SystemScheduledTimeOff;
import org.kuali.kpme.tklm.time.rules.timecollection.TimeCollectionRule;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.WorkflowDocumentFactory;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kew.api.note.Note;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

public class TimesheetServiceImpl implements TimesheetService {

    private static final Logger LOG = Logger.getLogger(TimesheetServiceImpl.class);

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
        }
    }

    @Override
    public TimesheetDocument openTimesheetDocument(String principalId, CalendarEntry calendarDates) throws WorkflowException {
        TimesheetDocument timesheetDocument = null;

        DateTime begin = calendarDates.getBeginPeriodFullDateTime();
        DateTime end = calendarDates.getEndPeriodFullDateTime();

        TimesheetDocumentHeader header = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(principalId, begin, end);

        if (header == null) {
            List<Assignment> activeAssignments = HrServiceLocator.getAssignmentService().getAssignmentsByCalEntryForTimeCalendar(principalId, calendarDates);
            //HrServiceLocator.getAssignmentService().getAssignments(principalId, TKUtils.getTimelessDate(payCalendarDates.getEndPeriodDate()));
            if (activeAssignments.size() == 0) {
                LOG.warn("No active assignments for " + principalId + " for " + calendarDates.getEndPeriodDate());
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
            timesheetDocument.setCalendarEntry(calendarDates);
        }

        timesheetDocument.setTimeSummary(TkServiceLocator.getTimeSummaryService().getTimeSummary(timesheetDocument));
        return timesheetDocument;
    }

    public void loadHolidaysOnTimesheet(TimesheetDocument timesheetDocument, String principalId, LocalDate beginDate, LocalDate endDate) {
        PrincipalHRAttributes principalCalendar = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, beginDate);
        if (principalCalendar != null && StringUtils.isNotEmpty(principalCalendar.getLeavePlan())) {
        	List<SystemScheduledTimeOff> sstoList = LmServiceLocator.getSysSchTimeOffService()
        		.getSystemScheduledTimeOffForPayPeriod(principalCalendar.getLeavePlan(), beginDate, endDate);
        	Assignment sstoAssign = getAssignmentToApplyScheduledTimeOff(timesheetDocument.getPrincipalId(), timesheetDocument.getAssignments(), endDate);
        	if (sstoAssign != null) {
        		for(SystemScheduledTimeOff ssto : sstoList) {
                  BigDecimal sstoCalcHours = LmServiceLocator.getSysSchTimeOffService().calculateSysSchTimeOffHours(sstoAssign.getJob(), ssto.getAmountofTime());
                  TimeBlock timeBlock = TkServiceLocator.getTimeBlockService().createTimeBlock(timesheetDocument, ssto.getScheduledTimeOffLocalDate().toDateTimeAtStartOfDay(),
                          ssto.getScheduledTimeOffLocalDate().toDateTimeAtStartOfDay(), sstoAssign, HrConstants.HOLIDAY_EARN_CODE, sstoCalcHours, BigDecimal.ZERO, false, false, HrContext.getPrincipalId());
                  timesheetDocument.getTimeBlocks().add(timeBlock);
              }
	            //If system scheduled time off are loaded will need to save them to the database
		        if (CollectionUtils.isNotEmpty(sstoList)) {
		           TkServiceLocator.getTimeBlockService().saveTimeBlocks(new LinkedList<TimeBlock>(), timesheetDocument.getTimeBlocks(), HrContext.getPrincipalId());
		        }
        	}
        }
    }

    private Assignment getAssignmentToApplyScheduledTimeOff(String principalId, List<Assignment> assignments, LocalDate endDate) {
		Job primaryJob = HrServiceLocator.getJobService().getPrimaryJob(principalId, endDate);
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

        documentHeader.setDocumentId(workflowDocument.getDocumentId().toString());
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
    	String batchUserPrincipalId = getBatchUserPrincipalId();
        
        if (batchUserPrincipalId != null) {
	    	List<LeaveBlock> leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, beginDate, endDate);
	
	    	for (LeaveBlock leaveBlock : leaveBlocks) {
	    		if (!StringUtils.equals(leaveBlock.getRequestStatus(), HrConstants.REQUEST_STATUS.APPROVED)) {
	    			LmServiceLocator.getLeaveBlockService().deleteLeaveBlock(leaveBlock.getLmLeaveBlockId(), batchUserPrincipalId);
	    		}
	    	}
        } else {
        	String principalName = ConfigContext.getCurrentContextConfig().getProperty(TkConstants.BATCH_USER_PRINCIPAL_NAME);
        	LOG.error("Could not delete leave request blocks due to missing batch user " + principalName);
        }
    }
    
    private String getBatchUserPrincipalId() {
    	String principalName = ConfigContext.getCurrentContextConfig().getProperty(TkConstants.BATCH_USER_PRINCIPAL_NAME);
        Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(principalName);
        return principal == null ? null : principal.getPrincipalId();
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
            CalendarEntry pce = HrServiceLocator.getCalendarService().getCalendarDatesByPayEndDate(tdh.getPrincipalId(), tdh.getEndDateTime(), HrConstants.PAY_CALENDAR_TYPE);
            loadTimesheetDocumentData(timesheetDocument, tdh.getPrincipalId(), pce);

            timesheetDocument.setCalendarEntry(pce);
        }
        
        return timesheetDocument;
    }

    protected void loadTimesheetDocumentData(TimesheetDocument tdoc, String principalId, CalendarEntry payCalEntry) {
        List<Assignment> assignments = HrServiceLocator.getAssignmentService().getAssignmentsByCalEntryForTimeCalendar(principalId, payCalEntry);
        List<Job> jobs = HrServiceLocator.getJobService().getJobs(principalId, payCalEntry.getEndPeriodFullDateTime().toLocalDate());
        List<TimeBlock> timeBlocks = TkServiceLocator.getTimeBlockService().getTimeBlocks(tdoc.getDocumentHeader().getDocumentId());

        tdoc.setAssignments(assignments);
        tdoc.setJobs(jobs);
        tdoc.setTimeBlocks(timeBlocks);
    }

    public boolean isSynchronousUser() {
        List<Assignment> assignments = HrServiceLocator.getAssignmentService().getAssignments(HrContext.getTargetPrincipalId(), LocalDate.now());
        boolean isSynchronousUser = true;
        for (Assignment assignment : assignments) {
        	TimeCollectionRule tcr = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule(assignment.getDept(), assignment.getWorkArea(), LocalDate.now());
            isSynchronousUser &= tcr == null || tcr.isClockUserFl();
        }
        return isSynchronousUser;
    }

    //this is an admin function used for testing
    public void deleteTimesheet(String documentId) {
        TkServiceLocator.getTimeBlockService().deleteTimeBlocksAssociatedWithDocumentId(documentId);
        TkServiceLocator.getTimesheetDocumentHeaderService().deleteTimesheetHeader(documentId);
    }

    public TimeBlock resetWorkedHours(TimeBlock timeBlock) {
        if (timeBlock.getBeginTime() != null && timeBlock.getEndTime() != null && StringUtils.equals(timeBlock.getEarnCodeType(), HrConstants.EARN_CODE_TIME)) {
            BigDecimal hours = TKUtils.getHoursBetween(timeBlock.getBeginTime().getTime(), timeBlock.getEndTime().getTime());
            timeBlock.setHours(hours);
        }
        return timeBlock;
    }

    @Override
    public void resetTimeBlock(List<TimeBlock> timeBlocks) {
        for (TimeBlock tb : timeBlocks) {
            resetWorkedHours(tb);
        }
        TkServiceLocator.getTimeBlockService().resetTimeHourDetail(timeBlocks);
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
/*        List<BalanceTransfer> balanceTransfers = LmServiceLocator.getBalanceTransferService().getBalanceTransfers(document.getPrincipalId(),
                document.getCalendarEntry().getBeginPeriodDate(),
                document.getCalendarEntry().getEndPeriodDate());
        if (!CollectionUtils.isEmpty(balanceTransfers))   {
	        for(BalanceTransfer balanceTransfer : balanceTransfers) {
	        	if(StringUtils.equals(HrConstants.DOCUMENT_STATUS.get(balanceTransfer.getStatus()), HrConstants.ROUTE_STATUS.ENROUTE))
	        		return false;
	            if (!StringUtils.equals(HrConstants.REQUEST_STATUS.APPROVED, balanceTransfer.getStatus())
	                    && !StringUtils.equals(HrConstants.REQUEST_STATUS.DISAPPROVED, balanceTransfer.getStatus())) {
	                return false;
	            }
	        }
        }
        List<LeavePayout> leavePayouts = LmServiceLocator.getLeavePayoutService().getLeavePayouts(document.getPrincipalId(),
        		document.getCalendarEntry().getBeginPeriodDate(),
        		document.getCalendarEntry().getEndPeriodDate());
        if (!CollectionUtils.isEmpty(leavePayouts)) {
        	for(LeavePayout payout : leavePayouts) {
	        	if(StringUtils.equals(HrConstants.DOCUMENT_STATUS.get(payout.getStatus()), HrConstants.ROUTE_STATUS.ENROUTE))
	        		return false;
	            if (!StringUtils.equals(HrConstants.REQUEST_STATUS.APPROVED, payout.getStatus())
	                    && !StringUtils.equals(HrConstants.REQUEST_STATUS.DISAPPROVED, payout.getStatus())) {
	                return false;
	            }
        	}
        }
        return true;*/
	}
	
    public List<EarnCode> getEarnCodesForTime(Assignment a, LocalDate asOfDate) {
        //getEarnCodesForTime and getEarnCodesForLeave have some overlapping logic, but they were separated so that they could follow their own distinct logic, so consolidation of logic is not desirable.

        if (a == null) throw new RuntimeException("No assignment parameter.");
        Job job = a.getJob();
        if (job == null || job.getPayTypeObj() == null) throw new RuntimeException("Null job or null job pay type on assignment.");

        List<EarnCode> earnCodes = new LinkedList<EarnCode>();
        String earnTypeCode = EarnCodeType.TIME.getCode();

        TimeCollectionRule tcr = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule(a.getDept(), a.getWorkArea(), asOfDate);
        
        boolean isClockUser = tcr == null || tcr.isClockUserFl();
        boolean isUsersTimesheet = StringUtils.equals(HrContext.getTargetPrincipalId(),a.getPrincipalId());

        // Reg earn codes will typically not be defined in the earn code security table
        EarnCode regularEarnCode = HrServiceLocator.getEarnCodeService().getEarnCode(job.getPayTypeObj().getRegEarnCode(), asOfDate);
        if (regularEarnCode == null) {
            throw new RuntimeException("No regular earn code defined for job pay type.");
        } else {
            //  if you are a clock user and this is your timesheet and you are processing the reg earn code, do not add this earn code. Use the clock in/out mechanism.
            if (isClockUser && isUsersTimesheet) {
                // do not add reg earn code. use clock.
            } else {
                earnCodes.add(regularEarnCode);
            }
        }

        List<String> listAccrualCategories = new LinkedList<String>();
        String accrualCategory;

        //  first make a list of the accrual categories available to the user's Leave Plan (yes, leave plan), for later comparison.
        PrincipalHRAttributes principalHRAttributes = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(job.getPrincipalId(), asOfDate);
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
        List<EarnCodeSecurity> decs = HrServiceLocator.getEarnCodeSecurityService().getEarnCodeSecurities(job.getDept(), job.getHrSalGroup(), job.getLocation(), asOfDate);
        for (EarnCodeSecurity dec : decs) {

            boolean addEarnCode = HrServiceLocator.getEarnCodeService().addEarnCodeBasedOnEmployeeApproverSettings(dec, a, asOfDate);
            if (addEarnCode) {

                //  allow types Time AND Both
                if (earnTypeCode.equals(dec.getEarnCodeType()) || EarnCodeType.BOTH.getCode().equals(dec.getEarnCodeType())) {
                    EarnCode ec = HrServiceLocator.getEarnCodeService().getEarnCode(dec.getEarnCode(), asOfDate);

                    //  make sure we got something back from the earn code dao
                    if (ec != null) {

                        //  now that we have a list of security earn codes, compare their accrual categories to the user's accrual category list.
                        //  we also allow earn codes that have no accrual category assigned.
                        if (listAccrualCategories.contains(ec.getAccrualCategory()) || ec.getAccrualCategory() == null) {

                            //  if the user's fmla flag is Yes, that means we are not restricting codes based on this flag, so any code is shown.
                            //    if the fmla flag on a code is yes they can see it.    (allow)
                            //    if the fmla flag on a code is no they should see it.  (allow)
                            //  if the user's fmla flag is No,
                            //    they can see any codes which are fmla=no.             (allow)
                            //    they can not see codes with fmla=yes.                 (exclude earn code)
                            //  the fmla earn codes=no do not require any exclusion
                            //  the only action required is if the fmla user flag=no: exclude those codes with fmla=yes.

                            if ( (fmlaEligible || ec.getFmla().equals("N")) ) {
                                //only want usage accrual balance actions
                                if (StringUtils.equals(ec.getAccrualBalanceAction(), HrConstants.ACCRUAL_BALANCE_ACTION.USAGE)) {
                                // go on, we are allowing these three combinations: YY, YN, NN

                                    //  apply the same logic as FMLA to the Worker Compensation flags.
                                    if ( (workersCompEligible || ec.getWorkmansComp().equals("N")) ) {
                                        // go on, we are allowing these three combinations: YY, YN, NN.

                                        //  determine if the holiday earn code should be displayed.
                                        if ( showEarnCodeIfHoliday(ec, dec) ) {
                                            //  non-Holiday earn code will go on, Holiday earn code must meet some requirements in the method.
                                            if ( !StringUtils.equals(regularEarnCode.toString(), dec.getEarnCode()) ) {
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

    private boolean showEarnCodeIfHoliday(EarnCode earnCode, EarnCodeSecurity security) {
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
    
}
