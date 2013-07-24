package org.kuali.kpme.core.api.kfs.coa.businessobject;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.kuali.rice.core.api.mo.common.active.Inactivatable;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

/**
 * <p>AccountContract interface.</p>
 *
 */
public interface AccountContract extends PersistableBusinessObject, Inactivatable {

	/**
	 * The organization value under which the new Account will be defined
	 * 
	 * <p>
	 * organizationCode of Account
	 * </p>
	 * 
	 * @return organizationCode for Account
	 */
	public String getOrganizationCode();
	
    /**
     * Numeric field used to identify the Account
     * 
     * <p>
     * accountNumber of Account
     * <p>
     * 
     * @return accountNumber for Account
     */
    public String getAccountNumber();
    
    /**
     * Account name
     * 
     * <p>
     * name of Account
     * <p>
     * 
     * @return name for Account
     */
    public String getAccountName();

    /**
     * Date the Account is created on
     * 
     * <p>
     * accountCreateDate of Account
     * <p>
     * 
     * @return accountCreateDate for Account
     */
    public Date getAccountCreateDate();

    /**
     * Date the Account becomes valid
     * 
     * <p>
     * accountEffectiveDate of Account
     * <p>
     * 
     * @return accountEffectiveDate for Account
     */
    public Date getAccountEffectiveDate();

    /**
     * Date the Account becomes invalid
     * 
     * <p>
     * accountExpirationDate of Account
     * <p>
     * 
     * @return accountExpirationDate for Account
     */
    public Date getAccountExpirationDate();

    /**
     * This method determines whether the account is expired or not. Note that if Expiration Date is the same date as testDate, then
     * this will return false. It will only return true if the account expiration date is one day earlier than testDate or earlier.
     * Note that this logic ignores all time components when doing the comparison. It only does the before/after comparison based on
     * date values, not time-values.
     *  
     * @param testDate - Calendar instance with the date to test the Account's Expiration Date against. This is most commonly set to
     *        today's date.
     *        
     * @return true or false based on the logic outlined above
     */
    public boolean isExpired(Calendar testDate);
    
    /**
     * This method determines whether the account is expired or not. Note that if Expiration Date is the same date as testDate, then
     * this will return false. It will only return true if the account expiration date is one day earlier than testDate or earlier.
     * Note that this logic ignores all time components when doing the comparison. It only does the before/after comparison based on
     * date values, not time-values.
     * 
     * @param testDate - Date instance with the date to test the Account's Expiration Date against. This is most commonly
     *        set to today's date.
     *        
     * @return true or false based on the logic outlined above
     */
    public boolean isExpired(Date testDate);
  
    /**
   	 * Flag indicates if the Account is closed
   	 * 
   	 * <p>
   	 * closed flag of Account
   	 * </p>
   	 * 
   	 * @return true if closed, false if not
   	 */
    public boolean isClosed();

    /**
   	 * Chart object that the Account is defined under
   	 * 
   	 * <p>
   	 * chartOfAccounts of Account
   	 * </p>
   	 * 
   	 * @return chartOfAccounts for Account
   	 */
    public ChartContract getChartOfAccounts();

    /**
   	 * Organization object that the Account is defined under
   	 * 
   	 * <p>
   	 * organization of Account
   	 * </p>
   	 * 
   	 * @return organization for Account
   	 */
    public OrganizationContract getOrganization();

    /**
   	 * List of subAccounts defined under the Account
   	 * 
   	 * <p>
   	 * subAccounts of Account
   	 * </p>
   	 * 
   	 * @return subAccounts for Account
   	 */
    public List getSubAccounts();

    /**
   	 * Code of the Chart object associated with the Account
   	 * 
   	 * <p>
   	 * chartOfAccountsCode of Account
   	 * </p>
   	 * 
   	 * @return chartOfAccountsCode for Account
   	 */
    public String getChartOfAccountsCode();
    
    /**
     * Convenience method to make the primitive Account fields from this Account easier to compare to the Account fields from
     * another Account or an AccountingLine
     * 
     * @return String representing the Account associated with this Accounting
     */
    public String getAccountKey();
    
}
