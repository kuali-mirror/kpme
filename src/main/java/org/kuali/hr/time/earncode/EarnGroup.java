package org.kuali.hr.time.earncode;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class EarnGroup extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3034933572755800531L;

	private Long earnGroupId;

	private String earnGroup;

	private String descr;

	private Date effectiveDate;

	private Boolean active;

	private List<EarnGroupDef> earnGroups = new ArrayList<EarnGroupDef>();
	
	public Long getEarnGroupId() {
		return earnGroupId;
	}

	public void setEarnGroupId(Long earnGroupId) {
		this.earnGroupId = earnGroupId;
	}

	public String getEarnGroup() {
		return earnGroup;
	}

	public void setEarnGroup(String earnGroup) {
		this.earnGroup = earnGroup;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	protected LinkedHashMap<String, Object> toStringMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setEarnGroups(List<EarnGroupDef> earnGroups) {
		this.earnGroups = earnGroups;
	}

	public List<EarnGroupDef> getEarnGroups() {
		return earnGroups;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getDescr() {
		return descr;
	}

}
