<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN"  "http://www.w3.org/TR/html4/frameset.dtd">
<HTML>
<HEAD>
<TITLE>Indiana University Timekeeping</TITLE>
</HEAD>
<FRAMESET rows="8%,92%">
  <FRAME src="CloseWindowMessage.jsp">
 
 <%if(request.getParameter("cardId")!= null){%>
  <FRAME name="mainContent" src="../../TimesheetDocument.do?method=open&kiosk=true&cardId=<%=request.getParameter("cardId")%>">
  <% }else{ %>
   <FRAME name="mainContent" src="../../TimesheetDocument.do?method=open"> 
  <%}%>

 <NOFRAMES>
      <P>This browser does not support frames.  Try the links below:
      <UL>
         <LI><A href="../../TimesheetDocument.do?method=open">Open Current Timesheet</A>
      </UL>
  </NOFRAMES>
</FRAMESET>
</HTML>