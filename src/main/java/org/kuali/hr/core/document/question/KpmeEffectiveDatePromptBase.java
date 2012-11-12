package org.kuali.hr.core.document.question;

import org.apache.commons.lang.time.DateUtils;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.rules.PromptBeforeValidationBase;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.service.KRADServiceLocator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class KpmeEffectiveDatePromptBase extends PromptBeforeValidationBase {
    @Override
    public boolean doPrompts(Document document) {
        boolean preRulesOK = true;
        preRulesOK &= conditionallyAskQuestion(document);
        return preRulesOK;
    }

    protected boolean conditionallyAskQuestion(Document document) {
        MaintenanceDocument maintenanceDocument = (MaintenanceDocument)document;
        PersistableBusinessObject bo = (PersistableBusinessObject)maintenanceDocument.getNewMaintainableObject().getDataObject();
        Class clazz = bo.getClass();
        boolean askQuestion = futureEffectiveDateExists(bo);
        if (askQuestion) {
            String questionText = KRADServiceLocator.getKualiConfigurationService().getPropertyValueAsString("warning.future.eff.date");
            boolean confirm = super.askOrAnalyzeYesNoQuestion("FutureEffectiveDateRecordExists", questionText);
            if (!confirm) {
                super.abortRulesCheck();
            }

        }
        return true;
    }

    protected abstract boolean futureEffectiveDateExists(PersistableBusinessObject bo);


}
