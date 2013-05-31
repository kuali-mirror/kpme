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
package org.kuali.kpme.tklm.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;

public abstract class CalendarApprovalForm extends ApprovalForm {
	
	private static final long serialVersionUID = -173408280988754540L;
	
	public static final String ORDER_BY_PRINCIPAL = "principalName";
    public static final String ORDER_BY_DOCID = "documentId";
    public static final String ORDER_BY_STATUS = "Status";
    public static final String ORDER_BY_WORKAREA = "WorkArea";
    
    private String hrCalendarEntryId;
    
    private String prevHrCalendarEntryId;
    private String nextHrCalendarEntryId;
    
	private Date beginCalendarEntryDate;
	private Date endCalendarEntryDate;
	
    private CalendarEntry calendarEntry;
	
	private List<String> calendarYears = new ArrayList<String>();
    private Map<String,String> payPeriodsMap = new HashMap<String,String>();
    
    private String selectedCalendarYear;
    private String selectedPayPeriod;

    private String outputString;

    private String searchField;
    private String searchTerm;

    private Integer resultSize = 0;
    
	public String getHrCalendarEntryId() {
		return hrCalendarEntryId;
	}

	public void setHrCalendarEntryId(String hrCalendarEntryId) {
		this.hrCalendarEntryId = hrCalendarEntryId;
	}
	
	public String getPrevHrCalendarEntryId() {
		return prevHrCalendarEntryId;
	}

	public void setPrevHrCalendarEntryId(String prevHrCalendarEntryId) {
		this.prevHrCalendarEntryId = prevHrCalendarEntryId;
	}

	public String getNextHrCalendarEntryId() {
		return nextHrCalendarEntryId;
	}

	public void setNextHrCalendarEntryId(String nextHrCalendarEntryId) {
		this.nextHrCalendarEntryId = nextHrCalendarEntryId;
	}

	public Date getBeginCalendarEntryDate() {
		return beginCalendarEntryDate;
	}

	public void setBeginCalendarEntryDate(Date beginCalendarEntryDate) {
		this.beginCalendarEntryDate = beginCalendarEntryDate;
	}

	public Date getEndCalendarEntryDate() {
		return endCalendarEntryDate;
	}

	public void setEndCalendarEntryDate(Date endCalendarEntryDate) {
		this.endCalendarEntryDate = endCalendarEntryDate;
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

    public String getOutputString() {
        return outputString;
    }

    public void setOutputString(String outputString) {
        this.outputString = outputString;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public int getResultSize() {
        return resultSize;
    }

    public void setResultSize(Integer resultSize) {
        this.resultSize = resultSize;
    }

}