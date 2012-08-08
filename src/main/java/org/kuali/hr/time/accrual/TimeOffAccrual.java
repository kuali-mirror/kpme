package org.kuali.hr.time.accrual;

import java.math.BigDecimal;
import java.sql.Date;

import org.kuali.hr.core.KPMEConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.impl.identity.PersonImpl;

public class TimeOffAccrual extends HrBusinessObject {
    public static final String CACHE_NAME = KPMEConstants.APPLICATION_NAMESPACE_CODE + "/" + "TimeOffAccrual";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String lmAccrualId;
	private String principalId;
	private String accrualCategory;
	private Date effectiveDate;
	private BigDecimal yearlyCarryover = new BigDecimal(0);
	private BigDecimal hoursAccrued = new BigDecimal(0);
	private BigDecimal hoursTaken = new BigDecimal(0);
	private BigDecimal hoursAdjust = new BigDecimal(0);

	private String lmAccrualCategoryId;
	
	private AccrualCategory accrualCategoryObj;

	protected PersonImpl principal;

	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	public String getAccrualCategory() {
		return accrualCategory;
	}

	public void setAccrualCategory(String accrualCategory) {
		this.accrualCategory = accrualCategory;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public BigDecimal getHoursAccrued() {
		return hoursAccrued;
	}

	public void setHoursAccrued(BigDecimal hoursAccrued) {
		this.hoursAccrued = hoursAccrued;
	}

	public BigDecimal getHoursTaken() {
		return hoursTaken;
	}

	public void setHoursTaken(BigDecimal hoursTaken) {
		this.hoursTaken = hoursTaken;
	}

	public BigDecimal getHoursAdjust() {
		return hoursAdjust;
	}

	public void setHoursAdjust(BigDecimal hoursAdjust) {
		this.hoursAdjust = hoursAdjust;
	}

	public void setAccrualCategoryObj(AccrualCategory accrualCategoryObj) {
		this.accrualCategoryObj = accrualCategoryObj;
	}

	public AccrualCategory getAccrualCategoryObj() {
		return accrualCategoryObj;
	}

	public Person getPrincipal() {
		return principal;
	}


	public String getLmAccrualId() {
		return lmAccrualId;
	}


	public void setLmAccrualId(String lmAccrualId) {
		this.lmAccrualId = lmAccrualId;
	}


	public void setPrincipal(PersonImpl principal) {
		this.principal = principal;
	}


	public String getLmAccrualCategoryId() {
		return lmAccrualCategoryId;
	}


	public void setLmAccrualCategoryId(String lmAccrualCategoryId) {
		this.lmAccrualCategoryId = lmAccrualCategoryId;
	}


	@Override
	public String getUniqueKey() {
		return accrualCategory;
	}
	
	@Override
	public String getId() {
		return getLmAccrualId();
	}

	@Override
	public void setId(String id) {
		setLmAccrualId(id);
	}


	public BigDecimal getYearlyCarryover() {
		return yearlyCarryover;
	}


	public void setYearlyCarryover(BigDecimal yearlyCarryover) {
		this.yearlyCarryover = yearlyCarryover;
	}

}
