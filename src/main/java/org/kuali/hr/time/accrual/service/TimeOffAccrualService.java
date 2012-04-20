package org.kuali.hr.time.accrual.service;

import org.kuali.hr.time.accrual.TimeOffAccrual;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface TimeOffAccrualService {
	/**
	 * Get a list of maps that represents a persons accrual balances
	 * @param principalId
	 * @param asOfDate
	 * @return
	 */
	public List<Map<String, Object>> getTimeOffAccrualsCalc(String principalId, Date asOfDate);
	
	/**
	 * Validate the accrual hours for the time blocks of the given TimesheetDocument
	 * and returns a JSONArray of warning messages
	 * @param timesheetDocument
	 * @return JSONArray
	 */
	public List<String> validateAccrualHoursLimit(TimesheetDocument timesheetDocument);
	
	public List<String> validateAccrualHoursLimit(String pId, List<TimeBlock> tbList, Date asOfDate);
	
	/**
	 * Validate the accrual hours for the time blocks by earncode of the given TimesheetDocument
	 * and returns a JSONArray of warning messages
	 * @param timesheetDocument
	 * @return JSONArray
	 */
	public List<String> validateAccrualHoursLimitByEarnCode(TimesheetDocument timesheetDocument, String selectEarnCode);
	
	/**
	 * Fetch time off accrual as of a particular unique id
	 * @param laTimeOffAccrualId
	 * @return
	 */
	public TimeOffAccrual getTimeOffAccrual(Long laTimeOffAccrualId);
	
	public List<TimeOffAccrual> getTimeOffAccruals(String principalId, Date asOfDate);

    /**
     * get the count of TimeOffAccrual by given parameters\
     * @param accrualCategory
     * @param effectiveDate
     * @param principalId
     * @param lmAccrualId
     * @return int
     */
	public int getTimeOffAccrualCount(String accrualCategory, Date effectiveDate, String principalId, String lmAccrualId);
}
