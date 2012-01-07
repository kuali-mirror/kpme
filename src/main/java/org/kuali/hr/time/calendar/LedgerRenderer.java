package org.kuali.hr.time.calendar;

import java.math.BigDecimal;

import org.kuali.hr.lm.ledger.Ledger;

public class LedgerRenderer {
    private Ledger ledger;
    private String leaveCode;
    private BigDecimal hours;
    private long ledgerId;
    private String documentId;

    public  LedgerRenderer(Ledger ledger) {
        this.ledger = ledger;
    }

    public Ledger getLedger() {
        return ledger;
    }

    public BigDecimal getHours() {
        return ledger.getHours();
    }

    public String getLeaveCode() {
        return ledger.getLeaveCode();
    }

    public String getLedgerId() {
        return ledger.getLmLedgerId();
    }

    public String getDocumentId() {
        return ledger.getDocumentId();
    }
  
}
