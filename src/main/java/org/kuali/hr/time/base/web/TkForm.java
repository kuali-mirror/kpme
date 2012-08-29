package org.kuali.hr.time.base.web;

import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.kns.web.struts.form.KualiForm;

public class TkForm extends KualiForm {

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

	public TKUser getUser() {
		return TKContext.getUser();
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
		return TKContext.getCurrentTimesheetDocumentId();
	}

    public String getDocumentStatus() {
        return TKContext.getCurrentTimesheetDoucment().getDocumentHeader().getDocumentStatus();
    }
    
    public boolean getLeaveEnabled() {
    	boolean canViewLeaveTab= false;
        canViewLeaveTab = this.getViewLeaveTabsWithNEStatus() || TkServiceLocator.getPermissionsService().canViewLeaveTabsWithEStatus();
        return canViewLeaveTab; 
    }
    
    public boolean getTimeEnabled() {
    	return TkServiceLocator.getPermissionsService().canViewTimeTabs();
    }
    
    public String getLeaveDocumentIdFromContext(){
		return TKContext.getCurrentLeaveCalendarDocumentId();
	}
 
    public boolean getViewLeaveTabsWithNEStatus() {
    	return TkServiceLocator.getPermissionsService().canViewLeaveTabsWithNEStatus();
    }
}
