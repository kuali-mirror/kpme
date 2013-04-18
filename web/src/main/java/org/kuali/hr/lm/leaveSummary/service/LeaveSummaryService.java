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
package org.kuali.hr.lm.leaveSummary.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.hr.lm.leaveSummary.LeaveSummary;
import org.kuali.hr.time.calendar.CalendarEntry;

public interface LeaveSummaryService {
	public LeaveSummary getLeaveSummary(String principalId, CalendarEntry calendarEntry) throws Exception;

	public List<Date> getLeaveSummaryDates(CalendarEntry cal);

    public LeaveSummary getLeaveSummaryAsOfDate(String principalId, LocalDate asOfDate);

    public LeaveSummary getLeaveSummaryAsOfDateForAccrualCategory(String principalId, LocalDate asOfDate, String accrualCategory);

    public LeaveSummary getLeaveSummaryAsOfDateWithoutFuture(String principalId, LocalDate asOfDate);
    
    
    public BigDecimal getLeaveBalanceForAccrCatUpToDate(String principalId, LocalDate startDate, LocalDate endDate, String accrualCategory, LocalDate usageEndDate);
}
