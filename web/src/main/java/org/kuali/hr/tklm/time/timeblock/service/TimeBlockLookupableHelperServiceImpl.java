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
package org.kuali.hr.tklm.time.timeblock.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.hr.core.department.Department;
import org.kuali.hr.core.job.Job;
import org.kuali.hr.core.role.KPMERole;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.tklm.common.TKContext;
import org.kuali.hr.tklm.leave.service.base.LmServiceLocator;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.hr.tklm.time.timeblock.TimeBlock;
import org.kuali.hr.tklm.time.timeblock.TimeHourDetail;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.krad.bo.BusinessObject;

public class TimeBlockLookupableHelperServiceImpl extends KualiLookupableHelperServiceImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static final String DOC_ID = "documentId";
	static final String DOC_STATUS_ID = "timesheetDocumentHeader.documentStatus";
	static final String BEGIN_DATE_ID = "beginDate";
		
	 @Override
    public List<? extends BusinessObject> getSearchResults(java.util.Map<String, String> fieldValues) {
	 
		 String docStatus = "", beginDateString="";

		 if(fieldValues.containsKey(DOC_STATUS_ID)){
				docStatus = fieldValues.get(DOC_STATUS_ID);
				fieldValues.remove(DOC_STATUS_ID);
			}
		 if(fieldValues.containsKey(BEGIN_DATE_ID)){
			 	beginDateString = fieldValues.get(BEGIN_DATE_ID);
				fieldValues.remove(BEGIN_DATE_ID);
			}
        
        List<TimeBlock> objectList = (List<TimeBlock>) super.getSearchResults(fieldValues);
      
        if(!objectList.isEmpty()) {
        	Iterator<? extends BusinessObject> itr = objectList.iterator();
			
        	while (itr.hasNext()) {
				TimeBlock tb = (TimeBlock) itr.next();
				
				Long workArea = tb.getWorkArea();
				
				Job job = HrServiceLocator.getJobService().getJob(tb.getUserPrincipalId(), tb.getJobNumber(), LocalDate.now(), false);
				String department = job != null ? job.getDept() : null;
				
				Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, LocalDate.now());
				String location = departmentObj != null ? departmentObj.getLocation() : null;
				
				boolean valid = false;
				if (TkServiceLocator.getHRGroupService().isMemberOfSystemAdministratorGroup(TKContext.getPrincipalId(), new DateTime())
						|| TkServiceLocator.getHRGroupService().isMemberOfSystemViewOnlyGroup(TKContext.getPrincipalId(), new DateTime())
						|| TkServiceLocator.getHRRoleService().principalHasRoleInWorkArea(TKContext.getPrincipalId(), KPMERole.APPROVER.getRoleName(), workArea, new DateTime())
						|| TkServiceLocator.getTKRoleService().principalHasRoleInDepartment(TKContext.getPrincipalId(), KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, new DateTime())
						|| LmServiceLocator.getLMRoleService().principalHasRoleInDepartment(TKContext.getPrincipalId(), KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, new DateTime())
						|| TkServiceLocator.getTKRoleService().principalHasRoleInLocation(TKContext.getPrincipalId(), KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), location, new DateTime())
						|| LmServiceLocator.getLMRoleService().principalHasRoleInLocation(TKContext.getPrincipalId(), KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), location, new DateTime())) {	
					valid = true;
				}
				
				if (!valid) {
					itr.remove();
					continue;
				}
				if(StringUtils.isNotEmpty(docStatus)) {
					if(tb.getTimesheetDocumentHeader() == null) {
						itr.remove();
						continue;
					} else {
						if(tb.getTimesheetDocumentHeader().getDocumentStatus() != null) {
							if(!tb.getTimesheetDocumentHeader().getDocumentStatus().equals(docStatus)){
								itr.remove();
								continue;
							}
						} else {
							itr.remove();
							continue;
						}
					}
				}
								
				if(StringUtils.isNotEmpty(beginDateString)) {
					if(tb.getBeginDate() != null) {
						if(!this.checkDate(tb, tb.getBeginDate(), beginDateString)) {
							itr.remove();
							continue;
						} 
					} else {
						itr.remove();
						continue;
					}
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
	 
	@SuppressWarnings("unchecked")
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
	}
}
