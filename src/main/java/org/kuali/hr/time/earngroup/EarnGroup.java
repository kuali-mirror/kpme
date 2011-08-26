package org.kuali.hr.time.earngroup;

import org.kuali.hr.time.HrBusinessObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class EarnGroup extends HrBusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3034933572755800531L;

	private Long hrEarnGroupId;

	private String earnGroup;

	private String descr;

	private Boolean history;
	
	private Boolean showSummary;

	private List<EarnGroupDefinition> earnGroups = new ArrayList<EarnGroupDefinition>();

	public String getEarnGroup() {
		return earnGroup;
	}

	public void setEarnGroup(String earnGroup) {
		this.earnGroup = earnGroup;
	}


	public Boolean getHistory() {
		return history;
	}

	public void setHistory(Boolean history) {
		this.history = history;
	}
	

	@Override
	protected LinkedHashMap<String, Object> toStringMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setEarnGroups(List<EarnGroupDefinition> earnGroups) {
		this.earnGroups = earnGroups;
	}

	public List<EarnGroupDefinition> getEarnGroups() {
		return earnGroups;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getDescr() {
		return descr;
	}

	public Long getHrEarnGroupId() {
		return hrEarnGroupId;
	}

	public void setHrEarnGroupId(Long hrEarnGroupId) {
		this.hrEarnGroupId = hrEarnGroupId;
	}
	
	public Boolean getShowSummary() {
		return showSummary;
	}

	public void setShowSummary(Boolean showSummary) {
		this.showSummary = showSummary;
	}

	@Override
	protected String getUniqueKey() {
		return earnGroup;
	}

	@Override
	public Long getId() {
		return getHrEarnGroupId();
	}

	@Override
	public void setId(Long id) {
		setHrEarnGroupId(id);
	}

}
