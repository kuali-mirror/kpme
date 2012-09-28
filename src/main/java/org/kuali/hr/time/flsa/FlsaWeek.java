/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.flsa;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalTime;

public class FlsaWeek {
	private List<FlsaDay> flsaDays = new ArrayList<FlsaDay>();
	private int flsaDayConstant;
	private LocalTime flsaTime;
	private LocalTime payPeriodBeginTime;
	
	public FlsaWeek() {
		
	}
	
	public FlsaWeek(int dayConstant, LocalTime flsaTime, LocalTime payPeriodBeginTime) {
		this.flsaDayConstant = dayConstant;
		this.flsaTime = flsaTime;
		this.payPeriodBeginTime = payPeriodBeginTime;
	}

	public List<FlsaDay> getFlsaDays() {
		return flsaDays;
	}
	
	public void addFlsaDay(FlsaDay flsaDay) {
		flsaDays.add(flsaDay);
	}

	/**
	 * Check to see if the first week is Full or not.
	 * 
	 * If the first week of a pay period has an FLSA starting time that is before
	 * the "Virtual Day" pay period start time, part of the time required for this
	 * first day will be in the previous pay period even if we have 7 days.
	 * 
	 * @return
	 */
	public boolean isFirstWeekFull() {
		if (flsaDays.size() == 7) {
			return (flsaTime.isBefore(payPeriodBeginTime)) ? false : true;
		} else {
			return false;
		}
	}
	
	public int getFlsaDayConstant() {
		return flsaDayConstant;
	}

	public void setFlsaDayConstant(int flsaDayConstant) {
		this.flsaDayConstant = flsaDayConstant;
	}

	public LocalTime getFlsaTime() {
		return flsaTime;
	}

	public void setFlsaTime(LocalTime flsaTime) {
		this.flsaTime = flsaTime;
	}
}
