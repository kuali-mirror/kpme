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
package org.kuali.hr.time.admin.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.roles.TkUserRoles;
import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

public class ChangeTargetPersonAction extends TkAction {
	
	private static final Logger LOG = Logger.getLogger(ChangeTargetPersonAction.class);
	
    public ActionForward changeTargetPerson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = mapping.findForward("basic");
    	
    	ChangeTargetPersonForm changeTargetPersonForm = (ChangeTargetPersonForm) form;

        if (StringUtils.isNotBlank(changeTargetPersonForm.getPrincipalName())) {
        	Principal targetPerson = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(changeTargetPersonForm.getPrincipalName());
        	
	        if (targetPerson != null) {
	        	UserRoles roles = TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId());
	            if (roles.isSystemAdmin()
	                	|| roles.isGlobalViewOnly()
	                	|| roles.isDepartmentAdminForPerson(targetPerson.getPrincipalId())
	                	|| roles.isDeptViewOnlyForPerson(targetPerson.getPrincipalId())
	                	|| roles.isLocationAdminForPerson(targetPerson.getPrincipalId())
	                	|| roles.isTimesheetReviewerForPerson(targetPerson.getPrincipalId())
	                	|| roles.isApproverForPerson(targetPerson.getPrincipalId())) {
		                	
	            	TKUser.setTargetPerson(targetPerson.getPrincipalId());
	
		            if (StringUtils.isNotEmpty(changeTargetPersonForm.getReturnUrl())) {
		            	GlobalVariables.getUserSession().addObject(TkConstants.TK_TARGET_USER_RETURN, changeTargetPersonForm.getReturnUrl());
		            }
		            
		            String returnAction = "PersonInfo.do";
		            if (StringUtils.isNotEmpty(changeTargetPersonForm.getTargetUrl())) {
		                returnAction = changeTargetPersonForm.getTargetUrl();
		            }
		            forward = new ActionRedirect(returnAction);
		
		            LOG.debug(GlobalVariables.getUserSession().getActualPerson().getPrincipalName() + " changed target person to " + targetPerson.getPrincipalName());
	            } else {
	                LOG.warn("Non-Admin user attempting to change target person.");
	                return mapping.findForward("unauthorized");
	            }
	        }
        }

        return forward;
    }
    
    public ActionForward clearTargetPerson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	TKUser.clearTargetUser();
        
        String returnAction = "PersonInfo.do";
        if (StringUtils.isNotBlank((String) GlobalVariables.getUserSession().retrieveObject(TkConstants.TK_TARGET_USER_RETURN))) {
        	returnAction = (String) GlobalVariables.getUserSession().retrieveObject(TkConstants.TK_TARGET_USER_RETURN);
        }
        
        LOG.debug(GlobalVariables.getUserSession().getActualPerson().getPrincipalName() + " cleared target person");

        return new ActionRedirect(returnAction);
    }

}