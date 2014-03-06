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
package org.kuali.kpme.tklm.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.DateTime;
import org.kuali.kpme.core.api.leaveplan.LeavePlan;
import org.kuali.kpme.core.leaveplan.LeavePlanBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.core.web.KPMEAction;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

public class CalculateLeaveAccrualsAction extends KPMEAction {
	
	private static final Logger LOG = Logger.getLogger(ChangeTargetPersonAction.class);
	
    public ActionForward runAccruals(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	CalculateLeaveAccrualsForm calculateLeaveAccrualsForm = (CalculateLeaveAccrualsForm) form;
    	String messagePrefix = "AccrualServiceImpl.runAccrual() called with ";
    	String messageParameters = "";
    	if(StringUtils.isNotBlank(calculateLeaveAccrualsForm.getPrincipalName())) {
    		Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(calculateLeaveAccrualsForm.getPrincipalName());
    		if (principal != null) {
        		if (StringUtils.isNotBlank(calculateLeaveAccrualsForm.getStartDate()) && StringUtils.isNotBlank(calculateLeaveAccrualsForm.getEndDate())) {
        			DateTime startDate = TKUtils.formatDateTimeString(calculateLeaveAccrualsForm.getStartDate());
        			DateTime endDate = TKUtils.formatDateTimeString(calculateLeaveAccrualsForm.getEndDate());
        			messageParameters = "Principal: " + principal.getPrincipalName() + " Start Date: " + TKUtils.formatDateTimeShort(startDate) + " End Date: " + TKUtils.formatDateTimeShort(endDate);
        			LOG.debug(messagePrefix + messageParameters);
        			LmServiceLocator.getLeaveAccrualService().runAccrual(principal.getPrincipalId(), startDate, endDate, true);
        		} else {
        			messageParameters = "Principal: " + principal.getPrincipalName();
        			LOG.debug(messagePrefix + messageParameters);
        			LmServiceLocator.getLeaveAccrualService().runAccrual(principal.getPrincipalId());
        		}
    		}	
    	} else if(StringUtils.isNotBlank(calculateLeaveAccrualsForm.getLeavePlanId())) {
    		LeavePlan aLeavePlan = HrServiceLocator.getLeavePlanService().getLeavePlan(calculateLeaveAccrualsForm.getLeavePlanId());
    		if (aLeavePlan != null) {
        		if (StringUtils.isNotBlank(calculateLeaveAccrualsForm.getStartDate()) && StringUtils.isNotBlank(calculateLeaveAccrualsForm.getEndDate())) {
        			DateTime startDate = TKUtils.formatDateTimeString(calculateLeaveAccrualsForm.getStartDate());
        			DateTime endDate = TKUtils.formatDateTimeString(calculateLeaveAccrualsForm.getEndDate());
        			messageParameters = "Leave Plan: " + aLeavePlan.getLeavePlan() + " Start Date: " + TKUtils.formatDateTimeShort(startDate) + " End Date: " + TKUtils.formatDateTimeShort(endDate);
        			LOG.debug(messagePrefix + messageParameters);
        			LmServiceLocator.getLeaveAccrualService().runAccrualForLeavePlan(aLeavePlan, startDate, endDate, true);
        		} else {
        			messageParameters = "Leave Plan: " + aLeavePlan.getLeavePlan();
        			LOG.debug(messagePrefix + messageParameters);
        			LmServiceLocator.getLeaveAccrualService().runAccrualForLeavePlan(aLeavePlan, null, null, true);
        		}
    		}	
    		
    	}
    	
		calculateLeaveAccrualsForm.setMessage("Leave accrual calculation has been submitted with " + messageParameters);
    	return mapping.findForward("basic");
    }
    
    public ActionForward clearAccruals(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	CalculateLeaveAccrualsForm adminForm = (CalculateLeaveAccrualsForm) form;
    	
    	if (StringUtils.isNotBlank(adminForm.getPrincipalName())) {
    		LOG.debug("AccrualServiceImpl.clearAccrual() called with Principal: " + adminForm.getPrincipalName());
    		adminForm.setPrincipalName("");
    	} 
    	if (StringUtils.isNotBlank(adminForm.getLeavePlanId())) {
    		LOG.debug("AccrualServiceImpl.clearAccrual() called with LeavePlanId: " + adminForm.getLeavePlanId());
    		adminForm.setLeavePlanId("");
    	} 
    	if (StringUtils.isNotBlank(adminForm.getStartDate())) {
    		LOG.debug("AccrualServiceImpl.clearAccrual() called with Start Date: " + adminForm.getStartDate());
    		adminForm.setStartDate("");
    	} 
    	if (StringUtils.isNotBlank(adminForm.getEndDate())) {
    		LOG.debug("AccrualServiceImpl.clearAccrual() called with End Date: " + adminForm.getEndDate());
    		adminForm.setEndDate("");
    	} 
    	
    	return mapping.findForward("basic");
    }

}
