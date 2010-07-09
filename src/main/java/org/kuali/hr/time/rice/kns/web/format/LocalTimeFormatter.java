package org.kuali.hr.time.rice.kns.web.format;

import java.util.Locale;

import org.joda.time.LocalTime;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kuali.rice.kns.util.RiceKeyConstants;
import org.kuali.rice.kns.web.format.FormatException;
import org.kuali.rice.kns.web.format.Formatter;

public class LocalTimeFormatter extends Formatter {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter timeFormat = DateTimeFormat.forPattern("hh:mm aa").withLocale(Locale.US).withChronology(ISOChronology.getInstanceUTC());

    static {
	registerFormatter(LocalTime.class, LocalTimeFormatter.class);
    }

    /**
     * TODO: We need to generate our own time formatting service, so we do not want the user to have to input
     * a full date to get a single time.
     */
    @Override
    protected Object convertToObject(String source) {
	Object o = null;

	try {
	    o = timeFormat.parseDateTime(source).toLocalTime();
	} catch (Exception e) {
	    throw new FormatException("parsing", RiceKeyConstants.ERROR_DATE, source, e);
	}

	return o;
    }

    @Override
    public Object format(Object source) {
	if (source != null && source instanceof LocalTime) {
	    LocalTime lt = (LocalTime)source;
	    return timeFormat.print(lt);
	}
	
	return null;
    }
    
    public static void main(String args[]) {
	LocalTimeFormatter ltf = new LocalTimeFormatter();
	System.out.println(ltf.format(new LocalTime(00,55,00)));
	
	LocalTime lt = (LocalTime)ltf.convertToObject("03:55 pm");
	System.out.println(lt);
	System.out.println(ltf.format(lt));
    }
}
