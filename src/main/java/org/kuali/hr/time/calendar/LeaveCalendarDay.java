/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.calendar;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.leaveblock.LeaveBlock;

public class LeaveCalendarDay extends CalendarDay {

    private List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
    private List<LeaveBlockRenderer> leaveBlockRenderers = new ArrayList<LeaveBlockRenderer>();

    public List<LeaveBlockRenderer> getLeaveBlockRenderers() {
        return leaveBlockRenderers;
    }

    public List<LeaveBlock> getLeaveBlocks() {
        return leaveBlocks;
    }

    public void setLeaveBlocks(List<LeaveBlock> leaveBlocks) {
        this.leaveBlocks = leaveBlocks;
        for (LeaveBlock leaveBlock : leaveBlocks) {
            leaveBlockRenderers.add(new LeaveBlockRenderer(leaveBlock));
        }
    }
}
