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
package org.kuali.kpme.tklm.common;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntryContract;
import org.kuali.kpme.core.web.KPMEAction;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.detail.web.ActionFormUtils;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public abstract class CalendarFormAction extends KPMEAction {
    protected static final ModelObjectUtils.Transformer<TimeBlock, TimeBlock.Builder> toTimeBlockBuilder =
            new ModelObjectUtils.Transformer<TimeBlock, TimeBlock.Builder>() {
                public TimeBlock.Builder transform(TimeBlock input) {
                    return TimeBlock.Builder.create(input);
                };
            };
    protected static final ModelObjectUtils.Transformer<LeaveBlock, LeaveBlock.Builder> toLeaveBlockBuilder =
            new ModelObjectUtils.Transformer<LeaveBlock, LeaveBlock.Builder>() {
                public LeaveBlock.Builder transform(LeaveBlock input) {
                    return LeaveBlock.Builder.create(input);
                };
            };


    protected void setCalendarFields(HttpServletRequest request, CalendarForm calendarForm) {
		Set<String> calendarYears = new TreeSet<String>(Collections.reverseOrder());
		List<CalendarEntryContract> calendarEntries = getCalendarEntries(calendarForm.getCalendarEntry());
		
	    for (CalendarEntryContract calendarEntry : calendarEntries) {
	    	String calendarEntryYear = calendarEntry.getBeginPeriodFullDateTime().toString("yyyy");
	    	calendarYears.add(calendarEntryYear);
	    }
	    
        String currentCalendarYear = calendarForm.getCalendarEntry().getBeginPeriodFullDateTime().toString("yyyy");
        String selectedCalendarYear = StringUtils.isNotBlank(calendarForm.getSelectedCalendarYear()) ? calendarForm.getSelectedCalendarYear() : currentCalendarYear;
	    
	    calendarForm.setCalendarYears(new ArrayList<String>(calendarYears));
	    calendarForm.setPayPeriodsMap(ActionFormUtils.getPayPeriodsMap(ActionFormUtils.getAllCalendarEntriesForYear(calendarEntries, selectedCalendarYear), null));
	    
	    calendarForm.setSelectedCalendarYear(selectedCalendarYear);
	    calendarForm.setSelectedPayPeriod(calendarForm.getCalendarEntry().getHrCalendarEntryId());
	}
    
    protected abstract List<CalendarEntryContract> getCalendarEntries(CalendarEntryContract currentCalendarEntry);

}
