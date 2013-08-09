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
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.block.CalendarBlock;
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
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.web.form.LookupForm;

public class TimeBlockLookupableHelperServiceImpl extends KPMELookupableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static final String DOC_ID = "documentId";
	static final String DOC_STATUS_ID = "timesheetDocumentHeader.documentStatus";
	static final String BEGIN_DATE_ID = "beginDate";
	private static final String BEGIN_TIMESTAMP = "beginTimestamp";
	

	@Override
	protected List<?> getSearchResults(LookupForm form,
			Map<String, String> searchCriteria, boolean unbounded) {
		// TODO Auto-generated method stub
		 if (searchCriteria.containsKey(BEGIN_DATE_ID)) {
			 //beginDate = searchCriteria.get(BEGIN_DATE);
			 searchCriteria.put(BEGIN_TIMESTAMP, searchCriteria.get(BEGIN_DATE_ID));
			 searchCriteria.remove(BEGIN_DATE_ID);
		 }
		 
/*		 if(searchCriteria.containsKey(DOC_STATUS_ID)) {
			 String docStatuses = searchCriteria.get(DOC_STATUS_ID);
			 String [] docStatusArray = docStatuses.split(",");
			 if(searchCriteria.get("DOC_ID") != null) {
				 //attach error to return lookup form: "doc status must match that of the document whose id is documentId"
				 //or clear doc statuses and keep only the status that matches the doc id.
			 }
			 else {
				 TkServiceLocator.getTimesheetDocumentHeaderService().get
			 }
			 searchCriteria.remove(DOC_STATUS_ID);
		 }*/
		 
		@SuppressWarnings("unchecked")
		String documentId = null;
		if(StringUtils.isNotBlank(searchCriteria.get(DOC_ID))) {
			documentId = searchCriteria.get(DOC_ID);
		}
		String principalId = null;
		if(StringUtils.isNotBlank(searchCriteria.get("principalId"))) {
			principalId = searchCriteria.get("principalId");
		}
		String userPrincipalId = null;
		if(StringUtils.isNotBlank(searchCriteria.get("userPrincipalId"))) {
			userPrincipalId = searchCriteria.get("userPrincipalId");
		}
		LocalDate fromDate = null;
		LocalDate toDate = null;
		if(StringUtils.isNotBlank(searchCriteria.get(BEGIN_TIMESTAMP))) {
			String fromDateString = searchCriteria.get(BEGIN_TIMESTAMP).substring(0, 10);
			if(searchCriteria.get(BEGIN_TIMESTAMP).length() > 10) {
				String toDateString = searchCriteria.get(BEGIN_TIMESTAMP).substring(12,22);
				toDate = TKUtils.formatDateString(toDateString);
			}
			fromDate = TKUtils.formatDateString(fromDateString);
		}

		//Could also simply use super.getSearchResults for an initial object list, then invoke LeaveBlockService with the relevant query params.
		List<CalendarBlock> calendarBlockList = HrServiceLocator.getCalendarBlockService().getCalendarBlocksForTimeBlockLookup(documentId, principalId, userPrincipalId, fromDate, toDate);
		List<TimeBlock> objectList = new ArrayList<TimeBlock>();//(List<TimeBlock>) super.getSearchResults(form, searchCriteria, unbounded);
		for(CalendarBlock cBlock : calendarBlockList) {
			if(StringUtils.equals(cBlock.getConcreteBlockType(),"Time")) {
				TimeBlock tBlock = TkServiceLocator.getTimeBlockService().getTimeBlock(cBlock.getConcreteBlockId());
				objectList.add(tBlock);
			}
			else if(StringUtils.equals(cBlock.getConcreteBlockType(), "Leave")) {
				LeaveBlock lBlock = LmServiceLocator.getLeaveBlockService().getLeaveBlock(cBlock.getConcreteBlockId());
				TimeBlock tBlock = new TimeBlock();
				tBlock.setAmount(cBlock.getAmount());
				tBlock.setHours(cBlock.getHours());
				tBlock.setJobNumber(cBlock.getJobNumber());
				tBlock.setEarnCode(cBlock.getEarnCode());
				tBlock.setPrincipalId(cBlock.getPrincipalId());
				tBlock.setUserPrincipalId(lBlock.getPrincipalIdModified());
				tBlock.setWorkArea(cBlock.getWorkArea());
				tBlock.setTask(cBlock.getTask());
				tBlock.setOvertimePref(cBlock.getOvertimePref());
				tBlock.setLunchDeleted(cBlock.getLunchDeleted());
				tBlock.setDocumentId(cBlock.getDocumentId());
				tBlock.setBeginDate(lBlock.getLeaveDate());
				tBlock.setEndDate(lBlock.getLeaveDate());
				tBlock.setTimeHourDetails(new ArrayList<TimeHourDetail>());
				TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(cBlock.getDocumentId());
				tBlock.setTimesheetDocumentHeader(tdh);
				objectList.add(tBlock);
			}
		}
		
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
		
		return actionUrlHref;
	}

}
