package org.kuali.hr.time.paycalendar.dao;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.codehaus.plexus.util.StringUtils;
import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class PayCalendarDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements PayCalendarDao {

    private static final Logger LOG = Logger.getLogger(PayCalendarDaoSpringOjbImpl.class);

    public void saveOrUpdate(PayCalendar payCalendar) {
        this.getPersistenceBrokerTemplate().store(payCalendar);
    }

    public void saveOrUpdate(List<PayCalendar> payCalendarList) {
        if (payCalendarList != null) {
            for (PayCalendar payCalendar : payCalendarList) {
                this.getPersistenceBrokerTemplate().store(payCalendar);
            }
        }
    }

    public PayCalendar getPayCalendar(String hrPyCalendarId) {
        Criteria currentRecordCriteria = new Criteria();
        currentRecordCriteria.addEqualTo("hrPyCalendarId", hrPyCalendarId);

        return (PayCalendar) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(PayCalendar.class, currentRecordCriteria));
    }

    // Is pay clendar groups alwasy unique?
    public PayCalendar getPayCalendarByGroup(String pyCalendarGroup) {
        Criteria currentRecordCriteria = new Criteria();
        currentRecordCriteria.addEqualTo("pyCalendarGroup", pyCalendarGroup);

        return (PayCalendar) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(PayCalendar.class, currentRecordCriteria));
    }

    public PayCalendarEntries getPreviousPayCalendarEntry(String tkPayCalendarId, Date beginDateCurrentPayCalendar) {
        Criteria payEndDateCriteria = new Criteria();
        payEndDateCriteria.addEqualTo("hr_py_calendar_id", tkPayCalendarId);
        payEndDateCriteria.addLessOrEqualThan("end_period_date", beginDateCurrentPayCalendar);

        return (PayCalendarEntries) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(PayCalendarEntries.class, payEndDateCriteria));

    }

    @Override
    public List<PayCalendar> getPayCalendars(String pyCalendarGroup, String flsaBeginDay, Time flsaBeginTime, String active) {
        Criteria crit = new Criteria();

        if (StringUtils.isNotEmpty(pyCalendarGroup)) {
            crit.addLike("pyCalendarGroup", pyCalendarGroup);
        }

        if (StringUtils.isNotEmpty(flsaBeginDay)) {
            crit.addEqualTo("flsaBeginDay", flsaBeginDay);
        }

        if (flsaBeginTime != null) {
            crit.addEqualTo("flsaBeginTime", flsaBeginTime);
        }

        List<PayCalendar> payCalendars = new ArrayList<PayCalendar>();

        if (org.apache.commons.lang.StringUtils.equals(active, "Y")) {
            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", true);
            crit.addAndCriteria(activeFilter);
        } else if (org.apache.commons.lang.StringUtils.equals(active, "N")) {
            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", false);
            crit.addAndCriteria(activeFilter);
        }

        Query query = QueryFactory.newQuery(PayCalendar.class, crit);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        payCalendars.addAll(c);

        return payCalendars;

    }
    
    public int getPyCalendarGroupCount(String pyCalendarGroup) {
    	Criteria crit = new Criteria();
    	crit.addEqualTo("pyCalendarGroup", pyCalendarGroup);
    	Query query = QueryFactory.newQuery(PayCalendar.class, crit);
      	return this.getPersistenceBrokerTemplate().getCount(query);
    }


}
