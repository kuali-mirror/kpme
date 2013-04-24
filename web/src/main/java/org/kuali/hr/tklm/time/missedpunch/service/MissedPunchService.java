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
package org.kuali.hr.tklm.time.missedpunch.service;

import java.util.List;

import org.kuali.hr.tklm.time.missedpunch.MissedPunchDocument;


public interface MissedPunchService {
	/**
	 * Fetch missed punch by header id
	 * @param headerId
	 * @return
	 */
    public MissedPunchDocument getMissedPunchByRouteHeader(String headerId);
    /**
     * Add clock log for missed punch
     * @param missedPunch
     * @param logEndId
     * @param logBeginId
     */
    public void addClockLogForMissedPunch(MissedPunchDocument missedPunch, String logEndId, String logBeginId);
    /**
     * Add clock log for missed punch
     * @param missedPunch
     */
    public void addClockLogForMissedPunch(MissedPunchDocument missedPunch);
    /**
     * Update clock log and time block if necessary
     * @param missedPunch
     */
    public void updateClockLogAndTimeBlockIfNecessary(MissedPunchDocument missedPunch);
    /**
     * Get missed punch by clock id
     * @param clockLogId
     * @return
     */
    public MissedPunchDocument getMissedPunchByClockLogId(String clockLogId);
    /**
     * Approve missed punch document
     * @param document
     * @return
     */
    public void approveMissedPunch(MissedPunchDocument document);
    /**
     * Get missed punch documents by timesheetDocumentId
     * @param timesheetDocumentId
     * @return
     */
    public List<MissedPunchDocument> getMissedPunchDocsByTimesheetDocumentId(String timesheetDocumentId);
}
