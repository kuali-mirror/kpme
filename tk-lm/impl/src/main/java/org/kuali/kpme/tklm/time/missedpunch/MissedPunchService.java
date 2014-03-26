/**
 * Copyright 2004-2014 The Kuali Foundation
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
