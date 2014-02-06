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
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.core.web.KPMEAction;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

public class CalculateLeaveAccrualsAction extends KPMEAction {
	
	private static final Logger LOG = Logger.getLogger(ChangeTargetPersonAction.class);
	
    public ActionForward runAccruals(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	CalculateLeaveAccrualsForm calculateLeaveAccrualsForm = (CalculateLeaveAccrualsForm) form;
    	
    	Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(calculateLeaveAccrualsForm.getPrincipalName());
		if (principal != null) {
    		if (StringUtils.isNotBlank(calculateLeaveAccrualsForm.getStartDate()) && StringUtils.isNotBlank(calculateLeaveAccrualsForm.getEndDate())) {
    			DateTime startDate = TKUtils.formatDateTimeString(calculateLeaveAccrualsForm.getStartDate());
    			DateTime endDate = TKUtils.formatDateTimeString(calculateLeaveAccrualsForm.getEndDate());
    	
    			LOG.debug("AccrualServiceImpl.runAccrual() called with Principal: " + principal.getPrincipalName() + " Start: " + startDate.toString() + " End: " + endDate.toString());
    			LmServiceLocator.getLeaveAccrualService().runAccrual(principal.getPrincipalId(), startDate, endDate, true);
    		} else {
    			LOG.debug("AccrualServiceImpl.runAccrual() called with Principal: " + principal.getPrincipalName());
    			LmServiceLocator.getLeaveAccrualService().runAccrual(principal.getPrincipalId());
    		}
		}
		calculateLeaveAccrualsForm.setMessage("Leave Accruals calculated sucessfully.");
    	return mapping.findForward("basic");
    }
    
    public ActionForward clearAccruals(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	CalculateLeaveAccrualsForm adminForm = (CalculateLeaveAccrualsForm) form;
    	
    	if (StringUtils.isNotBlank(adminForm.getPrincipalName())) {
    		LOG.debug("AccrualServiceImpl.clearAccrual() called with Principal: " + adminForm.getPrincipalName());
    		adminForm.setPrincipalName("");
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
