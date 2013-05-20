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
package org.kuali.kpme.tklm.time.timeblock.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.bo.department.Department;
import org.kuali.kpme.core.bo.job.Job;
import org.kuali.kpme.core.lookup.KPMELookupableHelper;
import org.kuali.kpme.core.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlockHistory;
import org.kuali.kpme.tklm.time.timeblock.TimeBlockHistoryDetail;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;

@SuppressWarnings("deprecation")
public class TimeBlockHistoryLookupableHelperServiceImpl extends KPMELookupableHelper {

	private static final long serialVersionUID = -4201048176986460032L;

	private static final String BEGIN_DATE = "beginDate";
	private static final String BEGIN_TIMESTAMP = "beginTimestamp";

	@Override
	public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
		List<TimeBlockHistory> results = new ArrayList<TimeBlockHistory>();

		if (fieldValues.containsKey(BEGIN_DATE)) {
			fieldValues.put(BEGIN_TIMESTAMP, fieldValues.get(BEGIN_DATE));
			fieldValues.remove(BEGIN_DATE);
		}

		List<? extends BusinessObject> searchResults = super.getSearchResults(fieldValues);
		
		for (BusinessObject searchResult : searchResults) {
			TimeBlockHistory timeBlockHistory = (TimeBlockHistory) searchResult;
			results.add(timeBlockHistory);
		}

		results = filterByPrincipalId(results, GlobalVariables.getUserSession().getPrincipalId());
		results = addDetails(results);

		return results;
	}
	
	private List<TimeBlockHistory> filterByPrincipalId(List<TimeBlockHistory> timeBlockHistories, String principalId) {
		List<TimeBlockHistory> results = new ArrayList<TimeBlockHistory>();
		
		for (TimeBlockHistory timeBlockHistory : timeBlockHistories) {
			Job jobObj = HrServiceLocator.getJobService().getJob(timeBlockHistory.getPrincipalId(), timeBlockHistory.getJobNumber(), LocalDate.now(), false);
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
				results.add(timeBlockHistory);
			}
		}
		
		return results;
	}

	private List<TimeBlockHistory> addDetails(List<TimeBlockHistory> timeBlockHistories) {
		List<TimeBlockHistory> results = new ArrayList<TimeBlockHistory>(timeBlockHistories);

		for (TimeBlockHistory timeBlockHistory : timeBlockHistories) {
			List<TimeBlockHistoryDetail> timeBlockHistoryDetails = timeBlockHistory.getTimeBlockHistoryDetails();

			for (TimeBlockHistoryDetail timeBlockHistoryDetail : timeBlockHistoryDetails) {
				if (!timeBlockHistoryDetail.getEarnCode().equalsIgnoreCase(timeBlockHistory.getEarnCode())) {
					TimeBlockHistory newTimeBlockHistory = timeBlockHistory.copy();
					newTimeBlockHistory.setEarnCode(timeBlockHistoryDetail.getEarnCode());
					newTimeBlockHistory.setHours(timeBlockHistoryDetail.getHours());
					newTimeBlockHistory.setAmount(timeBlockHistoryDetail.getAmount());
					results.add(newTimeBlockHistory);
				}
			}
		}

		return results;
	}

}