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
package org.kuali.kpme.tklm.leave.accrual;

import java.util.Date;

import org.joda.time.DateTime;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;


public class PrincipalAccrualRan extends PersistableBusinessObjectBase {

	private static final long serialVersionUID = -8102955197478338957L;
	
	private String principalId;
	private Date lastRanTs;

	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	public Date getLastRanTs() {
		return lastRanTs;
	}

	public void setLastRanTs(Date lastRanTs) {
		this.lastRanTs = lastRanTs;
	}

	public DateTime getLastRanDateTime() {
		return lastRanTs != null ? new DateTime(lastRanTs) : null;
	}
	
	public void setLastRanDateTime(DateTime lastRanDateTime) {
		lastRanTs = lastRanDateTime != null ? lastRanDateTime.toDate() : null;
	}
}