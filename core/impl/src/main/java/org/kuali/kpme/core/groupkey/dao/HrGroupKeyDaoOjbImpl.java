package org.kuali.kpme.core.groupkey.dao;


import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class HrGroupKeyDaoOjbImpl extends PlatformAwareDaoBaseOjb implements HrGroupKeyDao {
    @Override
    public HrGroupKeyBo getHrGroupKey(String groupKeyCode, LocalDate asOfDate) {
        Criteria root = new Criteria();

        root.addEqualTo("groupKeyCode", groupKeyCode);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(HrGroupKeyBo.class, asOfDate, HrGroupKeyBo.BUSINESS_KEYS, true));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(HrGroupKeyBo.class, HrGroupKeyBo.BUSINESS_KEYS, true));

        Query query = QueryFactory.newQuery(HrGroupKeyBo.class, root);

        HrGroupKeyBo d = (HrGroupKeyBo)this.getPersistenceBrokerTemplate().getObjectByQuery(query);

        return d;
    }
}
