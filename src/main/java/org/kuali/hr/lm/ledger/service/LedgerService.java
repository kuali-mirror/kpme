package org.kuali.hr.lm.ledger.service;

import java.util.Date;
import java.util.List;

import org.kuali.hr.lm.ledger.Ledger;

public interface LedgerService {
    public Ledger getLedger(Long ledgerId);
    public List<Ledger> getLedgersForDocumentId(String documentId);
    public List<Ledger> getLedgers(String principalId, Date beginDate, Date endDate);
}
