package org.kuali.hr.time.docsearch;

import java.util.Date;

import org.kuali.hr.time.timesheet.TimesheetDocument;

public interface TkSearchableAttributeService {
	public String createSearchableAttributeXml(TimesheetDocument document, Date asOfDate);
	public void updateSearchableAttribute(TimesheetDocument document, Date asOfDate);
}
