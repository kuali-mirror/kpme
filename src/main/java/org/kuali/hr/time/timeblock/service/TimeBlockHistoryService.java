package org.kuali.hr.time.timeblock.service;

import java.util.List;

import org.kuali.hr.time.detail.web.TimeDetailActionForm;
import org.kuali.hr.time.timeblock.TimeBlockHistory;
import org.kuali.hr.time.timesheet.web.TimesheetActionForm;

public interface TimeBlockHistoryService {

	public void saveTimeBlockHistory(TimesheetActionForm form);

	public List<TimeBlockHistory> saveTimeBlockHistoryList(TimeDetailActionForm tdaf);
}
