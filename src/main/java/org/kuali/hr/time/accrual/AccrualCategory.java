package org.kuali.hr.time.accrual;

import org.kuali.hr.time.HrBusinessObject;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

public class AccrualCategory extends HrBusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long lmAccrualCategoryId;
	private String accrualCategory;
	private String descr;
	private boolean history;
	
	
	
	@SuppressWarnings({ "rawtypes" })
	@Override
	protected LinkedHashMap toStringMapper() {
		// TODO Auto-generated method stub
		return null;
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


	public boolean isHistory() {
		return history;
	}

	public void setHistory(boolean history) {
		this.history = history;
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

	public Long getLmAccrualCategoryId() {
		return lmAccrualCategoryId;
	}

	public void setLmAccrualCategoryId(Long lmAccrualCategoryId) {
		this.lmAccrualCategoryId = lmAccrualCategoryId;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	protected String getUniqueKey() {
		return accrualCategory;
	}

	@Override
	public Long getId() {
		return getLmAccrualCategoryId();
	}

	@Override
	public void setId(Long id) {
		setLmAccrualCategoryId(id);
	}

}
