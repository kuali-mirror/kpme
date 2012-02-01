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
	private String lmAccrualCategoryId;
	private String accrualCategory;
	private String descr;
	private boolean history;
	
	
	
	@SuppressWarnings({ "rawtypes" })
	@Override
	protected LinkedHashMap toStringMapper() {
		LinkedHashMap<String,String> lhm = new LinkedHashMap<String, String>();
		lhm.put(accrualCategory, accrualCategory);
		return lhm;
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

	public String getLmAccrualCategoryId() {
		return lmAccrualCategoryId;
	}

	public void setLmAccrualCategoryId(String lmAccrualCategoryId) {
		this.lmAccrualCategoryId = lmAccrualCategoryId;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String getUniqueKey() {
		return accrualCategory;
	}

	@Override
	public String getId() {
		return getLmAccrualCategoryId();
	}

	@Override
	public void setId(String id) {
		setLmAccrualCategoryId(id);
	}

}
