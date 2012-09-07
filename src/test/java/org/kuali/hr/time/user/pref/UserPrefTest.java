package org.kuali.hr.time.user.pref;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.util.TKUtils;

public class UserPrefTest extends KPMETestCase{
	@Test
	public void testUserPrefFetch() throws Exception{
		UserPreferences userPref = TkServiceLocator.getUserPreferenceService().getUserPreferences("admin");
		Assert.assertTrue("User Pref is valid", userPref!=null && StringUtils.equals(userPref.getTimezone(),"America/Indianapolis"));
	}
	
	@Test
	public void testTimeZoneTranslate() throws Exception{
		TimeBlock tb = new TimeBlock();
		tb.setBeginTimestamp(new Timestamp(TKUtils.getCurrentDate().getTime()));
		tb.setEndTimestamp(new Timestamp(TKUtils.getCurrentDate().getTime()));
		
		List<TimeBlock> tbs = new ArrayList<TimeBlock>();
		tbs.add(tb);
		
		TkServiceLocator.getTimezoneService().translateForTimezone(tbs, "America/Indianapolis");
	}

}
