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
package org.kuali.kpme.core.api.document.calendar;

import java.util.Date;

import org.joda.time.DateTime;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

/**
 * <p>CalendarDocumentHeaderContract interface</p>
 *
 */
public interface CalendarDocumentHeaderContract extends PersistableBusinessObject {

	/**
	 * The document id the CalendarDocumentHeader is associated with
	 * 
	 * <p>
	 * documentId of a CalendarDocumentHeader
	 * </p>
	 * 
	 * @return documentId of CalendarDocumentHeader
	 */
    String getDocumentId();

    /**
	 * The principal id that initiated the the Document Header
	 * 
	 * <p>
	 * principalId of a CalendarDocumentHeader
	 * </p>
	 * 
	 * @return principalId of CalendarDocumentHeader
	 */
    String getPrincipalId();

    /**
	 * The current status code of the Document Header
	 * 
	 * <p>
	 * documentStatus of a CalendarDocumentHeader
	 * </p>
	 * 
	 * @return documentStatus of CalendarDocumentHeader
	 */
    String getDocumentStatus();

    /**
	 * The begin date of the calendar entry for the Calendar document
	 * 
	 * <p>
	 * beginDate of a CalendarDocumentHeader
	 * </p>
	 * 
	 * @return beginDate of CalendarDocumentHeader
	 */
    Date getBeginDate();

    /**
	 * The begin date time of the calendar entry for the Calendar document
	 * 
	 * <p>
	 * beginDateTime of a CalendarDocumentHeader
	 * </p>
	 * 
	 * @return beginDateTime of CalendarDocumentHeader
	 */
    DateTime getBeginDateTime();

    /**
   	 * The end date of the calendar entry for the Calendar document
   	 * 
   	 * <p>
   	 * endDate of a CalendarDocumentHeader
   	 * </p>
   	 * 
   	 * @return endDate of CalendarDocumentHeader
   	 */
    Date getEndDate();

    /**
   	 * The end date time of the calendar entry for the Calendar document
   	 * 
   	 * <p>
   	 * endDateTime of a CalendarDocumentHeader
   	 * </p>
   	 * 
   	 * @return endDateTime of CalendarDocumentHeader
   	 */
    DateTime getEndDateTime();
    
}