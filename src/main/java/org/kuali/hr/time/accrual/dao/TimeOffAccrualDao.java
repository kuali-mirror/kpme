package org.kuali.hr.time.accrual.dao;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.accrual.TimeOffAccrual;

public interface TimeOffAccrualDao {
	/**
	 * Get Time off accruals by principal id
	 * @param principalId
	 * @return
	 */
	public List<TimeOffAccrual> getTimeOffAccruals(String principalId);
	/**
	 * Get time off accrual by unique id
	 * @param laTimeOffAccrualId
	 * @return
	 */
	public TimeOffAccrual getTimeOffAccrual(Long laTimeOffAccrualId);
	
	/**
	 * Get active accrual categories
	 * @param principalId
	 * @param asOfDate
	 * @return
	 */
	public List<TimeOffAccrual> getActiveTimeOffAccruals (String principalId, Date asOfDate) ;
	
	public int getTimeOffAccrualCount(String accrualCategory, Date effectiveDate, String principalId, String lmAccrualId);

}
