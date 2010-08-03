package org.kuali.hr.job.service;

import java.util.List;

import org.kuali.hr.job.Job;
import org.kuali.hr.job.dao.JobDaoSpringOjbImpl;

public interface JobService {

	public void saveOrUpdate(JobDaoSpringOjbImpl job);
	public void saveOrUpdate(List<Job> jobList);
}
