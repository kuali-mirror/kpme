package org.kuali.hr.time.ojb;

import java.sql.Timestamp;

import org.apache.ojb.broker.accesslayer.conversions.ConversionException;
import org.apache.ojb.broker.accesslayer.conversions.FieldConversion;
import org.joda.time.DateTime;

public class DateTimeToTimestampFieldConversion implements FieldConversion {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public Object javaToSql(Object source) throws ConversionException {
	Object ret = source; // return source if the object type does not match expected class instance value

	if (source instanceof DateTime) {
	    DateTime dateTime = (DateTime)source;
	    Timestamp javaTimestamp = new Timestamp(dateTime.getMillis());
	    
	    ret = javaTimestamp;
	}

	return ret;
    }

    @Override
    public Object sqlToJava(Object source) throws ConversionException {
	Object ret = source;

	if (source instanceof Timestamp) {
	    Timestamp timestamp = (Timestamp)source;
	    ret = new DateTime(timestamp.getTime());
	}

	return ret;
    }

}
