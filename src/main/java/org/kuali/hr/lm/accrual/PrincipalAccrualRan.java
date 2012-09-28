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
package org.kuali.hr.lm.accrual;

import java.sql.Timestamp;


public class PrincipalAccrualRan extends Object {

	private static final long serialVersionUID = 1L;
	private String principalId;
	private Timestamp lastRanTs;

	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	public Timestamp getLastRanTs() {
		return lastRanTs;
	}

	public void setLastRanTs(Timestamp lastRanTs) {
		this.lastRanTs = lastRanTs;
	}


}
