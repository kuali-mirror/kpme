package org.kuali.hr.time.missedpunch.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.missedpunch.MissedPunch;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class MissedPunchDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements MissedPunchDao {

    @Override
    public MissedPunch getMissedPunchByRouteHeader(Long headerId) {
        MissedPunch mp = null;

        Criteria root = new Criteria();
        root.addEqualTo("timesheetDocumentId", headerId);
        Query query = QueryFactory.newQuery(MissedPunch.class, root);
        mp = (MissedPunch)this.getPersistenceBrokerTemplate().getObjectByQuery(query);

        return mp;
    }
}
