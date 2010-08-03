package org.kuali.hr.time.util;

import java.math.MathContext;
import java.math.RoundingMode;
import java.util.TimeZone;

public class TkConstants {

    public static final String CLOCK_IN = "CI";
    public static final String CLOCK_OUT = "CO";
    public static final String LUNCH_IN = "LI";
    public static final String LUNCH_OUT = "LO";
    public static final String BREAK_IN = "BI";
    public static final String BREAK_OUT = "BO";

    public static final String   GMT_TIME_ZONE_ID = "Etc/GMT";
    public static final TimeZone GMT_TIME_ZONE    = TimeZone.getTimeZone(GMT_TIME_ZONE_ID);
    public static final MathContext MATH_CONTEXT = new MathContext(5,RoundingMode.HALF_EVEN);
}
