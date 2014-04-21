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
package org.kuali.kpme.tklm.leave.block.web;

import java.util.Date;

import org.kuali.kpme.tklm.leave.block.LeaveBlockBo;
import org.kuali.rice.krad.web.form.UifFormBase;

public class LeaveBlockForm extends UifFormBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4877920434755295616L;
	
	private Date toDate;
	private Date fromDate;
	private LeaveBlockBo leaveBlock;
	private String calendarCount;

	public LeaveBlockBo getLeaveBlock() {
		return leaveBlock;
	}

	public void setLeaveBlock(LeaveBlockBo leaveBlock) {
		this.leaveBlock = leaveBlock;
	}

	public String getCalendarCount() {
		return calendarCount;
	}

	public void setCalendarCount(String calendarCount) {
		this.calendarCount = calendarCount;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	
	
}
