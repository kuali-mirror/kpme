<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<%@ attribute name="calType" required="true" type="java.lang.String"%>

<table class="cal-header" style="width: 100%;">
	<tr>
		<td align="center"><c:choose>
				<c:when test="${calType == 'payCalendar'}">
					<fieldset style="width: 75%; min-height: 150px">
						<legend>Weekly Status</legend>
						<div>
							Pay Period Week Total: <span style="font-weight: bold;">bold</span>
							<br /><br /><br />
						</div>
						<div>
							FLSA Week Total: <span style="font-style: italic;">italics</span>
						</div>
					</fieldset>
				</c:when>
				<c:when test="${calType == 'leaveCalendar'}">
					<fieldset style="width: 75%; min-height: 150px">
						<legend>Leave Request Status</legend>
						<div>
							Approved/Usage: <span class="approvals-approved">bold</span> 
							<br /><br /><br />
						</div>
						<div>
							Planned/Deferred: <span class="approvals-requested">italics</span>
						</div>
					</fieldset>
				</c:when>
			</c:choose>
	</tr>
</table>