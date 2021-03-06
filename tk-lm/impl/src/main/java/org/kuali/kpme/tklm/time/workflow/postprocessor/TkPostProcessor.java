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
package org.kuali.kpme.tklm.time.workflow.postprocessor;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.api.calendar.Calendar;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.api.earncode.EarnCode;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.api.time.timehourdetail.TimeHourDetail;
import org.kuali.kpme.tklm.common.LMConstants;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.framework.postprocessor.ProcessDocReport;
import org.kuali.rice.kew.postprocessor.DefaultPostProcessor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
			DateTime endDate = timesheetDocumentHeader.getEndDateTime();
			
			if (LmServiceLocator.getLeaveApprovalService().isActiveAssignmentFoundOnJobFlsaStatus(principalId, HrConstants.FLSA_STATUS_NON_EXEMPT, true)) {
				List<TimeBlock> timeBlocks = TkServiceLocator.getTimeBlockService().getTimeBlocks(documentId);
				
				List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
				
				for (TimeBlock timeBlock : timeBlocks) {
					EarnCode overtimeEarnCode = null;
					BigDecimal leaveAmount = null;
					for (TimeHourDetail timeHourDetail : timeBlock.getTimeHourDetails()) {
						EarnCode earnCode = HrServiceLocator.getEarnCodeService().getEarnCode(timeHourDetail.getEarnCode(),  endDate.toLocalDate());
						if (earnCode != null && earnCode.isOvtEarnCode()) {
							overtimeEarnCode = earnCode;
							leaveAmount = timeHourDetail.getHours();
							//What if [can] the units are [be] days?
							break;
						}
					}
					if (overtimeEarnCode != null && leaveAmount != null) {
						AccrualCategory accrualCategory = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(overtimeEarnCode.getAccrualCategory(), endDate.toLocalDate());
						
						if (accrualCategory != null) {
							LocalDate leaveDate = timeBlock.getBeginDateTime().toLocalDate();
                            LeaveBlock.Builder builder = LeaveBlock.Builder.create(principalId, overtimeEarnCode.getEarnCode(), leaveAmount, leaveDate, null);
                            builder.setAccrualCategory(accrualCategory.getAccrualCategory());
                            builder.setLeaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.ACCRUAL_SERVICE);
                            builder.setRequestStatus(HrConstants.REQUEST_STATUS.APPROVED);
							
							leaveBlocks.add(builder.build());
						}
					}
				}
				
				LmServiceLocator.getLeaveBlockService().saveLeaveBlocks(leaveBlocks);
			}
		}
	}
	
	private void calculateMaxCarryOver(TimesheetDocumentHeader timesheetDocumentHeader, DocumentStatus newDocumentStatus) {
		String documentId = timesheetDocumentHeader.getDocumentId();
		String principalId = timesheetDocumentHeader.getPrincipalId();
		DateTime endDate = timesheetDocumentHeader.getEndDateTime();
		DateTime beginDate = timesheetDocumentHeader.getBeginDateTime();
		if (DocumentStatus.ENROUTE.equals(newDocumentStatus)) {
			//create pending carry over leave blocks.

            Calendar calendar = HrServiceLocator.getCalendarService().getCalendarByPrincipalIdAndDate(principalId, endDate.toLocalDate(), true);
			
			if (calendar != null) {
				List<CalendarEntry> calendarEntries = HrServiceLocator.getCalendarEntryService().getCalendarEntriesEndingBetweenBeginAndEndDate(calendar.getHrCalendarId(), beginDate, endDate);
				
				LmServiceLocator.getAccrualCategoryMaxCarryOverService().calculateMaxCarryOver(documentId, principalId, calendarEntries, endDate.toLocalDate());
			}
		}
		else if (DocumentStatus.FINAL.equals(newDocumentStatus)) {
			// TODO: HrServiceLocator.getMaxCarryOverService.updateCarryOverLeaveBlockStatus(principalId, timesheetDocumentHeader.getBeginDateTime().toLocalDate(), endDate.toLocalDate());
			//approve the carry over leave block.
			List<LeaveBlock> leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, timesheetDocumentHeader.getBeginDateTime().toLocalDate(), endDate.toLocalDate());
			for(LeaveBlock lb : leaveBlocks) {
				if(StringUtils.equals(lb.getLeaveBlockType(), LMConstants.LEAVE_BLOCK_TYPE.CARRY_OVER_ADJUSTMENT)) {
                    LeaveBlock.Builder builder = LeaveBlock.Builder.create(lb);
					builder.setRequestStatus(HrConstants.REQUEST_STATUS.APPROVED);
					LmServiceLocator.getLeaveBlockService().updateLeaveBlock(builder.build(), HrContext.getPrincipalId());
				}
			}
		}
	}

}
