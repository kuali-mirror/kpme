package org.kuali.hr.lm.ledger.service;

import org.apache.log4j.Logger;
import org.kuali.hr.lm.ledger.Ledger;
import org.kuali.hr.lm.ledger.dao.LedgerDao;

public class LedgerServiceImpl implements LedgerService {

    private static final Logger LOG = Logger.getLogger(LedgerServiceImpl.class);

    private LedgerDao ledgerDao;

    @Override
    public Ledger getLedger(long ledgerId) {
        return ledgerDao.getLedger(ledgerId);
    }

    public LedgerDao getLedgerDao() {
        return ledgerDao;
    }

    public void setLedgerDao(LedgerDao ledgerDao) {
        this.ledgerDao = ledgerDao;
    }
}
