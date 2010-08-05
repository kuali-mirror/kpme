package org.kuali.hr.job.dao;

import java.util.List;

import org.kuali.hr.job.Job;

public interface JobDao {

	public void saveOrUpdate(Job job);

	public void saveOrUpdate(List<Job> jobList);

	public List<Job> getJobs(String principalId);

}
