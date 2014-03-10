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
import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.api.job.Job;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.job.JobContract;
import org.kuali.kpme.core.api.job.service.JobService;
import org.kuali.kpme.core.api.paytype.PayType;
import org.kuali.kpme.core.job.JobBo;
import org.kuali.kpme.core.job.dao.JobDao;
import org.kuali.kpme.core.paytype.PayTypeBo;
import org.kuali.kpme.core.api.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;

/**
 * Represents an implementation of {@link JobService}.
 */
public class JobServiceImpl implements JobService {

	private static final Logger LOG = Logger.getLogger(JobServiceImpl.class);
    private JobDao jobDao;
    private static final ModelObjectUtils.Transformer<JobBo, Job> toJob =
            new ModelObjectUtils.Transformer<JobBo, Job>() {
                public Job transform(JobBo input) {
                    return JobBo.to(input);
                };
            };
    private static final ModelObjectUtils.Transformer<Job, JobBo> toJobBo =
            new ModelObjectUtils.Transformer<Job, JobBo>() {
                public JobBo transform(Job input) {
                    return JobBo.from(input);
                };
            };
    @Override
    public Job saveOrUpdate(Job job) {
        if (job == null) {
            return null;
        }
        JobBo bo = KRADServiceLocator.getBusinessObjectService().save(JobBo.from(job));
        return JobBo.to(bo);
    }

    @Override
    public List<Job> saveOrUpdate(List<Job> jobList) {
        if (CollectionUtils.isEmpty(jobList)) {
            return Collections.emptyList();
        }
        List<JobBo> bos = ModelObjectUtils.transform(jobList, toJobBo);
        bos = (List<JobBo>)KRADServiceLocator.getBusinessObjectService().save(bos);
        return toImmutable(bos);

    }

    public void setJobDao(JobDao jobDao) {
        this.jobDao = jobDao;
    }

    @Override
    public List<Job> getJobs(String principalId, LocalDate asOfDate) {
        List<JobBo> jobs = jobDao.getJobs(principalId, asOfDate);

        for (JobBo job : jobs) {
            PayType payType = HrServiceLocator.getPayTypeService().getPayType(
                    job.getHrPayType(), asOfDate);
            job.setPayTypeObj(PayTypeBo.from(payType));
        }

        return toImmutable(jobs);
    }

    @Override
    public Job getJob(String principalId, Long jobNumber, LocalDate asOfDate) {
        return getJob(principalId, jobNumber, asOfDate, true);
    }

    public Job getPrimaryJob(String principalId, LocalDate payPeriodEndDate) {
        return JobBo.to(jobDao.getPrimaryJob(principalId, payPeriodEndDate));
    }

    @Override
    public Job getJob(String principalId, Long jobNumber, LocalDate asOfDate,
                      boolean chkDetails) {
        JobBo job = jobDao.getJob(principalId, jobNumber, asOfDate);
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
            PayTypeBo payType = PayTypeBo.from(HrServiceLocator.getPayTypeService().getPayType(
                    hrPayType, asOfDate));
            if (payType == null) {
//                throw new RuntimeException("No paytypes defined for this job!");
            	LOG.warn("No paytypes defined for this job!");
            	return null;
            } else {
            	job.setPayTypeObj(payType);
            }
        }
        return JobBo.to(job);
    }

    @Override
    public List<Job> getActiveJobsForPayType(String hrPayType, LocalDate asOfDate) {
        return toImmutable(jobDao.getActiveJobsForPayType(hrPayType, asOfDate));
    }

    @Override
    public Job getJob(String hrJobId) {
        return JobBo.to(jobDao.getJob(hrJobId));
    }

    @Override
    public Job getMaxJob(String principalId) {
        return JobBo.to(jobDao.getMaxJob(principalId));
    }

    @Override
    public List<Job> getJobs(String userPrincipalId, String principalId, String firstName, String lastName, String jobNumber,
                             String dept, String positionNbr, String payType,
                             LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory) {
    	List<JobBo> results = new ArrayList<JobBo>();
    	
    	List<JobBo> jobObjs = new ArrayList<JobBo>();
    	
        if (StringUtils.isNotEmpty(firstName) || StringUtils.isNotEmpty(lastName)) {
            Map<String, String> fields = new HashMap<String, String>();
            fields.put("firstName", firstName);
            fields.put("lastName", lastName);
            List<Person> people = KimApiServiceLocator.getPersonService().findPeople(fields);

            for (Person p : people) {
                List<JobBo> jobsForPerson = jobDao.getJobs(p.getPrincipalId(), jobNumber, dept, positionNbr, payType, fromEffdt, toEffdt, active, showHistory);
                jobObjs.addAll(jobsForPerson);
            }
        } else {
        	jobObjs.addAll(jobDao.getJobs(principalId, jobNumber, dept, positionNbr, payType, fromEffdt, toEffdt, active, showHistory));
        }
        
    	for (JobBo jobObj : jobObjs) {
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
    	
    	return toImmutable(results);
    }
    
    public int getJobCount(String principalId, Long jobNumber, String dept) {
    	return jobDao.getJobCount(principalId, jobNumber, dept);
    }
    
    @Override
    public List<Job> getActiveLeaveJobs(String principalId, LocalDate asOfDate) {
    	return toImmutable(jobDao.getActiveLeaveJobs(principalId, asOfDate));
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
		List<JobBo> lmEligibleJobs = jobDao.getActiveLeaveJobs(principalId, asOfDate);
		for(JobBo job : lmEligibleJobs) {
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
    	return toImmutable(jobDao.getAllActiveLeaveJobs(principalId, asOfDate));
    }
    
    public List<Job> getInactiveLeaveJobs(Long jobNumber, LocalDate endDate) {
    	return toImmutable(jobDao.getInactiveLeaveJobs(jobNumber, endDate));
    }
    
    @Override
    public List<Job> getAllInActiveLeaveJobsInRange(String principalId, LocalDate endDate) {
    	return toImmutable(jobDao.getAllInActiveLeaveJobsInRange(principalId, endDate));
    }
    
    @Override
    public Job getMaxTimestampJob(String principalId) {
    	return JobBo.to(jobDao.getMaxTimestampJob(principalId));
    }
    
    @Override
    public List<String> getPrincipalIdsInPosition(String positionNumber, LocalDate asOfDate) {
        return jobDao.getPrincipalIdsInPosition(positionNumber, asOfDate);
    }

    protected List<Job> toImmutable(List<JobBo> bos) {
        return ModelObjectUtils.transform(bos, toJob);
    }
    
}
