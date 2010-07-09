package org.kuali.hr.time.rice.kns.web.format;

import java.sql.Time;
import java.text.SimpleDateFormat;

import org.kuali.rice.kns.util.RiceKeyConstants;
import org.kuali.rice.kns.web.format.FormatException;
import org.kuali.rice.kns.web.format.Formatter;

public class SqlTimeFormatter extends Formatter {

    /**
    * 
    */
    private static final long serialVersionUID = 1L;
    private static final SimpleDateFormat sdFormat = new SimpleDateFormat("hh:mm aa");

    static {
	registerFormatter(Time.class, SqlTimeFormatter.class);
    }

    /**
    */
    @Override
    protected Object convertToObject(String source) {
	Object o = null;

	try {
	    o = new Time(sdFormat.parse(source).getTime());
	} catch (Exception e) {
	    throw new FormatException("parsing", RiceKeyConstants.ERROR_DATE, source, e);
	}

	return o;
    }

    @Override
    public Object format(Object source) {
	if (source != null && source instanceof Time) {
	    Time time = (Time) source;
	    return sdFormat.format(time);
	}

	return null;
    }

    public static void main(String args[]) {
    }
}
