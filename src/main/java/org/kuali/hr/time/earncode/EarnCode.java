package org.kuali.hr.time.earncode;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.accrual.AccrualCategory;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class EarnCode extends HrBusinessObject {

	/**
     *
     */
	private static final long serialVersionUID = 1L;

	private Long tkEarnCodeId;
	private String earnCode;
	private String description;
	//used for clock in and out
	private Boolean recordHours;
	//used for recording time
	private Boolean recordTime;
	private Boolean recordAmount;
    private Boolean ovtEarnCode;
	private String accrualCategory;
	private BigDecimal inflateMinHours;
	private BigDecimal inflateFactor;

	private boolean history;

	private AccrualCategory accrualCategoryObj;

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

	public Long getTkEarnCodeId() {
		return tkEarnCodeId;
	}

	public void setTkEarnCodeId(Long tkEarnCodeId) {
		this.tkEarnCodeId = tkEarnCodeId;
	}

	public Boolean getRecordHours() {
		return recordHours;
	}

	public void setRecordHours(Boolean recordHours) {
		this.recordHours = recordHours;
	}

	public Boolean getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Boolean recordTime) {
		this.recordTime = recordTime;
	}

	public Boolean getRecordAmount() {
		return recordAmount;
	}

	public void setRecordAmount(Boolean recordAmount) {
		this.recordAmount = recordAmount;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getAccrualCategory() {
		return accrualCategory;
	}

	public void setAccrualCategory(String accrualCategory) {
		this.accrualCategory = accrualCategory;
	}

	public AccrualCategory getAccrualCategoryObj() {
		if(accrualCategoryObj == null && !this.getAccrualCategory().isEmpty()) {
			this.assingAccrualCategoryObj();
		}
		return accrualCategoryObj;
	}
	public void assingAccrualCategoryObj() {
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("accrualCategory", getAccrualCategory());
		Collection c = KNSServiceLocator.getBusinessObjectService().findMatching(AccrualCategory.class, parameters);
		if(!c.isEmpty()) {
			this.setAccrualCategoryObj((AccrualCategory)c.toArray()[0]);
		}
	}

	public void setAccrualCategoryObj(AccrualCategory accrualCategoryObj) {
		this.accrualCategoryObj = accrualCategoryObj;
	}

	public BigDecimal getInflateMinHours() {
		return inflateMinHours;
	}

	public void setInflateMinHours(BigDecimal inflateMinHours) {
		this.inflateMinHours = inflateMinHours;
	}

	public BigDecimal getInflateFactor() {
		return inflateFactor;
	}

	public void setInflateFactor(BigDecimal inflateFactor) {
		this.inflateFactor = inflateFactor;
	}

    public Boolean getOvtEarnCode() {
        return ovtEarnCode;
    }

    public void setOvtEarnCode(Boolean ovtEarnCode) {
        this.ovtEarnCode = ovtEarnCode;
    }

    /**
	 * This is used by the timeblock json object.
	 * The purpose of this function is to create a string based on the record_* fields which can be used to render hour / begin(end) time input box
	 * @return String fieldType
	 */
	public String getEarnCodeType() {
		if(getRecordHours()) {
			return TkConstants.EARN_CODE_HOUR;
		}
		else if(getRecordTime()) {
			return TkConstants.EARN_CODE_TIME;
		}
		else if(getRecordAmount()) {
			return TkConstants.EARN_CODE_AMOUNT;
		}
		else {
			return "";
		}
	}

	@Override
	protected String getUniqueKey() {
		return earnCode;
	}
}
