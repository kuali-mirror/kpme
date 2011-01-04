<%@include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<c:set var="Form" value="${ClockActionForm}" scope="request"/>
<c:choose>
	<c:when test="${Form.currentClockAction eq 'CI'}">
		<c:set var="clockActionDescription" value="Clock In"/>
		<c:set var="lastClockActionMessage" value="Clocked out since : "/>
	</c:when>
	<c:when test="${Form.currentClockAction eq 'LI'}">
	    <c:set var="clockActionDescription" value="Clock Out"/>
	    <c:set var="lastClockActionMessage" value="Return from lunch since : "/>
	</c:when>
	<c:when test="${Form.currentClockAction eq 'LO'}">
	<c:set var="clockActionDescription" value="Clock Out"/>
		<c:set var="lastClockActionMessage" value="Take lunch since : "/>
	</c:when>
	<c:otherwise>
		<c:set var="clockActionDescription" value="Clock Out"/>
		<c:set var="lastClockActionMessage" value="Clocked in since : "/>
	</c:otherwise>
</c:choose>
<c:if test="${Form.lastClockTimestamp eq null}">
    <c:set var="clockActionDescription" value="Clock In"/>
    <c:set var="lastClockActionMessage" value="No previous clock information"/>
</c:if>

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
				<td>${lastClockActionMessage}
				<fmt:timeZone value="${Form.user.userPreference.timezone}"><fmt:formatDate type="both" value="${Form.lastClockTimestamp}" pattern="EEE, MMMM d yyyy HH:mm:ss, zzzz"/></fmt:timeZone>
				</td>
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
                    <%--<c:if test="${Form.currentClockAction eq 'CI' or Form.currentClockAction eq 'CO'}">  --%>
                        <input id="clock-button" type="submit" class="button" value="${clockActionDescription}" name="clockAction" onclick="this.form.methodToCall.value='clockAction';"/>
                    <%--</c:if>  --%>				
                    <c:choose>
						<c:when test="${Form.currentClockAction eq 'CO'}">
						   <input type="submit" class="button" value="Take Lunch" name="lunchIn" onclick="this.form.methodToCall.value='clockAction'; this.form.currentClockAction.value='LI';"/>
						</c:when>
						<c:when test="${Form.currentClockAction eq 'LO'}">
						   <input type="submit" class="button" value="Return From Lunch" name="lunchOut" onclick="this.form.methodToCall.value='clockAction'; this.form.currentClockAction.value='LO';"/>
						</c:when>
                    </c:choose>
					<input type="submit" class="button" value="Missed Punch" name="missedPunch"/>
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