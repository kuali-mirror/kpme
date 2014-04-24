package org.kuali.kpme.core.leaveplan;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.kuali.kpme.core.api.calendar.Calendar;
import org.kuali.kpme.core.api.leaveplan.LeavePlan;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jjhanso on 4/24/14.
 */
public class LeavePlanBoTest {
    private static Map<String, LeavePlan> testLeavePlans;
    static {
        testLeavePlans = new HashMap<String, LeavePlan>();
        LeavePlan.Builder b = LeavePlan.Builder.create("LEAVE");

        b.setVersionNumber(1L);
        b.setBatchPriorYearCarryOverStartLocalTime(new LocalTime(20, 0));
        b.setPlanningMonths("12");
        b.setLmLeavePlanId("KPME_TEST_001");
        b.setDescr("Leave Plan");
        b.setCalendarYearStart("01/01");
        b.setBatchPriorYearCarryOverStartDate("02/01");
        b.setVersionNumber(1L);
        b.setActive(true);
        b.setId(b.getLmLeavePlanId());
        b.setEffectiveLocalDate(new LocalDate(2010, 1, 1));
        b.setCreateTime(DateTime.now());
        b.setObjectId("ac1c2c7c-cbba-11e3-9cd3-51a754ad6a0a");
        testLeavePlans.put(b.getLeavePlan(), b.build());

        //b.setCalendarName("LM");
        //b.setCalendarTypes("Leave");
        //b.setHrCalendarId("KPME_TEST_0002");
        //b.setObjectId("b7dbaf1a-cbba-11e3-9cd3-51a754ad6a0a");
        //testCalendars.put(b.getCalendarName(), b.build());
    }

    public static LeavePlan getLeavePlan(String leavePlan) {
        return testLeavePlans.get(leavePlan);
    }
}
