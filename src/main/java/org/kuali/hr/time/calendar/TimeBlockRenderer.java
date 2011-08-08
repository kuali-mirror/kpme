package org.kuali.hr.time.calendar;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workarea.WorkArea;

import java.util.ArrayList;
import java.util.List;

/**
 * Render helper to handle timeblock and time hour details display
 */
public class TimeBlockRenderer {
    private TimeBlock timeBlock;
    private List<TimeHourDetailRenderer> detailRenderers = new ArrayList<TimeHourDetailRenderer>();
    private String assignmentClass;

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
            b.append(timeBlock.getBeginTimeString());
            b.append(" - ");
            b.append(timeBlock.getEndTimeString());
        }

        return b.toString();
    }

    public String getTitle() {
        StringBuilder b = new StringBuilder();

        WorkArea wa = TkServiceLocator.getWorkAreaService().getWorkArea(timeBlock.getTkWorkAreaId());
        b.append(wa.getDescription());

        return b.toString();
    }
    
    public String getAmount() {
    	if(timeBlock.getAmount() != null) {
    		return timeBlock.getAmount().toString();
    	}
    	return ""; 
    }

	public String getAssignmentClass() {
		return assignmentClass;
	}

	public void setAssignmentClass(String assignmentClass) {
		this.assignmentClass = assignmentClass;
	}

}
