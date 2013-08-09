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
package org.kuali.kpme.core.api.block;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * <p>CalendarBlockContract interface</p>
 *
 */
public interface CalendarBlockContract {

	public String getHrCalendarBlockId();
	
	public void setHrCalendarBlockId(String hrCalendarBlockId);
	
	public Long getWorkArea();
	
	public void setWorkArea(Long workArea);
	
	public Long getJobNumber();
	
	public void setJobNumber(Long jobNumber);
	
	public Long getTask();
	
	public String getEarnCode();
	
	public void setEarnCode(String earnCode);
	
	public String getConcreteBlockType();
	
	public void setConcreteBlockType(String concreteBlockType);
	
	public String getConcreteBlockId();
	
	public void setConcreteBlockId(String concreteBlockId);
	
	public void setTask(Long task);
	
	public String getDocumentId();

	public void setDocumentId(String documentId);

	public String getPrincipalId();

	public void setPrincipalId(String principalId);

	public Timestamp getTimestamp();

	public void setTimestamp(Timestamp timestamp);

	public Date getBeginTimestamp();

	public void setBeginTimestamp(Date beginTimestamp);

	public Date getEndTimestamp();

	public void setEndTimestamp(Date endTimestamp);
	
	public BigDecimal getAmount();

	public void setAmount(BigDecimal amount);
	
	public BigDecimal getHours();
	
	public void setHours(BigDecimal hours);
	
	public String getOvertimePref();
	
	public void setOvertimePref(String overtimePref);

	public boolean getLunchDeleted();
	
	public void setLunchDeleted(boolean lunchDeleted);

}