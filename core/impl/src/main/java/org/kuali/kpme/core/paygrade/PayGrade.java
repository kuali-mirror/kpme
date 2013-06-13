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
package org.kuali.kpme.core.paygrade;

import java.math.BigDecimal;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.institution.Institution;
import org.kuali.kpme.core.salarygroup.SalaryGroup;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.rice.location.impl.campus.CampusBo;

import com.google.common.collect.ImmutableList;

public class PayGrade extends HrBusinessObject {

	private static final long serialVersionUID = -5736949952127760566L;
	//KPME-2273/1965 Primary Business Keys List.
	public static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
            .add("payGrade")            
            .build();

	public static final String CACHE_NAME = HrConstants.CacheNamespace.NAMESPACE_PREFIX + "PayGrade";

	private String hrPayGradeId;
	private String payGrade;
	private String description;
	private String userPrincipalId;
	private String salGroup;
    private String institution;
    private String location;
    private String rateType;
    private BigDecimal minRate;
    private BigDecimal maxRate;
    private BigDecimal midPointRate;
    private BigDecimal maxHiringRate;
    private String history;
 
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

	public void setUserPrincipalId(String userPrincipalId) {
		this.userPrincipalId = userPrincipalId;
	}

	public String getUserPrincipalId() {
		return userPrincipalId;
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

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
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

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}