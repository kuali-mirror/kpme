package org.kuali.hr.lm.ledger.dao;

import org.apache.log4j.Logger;
import org.kuali.hr.lm.ledger.Ledger;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class LedgerDaoOjbImpl extends PersistenceBrokerDaoSupport implements LedgerDao {

    private static final Logger LOG = Logger.getLogger(LedgerDaoOjbImpl.class);

    @Override
    public Ledger getLedger(long ledgerId) {
        return null;
    }

    private void saveOrUpdate(Ledger ledger) {
        this.getPersistenceBrokerTemplate().store(ledger);
    }
}
