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
package org.kuali.kpme.tklm.time.timesummary;

import java.io.Serializable;
import java.math.BigDecimal;

public class AssignmentColumn implements Serializable {

	private static final long serialVersionUID = 879042466570649820L;
	
	private String cssClass;
	private BigDecimal amount = BigDecimal.ZERO;
	private BigDecimal total = BigDecimal.ZERO;
	private boolean weeklyTotal;
	
	public String getCssClass() {
		return cssClass;
	}
	
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public BigDecimal getTotal() {
		return total;
	}
	
	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public boolean isWeeklyTotal() {
		return weeklyTotal;
	}

	public void setWeeklyTotal(boolean weeklyTotal) {
		this.weeklyTotal = weeklyTotal;
	}

}