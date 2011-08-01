package org.kuali.hr.time.paycalendar.dao;

import java.util.Collection;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.joda.time.DateTime;
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

    @Override
    public PayCalendarEntries getNextPayCalendarEntriesByPayCalendarId(Long payCalendarId, PayCalendarEntries payCalendarEntries) {
        Criteria root = new Criteria();
        Criteria beginDate = new Criteria();
        Criteria endDate = new Criteria();

        beginDate.addEqualToField("payCalendarId", Criteria.PARENT_QUERY_PREFIX + "payCalendarId");
        beginDate.addGreaterThan("beginPeriodDateTime", payCalendarEntries.getBeginPeriodDateTime());
        ReportQueryByCriteria beginDateSubQuery = QueryFactory.newReportQuery(PayCalendarEntries.class, beginDate);
        beginDateSubQuery.setAttributes(new String[] { "min(beginPeriodDateTime)" });

        endDate.addEqualToField("payCalendarId", Criteria.PARENT_QUERY_PREFIX + "payCalendarId");
        endDate.addGreaterThan("endPeriodDateTime", payCalendarEntries.getEndPeriodDateTime());
        ReportQueryByCriteria endDateSubQuery = QueryFactory.newReportQuery(PayCalendarEntries.class, endDate);
        endDateSubQuery.setAttributes(new String[] { "min(endPeriodDateTime)" });

        root.addEqualTo("payCalendarId", payCalendarId);
        root.addEqualTo("beginPeriodDateTime", beginDateSubQuery);
        root.addEqualTo("endPeriodDateTime", endDateSubQuery);

        Query query = QueryFactory.newQuery(PayCalendarEntries.class, root);

        PayCalendarEntries pce = (PayCalendarEntries)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
        return pce;
    }

    @Override
    public PayCalendarEntries getPreviousPayCalendarEntriesByPayCalendarId(Long payCalendarId, PayCalendarEntries payCalendarEntries) {
        Criteria root = new Criteria();
        Criteria beginDate = new Criteria();
        Criteria endDate = new Criteria();

        beginDate.addEqualToField("payCalendarId", Criteria.PARENT_QUERY_PREFIX + "payCalendarId");
        beginDate.addLessThan("beginPeriodDateTime", payCalendarEntries.getBeginPeriodDateTime());
        ReportQueryByCriteria beginDateSubQuery = QueryFactory.newReportQuery(PayCalendarEntries.class, beginDate);
        beginDateSubQuery.setAttributes(new String[] { "max(beginPeriodDateTime)" });

        endDate.addEqualToField("payCalendarId", Criteria.PARENT_QUERY_PREFIX + "payCalendarId");
        endDate.addLessThan("endPeriodDateTime", payCalendarEntries.getEndPeriodDateTime());
        ReportQueryByCriteria endDateSubQuery = QueryFactory.newReportQuery(PayCalendarEntries.class, endDate);
        endDateSubQuery.setAttributes(new String[] { "max(endPeriodDateTime)" });

        root.addEqualTo("payCalendarId", payCalendarId);
        root.addEqualTo("beginPeriodDateTime", beginDateSubQuery);
        root.addEqualTo("endPeriodDateTime", endDateSubQuery);

        Query query = QueryFactory.newQuery(PayCalendarEntries.class, root);

        PayCalendarEntries pce = (PayCalendarEntries)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
        return pce;
    }

	public List<PayCalendarEntries> getCurrentPayCalendarEntryNeedsScheduled(int thresholdDays, Date asOfDate){
		DateTime current = new DateTime(asOfDate.getTime());
        DateTime windowStart = current.minusDays(thresholdDays);
        DateTime windowEnd = current.plusDays(thresholdDays);

        Criteria root = new Criteria();

        root.addGreaterOrEqualThan("beginPeriodDateTime", windowStart.toDate());
        root.addLessOrEqualThan("beginPeriodDateTime", windowEnd.toDate());

        Query query = QueryFactory.newQuery(PayCalendarEntries.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        List<PayCalendarEntries> pce = new ArrayList<PayCalendarEntries>(c.size());
        pce.addAll(c);

		return pce;
	}

}
