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
package org.kuali.kpme.tklm.leave.block.web;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.api.job.JobContract;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.lookup.KPMELookupableImpl;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.block.LeaveBlockHistory;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.workflow.LeaveCalendarDocumentHeader;
import org.kuali.rice.core.api.search.Range;
import org.kuali.rice.core.api.search.SearchExpressionUtils;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.lookup.LookupForm;
import org.kuali.rice.krad.lookup.LookupView;
import org.kuali.rice.krad.util.GlobalVariables;

import java.util.*;

public class LeaveBlockHistoryLookupableHelperServiceImpl extends KPMELookupableImpl  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String DOC_ID = "documentId";
	private static final String DOC_STATUS_ID = "leaveCalendarDocumentHeader.documentStatus";
	private static final String BEGIN_DATE_ID = "beginDate";
	private static final String BEGIN_TIMESTAMP = "beginTimestamp";

	private static final Logger LOG = Logger.getLogger(LeaveBlockHistoryLookupableHelperServiceImpl.class);

    @Override
    protected Collection<?> executeSearch(Map<String, String> searchCriteria, List<String> wildcardAsLiteralSearchCriteria, boolean bounded, Integer searchResultsLimit) {
		// TODO Auto-generated method stub
		 if (searchCriteria.containsKey(BEGIN_DATE_ID)) {
			 //beginDate = searchCriteria.get(BEGIN_DATE);
			 searchCriteria.put(BEGIN_TIMESTAMP, searchCriteria.get(BEGIN_DATE_ID));
			 searchCriteria.remove(BEGIN_DATE_ID);
		 }
		 
		String documentId = searchCriteria.get(DOC_ID);
		String principalId = searchCriteria.get("principalId");
		String userPrincipalId = searchCriteria.get("userPrincipalId");
		String leaveBlockType = searchCriteria.get("leaveBlockType");
		
		
		
		
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
				return new ArrayList<LeaveBlockHistory>();
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
				return new ArrayList<LeaveBlockHistory>();
			}
		}
		
		//Could also simply use super.getSearchResults for an initial object list, then invoke LeaveBlockService with the relevant query params.
		//List<LeaveBlock> leaveBlockList = LmServiceLocator.getLeaveBlockService().getLeaveBlocksForLookup(documentId, principalId, userPrincipalId, fromDate, toDate, leaveBlockType);
		List<LeaveBlock> leaveBlockList = LmServiceLocator.getLeaveBlockService().getLeaveBlocksForLookup(documentId, principalId, userPrincipalId, fromDate, toDate,leaveBlockType);
		List<LeaveBlockHistory> objectList = new ArrayList<LeaveBlockHistory>();
		
		for(LeaveBlock leaveBlock : leaveBlockList) {
			List<LeaveBlockHistory> histories = LmServiceLocator.getLeaveBlockHistoryService().getLeaveBlockHistoryByLmLeaveBlockId(leaveBlock.getLmLeaveBlockId());
			for(LeaveBlockHistory history : histories) {
				
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
					LeaveCalendarDocumentHeader lcHeader = LmServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(history.getDocumentId());
					
					if(lcHeader != null) {
						if(StringUtils.isNotBlank(searchCriteria.get(DOC_STATUS_ID))) {
							//only add if doc status is one of those specified
							if(searchCriteria.get(DOC_STATUS_ID).contains("category")) {
								//format for categorical statuses is "category:Q" where 'Q' is one of "P,S,U".
								//which category was selected, and is the time block on a timesheet with this status.
								if(searchCriteria.get(DOC_STATUS_ID).contains("P")) {
									//pending statuses
									if("I,S,R,E".contains(lcHeader.getDocumentStatus())) {
										objectList.add(history);
									}
								}
								else if(searchCriteria.get(DOC_STATUS_ID).contains("S")) {
									//successful statuses
									if("P,F".contains(lcHeader.getDocumentStatus())) {
										objectList.add(history);
									}
								}
								else if(searchCriteria.get(DOC_STATUS_ID).contains("U")) {
									//unsuccessful statuses
									if("X,D".contains(lcHeader.getDocumentStatus())) {
										objectList.add(history);
									}
								}
							}
							else if(searchCriteria.get(DOC_STATUS_ID).contains(lcHeader.getDocumentStatus())) {
								//match the specific doc status
								objectList.add(history);
							}
						}
						else {
							//no status specified, add regardless of status
							objectList.add(history);
						}
							
						
					} else if(StringUtils.isBlank(searchCriteria.get(DOC_STATUS_ID))) {
						//can't match doc status with a non existent header
						//only add to list if no status was selected
						objectList.add(history);
					}
				}
		
			}
		}
	        if(!objectList.isEmpty()) {
        	Iterator<? extends BusinessObject> itr = objectList.iterator();

            //TODO - performance  -- need to get roles outside of loop for user, and check inside... too many db calls
        	while (itr.hasNext()) {
        		LeaveBlockHistory tb = (LeaveBlockHistory) itr.next();
				
				Long workArea = tb.getWorkArea();
				
				JobContract job = HrServiceLocator.getJobService().getJob(tb.getPrincipalId(), tb.getJobNumber(), LocalDate.fromDateFields(tb.getLeaveDate()), false);
				String department = job != null ? job.getDept() : null;
				String groupKeyCode = job != null ? job.getGroupKeyCode() : null;
				Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, groupKeyCode, LocalDate.fromDateFields(tb.getLeaveDate()));
				String location = departmentObj != null ? departmentObj.getGroupKey().getLocationId() : null;
				DateTime date = LocalDate.now().toDateTimeAtStartOfDay();
				boolean valid = false;
				if (HrServiceLocator.getKPMEGroupService().isMemberOfSystemAdministratorGroup(HrContext.getPrincipalId(), date)
						|| HrServiceLocator.getKPMEGroupService().isMemberOfSystemViewOnlyGroup(HrContext.getPrincipalId(), date)
						|| HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(HrContext.getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), workArea, date)
						|| HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(HrContext.getPrincipalId(), KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, groupKeyCode, date)
						|| HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(HrContext.getPrincipalId(), KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, groupKeyCode, date)
						|| HrServiceLocator.getKPMERoleService().principalHasRoleInLocation(HrContext.getPrincipalId(), KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), location, date)
						|| HrServiceLocator.getKPMERoleService().principalHasRoleInLocation(HrContext.getPrincipalId(), KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), location, date)
						|| HrServiceLocator.getKPMERoleService().principalHasRoleInLocation(HrContext.getPrincipalId(), KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_LOCATION_VIEW_ONLY.getRoleName(), location, date)
						|| HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(HrContext.getPrincipalId(), KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_DEPARTMENT_VIEW_ONLY.getRoleName(), location, groupKeyCode, date)
						|| HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(HrContext.getPrincipalId(), KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_DEPARTMENT_VIEW_ONLY.getRoleName(), location, groupKeyCode, date)
						|| HrServiceLocator.getKPMERoleService().principalHasRoleInLocation(HrContext.getPrincipalId(), KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_LOCATION_VIEW_ONLY.getRoleName(), location, date)) {
					valid = true;
				}
				
				if (!valid) {
				
					itr.remove();
					continue;
				}
        	}
					
			
        }
        return objectList;
	}
		 
	 

	@Override
	protected String getMaintenanceActionUrl(LookupForm lookupForm, Object dataObject,
			String methodToCall, List<String> pkNames) {
		String actionUrlHref = super.getMaintenanceActionUrl(lookupForm, dataObject, methodToCall, pkNames);
		LeaveBlockHistory lbh = null;
		String concreteBlockId = null;
		if(dataObject instanceof LeaveBlockHistory) {
			lbh = (LeaveBlockHistory) dataObject;
			concreteBlockId = lbh.getLmLeaveBlockHistoryId();
		}
		if(concreteBlockId == null) {
			return null;
		}

		return actionUrlHref;
	}

    @Override
    public boolean allowsMaintenanceNewOrCopyAction() {
        return false;
    }

    @Override
    public boolean allowsMaintenanceEditAction(Object dataObject) {
        return false;
    }

    @Override
    public boolean allowsMaintenanceDeleteAction(Object dataObject) {
        return false;
    }

}