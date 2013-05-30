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
package org.kuali.hr.time.user.pref;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.KPMETestCase;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.user.pref.UserPreferences;

public class UserPrefTest extends KPMETestCase{
	@Test
	public void testUserPrefFetch() throws Exception{
		UserPreferences userPref = TkServiceLocator.getUserPreferenceService().getUserPreferences("admin");
		Assert.assertTrue("User Pref is valid", userPref!=null && StringUtils.equals(userPref.getTimezone(),"America/Indianapolis"));
	}
	
	@Test
	public void testTimeZoneTranslate() throws Exception{
		TimeBlock tb = new TimeBlock();
		tb.setBeginDateTime(new DateTime());
		tb.setEndDateTime(new DateTime());
		
		List<TimeBlock> tbs = new ArrayList<TimeBlock>();
		tbs.add(tb);
		
		//HrServiceLocator.getTimezoneService().translateForTimezone(tbs, DateTimeZone.forID("America/Indianapolis"));
	}

}
