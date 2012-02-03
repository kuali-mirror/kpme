package org.kuali.hr.time.warning;

import java.util.List;

import org.kuali.hr.time.timesheet.TimesheetDocument;

public interface TkWarningService {
	public List<String> getWarnings(String documentNumber);
	public List<String> getWarnings(TimesheetDocument td);
}
