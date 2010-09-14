package org.kuali.hr.job.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.job.Job;
import org.kuali.hr.job.dao.JobDao;
import org.kuali.hr.time.assignment.service.AssignmentService;
import org.kuali.hr.time.dept.earncode.service.DepartmentEarnCodeService;
import org.kuali.hr.time.paytype.PayType;
import org.kuali.hr.time.paytype.service.PayTypeService;

public class JobServiceImpl implements JobService {

	private JobDao jobDao;
	private AssignmentService assignmentService;
	private DepartmentEarnCodeService deptEarnCodeService;
	private PayTypeService payTypeService;

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
	public List<Job> getJobs(String principalId, Date payPeriodEndDate) {
		List<Job> jobs = jobDao.getJobs(principalId, payPeriodEndDate);

		// Add the child objects
		for (Job job : jobs) {
			// Add Assignments
			job.setAssignments(assignmentService.getAssignmentsByJobNumber(job.getJobNumber(), principalId, payPeriodEndDate));
			// Pay Type
			PayType payType = payTypeService.getPayType(job.getHrPayType(), payPeriodEndDate);
			job.setPayType(payType);
		}

		return jobs;
	}

	public void setAssignmentService(AssignmentService assignmentService) {
		this.assignmentService = assignmentService;
	}

	public void setDeptEarnCodeService(DepartmentEarnCodeService earnCodeService) {
		this.deptEarnCodeService = earnCodeService;
	}

	public void setPayTypeService(PayTypeService payTypeService) {
		this.payTypeService = payTypeService;
	}

}
