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
package org.kuali.kpme.core.api.bo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.mo.KpmeEffectiveDataTransferObject;
import org.kuali.rice.core.api.mo.common.active.Inactivatable;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
/**
 * <p>HrBusinessObjectContract interface.</p>
 *
 */
public interface HrBusinessObjectContract extends PersistableBusinessObject, KpmeEffectiveDataTransferObject {
	
	/**
	 * The unique id defined by the object, could be a combination
	 * of multiple fields
	 * 
	 * <p>
	 * id of HrBusinessObject
	 * <p>
	 * 
	 * @return id of HrBusinessObject
	 */
	public abstract String getId();
	
	/**
	 * The effective date of the HrBusinessObject
	 * 
	 * <p>
	 * effectiveDate of HrBusinessObject
	 * <p>
	 * 
	 * @return effectiveDate of HrBusinessObject
	 */
	public Date getEffectiveDate();
	
	/**
	 * The localDate format of the effective date of the HrBusinessObject
	 * 
	 * <p>
	 * effectiveLocalDate of HrBusinessObject
	 * <p>
	 * 
	 * @return effectiveLocalDate of HrBusinessObject
	 */
	public LocalDate getEffectiveLocalDate();
	
	/**
	 * Relative effective date of HrBusinessObject. Returns effectiveDate if not null, current date otherwise.
	 * 
	 * <p>
	 * effectiveDate of HrBusinessObject, or current date if null
	 * </p>
	 * 
	 * @return effectiveDate of HrBusinessObject, or current date if null
	 */
	public Date getRelativeEffectiveDate();
	
	/**
	 * The timestamp of when this HrBusinessObject was last created/updated
	 * 
	 * <p>
	 * timestamp of HrBusinessObject
	 * <p>
	 * 
	 * @return timestamp of HrBusinessObject
	 */
	public Timestamp getTimestamp();
	
	
	
	/**
	 * The map of key-vaue pairs that together form the business key criteria for this instance.
	 * 
	 * @return map of key-value pairs that comprise the business key
	 */
	public Map<String, Object> getBusinessKeyValuesMap();
	
	
	
	/**
	 * Checks if all the business keys have values currently
	 * @return
	 */
	public boolean areAllBusinessKeyValuesAvailable(); 
	

    /**
     * The userPrincipalId of when this HrBusinessObject was last created/updated
     *
     * <p>
     * userPrincipalId of HrBusinessObject
     * <p>
     *
     * @return userPrincipalId of HrBusinessObject
     */
    public String getUserPrincipalId();
}
