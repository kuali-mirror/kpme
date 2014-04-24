package org.kuali.kpme.core.calendar;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalTime;
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
        b.setFlsaBeginLocalTime(new LocalTime(0));
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
}
