package org.kuali.hr.lm.balancetransfer;

import java.math.BigDecimal;
import java.sql.Date;

import org.kuali.hr.core.KPMEConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.document.TransactionalDocumentBase;
import org.kuali.rice.krad.rules.rule.event.KualiDocumentEvent;


public class BalanceTransfer extends HrBusinessObject {

    //public static final String CACHE_NAME = KPMEConstants.APPLICATION_NAMESPACE_CODE + "/" + "BalanceTransfer";

	private static final long serialVersionUID = 1L;

	private String balanceTransferId;
	private String accrualCategoryRule;
	private String principalId;
	private String toAccrualCategory;
	private String fromAccrualCategory;
	private BigDecimal transferAmount;
	private BigDecimal forfeitedAmount;
	private String toEarnCode; //for payout through earn code - payroll module integration
	private Date effectiveDate;
	
	private Person principal;
	private EarnCode payoutEarnCode;
	private AccrualCategory creditedAccrualCategory;
	private AccrualCategory debitedAccrualCategory;

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	
	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	public String getToAccrualCategory() {
		return toAccrualCategory;
	}

	public void setToAccrualCategory(String toAccrualCategory) {
		this.toAccrualCategory = toAccrualCategory;
	}

	public String getFromAccrualCategory() {
		return fromAccrualCategory;
	}

	public void setFromAccrualCategory(String fromAccrualCategory) {
		this.fromAccrualCategory = fromAccrualCategory;
	}

	public BigDecimal getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(BigDecimal transferAmount) {
		this.transferAmount = transferAmount;
	}

	public BigDecimal getForfeitedAmount() {
		return forfeitedAmount;
	}

	public void setForfeitedAmount(BigDecimal forfeitedAmount) {
		this.forfeitedAmount = forfeitedAmount;
	}
	
	public String getBalanceTransferId() {
		return balanceTransferId;
	}

	public void setBalanceTransferId(String balanceTransferId) {
		this.balanceTransferId = balanceTransferId;
	}
	
	public String getAccrualCategoryRule() {
		return accrualCategoryRule;
	}

	public void setAccrualCategoryRule(String accrualCategoryRule) {
		this.accrualCategoryRule = accrualCategoryRule;
	}
	
	public String getToEarnCode() {
		return toEarnCode;
	}

	public void setToEarnCode(String toEarnCode) {
		this.toEarnCode = toEarnCode;
	}

	@Override
	protected String getUniqueKey() {
		// TODO Auto-generated method stub
		return balanceTransferId;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return getBalanceTransferId();
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		setBalanceTransferId(id);
	}

	public Person getPrincipal() {
		return principal;
	}

	public void setPrincipal(Person principal) {
		this.principal = principal;
	}

	public EarnCode getPayoutEarnCode() {
		return payoutEarnCode;
	}

	public void setPayoutEarnCode(EarnCode payoutEarnCode) {
		this.payoutEarnCode = payoutEarnCode;
	}

	public AccrualCategory getCreditedAccrualCategory() {
		return creditedAccrualCategory;
	}

	public void setCreditedAccrualCategory(AccrualCategory creditedAccrualCategory) {
		this.creditedAccrualCategory = creditedAccrualCategory;
	}

	public AccrualCategory getDebitedAccrualCategory() {
		return debitedAccrualCategory;
	}

	public void setDebitedAccrualCategory(AccrualCategory debitedAccrualCategory) {
		this.debitedAccrualCategory = debitedAccrualCategory;
	}
	
}
