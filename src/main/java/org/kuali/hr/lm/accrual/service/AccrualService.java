package org.kuali.hr.lm.accrual.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.lm.accrual.RateRangeAggregate;

public interface AccrualService {
	public void runAccrual(String principalId);
	public void runAccrual(String principalId, Date startDate, Date endDate);
	public void runAccrual(List<String> principalIds);
	
	
	/**
	 * determine if the given date is at the earn interval
	 * @param aDate
	 * @param earnInterval
	 * @return boolean
	 */
	public boolean isDateAtEarnInterval(java.util.Date aDate, String earnInterval);
	
	/**
	 * build a RateRangeAggregate with given parameters
	 * @param principalId
	 * @param startDate
	 * @param endDate
	 * @return boolean
	 */
	public RateRangeAggregate buildRateRangeAggregate(String principalId, Date startDate, Date endDate);
	
	/**
	 * determine if the employee's future status is changed during the range of given Calendar Entry
	 * @param principalId
	 * @param startDate
	 * @param endDate
	 * @return boolean
	 */
	public boolean isEmpoyeementFutureStatusChanged(String principalId, Date startDate, Date endDate);
	
	/**
	 * calculate future accrual for given principal id
	 * @param principalId
	 * @return
	 */
	public void calculateFutureAccrualUsingPlanningMonth(String principalId);
}
