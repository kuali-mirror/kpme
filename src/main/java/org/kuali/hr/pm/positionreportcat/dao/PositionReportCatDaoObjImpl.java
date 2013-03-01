package org.kuali.hr.pm.positionreportcat.dao;

import java.sql.Date;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.core.util.OjbSubQueryUtil;
import org.kuali.hr.pm.positionreportcat.PositionReportCategory;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import com.google.common.collect.ImmutableList;

public class PositionReportCatDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionReportCatDao {

	private static final ImmutableList<String> PRG_EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
																		    .add("positionReportCat")
																		    .build();
	@Override
	public PositionReportCategory getPositionReportCatById(
			String pmPositionReportCatId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPositionReportCatId", pmPositionReportCatId);

        Query query = QueryFactory.newQuery(PositionReportCategory.class, crit);
        return (PositionReportCategory) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
	public PositionReportCategory getPositionReportCatByCatAndDate(
			String positionReportCat, Date asOfDate) {
		Criteria root = new Criteria();

        root.addEqualTo("positionReportCat", positionReportCat); 
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionReportCategory.class, asOfDate, PRG_EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionReportCategory.class, PRG_EQUAL_TO_FIELDS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(PositionReportCategory.class, root);
        Object obj = this.getPersistenceBrokerTemplate().getObjectByQuery(query);

        return (PositionReportCategory) obj;
	}

}
