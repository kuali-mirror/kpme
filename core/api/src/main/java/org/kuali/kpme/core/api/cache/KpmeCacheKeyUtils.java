package org.kuali.kpme.core.api.cache;


import org.joda.time.DateTime;
import org.joda.time.LocalDate;


public final class KpmeCacheKeyUtils {
    private KpmeCacheKeyUtils() {
        throw new UnsupportedOperationException();
    }

    public static String dateTimeAtStartOfDate(DateTime dateTime) {
        if (dateTime == null) {
            return LocalDate.now().toDateTimeAtStartOfDay().toString();
        }
        return dateTime.toLocalDate().toDateTimeAtStartOfDay().toString();
    }
}
