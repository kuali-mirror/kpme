/**
 * Copyright 2004-2013 The Kuali Foundation
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class CalendarEvent {
    
    /* Data for date, date formatting, and time zone */
    private GregorianCalendar gCal = new GregorianCalendar();
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    public  TimeZone localeTZ = gCal.getTimeZone();
    
    /* Name of the time zone with daylight savings */
    private String DSTname;
    /* Name of the time zone without daylight savings */
    private String STname;
    /* The offset from UTC in the current time zone */
    private String UTCoffset;
    private String sTime;
    
    /* Data for properties that will be written to .ics file */
    private static final String VERSION = "\nVERSION:2.0";
    //Prod ID can be set as per the requirements
    private static final String PRODID  = "\nPRODID:-//KUALI KPME//Version 2.1//EN";
    private String date_start;
    private String date_end;
    private String description;
    private String title;
    private String timestamp;
    private String filename;
    
    public String getFilename() {
		return filename;
	}
    
    public String calendarHeader(){
    	setTimezone();
    	String headerContent = "BEGIN:VCALENDAR" + "\nMETHOD:PUBLISH" +
                  VERSION + "\nX-WR-CALNAME:" + filename + PRODID + 
                  "\nX-WR-TIMEZONE:" + localeTZ.getID() + "\nCALSCALE:GREGORIAN" +
                  
                  "\nBEGIN:VTIMEZONE" + "\nTZID:" + localeTZ.getID() +
                  
                  "\nBEGIN:STANDARD" + "\nTZOFFSETFROM:" + UTCoffset +
                  "\nDTSTART:" + "19420515T000000" + "\nTZNAME:" + STname +
                  "\nTZOFFSETTO:" + sTime + "\nRDATE:19420515T000000" +
                  "\nRDATE:19451015T000000" + "\nEND:STANDARD" +
                 
				  "\nBEGIN:DAYLIGHT" + "\nTZOFFSETFROM:" +  UTCoffset + 
				  "\nDTSTART:" + "19420901T000000" + "\nTZNAME:" +  DSTname +
				  "\nTZOFFSETTO:" + sTime + "\nRDATE:" + "19420901T000000" + "\nEND:DAYLIGHT" 
				  
				  + "\nEND:VTIMEZONE";
    	
    	return headerContent;
    }
    
    public String createEvent(String title, Date dStart, Date dEnd, String tStart,
            String tEnd, String desc, String uid){
    			setTitle(title);
    			setDate_start(dStart, tStart);
    			if(dStart.compareTo(dEnd)==0 && tStart.equals(tEnd)){
    				Calendar c = Calendar.getInstance();
    				c.setTime(dEnd);
    				c.add(Calendar.DATE, 1);
    				setDate_end(c.getTime(), tEnd);
    			}else{
    				setDate_end(dEnd, tEnd);
    			}
    			setTimestamp();
    			setDescription(desc);
    			
    			String eventDetails = "\nBEGIN:VEVENT" + "\nUID:" + uid +  
                  "\nCREATED:" + dateFormat.format(gCal.getTime()) +
                      "T" + timeFormat.format(gCal.getTime()) + "Z" +
                  "\nDTSTART;TZID=" + localeTZ.getID() + ":" + date_start +
                  "\nDTEND;TZID=" + localeTZ.getID() + ":" + date_end + 
                  timestamp + "\nTRANSP:OPAQUE" +
                  this.title + description + "\nSEQUENCE:0" + "\nEND:VEVENT";           
        
        return eventDetails;
    }
    
    public String calendarFooter(){
    	return "\nEND:VCALENDAR";
    }
    
    private void setTitle(String t){
        this.title = "\nSUMMARY:" + t;
    }
    
    public void setFilename(){
    	//Set FileName that you want
        this.filename =  "KPME-ALR-" + dateFormat.format(gCal.getTime()) + "-" + 
                timeFormat.format(gCal.getTime()) + ".ics";
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
        /*
         * Gets the current date and time used to stamp the .ics file
         */
        this.timestamp = "\nDTSTAMP:" + dateFormat.format(gCal.getTime()) +
                         "T" + timeFormat.format(gCal.getTime()) + "Z";
    }
    
    private void setTimezone(){
        int DST = gCal.get(Calendar.DST_OFFSET);
        
        // Get the name of the timezone that the user is in
        // based on the timezone of the user's computer
        
        /* Name of the timezone in standard and daylight savings */
        DSTname = localeTZ.getDisplayName(true, TimeZone.LONG);
        STname = localeTZ.getDisplayName(false, TimeZone.LONG);
        
        /* Get the UTC offset of the current timezone in ms */
        int offset = localeTZ.getRawOffset();  
        
        /* 
         * Convert the UTC offset to (+/-)hhmm format for 
         * standard and daylight savings in the current timezone
        */
        UTCoffset = String.format("%s%02d%02d", offset >= 0 ? "+" : "", 
                offset / 3600000, (offset / 60000) % 60);
        
        sTime = String.format("%s%02d%02d", (offset + DST) >= 0 ? "+" : "", 
                (offset + DST) / 3600000, ((offset + DST) / 60000) % 60);
    }
}
