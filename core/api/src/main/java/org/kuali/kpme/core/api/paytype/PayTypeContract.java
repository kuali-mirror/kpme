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

import java.util.Set;

import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.api.mo.EffectiveKeyContract;
import org.kuali.kpme.core.api.mo.KpmeEffectiveDataTransferObject;
import org.kuali.kpme.core.api.mo.KpmeEffectiveKeyedDataTransferObject;
import org.kuali.kpme.core.api.mo.KpmeEffectiveKeyedSetDataTransferObject;
import org.kuali.kpme.core.api.util.HrApiConstants;

/**
 * <p>PayTypeContract interface.</p>
 *
 */
public interface PayTypeContract extends KpmeEffectiveKeyedSetDataTransferObject {
	
	public static final String CACHE_NAME = HrApiConstants.CacheNamespace.NAMESPACE_PREFIX + "PayType";
	
	
	/* 
	 * The set of EffectiveKeyContract objects that hold the aggregate "white-list" of groupkeys 
	 * for this PayTypeContract object. 
	 * 
	 */
	@Override
	public Set<? extends EffectiveKeyContract> getEffectiveKeySet();
	
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

}
