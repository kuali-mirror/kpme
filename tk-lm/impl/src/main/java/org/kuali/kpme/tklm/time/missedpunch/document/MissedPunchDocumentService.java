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
package org.kuali.kpme.tklm.time.missedpunch.document;

import java.util.List;

import org.kuali.kpme.tklm.time.missedpunch.MissedPunch;
import org.kuali.kpme.tklm.time.missedpunch.MissedPunchDocument;

public interface MissedPunchDocumentService {

	/**
     * Get a list of Missed Punch Documents by Timesheet document ids.
     *
     * @param timesheetDocumentId The Timesheet document id to look up
     *
     * @return a list of Missed Punch Documents associated with the given Timesheet document id
     */
    List<MissedPunchDocument> getMissedPunchDocumentsByTimesheetDocumentId(String timesheetDocumentId);
    
	/**
     * Approve a Missed Punch Document.
     *
     * @param missedPunchDocument The Missed Punch Document to approve
     */
    public void approveMissedPunchDocument(MissedPunchDocument missedPunchDocument);

	public MissedPunchDocument getMissedPunchDocumentByMissedPunchId(String tkMissedPunchId);
    
}
