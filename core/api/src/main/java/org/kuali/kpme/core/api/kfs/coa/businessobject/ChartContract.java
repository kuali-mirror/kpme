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
