/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.tklm.api.leave.timeoff;

import java.math.BigDecimal;
import java.util.Date;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.accrualcategory.AccrualCategoryContract;
import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.api.leaveplan.LeavePlanContract;
import org.kuali.kpme.core.api.location.LocationContract;
import org.kuali.kpme.tklm.api.common.TkConstants;

/**
 * <p>SystemScheduledTimeOffContract interface</p>
 *
 */
public interface SystemScheduledTimeOffContract extends HrBusinessObjectContract {
    public static final String CACHE_NAME = TkConstants.Namespace.NAMESPACE_PREFIX + "SystemScheduledTimeOff";
	/**
	 * The primary key of a SystemScheduledTimeOff entry saved in a database
	 * 
	 * <p>
	 * lmSystemScheduledTimeOffId of a SystemScheduledTimeOff
	 * <p>
	 * 
	 * @return lmSystemScheduledTimeOffId for SystemScheduledTimeOff
	 */
	public String getLmSystemScheduledTimeOffId();

	/**
	 * The LeavePlan name associated with the SystemScheduledTimeOff
	 * 
	 * <p>
	 * leavePlan of a SystemScheduledTimeOff
	 * <p>
	 * 
	 * @return leavePlan for SystemScheduledTimeOff
	 */
	public String getLeavePlan();

	/**
	 * The AccrualCategory name associated with the SystemScheduledTimeOff
	 * 
	 * <p>
	 * accrualCategory of a SystemScheduledTimeOff
	 * <p>
	 * 
	 * @return accrualCategory for SystemScheduledTimeOff
	 */
	public String getAccrualCategory();

	/**
	 * The date the holiday is available to use
	 * 
	 * <p>
	 * accruedDate of a SystemScheduledTimeOff
	 * <p>
	 * 
	 * @return accruedDate for SystemScheduledTimeOff
	 */
	public Date getAccruedDate();
	
	/**
	 * The date the holiday is available to use
	 * 
	 * <p>
	 * accruedDate of a SystemScheduledTimeOff
	 * <p>
	 * 
	 * @return accruedDate in LocalDate format for SystemScheduledTimeOff
	 */
	public LocalDate getAccruedLocalDate();

	/**
	 * The date of the ScheduledTimeOff that is put on the calendar 
	 * 
	 * <p>
	 * scheduledTimeOffDate of a SystemScheduledTimeOff
	 * <p>
	 * 
	 * @return scheduledTimeOffDate in Date format for SystemScheduledTimeOff
	 */
	public Date getScheduledTimeOffDate();

	/**
	 * The date of the ScheduledTimeOff that is put on the calendar 
	 * 
	 * <p>
	 * scheduledTimeOffDate of a SystemScheduledTimeOff
	 * <p>
	 * 
	 * @return scheduledTimeOffDate in LocalDate format for SystemScheduledTimeOff
	 */
	public LocalDate getScheduledTimeOffLocalDate();

	/**
	 * The Location name associated with the SystemScheduledTimeOff
	 * 
	 * <p>
	 * location of a SystemScheduledTimeOff
	 * <p>
	 * 
	 * @return location for SystemScheduledTimeOff
	 */
	public String getLocation();

	/**
	 * The description of the SystemScheduledTimeOff
	 * 
	 * <p>
	 * description of a SystemScheduledTimeOff
	 * <p>
	 * 
	 * @return desc for SystemScheduledTimeOff
	 */
	public String getDescr();

	/**
	 * The amount of leave time taken for the ScheduledTimeOff
	 * 
	 * <p>
	 * amountofTime of a SystemScheduledTimeOff
	 * <p>
	 * 
	 * @return amountofTime for SystemScheduledTimeOff
	 */
	public BigDecimal getAmountofTime();

	/**
	 * Indicate how time can be banked/accrued
	 * 
	 * <p>
	 * unusedTime of a SystemScheduledTimeOff
	 * <p>
	 * 
	 * @return unusedTime for SystemScheduledTimeOff
	 */
	public String getUnusedTime();
	
	/**
	 * The conversion rate to be used to transfer the unused Amount of Time
	 * 
	 * <p>
	 * unusedTime of a SystemScheduledTimeOff
	 * <p>
	 * 
	 * @return unusedTime for SystemScheduledTimeOff
	 */
	public BigDecimal getTransferConversionFactor();

	/**
	 * TODO: Make sure this comment is right
	 * Indicate if the ScheduledTimeOff Date allows employees to get higher rate of pay for working it
	 * 
	 * <p>
	 * premiumHoliday of a SystemScheduledTimeOff
	 * <p>
	 * 
	 * @return Y if the ScheduledTimeOff Date allows employees to get higher rate of pay, N if not
	 */
	public String getPremiumHoliday();

	/**
	 * The LeavePlan object associated with the SystemScheduledTimeOff
	 * 
	 * <p>
	 * leavePlanObject of a SystemScheduledTimeOff
	 * <p>
	 * 
	 * @return leavePlanObj for SystemScheduledTimeOff
	 */
	public LeavePlanContract getLeavePlanObj();

	/**
	 * The AccrualCategory object associated with the SystemScheduledTimeOff
	 * 
	 * <p>
	 * accrualCategoryObj of a SystemScheduledTimeOff
	 * <p>
	 * 
	 * @return accrualCategoryObj for SystemScheduledTimeOff
	 */
	public AccrualCategoryContract getAccrualCategoryObj();

	/**
	 * The EarnCode name associated with the SystemScheduledTimeOff
	 * 
	 * <p>
	 * earnCode of a SystemScheduledTimeOff
	 * <p>
	 * 
	 * @return earnCode for SystemScheduledTimeOff
	 */
	public String getEarnCode();

	/**
	 * The EarnCode name used to transfer the unused time
	 * 
	 * <p>
	 * transfertoEarnCode of a SystemScheduledTimeOff
	 * <p>
	 * 
	 * @return transfertoEarnCode for SystemScheduledTimeOff
	 */
	public String getTransfertoEarnCode();

	/**
	 * The EarnCode object associated with the SystemScheduledTimeOff
	 * 
	 * <p>
	 * earnCodeObject of a SystemScheduledTimeOff
	 * <p>
	 * 
	 * @return earnCodeObject for SystemScheduledTimeOff
	 */
	public EarnCodeContract getEarnCodeObj();
	
	/**
	 * The Location object associated with the SystemScheduledTimeOff
	 * 
	 * <p>
	 * locationObj of a SystemScheduledTimeOff
	 * <p>
	 * 
	 * @return locationObj for SystemScheduledTimeOff
	 */
	public LocationContract getLocationObj();

    public String getPremiumEarnCode();

}
