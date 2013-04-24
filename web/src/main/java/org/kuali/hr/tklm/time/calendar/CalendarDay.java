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
package org.kuali.hr.tklm.time.calendar;


public abstract class CalendarDay {

    private String dayNumberString;
    private int dayNumberDelta;
    private Boolean gray;
    private String dateString;
	private Boolean dayEditable;

    public int getDayNumberDelta() {
        return dayNumberDelta;
    }

    public void setDayNumberDelta(int dayNumberDelta) {
        this.dayNumberDelta = dayNumberDelta;
    }

    public String getDayNumberString() {
        return dayNumberString;
    }

    public void setDayNumberString(String dayNumberString) {
        this.dayNumberString = dayNumberString;
    }

    public Boolean getGray() {
        return gray;
    }

    public void setGray(Boolean gray) {
        this.gray = gray;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

	public Boolean getDayEditable() {
		return dayEditable;
	}

	public void setDayEditable(Boolean dayEditable) {
		this.dayEditable = dayEditable;
	}

}
