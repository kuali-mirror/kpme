package org.kuali.hr.time.missedpunch.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.missedpunch.MissedPunchDocument;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class MissedPunchDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements MissedPunchDao {

    @Override
    public MissedPunchDocument getMissedPunchByRouteHeader(String headerId) {
    	MissedPunchDocument mp = null;

        Criteria root = new Criteria();
        root.addEqualTo("documentId", headerId);
        Query query = QueryFactory.newQuery(MissedPunchDocument.class, root);
        mp = (MissedPunchDocument)this.getPersistenceBrokerTemplate().getObjectByQuery(query);

        return mp;
    }
}
