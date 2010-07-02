package org.kuali.hr.time.ojb;

import java.sql.Time;
import java.util.Date;

import org.apache.ojb.broker.accesslayer.conversions.ConversionException;
import org.apache.ojb.broker.accesslayer.conversions.FieldConversion;
import org.joda.time.LocalTime;

public class DateToLocalTimeFieldConversion implements FieldConversion {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Convert from org.joda.time.LocalTime to java.util.Date and then to java.sql.Time.
     */
    @Override
    public Object javaToSql(Object jodaLocalTime) throws ConversionException {
	Object ret = jodaLocalTime; // return source if the object type does not match expected class instance value

	if (jodaLocalTime instanceof LocalTime) {
	    LocalTime time = (LocalTime) jodaLocalTime;
	    Date javaDate = time.toDateTimeToday().toDate();
	    Time javaTime = new Time(javaDate.getTime());
	    ret = javaTime;
	}

	return ret;
    }

    /**
     * Convert from a java.sql.Time to org.joda.time.LocalTime.
     */
    @Override
    public Object sqlToJava(Object javaSqlTime) throws ConversionException {
	Object ret = javaSqlTime;

	if (javaSqlTime instanceof Date) {
	    Date javaDate = (Date) javaSqlTime;
	    ret = LocalTime.fromDateFields(javaDate);
	}

	return ret;
    }
}
