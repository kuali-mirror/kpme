/**
 * Copyright 2004-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.core.job.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.department.DepartmentContract;
import org.kuali.kpme.core.api.job.JobContract;
import org.kuali.kpme.core.api.job.service.JobService;
import org.kuali.kpme.core.job.Job;
import org.kuali.kpme.core.job.dao.JobDao;
import org.kuali.kpme.core.paytype.PayType;
import org.kuali.kpme.core.api.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

/**
 * Represents an implementation of {@link JobService}.
 */
public class JobServiceImpl implements JobService {

	private static final Logger LOG = Logger.getLogger(JobServiceImpl.class);
    private JobDao jobDao;

    @Override
    public void saveOrUpdate(JobContract job) {
        jobDao.saveOrUpdate((Job)job);
    }

    @Override
    public void saveOrUpdate(List<? extends JobContract> jobList) {
        jobDao.saveOrUpdate((List<Job>)jobList);
    }

    public void setJobDao(JobDao jobDao) {
        this.jobDao = jobDao;
    }

    @Override
    public List<Job> getJobs(String principalId, LocalDate asOfDate) {
        List<Job> jobs = jobDao.getJobs(principalId, asOfDate);

        for (Job job : jobs) {
            PayType payType = (PayType) HrServiceLocator.getPayTypeService().getPayType(
                    job.getHrPayType(), asOfDate);
            job.setPayTypeObj(payType);
        }

        return jobs;
    }

    @Override
    public Job getJob(String principalId, Long jobNumber, LocalDate asOfDate) {
        return getJob(principalId, jobNumber, asOfDate, true);
    }

    public Job getPrimaryJob(String principalId, LocalDate payPeriodEndDate) {
        return jobDao.getPrimaryJob(principalId, payPeriodEndDate);
    }

    @Override
    public Job getJob(String principalId, Long jobNumber, LocalDate asOfDate,
                      boolean chkDetails) {
        Job job = jobDao.getJob(principalId, jobNumber, asOfDate);
        if (job == null && chkDetails) {
            return null;
            //throw new RuntimeException("No job for principal : " + principalId
            //        + " Job Number: " + jobNumber);
        }
        if (chkDetails) {
            String hrPayType = job.getHrPayType();
            if (StringUtils.isBlank(hrPayType)) {
//                throw new RuntimeException("No pay type for this job!");
            	LOG.warn("No pay type for this job!");
                return null;
            }
            PayType payType = (PayType) HrServiceLocator.getPayTypeService().getPayType(
                    hrPayType, asOfDate);
            if (payType == null) {
//                throw new RuntimeException("No paytypes defined for this job!");
            	LOG.warn("No paytypes defined for this job!");
            	return null;
            } else {
            	job.setPayTypeObj(payType);
            }
        }
        return job;
    }

    @Override
    public List<Job> getActiveJobsForPayType(String hrPayType, LocalDate asOfDate) {
        return jobDao.getActiveJobsForPayType(hrPayType, asOfDate);
    }

    @Override
    public Job getJob(String hrJobId) {
        return jobDao.getJob(hrJobId);
    }

    @Override
    public Job getMaxJob(String principalId) {
        return jobDao.getMaxJob(principalId);
    }

    @Override
    public List<Job> getJobs(String userPrincipalId, String principalId, String firstName, String lastName, String jobNumber,
                             String dept, String positionNbr, String payType,
                             LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory) {
    	List<Job> results = new ArrayList<Job>();
    	
    	List<Job> jobObjs = new ArrayList<Job>();
    	
        if (StringUtils.isNotEmpty(firstName) || StringUtils.isNotEmpty(lastName)) {
            Map<String, String> fields = new HashMap<String, String>();
            fields.put("firstName", firstName);
            fields.put("lastName", lastName);
            List<Person> people = KimApiServiceLocator.getPersonService().findPeople(fields);

            for (Person p : people) {
                List<Job> jobsForPerson = jobDao.getJobs(p.getPrincipalId(), jobNumber, dept, positionNbr, payType, fromEffdt, toEffdt, active, showHistory);
                jobObjs.addAll(jobsForPerson);
            }
        } else {
        	jobObjs.addAll(jobDao.getJobs(principalId, jobNumber, dept, positionNbr, payType, fromEffdt, toEffdt, active, showHistory));
        }
        
    	for (Job jobObj : jobObjs) {
        	String department = jobObj.getDept();
        	Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, jobObj.getEffectiveLocalDate());
        	String location = departmentObj != null ? departmentObj.getLocation() : null;
        	
        	Map<String, String> roleQualification = new HashMap<String, String>();
        	roleQualification.put(KimConstants.AttributeConstants.PRINCIPAL_ID, userPrincipalId);
        	roleQualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
        	roleQualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
        	
        	if (!KimApiServiceLocator.getPermissionService().isPermissionDefinedByTemplate(KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>())
    		  || KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(userPrincipalId, KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				  KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>(), roleQualification)) {
        		results.add(jobObj);
        	}
    	}
    	
    	return results;
    }
    
    public int getJobCount(String principalId, Long jobNumber, String dept) {
    	return jobDao.getJobCount(principalId, jobNumber, dept);
    }
    
    @Override
    public List<Job> getActiveLeaveJobs(String principalId, LocalDate asOfDate) {
    	return jobDao.getActiveLeaveJobs(principalId, asOfDate);
    }
    
    @Override
    public BigDecimal getFteSumForJobs(List<? extends JobContract> jobs) {
    	BigDecimal fteSum = new BigDecimal(0);
    	for(JobContract aJob : jobs) {
    		fteSum = fteSum.add(aJob.getFte());
    	}
    	return fteSum;
    	
    }
    
	@Override
	public BigDecimal getFteSumForAllActiveLeaveEligibleJobs(String principalId, LocalDate asOfDate) {
		BigDecimal fteSum = new BigDecimal(0);
		List<Job> lmEligibleJobs = jobDao.getActiveLeaveJobs(principalId, asOfDate);
		for(Job job : lmEligibleJobs) {
			fteSum = fteSum.add(job.getFte());
		}
		return fteSum;
	}
    
    @Override
    public BigDecimal getStandardHoursSumForJobs(List<? extends JobContract> jobs) {
    	BigDecimal hoursSum = new BigDecimal(0);
    	for(JobContract aJob : jobs) {
    		hoursSum = hoursSum.add(aJob.getStandardHours());
    	}
    	return hoursSum;
    }
   
    @Override
    public List<Job> getAllActiveLeaveJobs(String principalId, LocalDate asOfDate) {
    	return jobDao.getAllActiveLeaveJobs(principalId, asOfDate);
    }
    
    public List<Job> getInactiveLeaveJobs(Long jobNumber, LocalDate endDate) {
    	return jobDao.getInactiveLeaveJobs(jobNumber, endDate);
    }
    
    @Override
    public List<Job> getAllInActiveLeaveJobsInRange(String principalId, LocalDate endDate) {
    	return jobDao.getAllInActiveLeaveJobsInRange(principalId, endDate);
    }
    
    @Override
    public Job getMaxTimestampJob(String principalId) {
    	return jobDao.getMaxTimestampJob(principalId);
    }
    
    @Override
    public List<String> getPrincipalIdsInPosition(String positionNumber, LocalDate asOfDate) {
        return jobDao.getPrincipalIdsInPosition(positionNumber, asOfDate);
    }
    
}
