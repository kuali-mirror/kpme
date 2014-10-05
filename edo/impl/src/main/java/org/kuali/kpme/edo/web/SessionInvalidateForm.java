package org.kuali.kpme.edo.web;

import org.apache.struts.action.ActionForm;
public class SessionInvalidateForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8460462702359558693L;
	private String methodToCall = "";

	public String getMethodToCall() {
		return methodToCall;
	}

	public void SetMethodToCall(String methodToCall) {
		this.methodToCall = methodToCall;
	}

}
