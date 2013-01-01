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
package org.kuali.hr.time.user.pref.service;

import org.kuali.hr.time.user.pref.UserPreferences;
import org.kuali.hr.time.user.pref.dao.UserPreferenceDao;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

public class UserPreferenceServiceImpl implements UserPreferenceService {
	private UserPreferenceDao userPrefDao;
	
	
	@Override
	public UserPreferences getUserPreferences(String principalId) {
		UserPreferences userPref = userPrefDao.getUserPreferences(principalId);
		if(userPref == null){
			return new UserPreferences(TKContext.getPrincipalId(), TKUtils.getSystemTimeZone());
		}
		return userPref;
	}


	public UserPreferenceDao getUserPrefDao() {
		return userPrefDao;
	}


	public void setUserPrefDao(UserPreferenceDao userPrefDao) {
		this.userPrefDao = userPrefDao;
	}

}
