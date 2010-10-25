<%@include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<c:set var="Form" value="${ClockActionForm}" scope="request"/>
<c:choose>
	<c:when test="${Form.currentClockAction eq 'CI'}">
		<c:set var="clockActionDescription" value="Clock In"/>
		<c:set var="lastClockActionMessage" value="Clocked out since ${Form.lastClockTimestamp}"/>
	</c:when>
	<c:otherwise>
		<c:set var="clockActionDescription" value="Clock Out"/>
		<c:set var="lastClockActionMessage" value="Clocked in since ${Form.lastClockTimestamp}"/>
	</c:otherwise>
</c:choose>
<tk:tkHeader tabId="clock">

	<html:form action="/Clock.do">
	<html:hidden property="methodToCall" value=""/>
	<html:hidden property="currentClockAction" styleId="clockAction"/>
	<html:hidden property="lastClockedInTime" value="${Form.lastClockTimestamp}" styleId="lastClockedInTime"/>

	<div id="clock">
		<table>
			<tr class="header"><td colspan="2"><bean:message key="clock.title"/></td></tr>
			<tr>
				<td class="sub-header"><bean:message key="clock.currentTime"/> : </td>
				<td><div class="jClock"></div></td>
			</tr>
			<tr>
				<td class="sub-header"><bean:message key="clock.workStatus"/> : </td>
				<td>${lastClockActionMessage}</td>
			</tr>
			<%--
			<tr>
				<td class="sub-header"><bean:message key="clock.elapsedTime"/> : </td>

				<td>
					<c:choose>
						<c:when test="${Form.currentClockAction eq 'CO'}">
							<span class="elapsedTime"></span>
						</c:when>
						<c:otherwise>
							00:00:00
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td class="sub-header"><bean:message key="clock.lastClockedHours"/> : </td>
				<td>3.2</td>
			</tr>
			 --%>
			<tr>
				<td class="sub-header"><bean:message key="clock.clockAssignment"/> : </td>
				<td>
					<tk:assignment assignments="${Form.assignmentDescriptions}"/>
				</td>
			</tr>
			<tr class="footer">
				<td colspan="2" align="center">
					<input id="clock-button" type="button" class="button" value="${clockActionDescription}" name="clockAction" onclick="this.form.methodToCall.value='clockAction'; this.form.submit();"/>
					<input type="button" class="button" value="Skip Entry" name="skipEntry"/>
				</td>
			</tr>
		</table>
	</div>

	</html:form>
</tk:tkHeader>

<script language="JavaScript" type="text/javascript">
<!--

var current_date = new Date();
var current_timezone = current_date.getTimezoneOffset();

//document.write("Your time zone is " + current_timezone + " minutes from GMT<br/>");
//document.write("Your time zone is " + current_timezone/60 + " hours from GMT");

//-->
</script>