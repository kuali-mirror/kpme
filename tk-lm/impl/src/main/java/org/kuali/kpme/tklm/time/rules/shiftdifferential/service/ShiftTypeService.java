package org.kuali.kpme.tklm.time.rules.shiftdifferential.service;

import org.joda.time.Interval;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.ShiftDifferentialRule;

import java.util.List;

/**
 * Created by jjhanso on 4/23/14.
 */
public interface ShiftTypeService {
    public List<List<TimeBlock>> processShiftForEligibleBlocks(ShiftDifferentialRule rule, List<TimeBlock> timeBlocks);

    //public List<TimeBlock> applyShiftPremium(Interval evaluationInterval, )
}
