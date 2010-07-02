package org.kuali.hr.time.ojb;

import java.util.Date;

import org.apache.ojb.broker.accesslayer.conversions.ConversionException;
import org.apache.ojb.broker.accesslayer.conversions.FieldConversion;
import org.joda.time.LocalDate;

public class DateToLocalDateFieldConversion implements FieldConversion {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public Object javaToSql(Object source) throws ConversionException {
	Object ret = source; // return source if the object type does not match expected class instance value
	
	if (source instanceof LocalDate) {
	    LocalDate date = (LocalDate)source;
	    Date javaDate  = date.toDateTimeAtStartOfDay().toDate();
	    ret = javaDate;
	}
	
	return ret;
    }

    @Override
    public Object sqlToJava(Object source) throws ConversionException {
	Object ret = source;
	
	if (source instanceof Date) {
	    Date dbDate = (Date)source;
	    ret = LocalDate.fromDateFields(dbDate);
	}
	
	return ret;
    }

}
