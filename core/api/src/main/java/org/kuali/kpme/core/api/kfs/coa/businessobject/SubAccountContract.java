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
package org.kuali.kpme.core.api.kfs.coa.businessobject;

import org.kuali.rice.core.api.mo.common.active.Inactivatable;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

/**
 * <p>SubAccountContract interface.</p>
 *
 */
public interface SubAccountContract extends PersistableBusinessObject, Inactivatable {
	
	 /**
     * Numeric field used to identify the SubAccount
     * 
     * <p>
     * accountNumber of SubAccount
     * <p>
     * 
     * @return accountNumber for SubAccount
     */
    public String getAccountNumber();

    /**
   	 * Code of the Chart object associated with the SubAccount
   	 * 
   	 * <p>
   	 * chartOfAccountsCode of SubAccount
   	 * </p>
   	 * 
   	 * @return chartOfAccountsCode for SubAccount
   	 */
    public String getChartOfAccountsCode();
 
    /**
   	 * SubAccount name
   	 * 
   	 * <p>
   	 * subAccountName of SubAccount
   	 * </p>
   	 * 
   	 * @return subAccountName for SubAccount
   	 */
    public String getSubAccountName();
    
    /**
   	 * Account object that the SubAccount is defined under
   	 * 
   	 * <p>
   	 * account of SubAccount
   	 * </p>
   	 * 
   	 * @return account for SubAccount
   	 */
    public AccountContract getAccount();

    /**
     * Numeric field used to identify the SubAccount
     * 
     * <p>
     * accountNumber of SubAccount
     * <p>
     * 
     * @return accountNumber for SubAccount
     */
    public String getSubAccountNumber();
    
    /**
   	 * Chart object that the SubAccount is associated with
   	 * 
   	 * <p>
   	 * chart of SubAccount
   	 * </p>
   	 * 
   	 * @return chart for SubAccount
   	 */
    public ChartContract getChart();

    /**
   	 * Organization object that the SubAccount is associated with
   	 * 
   	 * <p>
   	 * org of SubAccount
   	 * </p>
   	 * 
   	 * @return org for SubAccount
   	 */
    public OrganizationContract getOrg();
    
}
