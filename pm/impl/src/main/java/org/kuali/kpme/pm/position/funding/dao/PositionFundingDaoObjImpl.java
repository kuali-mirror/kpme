package org.kuali.kpme.pm.position.funding.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.pm.position.funding.PositionFunding;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PositionFundingDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionFundingDao {

	@Override
	public List<PositionFunding> getFundingListForPosition(String hrPositionId) {
		List<PositionFunding> fundingList = new ArrayList<PositionFunding>();
		Criteria crit = new Criteria();
        crit.addEqualTo("hrPositionId", hrPositionId);

        Query query = QueryFactory.newQuery(PositionFunding.class, crit);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
        	fundingList.addAll(c);
        }
        return fundingList;
	}

}
