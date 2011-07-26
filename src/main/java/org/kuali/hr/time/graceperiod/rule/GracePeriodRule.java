package org.kuali.hr.time.graceperiod.rule;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

import org.kuali.hr.time.HrBusinessObject;



public class GracePeriodRule extends HrBusinessObject {
    
	private static final long serialVersionUID = 1L;

	private Long tkGracePeriodRuleId;
	private BigDecimal hourFactor;
	private String userPrincipalId;
	
	protected LinkedHashMap<String,Object> toStringMapper() {
		LinkedHashMap<String, Object> toStringMap = new LinkedHashMap<String,Object>();
		toStringMap.put("effDt", effectiveDate);
		toStringMap.put("hourFactor", hourFactor);
		return toStringMap;
	}

	public boolean isActive() {
	    return active;
	}

	public void setActive(boolean active) {
	    this.active = active;
	}

	public BigDecimal getHourFactor() {
	    return hourFactor;
	}

	public void setHourFactor(BigDecimal hourFactor) {
	    this.hourFactor = hourFactor;
	}


	public String getUserPrincipalId() {
	    return userPrincipalId;
	}


	public void setUserPrincipalId(String userPrincipalId) {
	    this.userPrincipalId = userPrincipalId;
	}


	public Timestamp getTimestamp() {
	    return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
	    this.timestamp = timestamp;
	}


	public Long getTkGracePeriodRuleId() {
		return tkGracePeriodRuleId;
	}


	public void setTkGracePeriodRuleId(Long tkGracePeriodRuleId) {
		this.tkGracePeriodRuleId = tkGracePeriodRuleId;
	}


	@Override
	protected String getUniqueKey() {
		return hourFactor + "";
	}

	@Override
	public Long getId() {
		return getTkGracePeriodRuleId();
	}

	@Override
	public void setId(Long id) {
		setTkGracePeriodRuleId(id);
	}


}
