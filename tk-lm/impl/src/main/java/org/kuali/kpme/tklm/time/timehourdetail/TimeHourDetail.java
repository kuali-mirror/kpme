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
package org.kuali.kpme.tklm.time.timehourdetail;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class TimeHourDetail extends PersistableBusinessObjectBase{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String tkTimeHourDetailId;
	private String tkTimeBlockId;
	private String earnCode;
	private BigDecimal hours = HrConstants.BIG_DECIMAL_SCALED_ZERO;
	private BigDecimal amount = HrConstants.BIG_DECIMAL_SCALED_ZERO;

    public TimeHourDetail() {
    }

    protected TimeHourDetail(TimeHourDetail t) {
        // All of the following are Immutable, be aware if new fields
        // are added.
        this.tkTimeHourDetailId = t.tkTimeHourDetailId;
        this.tkTimeBlockId = t.tkTimeBlockId;
        this.earnCode = t.earnCode;
        this.hours = t.hours;
        this.amount = t.amount;
    }

    public TimeHourDetail copy() {
        return new TimeHourDetail(this);
    }

	public String getEarnCode() {
		return earnCode;
	}

	public void setEarnCode(String earnCode) {
		this.earnCode = earnCode;
	}

	public BigDecimal getHours() {
		return hours;
	}

	public void setHours(BigDecimal hours) {
		if(hours != null){
			this.hours = hours.setScale(HrConstants.BIG_DECIMAL_SCALE, HrConstants.BIG_DECIMAL_SCALE_ROUNDING);
		} else {
			this.hours = hours;
		}
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		if(amount != null){
			this.amount = amount.setScale(HrConstants.BIG_DECIMAL_SCALE, HrConstants.BIG_DECIMAL_SCALE_ROUNDING);
		} else {
			this.amount = amount;
		}
	}

	public String getTkTimeBlockId() {
		return tkTimeBlockId;
	}

	public void setTkTimeBlockId(String tkTimeBlockId) {
		this.tkTimeBlockId = tkTimeBlockId;
	}
	public void setTkTimeHourDetailId(String tkTimeHourDetailId) {
		this.tkTimeHourDetailId = tkTimeHourDetailId;
	}

	public String getTkTimeHourDetailId() {
		return tkTimeHourDetailId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) { 
			return false;
		}
		if (obj == this) { 
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		TimeHourDetail timeHourDetail = (TimeHourDetail) obj;
		return new EqualsBuilder()
			.append(earnCode, timeHourDetail.earnCode)
			.append(amount, timeHourDetail.amount)
			.append(hours, timeHourDetail.hours)
			.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31)
			.append(earnCode)
			.append(amount)
			.append(hours)
			.toHashCode();
	}

}
