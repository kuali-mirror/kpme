package org.kuali.hr.time.accrual.service;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.kuali.hr.time.accrual.TimeOffAccrual;
import org.kuali.hr.time.timesheet.TimesheetDocument;

public interface TimeOffAccrualService {
	/**
	 * Get TimeOffAccrual object for a particular user
	 * @param principalId
	 * @return
	 */
	public List<TimeOffAccrual> getTimeOffAccruals(String principalId);
	/**
	 * Get a list of maps that represents a persons accrual balances
	 * @param principalId
	 * @return
	 */
	public List<Map<String, Object>> getTimeOffAccrualsCalc(String principalId);
	
	/**
	 * Validate the accrual hours for the time blocks of the given TimesheetDocument
	 * and returns a JSONArray of warning messages
	 * @param timesheetDocument
	 * @return JSONArray
	 */
	public JSONArray validateAccrualHoursLimit(TimesheetDocument timesheetDocument);
	
	public TimeOffAccrual getTimeOffAccrual(Long laTimeOffAccrualId);
}
