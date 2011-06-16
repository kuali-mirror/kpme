package org.kuali.hr.time.accrual;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.LinkedHashMap;

import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.bo.impl.PersonImpl;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class TimeOffAccrual extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long laAccrualId;
	private String principalId;
	private String accrualCategory;
	private Date effectiveDate;
	private BigDecimal hoursAccrued = new BigDecimal("0");
	private BigDecimal hoursTaken = new BigDecimal("0");
	private BigDecimal hoursAdjust = new BigDecimal("0");

	private Long laAccrualCategoryId;
	
	private AccrualCategory accrualCategoryObj;

	protected PersonImpl principal;

	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		return null;
	}


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


	public Long getLaAccrualId() {
		return laAccrualId;
	}


	public void setLaAccrualId(Long laAccrualId) {
		this.laAccrualId = laAccrualId;
	}


	public void setPrincipal(PersonImpl principal) {
		this.principal = principal;
	}


	public Long getLaAccrualCategoryId() {
		return laAccrualCategoryId;
	}


	public void setLaAccrualCategoryId(Long laAccrualCategoryId) {
		this.laAccrualCategoryId = laAccrualCategoryId;
	}

}
