<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<jsp:useBean id="tagSupport" class="org.kuali.hr.time.util.TagSupport"/>
<%@ attribute name="timeSummary" required="true" type="org.kuali.hr.time.timesummary.TimeSummary"%>

<div id="timesheet-summary">
	<div style="clear:both; text-align:center; font-weight: bold; margin-bottom: 5px;">Summary <%--(<a href="#" id="basic">Basic</a> / <a href="#" id="advance">Advanced</a> ) --%></div>
	<div id="timesheet-table-basic">

		<table>
			<thead>
				<tr class="ui-state-default">
				<td/>
				<c:forEach items="${timeSummary.dateDescr}" var="entry">
					<td>${entry}</td>
				</c:forEach>
				</tr>
			</thead>
	    	<tbody>
				<tr style="border-bottom-style: double; font-weight: bold;">
					<td>Worked Hours:</td>
					<c:forEach items="${timeSummary.dateDescr}" var="entry">
						<c:choose>
						<c:when test="${fn:startsWith(entry,'Week')}">
							<td>${timeSummary.workedHours.weeklyTotals[fn:substringAfter(entry,'Week ')-1]}</td>
						</c:when>
						<c:when test="${fn:startsWith(entry,'Period')}">
							<td>${timeSummary.workedHours.periodTotal}</td>
						</c:when>
						<c:otherwise>
							<td>${timeSummary.workedHours.dayToHours[entry]}</td>
						</c:otherwise> 
						</c:choose>
					</c:forEach>
				</tr>
				<c:forEach items="${timeSummary.sections}" var="section">
					<tr style="">
						<td>${section.earnGroup}</td>
					</tr>
					
					<c:forEach items="${section.assignRows}" var="assignRow">
						<tr style="">
							<td>${assignRow.descr}</td>
							<c:forEach items="${timeSummary.dateDescr}" var="entry">
								<c:choose>
								<c:when test="${fn:startsWith(entry,'Week')}">
									<td>${assignRow.weeklyTotals[fn:substringAfter(entry,'Week ')-1]}</td>
								</c:when>
								<c:when test="${fn:startsWith(entry,'Period')}">
									<td>${assignRow.periodTotal}</td>
								</c:when>					
								<c:otherwise>
									<td>${assignRow.dayToHours[entry]}</td>
								</c:otherwise> 
								</c:choose>				
							</c:forEach>
						</tr>
					</c:forEach>
				
					<tr style="border-bottom-style: double; font-weight: bold;">
						<td>${section.summaryRow.descr}</td>
						<c:forEach items="${timeSummary.dateDescr}" var="entry">
							<c:choose>
							<c:when test="${fn:startsWith(entry,'Week')}">
								<td>${section.summaryRow.weeklyTotals[fn:substringAfter(entry,'Week ')-1]}</td>
							</c:when>
							<c:when test="${fn:startsWith(entry,'Period')}">
								<td>${section.summaryRow.periodTotal}</td>
							</c:when>				
							<c:otherwise>
								<td>${section.summaryRow.dayToHours[entry]}</td>
							</c:otherwise> 
							</c:choose>	
						</c:forEach>
					</tr>
			   </c:forEach>
		   </tbody>
		</table>
	</div>
</div>