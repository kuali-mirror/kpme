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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.tklm.api.time.timesummary.EarnGroupSectionContract;

public class EarnGroupSection implements Serializable, EarnGroupSectionContract {

	private static final long serialVersionUID = 2655916770367036144L;
	private String earnGroup;
	private Map<String, EarnCodeSection> earnCodeToEarnCodeSectionMap = new HashMap<String, EarnCodeSection>();
	private List<EarnCodeSection> earnCodeSections = new ArrayList<EarnCodeSection>();
	private Map<Integer, BigDecimal> totals = new LinkedHashMap<Integer, BigDecimal>();
	private BigDecimal earnGroupTotal = BigDecimal.ZERO;
	
	public String getEarnGroup() {
		return earnGroup;
	}
	public void setEarnGroup(String earnGroup) {
		this.earnGroup = earnGroup;
	}

	public Map<Integer, BigDecimal> getTotals() {
		return totals;
	}
	
	public void addEarnCodeSection(EarnCodeSection earnCodeSection, List<Boolean> dayArrangements){
		for(AssignmentRow assignRow : earnCodeSection.getAssignmentsRows()) {
			for(Integer i : assignRow.getAssignmentColumns().keySet()) {
				AssignmentColumn assignmentColumn = assignRow.getAssignmentColumns().get(i);
				BigDecimal value = totals.get(i).add(assignmentColumn.getTotal(), HrConstants.MATH_CONTEXT);
				totals.put(i, value.setScale(HrConstants.BIG_DECIMAL_SCALE, HrConstants.BIG_DECIMAL_SCALE_ROUNDING));
				earnGroupTotal = earnGroupTotal.add(assignmentColumn.getTotal());
				earnGroupTotal.setScale(HrConstants.BIG_DECIMAL_SCALE, HrConstants.BIG_DECIMAL_SCALE_ROUNDING);
			}
			/**
			for (AssignmentColumn assignmentColumn : assignRow.getAssignmentColumns().values()) {
				BigDecimal value = totals.get(i).add(assignmentColumn.getTotal(), HrConstants.MATH_CONTEXT);
				totals.set(i, value.setScale(HrConstants.BIG_DECIMAL_SCALE, HrConstants.BIG_DECIMAL_SCALE_ROUNDING));
				System.out.println("Column total is "+assignmentColumn.getTotal());
				earnGroupTotal = earnGroupTotal.add(assignmentColumn.getTotal());
				earnGroupTotal.setScale(HrConstants.BIG_DECIMAL_SCALE, HrConstants.BIG_DECIMAL_SCALE_ROUNDING);
				i++;
			}**/
		}
		earnCodeToEarnCodeSectionMap.put(earnCodeSection.getEarnCode(), earnCodeSection);
		earnCodeSections.add(earnCodeSection);
		
		
	}
	public Map<String, EarnCodeSection> getEarnCodeToEarnCodeSectionMap() {
		return earnCodeToEarnCodeSectionMap;
	}
	public void setEarnCodeToEarnCodeSectionMap(
			Map<String, EarnCodeSection> earnCodeToEarnCodeSectionMap) {
		this.earnCodeToEarnCodeSectionMap = earnCodeToEarnCodeSectionMap;
	}
	
	public void addToTotal(int index, BigDecimal hrs){
		BigDecimal total = getTotals().get(index);
		total = total.add(hrs, HrConstants.MATH_CONTEXT);
		getTotals().put(index, total);
	}
	
	public List<EarnCodeSection> getEarnCodeSections() {
		return earnCodeSections;
	}
	
	public void setEarnCodeSections(List<EarnCodeSection> earnCodeSections) {
		this.earnCodeSections = earnCodeSections;
	}
	
	public BigDecimal getEarnGroupTotal() {
		return earnGroupTotal;
	}
	
	public void setEarnGroupTotal(BigDecimal earnGroupTotal) {
		this.earnGroupTotal = earnGroupTotal;
	}
	
	
}
