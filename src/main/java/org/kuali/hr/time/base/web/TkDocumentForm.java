package org.kuali.hr.time.base.web;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kns.util.ActionFormUtilMap;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.WebUtils;
import org.kuali.rice.kns.web.struts.form.KualiDocumentFormBase;

import javax.servlet.http.HttpServletRequest;

public class TkDocumentForm extends KualiDocumentFormBase {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	private String documentId;

	private String methodToCall;

	public String getMethodToCall() {
		return methodToCall;
	}

	public void setMethodToCall(String methodToCall) {
		this.methodToCall = methodToCall;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	@Override
	public boolean isPropertyEditable(String propertyName) {
		return true;
	}

	@Override
	public boolean isPropertyNonEditableButRequired(String propertyName) {
		return true;
	}

	@Override
	public void populate(HttpServletRequest request) {
        super.populate(request);
        ((ActionFormUtilMap) GlobalVariables.getKualiForm().getActionFormUtilMap()).setCacheValueFinderResults(false);

		if (this.getMethodToCall() == null || StringUtils.isEmpty(this.getMethodToCall())) {
			setMethodToCall(WebUtils.parseMethodToCall(this, request));
		}
	}
}
