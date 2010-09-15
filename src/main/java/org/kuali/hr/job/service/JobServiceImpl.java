package org.kuali.hr.job.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.job.Job;
import org.kuali.hr.job.dao.JobDao;
import org.kuali.hr.time.assignment.service.AssignmentService;
import org.kuali.hr.time.dept.earncode.service.DepartmentEarnCodeService;
import org.kuali.hr.time.paytype.PayType;
import org.kuali.hr.time.paytype.service.PayTypeService;
import org.kuali.hr.time.service.base.TkServiceLocator;

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
	public List<Job> getJobs(String principalId, Date currentDate) {
		List<Job> jobs = jobDao.getJobs(principalId, currentDate);

		for (Job job : jobs) {
			PayType payType = TkServiceLocator.getPayTypeSerivce().getPayType(job.getHrPayType(), currentDate);
			job.setPayType(payType);
		}

		return jobs;
	}

}
