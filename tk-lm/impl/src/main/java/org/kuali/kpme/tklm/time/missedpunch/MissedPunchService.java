package org.kuali.kpme.tklm.time.missedpunch;


import java.util.List;

public interface MissedPunchService {
    /**
     * Get a list of Missed Punches by Timesheet document ids.
     *
     * @param timesheetDocumentId The Timesheet document id to look up
     *
     * @return a list of Missed Punch Documents associated with the given Timesheet document id
     */
    List<MissedPunch> getMissedPunchByTimesheetDocumentId(String timesheetDocumentId);

    /**
     * Get a Missed Punch by its unique Clock Log id.
     *
     * @param clockLogId The Clock Log id to look up
     *
     * @return the Missed Punch associated with the given Clock Log id
     */
    public MissedPunch getMissedPunchByClockLogId(String clockLogId);

    /**
     * Add a Clock Log to the specified Missed Punch
     *
     * @param missedPunch The Missed Punch to add the Clock Log to
     * @param ipAddress The IP address of the user
     */
    public MissedPunch addClockLog(MissedPunch missedPunch, String ipAddress);

    /**
     * Update the Clock Log (and any Time Blocks if necessary) for the given Missed Punch.
     *
     * @param missedPunch The Missed Punch to update the Clock Logs for
     * @param ipAddress The IP address of the user
     */
    public MissedPunch updateClockLog(MissedPunch missedPunch, String ipAddress);
}
