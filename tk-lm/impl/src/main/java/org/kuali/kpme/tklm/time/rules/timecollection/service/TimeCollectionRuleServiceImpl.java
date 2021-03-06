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
package org.kuali.kpme.tklm.time.rules.timecollection.service;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.time.rules.timecollection.TimeCollectionRule;
import org.kuali.kpme.tklm.time.rules.timecollection.dao.TimeCollectionRuleDaoService;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeCollectionRuleServiceImpl implements TimeCollectionRuleService {
	private TimeCollectionRuleDaoService timeCollectRuleDao;

    @Override
	public TimeCollectionRule getTimeCollectionRule(String dept, Long workArea, String payType, String groupKeyCode, LocalDate asOfDate){
		return timeCollectRuleDao.getTimeCollectionRule(dept, workArea, payType, groupKeyCode, asOfDate);
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
   
    public List<TimeCollectionRule> getTimeCollectionRules(String userPrincipalId, List <TimeCollectionRule> timeCollectionRuleObjs){
    	List<TimeCollectionRule> results = new ArrayList<TimeCollectionRule>();
    	
    	if ( timeCollectionRuleObjs != null ){
	    	for (TimeCollectionRule timeCollectionRuleObj : timeCollectionRuleObjs) {
	        	String department = timeCollectionRuleObj.getDept(); 
	        	String grpKeyCode = timeCollectionRuleObj.getGroupKeyCode();
	        	Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, grpKeyCode, timeCollectionRuleObj.getEffectiveLocalDate());
	        	String location = departmentObj != null ? departmentObj.getGroupKey().getLocationId() : null;
	        	Map<String, String> permissionDetails = new HashMap<String, String>();
	        	permissionDetails.put(KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME, KRADServiceLocatorWeb.getDocumentDictionaryService().getMaintenanceDocumentTypeName(TimeCollectionRule.class));
	        	Map<String, String> roleQualification = new HashMap<String, String>();
	        	roleQualification.put(KimConstants.AttributeConstants.PRINCIPAL_ID, userPrincipalId);
	        	roleQualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
                roleQualification.put(KPMERoleMemberAttribute.GROUP_KEY_CODE.getRoleMemberAttributeName(), grpKeyCode);
	        	roleQualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
	        	
	        	if (!KimApiServiceLocator.getPermissionService().isPermissionDefinedByTemplate(KPMENamespace.KPME_WKFLW.getNamespaceCode(),
	    				KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), permissionDetails)
	    		  || KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(userPrincipalId, KPMENamespace.KPME_WKFLW.getNamespaceCode(),
	    				  KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), permissionDetails, roleQualification)) {
	        		results.add(timeCollectionRuleObj);
	        	}
	    	}
    	}
    	
    	return results;
    }
}
