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
package org.kuali.kpme.tklm.time.clocklog.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.bo.department.Department;
import org.kuali.kpme.core.bo.job.Job;
import org.kuali.kpme.core.lookup.KPMELookupableHelper;
import org.kuali.kpme.core.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.time.clocklog.ClockLog;
import org.kuali.kpme.tklm.time.missedpunch.MissedPunch;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

@SuppressWarnings("deprecation")
public class ClockLogLookupableHelper extends KPMELookupableHelper {

    private static final long serialVersionUID = -469827905426221716L;

    private static final String DOCUMENT_ID = "documentId";

    @Override
    @SuppressWarnings("rawtypes")
    public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
        List<HtmlData> customActionUrls = super.getCustomActionUrls(businessObject, pkNames);

		ClockLog clockLog = (ClockLog)businessObject;
		String tkClockLogId = clockLog.getTkClockLogId();

		MissedPunch missedPunch = TkServiceLocator.getMissedPunchService().getMissedPunchByClockLogId(clockLog.getTkClockLogId());
		if (missedPunch != null) {
			clockLog.setClockedByMissedPunch(true);
        }

        Properties params = new Properties();
        params.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, getBusinessObjectClass().getName());
        params.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);
        params.put("tkClockLogId", tkClockLogId);
		HtmlData.AnchorHtmlData viewUrl = new HtmlData.AnchorHtmlData(UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, params), "view");
        viewUrl.setDisplayText("view");
		viewUrl.setTarget(HtmlData.AnchorHtmlData.TARGET_BLANK);
        customActionUrls.add(viewUrl);

        return customActionUrls;
    }

    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
        List<ClockLog> results = new ArrayList<ClockLog>();

        String documentId = StringUtils.EMPTY;

        if (fieldValues.containsKey(DOCUMENT_ID)) {
            documentId = fieldValues.get(DOCUMENT_ID);
            fieldValues.remove(DOCUMENT_ID);
        }

        List<? extends BusinessObject> searchResults = super.getSearchResults(fieldValues);

        for (BusinessObject searchResult : searchResults) {
            ClockLog clockLog = (ClockLog) searchResult;
            results.add(clockLog);
        }

        results = filterByDocumentId(results, documentId);
        results = filterByPrincipalId(results, GlobalVariables.getUserSession().getPrincipalId());

        return results;
    }

    private List<ClockLog> filterByDocumentId(List<ClockLog> clockLogs, String documentId) {
        List<ClockLog> results = new ArrayList<ClockLog>();

        if (StringUtils.isNotEmpty(documentId)) {
            TimesheetDocumentHeader timesheetDocumentHeader = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(documentId);
            if (timesheetDocumentHeader != null) {
                String principalId = timesheetDocumentHeader.getPrincipalId();
                DateTime beginDate =  timesheetDocumentHeader.getBeginDateTime();
                DateTime endDate =  timesheetDocumentHeader.getEndDateTime();

                for (ClockLog clockLog : clockLogs) {
                    clockLog.setDocumentId(principalId);
                    if (clockLog.getPrincipalId().equalsIgnoreCase(principalId)) {
                        if (clockLog.getClockDateTime().compareTo(beginDate) >= 0 && clockLog.getClockDateTime().compareTo(endDate) <= 0) {
                            results.add(clockLog);
                        }
                    }
                }
            }
        } else {
            results.addAll(clockLogs);
        }

        return results;
    }

    private List<ClockLog> filterByPrincipalId(List<ClockLog> clockLogs, String principalId) {
        List<ClockLog> results = new ArrayList<ClockLog>();

        for (ClockLog clockLog : clockLogs) {
            if (clockLog.getDocumentId() == null) {
                TimesheetDocumentHeader tsdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeaderForDate(clockLog.getPrincipalId(), clockLog.getClockDateTime());
                if (tsdh != null) {
                    clockLog.setDocumentId(tsdh.getDocumentId());
                }
            }

            Job jobObj = HrServiceLocator.getJobService().getJob(clockLog.getPrincipalId(), clockLog.getJobNumber(), LocalDate.now(), false);
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
                results.add(clockLog);
            }
        }

        return results;
    }
}
