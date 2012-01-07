package org.kuali.hr.lm.ledger.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.lm.ledger.Ledger;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class LedgerDaoOjbImpl extends PersistenceBrokerDaoSupport implements LedgerDao {

    private static final Logger LOG = Logger.getLogger(LedgerDaoOjbImpl.class);

    @Override
    public Ledger getLedger(Long ledgerId) {
        Criteria root = new Criteria();
        root.addEqualTo("ledgerId", ledgerId);
        root.addEqualTo("active", true);

        Query query = QueryFactory.newQuery(Ledger.class, root);

        return (Ledger) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    @Override
    public void saveOrUpdate(Ledger ledger) {
        this.getPersistenceBrokerTemplate().store(ledger);
    }

    @Override
    public List<Ledger> getLedgersForDocumentId(String documentId) {
        List<Ledger> ledgers = new ArrayList<Ledger>();
        Criteria root = new Criteria();
        root.addEqualTo("documentId", documentId);
        root.addEqualTo("active", true);

        Query query = QueryFactory.newQuery(Ledger.class, root);
        @SuppressWarnings("rawtypes")
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
            ledgers.addAll(c);
        }
        return ledgers;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<Ledger> getLedgers(String principalId, Date beginDate, Date endDate) {
        List<Ledger> ledgers = new ArrayList<Ledger>();
        Criteria root = new Criteria();
        root.addEqualTo("principalId", principalId);
        root.addGreaterOrEqualThan("ledgerDate", beginDate);
        root.addLessOrEqualThan("ledgerDate", endDate);
        root.addEqualTo("active", true);

        Query query = QueryFactory.newQuery(Ledger.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
            ledgers.addAll(c);
        }

        return ledgers;
    }

}
