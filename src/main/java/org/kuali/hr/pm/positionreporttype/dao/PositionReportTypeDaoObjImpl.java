package org.kuali.hr.pm.positionreporttype.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.job.Job;
import org.kuali.hr.pm.positionreporttype.PositionReportType;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PositionReportTypeDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionReportTypeDao {
	
	@Override
	public PositionReportType getPositionReportTypeById(String pmPositionReportTypeId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPositonReportTypeId", pmPositionReportTypeId);

        Query query = QueryFactory.newQuery(Job.class, crit);
        return (PositionReportType) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

}
