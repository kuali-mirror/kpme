package org.kuali.hr.time.admin.web;

import org.kuali.hr.time.base.web.TkForm;

public class ChangeTargetPersonForm extends TkForm {

	private static final long serialVersionUID = -8307585413793442914L;

	private String principalName;
    
    private String targetUrl;
    private String returnUrl;
    
	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}
	
    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

	public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

}