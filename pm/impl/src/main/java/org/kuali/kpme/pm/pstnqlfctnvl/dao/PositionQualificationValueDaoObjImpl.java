package org.kuali.kpme.pm.pstnqlfctnvl.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.pm.pstnqlfctnvl.PositionQualificationValue;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PositionQualificationValueDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionQualificationValueDao {

	@Override
	public PositionQualificationValue getPositionQualificationValueByValue(String vlName) {
		Criteria crit = new Criteria();
        crit.addEqualTo("vlName", vlName);

        Query query = QueryFactory.newQuery(PositionQualificationValue.class, crit);
        return (PositionQualificationValue) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
	@Override
	public PositionQualificationValue getPositionQualificationValueById(String id) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPstnQlfctnVlId", id);

        Query query = QueryFactory.newQuery(PositionQualificationValue.class, crit);
        return (PositionQualificationValue) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

}
