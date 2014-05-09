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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlockContract;
import org.kuali.kpme.tklm.api.time.timehourdetail.TimeHourDetail;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.ShiftDifferentialRule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


public class Shift {
    private Interval shiftInterval;
    private ShiftDifferentialRule rule;
    private Set<ShiftBlock> shiftBlocks = new TreeSet<>();
    private Map<String, Interval> previousGapIntervals = new HashMap<>();
    private Long totalShiftTime;
    private DateTimeZone zone;


    public Shift(ShiftDifferentialRule rule, Interval shiftInterval, DateTimeZone zone) {
        this.shiftInterval = shiftInterval;
        this.rule = rule;
        this.zone = zone;
    }

    public Interval getShiftInterval() {
        return shiftInterval;
    }

    public void setShiftInterval(Interval shiftInterval) {
        this.shiftInterval = shiftInterval;
    }

    public ShiftDifferentialRule getRule() {
        return rule;
    }

    public void setRule(ShiftDifferentialRule rule) {
        this.rule = rule;
    }

    public Set<ShiftBlock> getShiftBlocks() {
        return shiftBlocks;
    }

    public void setShiftBlocks(Set<ShiftBlock> shiftBlocks) {
        this.shiftBlocks = shiftBlocks;
    }

    public void addShiftBlock(TimeBlock timeBlock) {
        ShiftBlock sb = new ShiftBlock(timeBlock, rule, shiftInterval, zone);
        getShiftBlocks().add(sb);
    }

    public DateTimeZone getZone() {
        return zone;
    }

    public void setZone(DateTimeZone zone) {
        this.zone = zone;
    }

    public void processShift() {
        long duration = 0L;
        Map<String, Interval> intervalMap = new HashMap<>();
        List<ShiftBlock> shiftBlockList = new ArrayList<>(getShiftBlocks());
        List<String> idsAddedToDuration = new ArrayList<String>();
        if (CollectionUtils.isNotEmpty(shiftBlockList)) {
            int size = shiftBlockList.size();
            if (size > 1) {
                for (int i = 1; i < size; i++) {
                    ShiftBlock sb1 = shiftBlockList.get(i - 1);
                    ShiftBlock sb2 = shiftBlockList.get(i);
                    Interval currentGapInterval = new Interval(sb1.getShiftOverlap().getEnd(), sb2.getShiftOverlap().getStart());
                    intervalMap.put(sb2.getTimeBlockId(), currentGapInterval);
                    if (rule.getMaxGap().compareTo(BigDecimal.ZERO) == 0
                            || !exceedsMaxGap(currentGapInterval, rule.getMaxGap())) {
                        if (!idsAddedToDuration.contains(sb1.getShiftBlockId())) {
                            duration += sb1.getShiftBlockDurationMillis();
                            idsAddedToDuration.add(sb1.getShiftBlockId());
                            sb1.setApplyPremium(Boolean.TRUE);
                        }
                        if (!idsAddedToDuration.contains(sb2.getShiftBlockId())) {
                            duration += sb2.getShiftBlockDurationMillis();
                            idsAddedToDuration.add(sb2.getShiftBlockId());
                            sb2.setApplyPremium(Boolean.TRUE);
                        }
                    } else {
                        //time blocks exceed max gap
                    }
                }
            } else {
                ShiftBlock singleShiftBlock = shiftBlockList.get(0);
                duration = singleShiftBlock.getShiftBlockDurationMillis();
                singleShiftBlock.setApplyPremium(Boolean.TRUE);
            }
        }


        Long negativeAdjustment = getNegativeAdjustmentTime();
        setTotalShiftTime(duration - negativeAdjustment);
        if (getFullShiftPremiumTime() <= 0) {
            //didn't hit minimum hours, make sure apply premium bools are all false
            for (ShiftBlock sb : getShiftBlocks()) {
                sb.setApplyPremium(Boolean.FALSE);
            }
        }
        setPreviousGapIntervals(intervalMap);

    }

    public Long getTotalShiftTime() {
        if (this.totalShiftTime == null) {
            processShift();
        }
        return totalShiftTime;
    }

    public boolean exceedsMinHours() {
        Long shiftDuration = getTotalShiftTime();
        return rule.getMinHours().compareTo(TKUtils.convertMillisToHours(shiftDuration)) <= 0;
    }

    public Long getNegativeAdjustmentTime() {
        BigDecimal sum = BigDecimal.ZERO;
        for (ShiftBlock sb : getShiftBlocks()) {
            for (TimeHourDetail detail : sb.getTimeBlock().getTimeHourDetails()) {
                // TODO: Should get a list of Earn codes for this from a new system parameter
                if (detail.getEarnCode().equals(HrConstants.LUNCH_EARN_CODE)) {
                    sum = sum.add(detail.getHours());
                }
            }
        }

        return TKUtils.convertHoursToMillis(sum);
    }

    public Long getFullShiftPremiumTime() {
        //long shiftDuration = getTotalShiftTime();
        if (exceedsMinHours()) {
            return getTotalShiftTime();
        }
        return 0L;
    }

    public Map<String, Interval> getPreviousGapIntervals() {
        if (MapUtils.isEmpty(this.previousGapIntervals)) {
            processShift();
        }
        return this.previousGapIntervals;
    }


    public void setPreviousGapIntervals(Map<String, Interval> previousGapIntervals) {
        this.previousGapIntervals = previousGapIntervals;
    }

    public void setTotalShiftTime(long totalShiftTime) {
        this.totalShiftTime = totalShiftTime;
    }

    protected boolean exceedsMaxGap(Interval gapInterval, BigDecimal maxGap) {
        if (gapInterval == null) {
            return false;
        }
        BigDecimal gapMinutes = TKUtils.convertMillisToMinutes(gapInterval.toDurationMillis());

        return (gapMinutes.compareTo(maxGap) > 0);
    }
}
