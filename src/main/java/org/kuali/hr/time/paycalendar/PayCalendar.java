package org.kuali.hr.time.paycalendar;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import oracle.jdbc.driver.OracleConnection;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class PayCalendar extends PersistableBusinessObjectBase {
    
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    
    private Long payCalendarId;
    private String calendarGroup;
    private String chart;

    private Date beginDate;
    private Time beginTime;

    private Date endDate;
    private Time endTime;

    private Date initiateDate;
    private Time initiateTime;

    private Date endPayPeriodDate;
    private Time endPayPeriodTime;

    private Date employeeApprovalDate;
    private Time employeeApprovalTime;

    private Date supervisorApprovalDate;
    private Time supervisorApprovalTime;

    private List<PayCalendarDates> payCalendarDates = new LinkedList<PayCalendarDates>();

    public PayCalendar() {

    }

    public Long getPayCalendarId() {
	return payCalendarId;
    }

    public void setPayCalendarId(Long payCalendarId) {
	this.payCalendarId = payCalendarId;
    }

    public String getCalendarGroup() {
	return calendarGroup;
    }

    public void setCalendarGroup(String calendarGroup) {
	this.calendarGroup = calendarGroup;
    }

    public String getChart() {
	return chart;
    }

    public void setChart(String chart) {
	this.chart = chart;
    }

    public Date getBeginDate() {
	return beginDate;
    }

    public void setBeginDate(Date beginDate) {
	this.beginDate = beginDate;
    }

    public Time getBeginTime() {
	return beginTime;
    }

    public void setBeginTime(Time beginTime) {
	this.beginTime = beginTime;
    }

    public Date getEndDate() {
	return endDate;
    }

    public void setEndDate(Date endDate) {
	this.endDate = endDate;
    }

    public Time getEndTime() {
	return endTime;
    }

    public void setEndTime(Time endTime) {
	this.endTime = endTime;
    }

    public Date getInitiateDate() {
	return initiateDate;
    }

    public void setInitiateDate(Date initiateDate) {
	this.initiateDate = initiateDate;
    }

    public Time getInitiateTime() {
	return initiateTime;
    }

    public void setInitiateTime(Time initiateTime) {
	this.initiateTime = initiateTime;
    }

    public Date getEndPayPeriodDate() {
	return endPayPeriodDate;
    }

    public void setEndPayPeriodDate(Date endPayPeriodDate) {
	this.endPayPeriodDate = endPayPeriodDate;
    }

    public Time getEndPayPeriodTime() {
	return endPayPeriodTime;
    }

    public void setEndPayPeriodTime(Time endPayPeriodTime) {
	this.endPayPeriodTime = endPayPeriodTime;
    }

    public Date getEmployeeApprovalDate() {
	return employeeApprovalDate;
    }

    public void setEmployeeApprovalDate(Date employeeApprovalDate) {
	this.employeeApprovalDate = employeeApprovalDate;
    }

    public Time getEmployeeApprovalTime() {
	return employeeApprovalTime;
    }

    public void setEmployeeApprovalTime(Time employeeApprovalTime) {
	this.employeeApprovalTime = employeeApprovalTime;
    }

    public Date getSupervisorApprovalDate() {
	return supervisorApprovalDate;
    }

    public void setSupervisorApprovalDate(Date supervisorApprovalDate) {
	this.supervisorApprovalDate = supervisorApprovalDate;
    }

    public Time getSupervisorApprovalTime() {
	return supervisorApprovalTime;
    }

    public void setSupervisorApprovalTime(Time supervisorApprovalTime) {
	this.supervisorApprovalTime = supervisorApprovalTime;
    }

    public List<PayCalendarDates> getPayCalendarDates() {
	return payCalendarDates;
    }

    public void setPayCalendarDates(List<PayCalendarDates> payCalendarDates) {
	this.payCalendarDates = payCalendarDates;
    }
    
    
    /**
     * 
     * Oracle:
     * CREATE  TABLE timestamp_test (ts1 TIMESTAMP, ts2 TIMESTAMP WITH TIME ZONE, ts3 TIMESTAMP  WITH LOCAL TIME ZONE )
     * 
     * Mysql:
     * 
     * Just three timestamps.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
	Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
	String url = "jdbc:oracle:thin:@esdbd04.uits.indiana.edu:1521:TK1DEV";
	String user = "lfox";
	String pw = "fox08al";
//	Class.forName("com.mysql.jdbc.Driver").newInstance();
//	String url = "jdbc:mysql://129.79.23.142:3306/tk";
//	String user = "tk";
//	String pw = "tk_tk_tk";
	Connection conn = DriverManager.getConnection(url, user, pw);
	
	if (url.startsWith("jdbc:oracle")) {
	    //((OracleConnection)conn).setSessionTimeZone("America/New_York");
	    ((OracleConnection)conn).setSessionTimeZone("America/Los_Angeles");
	}
	
	
	// GMT: 7:55
	DateTime datetimeEDT = new DateTime(2010, 6, 20, 3, 55, 0, 0, DateTimeZone.forOffsetHours(-4));; // 6/20 @ 3:55 am, EDT ; 3:55
	// GMT: 8:55
	DateTime datetimeCDT = new DateTime(2010, 6, 20, 3, 55, 0, 0, DateTimeZone.forOffsetHours(-5));; // 6/20 @ 3:55 am, CDT ; 4:55
	// GMT: 10:55
	DateTime datetimePDT = new DateTime(2010, 6, 20, 3, 55, 0, 0, DateTimeZone.forOffsetHours(-7));; // 6/20 @ 3:55 am, PDT ; 6:55
	
	Timestamp tsEDT = new Timestamp(datetimeEDT.getMillis());
	Timestamp tsCDT = new Timestamp(datetimeCDT.getMillis());
	Timestamp tsPDT = new Timestamp(datetimePDT.getMillis());
	
	System.out.println("DateTime object toString() values:");
	System.out.println("t1: " + datetimeEDT);
	System.out.println("t2: " + datetimeCDT);
	System.out.println("t3: " + datetimePDT);
	System.out.println("DateTime millis:");
	System.out.println("t1: " + datetimeEDT.getMillis());
	System.out.println("t2: " + datetimeCDT.getMillis());
	System.out.println("t3: " + datetimePDT.getMillis());	
	System.out.println("\n");
	
	System.out.println("Converted to timestamp values:");
	System.out.println("Timestamps :: t1: " + tsEDT + " t2: " + tsCDT + " t3: " + tsPDT);
	System.out.println("Timestamps (millis):: t1: " + tsEDT.getTime() + " t2: " + tsCDT.getTime() + " t3: " + tsPDT.getTime());
	
	Statement st = conn.createStatement();
	st.execute("delete from timestamp_test");
	
	String insert = "insert into timestamp_test (ts1, ts2, ts3, zonename) values (?,?,?,?)";
	PreparedStatement pst = conn.prepareStatement(insert);
	pst.setTimestamp(1, new Timestamp(datetimeEDT.getMillis()));
	pst.setTimestamp(2, new Timestamp(datetimeEDT.getMillis()));
	pst.setTimestamp(3, new Timestamp(datetimeEDT.getMillis()));
	pst.setString(4, "EDT");
	pst.execute();
	
	pst.setTimestamp(1, new Timestamp(datetimeCDT.getMillis()));
	pst.setTimestamp(2, new Timestamp(datetimeCDT.getMillis()));
	pst.setTimestamp(3, new Timestamp(datetimeCDT.getMillis()));
	pst.setString(4, "CDT");
	pst.execute();
	
	pst.setTimestamp(1, new Timestamp(datetimePDT.getMillis()));
	pst.setTimestamp(2, new Timestamp(datetimePDT.getMillis()));
	pst.setTimestamp(3, new Timestamp(datetimePDT.getMillis()));
	pst.setString(4, "PDT");
	pst.execute();
	
	
	// Pull them back out
	
	String query = "select ts1, ts2, ts3, zonename from timestamp_test";
	st = conn.createStatement();
	ResultSet rs = st.executeQuery(query);
	
	System.out.println("\n\nResults from database query:\n");
	while (rs.next()) {
	    String zonename = rs.getString("zonename");
	    DateTime ts1 = new DateTime(rs.getTimestamp("ts1").getTime());
	    DateTime ts2 = new DateTime(rs.getTimestamp("ts2").getTime());
	    DateTime ts3 = new DateTime(rs.getTimestamp("ts3").getTime());
	    
	   System.out.println(zonename + ": ts1: '" + ts1 + "' ts2: '" + ts2 + "' ts3: '" + ts3 + "'");
	   System.out.println("TZ (ts1): " + ts1.getZone() + " TZ (ts2): " + ts2.getZone() + " TZ (ts3): " + ts3.getZone());
	   System.out.println("\n");
	}
	
	conn.close();
    }

    @Override
    protected LinkedHashMap toStringMapper() {
	// TODO Auto-generated method stub
	return null;
    }
}
