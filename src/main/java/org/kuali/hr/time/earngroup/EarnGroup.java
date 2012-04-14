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

	private String hrEarnGroupId;

	private String earnGroup;

	private String descr;

	private Boolean history;
	
	private Boolean showSummary;

	private List<EarnGroupDefinition> earnGroups = new ArrayList<EarnGroupDefinition>();
	
	private String warningText;

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

	public String getHrEarnGroupId() {
		return hrEarnGroupId;
	}

	public void setHrEarnGroupId(String hrEarnGroupId) {
		this.hrEarnGroupId = hrEarnGroupId;
	}
	
	public Boolean getShowSummary() {
		return showSummary;
	}

	public void setShowSummary(Boolean showSummary) {
		this.showSummary = showSummary;
	}

	@Override
	public String getUniqueKey() {
		return earnGroup;
	}

	@Override
	public String getId() {
		return getHrEarnGroupId();
	}

	@Override
	public void setId(String id) {
		setHrEarnGroupId(id);
	}

	public String getWarningText() {
		return warningText;
	}

	public void setWarningText(String warningText) {
		this.warningText = warningText;
	}

}
