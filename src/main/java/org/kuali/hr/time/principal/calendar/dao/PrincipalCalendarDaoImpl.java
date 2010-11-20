package org.kuali.hr.time.principal.calendar.dao;

import java.sql.Date;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.principal.calendar.PrincipalCalendar;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class PrincipalCalendarDaoImpl extends PersistenceBrokerDaoSupport implements PrincipalCalendarDao {

	@Override
	public PrincipalCalendar getPrincipalCalendar(String principalId,
			Date asOfDate) {
		PrincipalCalendar pc = null;

		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();

		// OJB's awesome sub query setup part 1
		effdt.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(PrincipalCalendar.class, effdt);
		effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

		// OJB's awesome sub query setup part 2
		timestamp.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(PrincipalCalendar.class, timestamp);
		timestampSubQuery.setAttributes(new String[] { "max(timestamp)" });

		root.addEqualTo("principalId", principalId);
		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);

		Query query = QueryFactory.newQuery(PrincipalCalendar.class, root);
		Object obj = this.getPersistenceBrokerTemplate().getObjectByQuery(query);

		if (obj != null) {
			pc = (PrincipalCalendar) obj;
		}

		return pc;
	}

	@Override
	public void saveOrUpdate(PrincipalCalendar principalCalendar) {
		this.getPersistenceBrokerTemplate().store(principalCalendar);
		
	}

	@Override
	public void saveOrUpdate(List<PrincipalCalendar> lstPrincipalCalendar) {
		if(lstPrincipalCalendar != null){
			for(PrincipalCalendar principalCal : lstPrincipalCalendar){
				this.getPersistenceBrokerTemplate().store(principalCal);
			}
		}
		
	}

}
