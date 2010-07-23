package org.kuali.hr.time.earngroup;

import java.sql.Date;
import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class EarnGroupDefinition extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String earnsGroup;
	private String earnCode;
	private Date effectiveDate;
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		return null;
	}


	public String getEarnsGroup() {
		return earnsGroup;
	}


	public void setEarnsGroup(String earnsGroup) {
		this.earnsGroup = earnsGroup;
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

}
