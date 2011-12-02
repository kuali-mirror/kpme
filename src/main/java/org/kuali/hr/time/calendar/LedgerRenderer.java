package org.kuali.hr.time.calendar;

import org.kuali.hr.lm.ledger.Ledger;

import java.math.BigDecimal;

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

    public long getLedgerId() {
        return ledger.getLedgerId();
    }

    public String getDocumentId() {
        return ledger.getDocumentId();
    }
  
}
