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
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.kuali.kpme.core.util.HrConstants;

public class AssignmentRow implements Serializable {
	
	private static final long serialVersionUID = 5550424832906084957L;
	
	private String descr;
	private String assignmentKey;
	private String cssClass;

	private EarnCodeSection earnCodeSection;
	private BigDecimal periodTotal = BigDecimal.ZERO;
	
	private List<AssignmentColumn> assignmentColumns = new ArrayList<AssignmentColumn>();
	
	public String getDescr() {
		return descr;
	}
	
	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getAssignmentKey() {
		return assignmentKey;
	}
	
	public void setAssignmentKey(String assignmentKey) {
		this.assignmentKey = assignmentKey;
	}
	
	public String getCssClass() {
		return cssClass;
	}
	
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public EarnCodeSection getEarnCodeSection() {
		return earnCodeSection;
	}
	
	public void setEarnCodeSection(EarnCodeSection earnCodeSection) {
		this.earnCodeSection = earnCodeSection;
	}
	
	public BigDecimal getPeriodTotal() {
		return periodTotal;
	}
	
	public void setPeriodTotal(BigDecimal periodTotal) {
		this.periodTotal = periodTotal;
	}

	public List<AssignmentColumn> getAssignmentColumns() {
		return assignmentColumns;
	}

	public void setAssignmentColumns(List<AssignmentColumn> assignmentColumns) {
		this.assignmentColumns = assignmentColumns;
	}

	public void addToAmount(int index, BigDecimal amount) {
		if (amount != null) {
			if (0 <= index && index < getAssignmentColumns().size()) {
				AssignmentColumn assignmentColumn = getAssignmentColumns().get(index);
				
				BigDecimal newAmount = assignmentColumn.getAmount().add(amount, HrConstants.MATH_CONTEXT);
				assignmentColumn.setAmount(newAmount);
			}
		}
	}
	
	public void addToTotal(int index, BigDecimal total) {
		if (total != null) {
			if (0 <= index && index < getAssignmentColumns().size()) {
				AssignmentColumn assignmentColumn = getAssignmentColumns().get(index);

				BigDecimal newTotal = assignmentColumn.getTotal().add(total, HrConstants.MATH_CONTEXT);
				assignmentColumn.setTotal(newTotal);
				getEarnCodeSection().addToTotal(index, total);
			}
		}
	}

	public void addWeeklyTotal(int index, int weekSize) {
		BigDecimal weeklyAmount = BigDecimal.ZERO;
		BigDecimal weeklyTotal = BigDecimal.ZERO;

		for (ListIterator<AssignmentColumn> iterator = getAssignmentColumns().listIterator(index); iterator.hasPrevious() && iterator.previousIndex() >= (index - weekSize); ) {
			AssignmentColumn assignmentColumn = iterator.previous();
			weeklyAmount = weeklyAmount.add(assignmentColumn.getAmount(), HrConstants.MATH_CONTEXT);
			weeklyTotal = weeklyTotal.add(assignmentColumn.getTotal(), HrConstants.MATH_CONTEXT);
		}

		if (0 <= index && index < getAssignmentColumns().size()) {
			AssignmentColumn assignmentColumn = getAssignmentColumns().get(index);
			assignmentColumn.setAmount(weeklyAmount);
			assignmentColumn.setTotal(weeklyTotal);
			assignmentColumn.setWeeklyTotal(true);
		}

		if (!getAssignmentColumns().isEmpty()) {
			AssignmentColumn assignmentColumn = getAssignmentColumns().get(getAssignmentColumns().size() - 1);

			BigDecimal periodTotal = assignmentColumn.getTotal().add(weeklyTotal, HrConstants.MATH_CONTEXT);
			assignmentColumn.setTotal(periodTotal);

			BigDecimal periodAmount = assignmentColumn.getAmount().add(weeklyAmount, HrConstants.MATH_CONTEXT);
			assignmentColumn.setAmount(periodAmount);

			assignmentColumn.setWeeklyTotal(true);
		}
	}
	
}
