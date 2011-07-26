package org.kuali.hr.time.earngroup;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.kuali.hr.time.HrBusinessObject;

public class EarnGroup extends HrBusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3034933572755800531L;

	private Long tkEarnGroupId;

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

	public Long getTkEarnGroupId() {
		return tkEarnGroupId;
	}

	public void setTkEarnGroupId(Long tkEarnGroupId) {
		this.tkEarnGroupId = tkEarnGroupId;
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
		return getTkEarnGroupId();
	}

	@Override
	public void setId(Long id) {
		setTkEarnGroupId(id);
	}

}
