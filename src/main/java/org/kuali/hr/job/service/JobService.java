package org.kuali.hr.job.service;

import java.util.List;

import org.kuali.hr.job.Job;

public interface JobService {

	public void saveOrUpdate(Job job);
	public void saveOrUpdate(List<Job> jobList);
	public List<Job> getJobs(String principalId);
}
