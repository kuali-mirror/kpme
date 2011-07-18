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
		//TODO revist this fetch for background processing(i.e. missed punch)
		if(TKContext.getUser()!=null){
			UserPreferences userPref = TKContext.getUser().getTargetUserPreferences();
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
		for(TimeBlock tb : timeBlocks){
			/*
			 *  the code below won't work since the timestamp is the same across time zone,
			 *  so that's why we need to set the time with the time zone information to the fields for the display purpose
			 */
			//tb.setBeginTimestamp(new Timestamp(modifiedStartTime.getMillis()));
			//tb.setEndTimestamp(new Timestamp(modifiedEndTime.getMillis()));

			//No need for translation if it matches the current timezone
			if(StringUtils.equals(timezone, TkConstants.SYSTEM_TIME_ZONE)){
				tb.setBeginTimeDisplay(new DateTime(tb.getBeginTimestamp()));
				tb.setEndTimeDisplay(new DateTime(tb.getEndTimestamp()));
			}
			else {
				tb.setBeginTimeDisplay(new DateTime(tb.getBeginTimestamp(),DateTimeZone.forID(timezone)));
				tb.setEndTimeDisplay(new DateTime(tb.getEndTimestamp(), DateTimeZone.forID(timezone)));
			}
		}
		return timeBlocks;
	}

	@Override
	public boolean isSameTimezone() {
		String userTimezone = TKContext.getUser().getTargetUserPreferences().getTimezone();
		if(StringUtils.isNotBlank(userTimezone)) {
			return StringUtils.equals(TkConstants.SYSTEM_TIME_ZONE, TKContext.getUser().getTargetUserPreferences().getTimezone());
		}
		return true;
	}



}
