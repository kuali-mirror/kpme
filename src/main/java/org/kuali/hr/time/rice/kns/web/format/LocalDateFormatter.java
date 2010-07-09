package org.kuali.hr.time.rice.kns.web.format;

import java.text.ParseException;
import java.util.Date;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.kuali.hr.time.paycalendar.PayCalendarDates;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.service.impl.XmlObjectSerializerServiceImpl;
import org.kuali.rice.kns.service.impl.XmlObjectSerializerServiceImpl.ProxyAwareJavaReflectionProvider;
import org.kuali.rice.kns.util.RiceKeyConstants;
import org.kuali.rice.kns.web.format.FormatException;
import org.kuali.rice.kns.web.format.Formatter;

import com.thoughtworks.xstream.XStream;

public class LocalDateFormatter extends Formatter {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    static {
	registerFormatter(LocalDate.class, LocalDateFormatter.class);
    }

    /**
     * TODO: We need to generate our own time formatting service, so we do not want the user to have to input
     * a full date to get a single time.
     */
    @Override
    protected Object convertToObject(String source) {
	Object o = null;

	try {
	    Date javaUtilDate = KNSServiceLocator.getDateTimeService().convertToDateTime(source);
	    o = LocalDate.fromDateFields(javaUtilDate);
	} catch (ParseException pe) {
	    throw new FormatException("parsing", RiceKeyConstants.ERROR_DATE, source, pe);
	}

	return o;
    }

    /**
     * TODO: We need to generate our own time formatting service, so we do not want the user to have to input
     * a full date to get a single time.
     */
    @Override
    public Object format(Object source) {
	if (source != null && source instanceof LocalDate) {
	    LocalDate ld = (LocalDate)source;
	    Date javaUtilDate = ld.toDateTimeAtStartOfDay().toDate();
	    return KNSServiceLocator.getDateTimeService().toDateString(javaUtilDate);    
	}
	
	return null;
    }
    
    
    public static void main(String[] args) {
	LocalTime time1 = new LocalTime(3,55,0);
	LocalDate date1 = new LocalDate(2010,6,20);
	
	XStream xs = new XStream();
	String t_xml1 = xs.toXML(time1);
	String d_xml1 = xs.toXML(date1);
	
	//System.out.println(t_xml1);
	//System.out.println(d_xml1);
	
	LocalTime time2 = (LocalTime) xs.fromXML(t_xml1);
	LocalDate date2 = (LocalDate) xs.fromXML(d_xml1);
	
	System.out.println("t1: " + time1 + " t2: " + time2);
	System.out.println("d1: " + date1 + " d2: " + date2);
	
	
	Date javaUtilDate = new Date();
	LocalDate date3 = LocalDate.fromDateFields(javaUtilDate);
	
	LocalDate date4 = (LocalDate) xs.fromXML(xs.toXML(date3));
	
	System.out.println("d3: " + date3 + " d4: " + date4);
	
	PayCalendarDates pcd = new PayCalendarDates();
//	pcd.setEndPeriodDate(new LocalDate());
//	pcd.setBeginPeriodDate(new LocalDate());
//	pcd.setBeginPeriodTime(new LocalTime());
//	pcd.setEndPeriodTime(new LocalTime());
	
	String pcd_string = xs.toXML(pcd);
	System.out.println(pcd_string);
	PayCalendarDates pcd_new = (PayCalendarDates)xs.fromXML(pcd_string);
	String pcd_string2 = xs.toXML(pcd_new);
	System.out.println("\n\n");
	System.out.println(pcd_string2);
    }
}
