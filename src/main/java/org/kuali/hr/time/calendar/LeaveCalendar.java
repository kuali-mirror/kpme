package org.kuali.hr.time.calendar;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.joda.time.DateTime;
import org.kuali.hr.lm.ledger.Ledger;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class LeaveCalendar extends CalendarParent {

    public LeaveCalendar(CalendarEntries calendarEntry, String documentId) {
        super(calendarEntry);

        DateTime currDateTime = getBeginDateTime();
        DateTime endDateTime = getEndDateTime();
        DateTime firstDay = getBeginDateTime();

        // Fill in the days if the first day or end day is in the middle of the week
        // Monday = 1; Sunday = 7
        if (currDateTime.getDayOfWeek() > 0) {
            currDateTime = currDateTime.minusDays(currDateTime.getDayOfWeek());
            firstDay = currDateTime;
        }
        if (endDateTime.getDayOfWeek() < 7) {
            endDateTime = endDateTime.plusDays(7 - endDateTime.getDayOfWeek());
        }

        LeaveCalendarWeek leaveCalendarWeek = new LeaveCalendarWeek();

        while (currDateTime.isBefore(endDateTime)) {
            //Create weeks
            LeaveCalendarDay leaveCalendarDay = new LeaveCalendarDay();

            // If the day is not within the current pay period, mark them as read only (setGray)
            if (currDateTime.isBefore(getBeginDateTime()) || currDateTime.isAfter(getEndDateTime())) {
                leaveCalendarDay.setGray(true);
            } else {
                // This is for the div id of the days on the calendar.
                // It creates a day id like day_11/01/2011 which will make day parsing easier in the javascript.
                leaveCalendarDay.setDayNumberDelta(currDateTime.toString(TkConstants.DT_BASIC_DATE_FORMAT));
                Multimap<Date, Ledger> ledgersForDay = ledgerAggregator(documentId);
                // convert DateTime to sql date, since the ledger_date on the ledger is a timeless date
                java.sql.Date ledgerDate = TKUtils.getTimelessDate(currDateTime.toDate());
                leaveCalendarDay.setLedgers(new ArrayList<Ledger>(ledgersForDay.get(ledgerDate)));
            }
            leaveCalendarDay.setDayNumberString(currDateTime.dayOfMonth().getAsShortText());

            leaveCalendarWeek.getDays().add(leaveCalendarDay);
            // cut a week on Sat.
            if (currDateTime.getDayOfWeek() == 6 && currDateTime != firstDay) {
                getWeeks().add(leaveCalendarWeek);
                leaveCalendarWeek = new LeaveCalendarWeek();
            }

            currDateTime = currDateTime.plusDays(1);
        }

        if (!leaveCalendarWeek.getDays().isEmpty()) {
            getWeeks().add(leaveCalendarWeek);
        }
    }

    private Multimap<Date, Ledger> ledgerAggregator(String documentId) {
        List<Ledger> ledgers = TkServiceLocator.getLedgerService().getLedgersForDocumentId(documentId);
        Multimap<Date, Ledger> ledgerAggregrate = HashMultimap.create();
        for (Ledger ledger : ledgers) {
            ledgerAggregrate.put(ledger.getLedgerDate(), ledger);
        }

        return ledgerAggregrate;
    }
}
