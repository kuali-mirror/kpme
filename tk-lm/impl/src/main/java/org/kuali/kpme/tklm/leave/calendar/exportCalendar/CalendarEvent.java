/**
 * Copyright 2004-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.tklm.leave.calendar.exportCalendar;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.kuali.kpme.core.service.HrServiceLocator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class CalendarEvent {
    
    
    private static final String PRODID  = "\nPRODID:-//KUALI KPME//Version 2.1//EN";
    private static final String VERSION = "\nVERSION:2.0";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
    private DateTime dateTime = new DateTime();
	private DateTimeZone dtz = DateTimeZone.forID(HrServiceLocator.getTimezoneService().getTargetUserTimezone());
	public  TimeZone localeTZ = dtz.toTimeZone();
	private String DSTname;
	private String STname;
	private String UTCoffset;
	private String sTime;
    private String date_start;
    private String date_end;
    private String description;
    private String title;
    private String timestamp;
	private String filename;
        
    public String calendarHeader(){
    	setTimezone();
    	String headerContent = 
    			"BEGIN:VCALENDAR" + 
    			"\nMETHOD:PUBLISH" +
                VERSION + 
                "\nX-WR-CALNAME:" + filename +
                PRODID + 
                "\nX-WR-TIMEZONE:" + dtz +
                "\nCALSCALE:GREGORIAN" +
                  
                  "\nBEGIN:VTIMEZONE" + 
                  	"\nTZID:" + 
                  		"\nBEGIN:STANDARD" + 
                  			"\nDTSTART:19981025T020000" +
                  			"\nRDATE:19981025T020000" +
                  			"\nTZOFFSETFROM:" + UTCoffset +
                  			"\nTZOFFSETTO:"  + sTime + 
                  			"\nTZNAME:" + STname +
                  		"\nEND:STANDARD" +
                 
				  		"\nBEGIN:DAYLIGHT" + 
				  			"\nDTSTART:19990404T020000" +
				  			"\nRDATE:19990404T020000" +
				  			"\nTZOFFSETFROM:" + UTCoffset +   
				  			"\nTZOFFSETTO:" + sTime +
				  			"\nTZNAME:" + DSTname + 
				  		"\nEND:DAYLIGHT" 
				  
				  + "\nEND:VTIMEZONE";
    	
    	return headerContent;
    }
    
    public String createEvent(String calTitle, DateTime dStart, DateTime dEnd, String tStart,
            String tEnd, String desc, String uid){
    			
    			System.out.println(dStart.toString());
    			System.out.println(tStart);
    			System.out.println(dEnd.toString());
    			System.out.println(tEnd);
    			setTitle(calTitle);
    			if(dStart.compareTo(dEnd)==0 && tStart.equals(tEnd)){
    				
    				Calendar c = Calendar.getInstance();
    				c.setTime(dEnd.toDate());
    				c.add(Calendar.DATE, 1);
    				date_start = dateFormat.format(dStart.toDate());
    				date_end = dateFormat.format(c.getTime());
    			}else{
    				setDate_start(dStart.toDate(), tStart);
    				setDate_end(dEnd.toDate(), tEnd);
    			}
    			setTimestamp();
    			setDescription(desc);
    			
    			String eventDetails = 
    					"\nBEGIN:VEVENT" + 
    						"\nUID:" + uid +  
    						"\nCREATED:" + dateFormat.format(dateTime.toDate()) + "T" + timeFormat.format(dateTime.toDate()) +
    						"\nDTSTART;TZID=" + dtz + ":" + date_start +
    						"\nDTEND;TZID=" + dtz + ":" + date_end + 
    						timestamp + 
    						"\nTRANSP:OPAQUE" +
    						title + description + 
    						"\nSEQUENCE:0" + 
    					"\nEND:VEVENT";           
        
        return eventDetails;
    }
    
    public String calendarFooter(){
    	return "\nEND:VCALENDAR";
    }
    
    private void setTitle(String t){
        this.title = "\nSUMMARY:" + t;
    }
    
    public String generateFilename(){
        this.filename =  "KPME-ALR-" + dateFormat.format(dateTime.toDate()) + "-" + 
                timeFormat.format(dateTime.toDate()) + ".ics";
        return this.filename;
    }
    
    private void setDate_start(Date date, String time){
        this.date_start = dateFormat.format(date) + "T" + time;
    }
    
    private void setDate_end(Date date, String time){
        this.date_end = dateFormat.format(date) + "T" + time;
    }
    
    private void setDescription(String d){   
        d = d.replaceAll("\n", "\\\\n");
        this.description = "\nDESCRIPTION:" + d;
    } 
    
    private void setTimestamp(){
        this.timestamp = "\nDTSTAMP:" + dateFormat.format(dateTime.toDate()) +
                         "T" + timeFormat.format(dateTime.toDate()) + "Z";
    }
    
    private void setTimezone(){
    	
    	int offset = dtz.getOffset(dateTime);
    	int DST = localeTZ.getDSTSavings();
        
        DSTname = localeTZ.getDisplayName(true, TimeZone.LONG);
        STname = localeTZ.getDisplayName(false, TimeZone.LONG);
        
        //Changes for KPME-2996 offset issue
        char sign;
        if (offset >= 0) {
    		sign = '+';
    	}else{
    		sign = '-';
    		offset = offset * (-1);
    	}        
        
        UTCoffset = String.format("%s%02d%02d", sign, 
	    			offset / 3600000, (offset / 60000) % 60);
    	sTime = String.format("%s%02d%02d", sign, 
	    			(offset + DST) / 3600000, ((offset + DST) / 60000) % 60);
    	 
    }
}
