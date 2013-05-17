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
package org.kuali.kpme.core.web;

import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.kns.web.struts.form.KualiForm;

public class KPMEForm extends KualiForm {

	private static final long serialVersionUID = -3945893347262537122L;

	private String methodToCall;
	private String principalId;
    private String documentId;

	public String getMethodToCall() {
		return methodToCall;
	}

	public void setMethodToCall(String methodToCall) {
		this.methodToCall = methodToCall;
	}

	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}
	
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
	
	public String getWorkflowUrl(){
		return ConfigContext.getCurrentContextConfig().getProperty("workflow.url");
	}
	
	public String getDocumentIdFromContext(){
		return HrContext.getCurrentTimesheetDocumentId();
	}

    public String getDocumentStatus() {
        return HrContext.getCurrentTimesheetDocument().getDocumentHeader().getDocumentStatus();
    }

    public String getLeaveCalendarDocumentStatus() {
        return HrContext.getCurrentLeaveCalendarDocument().getDocumentHeader().getDocumentStatus();
    }
    
    public boolean getLeaveEnabled() {
    	boolean canViewLeaveTab= false;
        canViewLeaveTab = this.getViewLeaveTabsWithNEStatus() || HrServiceLocator.getHRPermissionService().canViewLeaveTabsWithEStatus();
        return canViewLeaveTab; 
    }
    
    public boolean getTimeEnabled() {
    	return HrServiceLocator.getHRPermissionService().canViewTimeTabs();
    }
    
    public String getLeaveDocumentIdFromContext(){
		return HrContext.getCurrentLeaveCalendarDocumentId();
	}
 
    public boolean getViewLeaveTabsWithNEStatus() {
    	return HrServiceLocator.getHRPermissionService().canViewLeaveTabsWithNEStatus();
    }
}
