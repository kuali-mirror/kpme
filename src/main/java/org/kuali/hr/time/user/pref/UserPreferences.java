package org.kuali.hr.time.user.pref;

import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class UserPreferences extends PersistableBusinessObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String principalId;
	private String timezone;
	
	public UserPreferences(){}
	
	public UserPreferences(String principalId, String timeZone){
		this.principalId = principalId;
		this.timezone = timeZone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getTimezone() {
		return timezone;
	}

	@Override
	protected LinkedHashMap toStringMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}
	
}
