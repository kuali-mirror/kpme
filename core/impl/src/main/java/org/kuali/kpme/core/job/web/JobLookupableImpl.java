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
import org.kuali.kpme.core.api.job.Job;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;
import org.kuali.kpme.core.job.JobBo;
import org.kuali.kpme.core.lookup.KPMELookupableImpl;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

import java.util.*;

public class JobLookupableImpl extends KPMELookupableImpl{
    private static final ModelObjectUtils.Transformer<Job, JobBo> toJobBo =
            new ModelObjectUtils.Transformer<Job, JobBo>() {
                public JobBo transform(Job input) {
                    return JobBo.from(input);
                };
            };

    protected List<JobBo> filterLookupJobs(List<JobBo> rawResults, String userPrincipalId)
    {
        List<JobBo> results = new ArrayList<JobBo>();
        for (JobBo jobObj : rawResults)
        {
            Map<String, String> roleQualification = new HashMap<String, String>();
            roleQualification.put(KimConstants.AttributeConstants.PRINCIPAL_ID, userPrincipalId);
            roleQualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), jobObj.getDept());
            roleQualification.put(KPMERoleMemberAttribute.GROUP_KEY_CODE.getRoleMemberAttributeName(), jobObj.getGroupKeyCode());

            HrGroupKeyBo groupKey = jobObj.getGroupKey();
            if(groupKey != null) {
                roleQualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), groupKey.getLocationId());
                roleQualification.put(KPMERoleMemberAttribute.INSTITUION.getRoleMemberAttributeName(), groupKey.getInstitutionCode());
            }

            if (!KimApiServiceLocator.getPermissionService().isPermissionDefinedByTemplate(KPMENamespace.KPME_WKFLW.getNamespaceCode(),
                    KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>())
                    || KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(userPrincipalId, KPMENamespace.KPME_WKFLW.getNamespaceCode(),
                    KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>(), roleQualification)) {
                results.add(jobObj);
            }
        }
        return results;
    }

    @Override
    protected Collection<?> executeSearch(Map<String, String> searchCriteria, List<String> wildcardAsLiteralSearchCriteria, boolean bounded, Integer searchResultsLimit) {
        String userPrincipalId = GlobalVariables.getUserSession().getPrincipalId();
        List<JobBo> rawSearchResults = new ArrayList<JobBo>();
        
        if (StringUtils.isNotEmpty(searchCriteria.get("firstName")) || StringUtils.isNotEmpty(searchCriteria.get("lastName"))) {
            Map<String, String> fields = new HashMap<String, String>();
            fields.put("firstName", searchCriteria.get("firstName"));
            fields.put("lastName", searchCriteria.get("lastName"));
            List<Person> people = KimApiServiceLocator.getPersonService().findPeople(fields);
            for (Person p : people) {
                Map<String, String> personSearch = new HashMap<String, String>();
                personSearch.putAll(searchCriteria);
                personSearch.put("principalId", p.getPrincipalId());
                personSearch.remove("firstName");
                personSearch.remove("lastName");

                List<JobBo> res = (List<JobBo>)super.executeSearch(personSearch, wildcardAsLiteralSearchCriteria, bounded, searchResultsLimit);
                rawSearchResults.addAll(res);
            }
        } else {
            searchCriteria.remove("firstName");
            searchCriteria.remove("lastName");
            
            rawSearchResults = (List<JobBo>)super.executeSearch(searchCriteria, wildcardAsLiteralSearchCriteria, bounded, searchResultsLimit);
        }

        List<JobBo> filteredResults = filterLookupJobs(rawSearchResults, userPrincipalId);
        return filteredResults;
    }


}
