/**
 * Copyright 2004-2014 The Kuali Foundation
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