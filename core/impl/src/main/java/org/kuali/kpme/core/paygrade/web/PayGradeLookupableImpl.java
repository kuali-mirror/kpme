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
package org.kuali.kpme.core.paygrade.web;

import java.util.List;
import java.util.Map;

import org.kuali.kpme.core.api.paygrade.PayGrade;
import org.kuali.kpme.core.lookup.KPMELookupableImpl;
import org.kuali.kpme.core.paygrade.PayGradeBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.web.form.LookupForm;

public class PayGradeLookupableImpl extends KPMELookupableImpl {

	private static final long serialVersionUID = 5424446452538063763L;
    private static final ModelObjectUtils.Transformer<PayGrade, PayGradeBo> toPayGradeBo =
            new ModelObjectUtils.Transformer<PayGrade, PayGradeBo>() {
                public PayGradeBo transform(PayGrade input) {
                    return PayGradeBo.from(input);
                };
            };
    @Override
    public List<? extends BusinessObject> getSearchResults(LookupForm form, Map<String, String> searchCriteria, boolean unbounded) {
        String payGrade = searchCriteria.get("payGrade");
        String descr = searchCriteria.get("description");
        String active = searchCriteria.get("active");
        String showHistory = searchCriteria.get("history");
        String salGroup = searchCriteria.get("salGroup");  // KPME-2700

        return ModelObjectUtils.transform(HrServiceLocator.getPayGradeService().getPayGrades(payGrade, descr, salGroup, active, showHistory), toPayGradeBo);
    }
    
}