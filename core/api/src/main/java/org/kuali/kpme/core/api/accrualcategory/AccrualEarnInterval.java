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
