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
package org.kuali.kpme.tklm.time.rules;

import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;

import java.util.List;

public interface TkRuleControllerService {

    /**
     * This method mutates the List<TimeBlock> that is passed in. If you need
     * to reference old vs. new changes, be sure to copy/clone the list before
     * passing it to this method.
     */
    public List<TimeBlock> applyRules(String action, List<TimeBlock> timeBlocks, List<LeaveBlock> leaveBlocks, CalendarEntry payEntry, TimesheetDocument timesheetDocument, String principalId);
}
