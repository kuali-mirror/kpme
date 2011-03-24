<%@include file="/WEB-INF/jsp/TkTldHeader.jsp"%>
<c:set var="Form" value="${ClockActionForm}" scope="request"/>

<c:choose>
	<c:when test="${Form.currentClockAction eq 'CI'}">
		<c:set var="clockActionDescription" value="Clock In"/>
		<c:set var="lastClockActionMessage" value="Clocked out since : "/>
	</c:when>
	<c:when test="${Form.currentClockAction eq 'LI'}">
	    <c:set var="clockActionDescription" value="Clock Out"/>
	    <c:set var="lastClockActionMessage" value="At lunch since : "/>
	</c:when>
	<c:when test="${Form.currentClockAction eq 'LO'}">
	<c:set var="clockActionDescription" value="Clock Out"/>
		<c:set var="lastClockActionMessage" value="Returned from lunch since : "/>
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
	<html:hidden property="currentServerTime" value="${Form.currentServerTime}" styleId="currentServerTime"/>
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
			<tr>
				<td class="sub-header"><bean:message key="clock.clockAssignment"/> : </td>
				<td>
					<tk:assignment assignments="${Form.assignmentDescriptions}"/>
				</td>
			</tr>
			<tr class="footer">
				<td colspan="2" align="center">
                    <input id="clock-button" type="submit" class="button" value="${clockActionDescription}" name="clockAction" onclick="this.form.methodToCall.value='clockAction';"/>
                    <c:choose>
						<c:when test="${Form.currentClockAction eq 'CO'}">
                           <c:choose>
                               <c:when test="${Form.showLunchButton}">
						        <input type="submit" class="button" value="Take Lunch" name="lunchOut" onclick="this.form.methodToCall.value='clockAction'; this.form.currentClockAction.value='LO';"/>
                               </c:when>
                           </c:choose>
						</c:when>
						<c:when test="${Form.currentClockAction eq 'LI'}">
						   <input type="submit" class="button" value="Return From Lunch" name="lunchIn" onclick="this.form.methodToCall.value='clockAction'; this.form.currentClockAction.value='LI';"/>
						</c:when>
                    </c:choose>
					<input type="button" class="button" id="missed-punch-iframe-button" value="Missed Punch" name="missedPunch"/>
					<c:if test="${Form.showDistributeButton}">
						<input id="distribute-button" type="submit" class="button" value="Distribute Time Blocks"
							name="distributeTime" onclick="this.form.methodToCall.value='clockAction'"/>
					</c:if>
				</td>
			</tr>
		</table>
	</div>

	</html:form>
</tk:tkHeader>

<div id="missed-punch-dialog" title="Missed Punch" style="display:none;">
   <iframe width="800" height="500" src="kr/maintenance.do?businessObjectClassName=org.kuali.hr.time.missedpunch.MissedPunch&methodToCall=start&tdocid=${Form.timesheetDocument.documentId}">
   </iframe>
</div>

<tk:note/>