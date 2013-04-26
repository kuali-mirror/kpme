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
package org.kuali.kpme.tklm.time.calendar;

import java.util.ArrayList;
import java.util.List;

import org.kuali.kpme.core.bo.calendar.CalendarDay;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.block.LeaveBlockRenderer;
import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.timeblock.TimeBlockRenderer;

public class TkCalendarDay extends CalendarDay{
	private List<TimeBlock> timeblocks = new ArrayList<TimeBlock>();
    private List<TimeBlockRenderer> blockRenderers = new ArrayList<TimeBlockRenderer>();
    private String dayNumberString;
    private Boolean gray;
    private List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
    private List<LeaveBlockRenderer> leaveBlockRenderers = new ArrayList<LeaveBlockRenderer>();

	public List<TimeBlock> getTimeblocks() {
		return timeblocks;
	}

	public void setTimeblocks(List<TimeBlock> timeblocks) {
		this.timeblocks = timeblocks;
        for (TimeBlock tb : timeblocks) {
            blockRenderers.add(new TimeBlockRenderer(tb));
        }
	}

    public List<TimeBlockRenderer> getBlockRenderers() {
        return blockRenderers;
    }

    public String getDayNumberString() {
        return dayNumberString;
    }

    public void setDayNumberString(String dayNumberString) {
        this.dayNumberString = dayNumberString;
    }

	public Boolean getGray() {
		return gray;
	}

	public void setGray(Boolean gray) {
		this.gray = gray;
	}

	public List<LeaveBlock> getLeaveBlocks() {
		return leaveBlocks;
	}

	public void setLeaveBlocks(List<LeaveBlock> leaveBlocks) {
		this.leaveBlocks = leaveBlocks;
        for (LeaveBlock lb : leaveBlocks) {
            leaveBlockRenderers.add(new LeaveBlockRenderer(lb));
        }
	}

	public List<LeaveBlockRenderer> getLeaveBlockRenderers() {
		return leaveBlockRenderers;
	}

	public void setLeaveBlockRenderers(List<LeaveBlockRenderer> leaveBlockRenderers) {
		this.leaveBlockRenderers = leaveBlockRenderers;
	}

}
