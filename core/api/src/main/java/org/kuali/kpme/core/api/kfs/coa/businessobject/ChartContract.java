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
 * <p>ChartContract interface.</p>
 *
 */
public interface ChartContract extends PersistableBusinessObject, Inactivatable{
	
	/**
	 * Text to describe the Chart grouping 
	 * 
	 * <p>
	 * finChartOfAccountDescription of Chart
	 * </p>
	 * 
	 * @return finChartOfAccountDescription for Chart
	 */
    public String getFinChartOfAccountDescription();

    /**
   	 * Text field used to identify the Chart
   	 * 
   	 * <p>
   	 * chartOfAccountsCode of Chart
   	 * </p>
   	 * 
   	 * @return chartOfAccountsCode for Chart
   	 */
    public String getChartOfAccountsCode();

    /**
     * String combination of the code and description of the Chart
     *  
     * @return Returns the code and description in format: xx - xxxxxxxxxxxxxxxx
     */
    public String getCodeAndDescription();
    
    /**
   	 * chartOfAccountsCode of Chart
   	 * 
   	 * <p>
   	 * code of Chart
   	 * </p>
   	 * 
   	 * @return code for Chart
   	 */
    public String getCode();

    /**
   	 * finChartOfAccountDescription of Chart
   	 * 
   	 * <p>
   	 * name of Chart
   	 * </p>
   	 * 
   	 * @return name for Chart
   	 */
    public String getName();

}
