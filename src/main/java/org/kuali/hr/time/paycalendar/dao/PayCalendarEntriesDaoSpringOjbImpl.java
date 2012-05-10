package org.kuali.hr.time.paycalendar.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.joda.time.DateTime;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class PayCalendarEntriesDaoSpringOjbImpl extends PersistenceBrokerDaoSupport  implements PayCalendarEntriesDao {

	public PayCalendarEntries getPayCalendarEntries(String hrPyCalendarEntriesId) {
		Criteria currentRecordCriteria = new Criteria();
		currentRecordCriteria.addEqualTo("hrPyCalendarEntriesId", hrPyCalendarEntriesId);

		return (PayCalendarEntries) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(PayCalendarEntries.class, currentRecordCriteria));
	}

    @Override
    public PayCalendarEntries getPayCalendarEntriesByIdAndPeriodEndDate(String hrPyCalendarId, Date endPeriodDate) {
        Criteria root = new Criteria();
        root.addEqualTo("hrPyCalendarId", hrPyCalendarId);
		root.addEqualTo("endPeriodDateTime", endPeriodDate);

        Query query = QueryFactory.newQuery(PayCalendarEntries.class, root);
        PayCalendarEntries pce = (PayCalendarEntries)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
        return pce;
    }

	@Override
	public PayCalendarEntries getCurrentPayCalendarEntriesByPayCalendarId(
			String hrPyCalendarId, Date currentDate) {
		Criteria root = new Criteria();
		root.addEqualTo("hrPyCalendarId", hrPyCalendarId);
        root.addLessOrEqualThan("beginPeriodDateTime", currentDate);
        root.addGreaterThan("endPeriodDateTime", currentDate);

		Query query = QueryFactory.newQuery(PayCalendarEntries.class, root);

		PayCalendarEntries pce = (PayCalendarEntries)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
		return pce;
	}

    @Override
    public PayCalendarEntries getNextPayCalendarEntriesByPayCalendarId(String hrPyCalendarId, PayCalendarEntries payCalendarEntries) {
        Criteria root = new Criteria();
        Criteria beginDate = new Criteria();
        Criteria endDate = new Criteria();

        beginDate.addEqualToField("hrPyCalendarId", Criteria.PARENT_QUERY_PREFIX + "hrPyCalendarId");
        beginDate.addGreaterThan("beginPeriodDateTime", payCalendarEntries.getBeginPeriodDateTime());
        ReportQueryByCriteria beginDateSubQuery = QueryFactory.newReportQuery(PayCalendarEntries.class, beginDate);
        beginDateSubQuery.setAttributes(new String[] { "min(beginPeriodDateTime)" });

        endDate.addEqualToField("hrPyCalendarId", Criteria.PARENT_QUERY_PREFIX + "hrPyCalendarId");
        endDate.addGreaterThan("endPeriodDateTime", payCalendarEntries.getEndPeriodDateTime());
        ReportQueryByCriteria endDateSubQuery = QueryFactory.newReportQuery(PayCalendarEntries.class, endDate);
        endDateSubQuery.setAttributes(new String[] { "min(endPeriodDateTime)" });

        root.addEqualTo("hrPyCalendarId", hrPyCalendarId);
        root.addEqualTo("beginPeriodDateTime", beginDateSubQuery);
        root.addEqualTo("endPeriodDateTime", endDateSubQuery);

        Query query = QueryFactory.newQuery(PayCalendarEntries.class, root);

        PayCalendarEntries pce = (PayCalendarEntries)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
        return pce;
    }

    @Override
    public PayCalendarEntries getPreviousPayCalendarEntriesByPayCalendarId(String hrPyCalendarId, PayCalendarEntries payCalendarEntries) {
        Criteria root = new Criteria();
        Criteria beginDate = new Criteria();
        Criteria endDate = new Criteria();

        beginDate.addEqualToField("hrPyCalendarId", Criteria.PARENT_QUERY_PREFIX + "hrPyCalendarId");
        beginDate.addLessThan("beginPeriodDateTime", payCalendarEntries.getBeginPeriodDateTime());
        ReportQueryByCriteria beginDateSubQuery = QueryFactory.newReportQuery(PayCalendarEntries.class, beginDate);
        beginDateSubQuery.setAttributes(new String[] { "max(beginPeriodDateTime)" });

        endDate.addEqualToField("hrPyCalendarId", Criteria.PARENT_QUERY_PREFIX + "hrPyCalendarId");
        endDate.addLessThan("endPeriodDateTime", payCalendarEntries.getEndPeriodDateTime());
        ReportQueryByCriteria endDateSubQuery = QueryFactory.newReportQuery(PayCalendarEntries.class, endDate);
        endDateSubQuery.setAttributes(new String[] { "max(endPeriodDateTime)" });

        root.addEqualTo("hrPyCalendarId", hrPyCalendarId);
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
		
	 public PayCalendarEntries getCalendarEntriesByBeginAndEndDate(Date beginPeriodDate, Date endPeriodDate) {
		 Criteria root = new Criteria();
		 root.addEqualTo("beginPeriodDateTime", beginPeriodDate);
		 root.addEqualTo("endPeriodDateTime", endPeriodDate);
		 Query query = QueryFactory.newQuery(PayCalendarEntries.class, root);

		 return (PayCalendarEntries)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	 }

}
