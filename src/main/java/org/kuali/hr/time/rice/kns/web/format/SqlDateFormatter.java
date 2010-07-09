package org.kuali.hr.time.rice.kns.web.format;

import java.sql.Date;

import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.RiceKeyConstants;
import org.kuali.rice.kns.web.format.FormatException;
import org.kuali.rice.kns.web.format.Formatter;

public class SqlDateFormatter extends Formatter {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    static {
	registerFormatter(Date.class, SqlDateFormatter.class);
    }

    /**
     */
    @Override
    protected Object convertToObject(String source) {
	Object o = null;

	try {
	    Date date = KNSServiceLocator.getDateTimeService().convertToSqlDate(source);
	    o = date;
	} catch (Exception e) {
	    throw new FormatException("parsing", RiceKeyConstants.ERROR_DATE, source, e);
	}

	return o;
    }

    @Override
    public Object format(Object source) {
	if (source != null && source instanceof Date) {
	    Date date = (Date) source;
	    return KNSServiceLocator.getDateTimeService().toDateString(date);
	}

	return null;
    }
}
