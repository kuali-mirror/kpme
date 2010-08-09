package org.kuali.hr.time.earncode;

import java.sql.Date;
import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class EarnGroupDef extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8463674251885306591L;

	private Long earnGroupDefId;

	private String earnCode;

	private Long earnsGroup;

	private Date effectiveDate;

	public Long getEarnGroupDefId() {
		return earnGroupDefId;
	}

	public void setEarnGroupDefId(Long earnGroupDefId) {
		this.earnGroupDefId = earnGroupDefId;
	}

	public String getEarnCode() {
		return earnCode;
	}

	public void setEarnCode(String earnCode) {
		this.earnCode = earnCode;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	@Override
	protected LinkedHashMap<String, Object> toStringMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setEarnsGroup(Long earnsGroup) {
		this.earnsGroup = earnsGroup;
	}

	public Long getEarnsGroup() {
		return earnsGroup;
	}

}
