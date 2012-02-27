package org.kuali.hr.time.missedpunch;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.rules.TransactionalDocumentRuleBase;
import org.kuali.rice.kns.util.GlobalVariables;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class MissedPunchValidation extends TransactionalDocumentRuleBase {

    /**
     * Checks the provided MissedPunch for a valid ClockAction.
     *
     * @param mp The MissedPunch we are validating.
     * @param lastClock The ClockLog entry that happened before the one we want to create.
     *
     * @return true if valid, false otherwise.
     */
    boolean validateClockAction(MissedPunchDocument mp, ClockLog lastClock) {
        boolean valid = true;
        Set<String> validActions = (lastClock != null) ? TkConstants.CLOCK_ACTION_TRANSITION_MAP.get(lastClock.getClockAction()) : new HashSet<String>();

        if (!StringUtils.equals("A", mp.getDocumentStatus()) && !validActions.contains(mp.getClockAction())) {
            GlobalVariables.getMessageMap().putError("document.clockAction", "clock.mp.invalid.action");
            valid = false;
        }

        return valid;
    }

    /**
     * Checks whether the provided MissedPunch has a valid time based on the
     * previous ClockLog.
     *
     * @param mp The MissedPunch we are validating.
     * @param lastClock The ClockLog entry that happened before the one we want to create.
     *
     * @return true if valid, false otherwise.
     * @throws ParseException 
     */
    boolean validateClockTime(MissedPunchDocument mp, ClockLog lastClock) throws ParseException {
        boolean valid = true;

        if (lastClock == null)
            return valid;

        DateTime clockLogDateTime = new DateTime(lastClock.getClockTimestamp().getTime());
        DateTime boundaryMax = clockLogDateTime.plusDays(1);
        DateTime nowTime = new DateTime(TKUtils.getCurrentDate());
        
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String s = formatter.format(mp.getActionDate());
		Date tempDate = formatter.parse(s);
		Timestamp dateLocal = new Timestamp(tempDate.getTime());
        LocalTime timeLocal = new LocalTime(mp.getActionTime().getTime());
        DateTime actionDateTime = new DateTime(dateLocal.getTime());
        actionDateTime = actionDateTime.plus(timeLocal.getMillisOfDay());

        // convert the action time to the system zone 
        Timestamp ts = new Timestamp(actionDateTime.getMillis());
        ClockLog lastLog = TkServiceLocator.getClockLogService().getLastClockLog(mp.getPrincipalId());
        Long zoneOffset = TkServiceLocator.getTimezoneService().getTimezoneOffsetFromServerTime(DateTimeZone.forID(lastLog.getClockTimestampTimezone()));
        Timestamp actionTime = new Timestamp(ts.getTime()-zoneOffset);
        DateTime newDateTime = new DateTime(actionTime.getTime());

        // if date is a future date
        if(actionDateTime.getYear()> nowTime.getYear()
        		|| (actionDateTime.getYear()==nowTime.getYear() && actionDateTime.getDayOfYear() > nowTime.getDayOfYear())) {
        	GlobalVariables.getMessageMap().putError("document.actionDate", "clock.mp.future.date");
        	return false;
        }

        // if time is a future time
        if(actionDateTime.getMillis() > nowTime.getMillis()) {
        	GlobalVariables.getMessageMap().putError("document.actionTime", "clock.mp.future.time");
        	return false;
        }
        
        
        if ( ((!StringUtils.equals(lastClock.getClockAction(), TkConstants.CLOCK_OUT) && actionDateTime.isAfter(boundaryMax)) 
        		|| newDateTime.isBefore(clockLogDateTime)) && StringUtils.equals(mp.getDocumentStatus(),"R")) {
        	GlobalVariables.getMessageMap().putError("document.actionTime", "clock.mp.invalid.datetime");
            valid = false;
        }

        return valid;
    }
 

	@Override
	public boolean processRouteDocument(Document document) {
        boolean ret = super.processRouteDocument(document);
        MissedPunchDocument mpDoc = (MissedPunchDocument)document;
        ClockLog lastClock = TkServiceLocator.getClockLogService().getLastClockLog(mpDoc.getPrincipalId());
        ret = validateClockAction(mpDoc, lastClock);
        try {
			ret &= validateClockTime(mpDoc, lastClock);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return ret;
	}
}
