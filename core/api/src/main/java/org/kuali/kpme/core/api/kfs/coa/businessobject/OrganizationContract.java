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
package org.kuali.kpme.core.api.kfs.coa.businessobject;

import org.kuali.rice.core.api.mo.common.active.Inactivatable;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

/**
 * <p>OrganizationContract interface.</p>
 *
 */
public interface OrganizationContract extends PersistableBusinessObject, Inactivatable {
	
	/**
   	 * Text field used to identify the Organization 
   	 * 
   	 * <p>
   	 * organizationCode of Organization
   	 * </p>
   	 * 
   	 * @return organizationCode for Organization
   	 */
    public String getOrganizationCode();
    
    /**
   	 * Text to describe the organization grouping
   	 * 
   	 * <p>
   	 * organizationName of Organization
   	 * </p>
   	 * 
   	 * @return organizationName for Organization
   	 */
    public String getOrganizationName();

    /**
   	 * The Chart Object that the Organization is defined under
   	 * 
   	 * <p>
   	 * chartOfAccounts of Organization
   	 * </p>
   	 * 
   	 * @return chartOfAccounts for Organization
   	 */
    public ChartContract getChartOfAccounts();

    /**
   	 * The code of the Chart Object that the Organization is defined under
   	 * 
   	 * <p>
   	 * chartOfAccountsCode of Organization
   	 * </p>
   	 * 
   	 * @return chartOfAccountsCode for Organization
   	 */
    public String getChartOfAccountsCode();
    
    /**
   	 * The string combination of code and description of the Organization
   	 * 
   	 * <p>
   	 * CodeAndDescription of Organization
   	 * </p>
   	 * 
     * @return the code and description in format: xx - xxxxxxxxxxxxxxxx
     */
    public String getCodeAndDescription();
}
