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
package org.kuali.hr.lm.leaveplan.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.time.calendar.CalendarEntries;
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
    public LeavePlan getLeavePlan(String leavePlan, Date asOfDate);
    
    public boolean isValidLeavePlan(String leavePlan);
    
    public List<LeavePlan> getAllActiveLeavePlan(String leavePlan, Date asOfDate);
    
    public List<LeavePlan> getAllInActiveLeavePlan(String leavePlan, Date asOfDate);

    List<LeavePlan> getLeavePlans(String leavePlan, String calendarYearStart, String descr, String planningMonths,
                                  Date fromEffdt, Date toEffdt, String active, String showHistory);
    
    boolean isFirstCalendarPeriodOfLeavePlan(CalendarEntries calendarEntry, String leavePlan, Date asOfDate);
    
    boolean isLastCalendarPeriodOfLeavePlan(CalendarEntries calendarEntry, String leavePlan, Date asOfDate);
    
}
