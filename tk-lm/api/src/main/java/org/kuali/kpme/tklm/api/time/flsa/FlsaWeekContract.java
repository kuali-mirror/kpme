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
package org.kuali.kpme.tklm.api.time.flsa;

import java.util.List;

import org.joda.time.LocalTime;


/**
 * <p>FlsaWeekContract interface</p>
 *
 */
public interface FlsaWeekContract {
	
	/**
	 * The list of FlsaDay objects associated with the FlsaWeek
	 * 
	 * <p>
	 * flsaDays of a FlsaWeek
	 * <p>
	 * 
	 * @return flsaDays for FlsaWeek
	 */
	public List<? extends FlsaDayContract> getFlsaDays();

	/**
	 * Indicates if the first week is full or not
	 * 
	 * <p>
	 * If the first week of a pay period has an FLSA starting time that is before
	 * the "Virtual Day" pay period start time, part of the time required for this
	 * first day will be in the previous pay period even if we have 7 days.
	 * <p>
	 * 
	 * @return true if the first week is full, false if not
	 */
	public boolean isFirstWeekFull();
	
	/**
	 * Indicates if the last week is full or not
	 * 
	 * <p>
	 * If the last week of a pay period has an FLSA starting time that is after
	 * the "Virtual Day" pay period start time, part of the time required for this
	 * last day will be in the next pay period even if we have 7 days.
	 * <p>
	 * 
	 * @return true if the last week is full, false if not
	 */
	public boolean isLastWeekFull();
	
	/**
	 * The FlsaDay constant associated with the FlsaWeek
	 * 
	 * <p>
	 * flsaDayConstant of a FlsaWeek
	 * <p>
	 * 
	 * @return flsaDayConstant for FlsaWeek
	 */
	public int getFlsaDayConstant();
	
	/**
	 * The flsaTime associated with the FlsaWeek
	 * 
	 * <p>
	 * flsaTime of a FlsaWeek
	 * <p>
	 * 
	 * @return flsaTime for FlsaWeek
	 */
	public LocalTime getFlsaTime();

}
