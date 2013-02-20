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
package org.kuali.hr.time.batch.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.service.base.TkServiceLocator;

public class CarryOverBatchJobAction extends TkAction {

    public ActionForward runCarryOverBatchJob(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	CarryOverBatchJobActionForm cobjaf = (CarryOverBatchJobActionForm) form;

    	LeavePlan leavePlan = TkServiceLocator.getLeavePlanService().getLeavePlan(cobjaf.getLeavePlan(), new java.sql.Date(System.currentTimeMillis()));
    	Date scheduleDate = new Date();
    	
    	TkServiceLocator.getBatchJobService().scheduleLeaveCarryOverJobs(leavePlan, scheduleDate);
    	
    	return mapping.findForward("basic");
    }

}