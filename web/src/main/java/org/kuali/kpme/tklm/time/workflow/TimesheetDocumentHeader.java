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
package org.kuali.kpme.tklm.time.workflow;

import java.util.Date;

import org.joda.time.DateTime;
import org.kuali.kpme.core.document.CalendarDocumentHeaderContract;
import org.kuali.kpme.core.document.calendar.CalendarDocumentHeaderBase;
import org.kuali.kpme.core.util.HrConstants;

public class TimesheetDocumentHeader extends CalendarDocumentHeaderBase implements CalendarDocumentHeaderContract {

	private static final long serialVersionUID = 1L;

	public TimesheetDocumentHeader() {
		
	}
	
	public TimesheetDocumentHeader(String documentId, String principalId, Date payBeginDate, Date payEndDate, String documentStatus) {
		this.documentId = documentId;
		this.principalId = principalId;
		this.beginDate = payBeginDate;
		this.endDate = payEndDate;
		this.documentStatus = documentStatus;
		this.calendarType = HrConstants.PAY_CALENDAR_TYPE;
	}

    @Override
	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

    @Override
	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

    @Override
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Override
	public DateTime getEndDateTime() {
		return endDate != null ? new DateTime(endDate) : null;
	}
	
	public void setEndDateTime(DateTime endDateTime) {
		this.endDate = endDateTime != null ? endDateTime.toDate() : null;
	}

    @Override
	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

    @Override
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	@Override
	public DateTime getBeginDateTime() {
		return beginDate != null ? new DateTime(beginDate) : null;
	}
	
	public void setBeginDateTime(DateTime beginDateTime) {
		this.beginDate = beginDateTime != null ? beginDateTime.toDate() : null;
	}

	@Override
	public String getCalendarType() {
		// TODO Auto-generated method stub
		return calendarType;
	}

}