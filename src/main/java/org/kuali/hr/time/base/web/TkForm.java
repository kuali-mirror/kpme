package org.kuali.hr.time.base.web;

import java.sql.Date;

import org.kuali.hr.time.permissions.TkPermissionsServiceImpl;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.core.config.ConfigContext;
import org.kuali.rice.kns.web.struts.form.KualiForm;

public class TkForm extends KualiForm {

	/**
     *
     */
	private static final long serialVersionUID = -3945893347262537122L;

	private String methodToCall;
	private String principalId;
	

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
        String principalId = TKContext.getUser().getPrincipalId();
        Date asOfDate = TKUtils.getTimelessDate(null);
        PrincipalHRAttributes principalHRAttributes = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, asOfDate);
        if(principalHRAttributes != null && principalHRAttributes.getLeavePlan() != null) {
        	canViewLeaveTab = true;
        }
        return canViewLeaveTab; 
    }
    
    public String getLeaveDocumentIdFromContext(){
		return TKContext.getCurrentLeaveCalendarDocumentId();
	}
    
}
