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
package org.kuali.hr.tklm.time.batch.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.DateTime;
import org.kuali.hr.tklm.time.base.web.TkAction;
import org.kuali.hr.tklm.time.calendar.CalendarEntry;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.hr.tklm.time.util.TkConstants;

public class BatchJobAction extends TkAction {
    
    public ActionForward runBatchJob(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        BatchJobActionForm bjaf = (BatchJobActionForm) form;
        String batchJobName = bjaf.getSelectedBatchJob();

        CalendarEntry calendarEntry = TkServiceLocator.getCalendarEntryService().getCalendarEntry(bjaf.getHrPyCalendarEntryId());
        DateTime scheduleDate = new DateTime();
        
        if (StringUtils.equals(batchJobName, TkConstants.BATCH_JOB_NAMES.INITIATE)) {
        	TkServiceLocator.getBatchJobService().scheduleInitiateJobs(calendarEntry, scheduleDate);
        }
        
        if (StringUtils.equals(batchJobName, TkConstants.BATCH_JOB_NAMES.END_PAY_PERIOD)) {
        	TkServiceLocator.getBatchJobService().scheduleEndPayPeriodJobs(calendarEntry, scheduleDate);
        }
        
        if (StringUtils.equals(batchJobName, TkConstants.BATCH_JOB_NAMES.END_REPORTING_PERIOD)) {
        	TkServiceLocator.getBatchJobService().scheduleEndReportingPeriodJobs(calendarEntry, scheduleDate);
        }
        
        if (StringUtils.equals(batchJobName, TkConstants.BATCH_JOB_NAMES.EMPLOYEE_APPROVAL)) {
        	TkServiceLocator.getBatchJobService().scheduleEmployeeApprovalJobs(calendarEntry, scheduleDate);
        }
        
        if (StringUtils.equals(batchJobName, TkConstants.BATCH_JOB_NAMES.MISSED_PUNCH_APPROVAL)) {
        	TkServiceLocator.getBatchJobService().scheduleMissedPunchApprovalJobs(calendarEntry, scheduleDate);
        }
        
        if (StringUtils.equals(batchJobName, TkConstants.BATCH_JOB_NAMES.SUPERVISOR_APPROVAL)) {
        	TkServiceLocator.getBatchJobService().scheduleSupervisorApprovalJobs(calendarEntry, scheduleDate);
        }
        
        return mapping.findForward("basic");
    }

}