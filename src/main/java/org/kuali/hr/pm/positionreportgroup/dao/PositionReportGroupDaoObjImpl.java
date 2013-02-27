package org.kuali.hr.pm.positionreportgroup.dao;

import java.sql.Date;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.core.util.OjbSubQueryUtil;
import org.kuali.hr.pm.positionreportgroup.PositionReportGroup;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import com.google.common.collect.ImmutableList;

public class PositionReportGroupDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionReportGroupDao {

	private static final ImmutableList<String> PRG_EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
																		    .add("positionReportGroup")
																		    .build();
	@Override
	public PositionReportGroup getPositionReportGroupById(
			String pmPositionReportGroupId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPositionReportGroupId", pmPositionReportGroupId);

        Query query = QueryFactory.newQuery(PositionReportGroup.class, crit);
        return (PositionReportGroup) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
	public PositionReportGroup getPositionReportGroupByGroupAndDate(
			String positionReportGroup, Date asOfDate) {
		Criteria root = new Criteria();

        root.addEqualTo("positionReportGroup", positionReportGroup); 
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionReportGroup.class, asOfDate, PRG_EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionReportGroup.class, PRG_EQUAL_TO_FIELDS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(PositionReportGroup.class, root);
        Object obj = this.getPersistenceBrokerTemplate().getObjectByQuery(query);

        return (PositionReportGroup) obj;
	}

}
