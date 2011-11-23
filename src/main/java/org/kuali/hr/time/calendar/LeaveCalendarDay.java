package org.kuali.hr.time.calendar;

import org.kuali.hr.lm.ledger.Ledger;

public class LeaveCalendarDay extends CalendarDay {

    private Ledger ledger;

    public Ledger getLedger() {
        return ledger;
    }

    public void setLedger(Ledger ledger) {
        this.ledger = ledger;
    }
}
