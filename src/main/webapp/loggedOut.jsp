<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<jsp:useBean id="form" class="org.kuali.hr.time.base.web.TkForm"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>Kuali Time</title>
  <tk:tkInclude/>
  <tk:tkJsInclude/>
</head>
<body>
<div id="tabs" class="ui-tabs ui-widget ui-widget-content ui-corner-all">
<span class="title ${backdoor}" >
<img src="images/kuali_base.png" style="width:4em;"/>
TIME
</span>
<br/><br/>
<p>
You have logged out of the system.
</p>
<br/>
<p>
<a href="Admin.do">Administrative</a><br/>
<a href="Clock.do">Clock</a><br/>
<a href="TimeDetail.do">TimeDetail</a><br/>
<a href="TimeApproval.do?methodToCall=loadApprovalTab">Time Approval</a><br/>
<a href="PersonInfo.do">Person Info</a><br/>
</p>
</div>
</body>
</html>