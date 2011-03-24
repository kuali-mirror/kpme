package org.kuali.hr.time.missedpunch;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

import java.util.Set;

public class MissedPunchValidation extends MaintenanceDocumentRuleBase {

    /**
     * Checks the provided MissedPunch for a valid ClockAction.
     *
     * @param mp The MissedPunch we are validating.
     * @param lastClock The ClockLog entry that happened before the one we want to create.
     *
     * @return true if valid, false otherwise.
     */
    boolean validateClockAction(MissedPunch mp, ClockLog lastClock) {
        boolean valid = true;
        Set<String> validActions = TkConstants.CLOCK_ACTION_TRANSITION_MAP.get(lastClock.getClockAction());

        if (!validActions.contains(mp.getClockAction())) {
            this.putFieldError("clockAction", "clock.mp.invalid.action");
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
    boolean validateClockTime(MissedPunch mp, ClockLog lastClock) {
        boolean valid = true;

        if (lastClock == null)
            return valid;

        DateTime clockLogDateTime = new DateTime(lastClock.getClockTimestamp().getTime(), TkConstants.SYSTEM_DATE_TIME_ZONE);
        LocalTime actionTimeLocal = new LocalTime(mp.getActionTime().getTime(), TkConstants.SYSTEM_DATE_TIME_ZONE);
        DateTime actionDateTime = new DateTime(mp.getActionDate().getTime(), TkConstants.SYSTEM_DATE_TIME_ZONE);
        actionDateTime = actionDateTime.plus(actionTimeLocal.getMillisOfDay());
        DateTime boundaryMax = clockLogDateTime.plusDays(1);

        if ( (!StringUtils.equals(lastClock.getClockAction(), TkConstants.CLOCK_OUT) && actionDateTime.isAfter(boundaryMax))
                || actionDateTime.isBefore(clockLogDateTime)) {
            // Error -
            this.putFieldError("actionDate", "clock.mp.invalid.datetime");
            this.putFieldError("actionTime", "clock.mp.invalid.datetime");
            valid = false;
        }

        return valid;
    }

    @Override
	protected boolean processCustomSaveDocumentBusinessRules(MaintenanceDocument document) {
        boolean ret = true;

        PersistableBusinessObject pbo = this.getNewBo();
        if (pbo instanceof MissedPunch) {
            MissedPunch mp = (MissedPunch)pbo;

            ClockLog lastClock = TkServiceLocator.getClockLogService().getLastClockLog(mp.getPrincipalId());

            ret = validateClockAction(mp, lastClock);
            ret &= validateClockTime(mp, lastClock);
        }

        return ret;
    }
}
