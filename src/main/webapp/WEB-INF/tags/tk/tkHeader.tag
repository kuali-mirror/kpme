<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<%@ attribute name="tabId" required="false"%>

<jsp:useBean id="form" class="org.kuali.hr.time.base.web.TkForm"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Trojan Time</title>
		<tk:tkInclude/>
		<tk:tkJsInclude/>
	</head>
	<body>

	<c:choose>
		<c:when test="${form.user.backdoorPerson ne null}">
			<c:set var="person" value="${form.user.backdoorPerson}" />
			<c:set var="prefix" value="Backdoor" />
			<c:set var="highlight" value="ui-state-highlight" />
			<c:set var="backdoor" value="backdoor" />
		</c:when>
		<c:otherwise>
			<c:set var="person" value="${form.user.actualPerson}" />
		</c:otherwise>
	</c:choose>

<input type="hidden" id="tabId" value="${tabId}"/>
	<div id="tabs" class="ui-tabs ui-widget ui-widget-content ui-corner-all">
		<ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-corner-all ui-header ${highlight}">
			<span class="title ${backdoor}" >
			<%--<img src="images/usc_logo.gif" style="width:2em; height:2em; vertical-align: middle" />  --%>
				TIME
			</span>

			<div class="person-info" style="float:right">
				<table class="${backdoor}">
					<tr>
						<td align="right">${prefix} <bean:message key="person.info.employeeName"/>:</td>
						<td><a href="<%=request.getContextPath() %>/PersonInfo.do?methodToCall=showInfo">${person.principalName}</a></td>
					</tr>
					<tr>
						<td align="right">${prefix} <bean:message key="person.info.employeeId"/>:</td>
						<td>${person.principalId}</td>
					</tr>
					<c:if test="${form.user.backdoorPerson ne null}">
						<tr>
							<td colspan="2" align="right">
							<a href="./Clock.do?methodToCall=clearBackdoor"><input type="button" class="button" value="Clear Backdoor" name="clearBackdoor" style="font-size:.7em;"></a>
							</td>
						</tr>
					</c:if>
				</table>
			</div>
		</ul>
		<ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all">
			<li id="admin" class="ui-state-default ui-corner-top"><a href="Admin.do">Admin</a></li>
			<li id="personInfo" class="ui-state-default ui-corner-top"><a href="PersonInfo.do">Person Info</a></li>
			<li id="approvals" class="ui-state-default ui-corner-top"><a href="TimeApproval.do">Approvals</a></li>
			<li id="timesheet" class="ui-state-default ui-corner-top"><a href="Timesheet.do">Historical Timesheets</a></li>
			<li id="timeDetail" class="ui-state-default ui-corner-top"><a href="TimeDetail.do">Time Detail</a></li>
			<li id="clock" class="ui-state-default ui-corner-top"><a href="Clock.do">Clock</a></li>
		</ul>

		<jsp:doBody />
	<tk:note/>
	</div>
	<tk:tkFooter/>
	</body>
</html>
