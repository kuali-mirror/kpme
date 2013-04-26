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
package org.kuali.hr.tklm.time.timeblock;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.core.task.Task;
import org.kuali.hr.core.workarea.WorkArea;
import org.kuali.hr.tklm.common.TkConstants;
import org.kuali.hr.tklm.time.timehourdetail.TimeHourDetailRenderer;

/**
 * Render helper to handle timeblock and time hour details display
 */
public class TimeBlockRenderer {

    private TimeBlock timeBlock;
    private List<TimeHourDetailRenderer> detailRenderers = new ArrayList<TimeHourDetailRenderer>();
    private String assignmentClass;
    private String lunchLabel;
    private String lunchLabelId;

    public TimeBlockRenderer(TimeBlock b) {
        this.timeBlock = b;
        for (TimeHourDetail detail : timeBlock.getTimeHourDetails()) {
            detailRenderers.add(new TimeHourDetailRenderer(detail));
        }
    }

    public List<TimeHourDetailRenderer> getDetailRenderers() {
        return detailRenderers;
    }

    public TimeBlock getTimeBlock() {
        return timeBlock;
    }

    public String getTimeRange() {
        StringBuilder b = new StringBuilder();
        if(StringUtils.equals(timeBlock.getEarnCodeType(), TkConstants.EARN_CODE_TIME)) {
            b.append(timeBlock.getBeginTimeDisplay().toString(TkConstants.DT_BASIC_TIME_FORMAT));
            b.append(" - ");
            b.append(timeBlock.getEndTimeDisplay().toString(TkConstants.DT_BASIC_TIME_FORMAT));
        }

        return b.toString();
    }

    public String getTitle() {
        StringBuilder b = new StringBuilder();

        WorkArea wa = HrServiceLocator.getWorkAreaService().getWorkArea(timeBlock.getWorkArea(), LocalDate.now());
        b.append(wa.getDescription());
        Task task = HrServiceLocator.getTaskService().getTask(timeBlock.getTask(), timeBlock.getBeginDateTime().toLocalDate());
        if(task != null) {
        	// do not display task description if the task is the default one
        	// default task is created in getTask() of TaskService
        	if(!task.getDescription().equals(TkConstants.TASK_DEFAULT_DESP)) {
        		b.append("-" + task.getDescription());
        	}
        }
        return b.toString();
    }

    public String getAmount() {
    	if(StringUtils.equals(timeBlock.getEarnCodeType(), TkConstants.EARN_CODE_AMOUNT)) {
    		if(timeBlock.getAmount() != null) {
    			return timeBlock.getEarnCode() + ": $" + timeBlock.getAmount().toString();
    		} else {
    			return timeBlock.getEarnCode() + ": $0.00";
    		}
	    }
    	return "";
    }

    public String getEarnCodeType() {
    	return timeBlock.getEarnCodeType();
    }

	public String getAssignmentClass() {
		return assignmentClass;
	}

	public void setAssignmentClass(String assignmentClass) {
		this.assignmentClass = assignmentClass;
	}

	public String getLunchLabel() {
		return lunchLabel;
	}

	public void setLunchLabel(String lunchLabel) {
		this.lunchLabel = lunchLabel;
	}

    public String getLunchLabelId() {
        return lunchLabelId;
    }

    public void setLunchLabelId(String lunchLabelId) {
        this.lunchLabelId = lunchLabelId;
    }
}
