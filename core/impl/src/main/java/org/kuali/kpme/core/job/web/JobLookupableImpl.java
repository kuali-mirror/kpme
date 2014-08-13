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
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;
import org.kuali.kpme.core.job.JobBo;
import org.kuali.kpme.core.lookup.KpmeHrGroupKeyedBusinessObjectLookupableImpl;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.MessageMap;
import org.kuali.rice.krad.web.form.LookupForm;

import java.util.*;

public class JobLookupableImpl extends KpmeHrGroupKeyedBusinessObjectLookupableImpl {
   
	private static final long serialVersionUID = 7167634961509244966L;
	
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


    @SuppressWarnings("unchecked")
    @Override
    public List<?> getSearchResults(LookupForm form, Map<String, String> searchCriteria, boolean unbounded) {
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

                List<JobBo> res = (List<JobBo>)super.getSearchResults(form, personSearch, unbounded);
                rawSearchResults.addAll(res);
            }
        } else {
            searchCriteria.remove("firstName");
            searchCriteria.remove("lastName");
            
            rawSearchResults = (List<JobBo>)super.getSearchResults(form, searchCriteria, unbounded);
        }

        List<JobBo> filteredResults = filterLookupJobs(rawSearchResults, userPrincipalId);
        GlobalVariables.setMessageMap(new MessageMap());
        generateLookupResultsMessages(form, searchCriteria, filteredResults, unbounded);
        return filteredResults;
    }


}
