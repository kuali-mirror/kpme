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
package org.kuali.kpme.tklm.api.leave.accrual;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntryContract;

/**
 * <p>RateRangeAggregateContract interface</p>
 *
 */
public interface RateRangeAggregateContract {

	/**
	 * The current RateRange of the RateRangeAggregate
	 * 
	 * <p>
	 * currentRate of a RateRangeAggregate
	 * <p>
	 * 
	 * @return rateRanges(0) if currentRate is null, else currentRate
	 */
	public RateRangeContract getRate(DateTime date);
	
	/**
	 * The list of RateRange objects associated with the RateRangeAggregate
	 * 
	 * <p>
	 * rateRanges of a RateRangeAggregate
	 * <p>
	 * 
	 * @return rateRanges for RateRangeAggregate
	 */
	public List<? extends RateRangeContract> getRateRanges();

	/**
	 * The current RateRange of the RateRangeAggregate
	 * 
	 * <p>
	 * currentRate of a RateRangeAggregate
	 * <p>
	 * 
	 * @return currentRate for RateRangeAggregate
	 */
	public RateRangeContract getCurrentRate();
	
	/**
	 * The rateRangeChanged flag of the RateRangeAggregate
	 * 
	 * <p>
	 * rateRangeChanged flag of a RateRangeAggregate
	 * <p>
	 * 
	 * @return Y if the rate range has changed, N if not
	 */
	public boolean isRateRangeChanged();
	
	/**
	 * The RateRange of the RateRangeAggregate on a given date
	 * 
	 * <p>
	 * RateRange of a RateRangeAggregate
	 * <p>
	 * 
	 * @param DateTime object instance to retrieve RateRange for
	 * @return RateRange for RateRangeAggregate on a given date
	 */
	public RateRangeContract getRateOnDate(DateTime date); 

	/**
	 * The map of CalendarEntry objects associated with the RateRangeAggregate
	 * 
	 * <p>
	 * calEntryMap of a RateRange
	 * <p>
	 * 
	 * @return calEntryMap for RateRangeAggregate
	 */
	public Map<String, ? extends List<? extends CalendarEntryContract>> getCalEntryMap();

}
