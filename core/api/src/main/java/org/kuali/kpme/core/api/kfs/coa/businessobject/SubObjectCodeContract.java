package org.kuali.kpme.core.api.kfs.coa.businessobject;

import org.kuali.rice.core.api.mo.common.active.Inactivatable;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

/**
 * <p>SubObjectCodeContract interface.</p>
 *
 */
public interface SubObjectCodeContract extends PersistableBusinessObject, Inactivatable {
	
	/**
     * Numeric field used to identify the SubObjectCode
     * 
     * <p>
     * financialSubObjectCode of SubObjectCode
     * <p>
     * 
     * @return financialSubObjectCode for SubObjectCode
     */
	public String getFinancialSubObjectCode();

	/**
     * The code of the ObjectCode object that the SubObjectCode is defined under
     * 
     * <p>
     * financialObjectCode of SubObjectCode
     * <p>
     * 
     * @return financialObjectCode for SubObjectCode
     */
    public String getFinancialObjectCode();
    
    /**
     * The ObjectCode object that the SubObjectCode is defined under
     * 
     * <p>
     * financialObject of SubObjectCode
     * <p>
     * 
     * @return financialObject for SubObjectCode
     */
    public ObjectCodeContract getFinancialObject();

    /**
     * Text to describe the SubObjectCode
     * 
     * <p>
     * financialSubObjectCodeName of SubObjectCode
     * <p>
     * 
     * @return financialSubObjectCodeName for SubObjectCode
     */
    public String getFinancialSubObjectCodeName();

    /**
     * Abbreviated text to describe the sub-object code.
     * 
     * <p>
     * financialSubObjectCdshortNm of SubObjectCode
     * <p>
     * 
     * @return financialSubObjectCdshortNm for SubObjectCode
     */
    public String getFinancialSubObjectCdshortNm();
    
    /**
     * The Chart object that the SubObjectCode is defined under
     * 
     * <p>
     * chartOfAccounts of SubObjectCode
     * <p>
     * 
     * @return chartOfAccounts for SubObjectCode
     */
    public ChartContract getChartOfAccounts();

    /**
     * The Account object that the SubObjectCode is defined under
     * 
     * <p>
     * account of SubObjectCode
     * <p>
     * 
     * @return account for SubObjectCode
     */
    public AccountContract getAccount() ;
    
    /**
     * The number of the Account object that the SubObjectCode is defined under
     * 
     * <p>
     * accountNumber of SubObjectCode
     * <p>
     * 
     * @return accountNumber for SubObjectCode
     */
    public String getAccountNumber();
    
    /**
     * The code of the Chart object that the SubObjectCode is defined under
     * 
     * <p>
     * chartOfAccountsCode of SubObjectCode
     * <p>
     * 
     * @return chartOfAccountsCode for SubObjectCode
     */
    public String getChartOfAccountsCode();
    
    /**
     * The fiscal year which the SubObjectCode is valid for
     * 
     * <p>
     * universityFiscalYear of SubObjectCode
     * <p>
     * 
     * @return universityFiscalYear for SubObjectCode
     */
    public Integer getUniversityFiscalYear();

}
