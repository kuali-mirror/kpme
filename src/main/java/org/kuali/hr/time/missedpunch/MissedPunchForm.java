package org.kuali.hr.time.missedpunch;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kns.util.ActionFormUtilMap;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.WebUtils;
import org.kuali.rice.kns.web.struts.form.KualiTransactionalDocumentFormBase;

public class MissedPunchForm extends KualiTransactionalDocumentFormBase {

    private static final long serialVersionUID = 1L;
    private Boolean assignmentReadOnly = false;

    @Override
    public String getBackLocation() {
        return "Clock.do?methodToCall=closeMissedPunchDoc";
    }

    @Override
    public void populate(HttpServletRequest request) {
        super.populate(request);
        ((ActionFormUtilMap) GlobalVariables.getKualiForm().getActionFormUtilMap()).setCacheValueFinderResults(false);

        if (this.getMethodToCall() == null || StringUtils.isEmpty(this.getMethodToCall())) {
            setMethodToCall(WebUtils.parseMethodToCall(this, request));
        }
    }

	public boolean isAssignmentReadOnly() {
		return assignmentReadOnly;
	}

	public void setAssignmentReadOnly(boolean assignmentReadOnly) {
		this.assignmentReadOnly = assignmentReadOnly;
	}

}
