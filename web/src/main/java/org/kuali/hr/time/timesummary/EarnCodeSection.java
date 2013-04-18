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
package org.kuali.hr.time.timesummary;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.hr.time.util.TkConstants;

public class EarnCodeSection implements Serializable {
	private String earnCode;
	private String description;
	private Map<String, AssignmentRow> assignKeyToAssignmentRowMap = new HashMap<String, AssignmentRow>();
	private List<AssignmentRow> assignmentsRows = new ArrayList<AssignmentRow>();
	private List<BigDecimal> totals = new ArrayList<BigDecimal>();
	private Boolean isAmountEarnCode = Boolean.FALSE;;
	
	private EarnGroupSection earnGroupSection;
	
	public String getEarnCode() {
		return earnCode;
	}
	public void setEarnCode(String earnCode) {
		this.earnCode = earnCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void addAssignmentRow(AssignmentRow assignRow){
		for(int i = 0;i<assignRow.getTotal().size()-1;i++){
			
			BigDecimal value = totals.get(i).add(assignRow.getTotal().get(i), TkConstants.MATH_CONTEXT);
			totals.set(i, value.setScale(TkConstants.BIG_DECIMAL_SCALE, TkConstants.BIG_DECIMAL_SCALE_ROUNDING));
		}
		assignKeyToAssignmentRowMap.put(assignRow.getAssignmentKey(), assignRow);
		assignmentsRows.add(assignRow);
	}
	
	public void addWeeklyTotal(int index, int weekSize){
		
		BigDecimal weeklyTotal = BigDecimal.ZERO;
		for(int i = index; i >= (index-weekSize) && i >=0;i--){
			weeklyTotal = weeklyTotal.add(getTotals().get(i), TkConstants.MATH_CONTEXT);
		}
		getTotals().set(index,weeklyTotal);
		BigDecimal periodTotal = getTotals().get(getTotals().size()-1);
		periodTotal = periodTotal.add(weeklyTotal, TkConstants.MATH_CONTEXT);
		getTotals().set(getTotals().size()-1,periodTotal);
		
		for(AssignmentRow ar : assignmentsRows){
			ar.addWeeklyTotal(index, weekSize);
		}
	}
	
	public List<AssignmentRow> getAssignmentsRows() {
		return assignmentsRows;
	}
	public void setAssignmentsRows(List<AssignmentRow> assignmentsRows) {
		this.assignmentsRows = assignmentsRows;
	}
	public List<BigDecimal> getTotals() {
		return totals;
	}
	public void setTotals(List<BigDecimal> totals) {
		this.totals = totals;
	}
	public Map<String, AssignmentRow> getAssignKeyToAssignmentRowMap() {
		return assignKeyToAssignmentRowMap;
	}
	public void setAssignKeyToAssignmentRowMap(
			Map<String, AssignmentRow> assignKeyToAssignmentRowMap) {
		this.assignKeyToAssignmentRowMap = assignKeyToAssignmentRowMap;
	}
	
	public void addToTotal(int index, BigDecimal hrs){
		BigDecimal total = getTotals().get(index);
		total = total.add(hrs, TkConstants.MATH_CONTEXT);
		getTotals().set(index, total);
	}
	
	public void addToAmount(int index, BigDecimal amount){
		BigDecimal amtTotal = getTotals().get(index);
		amtTotal = amtTotal.add(amount, TkConstants.MATH_CONTEXT);
		getTotals().set(index, amtTotal);
	}
	public EarnGroupSection getEarnGroupSection() {
		return earnGroupSection;
	}
	public void setEarnGroupSection(EarnGroupSection earnGroupSection) {
		this.earnGroupSection = earnGroupSection;
	}
	public Boolean getIsAmountEarnCode() {
		return isAmountEarnCode;
	}
	public void setIsAmountEarnCode(Boolean isAmountEarnCode) {
		this.isAmountEarnCode = isAmountEarnCode;
	}
	
	
}
