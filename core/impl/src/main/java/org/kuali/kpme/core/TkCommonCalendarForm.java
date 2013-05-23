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
package org.kuali.kpme.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.kuali.kpme.core.bo.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.web.KPMEForm;

public class TkCommonCalendarForm extends KPMEForm {

	private static final long serialVersionUID = 7437602046032470340L;
	
    private String prevDocumentId;
    private String nextDocumentId;
    
    private String hrCalendarEntryId;
	
	private List<String> calendarYears = new ArrayList<String>();
    private Map<String,String> payPeriodsMap = new HashMap<String,String>();
    
    private CalendarEntry calendarEntry;
    
    private String selectedCalendarYear;
    private String selectedPayPeriod;

    private List<String> errorMessages = new ArrayList<String>();
    // Messages like: "you might lose leave if you don't act." or "you're over the limit - use / transfer / payout leave or risk forfeiting."  i.e. just warns of an upcoming consequence
    private List<String> warningMessages = new ArrayList<String>();
    // Messages like: "leave was forfeted on this calendar" i.e. reports what happened or presents additional info to user.
    private List<String> infoMessages = new ArrayList<String>();
    // Messages like: "must approve transfer / payout doc ( or take other action ) before this calendar can be approved / submitted." i.e.: messages that informs about a required action.
    private List<String> actionMessages = new ArrayList<String>();

    public String getNextDocumentId() {
        return nextDocumentId;
    }

    public void setNextDocumentId(String nextDocumentId) {
        this.nextDocumentId = nextDocumentId;
    }

    public String getPrevDocumentId() {
        return prevDocumentId;
    }

    public void setPrevDocumentId(String prevDocumentId) {
        this.prevDocumentId = prevDocumentId;
    }
    
	public String getHrCalendarEntryId() {
		return hrCalendarEntryId;
	}

	public void setHrCalendarEntryId(String hrCalendarEntryId) {
		this.hrCalendarEntryId = hrCalendarEntryId;
	}
    
	public List<String> getCalendarYears() {
		return calendarYears;
	}
	
	public void setCalendarYears(List<String> calendarYears) {
		this.calendarYears = calendarYears;
	}
	
	public Map<String, String> getPayPeriodsMap() {
		return payPeriodsMap;
	}
	
	public void setPayPeriodsMap(Map<String, String> payPeriodsMap) {
		this.payPeriodsMap = payPeriodsMap;
	}
	
    public CalendarEntry getCalendarEntry() {
        return calendarEntry;
    }

    public void setCalendarEntry(CalendarEntry calendarEntry) {
        this.calendarEntry = calendarEntry;
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

	public boolean isOnCurrentPeriod() {
		boolean isOnCurrentPeriod = false;
		
		if (getCalendarEntry() != null) {
			DateTime beginPeriodDateTime = getCalendarEntry().getBeginPeriodFullDateTime();
			DateTime endPeriodDateTime = getCalendarEntry().getEndPeriodFullDateTime();
			isOnCurrentPeriod = (beginPeriodDateTime.isEqualNow() || beginPeriodDateTime.isBeforeNow()) && endPeriodDateTime.isAfterNow();
		}
		
		return isOnCurrentPeriod;
	}

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

}