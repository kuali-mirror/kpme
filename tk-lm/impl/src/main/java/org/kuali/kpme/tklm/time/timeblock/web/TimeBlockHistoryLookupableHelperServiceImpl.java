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
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.department.Department;
import org.kuali.kpme.core.job.Job;
import org.kuali.kpme.core.lookup.KPMELookupableImpl;
import org.kuali.kpme.core.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.core.util.ValidationUtils;
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
import org.kuali.rice.core.api.search.Range;
import org.kuali.rice.core.api.search.SearchExpressionUtils;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.uif.view.LookupView;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.LookupForm;

public class TimeBlockHistoryLookupableHelperServiceImpl extends KPMELookupableImpl {

	private static final long serialVersionUID = -4201048176986460032L;

	private static final String BEGIN_DATE = "beginDate";
	private static final String BEGIN_TIMESTAMP = "beginTimestamp";
	private static final String DOC_STATUS_ID = "timesheetDocumentHeader.documentStatus";
	private static final String DOC_ID = "documentId";

/*	@Override
	public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {

	}*/
	
	private List<TimeBlockHistory> filterByPrincipalId(List<TimeBlockHistory> timeBlockHistories, String principalId) {
		List<TimeBlockHistory> results = new ArrayList<TimeBlockHistory>();
		
		for (TimeBlockHistory timeBlockHistory : timeBlockHistories) {
			Job jobObj = HrServiceLocator.getJobService().getJob(timeBlockHistory.getPrincipalId(), timeBlockHistory.getJobNumber(), LocalDate.fromDateFields(timeBlockHistory.getBeginDate()), false);
			String department = jobObj != null ? jobObj.getDept() : null;

			Department departmentObj = jobObj != null ? HrServiceLocator.getDepartmentService().getDepartment(department, LocalDate.fromDateFields(timeBlockHistory.getBeginDate())) : null;
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
	
	@Override
	protected String getActionUrlHref(LookupForm lookupForm, Object dataObject,
			String methodToCall, List<String> pkNames) {
		String actionUrlHref = super.getActionUrlHref(lookupForm, dataObject, methodToCall, pkNames);
		TimeBlock tb = null;
		String concreteBlockId = null;
		if(dataObject instanceof TimeBlock) {
			tb = (TimeBlock) dataObject;
			concreteBlockId = tb.getTkTimeBlockId();
		}
		if(concreteBlockId == null) {
			return null;
		}

		return actionUrlHref;
	}

	@Override
	public void initSuppressAction(LookupForm lookupForm) {
		((LookupView) lookupForm.getView()).setSuppressActions(false);
	}

	@Override
	protected List<?> getSearchResults(LookupForm form,
			Map<String, String> searchCriteria, boolean unbounded) {

		if (searchCriteria.containsKey(BEGIN_DATE)) {
			searchCriteria.put(BEGIN_TIMESTAMP, searchCriteria.get(BEGIN_DATE));
			searchCriteria.remove(BEGIN_DATE);
		}

		String documentId = searchCriteria.get(DOC_ID);
		String principalId = searchCriteria.get("principalId");
		String userPrincipalId = searchCriteria.get("userPrincipalId");

		LocalDate fromDate = null;
		LocalDate toDate = null;
		if(StringUtils.isNotBlank(searchCriteria.get(BEGIN_TIMESTAMP))) {
			String fromDateString = TKUtils.getFromDateString(searchCriteria.get(BEGIN_TIMESTAMP));
			String toDateString = TKUtils.getToDateString(searchCriteria.get(BEGIN_TIMESTAMP));
			Range range = SearchExpressionUtils.parseRange(searchCriteria.get(BEGIN_TIMESTAMP));
			boolean invalid = false;
			if(range.getLowerBoundValue() != null && range.getUpperBoundValue() != null) {
				fromDate = TKUtils.formatDateString(fromDateString);
				if(fromDate == null) {
					GlobalVariables.getMessageMap().putError("lookupCriteria[rangeLowerBoundKeyPrefix_beginDate]", "error.invalidLookupDate", range.getLowerBoundValue());
					invalid = true;
				}

				toDate = TKUtils.formatDateString(toDateString);
				if(toDate == null) {
					GlobalVariables.getMessageMap().putError("lookupCriteria[beginDate]", "error.invalidLookupDate", range.getUpperBoundValue());
					invalid = true;
				}
			}
			else if(range.getLowerBoundValue() != null) {
				fromDate = TKUtils.formatDateString(fromDateString);
				if(fromDate == null) {
					GlobalVariables.getMessageMap().putError("lookupCriteria[rangeLowerBoundKeyPrefix_beginDate]", "error.invalidLookupDate", range.getLowerBoundValue());
					invalid = true;
				}
			}
			else if(range.getUpperBoundValue() != null) {
				toDate = TKUtils.formatDateString(toDateString);
				if(toDate == null) {
					GlobalVariables.getMessageMap().putError("lookupCriteria[beginDate]", "error.invalidLookupDate", range.getUpperBoundValue());
					invalid = true;
				}
			}
			if(invalid) {
				return new ArrayList<TimeBlock>();
			}
		}
		LocalDate modifiedFromDate = null;
		LocalDate modifiedToDate = null;
		if(StringUtils.isNotBlank(searchCriteria.get("timestamp"))) {
			String fromDateString = TKUtils.getFromDateString(searchCriteria.get("timestamp"));
			String toDateString = TKUtils.getToDateString(searchCriteria.get("timestamp"));
			Range range = SearchExpressionUtils.parseRange(searchCriteria.get("timestamp"));
			boolean invalid = false;
			if(range.getLowerBoundValue() != null && range.getUpperBoundValue() != null) {
				modifiedFromDate = TKUtils.formatDateString(fromDateString);
				if(modifiedFromDate == null) {
					GlobalVariables.getMessageMap().putError("lookupCriteria[rangeLowerBoundKeyPrefix_beginDate]", "error.invalidLookupDate", range.getLowerBoundValue());
					invalid = true;
				}

				modifiedToDate = TKUtils.formatDateString(toDateString);
				if(modifiedToDate == null) {
					GlobalVariables.getMessageMap().putError("lookupCriteria[beginDate]", "error.invalidLookupDate", range.getUpperBoundValue());
					invalid = true;
				}
			}
			else if(range.getLowerBoundValue() != null) {
				modifiedFromDate = TKUtils.formatDateString(fromDateString);
				if(modifiedFromDate == null) {
					GlobalVariables.getMessageMap().putError("lookupCriteria[rangeLowerBoundKeyPrefix_beginDate]", "error.invalidLookupDate", range.getLowerBoundValue());
					invalid = true;
				}
			}
			else if(range.getUpperBoundValue() != null) {
				modifiedToDate = TKUtils.formatDateString(toDateString);
				if(modifiedToDate == null) {
					GlobalVariables.getMessageMap().putError("lookupCriteria[beginDate]", "error.invalidLookupDate", range.getUpperBoundValue());
					invalid = true;
				}
			}
			if(invalid) {
				return new ArrayList<TimeBlock>();
			}
		}

		//Could also simply use super.getSearchResults for an initial object list, then invoke LeaveBlockService with the relevant query params.
		List<TimeBlock> timeBlockList = TkServiceLocator.getTimeBlockService().getTimeBlocksForLookup(documentId, principalId, userPrincipalId, fromDate, toDate);
		List<LeaveBlock> leaveBlockList = LmServiceLocator.getLeaveBlockService().getTimeCalendarLeaveBlocksForTimeBlockLookup(documentId, principalId, userPrincipalId, fromDate, toDate);
		List<TimeBlockHistory> objectList = new ArrayList<TimeBlockHistory>();
		for(TimeBlock timeBlock : timeBlockList) {
			List<TimeBlockHistory> histories = TkServiceLocator.getTimeBlockHistoryService().getTimeBlockHistoryByTkTimeBlockId(timeBlock.getConcreteBlockId());
			for(TimeBlockHistory history : histories) {
				//history.setActionHistory(TkConstants.ACTION_HISTORY_CODES.get(history.getActionHistory()));
				boolean addToResults = false;
				if(modifiedFromDate != null && modifiedToDate != null) {
					if(history.getTimestamp().compareTo(modifiedFromDate.toDate()) >= 0
							&& history.getTimestamp().compareTo(modifiedToDate.toDate()) <= 0) {
						addToResults = true;
					}
				}
				else if(modifiedFromDate != null) {
					if(history.getTimestamp().compareTo(modifiedFromDate.toDate()) >= 0) {
						addToResults = true;
					}
				}
				else if(modifiedToDate != null) {
					if(history.getTimestamp().compareTo(modifiedToDate.toDate()) <= 0) {
						addToResults = true;
					}
				}
				else {
					addToResults = true;
				}
				if(addToResults) {
					TimesheetDocumentHeader timesheetHeader = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(history.getDocumentId());
					
					if(timesheetHeader != null) {
						if(StringUtils.isNotBlank(searchCriteria.get(DOC_STATUS_ID))) {
							//only add if doc status is one of those specified
							//format for categorical statuses is "category:Q" where 'Q' is one of "P,S,U". This format is specific to KRAD View handlers.
							//otherwise, the format is "Pending", "Successful" and "Unsuccessful".
							if(searchCriteria.get(DOC_STATUS_ID).contains("category")) {
	
								//which category was selected, and is the time block on a timesheet with this status.
								if(searchCriteria.get(DOC_STATUS_ID).contains(":P")) {
									//pending statuses
									//searchCriteria[DOC_STATUS_ID] differs between TimeBlock and TimeBlockHistory Lookupables.
									//Here, the key is not shortended as it is for TimeBlockLookupable.
									//The same KeyValueFinder is used in both instances. Might be due to implementation differences between KNS and KRAD
									//Lookup Views.
									if("I,S,R,E".contains(timesheetHeader.getDocumentStatus())) {
										objectList.add(history);
									}
								}
								else if(searchCriteria.get(DOC_STATUS_ID).contains(":S")) {
									//successful statuses
									if("P,F".contains(timesheetHeader.getDocumentStatus())) {
										objectList.add(history);
									}
								}
								else if(searchCriteria.get(DOC_STATUS_ID).contains(":U")) {
									//unsuccessful statuses
									if("X,D".contains(timesheetHeader.getDocumentStatus())) {
										objectList.add(history);
									}
								}
							}
							else if(searchCriteria.get(DOC_STATUS_ID).contains(timesheetHeader.getDocumentStatus())) {
								//match the specific doc status
								objectList.add(history);
							}
						}
						else {
							//no status specified, add regardless of status
							objectList.add(history);
						}
					}
					else if(StringUtils.isBlank(searchCriteria.get(DOC_STATUS_ID))) {
						//can't match doc status with a non existent header
						//only add to list if no status was selected
						objectList.add(history);
					}
				}
			}
		}
		
		//must filter here to avoid removing mocked TimeBlockHistories created from LeaveBlockHistories
		//LeaveBlockHistorys do not have job numbers. This filter may need to be updated, and/or TimeBlockLookupableHelper's RoleService logic
		//be incorporated into the method to appropriately filter histories.
		objectList = filterByPrincipalId(objectList, GlobalVariables.getUserSession().getPrincipalId());
		objectList = addDetails(objectList);
		
		for(LeaveBlock leaveBlock : leaveBlockList) {
			List<LeaveBlockHistory> histories = LmServiceLocator.getLeaveBlockHistoryService().getLeaveBlockHistoryByLmLeaveBlockId(leaveBlock.getLmLeaveBlockId());
			for(LeaveBlockHistory history : histories) {

				TimesheetDocumentHeader timesheetHeader = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeaderForDate(history.getPrincipalId(), 
						new DateTime(history.getLeaveDate().getTime()));
				
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
					tBlock.setAmount(history.getLeaveAmount());
					tBlock.setHours(history.getHours());
					tBlock.setJobNumber(history.getJobNumber());
					tBlock.setEarnCode(history.getEarnCode());
					tBlock.setPrincipalId(history.getPrincipalId());
					tBlock.setUserPrincipalId(history.getPrincipalIdModified());
					tBlock.setPrincipalIdModified(history.getPrincipalIdModified());
					tBlock.setWorkArea(history.getWorkArea());
					tBlock.setTask(history.getTask());
					tBlock.setOvertimePref(history.getOvertimePref());
					tBlock.setLunchDeleted(history.getLunchDeleted());
					tBlock.setDocumentId(history.getDocumentId());
					tBlock.setBeginDate(history.getLeaveDate());
					tBlock.setEndDate(history.getLeaveDate());
					tBlock.setTimeHourDetails(new ArrayList<TimeHourDetail>());
					tBlock.setTimestampModified(history.getTimestamp());
					tBlock.setTimestamp(history.getTimestamp());
					tBlock.setActionHistory(TkConstants.ACTION_HISTORY_CODES.get(history.getAction()));
					tBlock.setClockLogCreated(false);
					tBlock.setTkTimeBlockId(leaveBlock.getConcreteBlockId());
					if(timesheetHeader != null) {
						tBlock.setDocumentId(timesheetHeader.getDocumentId());
						tBlock.setTimesheetDocumentHeader(timesheetHeader);
						if(StringUtils.isNotBlank(searchCriteria.get(DOC_STATUS_ID))) {
							//only add if doc status is one of those specified
							//format for categorical statuses is "category:Q" where 'Q' is one of "P,S,U".
							if(searchCriteria.get(DOC_STATUS_ID).contains("category")) {
	
								//which category was selected, and is the time block on a timesheet with this status.
								if(searchCriteria.get(DOC_STATUS_ID).contains(":P")) {
									//pending statuses
									if("I,S,R,E".contains(timesheetHeader.getDocumentStatus())) {
										objectList.add(tBlock);
									}
								}
								else if(searchCriteria.get(DOC_STATUS_ID).contains(":S")) {
									//successful statuses
									if("P,F".contains(timesheetHeader.getDocumentStatus())) {
										objectList.add(tBlock);
									}
								}
								else if(searchCriteria.get(DOC_STATUS_ID).contains(":U")) {
									//unsuccessful statuses
									if("X,D".contains(timesheetHeader.getDocumentStatus())) {
										objectList.add(tBlock);
									}
								}
							}
							else if(StringUtils.equals(searchCriteria.get(DOC_STATUS_ID),timesheetHeader.getDocumentStatus())) {
								//match the specific doc status
								objectList.add(tBlock);
							}
						}
						else {
							//no status specified, add regardless of status
							objectList.add(tBlock);
						}
					}
					else if(StringUtils.isBlank(searchCriteria.get(DOC_STATUS_ID))) {
						//can't match doc status with a non existent header
						//only add to list if no status was selected
						objectList.add(tBlock);
					}
				}
			}
		}

		return objectList;
	}

}