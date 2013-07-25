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
import org.kuali.rice.krad.bo.KualiCode;
/**
 * <p>ProjectCodeContract interface.</p>
 *
 */
public interface ProjectCodeContract extends KualiCode, Inactivatable {
	
	/**
	 * Text field used to identify the ProjectCode 
	 * 
	 * <p>
	 * projectDescription of ProjectCode
	 * </p>
	 * 
	 * @return projectDescription for ProjectCode
	 */
    public String getProjectDescription();
    
    /**
	 * The Chart object the ProjectCode is defined under
	 * 
	 * <p>
	 * chartOfAccounts of ProjectCode
	 * </p>
	 * 
	 * @return chartOfAccounts for ProjectCode
	 */
    public ChartContract getChartOfAccounts();

    /**
	 * The Organization object the ProjectCode is defined under
	 * 
	 * <p>
	 * organization of ProjectCode
	 * </p>
	 * 
	 * @return organization for ProjectCode
	 */
    public OrganizationContract getOrganization();

    /**
   	 * The code of the Chart object the ProjectCode is defined under
   	 * 
   	 * <p>
   	 * chartOfAccountsCode of ProjectCode
   	 * </p>
   	 * 
   	 * @return chartOfAccountsCode for ProjectCode
   	 */
    public String getChartOfAccountsCode();

    /**
   	 * The code of the Organization object the ProjectCode is defined under
   	 * 
   	 * <p>
   	 * organizationCode of ProjectCode
   	 * </p>
   	 * 
   	 * @return organizationCode for ProjectCode
   	 */
    public String getOrganizationCode();

}
