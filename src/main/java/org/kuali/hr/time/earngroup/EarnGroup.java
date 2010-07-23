package org.kuali.hr.time.earngroup;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class EarnGroup extends PersistableBusinessObjectBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long earnsGroupId;
	private String earnGroup;
	private String descr;
	private Date effectiveDate;
	private boolean active;
	
	private List<EarnGroupDefinition> earnGroupsDefs = new ArrayList<EarnGroupDefinition>();
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		return null;
	}



	public Long getEarnsGroupId() {
		return earnsGroupId;
	}



	public void setEarnsGroupId(Long earnsGroupId) {
		this.earnsGroupId = earnsGroupId;
	}



	public String getEarnGroup() {
		return earnGroup;
	}



	public void setEarnGroup(String earnGroup) {
		this.earnGroup = earnGroup;
	}



	public String getDescr() {
		return descr;
	}



	public void setDescr(String descr) {
		this.descr = descr;
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



	public List<EarnGroupDefinition> getEarnGroupsDefs() {
		return earnGroupsDefs;
	}



	public void setEarnGroupsDefs(List<EarnGroupDefinition> earnGroupsDefs) {
		this.earnGroupsDefs = earnGroupsDefs;
	}

}
