package org.kuali.hr.time.timeblock;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class TimeHourDetail extends PersistableBusinessObjectBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long tkTimeHourDetailId;
	private Long tkTimeBlockId;
	private String earnCode;
	private BigDecimal hours = new BigDecimal("0.00");
	private BigDecimal amount = new BigDecimal("0.00");
	
	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		return null;
	}

	public String getEarnCode() {
		return earnCode;
	}

	public void setEarnCode(String earnCode) {
		this.earnCode = earnCode;
	}

	public BigDecimal getHours() {
		return hours;
	}

	public void setHours(BigDecimal hours) {
		this.hours = hours;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Long getTkTimeBlockId() {
		return tkTimeBlockId;
	}

	public void setTkTimeBlockId(Long tkTimeBlockId) {
		this.tkTimeBlockId = tkTimeBlockId;
	}
	public void setTkTimeHourDetailId(Long tkTimeHourDetailId) {
		this.tkTimeHourDetailId = tkTimeHourDetailId;
	}

	public Long getTkTimeHourDetailId() {
		return tkTimeHourDetailId;
	}
}
