package org.kuali.hr.time.calendar;

import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.workarea.WorkArea;

import java.util.ArrayList;
import java.util.List;

/**
 * Render helper to handle timeblock and time hour details display
 */
public class TimeBlockRenderer {
    private TimeBlock timeBlock;
    private List<TimeHourDetailRenderer> detailRenderers = new ArrayList<TimeHourDetailRenderer>();

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

        b.append(timeBlock.getBeginTimeString());
        b.append(" - ");
        b.append(timeBlock.getEndTimeString());

        return b.toString();
    }

    public String getTitle() {
        StringBuilder b = new StringBuilder();

        WorkArea wa = TkServiceLocator.getWorkAreaService().getWorkArea(timeBlock.getTkWorkAreaId());
        b.append(wa.getDescription());

        return b.toString();
    }

}
