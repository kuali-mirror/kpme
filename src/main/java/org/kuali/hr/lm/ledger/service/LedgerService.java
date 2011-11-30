package org.kuali.hr.lm.ledger.service;

import org.kuali.hr.lm.ledger.Ledger;

import java.util.Date;
import java.util.List;

public interface LedgerService {
    public Ledger getLedger(Long ledgerId);
    public List<Ledger> getLedgersForDocumentId(String documentId);
    public List<Ledger> getLedgers(String principalId, Date beginDate, Date endDate);

    void saveLedgers(List<Ledger> ledgers);

    void saveLedger(Ledger ledger);
}
