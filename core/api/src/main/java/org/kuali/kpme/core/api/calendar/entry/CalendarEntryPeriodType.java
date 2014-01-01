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
package org.kuali.kpme.core.api.calendar.entry;

import org.apache.log4j.Logger;


public enum CalendarEntryPeriodType {
    WEEKLY("W"),
    BI_WEEKLY("B"),
    SEMI_MONTHLY("S"),
    MONTHLY("M");

    public final String code;
    private static final Logger LOG = Logger.getLogger(CalendarEntryPeriodType.class);

    private CalendarEntryPeriodType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static CalendarEntryPeriodType fromCode(String code) {
        if (code == null) {
            return null;
        }
        for (CalendarEntryPeriodType type : values()) {
            if (type.code.equals(code)) {
                return type;
            } 
        }
        // throw new IllegalArgumentException("Failed to locate the CalendarEntryPeriodType with the given code: " + code);
        LOG.warn("Failed to locate the CalendarEntryPeriodType with the given code: " + code);
        return null;
    }

}
