package org.kuali.hr.time.timezone.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.user.pref.UserPreferences;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

public class TimezoneServiceImpl implements TimezoneService {
	
	/**
	 * Used to determine if an override condition exists for a user timezone
	 */
	@Override
	public String getUserTimeZone() {
		UserPreferences userPref = TKContext.getUser().getUserPreference();
		if(userPref != null && StringUtils.isNotEmpty(userPref.getTimezone())){
			return userPref.getTimezone();
		}
		
		if(!TkConstants.LOCATION_TO_TIME_ZONE_MAP.isEmpty()){
			//TODO change current date to context of document being looked at
			List<Job> lstJobs = TkServiceLocator.getJobSerivce().getJobs(TKContext.getPrincipalId(), TKUtils.getCurrentDate());
			if(lstJobs.size() > 0){
				//Grab the location off the first job in the list
				String location = lstJobs.get(0).getLocation();
				if(TkConstants.LOCATION_TO_TIME_ZONE_MAP.containsKey(location)){
					return TkConstants.LOCATION_TO_TIME_ZONE_MAP.get(location);
				}
			}
		}
		return TkConstants.SYSTEM_TIME_ZONE;
	}
	/**
	 * Translation needed for UI Display
	 * @param timeBlocks
	 * @param timezone
	 * @return timeblock list modified with times offset for timezone
	 */
	public List<TimeBlock> translateForTimezone(List<TimeBlock> timeBlocks, String timezone){
		//No need for translation if it matches the current timezone
		if(StringUtils.equals(timezone, TkConstants.SYSTEM_TIME_ZONE)){
			return timeBlocks;
		}
		for(TimeBlock tb : timeBlocks){
			DateTime modifiedStartTime = new DateTime(tb.getBeginTimestamp(),DateTimeZone.forID(timezone));  
			DateTime modifiedEndTime = new DateTime(tb.getEndTimestamp(), DateTimeZone.forID(timezone));
			tb.setBeginTimeDisplay(modifiedStartTime.toString());
			tb.setEndTimeDisplay(modifiedEndTime.toString());
		}
		return timeBlocks;
	}

}
