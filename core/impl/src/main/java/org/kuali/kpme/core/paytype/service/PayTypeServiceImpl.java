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
package org.kuali.kpme.core.paytype.service;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.paytype.PayType;
import org.kuali.kpme.core.api.paytype.PayTypeContract;
import org.kuali.kpme.core.api.paytype.service.PayTypeService;
import org.kuali.kpme.core.paytype.PayTypeBo;
import org.kuali.kpme.core.paytype.dao.PayTypeDao;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.krad.service.KRADServiceLocator;

public class PayTypeServiceImpl implements PayTypeService {

	private PayTypeDao payTypeDao;
    private static final ModelObjectUtils.Transformer<PayTypeBo, PayType> toPayType =
            new ModelObjectUtils.Transformer<PayTypeBo, PayType>() {
                public PayType transform(PayTypeBo input) {
                    return PayTypeBo.to(input);
                };
            };
    private static final ModelObjectUtils.Transformer<PayType, PayTypeBo> toPayTypeBo =
            new ModelObjectUtils.Transformer<PayType, PayTypeBo>() {
                public PayTypeBo transform(PayType input) {
                    return PayTypeBo.from(input);
                };
            };
	@Override
	public PayType saveOrUpdate(PayType payType) {
        if (payType == null) {
            return null;
        }
        PayTypeBo bo = KRADServiceLocator.getBusinessObjectService().save(PayTypeBo.from(payType));
		return PayTypeBo.to(bo);
	}

	@Override
	public List<PayType> saveOrUpdate(List<PayType> payTypeList) {
        if (CollectionUtils.isEmpty(payTypeList)) {
            return Collections.emptyList();
        }
        List<PayTypeBo> bos = ModelObjectUtils.transform(payTypeList, toPayTypeBo);
		bos = (List<PayTypeBo>)KRADServiceLocator.getBusinessObjectService().save(bos);
        return ModelObjectUtils.transform(bos, toPayType);
	}

	public void setPayTypeDao(PayTypeDao payTypeDao) {
		this.payTypeDao = payTypeDao;
	}

	@Override
	public PayType getPayType(String payType, LocalDate effectiveDate) {
		return PayTypeBo.to(getPayTypeBo(payType, effectiveDate));
	}

    protected PayTypeBo getPayTypeBo(String payType, LocalDate effectiveDate) {
        return payTypeDao.getPayType(payType, effectiveDate);
    }

    protected PayTypeBo getPayTypeBo(String hrPayTypeId) {
        return payTypeDao.getPayType(hrPayTypeId);
    }

	@Override
	public PayType getPayType(String hrPayTypeId) {
		return PayTypeBo.to(getPayTypeBo(hrPayTypeId));
	}
	
	@Override
	public int getPayTypeCount(String payType) {
		return payTypeDao.getPayTypeCount(payType);
	}

    @Override
    public List<PayType> getPayTypes(String payType, String regEarnCode, String descr, String location, String institution, String flsaStatus,
    		String payFrequency, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHist) {
        return ModelObjectUtils.transform(payTypeDao.getPayTypes(payType, regEarnCode, descr, location, institution, flsaStatus, payFrequency, fromEffdt, toEffdt, active, showHist), toPayType);
    }

}
