package org.kuali.hr.lm.ledger.service;

import org.joda.time.DateTime;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.lm.ledger.Ledger;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface LedgerService {
    public Ledger getLedger(Long ledgerId);
    public List<Ledger> getLedgersForDocumentId(String documentId);
    public List<Ledger> getLedgers(String principalId, Date beginDate, Date endDate);

    void saveLedgers(List<Ledger> ledgers);

    void saveLedger(Ledger ledger);

    /**
     * The deletion marks the ledger inactive instead of removing the row from the database.
     * @param ledgerId
     */
    void deleteLedger(long ledgerId);

    void addLedgers(DateTime beginDate, DateTime endDate, LeaveCalendarDocument lcd, String leaveCode, BigDecimal hours, String description);
}
