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
