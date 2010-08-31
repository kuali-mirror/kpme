package org.kuali.hr.job.dao;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendarDates;
import org.kuali.hr.time.paytype.PayType;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class JobDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements JobDao {

	private static final Logger LOG = Logger.getLogger(JobDaoSpringOjbImpl.class);

	public void saveOrUpdate(Job job) {
		this.getPersistenceBrokerTemplate().store(job);
	}

	public void saveOrUpdate(List<Job> jobList) {
		if (jobList != null) {
			for (Job job : jobList) {
				this.getPersistenceBrokerTemplate().store(job);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<Job> getJobs(String principalId) {
		Criteria currentRecordCriteria = new Criteria();
		currentRecordCriteria.addEqualTo("principalId", principalId);

		Criteria effdtJoinCriteria = new Criteria();
		effdtJoinCriteria.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
		effdtJoinCriteria.addEqualToField("effdt", Criteria.PARENT_QUERY_PREFIX + "effdt");
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(Job.class, effdtJoinCriteria);
		effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

		currentRecordCriteria.addEqualTo("effdt", effdtSubQuery);

		Criteria timestampJoinCriteria = new Criteria();
		timestampJoinCriteria.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
		timestampJoinCriteria.addEqualToField("timestamp", Criteria.PARENT_QUERY_PREFIX + "timestamp");
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(Job.class, timestampJoinCriteria);
		timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

		currentRecordCriteria.addEqualTo("timestamp", timestampSubQuery);

		List<Job> jobs = new LinkedList<Job>();
		Collection<Job> c = this.getPersistenceBrokerTemplate().getCollectionByQuery(QueryFactory.newQuery(Job.class, currentRecordCriteria));
		jobs.addAll(c);

		List<Job> principalJobs = new LinkedList<Job>();

		for(Job job : jobs) {
			PayType payType = TkServiceLocator.getPayTypeSerivce().getPayType(job.getPayTypeId());
			PayCalendar payCalendar = TkServiceLocator.getPayCalendarSerivce().getPayCalendarByGroup(payType.getCalendarGroup());
			//List<PayCalendarDates> payCalendarDates = TkServiceLocator.getPayCalendarDatesSerivce().getPayCalendarDates(payCalendar.getPayCalendarId());
			//payCalendar.setPayCalendarDates(payCalendarDates);
			payType.setPayCalendar(payCalendar);
			job.setPayType(payType);

			principalJobs.add(job);
		}

		return principalJobs;
	}
}
