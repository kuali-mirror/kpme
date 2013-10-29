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

import org.kuali.kpme.core.api.bo.service.HrBusinessObjectService;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.rules.PromptBeforeValidationBase;
import org.kuali.rice.krad.document.Document;

@SuppressWarnings("deprecation")
public class KPMEHrObjectNewerVersionPromptBase extends PromptBeforeValidationBase {
    private static final String FUTURE_EFFECTIVE_DATE_RECORD_EXISTS_QN_ID = "FutureEffectiveDateRecordExists";
	private static final String WARNING_FUTURE_EFF_DATE_KEY = "warning.future.eff.date";
	private static final String HR_BUSINESS_OBJECT_SERVICE_NAME = "hrBusinessObjectService";

	@Override
    public boolean doPrompts(Document document) {
        boolean preRulesOK = true;
        preRulesOK &= conditionallyAskQuestion(document);
        return preRulesOK;
    }
    
    protected HrBusinessObject getNewHrBusinessObjectInstance(MaintenanceDocument document) {
		return (HrBusinessObject) document.getNewMaintainableObject().getDataObject();
	}

    protected boolean conditionallyAskQuestion(Document document) {
        HrBusinessObject newBo =  this.getNewHrBusinessObjectInstance((MaintenanceDocument) document);
        boolean askQuestion = this.doesNewerVersionExist(newBo);
        if (askQuestion) {
            String questionText = CoreApiServiceLocator.getKualiConfigurationService().getPropertyValueAsString(WARNING_FUTURE_EFF_DATE_KEY);
            boolean confirm = super.askOrAnalyzeYesNoQuestion(FUTURE_EFFECTIVE_DATE_RECORD_EXISTS_QN_ID, questionText);
            if (!confirm) {
                super.abortRulesCheck();
            }

        }
        return true;
    }
    
    protected String getHrBusinessObjectServiceName() {
		return HR_BUSINESS_OBJECT_SERVICE_NAME;
	}

    protected boolean doesNewerVersionExist(HrBusinessObject newBo) {
    	// use the hr BO service to check if a new version exists
		HrBusinessObjectService hrBOService = HrServiceLocator.getService(getHrBusinessObjectServiceName());
		return hrBOService.doesNewerVersionExist(newBo);
    }


}
