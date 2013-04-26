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
package org.kuali.hr.tklm.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.hr.core.HrForm;

public class TkCommonCalendarForm extends HrForm {
	private static final long serialVersionUID = 1L;
	
	private List<String> calendarYears = new ArrayList<String>();
    private Map<String,String> payPeriodsMap = new HashMap<String,String>();
    
    private String selectedCalendarYear;
    private String selectedPayPeriod;
    private boolean onCurrentPeriod;
//    private List<String> warnings;

    private List<String> errorMessages;
    private List<String> warningMessages = new ArrayList<String>(); ;        // Messages like: "you might lose leave if you don't act." or "you're over the limit - use / transfer / payout leave or risk forfeiting."  i.e. just warns of an upcoming consequence
    private List<String> infoMessages = new ArrayList<String>(); ;           // Messages like: "leave was forfeted on this calendar" i.e. reports what happened or presents additional info to user.
    private List<String> actionMessages = new ArrayList<String>();           // Messages like: "must approve transfer / payout doc ( or take other action ) before this calendar can be approved / submitted." i.e.: messages that informs about a required action.

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
	
//    public List<String> getWarnings() {
//        return warnings;
//    }
//
//    public void setWarnings(List<String> warnings) {
//        this.warnings = warnings;
//    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public List<String> getWarningMessages() {
        return warningMessages;
    }

    public void setWarningMessages(List<String> warningMessages) {
        this.warningMessages = warningMessages;
    }

    public List<String> getInfoMessages() {
        return infoMessages;
    }

    public void setInfoMessages(List<String> infoMessages) {
        this.infoMessages = infoMessages;
    }

    public List<String> getActionMessages() {
        return actionMessages;
    }

    public void setActionMessages(List<String> actionMessages) {
        this.actionMessages = actionMessages;
    }

    //TODO: create a mehtod to get all existing messages on the form. action/info/warning

}
