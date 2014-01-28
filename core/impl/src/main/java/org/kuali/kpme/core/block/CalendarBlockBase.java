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
package org.kuali.kpme.core.block;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Transient;

import org.kuali.kpme.core.api.block.CalendarBlockContract;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public abstract class CalendarBlockBase extends PersistableBusinessObjectBase implements CalendarBlockContract {

	private static final long serialVersionUID = -4067369587522388260L;
	
	@Transient
	protected String hrCalendarBlockId;
	protected String principalId;
	protected String userPrincipalId;
	protected String documentId;
	protected Timestamp beginTimestamp;
	protected Timestamp endTimestamp;
	protected Timestamp timestamp;
	protected boolean lunchDeleted;
	protected BigDecimal hours;
	protected BigDecimal amount;
	protected String overtimePref;
	protected String earnCode;
	protected Long workArea;
	protected Long jobNumber;
	protected Long task;
	@Transient
	protected String concreteBlockType;
	@Transient
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

}