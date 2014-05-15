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
package org.kuali.kpme.tklm.time.rules.shiftdifferential.service;

import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.shift.Shift;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.shift.ShiftCalendarInterval;

import java.util.List;

/**
 * Created by jjhanso on 4/23/14.
 */
public interface ShiftTypeService {
    /**
     * Process all blocks on a shift to determine and mark those that should be applied to a shift amount
     *
     * <p>
     * This will include all groups directly assigned as well as those inferred
     * by the fact that they are members of higher level groups.
     * </p>
     *
     * @param shift The Shift object to be processed
     * @return returns a modified Shift object that has shift blocks marked to be applied, and total hours and gaps computed.
     */
    Shift processShift(Shift shift);

    /**
     * Returns the full shift premium amount in milliseconds (can be converted to Hours with TKUtils.convertMillisToHours(longValue))
     *
     * @param shift The Shift object to be processed
     * @return returns amount of time the full shift will be applying.  If this doesn't exceed the min hours, will return 0L
     */
    Long getFullShiftPremium(Shift shift);


    /**
     * Returns the amount of time that apply a negative value to the shift (ie Lunch)
     *
     *
     * @param shift The Shift object to be processed
     * @return returns amount of time that
     */
    Long getNegativeAdjustmentTime(Shift shift);

    void placeTimeBlocks(ShiftCalendarInterval shiftCalendarInterval, List<TimeBlock> timeBlocks);
}
