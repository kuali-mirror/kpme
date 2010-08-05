package org.kuali.hr.job.service;

import java.util.List;

import org.kuali.hr.job.Job;
import org.kuali.hr.job.dao.JobDao;

public class JobServiceImpl implements JobService {

	private JobDao jobDao;

	@Override
	public void saveOrUpdate(Job job) {
		jobDao.saveOrUpdate(job);
	}

	@Override
	public void saveOrUpdate(List<Job> jobList) {
		jobDao.saveOrUpdate(jobList);
	}

	public void setJobDao(JobDao jobDao) {
		this.jobDao = jobDao;
	}

	@Override
	public List<Job> getJobs(String principalId) {
		return jobDao.getJobs(principalId);
	}

}
