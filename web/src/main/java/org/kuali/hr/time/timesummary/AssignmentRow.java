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
import java.util.List;

import org.kuali.hr.time.util.TkConstants;

public class AssignmentRow implements Serializable {
	private String descr;
	private List<BigDecimal> total = new ArrayList<BigDecimal>();
	private String cssClass;
	private String assignmentKey;
	
	private List<BigDecimal> amount = new ArrayList<BigDecimal>();
	
	private BigDecimal periodTotal = BigDecimal.ZERO;
	
	private EarnCodeSection earnCodeSection;
	
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public List<BigDecimal> getTotal() {
		return total;
	}
	public void setTotal(List<BigDecimal> total) {
		this.total = total;
	}
	public String getCssClass() {
		return cssClass;
	}
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	public String getAssignmentKey() {
		return assignmentKey;
	}
	public void setAssignmentKey(String assignmentKey) {
		this.assignmentKey = assignmentKey;
	}
	
	public void addToTotal(int index, BigDecimal hrs){
		BigDecimal total = getTotal().get(index);
		total = total.add(hrs, TkConstants.MATH_CONTEXT);
		getTotal().set(index, total);
		getEarnCodeSection().addToTotal(index, hrs);
	}
	
	public void addWeeklyTotal(int index, int weekSize){
		BigDecimal weeklyTotal = BigDecimal.ZERO;
		BigDecimal weeklyAmt = BigDecimal.ZERO;
		for(int i = index; i >= (index-weekSize) && i >=0;i--){
			weeklyTotal = weeklyTotal.add(getTotal().get(i), TkConstants.MATH_CONTEXT);
			weeklyAmt = weeklyAmt.add(getAmount().get(i), TkConstants.MATH_CONTEXT);
		}
		getTotal().set(index,weeklyTotal);
		getAmount().set(index, weeklyAmt);
		
		BigDecimal periodTotal = getTotal().get(getTotal().size()-1);
		periodTotal = periodTotal.add(weeklyTotal, TkConstants.MATH_CONTEXT);
		getTotal().set(getTotal().size()-1,periodTotal);
		
		//accumulate amount
		BigDecimal amountTotal = getAmount().get(getTotal().size()-1);
		amountTotal = amountTotal.add(weeklyAmt, TkConstants.MATH_CONTEXT);
		getAmount().set(getAmount().size()-1, amountTotal);
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
	public List<BigDecimal> getAmount() {
		return amount;
	}
	public void setAmount(List<BigDecimal> amount) {
		this.amount = amount;
	}
	
	public void addToAmount(int index, BigDecimal amount){
		if(amount == null){
			return;
		}
		BigDecimal amtTotal = getAmount().get(index);
		amtTotal = amtTotal.add(amount, TkConstants.MATH_CONTEXT);
		getAmount().set(index, amtTotal);
	}
}
