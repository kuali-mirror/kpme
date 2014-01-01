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
package org.kuali.kpme.core.api.paygrade;

import java.math.BigDecimal;

import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.util.HrApiConstants;

/**
 * <p>PayGradeContract interface.</p>
 *
 */
public interface PayGradeContract extends HrBusinessObjectContract {
	
	public static final String CACHE_NAME = HrApiConstants.CacheNamespace.NAMESPACE_PREFIX + "PayGrade";
	
	/**
	 * The Primary Key of a PayGrade entry saved in a database
	 * 
	 * <p>
	 * hrPayGradeId of a PayGrade
	 * <p>
	 * 
	 * @return hrPayGradeId for PayGrade
	 */
	public String getHrPayGradeId();

	/**
	 * Text field used to identify the PayGrade
	 * 
	 * <p>
	 * payGrade of a PayGrade
	 * <p>
	 * 
	 * @return payGrade for PayGrade
	 */
	public String getPayGrade();
	
	/**
	 * Description of the PayGrade
	 * 
	 * <p>
	 * description of a PayGrade
	 * <p>
	 * 
	 * @return description for PayGrade
	 */
	public String getDescription();

	/**
	 *TODO: is this field needed?
	 */
	public String getUserPrincipalId() ;
	
	/**
	 * The name of the SalaryGroup the PayGrade is associated with.
	 * 
	 * <p>
	 * salGroup of a PayGrade
	 * <p>
	 * 
	 * @return salGroup for PayGrade
	 */
	public String getSalGroup();

	/**
	 * The name of the Institution the PayGrade is associated with.
	 * 
	 * <p>
	 * institution of a PayGrade
	 * <p>
	 * 
	 * @return institution for PayGrade
	 */
    public String getInstitution();
    /**
	 * The measurement of the pay rate ranges being defined for this PayGrade
	 * 
	 * <p>
	 * rateType of a PayGrade
	 * <p>
	 * 
	 * @return H forHour, W for Week, S for Semi-Month, M for Month, Y for Year
	 */
    public String getRateType();
    
    /**
	 * Minimum pay rate for this PayGrade
	 * 
	 * <p>
	 * minRate of a PayGrade
	 * <p>
	 * 
	 * @return minRate for PayGrade
	 */
    public BigDecimal getMinRate();

    /**
	 * Maximum pay rate for this PayGrade
	 * 
	 * <p>
	 * maxRate of a PayGrade
	 * <p>
	 * 
	 * @return maxRate for PayGrade
	 */
    public BigDecimal getMaxRate();
    
    /**
	 * Mid Point pay rate for this PayGrade
	 * 
	 * <p>
	 * midPointRate of a PayGrade
	 * <p>
	 * 
	 * @return midPointRate for PayGrade
	 */
    public BigDecimal getMidPointRate();

    /**
	 * Maximum Hiring pay rate for this PayGrade
	 * 
	 * <p>
	 * maxHiringRate of a PayGrade
	 * <p>
	 * 
	 * @return maxHiringRate for PayGrade
	 */
    public BigDecimal getMaxHiringRate();

    /**
	 * History flag for PayGrade lookups 
	 * 
	 * <p>
	 * history of PayGrade
	 * </p>
	 * 
	 * @return true if want to show history, false if not
	 */
    public String getHistory();
    
    /**
	 * The name of the Location the PayGrade is associated with.
	 * 
	 * <p>
	 * location of a PayGrade
	 * <p>
	 * 
	 * @return location for PayGrade
	 */
	public String getLocation();
}
