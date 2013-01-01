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
package org.kuali.hr.core.document;

import java.util.Date;


public interface CalendarDocumentHeaderContract {
    /**
     * The document id for the Document Header.
     *
     * @return documentId
     */
    String getDocumentId();

    /**
     * The principal id that initiated the the Document Header.
     *
     * @return principalId
     */
    String getPrincipalId();

    /**
     * The current status code of the Document Header.
     *
     * @return documentStatus
     */
    String getDocumentStatus();

    /**
     * The begin date of the calendar entry for the Calendar document
     *
     * @return beginDate
     */
    Date getBeginDate();

    /**
     * The end date of the calendar entry for the Calendar document
     *
     * @return endDate
     */
    Date getEndDate();
}
