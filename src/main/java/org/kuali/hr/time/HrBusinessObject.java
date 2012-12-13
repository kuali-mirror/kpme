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
package org.kuali.hr.time;

import java.sql.Date;
import java.sql.Timestamp;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public abstract class HrBusinessObject extends PersistableBusinessObjectBase{

	private static final long serialVersionUID = -5743717258128864335L;
	
	private Date effectiveDate;
	private boolean active;
	private Timestamp timestamp;
	
	public abstract String getId();
	
	public abstract void setId(String id);
	
	protected abstract String getUniqueKey();
	
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

}
