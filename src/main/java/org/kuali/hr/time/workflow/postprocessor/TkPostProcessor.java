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

import java.util.Date;
import java.util.List;

import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
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
				
				calculateMaxCarryOver(document, newDocumentStatus);
			}
		}
		
		return pdr;
	}
	
	private void updateTimesheetDocumentHeaderStatus(TimesheetDocumentHeader timesheetDocumentHeader, DocumentStatus newDocumentStatus) {
		timesheetDocumentHeader.setDocumentStatus(newDocumentStatus.getCode());
		TkServiceLocator.getTimesheetDocumentHeaderService().saveOrUpdate(timesheetDocumentHeader);
	}
	
	private void calculateMaxCarryOver(TimesheetDocumentHeader timesheetDocumentHeader, DocumentStatus newDocumentStatus) {
		if (DocumentStatus.FINAL.equals(newDocumentStatus)) {
			String documentId = timesheetDocumentHeader.getDocumentId();
			String principalId = timesheetDocumentHeader.getPrincipalId();
			Date beginDate = timesheetDocumentHeader.getBeginDate();
			Date endDate = timesheetDocumentHeader.getEndDate();
			
			if (TkServiceLocator.getLeaveApprovalService().isActiveAssignmentFoundOnJobFlsaStatus(principalId, TkConstants.FLSA_STATUS_NON_EXEMPT, true)) {
				Calendar calendar = TkServiceLocator.getCalendarService().getCalendarByPrincipalIdAndDate(principalId, endDate, true);
					
				if (calendar != null) {
					List<CalendarEntries> calendarEntries = TkServiceLocator.getCalendarEntriesService().getCalendarEntriesEndingBetweenBeginAndEndDate(calendar.getHrCalendarId(), beginDate, endDate);
					
					TkServiceLocator.getAccrualCategoryMaxCarryOverService().calculateMaxCarryOver(documentId, principalId, calendarEntries, endDate);
				}
			}
		}
	}

}
