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
package org.kuali.hr.time.base.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TkCommonCalendarForm extends TkForm {
	private static final long serialVersionUID = 1L;
	
	private List<String> calendarYears = new ArrayList<String>();
    private Map<String,String> payPeriodsMap = new HashMap<String,String>();
    
    private String selectedCalendarYear;
    private String selectedPayPeriod;
    private boolean onCurrentPeriod;
    private List<String> warnings;
    
	public List<String> getCalendarYears() {
		return calendarYears;
	}
	public void setCalendarYears(List<String> calendarYears) {
		this.calendarYears = calendarYears;
	}
	
	public String getSelectedCalendarYear() {
		return selectedCalendarYear;
	}
	public void setSelectedCalendarYear(String selectedCalendarYear) {
		this.selectedCalendarYear = selectedCalendarYear;
	}
	public String getSelectedPayPeriod() {
		return selectedPayPeriod;
	}
	public void setSelectedPayPeriod(String selectedPayPeriod) {
		this.selectedPayPeriod = selectedPayPeriod;
	}
	public Map<String, String> getPayPeriodsMap() {
		return payPeriodsMap;
	}
	public void setPayPeriodsMap(Map<String, String> payPeriodsMap) {
		this.payPeriodsMap = payPeriodsMap;
	}
	public boolean isOnCurrentPeriod() {
		return onCurrentPeriod;
	}
	public void setOnCurrentPeriod(boolean onCurrentPeriod) {
		this.onCurrentPeriod = onCurrentPeriod;
	}
	
    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }
}
