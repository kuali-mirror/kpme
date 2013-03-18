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
package org.kuali.hr.time.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.base.web.TkForm;
import org.kuali.hr.time.roles.TkUserRoles;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.util.GlobalVariables;

public class TimeAction extends TkAction {

	private static final Logger LOG = Logger.getLogger(TimeAction.class);

    @Override
    protected void checkTKAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
        TkForm tkForm = (TkForm) form;

        if (StringUtils.equals(methodToCall, "targetEmployee") || StringUtils.equals(methodToCall, "changeEmployee") || StringUtils.equals(methodToCall, "clearBackdoor") || StringUtils.equals(methodToCall, "clearChangeUser")) {
            // Handle security validation in targetEmployee action, we may need
            // to check the document for validity, since the user may not
            // necessarily be a system administrator.
        } else {
            if (!TKUser.isSystemAdmin()
        			&& !TKUser.isLocationAdmin()
        			&& !TKUser.isDepartmentAdmin()
        			&& !TKUser.isGlobalViewOnly()
        			&& !TKUser.isDeptViewOnly()
        			&& (tkForm.getDocumentId() != null && !TKUser.isApproverForTimesheet(tkForm.getDocumentId()))
        			&& (tkForm.getDocumentId() != null && !TKUser.isDocumentReadable(tkForm.getDocumentId())))  {
                throw new AuthorizationException("", "TimeAction", "");
            }
        }
    }

    
    @Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        boolean synch = TKUser.isSynchronous();
        if (TKUser.getCurrentTargetPerson() != null) {
            if (TKUser.isSystemAdmin()) {
                return new ActionRedirect("/portal.do");
            } else if (TKUser.isDepartmentAdmin()
                    && !synch) {
                return new ActionRedirect("/portal.do");
            } else if (TKUser.isApprover()
                    && !synch) {
                return new ActionRedirect("/TimeApproval.do");
            } else if (TKUser.isReviewer()
                    && !synch) {
                return new ActionRedirect("/TimeApproval.do");
            } else if (TKUser.isActiveEmployee()
                    && !synch) {
                return new ActionRedirect("/TimeDetail.do");
            } else if (synch) {
                return new ActionRedirect("/Clock.do");
            } else {
                return new ActionRedirect("/PersonInfo.do");
            }
        }
	return super.execute(mapping, form, request, response);
}
    
}
