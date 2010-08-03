package org.kuali.hr.job.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.hr.job.Job;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class JobDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements JobDao {
	private static final Logger LOG = Logger.getLogger(JobDaoSpringOjbImpl.class);

	public void saveOrUpdate(JobDaoSpringOjbImpl job) {
		this.getPersistenceBrokerTemplate().store(job);
	}

	public void saveOrUpdate(List<Job> jobList) {
		if (jobList != null) {
			for (Job job : jobList) {
				this.getPersistenceBrokerTemplate().store(job);
			}
		}
	}
}
