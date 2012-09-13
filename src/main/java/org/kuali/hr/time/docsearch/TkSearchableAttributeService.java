package org.kuali.hr.time.docsearch;

import java.util.Date;

import org.kuali.hr.core.document.calendar.CalendarDocumentContract;
import org.kuali.hr.time.timesheet.TimesheetDocument;

public interface TkSearchableAttributeService {
	public String createSearchableAttributeXml(CalendarDocumentContract document, Date asOfDate);
	public void updateSearchableAttribute(CalendarDocumentContract document, Date asOfDate);
}
