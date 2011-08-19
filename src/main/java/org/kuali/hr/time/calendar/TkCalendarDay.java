package org.kuali.hr.time.calendar;

import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.time.timeblock.TimeBlock;

public class TkCalendarDay {
	private List<TimeBlock> timeblocks = new ArrayList<TimeBlock>();
    private List<TimeBlockRenderer> blockRenderers = new ArrayList<TimeBlockRenderer>();
    private String dayNumberString;
    private int dayNumberDelta;
    private Boolean gray;

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

    public int getDayNumberDelta() {
        return dayNumberDelta;
    }

    public void setDayNumberDelta(int dayNumberDelta) {
        this.dayNumberDelta = dayNumberDelta;
    }

	public Boolean getGray() {
		return gray;
	}

	public void setGray(Boolean gray) {
		this.gray = gray;
	}
}
