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
package org.kuali.hr.core.bo.leaveplan.service;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.hr.core.bo.calendar.entry.CalendarEntry;
import org.kuali.hr.core.bo.leaveplan.LeavePlan;
import org.springframework.cache.annotation.Cacheable;

public interface LeavePlanService {
    
    /**
     * Fetch LeavePlan by id
     * @param lmLeavePlanId
     * @return
     */
    @Cacheable(value= LeavePlan.CACHE_NAME, key="'lmLeavePlanId=' + #p0")
    public LeavePlan getLeavePlan(String lmLeavePlanId);

    @Cacheable(value= LeavePlan.CACHE_NAME, key="'leavePlan=' + #p0 + '|' + 'asOfDate=' + #p1")
    public LeavePlan getLeavePlan(String leavePlan, LocalDate asOfDate);
    
    public boolean isValidLeavePlan(String leavePlan);
    
    public List<LeavePlan> getAllActiveLeavePlan(String leavePlan, LocalDate asOfDate);
    
    public List<LeavePlan> getAllInActiveLeavePlan(String leavePlan, LocalDate asOfDate);

    List<LeavePlan> getLeavePlans(String leavePlan, String calendarYearStart, String descr, String planningMonths,
                                  LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory);
    
    boolean isFirstCalendarPeriodOfLeavePlan(CalendarEntry calendarEntry, String leavePlan, LocalDate asOfDate);
    
    boolean isLastCalendarPeriodOfLeavePlan(CalendarEntry calendarEntry, String leavePlan, LocalDate asOfDate);
    
    public List<LeavePlan> getLeavePlansNeedsCarryOverScheduled(int thresholdDays, LocalDate asOfDate);

    public DateTime getFirstDayOfLeavePlan(String leavePlan, LocalDate date);
    public DateTime getRolloverDayOfLeavePlan(String leavePlan, LocalDate asOfDate);
}
