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
package org.kuali.kpme.core.calendar;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.api.calendar.Calendar;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jjhanso on 4/24/14.
 */
public class CalendarBoTest {
    private static Map<String, Calendar> testCalendars;
    static {
        testCalendars = new HashMap<String, Calendar>();
        Calendar.Builder b = Calendar.Builder.create();
        b.setCalendarDescriptions("Test Description");
        b.setCalendarName("BWS-CAL");
        b.setCalendarTypes("Pay");
        b.setFlsaBeginDay("Sun");
        b.setFlsaBeginLocalTime(new LocalTime(12, 0));
        b.setFlsaBeginDayConstant(DateTimeConstants.SUNDAY);
        b.setHrCalendarId("KPME_TEST_0001");
        b.setVersionNumber(1L);
        b.setObjectId("d9e94e3a-cbb7-11e3-9cd3-51a754ad6a0a");
        testCalendars.put(b.getCalendarName(), b.build());

        b.setCalendarName("LM");
        b.setCalendarTypes("Leave");
        b.setHrCalendarId("KPME_TEST_0002");
        testCalendars.put(b.getCalendarName(), b.build());
    }

    public static Calendar getTestCalendar(String calendarName) {
        return testCalendars.get(calendarName);
    }

    @Test
    public void testCalendarConversions() {
        Calendar immutable = CalendarBoTest.getTestCalendar("BWS-CAL");
        CalendarBo bo = CalendarBo.from(immutable);
        //mockIdentityService
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, CalendarBo.to(bo));
    }
}
