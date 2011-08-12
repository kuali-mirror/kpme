package org.kuali.hr.time.timezone.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.kuali.hr.job.Job;
import org.kuali.hr.location.Location;
import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.principal.calendar.PrincipalCalendar;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

public class TimezoneServiceImpl implements TimezoneService {

    @Override
    @CacheResult
    public String getUserTimezone(String principalId) {
        PrincipalCalendar principalCalendar = TkServiceLocator.getPrincipalCalendarService().getPrincipalCalendar(principalId, TKUtils.getCurrentDate());
        if(principalCalendar != null && principalCalendar.getTimezone() != null){
            return principalCalendar.getTimezone();
        }
        List<Job> jobs = TkServiceLocator.getJobSerivce().getJobs(TKContext.getPrincipalId(), TKUtils.getCurrentDate());
        if (jobs.size() > 0) {
            // Grab the location off the first job in the list
            Location location = TkServiceLocator.getLocationService().getLocation(jobs.get(0).getLocation(), TKUtils.getCurrentDate());
            if (location!=null){
                if(StringUtils.isNotBlank(location.getTimezone())){
                    return location.getTimezone();
                }
            }
        }
        return TkConstants.SYSTEM_TIME_ZONE;
    }

    /**
	 * Used to determine if an override condition exists for a user timezone
	 */
	@Override
	public String getUserTimezone() {
        return getUserTimezone(TKContext.getPrincipalId());
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
		String userTimezone = getUserTimezone();
		if(StringUtils.isNotBlank(userTimezone)) {
			return StringUtils.equals(TkConstants.SYSTEM_TIME_ZONE, userTimezone);
		}
		return true;
	}



}
