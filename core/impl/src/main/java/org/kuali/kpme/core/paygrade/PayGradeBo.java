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
package org.kuali.kpme.core.paygrade;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.kuali.kpme.core.api.paygrade.PayGrade;
import org.kuali.kpme.core.api.paygrade.PayGradeContract;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.util.HrConstants;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class PayGradeBo extends HrBusinessObject implements PayGradeContract {

	private static final String SAL_GROUP = "salGroup";
	private static final String PAY_GRADE = "payGrade";
	
	private static final long serialVersionUID = -5736949952127760566L;
	//KPME-2273/1965 Primary Business Keys List.
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
            .add(PAY_GRADE)
            .add(SAL_GROUP)
            .build();

	public static final String CACHE_NAME = HrConstants.CacheNamespace.NAMESPACE_PREFIX + "PayGrade";

	private String hrPayGradeId;
	private String payGrade;
	private String description;
	private String salGroup;
    private String rateType;
    private BigDecimal minRate;
    private BigDecimal maxRate;
    private BigDecimal midPointRate;
    private BigDecimal maxHiringRate;

    
    @Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
    	return  new ImmutableMap.Builder<String, Object>()
			.put(PAY_GRADE, this.getPayGrade())
			.put(SAL_GROUP, this.getSalGroup())
			.build();
	}
    
    
    public String getHrPayGradeId() {
		return hrPayGradeId;
	}

	public void setHrPayGradeId(String hrPayGradeId) {
		this.hrPayGradeId = hrPayGradeId;
	}

	public String getPayGrade() {
		return payGrade;
	}

	public void setPayGrade(String payGrade) {
		this.payGrade = payGrade;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getUniqueKey() {
		return payGrade;
	}
	
	@Override
	public String getId() {
		return getHrPayGradeId();
	}

	@Override
	public void setId(String id) {
		setHrPayGradeId(id);
	}
	
	public String getSalGroup() {
		return salGroup;
	}

	public void setSalGroup(String salGroup) {
		this.salGroup = salGroup;
	}

    public String getRateType() {
        return rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    public BigDecimal getMinRate() {
        return minRate;
    }

    public void setMinRate(BigDecimal minRate) {
        this.minRate = minRate;
    }

    public BigDecimal getMaxRate() {
        return maxRate;
    }

    public void setMaxRate(BigDecimal maxRate) {
        this.maxRate = maxRate;
    }

    public BigDecimal getMidPointRate() {
        return midPointRate;
    }

    public void setMidPointRate(BigDecimal midPointRate) {
        this.midPointRate = midPointRate;
    }

    public BigDecimal getMaxHiringRate() {
        return maxHiringRate;
    }

    public void setMaxHiringRate(BigDecimal maxHiringRate) {
        this.maxHiringRate = maxHiringRate;
    }

    public static PayGradeBo from(PayGrade im) {
        if (im == null) {
            return null;
        }
        PayGradeBo pg = new PayGradeBo();

        pg.setHrPayGradeId(im.getHrPayGradeId());
        pg.setPayGrade(im.getPayGrade());
        pg.setDescription(im.getDescription());
        pg.setSalGroup(im.getSalGroup());
        pg.setRateType(im.getRateType());
        pg.setMinRate(im.getMinRate());
        pg.setMaxRate(im.getMaxRate());
        pg.setMidPointRate(im.getMidPointRate());
        pg.setMaxHiringRate(im.getMaxHiringRate());


        pg.setEffectiveDate(im.getEffectiveLocalDate() == null ? null : im.getEffectiveLocalDate().toDate());
        pg.setActive(im.isActive());
        if (im.getCreateTime() != null) {
            pg.setTimestamp(new Timestamp(im.getCreateTime().getMillis()));
        }
        pg.setUserPrincipalId(im.getUserPrincipalId());
        pg.setVersionNumber(im.getVersionNumber());
        pg.setObjectId(im.getObjectId());

        return pg;
    }

    public static PayGrade to(PayGradeBo bo) {
        if (bo == null) {
            return null;
        }

        return PayGrade.Builder.create(bo).build();
    }
}