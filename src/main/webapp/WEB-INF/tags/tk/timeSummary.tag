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
					<c:if test="${beginPosition<0}">
						<c:set var="beginPosition" value="0" />
					</c:if>
					<c:forEach items="${timeSummary.workedHours}" begin="${beginPosition}" var="entry">
						<td>${entry}</td>
					</c:forEach>
				</tr>
				<c:forEach items="${timeSummary.sections}" var="section">
					<c:forEach items="${section.earnCodeSections}" var="earnCodeSection">
						<tr>
							<td>${earnCodeSection.earnCode}</td>
						</tr>
						<c:forEach items="${earnCodeSection.assignmentsRows}" var="assignRow">
						<tr style="border-bottom-style: double; font-weight: bold;">
							<td class="${assignRow.cssClass}">${assignRow.descr}</td>
							<c:choose>
							<c:when test="${!earnCodeSection.isAmountEarnCode}">
								<c:forEach items="${assignRow.total}" var="entry">
									<c:choose>
									<c:when test="${entry ne '0.00' and entry%7 != 0}">
										<td class="${assignRow.cssClass}">${entry}</td>
									</c:when>
									<c:otherwise>
										<td/>
									</c:otherwise>
									</c:choose>								
								</c:forEach>
							</c:when>
							<c:otherwise>
								<c:forEach items="${assignRow.amount}" var="entry">
									<c:choose>
									<c:when test="${entry ne '0.00' and entry%7 != 0}">
										<td class="${assignRow.cssClass}">$${entry}</td>
									</c:when>
									<c:otherwise>
										<td/>
									</c:otherwise>
									</c:choose>								
								</c:forEach>
							</c:otherwise>
							</c:choose>
						</tr>
						</c:forEach>	
					</c:forEach>
					<tr>
						<td>${section.earnGroup}</td>
							<c:forEach items="${section.totals}" var="entry">
								<c:choose>
								<c:when test="${entry ne '0.00' and entry%7 != 0}">
									<td>${entry}</td>
								</c:when>
								<c:otherwise>
									<td/>
								</c:otherwise>	
								</c:choose>							
							</c:forEach>
					</tr>
				</c:forEach>
		   </tbody>
		</table>
	</div>
</div>