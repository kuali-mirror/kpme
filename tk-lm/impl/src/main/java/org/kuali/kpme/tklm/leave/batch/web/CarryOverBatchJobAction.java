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
package org.kuali.kpme.tklm.leave.batch.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.leaveplan.LeavePlan;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.web.KPMEAction;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;

public class CarryOverBatchJobAction extends KPMEAction {

    public ActionForward runCarryOverBatchJob(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	CarryOverBatchJobActionForm cobjaf = (CarryOverBatchJobActionForm) form;

    	LeavePlan leavePlan = (LeavePlan) HrServiceLocator.getLeavePlanService().getLeavePlan(cobjaf.getLeavePlan(), LocalDate.now());
    	DateTime scheduleDate = new DateTime();
    	
    	TkServiceLocator.getBatchJobService().scheduleLeaveCarryOverJobs(leavePlan, scheduleDate);
    	
    	return mapping.findForward("basic");
    }

}
