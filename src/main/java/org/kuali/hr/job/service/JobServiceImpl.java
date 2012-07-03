package org.kuali.hr.job.service;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.job.Job;
import org.kuali.hr.job.dao.JobDao;
import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.paytype.PayType;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;

import java.math.BigDecimal;
import java.util.*;

/**
 * Represents an implementation of {@link JobService}.
 */
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
    @CacheResult(secondsRefreshPeriod = TkConstants.DEFAULT_CACHE_TIME)
    public List<Job> getJobs(String principalId, Date asOfDate) {
        List<Job> jobs = jobDao.getJobs(principalId, asOfDate);

        for (Job job : jobs) {
            PayType payType = TkServiceLocator.getPayTypeService().getPayType(
                    job.getHrPayType(), asOfDate);
            job.setPayTypeObj(payType);
        }

        return jobs;
    }

    @Override
    @CacheResult(secondsRefreshPeriod = TkConstants.DEFAULT_CACHE_TIME)
    public Job getJob(String principalId, Long jobNumber, Date asOfDate) {
        return getJob(principalId, jobNumber, asOfDate, true);
    }

    @CacheResult(secondsRefreshPeriod = TkConstants.DEFAULT_CACHE_TIME)
    public Job getPrimaryJob(String principalId, Date payPeriodEndDate) {
        return jobDao.getPrimaryJob(principalId, payPeriodEndDate);
    }

    @Override
    @CacheResult(secondsRefreshPeriod = TkConstants.DEFAULT_CACHE_TIME)
    public Job getJob(String principalId, Long jobNumber, Date asOfDate,
                      boolean chkDetails) {
        Job job = jobDao.getJob(principalId, jobNumber, asOfDate);
        if (job == null && chkDetails) {
            throw new RuntimeException("No job for principal : " + principalId
                    + " Job Number: " + jobNumber);
        }
        if (chkDetails) {
            String hrPayType = job.getHrPayType();
            if (StringUtils.isBlank(hrPayType)) {
                throw new RuntimeException("No pay type for this job!");
            }
            PayType payType = TkServiceLocator.getPayTypeService().getPayType(
                    hrPayType, asOfDate);
            if (payType == null)
                throw new RuntimeException("No paytypes defined for this job!");
            job.setPayTypeObj(payType);
        }
        return job;
    }

    @Override
    @CacheResult(secondsRefreshPeriod = TkConstants.DEFAULT_CACHE_TIME)
    public List<Job> getActiveJobsForPosition(String positionNbr, Date asOfDate) {
        return jobDao.getActiveJobsForPosition(positionNbr, asOfDate);
    }

    @Override
    @CacheResult(secondsRefreshPeriod = TkConstants.DEFAULT_CACHE_TIME)
    public List<Job> getActiveJobsForPayType(String hrPayType, Date asOfDate) {
        return jobDao.getActiveJobsForPayType(hrPayType, asOfDate);
    }

    @Override
    @CacheResult(secondsRefreshPeriod = TkConstants.DEFAULT_CACHE_TIME)
    public Job getJob(String hrJobId) {
        return jobDao.getJob(hrJobId);
    }

    @Override
    @CacheResult(secondsRefreshPeriod = TkConstants.DEFAULT_CACHE_TIME)
    public Job getMaxJob(String principalId) {
        return jobDao.getMaxJob(principalId);
    }

    @Override
    public List<Job> getJobs(String principalId, String firstName, String lastName, String jobNumber,
                             String dept, String positionNbr, String payType,
                             java.sql.Date fromEffdt, java.sql.Date toEffdt, String active, String showHistory) {

        if (StringUtils.isNotEmpty(firstName) || StringUtils.isNotEmpty(lastName)) {
            Map<String, String> fields = new HashMap<String, String>();
            fields.put("firstName", firstName);
            fields.put("lastName", lastName);
            List<Person> people = KIMServiceLocator.getPersonService().findPeople(fields);

            List<Job> jobs = new ArrayList<Job>();
            for (Person p : people) {
                List<Job> jobsForPerson = jobDao.getJobs(p.getPrincipalId(), jobNumber, dept, positionNbr, payType, fromEffdt, toEffdt, active, showHistory);
                jobs.addAll(jobsForPerson);
            }

            return jobs;
        }

        return jobDao.getJobs(principalId, jobNumber, dept, positionNbr, payType, fromEffdt, toEffdt, active, showHistory);
    }
    
    public int getJobCount(String principalId, Long jobNumber, String dept) {
    	return jobDao.getJobCount(principalId, jobNumber, dept);
    }
    
    @Override
    public List<Job> getActiveLeaveJobs(String principalId, Date asOfDate) {
    	return jobDao.getActiveLeaveJobs(principalId, asOfDate);
    }
    
    @Override
    public BigDecimal getFteSumForJobs(List<Job> jobs) {
    	BigDecimal fteSum = new BigDecimal(0);
    	for(Job aJob : jobs) {
    		fteSum = fteSum.add(aJob.getFte());
    	}
    	return fteSum;
    	
    }
    
    @Override
    public BigDecimal getStandardHoursSumForJobs(List<Job> jobs) {
    	BigDecimal hoursSum = new BigDecimal(0);
    	for(Job aJob : jobs) {
    		hoursSum = hoursSum.add(aJob.getStandardHours());
    	}
    	return hoursSum;
    }
}
