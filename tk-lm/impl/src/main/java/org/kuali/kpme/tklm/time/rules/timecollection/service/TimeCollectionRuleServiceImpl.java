/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.kpme.tklm.time.rules.timecollection.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.bo.department.Department;
import org.kuali.kpme.core.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.time.rules.timecollection.TimeCollectionRule;
import org.kuali.kpme.tklm.time.rules.timecollection.dao.TimeCollectionRuleDaoService;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

public class TimeCollectionRuleServiceImpl implements TimeCollectionRuleService {
	private TimeCollectionRuleDaoService timeCollectRuleDao;

    @Override
	public TimeCollectionRule getTimeCollectionRule(String dept, Long workArea, String payType, LocalDate asOfDate){
		return timeCollectRuleDao.getTimeCollectionRule(dept, workArea, payType, asOfDate);
	}

    @Override
	public TimeCollectionRule getTimeCollectionRule(String dept, Long workArea,LocalDate asOfDate){
		return timeCollectRuleDao.getTimeCollectionRule(dept, workArea, asOfDate);
	}

	public TimeCollectionRuleDaoService getTimeCollectRuleDao() {
		return timeCollectRuleDao;
	}

	public void setTimeCollectRuleDao(
			TimeCollectionRuleDaoService timeCollectRuleDao) {
		this.timeCollectRuleDao = timeCollectRuleDao;
	}

	@Override
	public TimeCollectionRule getTimeCollectionRule(String tkTimeCollectionRuleId) {
		return timeCollectRuleDao.getTimeCollectionRule(tkTimeCollectionRuleId);
	}

    @Override
    public List<TimeCollectionRule> getTimeCollectionRules(String userPrincipalId, String dept, String workArea, String payType, String active, String showHistory) {
    	List<TimeCollectionRule> results = new ArrayList<TimeCollectionRule>();
    	
    	Long workAreaToSearch = StringUtils.isEmpty(workArea) ? null : Long.parseLong(workArea);
    	
    	List<TimeCollectionRule> timeCollectionRuleObjs = timeCollectRuleDao.getTimeCollectionRules(dept, workAreaToSearch , payType, active, showHistory);
    
    	for (TimeCollectionRule timeCollectionRuleObj : timeCollectionRuleObjs) {
        	String department = timeCollectionRuleObj.getDept();
        	Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, timeCollectionRuleObj.getEffectiveLocalDate());
        	String location = departmentObj != null ? departmentObj.getLocation() : null;
        	
        	Map<String, String> roleQualification = new HashMap<String, String>();
        	roleQualification.put(KimConstants.AttributeConstants.PRINCIPAL_ID, userPrincipalId);
        	roleQualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
        	roleQualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
        	
        	if (!KimApiServiceLocator.getPermissionService().isPermissionDefinedByTemplate(KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>())
    		  || KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(userPrincipalId, KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				  KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>(), roleQualification)) {
        		results.add(timeCollectionRuleObj);
        	}
    	}
    	
    	return results;
    }
}
