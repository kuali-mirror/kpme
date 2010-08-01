package org.kuali.hr.time.earncode;

import java.sql.Date;
import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class EarnCode extends PersistableBusinessObjectBase {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	private Long earnCodeId;
	private String earnCode;
	private String description;
	private Date effectiveDate;
	private boolean active;

	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		return null;
	}

	public Long getEarnCodeId() {
		return earnCodeId;
	}

	public void setEarnCodeId(Long earnCodeId) {
		this.earnCodeId = earnCodeId;
	}

	public String getEarnCode() {
		return earnCode;
	}

	public void setEarnCode(String earnCode) {
		this.earnCode = earnCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
