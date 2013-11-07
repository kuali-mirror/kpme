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
package org.kuali.kpme.core.api.leaveplan.service;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntryContract;
import org.kuali.kpme.core.api.leaveplan.LeavePlanContract;
import org.springframework.cache.annotation.Cacheable;

public interface LeavePlanService {
    
    /**
     * Fetch LeavePlan by id
     * @param lmLeavePlanId
     * @return
     */
    @Cacheable(value= LeavePlanContract.CACHE_NAME, key="'lmLeavePlanId=' + #p0")
    public LeavePlanContract getLeavePlan(String lmLeavePlanId);

    @Cacheable(value= LeavePlanContract.CACHE_NAME, key="'leavePlan=' + #p0 + '|' + 'asOfDate=' + #p1")
    public LeavePlanContract getLeavePlan(String leavePlan, LocalDate asOfDate);
    
    public boolean isValidLeavePlan(String leavePlan);
    
    public List<? extends LeavePlanContract> getAllActiveLeavePlan(String leavePlan, LocalDate asOfDate);
    
    public List<? extends LeavePlanContract> getAllInActiveLeavePlan(String leavePlan, LocalDate asOfDate);

    List<? extends LeavePlanContract> getLeavePlans(String leavePlan, String calendarYearStart, String descr, String planningMonths,
                                  LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory);
    
    boolean isFirstCalendarPeriodOfLeavePlan(CalendarEntryContract calendarEntry, String leavePlan, LocalDate asOfDate);
    
    boolean isLastCalendarPeriodOfLeavePlan(CalendarEntryContract calendarEntry, String leavePlan, LocalDate asOfDate);
    
    public List<? extends LeavePlanContract> getLeavePlansNeedsCarryOverScheduled(int thresholdDays, LocalDate asOfDate);

    public DateTime getFirstDayOfLeavePlan(String leavePlan, LocalDate date);
    public DateTime getRolloverDayOfLeavePlan(String leavePlan, LocalDate asOfDate);
}
