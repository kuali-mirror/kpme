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
package org.kuali.hr.time.workflow.postprocessor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntry;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.framework.postprocessor.ProcessDocReport;
import org.kuali.rice.kew.postprocessor.DefaultPostProcessor;

public class TkPostProcessor extends DefaultPostProcessor {

	@Override
	public ProcessDocReport doRouteStatusChange(DocumentRouteStatusChange statusChangeEvent) throws Exception {		
		ProcessDocReport pdr = null;
		Long documentId = new Long(statusChangeEvent.getDocumentId());
		TimesheetDocumentHeader document = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(documentId.toString());
		if (document != null) {
			pdr = super.doRouteStatusChange(statusChangeEvent);
			// Only update the status if it's different.
			if (!document.getDocumentStatus().equals(statusChangeEvent.getNewRouteStatus())) {
				DocumentStatus newDocumentStatus = DocumentStatus.fromCode(statusChangeEvent.getNewRouteStatus());

				updateTimesheetDocumentHeaderStatus(document, newDocumentStatus);
				
				calculateLeaveCalendarOvertime(document, newDocumentStatus);
				
				calculateMaxCarryOver(document, newDocumentStatus);
			}
		}
		
		return pdr;
	}
	
	private void updateTimesheetDocumentHeaderStatus(TimesheetDocumentHeader timesheetDocumentHeader, DocumentStatus newDocumentStatus) {
		timesheetDocumentHeader.setDocumentStatus(newDocumentStatus.getCode());
		TkServiceLocator.getTimesheetDocumentHeaderService().saveOrUpdate(timesheetDocumentHeader);
	}
	
	private void calculateLeaveCalendarOvertime(TimesheetDocumentHeader timesheetDocumentHeader, DocumentStatus newDocumentStatus) {
		if (DocumentStatus.FINAL.equals(newDocumentStatus)) {
			String documentId = timesheetDocumentHeader.getDocumentId();
			String principalId = timesheetDocumentHeader.getPrincipalId();
			Date endDate = timesheetDocumentHeader.getEndDate();
			
			if (TkServiceLocator.getLeaveApprovalService().isActiveAssignmentFoundOnJobFlsaStatus(principalId, TkConstants.FLSA_STATUS_NON_EXEMPT, true)) {
				List<TimeBlock> timeBlocks = TkServiceLocator.getTimeBlockService().getTimeBlocks(documentId);
				
				List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
				
				for (TimeBlock timeBlock : timeBlocks) {
					EarnCode overtimeEarnCode = getOvertimeEarnCode(timeBlock, endDate);
					
					if (overtimeEarnCode != null) {
						AccrualCategory accrualCategory = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(overtimeEarnCode.getAccrualCategory(), new java.sql.Date(endDate.getTime()));
						
						if (accrualCategory != null) {
							DateTime leaveDate = new DateTime(timeBlock.getBeginDate());
							BigDecimal leaveAmount = timeBlock.getHours();
							
							LeaveBlock.Builder builder = new LeaveBlock.Builder(leaveDate, null, principalId, overtimeEarnCode.getEarnCode(), leaveAmount)
								.accrualCategory(accrualCategory.getAccrualCategory())
								.leaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.ACCRUAL_SERVICE)
								.requestStatus(LMConstants.REQUEST_STATUS.APPROVED);
							
							leaveBlocks.add(builder.build());
						}
					}
				}
				
				TkServiceLocator.getLeaveBlockService().saveLeaveBlocks(leaveBlocks);
			}
		}
	}
	
	private EarnCode getOvertimeEarnCode(TimeBlock timeBlock, Date asOfDate) {
		EarnCode overtimeEarnCode = null;
		
		for (TimeHourDetail timeHourDetail : timeBlock.getTimeHourDetails()) {
			EarnCode earnCode = TkServiceLocator.getEarnCodeService().getEarnCode(timeHourDetail.getEarnCode(), new java.sql.Date(asOfDate.getTime()));
		
			if (earnCode != null && earnCode.getOvtEarnCode()) {
				overtimeEarnCode = earnCode;
				break;
			}
		}
		
		return overtimeEarnCode;
	}
	
	private void calculateMaxCarryOver(TimesheetDocumentHeader timesheetDocumentHeader, DocumentStatus newDocumentStatus) {
		String documentId = timesheetDocumentHeader.getDocumentId();
		String principalId = timesheetDocumentHeader.getPrincipalId();
		Date endDate = timesheetDocumentHeader.getEndDate();
		Date beginDate = timesheetDocumentHeader.getBeginDate();
		if (DocumentStatus.ENROUTE.equals(newDocumentStatus)) {
			//create pending carry over leave blocks.
			
			Calendar calendar = TkServiceLocator.getCalendarService().getCalendarByPrincipalIdAndDate(principalId, endDate, true);
			
			if (calendar != null) {
				List<CalendarEntry> calendarEntries = TkServiceLocator.getCalendarEntryService().getCalendarEntriesEndingBetweenBeginAndEndDate(calendar.getHrCalendarId(), beginDate, endDate);
				
				TkServiceLocator.getAccrualCategoryMaxCarryOverService().calculateMaxCarryOver(documentId, principalId, calendarEntries, endDate);
			}
		}
		else if (DocumentStatus.FINAL.equals(newDocumentStatus)) {
			//approve the carry over leave block.
			List<LeaveBlock> leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, timesheetDocumentHeader.getBeginDate(), endDate);
			for(LeaveBlock lb : leaveBlocks) {
				if(StringUtils.equals(lb.getDescription(),"Max carry over adjustment")) {
					lb.setRequestStatus(LMConstants.REQUEST_STATUS.APPROVED);
					TkServiceLocator.getLeaveBlockService().updateLeaveBlock(lb, TKContext.getPrincipalId());
				}
			}
		}
	}

}