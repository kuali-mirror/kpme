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
