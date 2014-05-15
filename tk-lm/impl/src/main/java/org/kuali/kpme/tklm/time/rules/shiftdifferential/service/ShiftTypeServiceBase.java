package org.kuali.kpme.tklm.time.rules.shiftdifferential.service;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.api.time.timehourdetail.TimeHourDetail;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.ShiftDifferentialRule;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.shift.Shift;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.shift.ShiftBlock;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.shift.ShiftCalendarInterval;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by jjhanso on 5/13/14.
 */
public class ShiftTypeServiceBase implements ShiftTypeService {
    @Override
    public Shift processShift(Shift shift) {
        long duration = 0L;
        ShiftDifferentialRule rule = shift.getRule();
        Map<String, Interval> intervalMap = new HashMap<>();
        List<ShiftBlock> shiftBlockList = new ArrayList<>(shift.getShiftBlocks());
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
                            || !shift.exceedsMaxGap(currentGapInterval, rule.getMaxGap())) {
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
                        //add if either block exceeds minimum
                        if (sb1.exceedsMinHours()) {
                            duration += sb1.getShiftBlockDurationMillis();
                            idsAddedToDuration.add(sb1.getShiftBlockId());
                            sb1.setApplyPremium(Boolean.TRUE);
                        }
                        if (sb2.exceedsMinHours()) {
                            duration += sb2.getShiftBlockDurationMillis();
                            idsAddedToDuration.add(sb2.getShiftBlockId());
                            sb2.setApplyPremium(Boolean.TRUE);
                        }

                    }
                }
            } else {
                ShiftBlock singleShiftBlock = shiftBlockList.get(0);
                duration = singleShiftBlock.getShiftBlockDurationMillis();
                singleShiftBlock.setApplyPremium(Boolean.TRUE);
            }
        }


        Long negativeAdjustment = shift.getNegativeAdjustmentTime();
        shift.setTotalShiftTime(duration - negativeAdjustment);
        if (shift.getFullShiftPremiumTime() <= 0) {
            //didn't hit minimum hours, make sure apply premium bools are all false
            for (ShiftBlock sb : shift.getShiftBlocks()) {
                sb.setApplyPremium(Boolean.FALSE);
            }
        }
        shift.setPreviousGapIntervals(intervalMap);
        return shift;
    }

    @Override
    public Long getFullShiftPremium(Shift shift) {
        //long shiftDuration = getTotalShiftTime();
        if (shift.exceedsMinHours()) {
            return shift.getTotalShiftTime();
        }
        return 0L;
    }

    @Override
    public Long getNegativeAdjustmentTime(Shift shift) {
        BigDecimal sum = BigDecimal.ZERO;
        for (ShiftBlock sb : shift.getShiftBlocks()) {
            for (TimeHourDetail detail : sb.getTimeBlock().getTimeHourDetails()) {
                // TODO: Should get a list of Earn codes for this from a new system parameter
                if (detail.getEarnCode().equals(HrConstants.LUNCH_EARN_CODE)) {
                    sum = sum.add(detail.getHours());
                }
            }
        }

        return TKUtils.convertHoursToMillis(sum);
    }

    @Override
    public void placeTimeBlocks(ShiftCalendarInterval shiftCalendarInterval, List<TimeBlock> timeBlocks) {
        if (shiftCalendarInterval == null) {
            return;
        }
        if (CollectionUtils.isEmpty(shiftCalendarInterval.getJobNumbers())
                || CollectionUtils.isEmpty(timeBlocks)) {
            return;
        }
        for (TimeBlock tb : timeBlocks) {
            if (shiftCalendarInterval.getJobNumbers().contains(tb.getJobNumber())) {
                int counter = 0;  // only possible for timeblock to overlap two shifts
                Interval tbInterval = new Interval(tb.getBeginDateTime(), tb.getEndDateTime());
                for (Shift shift : shiftCalendarInterval.getShifts()) {
                    if (shift.getShiftInterval().overlaps(tbInterval)) {
                        shift.addShiftBlock(tb);
                        counter++;
                    }
                    if (counter >= 2) {
                        break;
                    }
                }
            }
        }
    }
}
