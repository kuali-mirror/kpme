package org.kuali.hr.time.user.pref;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

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
        if (StringUtils.isEmpty(timezone))
            return TkConstants.SYSTEM_TIME_ZONE;

		return timezone;
	}

	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

}
