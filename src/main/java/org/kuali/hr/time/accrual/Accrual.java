package org.kuali.hr.time.accrual;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class Accrual extends PersistableBusinessObjectBase{
	private static final long serialVersionUID = 1L;

	private Long laAccrualsId;
	private String principalId;
	private String accrualCategory;
	private Date effdt;
	private BigDecimal hoursAccrued;
	private BigDecimal hoursTaken;
	private BigDecimal hoursAdjust;
	
	private AccrualCategory accrualCategoryObj;

	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getLaAccrualsId() {
		return laAccrualsId;
	}

	public void setLaAccrualsId(Long laAccrualsId) {
		this.laAccrualsId = laAccrualsId;
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

	public Date getEffdt() {
		return effdt;
	}

	public void setEffdt(Date effdt) {
		this.effdt = effdt;
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

	public AccrualCategory getAccrualCategoryObj() {
		return accrualCategoryObj;
	}

	public void setAccrualCategoryObj(AccrualCategory accrualCategoryObj) {
		this.accrualCategoryObj = accrualCategoryObj;
	}

}
