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
package org.kuali.kpme.core.api.block;

import org.joda.time.DateTime;
import org.kuali.kpme.core.api.mo.KeyedData;
import org.kuali.rice.core.api.mo.common.GloballyUnique;
import org.kuali.rice.core.api.mo.common.Versioned;

import java.math.BigDecimal;

/**
 * <p>CalendarBlockContract interface</p>
 *
 */
public interface CalendarBlockContract extends GloballyUnique, Versioned, KeyedData {

	public String getHrCalendarBlockId();
	
	public Long getWorkArea();
	
	public Long getJobNumber();
	
	public Long getTask();
	
	public String getEarnCode();
	
	public String getConcreteBlockType();
	
	public String getConcreteBlockId();
	
	public String getDocumentId();

	public String getPrincipalId();

    public DateTime getBeginDateTime();

    public DateTime getEndDateTime();

	public BigDecimal getAmount();

	public BigDecimal getHours();
	
	public String getOvertimePref();
	
	public boolean isLunchDeleted();
	
}