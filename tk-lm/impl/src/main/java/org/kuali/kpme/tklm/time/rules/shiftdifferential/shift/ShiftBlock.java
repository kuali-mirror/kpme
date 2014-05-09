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
package org.kuali.kpme.tklm.time.rules.shiftdifferential.shift;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.ShiftDifferentialRule;


public class ShiftBlock implements Comparable<ShiftBlock> {
    private ShiftDifferentialRule rule;
    private TimeBlock timeBlock;
    private Interval timeBlockInterval;
    private Interval shiftOverlap;
    private DateTime startTime;
    private boolean applyPremium = false;

    public ShiftBlock(TimeBlock timeBlock, ShiftDifferentialRule rule, Interval shiftInterval, DateTimeZone zone) {
        this.timeBlock = timeBlock;
        this.rule = rule;
        this.timeBlockInterval = new Interval(timeBlock.getBeginDateTime().withZone(zone), timeBlock.getEndDateTime().withZone(zone));
        this.shiftOverlap = shiftInterval.overlap(this.timeBlockInterval);
        this.startTime = timeBlock.getBeginDateTime();
    }

    public long getShiftBlockDurationMillis() {
        return shiftOverlap.toDurationMillis();
    }

    public ShiftDifferentialRule getRule() {
        return rule;
    }

    public void setRule(ShiftDifferentialRule rule) {
        this.rule = rule;
    }

    public TimeBlock getTimeBlock() {
        return timeBlock;
    }

    public void setTimeBlock(TimeBlock timeBlock) {
        this.timeBlock = timeBlock;
    }

    public Interval getTimeBlockInterval() {
        return timeBlockInterval;
    }

    public void setTimeBlockInterval(Interval timeBlockInterval) {
        this.timeBlockInterval = timeBlockInterval;
    }

    public Interval getShiftOverlap() {
        return shiftOverlap;
    }

    public void setShiftOverlap(Interval shiftOverlap) {
        this.shiftOverlap = shiftOverlap;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public String getTimeBlockId() {
        return getTimeBlock() == null ? StringUtils.EMPTY : getTimeBlock().getTkTimeBlockId();
    }

    public String getShiftBlockId() {
        return getTimeBlockId() + "|" + getShiftOverlap().toString();
    }

    public boolean isApplyPremium() {
        return applyPremium;
    }

    public void setApplyPremium(boolean applyPremium) {
        this.applyPremium = applyPremium;
    }

    @Override
    public int compareTo(ShiftBlock o) {
        if (o == null) {
            return -1;
        }
        return this.getStartTime().compareTo(o.getStartTime());
    }
}
