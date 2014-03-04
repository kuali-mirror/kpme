package org.kuali.kpme.core.api.util.jaxb;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Calendar;

public class LocalTimeAdapter extends XmlAdapter<Calendar, LocalTime> {

    @Override
    public Calendar marshal(LocalTime localTime) throws Exception {
        if (localTime == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(localTime.getMillisOfDay());
        return calendar;
    }

    @Override
    public LocalTime unmarshal(Calendar calendar) throws Exception {
        return calendar == null ? null : new LocalTime(calendar.getTimeInMillis());
    }
}
