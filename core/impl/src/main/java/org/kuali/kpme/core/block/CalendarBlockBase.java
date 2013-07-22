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
package org.kuali.kpme.core.block;

import java.sql.Timestamp;
import java.util.Date;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public abstract class CalendarBlockBase extends PersistableBusinessObjectBase implements CalendarBlockContract {

	private static final long serialVersionUID = -4067369587522388260L;
	
	protected String hrCalendarBlockId;
	protected String principalId;
	protected String documentId;
	protected String principalIdModified;
	protected Date beginTimestamp;
	protected Date endTimestamp;
	protected Timestamp timestamp;
	protected boolean lunchDeleted;
	protected String earnCode;
	protected Long workArea;
	protected Long jobNumber;
	protected Long task;
	protected String concreteBlockType;
	protected String concreteBlockId;
	
	public CalendarBlockBase() {
		this.concreteBlockType = this.getClass().getName();
	}
	
	public abstract String getConcreteBlockType();
	
	
	public abstract void setConcreteBlockType(String concreteBlockType);

	
	public abstract String getConcreteBlockId();

	
	public abstract void setConcreteBlockId(String concreteBlockId);
	
	public String getHrCalendarBlockId() {
		return hrCalendarBlockId;
	}
	
	public void setHrCalendarBlockId(String hrCalendarBlockId) {
		this.hrCalendarBlockId = hrCalendarBlockId;
	}
	
	/* (non-Javadoc)
	 * @see org.kuali.kpme.tklm.leave.block.CalendarBlockContract#getPrincipalIdModified()
	 */
	public String getPrincipalIdModified() {
		return principalIdModified;
	}

}