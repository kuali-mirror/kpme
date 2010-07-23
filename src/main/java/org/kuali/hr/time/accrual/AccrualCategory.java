package org.kuali.hr.time.accrual;

import java.sql.Date;
import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class AccrualCategory extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long tkAccrualCategoryId;
	private String accrualCategory;
	private String descr;
	private Date effectiveDate;
	private boolean active;
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		// TODO Auto-generated method stub
		return null;
	}



	public Long getTkAccrualCategoryId() {
		return tkAccrualCategoryId;
	}



	public void setTkAccrualCategoryId(Long tkAccrualCategoryId) {
		this.tkAccrualCategoryId = tkAccrualCategoryId;
	}



	public String getAccrualCategory() {
		return accrualCategory;
	}



	public void setAccrualCategory(String accrualCategory) {
		this.accrualCategory = accrualCategory;
	}



	public String getDescr() {
		return descr;
	}



	public void setDescr(String descr) {
		this.descr = descr;
	}



	public boolean isActive() {
		return active;
	}



	public void setActive(boolean active) {
		this.active = active;
	}



	public Date getEffectiveDate() {
		return effectiveDate;
	}



	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

}
