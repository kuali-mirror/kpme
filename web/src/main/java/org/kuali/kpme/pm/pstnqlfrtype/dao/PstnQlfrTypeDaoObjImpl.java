package org.kuali.kpme.pm.pstnqlfrtype.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.pm.pstnqlfrtype.PstnQlfrType;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PstnQlfrTypeDaoObjImpl extends PlatformAwareDaoBaseOjb implements PstnQlfrTypeDao {

	@Override
	public PstnQlfrType getPstnQlfrTypeById(String pmPstnQlfrTypeId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPstnQlfrTypeId", pmPstnQlfrTypeId);

        Query query = QueryFactory.newQuery(PstnQlfrType.class, crit);
        return (PstnQlfrType) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

}
