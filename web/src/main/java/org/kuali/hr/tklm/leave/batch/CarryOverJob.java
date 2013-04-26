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
package org.kuali.hr.tklm.leave.batch;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.kuali.hr.core.accrualcategory.AccrualCategory;
import org.kuali.hr.core.accrualcategory.service.AccrualCategoryService;
import org.kuali.hr.core.assignment.Assignment;
import org.kuali.hr.core.assignment.service.AssignmentService;
import org.kuali.hr.core.calendar.service.CalendarEntryService;
import org.kuali.hr.core.leaveplan.LeavePlan;
import org.kuali.hr.core.leaveplan.service.LeavePlanService;
import org.kuali.hr.core.principal.PrincipalHRAttributes;
import org.kuali.hr.core.principal.service.PrincipalHRAttributesService;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.tklm.common.TkConstants;
import org.kuali.hr.tklm.leave.block.LeaveBlock;
import org.kuali.hr.tklm.leave.block.service.LeaveBlockService;
import org.kuali.hr.tklm.leave.service.base.LmServiceLocator;
import org.kuali.hr.tklm.leave.summary.LeaveSummary;
import org.kuali.hr.tklm.leave.summary.LeaveSummaryRow;
import org.kuali.hr.tklm.leave.summary.service.LeaveSummaryService;
import org.kuali.hr.tklm.leave.util.LMConstants;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class CarryOverJob implements Job{

	private static final Logger LOG = Logger.getLogger(CarryOverJob.class);

	private AccrualCategoryService accrualCategoryService;
	private AssignmentService assignmentService;
	private LeavePlanService leavePlanService;
	private PrincipalHRAttributesService principalHRAttributesService;
	private LeaveSummaryService leaveSummaryService;
	private CalendarEntryService calendarEntryService;
	private LeaveBlockService leaveBlockService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
        String batchUserPrincipalId = getBatchUserPrincipalId();

        if (batchUserPrincipalId != null) {
            JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
            String leavePlan = jobDataMap.getString("leavePlan");
            if (leavePlan!= null) {

            	LocalDate asOfDate = LocalDate.now();
                LeavePlan leavePlanObj = getLeavePlanService().getLeavePlan(leavePlan, asOfDate);
                List<Assignment> assignments = getAssignmentService().getActiveAssignments(asOfDate);

                //holds a list of principalIds so this isn't run multiple time for the same person
                Set<String> principalIds = new HashSet<String>();
                for (Assignment assignment : assignments) {
                    String principalId = assignment.getPrincipalId();
                    if (assignment.getJob().isEligibleForLeave() && !principalIds.contains(principalId)) {

                        PrincipalHRAttributes principalHRAttributes = getPrincipalHRAttributesService().getPrincipalCalendar(principalId, asOfDate);
                        principalIds.add(principalId);

                        if (principalHRAttributes != null) {
                            LocalDate serviceDate = principalHRAttributes.getServiceLocalDate();
                            if(serviceDate != null){

                                if (leavePlanObj != null && leavePlanObj.getLeavePlan().equalsIgnoreCase(principalHRAttributes.getLeavePlan())) {

                                    DateTime leavePlanStartDate = getLeavePlanService().getFirstDayOfLeavePlan(leavePlan, LocalDate.now());

                                    DateTime lpPreviousLastDay = (new LocalDateTime(leavePlanStartDate)).toDateTime().minus(1);
                                    DateTime lpPreviousFirstDay = getLeavePlanService().getFirstDayOfLeavePlan(leavePlan, lpPreviousLastDay.toLocalDate());

                                    List<LeaveBlock> prevYearCarryOverleaveBlocks = getLeaveBlockService().getLeaveBlocksWithType(principalId,  lpPreviousFirstDay.toLocalDate(), lpPreviousLastDay.toLocalDate(), LMConstants.LEAVE_BLOCK_TYPE.CARRY_OVER);
                                    LeaveSummary leaveSummary = getLeaveSummaryService().getLeaveSummaryAsOfDateWithoutFuture(principalId, lpPreviousLastDay.toLocalDate());
                                    //no existing carry over blocks.  just create new
                                    if(CollectionUtils.isEmpty(prevYearCarryOverleaveBlocks)){
                                        getLeaveBlockService().saveLeaveBlocks(createCarryOverLeaveBlocks(principalId, lpPreviousLastDay, leaveSummary));
                                    } else {
                                        Map<String, LeaveBlock> existingCarryOver = new HashMap<String, LeaveBlock>(prevYearCarryOverleaveBlocks.size());
                                        // just easier to get to things when in a map...
                                        for (LeaveBlock lb : prevYearCarryOverleaveBlocks) {
                                            existingCarryOver.put(lb.getAccrualCategory(), lb);
                                        }

                                        // update existing first
                                        for (Map.Entry<String, LeaveBlock> entry : existingCarryOver.entrySet()) {
                                            LeaveBlock carryOverBlock = entry.getValue();
                                            LeaveSummaryRow lsr = leaveSummary.getLeaveSummaryRowForAccrualCtgy(entry.getKey());

                                            //update values
                                            if(lsr.getAccruedBalance() != null)  {
                                                if(lsr.getMaxCarryOver() != null && lsr.getAccruedBalance().compareTo(lsr.getMaxCarryOver()) > 0 ){
                                                    carryOverBlock.setLeaveAmount(lsr.getMaxCarryOver());
                                                } else {
                                                    carryOverBlock.setLeaveAmount(lsr.getAccruedBalance());
                                                }
                                            }
                                            getLeaveBlockService().updateLeaveBlock(carryOverBlock, batchUserPrincipalId);
                                            //remove row from leave summary
                                            leaveSummary.getLeaveSummaryRows().remove(lsr);
                                        }


                                        // create for any new accrual categories
                                        getLeaveBlockService().saveLeaveBlocks(createCarryOverLeaveBlocks(principalId, lpPreviousLastDay, leaveSummary));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            String principalName = ConfigContext.getCurrentContextConfig().getProperty(TkConstants.BATCH_USER_PRINCIPAL_NAME);
            LOG.error("Could not run batch jobs due to missing batch user " + principalName);
        }
	
	}

	private AccrualCategoryService getAccrualCategoryService() {
        if (accrualCategoryService == null) {
            accrualCategoryService = HrServiceLocator.getAccrualCategoryService();
        }
		return accrualCategoryService;
	}

	public void setAccrualCategoryService(AccrualCategoryService accrualCategoryService) {
		this.accrualCategoryService = accrualCategoryService;
	}

	private AssignmentService getAssignmentService() {
		if (assignmentService == null) {
            assignmentService = HrServiceLocator.getAssignmentService();
        }
		return assignmentService;
	}

	public void setAssignmentService(AssignmentService assignmentService) {
		this.assignmentService = assignmentService;
	}

	public LeavePlanService getLeavePlanService() {
        if (leavePlanService == null) {
		    leavePlanService = HrServiceLocator.getLeavePlanService();
        }
		return leavePlanService;
	}

	public void setLeavePlanService(LeavePlanService leavePlanService) {
		this.leavePlanService = leavePlanService;
	}

	private PrincipalHRAttributesService getPrincipalHRAttributesService() {
        if (principalHRAttributesService == null) {
		    principalHRAttributesService = HrServiceLocator.getPrincipalHRAttributeService();
        }
		return principalHRAttributesService;
	}

	public void setPrincipalHRAttributesService(PrincipalHRAttributesService principalHRAttributesService) {
		this.principalHRAttributesService = principalHRAttributesService;
	}

	private LeaveSummaryService getLeaveSummaryService() {
        if (leaveSummaryService == null) {
		    leaveSummaryService = LmServiceLocator.getLeaveSummaryService();
        }
		return leaveSummaryService;
	}

	public void setLeaveSummaryService(LeaveSummaryService leaveSummaryService) {
		this.leaveSummaryService = leaveSummaryService;
	}

	private CalendarEntryService getCalendarEntryService() {
        if (calendarEntryService == null) {
		    calendarEntryService = HrServiceLocator.getCalendarEntryService();
        }
		return calendarEntryService;
	}

	public void setCalendarEntryService(CalendarEntryService calendarEntryService) {
		this.calendarEntryService = calendarEntryService;
	}

	private LeaveBlockService getLeaveBlockService() {
        if (leaveBlockService == null) {
		    leaveBlockService = LmServiceLocator.getLeaveBlockService();
        }
		return leaveBlockService;
	}

	public void setLeaveBlockService(LeaveBlockService leaveBlockService) {
		this.leaveBlockService = leaveBlockService;
	}
	
	private List<LeaveBlock> createCarryOverLeaveBlocks(String principalId,
                                                        DateTime prevCalEndDate,
                                                        LeaveSummary leaveSummary) {

        List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
        List<LeaveSummaryRow> leaveSummaryRows = leaveSummary.getLeaveSummaryRows();
        if(leaveSummaryRows !=null && !leaveSummaryRows.isEmpty()){

            for(LeaveSummaryRow lsr : leaveSummaryRows){
                AccrualCategory accrualCategory = getAccrualCategoryService().getAccrualCategory(lsr.getAccrualCategoryId());

                LeaveBlock leaveBlock = new LeaveBlock();
                leaveBlock.setAccrualCategory(lsr.getAccrualCategory());
                leaveBlock.setLeaveLocalDate(prevCalEndDate.toLocalDate());
                leaveBlock.setLeaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.CARRY_OVER);

                //More than one earn code can be associated with an accrual category. Which one does this get?
                if(accrualCategory != null && accrualCategory.getEarnCode() != null ){
                    leaveBlock.setEarnCode(accrualCategory.getEarnCode());
                }

                leaveBlock.setDateAndTime(new Timestamp(System.currentTimeMillis()));
                leaveBlock.setAccrualGenerated(true);
                leaveBlock.setBlockId(0L);

                // ASk--Set null
                leaveBlock.setScheduleTimeOffId(null);

                if(lsr.getAccruedBalance() != null)  {
                    if(lsr.getMaxCarryOver() != null && lsr.getAccruedBalance().compareTo(lsr.getMaxCarryOver()) > 0 ){
                        leaveBlock.setLeaveAmount(lsr.getMaxCarryOver());
                    } else {
                        leaveBlock.setLeaveAmount(lsr.getAccruedBalance());
                    }
                }

                leaveBlock.setPrincipalId(principalId);
                leaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS.APPROVED);

                // Set EarnCode
                if(leaveBlock.getLeaveAmount() != null && leaveBlock.getEarnCode() != null) {
                    leaveBlocks.add(leaveBlock);
                }
            }
        }


		return leaveBlocks;
	}

    private String getBatchUserPrincipalId() {
        String principalName = ConfigContext.getCurrentContextConfig().getProperty(TkConstants.BATCH_USER_PRINCIPAL_NAME);
        Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(principalName);
        return principal == null ? null : principal.getPrincipalId();
    }

}