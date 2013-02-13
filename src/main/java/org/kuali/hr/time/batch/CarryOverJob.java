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
package org.kuali.hr.time.batch;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.service.AccrualCategoryService;
import org.kuali.hr.lm.accrual.service.AccrualService;
import org.kuali.hr.lm.leaveSummary.LeaveSummary;
import org.kuali.hr.lm.leaveSummary.LeaveSummaryRow;
import org.kuali.hr.lm.leaveSummary.service.LeaveSummaryService;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leaveblock.service.LeaveBlockService;
import org.kuali.hr.lm.leavecalendar.service.LeaveCalendarServiceImpl;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.lm.leaveplan.service.LeavePlanService;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.service.AssignmentService;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.calendar.LeaveCalendar;
import org.kuali.hr.time.calendar.service.CalendarEntriesService;
import org.kuali.hr.time.calendar.service.CalendarService;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.principal.service.PrincipalHRAttributesService;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class CarryOverJob implements Job{

	private static final Logger LOG = Logger
			.getLogger(CarryOverJob.class);

	private static AccrualCategoryService ACCRUAL_CATEGORY_SERVICE;
	private static AssignmentService ASSIGNMENT_SERVICE;
	private static LeavePlanService LEAVE_PLAN_SERVICE;
	private static PrincipalHRAttributesService PRINCIPAL_HR_ATTRIBUTES_SERVICE;
	private static LeaveSummaryService LEAVE_SUMMARY_SERVICE;
	private static CalendarEntriesService CALENDAR_ENTRIES_SERVICE;
	private static LeaveBlockService LEAVE_BLOCK_SERVICE;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		String leavePlan = jobDataMap.getString("leavePlanCode");
        if (leavePlan!= null) {
        	
        	Date asOfDate = TKUtils.getCurrentDate();
        	LeavePlan leavePlanObj = getLeavePlanService().getLeavePlan(leavePlan, asOfDate);
			List<Assignment> assignments = getAssignmentService().getActiveAssignments(asOfDate);

            //holds a list of principalIds so this isn't run multiple time for the same person
			Set<String> principalIds = new HashSet<String>();
			Map<String,LeaveBlock> carryOverLeaveBlockMap = null;
			for (Assignment assignment : assignments) {
				carryOverLeaveBlockMap =  new HashMap<String, LeaveBlock>();
				String principalId = assignment.getPrincipalId();
                if (assignment.getJob().isEligibleForLeave() && !principalIds.contains(principalId)) {

                    PrincipalHRAttributes principalHRAttributes = getPrincipalHRAttributesService().getPrincipalCalendar(principalId, asOfDate);
                    principalIds.add(principalId);

                    if (principalHRAttributes != null) {
                        Date serviceDate = principalHRAttributes.getServiceDate();
                        if(serviceDate != null){

                            Calendar leaveCalendar = principalHRAttributes.getLeaveCalObj();
                            if (leavePlanObj != null && leavePlanObj.getLeavePlan().equalsIgnoreCase(principalHRAttributes.getLeavePlan())) {

                                java.util.Calendar lpYearNextStart = getLeavePlanCalendarYearStart(leavePlanObj);
                                java.util.Date originYearStart = lpYearNextStart.getTime();
                                // this should be passed to the

                                java.util.Calendar servicStartCal = this.getLeaveCalendarServiceStart(serviceDate, leavePlanObj);

                                while (servicStartCal.getTime().compareTo(originYearStart) <= 0) {

                                    java.util.Calendar lpYearPreviousStart = java.util.Calendar.getInstance();
                                    lpYearPreviousStart.setTime(servicStartCal.getTime());
                                    lpYearPreviousStart.add(java.util.Calendar.YEAR, -1);

                                    java.util.Calendar prevCalEndDateCal = java.util.Calendar.getInstance();
                                    prevCalEndDateCal.setTime(servicStartCal.getTime());
                                    prevCalEndDateCal.add(java.util.Calendar.DATE, -1);

                                    java.util.Date prevCalEndDate = prevCalEndDateCal.getTime();

                                    List<LeaveBlock> prevYearCarryOverleaveBlocks = getLeaveBlockService().getLeaveBlocksWithType(principalId,  lpYearPreviousStart.getTime(), prevCalEndDate, LMConstants.LEAVE_BLOCK_TYPE.CARRY_OVER);
                                    if(prevYearCarryOverleaveBlocks == null || prevYearCarryOverleaveBlocks.isEmpty()){
                                        if (serviceDate.getTime() > lpYearPreviousStart.getTime().getTime()) {
                                            lpYearPreviousStart.setTime(serviceDate);
                                        }
                                        if (prevCalEndDate.getTime() >= serviceDate.getTime()) {
                                            fillCarryOverLeaveBlockMap(principalId, leaveCalendar, prevCalEndDate, carryOverLeaveBlockMap);
                                        }
                                    }

                                    servicStartCal.add(java.util.Calendar.YEAR, 1);

                                    getLeaveBlockService().saveLeaveBlocks(new ArrayList<LeaveBlock>(carryOverLeaveBlockMap.values()));
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

	public static AccrualCategoryService getAccrualCategoryService() {
		ACCRUAL_CATEGORY_SERVICE = TkServiceLocator.getAccrualCategoryService();
		return ACCRUAL_CATEGORY_SERVICE;
	}

	public static void setAccrualCategoryService(AccrualCategoryService accrualCategoryService) {
		ACCRUAL_CATEGORY_SERVICE = accrualCategoryService;
	}

	public static AssignmentService getAssignmentService() {
		ASSIGNMENT_SERVICE = TkServiceLocator.getAssignmentService();
		return ASSIGNMENT_SERVICE;
	}

	public static void setAssignmentService(AssignmentService assignmentService) {
		ASSIGNMENT_SERVICE = assignmentService;
	}

	public static LeavePlanService getLeavePlanService() {
		LEAVE_PLAN_SERVICE = TkServiceLocator.getLeavePlanService();
		return LEAVE_PLAN_SERVICE;
	}

	public static void setLeavePlanService(LeavePlanService leavePlanService) {
		LEAVE_PLAN_SERVICE = leavePlanService;
	}

	public static PrincipalHRAttributesService getPrincipalHRAttributesService() {
		PRINCIPAL_HR_ATTRIBUTES_SERVICE = TkServiceLocator.getPrincipalHRAttributeService();
		return PRINCIPAL_HR_ATTRIBUTES_SERVICE;
	}

	public static void setPrincipalHRAttributesService(
			PrincipalHRAttributesService principalHRAttributesService) {
		PRINCIPAL_HR_ATTRIBUTES_SERVICE = principalHRAttributesService;
	}

	public static LeaveSummaryService getLeaveSummaryService() {
		LEAVE_SUMMARY_SERVICE = TkServiceLocator.getLeaveSummaryService();
		return LEAVE_SUMMARY_SERVICE;
	}

	public static void setLeaveSummaryService(
			LeaveSummaryService leaveSummaryService) {
		LEAVE_SUMMARY_SERVICE = leaveSummaryService;
	}

	public static CalendarEntriesService getCalendarEntriesService() {
		CALENDAR_ENTRIES_SERVICE = TkServiceLocator.getCalendarEntriesService();
		return CALENDAR_ENTRIES_SERVICE;
	}

	public static void setCalendarEntriesService(
			CalendarEntriesService calendarEntriesService) {
		CALENDAR_ENTRIES_SERVICE = calendarEntriesService;
	}

	public static LeaveBlockService getLeaveBlockService() {
		LEAVE_BLOCK_SERVICE = TkServiceLocator.getLeaveBlockService();
		return LEAVE_BLOCK_SERVICE;
	}

	public static void setLeaveBlockService(LeaveBlockService leaveBlockService) {
		LEAVE_BLOCK_SERVICE = leaveBlockService;
	}
	
	private void fillCarryOverLeaveBlockMap(String principalId,
                                            Calendar leaveCalendar,
                                            java.util.Date prevCalEndDate,
                                            Map<String, LeaveBlock> carryOverLeaveBlockMap){

 				   CalendarEntries calendarEntries = getCalendarEntriesService().getCurrentCalendarEntriesByCalendarId(leaveCalendar.getHrCalendarId(), prevCalEndDate);
					try {
						LeaveSummary leaveSummary = getLeaveSummaryService().getLeaveSummary(principalId, calendarEntries);
						List<LeaveSummaryRow> leaveSummaryRows = leaveSummary.getLeaveSummaryRows();
						
						if(leaveSummaryRows !=null && !leaveSummaryRows.isEmpty()){
							
							for(LeaveSummaryRow lsr : leaveSummaryRows){
								AccrualCategory accrualCategory = getAccrualCategoryService().getAccrualCategory(lsr.getAccrualCategoryId());
								
								LeaveBlock leaveBlock = new LeaveBlock();
								leaveBlock.setAccrualCategory(lsr.getAccrualCategory());
								leaveBlock.setLeaveDate(TKUtils.getTimelessDate(prevCalEndDate));
								leaveBlock.setLeaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.CARRY_OVER);
								
								//More than one earn code can be associated with an accrual category. Which one does this get?
								if(accrualCategory != null && accrualCategory.getEarnCode() != null ){
									leaveBlock.setEarnCode(accrualCategory.getEarnCode());	
								}
								
								leaveBlock.setDateAndTime(new Timestamp(new java.util.Date().getTime()));
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
									if(!carryOverLeaveBlockMap.containsKey(lsr.getAccrualCategoryId())) {
										carryOverLeaveBlockMap.put(lsr.getAccrualCategoryId(), leaveBlock);
									} else {
										LeaveBlock lb = carryOverLeaveBlockMap.get(lsr.getAccrualCategoryId());
										leaveBlock.setLeaveAmount(lb.getLeaveAmount().add(leaveBlock.getLeaveAmount()));
										carryOverLeaveBlockMap.put(lsr.getAccrualCategoryId(), leaveBlock);
									}
								}
							}
						}
						
					} catch (Exception e) {
						LOG.error("Could not run batch jobs due to missing leaveSummary "+e);
						e.printStackTrace();
					}
		
	}
	
	private java.util.Calendar getLeaveCalendarServiceStart(java.sql.Date serviceDate, LeavePlan leavePlan) {
		// check if Calendar entry is first entry of the year start the make
		// accrued balance and approved usage zero
		
		String calendarYearStartStr = leavePlan.getCalendarYearStart();

		java.util.Calendar serviceCal = java.util.Calendar.getInstance();
		serviceCal.setTimeInMillis(serviceDate.getTime());
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
		sdf.setLenient(false);
		
		java.util.Date calYearStart = null;
		
		try {
			calYearStart = sdf.parse(calendarYearStartStr);
		} catch (ParseException e) {
		}
		
		java.util.Calendar lpYearStart = java.util.Calendar.getInstance();
		lpYearStart.setTime(calYearStart);
		lpYearStart.set(java.util.Calendar.YEAR, serviceCal.get(java.util.Calendar.YEAR));
		lpYearStart.set(java.util.Calendar.HOUR_OF_DAY, 0);
		lpYearStart.set(java.util.Calendar.MINUTE, 0);
		lpYearStart.set(java.util.Calendar.SECOND, 0);
		lpYearStart.set(java.util.Calendar.MILLISECOND, 0);
		
		return lpYearStart;
	}
	
	private java.util.Date getLeavePlanCalendarYearEnd(LeavePlan leavePlan) {
		// check if Calendar entry is first entry of the year start the make
		// accrued balance and approved usage zero
		String calendarYearStartStr = leavePlan.getCalendarYearStart();

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
		sdf.setLenient(false);
		java.util.Date calYearStart = null;
		try {
			calYearStart = sdf.parse(calendarYearStartStr);
		} catch (ParseException e) {
		}
		java.util.Calendar lpYearStart = java.util.Calendar.getInstance();
		lpYearStart.setTime(calYearStart);
		lpYearStart.set(java.util.Calendar.YEAR,java.util.Calendar.getInstance().get(java.util.Calendar.YEAR));
		lpYearStart.set(java.util.Calendar.HOUR_OF_DAY, 0);
		lpYearStart.set(java.util.Calendar.MINUTE, 0);
		lpYearStart.set(java.util.Calendar.SECOND, 0);
		lpYearStart.set(java.util.Calendar.MILLISECOND, 0);
		lpYearStart.add(java.util.Calendar.DATE, -1);
		
		return lpYearStart.getTime();
	}
	
	private java.util.Calendar getLeavePlanCalendarYearStart(LeavePlan leavePlan) {
		// check if Calendar entry is first entry of the year start the make
		// accrued balance and approved usage zero
		
		String calendarYearStartStr = leavePlan.getCalendarYearStart();

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
		sdf.setLenient(false);
		java.util.Date calYearStart = null;
		
		try {
			calYearStart = sdf.parse(calendarYearStartStr);
		} catch (ParseException e) {
		}
		
		java.util.Calendar lpYearStart = java.util.Calendar.getInstance();
		lpYearStart.setTime(calYearStart);
		lpYearStart.set(java.util.Calendar.YEAR,java.util.Calendar.getInstance().get(java.util.Calendar.YEAR));
		lpYearStart.set(java.util.Calendar.HOUR_OF_DAY, 0);
		lpYearStart.set(java.util.Calendar.MINUTE, 0);
		lpYearStart.set(java.util.Calendar.SECOND, 0);
		lpYearStart.set(java.util.Calendar.MILLISECOND, 0);
		
		return lpYearStart;
	}
	
}