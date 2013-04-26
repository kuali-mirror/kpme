package org.kuali.kpme.pm.positionflag.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.pm.positionflag.PositionFlag;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PositionFlagDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionFlagDao {

	@Override
	public PositionFlag getPositionFlagById(String pmPositionFlagId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPositionFlagId", pmPositionFlagId);

        Query query = QueryFactory.newQuery(PositionFlag.class, crit);
        return (PositionFlag) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

}
