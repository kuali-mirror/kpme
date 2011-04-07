package org.kuali.hr.time.paycalendar.dao;

import java.sql.Date;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class PayCalendarEntriesDaoSpringOjbImpl extends PersistenceBrokerDaoSupport  implements PayCalendarEntriesDao {
	
	public PayCalendarEntries getPayCalendarEntries(Long payCalendarEntriesId) {
		Criteria currentRecordCriteria = new Criteria();
		currentRecordCriteria.addEqualTo("payCalendarEntriesId", payCalendarEntriesId);

		return (PayCalendarEntries) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(PayCalendarEntries.class, currentRecordCriteria));
	}

	@Override
	public PayCalendarEntries getCurrentPayCalendarEntriesByPayCalendarId(
			Long payCalendarId, Date currentDate) {
		Criteria root = new Criteria();
		Criteria beginDate = new Criteria();
		Criteria endDate = new Criteria();

		beginDate.addEqualToField("payCalendarId", Criteria.PARENT_QUERY_PREFIX + "payCalendarId");
		beginDate.addLessOrEqualThan("beginPeriodDateTime", currentDate);
		ReportQueryByCriteria beginDateSubQuery = QueryFactory.newReportQuery(PayCalendarEntries.class, beginDate);
		beginDateSubQuery.setAttributes(new String[] { "max(beginPeriodDateTime)" });

		endDate.addEqualToField("payCalendarId", Criteria.PARENT_QUERY_PREFIX + "payCalendarId");
		endDate.addGreaterOrEqualThan("endPeriodDateTime", currentDate);
		ReportQueryByCriteria endDateSubQuery = QueryFactory.newReportQuery(PayCalendarEntries.class, endDate);
		endDateSubQuery.setAttributes(new String[] { "min(endPeriodDateTime)" });
		
		root.addEqualTo("payCalendarId", payCalendarId);
		root.addEqualTo("beginPeriodDateTime", beginDateSubQuery);
		root.addEqualTo("endPeriodDateTime", endDateSubQuery);
		
		Query query = QueryFactory.newQuery(PayCalendarEntries.class, root);
		
		PayCalendarEntries pce = (PayCalendarEntries)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
		return pce;
	}

}
