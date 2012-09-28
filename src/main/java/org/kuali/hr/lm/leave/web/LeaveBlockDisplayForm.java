/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.lm.leave.web;

import java.util.List;

import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leaveblock.LeaveBlockHistory;
import org.kuali.hr.time.base.web.TkForm;

public class LeaveBlockDisplayForm extends TkForm {

	private int year;
	private String navString;
	private List<LeaveBlock> leaveEntries;
	private List<LeaveBlockHistory> correctedLeaveEntries;
	private List<LeaveBlockHistory> inActiveLeaveEntries;
	private List<String> accrualCategoires;
	private String targetName;
	
	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public List<LeaveBlockHistory> getInActiveLeaveEntries() {
		return inActiveLeaveEntries;
	}

	public void setInActiveLeaveEntries(List<LeaveBlockHistory> inActiveLeaveEntries) {
		this.inActiveLeaveEntries = inActiveLeaveEntries;
	}

	public List<LeaveBlockHistory> getCorrectedLeaveEntries() {
		return correctedLeaveEntries;
	}

	public void setCorrectedLeaveEntries(
			List<LeaveBlockHistory> correctedLeaveEntries) {
		this.correctedLeaveEntries = correctedLeaveEntries;
	}

	public List<LeaveBlock> getLeaveEntries() {
		return leaveEntries;
	}

	public void setLeaveEntries(List<LeaveBlock> leaveEntries) {
		this.leaveEntries = leaveEntries;
	}

	public String getNavString() {
		return navString;
	}

	public void setNavString(String navString) {
		this.navString = navString;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public List<String> getAccrualCategoires() {
		return accrualCategoires;
	}

	public void setAccrualCategoires(List<String> accrualCategoires) {
		this.accrualCategoires = accrualCategoires;
	}
	

}
