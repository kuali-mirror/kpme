package org.kuali.hr.time.calendar;

import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.lm.ledger.Ledger;

public class LeaveCalendarDay extends CalendarDay {

    private List<Ledger> ledgers = new ArrayList<Ledger>();
    private List<LedgerRenderer> ledgerRenderers = new ArrayList<LedgerRenderer>();

    public List<LedgerRenderer> getLedgerRenderers() {
        return ledgerRenderers;
    }

    public List<Ledger> getLedgers() {
        return ledgers;
    }

    public void setLedgers(List<Ledger> ledgers) {
        this.ledgers = ledgers;
        for (Ledger ledger : ledgers) {
            ledgerRenderers.add(new LedgerRenderer(ledger));
        }
    }
}
