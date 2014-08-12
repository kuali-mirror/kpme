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
package org.kuali.kpme.core.job.web;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.api.job.Job;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.job.JobBo;
import org.kuali.kpme.core.lookup.KPMELookupableImpl;
import org.kuali.kpme.core.lookup.KpmeHrGroupKeyedBusinessObjectLookupableImpl;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.lookup.LookupUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.LookupForm;

import java.util.*;

//public class JobLookupableImpl extends KPMELookupableImpl{
public class JobLookupableImpl extends KpmeHrGroupKeyedBusinessObjectLookupableImpl {
    private static final ModelObjectUtils.Transformer<Job, JobBo> toJobBo =
            new ModelObjectUtils.Transformer<Job, JobBo>() {
                public JobBo transform(Job input) {
                    return JobBo.from(input);
                };
            };
/*
	@SuppressWarnings("unchecked")
	@Override
	protected List<?> getSearchResults(LookupForm form, Map<String, String> searchCriteria, boolean unbounded) {
        String userPrincipalId = GlobalVariables.getUserSession().getPrincipalId();

        Integer searchResultsLimit = null;

        Collection<?> rawSearchResults;

        // removed blank search values and decrypt any encrypted search values
        Map<String, String> nonBlankSearchCriteria = processSearchCriteria(form, searchCriteria);

        if (nonBlankSearchCriteria == null) {
            return new ArrayList<Object>();
        }

        if (!unbounded) {
            searchResultsLimit = LookupUtils.getSearchResultsLimit(getDataObjectClass(), form);
        }

        rawSearchResults = getLookupService().findCollectionBySearchHelper(getDataObjectClass(),
                nonBlankSearchCriteria, unbounded, searchResultsLimit);

        if (rawSearchResults == null) {
            rawSearchResults = new ArrayList<Object>();
        } else {
            sortSearchResults(form, (List<?>) rawSearchResults);
        }

        List<AssignmentBo> filteredResults = filterLookupAssignments((List<AssignmentBo>)rawSearchResults, userPrincipalId);

        generateLookupResultsMessages(form, nonBlankSearchCriteria, filteredResults, unbounded);

        return filteredResults;
	}
 */
//    protected List<AssignmentBo> filterLookupAssignments(List<AssignmentBo> rawResults, String userPrincipalId) {

/*
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
        	Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, jobObj.getGroupKeyCode(), jobObj.getEffectiveLocalDate());
            String location = departmentObj != null ? departmentObj.getGroupKey().getLocationId() : null;

        	Map<String, String> roleQualification = new HashMap<String, String>();
        	roleQualification.put(KimConstants.AttributeConstants.PRINCIPAL_ID, userPrincipalId);
            roleQualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
            roleQualification.put(KPMERoleMemberAttribute.GROUP_KEY_CODE.getRoleMemberAttributeName(), jobObj.getGroupKeyCode());
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
 */



    protected List<JobBo> filterLookupJobs(List<JobBo> rawResults, String userPrincipalId)
    {
        List<JobBo> results = new ArrayList<JobBo>();
        for (JobBo jobObj : rawResults)
        {
            String department = jobObj.getDept();
            Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, jobObj.getGroupKeyCode(), jobObj.getEffectiveLocalDate());
            String location = departmentObj != null ? departmentObj.getGroupKey().getLocationId() : null;

            Map<String, String> roleQualification = new HashMap<String, String>();
            roleQualification.put(KimConstants.AttributeConstants.PRINCIPAL_ID, userPrincipalId);
            roleQualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
            roleQualification.put(KPMERoleMemberAttribute.GROUP_KEY_CODE.getRoleMemberAttributeName(), jobObj.getGroupKeyCode());
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


    @SuppressWarnings("unchecked")
    @Override
    public List<?> getSearchResults(LookupForm form, Map<String, String> searchCriteria, boolean unbounded) {
        String userPrincipalId = GlobalVariables.getUserSession().getPrincipalId();

        Integer searchResultsLimit = null;

        List<JobBo> rawSearchResults = new ArrayList<JobBo>();

        // removed blank search values and decrypt any encrypted search values
        Map<String, String> nonBlankSearchCriteria = processSearchCriteria(form, searchCriteria);
        nonBlankSearchCriteria.remove("firstName");
        nonBlankSearchCriteria.remove("lastName");


        if (nonBlankSearchCriteria == null) {
            return new ArrayList<Object>();
        }

        if (!unbounded) {
            searchResultsLimit = LookupUtils.getSearchResultsLimit(getDataObjectClass(), form);
        }

        if (StringUtils.isNotEmpty(searchCriteria.get("firstName")) || StringUtils.isNotEmpty(searchCriteria.get("lastName"))) {
            Map<String, String> fields = new HashMap<String, String>();
            fields.put("firstName", searchCriteria.get("firstName"));
            fields.put("lastName", searchCriteria.get("lastName"));
            List<Person> people = KimApiServiceLocator.getPersonService().findPeople(fields);


            for (Person p : people) {
                Map<String, String> personSearch = new HashMap<String, String>();
                personSearch.putAll(nonBlankSearchCriteria);
                personSearch.put("principalId", p.getPrincipalId());

                List<JobBo> res = (List<JobBo>)getLookupService().findCollectionBySearchHelper(getDataObjectClass(),
                        personSearch, unbounded, searchResultsLimit);

                rawSearchResults.addAll(res);
            }
        }
        else
        {
            rawSearchResults = (List<JobBo>)getLookupService().findCollectionBySearchHelper(getDataObjectClass(),
                    nonBlankSearchCriteria, unbounded, searchResultsLimit);
        }

        if (rawSearchResults == null) {
            rawSearchResults = new ArrayList<JobBo>();
        } else {
            sortSearchResults(form, (List<?>) rawSearchResults);
        }


        List<JobBo> filteredResults = filterLookupJobs(rawSearchResults, userPrincipalId);

        generateLookupResultsMessages(form, nonBlankSearchCriteria, filteredResults, unbounded);

        return filteredResults;
    }


}
