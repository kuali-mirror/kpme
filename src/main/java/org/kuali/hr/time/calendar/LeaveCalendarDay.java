package org.kuali.hr.time.calendar;

import org.kuali.hr.lm.ledger.Ledger;

import java.util.ArrayList;
import java.util.List;

public class LeaveCalendarDay extends CalendarDay {

    private List<Ledger> ledgers = new ArrayList<Ledger>();
    private List<LedgerRenderer> ledgerRenderers = new ArrayList<LedgerRenderer>();

    public List<LedgerRenderer> getLedgerRenderers() {
        return ledgerRenderers;
    }

    public void setLedgerRenderers(List<LedgerRenderer> ledgerRenderers) {
        this.ledgerRenderers = ledgerRenderers;
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
