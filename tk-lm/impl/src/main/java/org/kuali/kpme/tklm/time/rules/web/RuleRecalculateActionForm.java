package org.kuali.kpme.tklm.time.rules.web;

import org.kuali.kpme.core.web.KPMEForm;

public class RuleRecalculateActionForm extends KPMEForm {

    private static final long serialVersionUID = 690136386415512811L;
    private String ruleRecalculateDocumentId;

    public String getRuleRecalculateDocumentId() {
        return ruleRecalculateDocumentId;
    }

    public void setRuleRecalculateDocumentId(String ruleRecalculateDocumentId) {
        this.ruleRecalculateDocumentId = ruleRecalculateDocumentId;
    }

}