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
package org.kuali.hr.tklm.leave.web;

import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.core.HrForm;
import org.kuali.hr.core.accrualcategory.AccrualCategory;
import org.kuali.hr.tklm.leave.block.LeaveBlockHistory;

public class LeaveBlockDisplayForm extends HrForm {

	private static final long serialVersionUID = 1438955879572170167L;
	
	private String targetName;
	private int year;
	private String navString;
	private List<AccrualCategory> accrualCategories = new ArrayList<AccrualCategory>();
	private List<LeaveBlockDisplay> leaveEntries;
	private List<LeaveBlockHistory> correctedLeaveEntries;
	private List<LeaveBlockHistory> inActiveLeaveEntries;
	
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getNavString() {
		return navString;
	}

	public void setNavString(String navString) {
		this.navString = navString;
	}
	
	public List<AccrualCategory> getAccrualCategories() {
		return accrualCategories;
	}

	public void setAccrualCategories(List<AccrualCategory> accrualCategories) {
		this.accrualCategories = accrualCategories;
	}
	
	public List<LeaveBlockDisplay> getLeaveEntries() {
		return leaveEntries;
	}

	public void setLeaveEntries(List<LeaveBlockDisplay> leaveEntries) {
		this.leaveEntries = leaveEntries;
	}
	
	public List<LeaveBlockHistory> getCorrectedLeaveEntries() {
		return correctedLeaveEntries;
	}

	public void setCorrectedLeaveEntries(List<LeaveBlockHistory> correctedLeaveEntries) {
		this.correctedLeaveEntries = correctedLeaveEntries;
	}

	public List<LeaveBlockHistory> getInActiveLeaveEntries() {
		return inActiveLeaveEntries;
	}

	public void setInActiveLeaveEntries(List<LeaveBlockHistory> inActiveLeaveEntries) {
		this.inActiveLeaveEntries = inActiveLeaveEntries;
	}
	
}