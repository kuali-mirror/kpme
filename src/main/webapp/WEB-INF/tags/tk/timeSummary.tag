<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<jsp:useBean id="tagSupport" class="org.kuali.hr.time.util.TagSupport"/>
<%@ attribute name="timeSummary" required="true" type="org.kuali.hr.time.timesummary.TimeSummary"%>
<c:set var="headerLength" value="${fn:length(timeSummary.summaryHeader)}"/>  
<c:set var="workedHoursLength" value="${fn:length(timeSummary.workedHours)}"/>  
<c:set var="beginPosition" value="${workedHoursLength - headerLength}"/>  
<div id="timesheet-summary">
	<div style="clear:both; text-align:center; font-weight: bold; margin-bottom: 5px;">Summary <%--(<a href="#" id="basic">Basic</a> / <a href="#" id="advance">Advanced</a> ) --%></div>
	<div id="timesheet-table-basic">

		<table>
			<thead>
				<tr class="ui-state-default">
				<th></th>
					<c:forEach items="${timeSummary.summaryHeader}" var="entry">
						<th scope="col">${entry}</th>
					</c:forEach>
				</tr>
			</thead>
	    	<tbody>
				<tr style="border-bottom-style: double; font-weight: bold;">
					<td>Worked Hours:</td>
					<c:forEach items="${timeSummary.workedHours}" begin="${beginPosition}" var="entry">
						<td>${entry}</td>
					</c:forEach>
				</tr>
				<c:forEach items="${timeSummary.sections}" var="section">
				<tr>
					<td>${section.earnGroup}</td>
				</tr>
					<c:forEach items="${section.assignmentRows}" var="assignRow">
						<tr style="border-bottom-style: double; font-weight: bold;">
							<td>${assignRow.descr}</td>
							<c:forEach items="${assignRow.total}" var="entry">
								<td><c:if test="${entry ne '0.00' and entry%7 != 0}">${entry}</c:if></td>
							</c:forEach>
						</tr>
					</c:forEach>
					<tr style="border-bottom-style: double; font-weight: bold;">
						<td>${section.earnGroup}</td>
						<c:forEach items="${section.totals}" var="entry">
							<td><c:if test="${entry ne '0.00' and entry%7 != 0}">${entry}</c:if></td>
						</c:forEach>
					</tr>
				</c:forEach>
		   </tbody>
		</table>
	</div>
</div>