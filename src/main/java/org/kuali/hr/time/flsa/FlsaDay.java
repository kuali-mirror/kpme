package org.kuali.hr.time.flsa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalTime;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

public class FlsaDay {
	private Map<String,BigDecimal> earnCodeToHours = new HashMap<String,BigDecimal>();
	private Map<String,List<TimeBlock>> earnCodeToTimeBlocks = new HashMap<String,List<TimeBlock>>();
	private List<TimeBlock> appliedTimeBlocks = new ArrayList<TimeBlock>();
	LocalTime flsaBeginTime;
	
	Interval flsaDateInterval;
	DateTime flsaDate;

	public FlsaDay(DateTime flsaDate, List<TimeBlock> timeBlocks) {
		this.flsaDate = flsaDate;
		flsaDateInterval = new Interval(flsaDate, flsaDate.plusHours(24));
		this.setTimeBlocks(timeBlocks);
	}
	
	/**
	 * Handles the breaking apart of existing time blocks around FLSA boundaries.
	 * 
	 * This method will compare the FLSA interval against the timeblock interval
	 * to determine how many hours overlap.  It will then examine the time hour 
	 * details 
	 * @param timeBlocks a sorted list of time blocks.
	 */
	public void setTimeBlocks(List<TimeBlock> timeBlocks) {
		for (TimeBlock block : timeBlocks) {
			
			DateTime beginDateTime = new DateTime(block.getBeginTimestamp(), TkConstants.SYSTEM_DATE_TIME_ZONE);
			DateTime endDateTime = new DateTime(block.getEndTimestamp(), TkConstants.SYSTEM_DATE_TIME_ZONE);
			
			if (beginDateTime.isAfter(flsaDateInterval.getEnd())) {
				break;
			}
			Interval timeBlockInterval = new Interval(beginDateTime, endDateTime);
			
			Interval overlapInterval = flsaDateInterval.overlap(timeBlockInterval);
			long overlap = (overlapInterval == null) ? 0L : overlapInterval.toDurationMillis();
			BigDecimal overlapHours = TKUtils.convertMillisToHours(overlap);
			
			if (overlapHours.compareTo(BigDecimal.ZERO) > 0) {
				
				// TODO : Looking at the overlap and bucketing isn't really 
				// accurate for non REG earn codes, since we are equally
				// applying the overlap to every earn code that exists.
				
				List<TimeHourDetail> details = block.getTimeHourDetails();
				for (TimeHourDetail thd : details) {
					BigDecimal ecHours = earnCodeToHours.containsKey(thd.getEarnCode()) ? earnCodeToHours.get(thd.getEarnCode()) : BigDecimal.ZERO;
					if (overlapHours.compareTo(ecHours) >= 0) {
						ecHours = ecHours.add(overlapHours, TkConstants.MATH_CONTEXT);
						earnCodeToHours.put(thd.getEarnCode(), ecHours);
					}
				}
								
				List<TimeBlock> blocks = earnCodeToTimeBlocks.get(block.getEarnCode());
				if (blocks == null) {
					blocks = new ArrayList<TimeBlock>();
					earnCodeToTimeBlocks.put(block.getEarnCode(), blocks);
				}
				blocks.add(block);
				appliedTimeBlocks.add(block);
			}
		}
	}

	public Map<String, BigDecimal> getEarnCodeToHours() {
		return earnCodeToHours;
	}

	public Map<String, List<TimeBlock>> getEarnCodeToTimeBlocks() {
		return earnCodeToTimeBlocks;
	}

	public void setEarnCodeToTimeBlocks(Map<String, List<TimeBlock>> earnCodeToTimeBlocks) {
		this.earnCodeToTimeBlocks = earnCodeToTimeBlocks;
	}

	public List<TimeBlock> getAppliedTimeBlocks() {
		return appliedTimeBlocks;
	}

	public void setAppliedTimeBlocks(List<TimeBlock> appliedTimeBlocks) {
		this.appliedTimeBlocks = appliedTimeBlocks;
	}
	
	
}
