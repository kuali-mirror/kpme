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
