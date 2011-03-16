package org.kuali.hr.time.syslunch.rule;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

import org.kuali.hr.time.rule.TkRule;

public class SystemLunchRule extends TkRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long tkSystemLunchRuleId;
	private Date effectiveDate;
	private Boolean showLunchButton = false;
	private boolean history;
	private boolean active;
	private String userPrincipalId;
	private Timestamp timeStamp;

	
	public Timestamp getTimeStamp() {
		return timeStamp;
	}


	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}


	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getTkSystemLunchRuleId() {
		return tkSystemLunchRuleId;
	}

	public void setTkSystemLunchRuleId(Long tkSystemLunchRuleId) {
		this.tkSystemLunchRuleId = tkSystemLunchRuleId;
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

	public String getUserPrincipalId() {
		return userPrincipalId;
	}

	public void setUserPrincipalId(String userPrincipalId) {
		this.userPrincipalId = userPrincipalId;
	}


	public Boolean getShowLunchButton() {
		return showLunchButton;
	}


	public void setShowLunchButton(Boolean showLunchButton) {
		this.showLunchButton = showLunchButton;
	}

}
