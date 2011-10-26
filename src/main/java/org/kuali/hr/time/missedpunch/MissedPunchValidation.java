package org.kuali.hr.time.missedpunch;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.rules.TransactionalDocumentRuleBase;
import org.kuali.rice.kns.util.GlobalVariables;

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
     */
    boolean validateClockTime(MissedPunchDocument mp, ClockLog lastClock) {
        boolean valid = true;

        if (lastClock == null)
            return valid;

        DateTime clockLogDateTime = new DateTime(lastClock.getClockTimestamp().getTime());
        LocalTime actionTimeLocal = new LocalTime(mp.getActionTime().getTime());
        DateTime actionDateTime = new DateTime(mp.getActionDate().getTime());
        actionDateTime = actionDateTime.plus(actionTimeLocal.getMillisOfDay());
        DateTime boundaryMax = clockLogDateTime.plusDays(1);
        DateTime nowTime = new DateTime(System.currentTimeMillis());
        
        // if date is a future date
        if(actionDateTime.getDayOfYear() > nowTime.getDayOfYear()) {
        	GlobalVariables.getMessageMap().putError("document.actionDate", "clock.mp.future.date");
        	return false;
        }
        // if time is a future time
        if(actionTimeLocal.getMillisOfDay() > nowTime.getMillisOfDay()) {
        	GlobalVariables.getMessageMap().putError("document.actionTime", "clock.mp.future.time");
        	return false;
        }
        
        if ( ((!StringUtils.equals(lastClock.getClockAction(), TkConstants.CLOCK_OUT) && actionDateTime.isAfter(boundaryMax)) 
        		|| actionDateTime.isBefore(clockLogDateTime)) && StringUtils.equals(mp.getDocumentStatus(),"R")) {
            // Error -
//        	GlobalVariables.getMessageMap().putError("document.actionDate", "clock.mp.invalid.datetime");
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
        ret &= validateClockTime(mpDoc, lastClock);
        
        return ret;
	}
}
