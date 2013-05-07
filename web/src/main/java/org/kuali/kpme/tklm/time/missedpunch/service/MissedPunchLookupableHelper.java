package org.kuali.kpme.tklm.time.missedpunch.service;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.bo.assignment.Assignment;
import org.kuali.kpme.core.bo.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.bo.department.Department;
import org.kuali.kpme.core.bo.job.Job;
import org.kuali.kpme.core.lookup.KPMELookupableHelper;
import org.kuali.kpme.core.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.time.missedpunch.MissedPunchDocument;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

@SuppressWarnings("deprecation")
public class MissedPunchLookupableHelper extends KPMELookupableHelper {

	private static final long serialVersionUID = 6521192698205632171L;

	@Override
	@SuppressWarnings("rawtypes")
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(businessObject, pkNames);
		
		MissedPunchDocument missedPunch = (MissedPunchDocument) businessObject;
		String documentNumber = missedPunch.getDocumentNumber();
		
		Properties params = new Properties();
		params.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, getBusinessObjectClass().getName());
		params.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);
		params.put("documentNumber", documentNumber);
		AnchorHtmlData viewUrl = new AnchorHtmlData(UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, params), "view");
		viewUrl.setDisplayText("view");
		viewUrl.setTarget(AnchorHtmlData.TARGET_BLANK);
		customActionUrls.add(viewUrl);
		
		return customActionUrls;
	}
	
	@Override
	public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
		List<MissedPunchDocument> results = new ArrayList<MissedPunchDocument>();
		
		List<? extends BusinessObject> searchResults = super.getSearchResults(fieldValues);

		for (BusinessObject searchResult : searchResults) {
			MissedPunchDocument missedPunch = (MissedPunchDocument) searchResult;
			results.add(missedPunch);
		}
		
		results = filterByPrincipalId(results, GlobalVariables.getUserSession().getPrincipalId());
	
		return results;
	}
	
	private List<MissedPunchDocument> filterByPrincipalId(List<MissedPunchDocument> missedPunches, String principalId) {
		List<MissedPunchDocument> results = new ArrayList<MissedPunchDocument>();
		
		for (MissedPunchDocument missedPunch : missedPunches) {
			Assignment assignment = HrServiceLocator.getAssignmentService().getAssignment(new AssignmentDescriptionKey(missedPunch.getAssignment()), LocalDate.now());
			Job jobObj = HrServiceLocator.getJobService().getJob(principalId, assignment.getJobNumber(), assignment.getEffectiveLocalDate());
			String department = jobObj != null ? jobObj.getDept() : null;
			
			Department departmentObj = jobObj != null ? HrServiceLocator.getDepartmentService().getDepartment(department, jobObj.getEffectiveLocalDate()) : null;
			String location = departmentObj != null ? departmentObj.getLocation() : null;
			
			Map<String, String> roleQualification = new HashMap<String, String>();
        	roleQualification.put(KimConstants.AttributeConstants.PRINCIPAL_ID, GlobalVariables.getUserSession().getPrincipalId());
        	roleQualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
        	roleQualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
        	
        	if (!KimApiServiceLocator.getPermissionService().isPermissionDefinedByTemplate(KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>())
    		  || KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(principalId, KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				  KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>(), roleQualification)) {
					results.add(missedPunch);
			}
		}
		
		return results;
	}
	
}
