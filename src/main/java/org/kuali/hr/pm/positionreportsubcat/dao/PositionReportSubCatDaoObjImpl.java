package org.kuali.hr.pm.positionreportsubcat.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.pm.positionreportsubcat.PositionReportSubCategory;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PositionReportSubCatDaoObjImpl extends PlatformAwareDaoBaseOjb  implements PositionReportSubCatDao {
	public PositionReportSubCategory getPositionReportSubCatById(
			String pmPositionReportSubCatId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPositionReportSubCatId", pmPositionReportSubCatId);

        Query query = QueryFactory.newQuery(PositionReportSubCategory.class, crit);
        return (PositionReportSubCategory) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

}
