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
package org.kuali.kpme.tklm.leave.workflow.postprocessor;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.calendar.Calendar;
import org.kuali.kpme.core.bo.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.TKContext;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.workflow.LeaveCalendarDocumentHeader;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.framework.postprocessor.ProcessDocReport;
import org.kuali.rice.kew.postprocessor.DefaultPostProcessor;

public class LmPostProcessor extends DefaultPostProcessor {

	@Override
	public ProcessDocReport doRouteStatusChange(DocumentRouteStatusChange statusChangeEvent) throws Exception {		
		ProcessDocReport pdr = null;
		Long documentId = new Long(statusChangeEvent.getDocumentId());
		LeaveCalendarDocumentHeader document = LmServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(documentId.toString());
		if (document != null) {
			pdr = super.doRouteStatusChange(statusChangeEvent);
			// Only update the status if it's different.
			if (!document.getDocumentStatus().equals(statusChangeEvent.getNewRouteStatus())) {
                DocumentStatus newDocumentStatus = DocumentStatus.fromCode(statusChangeEvent.getNewRouteStatus());

				updateLeaveCalendarDocumentHeaderStatus(document, newDocumentStatus);
				
				calculateMaxCarryOver(document, newDocumentStatus);
			}
		}
		
		return pdr;
	}
	
	private void updateLeaveCalendarDocumentHeaderStatus(LeaveCalendarDocumentHeader leaveCalendarDocumentHeader, DocumentStatus newDocumentStatus) {
		leaveCalendarDocumentHeader.setDocumentStatus(newDocumentStatus.getCode());
		LmServiceLocator.getLeaveCalendarDocumentHeaderService().saveOrUpdate(leaveCalendarDocumentHeader);
	}
	
	private void calculateMaxCarryOver(LeaveCalendarDocumentHeader leaveCalendarDocumentHeader, DocumentStatus newDocumentStatus) {
		String documentId = leaveCalendarDocumentHeader.getDocumentId();
		String principalId = leaveCalendarDocumentHeader.getPrincipalId();
		DateTime endDate = leaveCalendarDocumentHeader.getEndDateTime();
		if (DocumentStatus.ENROUTE.equals(newDocumentStatus)) {
			//create pending carry over leave blocks.
			
			Calendar calendar = HrServiceLocator.getCalendarService().getCalendarByPrincipalIdAndDate(principalId, new LocalDate(endDate), true);
			
			if (calendar != null) {
				CalendarEntry calendarEntry = HrServiceLocator.getCalendarEntryService().getCalendarEntryByIdAndPeriodEndDate(calendar.getHrCalendarId(), new DateTime(endDate));
				
				LmServiceLocator.getAccrualCategoryMaxCarryOverService().calculateMaxCarryOver(documentId, principalId, calendarEntry, endDate.toLocalDate());
			}
		}
		else if (DocumentStatus.FINAL.equals(newDocumentStatus)) {
			//approve the carry over leave block.
			List<LeaveBlock> leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, leaveCalendarDocumentHeader.getBeginDateTime().toLocalDate(), endDate.toLocalDate());
			for(LeaveBlock lb : leaveBlocks) {
				if(StringUtils.equals(lb.getDescription(),"Max carry over adjustment")) {
					lb.setRequestStatus(HrConstants.REQUEST_STATUS.APPROVED);
					LmServiceLocator.getLeaveBlockService().updateLeaveBlock(lb, TKContext.getPrincipalId());
				}
			}
		}
	}

}