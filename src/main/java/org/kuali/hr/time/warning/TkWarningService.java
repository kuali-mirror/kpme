package org.kuali.hr.time.warning;

import org.kuali.hr.time.timesheet.TimesheetDocument;

import java.util.List;

public interface TkWarningService {
	public List<String> getWarnings(String documentNumber);
	public List<String> getWarnings(TimesheetDocument td);
}
