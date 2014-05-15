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
import org.kuali.kpme.tklm.time.rules.shiftdifferential.ruletype.ShiftDifferentialRuleType;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.service.ShiftTypeService;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.service.ShiftTypeServiceBase;

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

    private ShiftTypeService shiftTypeService;


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
        getShiftTypeService().processShift(this);
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
        return getShiftTypeService().getNegativeAdjustmentTime(this);
    }

    public Long getFullShiftPremiumTime() {
        return getShiftTypeService().getFullShiftPremium(this);
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

    public boolean exceedsMaxGap(Interval gapInterval, BigDecimal maxGap) {
        if (gapInterval == null) {
            return false;
        }
        BigDecimal gapMinutes = TKUtils.convertMillisToMinutes(gapInterval.toDurationMillis());

        return (gapMinutes.compareTo(maxGap) > 0);
    }

    protected ShiftTypeService getShiftTypeService() {
        if (shiftTypeService == null) {
            if (rule.getRuleTypeObj() == null) {
                //fall back to base logic
                shiftTypeService = new ShiftTypeServiceBase();
            } else {
                shiftTypeService = rule.getRuleTypeObj().getShiftTypeService();
            }
        }
        return shiftTypeService;
    }
}
