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
package org.kuali.kpme.tklm.time.flsa;

import org.apache.commons.lang.StringUtils;
import org.joda.time.*;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.api.time.flsa.FlsaDayContract;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.api.time.timehourdetail.TimeHourDetail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlsaDay implements FlsaDayContract {

	private Map<String,List<TimeBlock>> earnCodeToTimeBlocks = new HashMap<String,List<TimeBlock>>();
	private List<TimeBlock> appliedTimeBlocks = new ArrayList<TimeBlock>();
	
	private Map<String,List<LeaveBlock>> earnCodeToLeaveBlocks = new HashMap<String,List<LeaveBlock>>();
	private List<LeaveBlock> appliedLeaveBlocks = new ArrayList<LeaveBlock>();

	Interval flsaDateInterval;
	LocalDateTime flsaDate;
    DateTimeZone timeZone;

    /**
     *
     * @param flsaDate A LocalDateTime because we want to be conscious of the
     * relative nature of this flsa/window
     * @param timeBlocks
     * @param timeZone The timezone we are constructing, relative.
     */
	public FlsaDay(LocalDateTime flsaDate, List<TimeBlock> timeBlocks, List<LeaveBlock> leaveBlocks, DateTimeZone timeZone) {
		this.flsaDate = flsaDate;
        this.timeZone = timeZone;
		flsaDateInterval = new Interval(flsaDate.toDateTime(timeZone), flsaDate.toDateTime(timeZone).plusDays(1));
		this.setTimeBlocks(timeBlocks);
		this.setLeaveBlocks(leaveBlocks);
	}

	/**
	 * Handles the breaking apart of existing time blocks around FLSA boundaries.
	 *
	 * This method will compare the FLSA interval against the timeblock interval
	 * to determine how many hours overlap.  It will then examine the time hour
	 * details
	 *
	 * @param timeBlocks a sorted list of time blocks.
	 */
	public void setTimeBlocks(List<TimeBlock> timeBlocks) {
		for (TimeBlock block : timeBlocks) {
            applyBlock(block, this.appliedTimeBlocks);
        }
	}
	
	/**
	 * Handles the breaking apart of existing leave blocks around FLSA boundaries.
	 *
	 * This method will compare the FLSA interval against the leaveblock interval
	 * to determine how many hours overlap.  It will then examine the leave hour
	 * details
	 *
	 * @param leaveBlocks a sorted list of time blocks.
	 */
	public void setLeaveBlocks(List<LeaveBlock> leaveBlocks) {
		for (LeaveBlock block : leaveBlocks) {
            applyBlock(block, this.appliedLeaveBlocks);
        }
	}

	/**
	 * This method will compute the mappings present for this object:
	 *
	 * earnCodeToTimeBlocks
	 * earnCodeToHours
	 *
	 */
	public void remapTimeHourDetails() {
		List<TimeBlock> reApplied = new ArrayList<TimeBlock>(appliedTimeBlocks.size());
		earnCodeToTimeBlocks.clear();
		for (TimeBlock block : appliedTimeBlocks) {
			applyBlock(block, reApplied);
		}
	}
	
	/**
	 * This method will compute the mappings present for this object:
	 *
	 * earnCodeToTimeBlocks
	 * earnCodeToHours
	 *
	 */
	public void remapLeaveHourDetails() {
		List<LeaveBlock> reApplied = new ArrayList<LeaveBlock>(appliedLeaveBlocks.size());
		earnCodeToLeaveBlocks.clear();
		for (LeaveBlock block : appliedLeaveBlocks) {
			applyBlock(block, reApplied);
		}
	}

	/**
     * This method determines if the provided TimeBlock is applicable to this
     * FLSA day, and if so will add it to the applyList. It could be the case
     * that a TimeBlock is on the boundary of the FLSA day so that only a
     * partial amount of the hours for that TimeBlock will count towards this
     * day.
     *
     * |---------+------------------+---------|
     * | Day 1   | Day 1/2 Boundary | Day 2   |
     * |---------+------------------+---------|
     * | Block 1 |             | Block 2      |
     * |---------+------------------+---------|
     *
     * The not so obvious ascii diagram above is intended to illustrate the case
     * where on day one you have 1 fully overlapping time block (block1) and one
     * partially overlapping time block (block2). Block 2 belongs to both FLSA
     * Day 1 and Day 2.
     *
	 * @param block A time block that we want to check and apply to this day.
	 * @param applyList A list of time blocks we want to add applicable time blocks to.
	 *
	 * @return True if the block is applicable, false otherwise.  The return
	 * value can be used as a quick exit for the setTimeBlocks() method.
	 *
	 * TODO : Bucketing of partial FLSA days is still suspect, however real life examples of this are likely non-existent to rare.
     *
     * Danger may still lurk in day-boundary overlapping time blocks that have multiple Time Hour Detail entries.
	 */
	private boolean applyBlock(TimeBlock block, List<TimeBlock> applyList) {
		DateTime beginDateTime = null;
		DateTime endDateTime = null;
		boolean zeroHoursTimeBlock = false;
		boolean addLeaveBlock = false;
		boolean isLeaveBlock = false;
		if(block.getLeaveDateTime() == null) {
			beginDateTime = block.getBeginTimeDisplay();
			endDateTime = block.getEndTimeDisplay();
			if(flsaDateInterval.contains(beginDateTime)){
				if(flsaDateInterval.contains(endDateTime) || endDateTime.compareTo(flsaDateInterval.getEnd()) == 0){
					zeroHoursTimeBlock = true;
				}
			}
		} else {
			isLeaveBlock = true;
			beginDateTime = new DateTime(block.getLeaveDateTime(), this.timeZone);
			endDateTime = new DateTime(block.getLeaveDateTime(), this.timeZone);
			DateTime localTime = flsaDateInterval.getStart().toLocalDateTime().toDateTime();
			String intervalStartDateString = localTime.toLocalDate().toString();
			if(flsaDateInterval.getEnd().getHourOfDay() == 0) {
				String lbDateString = block.getLeaveDateTime().toLocalDate().toString();
				if(intervalStartDateString.equals(lbDateString)) {
					addLeaveBlock = true;
				}
			} else {
                LocalDate localDate = block.getLeaveDateTime().toLocalDate();
                LocalDate dayIntBegin = flsaDateInterval.getStart().toLocalDate();
                if(localDate.equals(dayIntBegin)){
					addLeaveBlock = true;
                }
			}
		}
			
		if (beginDateTime.isAfter(flsaDateInterval.getEnd()))
			return false;
		
		Interval timeBlockInterval = null;

		//Requested to have zero hour time blocks be able to be added to the GUI
		if(endDateTime.getMillis() > beginDateTime.getMillis()){
			timeBlockInterval = new Interval(beginDateTime,endDateTime);
		}
		
		Interval overlapInterval = flsaDateInterval.overlap(timeBlockInterval);
		long overlap = (overlapInterval == null) ? 0L : overlapInterval.toDurationMillis();
		BigDecimal overlapHours = TKUtils.convertMillisToHours(overlap);
		if((overlapHours.compareTo(BigDecimal.ZERO) == 0) && flsaDateInterval.contains(beginDateTime) && flsaDateInterval.contains(endDateTime)){
			if(block.getHours().compareTo(BigDecimal.ZERO) > 0){
				overlapHours = block.getHours();
			}
		}

        // Local lookup for this time-block to ensure we are not over applicable hours.
        // You will notice below we are earn codes globally per day, and also locally per timeblock.
        // The local per-time block mapping is used only to verify that we have not gone over allocated overlap time
        // for the individual time block.
        Map<String,BigDecimal> localEarnCodeToHours = new HashMap<String,BigDecimal>();

        if(!isLeaveBlock) {
    		if (zeroHoursTimeBlock || overlapHours.compareTo(BigDecimal.ZERO) > 0 || (flsaDateInterval.contains(beginDateTime) && StringUtils.equals(block.getEarnCodeType(),HrConstants.EARN_CODE_AMOUNT)))  {

                List<TimeHourDetail> details = block.getTimeHourDetails();
                for (TimeHourDetail thd : details) {
                    BigDecimal localEcHours = localEarnCodeToHours.containsKey(thd.getEarnCode()) ? localEarnCodeToHours.get(thd.getEarnCode()) : BigDecimal.ZERO;
                    //NOTE adding this in the last few hours before release.. remove if side effects are noticed
                    if (overlapHours.compareTo(localEcHours) >= 0 || thd.getAmount().compareTo(BigDecimal.ZERO) == 0) {
                        localEcHours = localEcHours.add(thd.getHours(), HrConstants.MATH_CONTEXT);
                        localEarnCodeToHours.put(thd.getEarnCode(), localEcHours);
                    }
                }

    			List<TimeBlock> blocks = earnCodeToTimeBlocks.get(block.getEarnCode());
    			if (blocks == null) {
    				blocks = new ArrayList<TimeBlock>();
    				earnCodeToTimeBlocks.put(block.getEarnCode(), blocks);
    			}
    			blocks.add(block);
    			applyList.add(block);
    		}

        } else {
        	if(addLeaveBlock) {
        		List<TimeBlock> blocks = earnCodeToTimeBlocks.get(block.getEarnCode());
    			if (blocks == null) {
    				blocks = new ArrayList<TimeBlock>();
    				earnCodeToTimeBlocks.put(block.getEarnCode(), blocks);
    			}
    			blocks.add(block);
    			applyList.add(block);
        	}
        }
        
		return true;
	}
	
	/**
     * This method determines if the provided LeaveBlock is applicable to this
     * FLSA day, and if so will add it to the applyList. It could be the case
     * that a LeaveBlock is on the boundary of the FLSA day so that only a
     * partial amount of the hours for that LeaveBlock will count towards this
     * day.
     *
     * |---------+------------------+---------|
     * | Day 1   | Day 1/2 Boundary | Day 2   |
     * |---------+------------------+---------|
     * | Block 1 |             | Block 2      |
     * |---------+------------------+---------|
     *
     * The not so obvious ascii diagram above is intended to illustrate the case
     * where on day one you have 1 fully overlapping leave block (block1) and one
     * partially overlapping leave block (block2). Block 2 belongs to both FLSA
     * Day 1 and Day 2.
     *
	 * @param block A leave block that we want to check and apply to this day.
	 * @param applyList A list of leave blocks we want to add applicable leave blocks to.
	 *
	 * @return True if the block is applicable, false otherwise.  The return
	 * value can be used as a quick exit for the setLeaveBlocks() method.
	 *
	 * TODO : Bucketing of partial FLSA days is still suspect, however real life examples of this are likely non-existent to rare.
	 */
	private boolean applyBlock(LeaveBlock block, List<LeaveBlock> applyList) {
		DateTime beginDateTime = new DateTime(block.getLeaveDateTime().toDate(), this.timeZone);
		DateTime endDateTime = new DateTime(block.getLeaveDateTime().toDate(), this.timeZone);

		if (beginDateTime.isAfter(flsaDateInterval.getEnd())) {
			return false;
        }

		Interval leaveBlockInterval = null;
		if(endDateTime.getMillis() > beginDateTime.getMillis()){
			leaveBlockInterval = new Interval(beginDateTime,endDateTime);
		}

		Interval overlapInterval = flsaDateInterval.overlap(leaveBlockInterval);
		long overlap = (overlapInterval == null) ? 0L : overlapInterval.toDurationMillis();
		BigDecimal overlapHours = TKUtils.convertMillisToHours(overlap);
		if((overlapHours.compareTo(BigDecimal.ZERO) == 0) && flsaDateInterval.contains(beginDateTime) && flsaDateInterval.contains(endDateTime)){
			if(block.getLeaveAmount().negate().compareTo(BigDecimal.ZERO) > 0){
				overlapHours = block.getLeaveAmount().negate();
			}
		}
		
		if (overlapHours.compareTo(BigDecimal.ZERO) > 0)  {
			List<LeaveBlock> blocks = earnCodeToLeaveBlocks.get(block.getEarnCode());
			if (blocks == null) {
				blocks = new ArrayList<LeaveBlock>();
				earnCodeToLeaveBlocks.put(block.getEarnCode(), blocks);
			}
			blocks.add(block);
			applyList.add(block);
		}

		return true;
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

	public Map<String, List<LeaveBlock>> getEarnCodeToLeaveBlocks() {
		return earnCodeToLeaveBlocks;
	}

	public void setEarnCodeToLeaveBlocks(Map<String, List<LeaveBlock>> earnCodeToLeaveBlocks) {
		this.earnCodeToLeaveBlocks = earnCodeToLeaveBlocks;
	}

	public List<LeaveBlock> getAppliedLeaveBlocks() {
		return appliedLeaveBlocks;
	}

	public void setAppliedLeaveBlocks(List<LeaveBlock> appliedLeaveBlocks) {
		this.appliedLeaveBlocks = appliedLeaveBlocks;
	}

	public LocalDateTime getFlsaDate() {
		return flsaDate;
	}

	public void setFlsaDate(LocalDateTime flsaDate) {
		this.flsaDate = flsaDate;
	}

}