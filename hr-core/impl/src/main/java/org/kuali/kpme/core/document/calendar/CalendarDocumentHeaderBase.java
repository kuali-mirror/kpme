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
package org.kuali.kpme.core.document.calendar;

import java.util.Date;

import org.joda.time.DateTime;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public abstract class CalendarDocumentHeaderBase extends PersistableBusinessObjectBase {

	protected String documentId;
	protected String principalId;
	protected Date beginDate;
	protected Date endDate;
	protected String documentStatus;
	protected String calendarType;
	
	public abstract String getDocumentId();

	public abstract String getPrincipalId();

	public abstract String getDocumentStatus();

	public abstract Date getBeginDate();

	public abstract DateTime getBeginDateTime();

	public abstract Date getEndDate();

	public abstract DateTime getEndDateTime();
	
	public abstract String getCalendarType();

}
