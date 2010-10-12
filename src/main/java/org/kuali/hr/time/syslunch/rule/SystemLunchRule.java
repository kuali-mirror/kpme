package org.kuali.hr.time.syslunch.rule;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

import org.kuali.hr.time.rule.TkRule;
import org.kuali.hr.time.rule.TkRuleContext;

public class SystemLunchRule extends TkRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long tkSystemLunchRuleId;
	private Date effectiveDate;
	private BigDecimal minMinutes;
	private BigDecimal blockHours;
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

	public BigDecimal getMinMinutes() {
		return minMinutes;
	}

	public void setMinMinutes(BigDecimal minMinutes) {
		this.minMinutes = minMinutes;
	}

	public BigDecimal getBlockHours() {
		return blockHours;
	}

	public void setBlockHours(BigDecimal blockHours) {
		this.blockHours = blockHours;
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

}
