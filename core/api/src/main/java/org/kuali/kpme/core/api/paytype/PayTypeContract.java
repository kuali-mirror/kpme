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
package org.kuali.kpme.core.api.paytype;

import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.api.mo.KpmeEffectiveDataTransferObject;
import org.kuali.kpme.core.api.util.HrApiConstants;

/**
 * <p>PayTypeContract interface.</p>
 *
 */
public interface PayTypeContract extends KpmeEffectiveDataTransferObject {
	
	public static final String CACHE_NAME = HrApiConstants.CacheNamespace.NAMESPACE_PREFIX + "PayType";
	
	/**
	 * The EarnCode object which is used to record regular time by this PayType
	 * 
	 * <p>
	 * regEarnCodeObj of PayType
	 * </p>
	 * 
	 * @return regEarnCodeObj of PayType
	 */
	public EarnCodeContract getRegEarnCodeObj();

	/**
	 * Text field used to identify the PayType
	 * 
	 * <p>
	 * payType of PayType
	 * </p>
	 * 
	 * @return payType of PayType
	 */
	public String getPayType();
	
	/**
	 * Text which describes the PayType
	 * 
	 * <p>
	 * descr of PayType
	 * </p>
	 * 
	 * @return descr of PayType
	 */
	public String getDescr();
	
	/**
	 * EarnCode of the EarnCode object which is used to record regular time by this PayType
	 * 
	 * <p>
	 * regEarnCode of PayType
	 * </p>
	 * 
	 * @return regEarnCode of PayType
	 */
	public String getRegEarnCode();
	
	/**
	 * The Primary Key of a PayType entry saved in a database
	 * 
	 * <p>
	 * hrPayTypeId of a PayType
	 * <p>
	 * 
	 * @return hrPayTypeId for a PayType
	 */
	public String getHrPayTypeId();

	/**
	 * The Primary Key of the EarnCode associated with this PayType
	 * 
	 * <p>
	 * hrEarnCodeId of a PayType
	 * <p>
	 * 
	 * @return hrEarnCodeId for a PayType
	 */
	public String getHrEarnCodeId();
	
	/**
	 * Indicates if the EarnCode associated with the PayType is an Overtime earn code
	 * 
	 * <p>
	 * ovtEarnCode of PayType
	 * </p>
	 * 
	 * @return true if is overtime, false if not
	 */
	public Boolean isOvtEarnCode();

	/**
	 * Name of the Institution object the PayType is associated with
	 * 
	 * <p>
	 * institution of PayType
	 * </p>
	 * 
	 * @return institution for PayType
	 */
	public String getInstitution();
	
	/**
	 * The Institution object the PayType is associated with
	 * 
	 * <p>
	 * institutionObj of PayType
	 * </p>
	 * 
	 * @return institutionObj for PayType
	 */
	//public InstitutionContract getInstitutionObj();
	
	/**
	 * Indicates if the PayType's Position is FLSA exempt or non-exempt 
	 * 
	 * <p>
	 * flsaStatus of PayType
	 * </p>
	 * 
	 * @return NE if non-exempt, E if exempt
	 */
	public String getFlsaStatus() ;

	/**
	 * Frequency of payroll
	 * 
	 * <p>
	 * payFrequency of PayType
	 * </p>
	 * 
	 * @return payFrequency for PayType
	 */
	public String getPayFrequency();

	/**
	 * Name of the Location object the PayType is associated with
	 * 
	 * <p>
	 * location of PayType
	 * </p>
	 * 
	 * @return location for PayType
	 */
	public String getLocation();

	/**
	 * The Location object the PayType is associated with
	 * 
	 * <p>
	 * locationObj of PayType
	 * </p>
	 * 
	 * @return locationObj for PayType
	 */
	//public LocationContract getLocationObj();
}
