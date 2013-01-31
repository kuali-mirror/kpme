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
package org.kuali.hr.time.timeblock.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.Interval;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlockHistory;
import org.kuali.hr.time.timeblock.TimeBlockHistoryDetail;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.krad.bo.BusinessObject;

public class TimeBlockHistoryDetailLookupableHelperServiceImpl extends KualiLookupableHelperServiceImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static final String DOC_ID = "documentId";
	static final String DOC_STATUS_ID = "timeBlockHistory.timesheetDocumentHeader.documentStatus";
	static final String BEGIN_DATE_ID = "beginDate";

    @SuppressWarnings("unchecked")
	@Override
    public List<? extends BusinessObject> getSearchResults(java.util.Map<String, String> fieldValues) {

        String docStatus = "", beginDateString = "";

        if (fieldValues.containsKey(DOC_STATUS_ID)) {
            docStatus = fieldValues.get(DOC_STATUS_ID);
            fieldValues.remove(DOC_STATUS_ID);
        }
        if (fieldValues.containsKey(BEGIN_DATE_ID)) {
            beginDateString = fieldValues.get(BEGIN_DATE_ID);
            fieldValues.remove(BEGIN_DATE_ID);
        }
        List<TimeBlockHistoryDetail> objectList = (List<TimeBlockHistoryDetail>) super.getSearchResults(fieldValues);
        Map<String,List<TimeBlockHistoryDetail>> timeBlockHistoryToDetailMap = new HashMap<String,List<TimeBlockHistoryDetail>>();
        Map<String,List<TimeBlockHistoryDetail>> filteredTimeBlockHistoryToDetailMap = new HashMap<String,List<TimeBlockHistoryDetail>>();
        
        if (!objectList.isEmpty()) {
        	this.filterWithSecurity(objectList);
        	
        	for(TimeBlockHistoryDetail tbhd : objectList){
        		if(!timeBlockHistoryToDetailMap.containsKey(tbhd.getTkTimeBlockHistoryId())){
        			List<TimeBlockHistoryDetail> thdList = new ArrayList<TimeBlockHistoryDetail>();
        			timeBlockHistoryToDetailMap.put(tbhd.getTkTimeBlockHistoryId(), thdList);
        		}
        		
        		List<TimeBlockHistoryDetail> thdList = timeBlockHistoryToDetailMap.get(tbhd.getTkTimeBlockHistoryId());
        		thdList.add(tbhd);
        	}
        	filteredTimeBlockHistoryToDetailMap.putAll(timeBlockHistoryToDetailMap);

        	Iterator<String> itr = timeBlockHistoryToDetailMap.keySet().iterator();
        	while(itr.hasNext()){
        		String timeHourDetailId = itr.next();
        		List<TimeBlockHistoryDetail> tbhdList = timeBlockHistoryToDetailMap.get(timeHourDetailId);
        		TimeBlockHistoryDetail tbhd = tbhdList.get(0);
        		TimeBlockHistory tbh = tbhd.getTimeBlockHistory();
        		
        		if(StringUtils.isNotEmpty(docStatus)){
        			if(tbh.getTimesheetDocumentHeader() == null){
        				filteredTimeBlockHistoryToDetailMap.remove(timeHourDetailId);
        				continue;
        			} else {
        				if (tbh.getTimesheetDocumentHeader().getDocumentStatus() != null) {
        					if (!tbh.getTimesheetDocumentHeader().getDocumentStatus().equals(docStatus)) {
        						filteredTimeBlockHistoryToDetailMap.remove(timeHourDetailId);
        						continue;
        					}
        				} else {
        					filteredTimeBlockHistoryToDetailMap.remove(timeHourDetailId);
        					continue;
        				}
        			}
        		}
        		
                if(StringUtils.isNotEmpty(beginDateString)) {
					if(tbh.getBeginDate() != null) {
						if(!this.inDateRange(tbh.getBeginDate(), beginDateString)) {
							filteredTimeBlockHistoryToDetailMap.remove(timeHourDetailId);
							continue;
						} 
					} else {
						filteredTimeBlockHistoryToDetailMap.remove(timeHourDetailId);
						continue;
					}
				}
        	}
        }
        
        List<TimeBlockHistoryDetail> lstFinalList = new ArrayList<TimeBlockHistoryDetail>();
        for(List<TimeBlockHistoryDetail> tbhdList : filteredTimeBlockHistoryToDetailMap.values()){
        	lstFinalList.addAll(tbhdList);
        }

        sortByTimeBlockId(lstFinalList);
        return lstFinalList;
    }

    private void filterWithSecurity(List<TimeBlockHistoryDetail> objectList) {
    	Iterator<? extends BusinessObject> itr = objectList.iterator();
    	List<TkRole> tkRoles = TkServiceLocator.getTkRoleService().getRoles(TKContext.getPrincipalId(), TKUtils.getCurrentDate());
		while(itr.hasNext()){
			TimeBlockHistoryDetail tbhd = (TimeBlockHistoryDetail)itr.next();
			Job job = TkServiceLocator.getJobService().getJob(tbhd.getTimeBlockHistory().getPrincipalId(), tbhd.getTimeBlockHistory().getJobNumber(), TKUtils.getCurrentDate(), false);
			boolean valid = false;
			for (TkRole tkRole : tkRoles) {
				if (StringUtils.equals(tkRole.getRoleName(),
						TkConstants.ROLE_TK_SYS_ADMIN)
						|| (StringUtils.equals(tkRole.getRoleName(), TkConstants.ROLE_TK_GLOBAL_VO))
						|| (StringUtils.equals(tkRole.getRoleName(),
								TkConstants.ROLE_TK_APPROVER) && tbhd.getTimeBlockHistory().getWorkArea().equals(tkRole.getWorkArea()))
						|| (StringUtils.equals(tkRole.getRoleName(),
								TkConstants.ROLE_TK_DEPT_ADMIN) && (job != null && (job.getDept().equals(tkRole.getDepartment()))))) {
					valid = true;
					break;
				}
				if(StringUtils.equals(tkRole.getRoleName(), TkConstants.ROLE_TK_LOCATION_ADMIN) && job != null && tkRole.getLocationObj()!=null){
					List<Department> departments = TkServiceLocator.getDepartmentService().getDepartmentByLocation(tkRole.getLocationObj().getLocation());
					for(Department department : departments){
						if(StringUtils.equals(job.getDept(), department.getDept())){
							valid = true;
							break;
						}
					}
					if(valid){
						break;
					}
				}
			}
			if (!valid) {
				itr.remove();
				continue;
			}
		}
    }
    
    private void sortByTimeBlockId(List<TimeBlockHistoryDetail> objectList) {
        Collections.sort(objectList, new Comparator<TimeBlockHistoryDetail>() { // Sort the Time Blocks
            @Override
            public int compare(TimeBlockHistoryDetail timeBlockHistory, TimeBlockHistoryDetail timeBlockHistory1) {
                return timeBlockHistory.getTimeBlockHistory().getTkTimeBlockId().compareTo(timeBlockHistory1.getTimeBlockHistory().getTkTimeBlockId());
            }
        });
    }
    
    public boolean inDateRange(Date asOfDate, String dateString) {
		try {
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			java.util.Date dateFrom = df.parse(TKUtils.getFromDateString(dateString));
			java.util.Date dateTo = df.parse(TKUtils.getToDateString(dateString));;
			
			if(dateString.indexOf("..") == 10) {
				if(asOfDate != null) {
					Interval range = new Interval(dateFrom.getTime(),dateTo.getTime());
					return range.contains(asOfDate.getTime());
				} else {
					return false;
				}
			} else{
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
}
