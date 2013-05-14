package org.kuali.kpme.pm.pstnqlfrtype.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

	@Override
	public List<PstnQlfrType> getAllActivePstnQlfrTypes() {
		List<PstnQlfrType> aList = new ArrayList<PstnQlfrType>();
		Criteria root = new Criteria();
		root.addEqualTo("active", true);
		Query query = QueryFactory.newQuery(PstnQlfrType.class, root);
		
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			aList.addAll(c);
		
		return aList;
	}

}
