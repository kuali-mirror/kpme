package org.kuali.hr.pm.pstnqlfctnvl.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.pm.pstnqlfctnvl.PositionQualificationValue;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PositionQualificationValueDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionQualificationValueDao {

	@Override
	public PositionQualificationValue getPositionQualificationValueByValue(String value) {
		Criteria crit = new Criteria();
        crit.addEqualTo("value", value);

        Query query = QueryFactory.newQuery(PositionQualificationValue.class, crit);
        return (PositionQualificationValue) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

}
