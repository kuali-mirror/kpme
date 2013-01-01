/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.lm.accrual.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import org.kuali.hr.lm.accrual.RateRangeAggregate;

public interface AccrualService {
	public void runAccrual(String principalId);
	
	/*
	 * run Accrual for the given principal id and dates. 
	 * If recordRanData is true, record the timestamp of this run in database 
	 */
	public void runAccrual(String principalId, Date startDate, Date endDate, boolean recordRanData);
	public void runAccrual(String principalId, Date startDate, Date endDate, boolean recordRanData, String runAsPrincipalId);
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
	 * @param asOfDate
	 * @return
	 */
	public void calculateFutureAccrualUsingPlanningMonth(String principalId, Date asOfDate);
	
	/**
	 * determine if minimumPercentage has been reached with given parameters
	 * @param min
	 * @param earnInterval
	 * @param daysInBetween
	 * @param intervalDate
	 * @return boolean, 
	 * the given intervalDate could be the first or the last accrual interval of an employment
	 */
	public boolean minimumPercentageReachedForPayPeriod(BigDecimal min, String earnInterval, int daysInBetween, Date intervalDate);
	
	/**
	 * get the accrual interval date of the previous accrual period with given parameters
	 * @param earnInterval
	 * @param aDate
	 * @return
	 */
	public java.util.Date getPreviousAccrualIntervalDate(String earnInterval, Date aDate);
	
	/**
	 * get the accrual interval date of the next accrual period with given parameters
	 * @param earnInterval
	 * @param aDate
	 * @return
	 */
	public java.util.Date getNextAccrualIntervalDate(String earnInterval, Date aDate);
	
	/**
	 * calculate # of calendar days in an accrual period
	 * @param earnInterval
	 * @param aDate
	 * @return	int
	 */
	public int getDaysInAccrualInterval(String earnInterval, Date aDate);
	/**
	 * get the start date of an accrual period
	 * @param earnInterval
	 * @param aDate
	 * @return	int
	 */
	public java.util.Date getAccrualIntervalStartDate(String earnInterval, Date aDate);
	
	/**
	 * calculate # of work days in an accrual period
	 * @param earnInterval
	 * @param aDate
	 * @return	int
	 */
	public int getWorkDaysInAccrualInterval(String earnInterval, Date aDate);
	
	public boolean statusChangedSinceLastRun(String principalId);
	
}
