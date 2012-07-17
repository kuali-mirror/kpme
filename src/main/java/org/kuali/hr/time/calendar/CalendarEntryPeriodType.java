package org.kuali.hr.time.calendar;


public enum CalendarEntryPeriodType {
    WEEKLY("W"),
    BI_WEEKLY("B"),
    SEMI_MONTHLY("S"),
    MONTHLY("M");

    public final String code;

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
        throw new IllegalArgumentException("Failed to locate the CalendarEntryPeriodType with the given code: " + code);
    }

}
