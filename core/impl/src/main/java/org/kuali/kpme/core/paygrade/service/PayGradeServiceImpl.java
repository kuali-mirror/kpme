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
package org.kuali.kpme.core.paygrade.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.paygrade.PayGrade;
import org.kuali.kpme.core.api.paygrade.service.PayGradeService;
import org.kuali.kpme.core.paygrade.PayGradeBo;
import org.kuali.kpme.core.paygrade.dao.PayGradeDao;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

public class PayGradeServiceImpl implements PayGradeService {

	private PayGradeDao payGradeDao;
    private static final ModelObjectUtils.Transformer<PayGradeBo, PayGrade> toPayGrade =
            new ModelObjectUtils.Transformer<PayGradeBo, PayGrade>() {
                public PayGrade transform(PayGradeBo input) {
                    return PayGradeBo.to(input);
                };
            };
	@Override
	public PayGrade getPayGrade(String payGrade, String salGroup, LocalDate asOfDate) {
		return PayGradeBo.to(getPayGradeBo(payGrade, salGroup, asOfDate));
	}

    protected PayGradeBo getPayGradeBo(String hrPayGradeId) {
        return payGradeDao.getPayGrade(hrPayGradeId);
    }

    protected PayGradeBo getPayGradeBo(String payGrade, String salGroup, LocalDate asOfDate) {
        return payGradeDao.getPayGrade(payGrade, salGroup, asOfDate);
    }
 
	public void setPayGradeDao(PayGradeDao payGradeDao) {
		this.payGradeDao = payGradeDao;
	}

	@Override
	public PayGrade getPayGrade(String hrPayGradeId) {
		return PayGradeBo.to(getPayGradeBo(hrPayGradeId));
	}
	@Override
	public int getPayGradeCount(String payGrade) {
		return payGradeDao.getPayGradeCount(payGrade);
	}

    @Override
    public List<PayGrade> getPayGrades(String payGrade, String payGradeDescr, String salGroup, String active, String showHistory) {
        return ModelObjectUtils.transform(payGradeDao.getPayGrades(payGrade, payGradeDescr, salGroup, active, showHistory), toPayGrade);
    }

}
