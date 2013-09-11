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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.department.Department;
import org.kuali.kpme.core.job.Job;
import org.kuali.kpme.core.lookup.KPMELookupableImpl;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.timehourdetail.TimeHourDetail;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.core.api.search.Range;
import org.kuali.rice.core.api.search.SearchExpressionUtils;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.uif.view.LookupView;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.LookupForm;

public class TimeBlockLookupableHelperServiceImpl extends KPMELookupableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String DOC_ID = "documentId";
	private static final String DOC_STATUS_ID = "timesheetDocumentHeader.documentStatus";
	private static final String BEGIN_DATE_ID = "beginDate";
	private static final String BEGIN_TIMESTAMP = "beginTimestamp";

	private static final Logger LOG = Logger.getLogger(TimeBlockLookupableHelperServiceImpl.class);
	

	@Override
	protected List<?> getSearchResults(LookupForm form,
			Map<String, String> searchCriteria, boolean unbounded) {
		// TODO Auto-generated method stub
		 if (searchCriteria.containsKey(BEGIN_DATE_ID)) {
			 //beginDate = searchCriteria.get(BEGIN_DATE);
			 searchCriteria.put(BEGIN_TIMESTAMP, searchCriteria.get(BEGIN_DATE_ID));
			 searchCriteria.remove(BEGIN_DATE_ID);
		 }
		 
		String documentId = searchCriteria.get(DOC_ID);
		String principalId = searchCriteria.get("principalId");
		String userPrincipalId = searchCriteria.get("userPrincipalId");
		String fromDateString = TKUtils.getFromDateString(searchCriteria.get(BEGIN_TIMESTAMP));
		String toDateString = TKUtils.getToDateString(searchCriteria.get(BEGIN_TIMESTAMP));

		LocalDate fromDate = null;
		LocalDate toDate = null;
		if(StringUtils.isNotBlank(searchCriteria.get(BEGIN_TIMESTAMP))) {
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

		//Could also simply use super.getSearchResults for an initial object list, then invoke LeaveBlockService with the relevant query params.
		List<TimeBlock> timeBlockList = TkServiceLocator.getTimeBlockService().getTimeBlocksForLookup(documentId, principalId, userPrincipalId, fromDate, toDate);
		List<TimeBlock> objectList = new ArrayList<TimeBlock>();
		for(TimeBlock tBlock : timeBlockList) {

			TimesheetDocumentHeader timesheetHeader = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(tBlock.getDocumentId());
			
			if(timesheetHeader != null) {
				if(StringUtils.isNotBlank(searchCriteria.get(DOC_STATUS_ID))) {
					//only add if doc status is one of those specified
					if(searchCriteria.get(DOC_STATUS_ID).contains("category")) {
						//format for categorical statuses is "category:Q" where 'Q' is one of "P,S,U".
						//which category was selected, and is the time block on a timesheet with this status.
						if(searchCriteria.get(DOC_STATUS_ID).contains("P")) {
							//pending statuses
							if("I,S,R,E".contains(timesheetHeader.getDocumentStatus())) {
								objectList.add(tBlock);
							}
						}
						else if(searchCriteria.get(DOC_STATUS_ID).contains("S")) {
							//successful statuses
							if("P,F".contains(timesheetHeader.getDocumentStatus())) {
								objectList.add(tBlock);
							}
						}
						else if(searchCriteria.get(DOC_STATUS_ID).contains("U")) {
							//unsuccessful statuses
							if("X,D".contains(timesheetHeader.getDocumentStatus())) {
								objectList.add(tBlock);
							}
						}
					}
					else if(searchCriteria.get(DOC_STATUS_ID).contains(timesheetHeader.getDocumentStatus())) {
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
		
		List<LeaveBlock> leaveBlocks = LmServiceLocator.getLeaveBlockService().getTimeCalendarLeaveBlocksForTimeBlockLookup(documentId,principalId,userPrincipalId,fromDate,toDate);
		for(LeaveBlock leaveBlock : leaveBlocks) {
			TimesheetDocumentHeader timesheetHeader = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeaderForDate(leaveBlock.getPrincipalId(), 
					new DateTime(leaveBlock.getLeaveDate().getTime()));

			// NOTE: Do NOT set tkTimeBlockId.
			// We'll leave this field blank. When getActionUrlHref is called with this object
			// we can infer that the object is a "mocked" time block, and remove the "view" link from results.
			TimeBlock tBlock = new TimeBlock();
			tBlock.setAmount(leaveBlock.getAmount());
			tBlock.setHours(leaveBlock.getHours());
			tBlock.setJobNumber(leaveBlock.getJobNumber());
			tBlock.setEarnCode(leaveBlock.getEarnCode());
			tBlock.setPrincipalId(leaveBlock.getPrincipalId());
			tBlock.setUserPrincipalId(leaveBlock.getPrincipalIdModified());
			tBlock.setWorkArea(leaveBlock.getWorkArea());
			tBlock.setTask(leaveBlock.getTask());
			tBlock.setOvertimePref(leaveBlock.getOvertimePref());
			tBlock.setLunchDeleted(leaveBlock.getLunchDeleted());
			tBlock.setBeginDate(leaveBlock.getLeaveDate());
			tBlock.setEndDate(leaveBlock.getLeaveDate());
			tBlock.setTimeHourDetails(new ArrayList<TimeHourDetail>());
			if(timesheetHeader != null) {
				tBlock.setDocumentId(timesheetHeader.getDocumentId());
				tBlock.setTimesheetDocumentHeader(timesheetHeader);
				if(StringUtils.isNotBlank(searchCriteria.get(DOC_STATUS_ID))) {
					//only add if doc status is one of those specified
					if(searchCriteria.get(DOC_STATUS_ID).contains("category")) {
						//format for categorical statuses is "category:Q" where 'Q' is one of "P,S,U".
						//which category was selected, and is the time block on a timesheet with this status.
						if(searchCriteria.get(DOC_STATUS_ID).contains("P")) {
							//pending statuses
							if("I,S,R,E".contains(timesheetHeader.getDocumentStatus())) {
								objectList.add(tBlock);
							}
						}
						else if(searchCriteria.get(DOC_STATUS_ID).contains("S")) {
							//successful statuses
							if("P,F".contains(timesheetHeader.getDocumentStatus())) {
								objectList.add(tBlock);
							}
						}
						else if(searchCriteria.get(DOC_STATUS_ID).contains("U")) {
							//unsuccessful statuses
							if("X,D".contains(timesheetHeader.getDocumentStatus())) {
								objectList.add(tBlock);
							}
						}
					}
					else if(searchCriteria.get(DOC_STATUS_ID).contains(timesheetHeader.getDocumentStatus())) {
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
		
        if(!objectList.isEmpty()) {
        	Iterator<? extends BusinessObject> itr = objectList.iterator();
			
        	while (itr.hasNext()) {
				TimeBlock tb = (TimeBlock) itr.next();
				
				Long workArea = tb.getWorkArea();
				
				Job job = HrServiceLocator.getJobService().getJob(tb.getPrincipalId(), tb.getJobNumber(), LocalDate.fromDateFields(tb.getBeginDate()), false);
				String department = job != null ? job.getDept() : null;
				
				Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, LocalDate.fromDateFields(tb.getBeginDate()));
				String location = departmentObj != null ? departmentObj.getLocation() : null;
				
				boolean valid = false;
				if (HrServiceLocator.getKPMEGroupService().isMemberOfSystemAdministratorGroup(HrContext.getPrincipalId(), new DateTime())
						|| HrServiceLocator.getKPMEGroupService().isMemberOfSystemViewOnlyGroup(HrContext.getPrincipalId(), new DateTime())
						|| HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(HrContext.getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), workArea, new DateTime())
						|| HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(HrContext.getPrincipalId(), KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, new DateTime())
						|| HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(HrContext.getPrincipalId(), KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, new DateTime())
						|| HrServiceLocator.getKPMERoleService().principalHasRoleInLocation(HrContext.getPrincipalId(), KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), location, new DateTime())
						|| HrServiceLocator.getKPMERoleService().principalHasRoleInLocation(HrContext.getPrincipalId(), KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), location, new DateTime())) {	
					valid = true;
				}
				
				if (!valid) {
					itr.remove();
					continue;
				}
        	}
			
			// Fetch list from time hour detail and convert it into TimeBlock
			if(!objectList.isEmpty()) {
				List<TimeBlock> timeBlocks = new ArrayList<TimeBlock>(objectList);
				for(TimeBlock tb: timeBlocks) {
					List<TimeHourDetail> timeHourDetails = tb.getTimeHourDetails();
					for(TimeHourDetail thd : timeHourDetails) {
					  if(!thd.getEarnCode().equalsIgnoreCase(tb.getEarnCode())) {
						  TimeBlock timeBlock = tb.copy();
						  timeBlock.setEarnCode(thd.getEarnCode());
						  timeBlock.setHours(thd.getHours());
						  timeBlock.setAmount(thd.getAmount());
						  objectList.add(timeBlock);
					  }
					} // inner for ends
				} // outer for ends
			} // if ends
			
        }
        
     
        return objectList;
	}
	
/*	@Override
    public List<? extends BusinessObject> getSearchResults(java.util.Map<String, String> fieldValues) {
		 if (fieldValues.containsKey(BEGIN_DATE_ID)) {
			 //beginDate = fieldValues.get(BEGIN_DATE);
			 fieldValues.put(BEGIN_TIMESTAMP, fieldValues.get(BEGIN_DATE_ID));
			 fieldValues.remove(BEGIN_DATE_ID);
		 }

		 @SuppressWarnings({ "unchecked", "deprecation" })
		List<TimeBlock> objectList = (List<TimeBlock>) super.getSearchResults(fieldValues);
      
        if(!objectList.isEmpty()) {
        	Iterator<? extends BusinessObject> itr = objectList.iterator();
			
        	while (itr.hasNext()) {
				TimeBlock tb = (TimeBlock) itr.next();
				
				Long workArea = tb.getWorkArea();
				
				Job job = HrServiceLocator.getJobService().getJob(tb.getPrincipalId(), tb.getJobNumber(), LocalDate.now(), false);
				String department = job != null ? job.getDept() : null;
				
				Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, LocalDate.now());
				String location = departmentObj != null ? departmentObj.getLocation() : null;
				
				boolean valid = false;
				if (HrServiceLocator.getKPMEGroupService().isMemberOfSystemAdministratorGroup(HrContext.getPrincipalId(), new DateTime())
						|| HrServiceLocator.getKPMEGroupService().isMemberOfSystemViewOnlyGroup(HrContext.getPrincipalId(), new DateTime())
						|| HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(HrContext.getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), workArea, new DateTime())
						|| HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(HrContext.getPrincipalId(), KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, new DateTime())
						|| HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(HrContext.getPrincipalId(), KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, new DateTime())
						|| HrServiceLocator.getKPMERoleService().principalHasRoleInLocation(HrContext.getPrincipalId(), KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), location, new DateTime())
						|| HrServiceLocator.getKPMERoleService().principalHasRoleInLocation(HrContext.getPrincipalId(), KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), location, new DateTime())) {	
					valid = true;
				}
				
				if (!valid) {
					itr.remove();
					continue;
				}
        	}
			
			// Fetch list from time hour detail and convert it into TimeBlock
			if(!objectList.isEmpty()) {
				List<TimeBlock> timeBlocks = new ArrayList<TimeBlock>(objectList);
				for(TimeBlock tb: timeBlocks) {
					List<TimeHourDetail> timeHourDetails = tb.getTimeHourDetails();
					for(TimeHourDetail thd : timeHourDetails) {
					  if(!thd.getEarnCode().equalsIgnoreCase(tb.getEarnCode())) {
						  TimeBlock timeBlock = tb.copy();
						  timeBlock.setEarnCode(thd.getEarnCode());
						  timeBlock.setHours(thd.getHours());
						  timeBlock.setAmount(thd.getAmount());
						  objectList.add(timeBlock);
					  }
					} // inner for ends
				} // outer for ends
			} // if ends
			
        }
        
     
        return objectList;
	 }*/
	 
	 public boolean checkDate(TimeBlock tb, Date asOfDate, String dateString) {
		 if(tb.getTimesheetDocumentHeader() == null) {
				return false;
		 }
		try {
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			Date dateFrom;
			Date dateTo;
			String subDateString;
			if(dateString.indexOf("..") == 10) {
				subDateString= dateString.substring(0, 10);
				dateFrom = df.parse(subDateString);
				subDateString= dateString.substring(12, dateString.length());
				dateTo = df.parse(subDateString);
				if(asOfDate != null) {
					if(!( (asOfDate.after(dateFrom) || asOfDate.equals(dateFrom))
							&& (asOfDate.before(dateTo) || asOfDate.equals(dateTo)))) {
						return false;
					}
				} else {
					return false;
				}
			} else{
				subDateString= dateString.substring(2, dateString.length());
				dateTo = df.parse(subDateString);
				if(asOfDate != null) {
					if( (dateString.startsWith(">=") && asOfDate.before(dateTo))
							|| (dateString.startsWith("<=") && asOfDate.after(dateTo))) {
						return false;
					}
				} else {
					return false;
				}
			}
		} catch (ParseException e) {
		}
	  return true;
	 }
	 
/*	@SuppressWarnings("unchecked")
	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(businessObject, pkNames);
		List<HtmlData> overrideUrls = new ArrayList<HtmlData>();
		for(HtmlData actionUrl : customActionUrls){
			if(!StringUtils.equals(actionUrl.getMethodToCall(), "copy")){
				overrideUrls.add(actionUrl);
			}
		}
		return overrideUrls;
	}*/

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

}
