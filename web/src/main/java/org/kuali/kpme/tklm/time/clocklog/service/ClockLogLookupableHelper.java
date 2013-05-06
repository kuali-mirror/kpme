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
package org.kuali.kpme.tklm.time.clocklog.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.department.Department;
import org.kuali.kpme.core.bo.job.Job;
import org.kuali.kpme.core.lookup.KPMELookupableHelper;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.time.clocklog.ClockLog;
import org.kuali.kpme.tklm.time.missedpunch.MissedPunchDocument;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.kpme.tklm.time.workflow.service.TimesheetDocumentHeaderService;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

@SuppressWarnings("deprecation")
public class ClockLogLookupableHelper extends KPMELookupableHelper {

	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(businessObject, pkNames);
		
		ClockLog clockLog = (ClockLog)businessObject;
		String tkClockLogId = clockLog.getTkClockLogId();
		
		MissedPunchDocument mpDoc = TkServiceLocator.getMissedPunchService().getMissedPunchByClockLogId(clockLog.getTkClockLogId());
		if (mpDoc != null) {
			clockLog.setMissedPunchDocumentId(mpDoc.getDocumentNumber());
		}
		
		Properties params = new Properties();
		params.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, getBusinessObjectClass().getName());
		params.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);
		params.put("tkClockLogId", tkClockLogId);
		AnchorHtmlData viewUrl = new AnchorHtmlData(UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, params), "view");
		viewUrl.setDisplayText("view");
		viewUrl.setTarget(AnchorHtmlData.TARGET_BLANK);
		customActionUrls.add(viewUrl);
		
		return customActionUrls;
	}

	@Override
	public List<? extends BusinessObject> getSearchResults(
			Map<String, String> fieldValues) {
		List<? extends BusinessObject> objectList = new ArrayList<BusinessObject>();
				
		
		TimesheetDocumentHeaderService timesheetDocumentHeaderService = TkServiceLocator.getTimesheetDocumentHeaderService();
		
		// search if documentid is given for search critria.
		String documentId =fieldValues.get("documentId");
		if(documentId != null && StringUtils.isNotEmpty(documentId)) {
			fieldValues.remove("documentId");

			TimesheetDocumentHeader timesheetDocumentHeader = timesheetDocumentHeaderService.getDocumentHeader(documentId);
			if(timesheetDocumentHeader == null) {
				objectList = new ArrayList<BusinessObject>();
			} else {
				String timesheetUserId = timesheetDocumentHeader.getPrincipalId();
				Date beginDate =  timesheetDocumentHeader.getBeginDate();
				Date endDate =  timesheetDocumentHeader.getEndDate();
				objectList = super.getSearchResultsUnbounded(fieldValues);
				Iterator itr = objectList.iterator();
				while (itr.hasNext()) {
					ClockLog cl = (ClockLog) itr.next();
					cl.setDocumentId(timesheetUserId);
					if(cl.getPrincipalId().equalsIgnoreCase(timesheetUserId)) {
						if(new Date(cl.getClockTimestamp().getTime()).compareTo(beginDate) >= 0 && new Date(cl.getClockTimestamp().getTime()).compareTo(endDate) <= 0) {
							continue;
						} else {
							itr.remove();
						}
					} else {
						itr.remove();
					}
				}
			}
		} else {
			objectList = super.getSearchResults(fieldValues);
		}
		
		if (!objectList.isEmpty()) {
			Iterator itr = objectList.iterator();
			while (itr.hasNext()) {
				ClockLog cl = (ClockLog) itr.next();
				
				// Set Document Id 
				if(cl.getDocumentId() == null) {
					TimesheetDocumentHeader tsdh = timesheetDocumentHeaderService.getDocumentHeaderForDate(cl.getPrincipalId(), cl.getClockDateTime());
					if(tsdh != null) {
						cl.setDocumentId(tsdh.getDocumentId());
					}
				}
				
				Job job = HrServiceLocator.getJobService().getJob(cl.getPrincipalId(), cl.getJobNumber(), LocalDate.now(), false);
				String department = job != null ? job.getDept() : null;
				
				Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, LocalDate.now());
				String location = departmentObj != null ? departmentObj.getLocation() : null;
				
				boolean valid = false;
				if (HrServiceLocator.getHRGroupService().isMemberOfSystemAdministratorGroup(HrContext.getPrincipalId(), new DateTime())
						|| HrServiceLocator.getHRRoleService().principalHasRoleInWorkArea(HrContext.getPrincipalId(), KPMERole.APPROVER.getRoleName(), cl.getWorkArea(), new DateTime())
						|| TkServiceLocator.getTKRoleService().principalHasRoleInDepartment(HrContext.getPrincipalId(), KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, new DateTime())
						|| LmServiceLocator.getLMRoleService().principalHasRoleInDepartment(HrContext.getPrincipalId(), KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, new DateTime())
						|| TkServiceLocator.getTKRoleService().principalHasRoleInLocation(HrContext.getPrincipalId(), KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), location, new DateTime())
						|| LmServiceLocator.getLMRoleService().principalHasRoleInLocation(HrContext.getPrincipalId(), KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), location, new DateTime())) {
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
	
	
}
