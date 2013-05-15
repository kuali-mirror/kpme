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
package org.kuali.kpme.tklm.leave.accrual.service;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.kuali.kpme.tklm.leave.accrual.RateRangeAggregate;

public interface AccrualService {
	public void runAccrual(String principalId);
	
	/*
	 * run Accrual for the given principal id and dates. 
	 * If recordRanData is true, record the timestamp of this run in database 
	 */
	public void runAccrual(String principalId, DateTime startDate, DateTime endDate, boolean recordRanData);
	public void runAccrual(String principalId, DateTime startDate, DateTime endDate, boolean recordRanData, String runAsPrincipalId);
	public void runAccrual(List<String> principalIds);
	
	
	/**
	 * determine if the given date is at the earn interval
	 * @param aDate
	 * @param earnInterval
	 * @return boolean
	 */
	public boolean isDateAtEarnInterval(LocalDate aDate, String earnInterval);
	
	/**
	 * build a RateRangeAggregate with given parameters
	 * @param principalId
	 * @param startDate
	 * @param endDate
	 * @return boolean
	 */
	public RateRangeAggregate buildRateRangeAggregate(String principalId, DateTime startDate, DateTime endDate);
	
	/**
	 * determine if the employee's future status is changed during the range of given Calendar Entry
	 * @param principalId
	 * @param startDate
	 * @param endDate
	 * @return boolean
	 */
	public boolean isEmpoyeementFutureStatusChanged(String principalId, DateTime startDate, DateTime endDate);
	
	/**
	 * calculate future accrual for given principal id
	 * @param principalId
	 * @param asOfDate
	 * @return
	 */
	public void calculateFutureAccrualUsingPlanningMonth(String principalId, LocalDate asOfDate);

	/**
	 * get the accrual interval date of the previous accrual period with given parameters
	 * @param earnInterval
	 * @param aDate
	 * @return
	 */
	public DateTime getPreviousAccrualIntervalDate(String earnInterval, DateTime aDate);
	
	/**
	 * get the accrual interval date of the next accrual period with given parameters
	 * @param earnInterval
	 * @param aDate
	 * @return
	 */
	public DateTime getNextAccrualIntervalDate(String earnInterval, DateTime aDate);

	/**
	 * calculate # of work days in an accrual period
	 * @param earnInterval
	 * @param aDate
	 * @return	int
	 */
	public int getWorkDaysInAccrualInterval(String earnInterval, DateTime aDate);
	
	public boolean statusChangedSinceLastRun(String principalId);

    /**
     * Retreives the principal's balance on the current calendar for the given accrual category through the date supplied.
     * @param principalId The id of the principal 
     * @param accrualCategory The accrual category the balance is being requested of
     * @param asOfDate 
     * @return
     * @throws Exception 
     */
	public BigDecimal getAccruedBalanceForPrincipal(String principalId, AccrualCategory accrualCategory, LocalDate asOfDate);
	
	public BigDecimal getApprovedBalanceForPrincipal(String principalId, AccrualCategory accrualCategory, LocalDate asOfDate);
	
}
