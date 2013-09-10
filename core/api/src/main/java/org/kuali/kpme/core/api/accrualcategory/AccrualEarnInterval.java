/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.kpme.core.api.accrualcategory;

import org.apache.log4j.Logger;


public enum AccrualEarnInterval {
   DAILY("D", "Daily"),
   WEEKLY("W", "Weekly"),
   SEMI_MONTHLY("S", "Semi-Monthly"),
   MONTHLY("M", "Monthly"),
   YEARLY("Y", "Yearly"),
   PAY_CAL("P", "Pay Calendar"),
   NO_ACCRUAL("N", "No Accrual");



    public final String code;
    public final String description;
    private static final Logger LOG = Logger.getLogger(AccrualEarnInterval.class);

    private AccrualEarnInterval(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public static AccrualEarnInterval fromCode(String code) {
        if (code == null) {
            return null;
        }
        for (AccrualEarnInterval type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        // throw new IllegalArgumentException("Failed to locate the CalendarEntryPeriodType with the given code: " + code);
        LOG.warn("Failed to locate the AccrualEarnInterval with the given code: " + code);
        return null;
    }

}
