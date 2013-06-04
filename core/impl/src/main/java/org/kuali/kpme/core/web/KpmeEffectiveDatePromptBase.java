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

import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.rules.PromptBeforeValidationBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.document.Document;

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
        boolean askQuestion = futureEffectiveDateExists(bo);
        if (askQuestion) {
            String questionText = CoreApiServiceLocator.getKualiConfigurationService().getPropertyValueAsString("warning.future.eff.date");
            boolean confirm = super.askOrAnalyzeYesNoQuestion("FutureEffectiveDateRecordExists", questionText);
            if (!confirm) {
                super.abortRulesCheck();
            }

        }
        return true;
    }

    protected abstract boolean futureEffectiveDateExists(PersistableBusinessObject bo);


}
