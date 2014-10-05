package org.kuali.kpme.edo.admin.web;


import org.kuali.kpme.edo.base.web.EdoForm;

public class EdoBackdoorForm extends EdoForm {

	private static final long serialVersionUID = -6073264977998783421L;
	private String principalName;
	private Boolean isAdmin;
	private String targetUrl;
	private String returnUrl;

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}
    
	public Boolean getIsAdmin() {
        return isAdmin;
    }
    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
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