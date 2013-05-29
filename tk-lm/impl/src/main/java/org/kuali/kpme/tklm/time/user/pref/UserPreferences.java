/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.tklm.time.user.pref;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.KPMEConstants;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.common.TkConstants;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class UserPreferences extends PersistableBusinessObjectBase {
    public static final String CACHE_NAME = TkConstants.CacheNamespace.NAMESPACE_PREFIX + "UserPreferences";
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
            return TKUtils.getSystemTimeZone();

		return timezone;
	}

	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

}
