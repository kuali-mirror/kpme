<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN"  "http://www.w3.org/TR/html4/frameset.dtd">
<HTML>
<HEAD>
<TITLE>Indiana University Timekeeping</TITLE>
</HEAD>
<FRAMESET rows="5%,78%,17%">
  <FRAME src="CloseWindowMessage.jsp">
  <FRAME name="mainContent" src="../../TimesheetDocument.do?method=open&kiosk=true&cardId=<%=request.getParameter("cardId")%>">
  <FRAMESET cols="33%, 34%, 33%">
      <FRAME src="../../OtherTimesheetsLookup.do?method=load&kiosk=true">
      <FRAME src="../../NotifySupervisorLookup.do?method=load&kiosk=true">
	  <FRAME src="../../UserPreference.do?method=load&kiosk=true">
  </FRAMESET>
 
  <NOFRAMES>
      <P>This browser does not support frames.  Try the links below:
      <UL>
         <LI><A href="../../TimesheetDocument.do?method=open">Open Current Timesheet</A>
         <LI><A href="../../NotifySupervisorLookup.do?method=load">Notify TIME Approver</A>
      </UL>
  </NOFRAMES>
</FRAMESET>
</HTML>