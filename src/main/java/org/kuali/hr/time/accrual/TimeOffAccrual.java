package org.kuali.hr.time.accrual;

import java.sql.Date;
import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class TimeOffAccrual extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long tkAccrualId;
	private String principalId;
	private String accrualCategory;
	private Date effectiveDate;
	private Integer hoursAccrued;
	private Integer hoursTaken;
	private Integer hoursAdjust;
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		return null;
	}


	public Long getTkAccrualId() {
		return tkAccrualId;
	}


	public void setTkAccrualId(Long tkAccrualId) {
		this.tkAccrualId = tkAccrualId;
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


	public Integer getHoursAccrued() {
		return hoursAccrued;
	}


	public void setHoursAccrued(Integer hoursAccrued) {
		this.hoursAccrued = hoursAccrued;
	}


	public Integer getHoursTaken() {
		return hoursTaken;
	}


	public void setHoursTaken(Integer hoursTaken) {
		this.hoursTaken = hoursTaken;
	}


	public Integer getHoursAdjust() {
		return hoursAdjust;
	}


	public void setHoursAdjust(Integer hoursAdjust) {
		this.hoursAdjust = hoursAdjust;
	}

}
