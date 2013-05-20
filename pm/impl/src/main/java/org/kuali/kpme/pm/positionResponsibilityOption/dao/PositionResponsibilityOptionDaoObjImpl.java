package org.kuali.kpme.pm.positionResponsibilityOption.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.pm.positionResponsibilityOption.PositionResponsibilityOption;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;


public class PositionResponsibilityOptionDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionResponsibilityOptionDao {

	@Override
	public PositionResponsibilityOption getPositionResponsibilityOptionById(
			String prOptionId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("prOptionId", prOptionId);

        Query query = QueryFactory.newQuery(PositionResponsibilityOption.class, crit);
        return (PositionResponsibilityOption) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
	
}
