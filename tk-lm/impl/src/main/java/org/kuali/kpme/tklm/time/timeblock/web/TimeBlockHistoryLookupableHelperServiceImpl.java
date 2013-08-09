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

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.block.CalendarBlock;
import org.kuali.kpme.core.department.Department;
import org.kuali.kpme.core.job.Job;
import org.kuali.kpme.core.lookup.KPMELookupableHelper;
import org.kuali.kpme.core.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.common.TkConstants;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.block.LeaveBlockHistory;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.timeblock.TimeBlockHistory;
import org.kuali.kpme.tklm.time.timeblock.TimeBlockHistoryDetail;
import org.kuali.kpme.tklm.time.timehourdetail.TimeHourDetail;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;

@SuppressWarnings("deprecation")
public class TimeBlockHistoryLookupableHelperServiceImpl extends KPMELookupableHelper {

	private static final long serialVersionUID = -4201048176986460032L;

	private static final String BEGIN_DATE = "beginDate";
	private static final String BEGIN_TIMESTAMP = "beginTimestamp";
	private static final String DOC_ID = "documentId";

	@Override
	public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
		List<TimeBlockHistory> results = new ArrayList<TimeBlockHistory>();

		if (fieldValues.containsKey(BEGIN_DATE)) {
			fieldValues.put(BEGIN_TIMESTAMP, fieldValues.get(BEGIN_DATE));
			fieldValues.remove(BEGIN_DATE);
		}

		@SuppressWarnings("unchecked")
		String documentId = null;
		if(StringUtils.isNotBlank(fieldValues.get(DOC_ID))) {
			documentId = fieldValues.get(DOC_ID);
		}
		String principalId = null;
		if(StringUtils.isNotBlank(fieldValues.get("principalId"))) {
			principalId = fieldValues.get("principalId");
		}
		String userPrincipalId = null;
		if(StringUtils.isNotBlank(fieldValues.get("userPrincipalId"))) {
			userPrincipalId = fieldValues.get("userPrincipalId");
		}
		LocalDate fromDate = null;
		LocalDate toDate = null;
		if(StringUtils.isNotBlank(fieldValues.get(BEGIN_TIMESTAMP))) {
			String fromDateString = fieldValues.get(BEGIN_TIMESTAMP).substring(0, 10);
			if(fieldValues.get(BEGIN_TIMESTAMP).length() > 10) {
				String toDateString = fieldValues.get(BEGIN_TIMESTAMP).substring(12,22);
				toDate = TKUtils.formatDateString(toDateString);
			}
			fromDate = TKUtils.formatDateString(fromDateString);
		}
		
		LocalDate modifiedFromDate = null;
		LocalDate modifiedToDate = null;
		if(StringUtils.isNotBlank(fieldValues.get("timestamp"))) {
			String fromDateString = fieldValues.get("timestamp").substring(0, 10);
			if(fieldValues.get("timestamp").length() > 10) {
				String toDateString = fieldValues.get("timestamp").substring(12,22);
				modifiedToDate = TKUtils.formatDateString(toDateString);
			}
			modifiedFromDate = TKUtils.formatDateString(fromDateString);
		}

		//Could also simply use super.getSearchResults for an initial object list, then invoke LeaveBlockService with the relevant query params.
		List<CalendarBlock> calendarBlockList = HrServiceLocator.getCalendarBlockService().getCalendarBlocksForTimeBlockLookup(documentId, principalId, userPrincipalId, fromDate, toDate);
		List<TimeBlock> objectList = new ArrayList<TimeBlock>();//(List<TimeBlock>) super.getSearchResults(form, fieldValues, unbounded);
		for(CalendarBlock cBlock : calendarBlockList) {
			if(StringUtils.equals(cBlock.getConcreteBlockType(),"Time")) {
				List<TimeBlockHistory> histories = TkServiceLocator.getTimeBlockHistoryService().getTimeBlockHistoryByTkTimeBlockId(cBlock.getConcreteBlockId());
				for(TimeBlockHistory history : histories) {
					boolean addedToResults = false;
					if(modifiedFromDate != null && modifiedToDate != null) {
						if(history.getTimestamp().compareTo(modifiedFromDate.toDate()) >= 0
								&& history.getTimestamp().compareTo(modifiedToDate.toDate()) <= 0) {
							addedToResults = results.add(history);
						}
					}
					else if(modifiedFromDate != null) {
						if(history.getTimestamp().compareTo(modifiedFromDate.toDate()) >= 0) {
							addedToResults = results.add(history);
						}
					}
					else if(modifiedToDate != null) {
						if(history.getTimestamp().compareTo(modifiedToDate.toDate()) <= 0) {
							addedToResults = results.add(history);
						}
					}
					else {
						addedToResults = results.add(history);
					}
					if(addedToResults) {
						results = addDetails(results);
					}
				}
			}
			else if(StringUtils.equals(cBlock.getConcreteBlockType(), "Leave")) {
				List<LeaveBlockHistory> histories = LmServiceLocator.getLeaveBlockHistoryService().getLeaveBlockHistoryByLmLeaveBlockId(cBlock.getConcreteBlockId());
				for(LeaveBlockHistory history : histories) {
					boolean addToHistory = false;
					if(modifiedFromDate != null && modifiedToDate != null) {
						if(history.getTimestamp().compareTo(modifiedFromDate.toDate()) >= 0
								&& history.getTimestamp().compareTo(modifiedToDate.toDate()) <= 0) {
							addToHistory = true;
						}
					}
					else if(modifiedFromDate != null) {
						if(history.getTimestamp().compareTo(modifiedFromDate.toDate()) >= 0) {
							addToHistory = true;
						}
					}
					else if(modifiedToDate != null) {
						if(history.getTimestamp().compareTo(modifiedToDate.toDate()) <= 0) {
							addToHistory = true;
						}
					}
					else {
						addToHistory = true;
					}
					if(addToHistory) {
						TimeBlockHistory tBlock = new TimeBlockHistory();
						tBlock.setAmount(history.getAmount());
						tBlock.setHours(history.getHours());
						tBlock.setJobNumber(history.getJobNumber());
						tBlock.setEarnCode(history.getEarnCode());
						tBlock.setPrincipalId(history.getPrincipalId());
						tBlock.setUserPrincipalId(history.getPrincipalIdModified());
						tBlock.setWorkArea(history.getWorkArea());
						tBlock.setTask(history.getTask());
						tBlock.setOvertimePref(history.getOvertimePref());
						tBlock.setLunchDeleted(history.getLunchDeleted());
						tBlock.setDocumentId(history.getDocumentId());
						tBlock.setBeginDate(history.getLeaveDate());
						tBlock.setEndDate(history.getLeaveDate());
						tBlock.setTimeHourDetails(new ArrayList<TimeHourDetail>());
						tBlock.setTimestampModified(history.getTimestamp());
						tBlock.setActionHistory(TkConstants.ACTION_HISTORY_CODES.get(history.getAction()));
						tBlock.setClockLogCreated(false);
						tBlock.setTkTimeBlockId(cBlock.getConcreteBlockId());
						TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(history.getDocumentId());
						tBlock.setTimesheetDocumentHeader(tdh);
						results.add(tBlock);
					}
				}
			}
		}
		
/*		List<? extends BusinessObject> searchResults = super.getSearchResults(fieldValues);
		
		
		for (BusinessObject searchResult : searchResults) {
			TimeBlockHistory timeBlockHistory = (TimeBlockHistory) searchResult;
			results.add(timeBlockHistory);
		}*/

		results = filterByPrincipalId(results, GlobalVariables.getUserSession().getPrincipalId());


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