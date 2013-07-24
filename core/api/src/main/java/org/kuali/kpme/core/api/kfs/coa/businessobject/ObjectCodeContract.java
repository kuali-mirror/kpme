package org.kuali.kpme.core.api.kfs.coa.businessobject;

import org.kuali.rice.core.api.mo.common.active.Inactivatable;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

/**
 * <p>ObjectCodeContract interface.</p>
 *
 */
public interface ObjectCodeContract extends PersistableBusinessObject, Inactivatable {
	
	/**
	 * Numeric field used to identify the ObjectCode
	 * 
	 * <p>
	 * financialObjectCode of ObjectCode
	 * </p>
	 * 
	 * @return financialObjectCode for ObjectCode
	 */
    public String getFinancialObjectCode();
    
    /**
	 * Text to describe the ObjectCode 
	 * 
	 * <p>
	 * financialObjectCodeName of ObjectCode
	 * </p>
	 * 
	 * @return financialObjectCodeName for ObjectCode
	 */
    public String getFinancialObjectCodeName();

    /**
	 * Abbreviated text to describe the ObjectCode
	 * 
	 * <p>
	 * financialObjectCodeShortName of ObjectCode
	 * </p>
	 * 
	 * @return financialObjectCodeShortName for ObjectCode
	 */
    public String getFinancialObjectCodeShortName();

    /**
	 * Active flag of the ObjectCode
	 * 
	 * <p>
	 * financialObjectActiveCode of ObjectCode
	 * </p>
	 * 
	 * @return financialObjectActiveCode for ObjectCode
	 */
    public boolean isFinancialObjectActiveCode();
    
    /**
	 * The Chart object that the ObjectCode defined under
	 * 
	 * <p>
	 * chartOfAccounts of ObjectCode
	 * </p>
	 * 
	 * @return chartOfAccounts for ObjectCode
	 */
    public ChartContract getChartOfAccounts();
    
    /**
	 * The code of the Chart object that the ObjectCode defined under
	 * 
	 * <p>
	 * chartOfAccountsCode of ObjectCode
	 * </p>
	 * 
	 * @return chartOfAccountsCode for ObjectCode
	 */
    public String getChartOfAccountsCode();
    
    /**
	 * The fiscal year which the ObjectCode is valid for
	 * 
	 * <p>
	 * universityFiscalYear of ObjectCode
	 * </p>
	 * 
	 * @return universityFiscalYear for ObjectCode
	 */
    public Integer getUniversityFiscalYear();
    
    /**
	 * The level code of the ObjectCode
	 * 
	 * <p>
	 * financialObjectLevelCode of ObjectCode
	 * </p>
	 * 
	 * @return financialObjectLevelCode for ObjectCode
	 */
    public String getFinancialObjectLevelCode();
    
    /**
	 * The financialObjectCode of the ObjectCode
	 * 
	 * <p>
	 * code of ObjectCode
	 * </p>
	 * 
	 * @return code for ObjectCode
	 */
    public String getCode();
    
    /**
   	 * The financialObjectCodeName of the ObjectCode
   	 * 
   	 * <p>
   	 * name of ObjectCode
   	 * </p>
   	 * 
   	 * @return name for ObjectCode
   	 */
    public String getName();
}
