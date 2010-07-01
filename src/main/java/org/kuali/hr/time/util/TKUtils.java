package org.kuali.hr.time.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.kuali.rice.core.config.ConfigContext;

public class TKUtils {
        
    	public static String getTimeZone(){
    	   HttpServletRequest request = TKContext.getHttpServletRequest(); 
    	   Locale clientLocale = request.getLocale();
    	   Calendar calendar = Calendar.getInstance(clientLocale);  
    	   TimeZone clientTimeZone = calendar.getTimeZone();  
    	   
    	   return clientTimeZone.getID();
    	}
    	
	public static String getEnvironment(){
		return ConfigContext.getCurrentContextConfig().getProperty("environment");
	}
	
	
	public static void main(String[] args) {
	    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));	    
	    DateFormat df = DateFormat.getDateTimeInstance();
	    cal.set(Calendar.HOUR_OF_DAY, 01);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	  
	    df.setTimeZone(TimeZone.getTimeZone("GMT"));
	    System.out.println("GMT Time: " + df.format(cal.getTime()));
	    df.setTimeZone(TimeZone.getTimeZone("SystemV/EST5EDT"));
	    System.out.println("EST Time: " + df.format(cal.getTime()));
	    


	    //String[] tzids = TimeZone.getAvailableIDs();
	    //String[] tzids = TimeZone.getAvailableIDs(-1000*5*60*60);
	    //for (String s : tzids) {
	//	System.out.println(s);
	  //  }
	}
}
