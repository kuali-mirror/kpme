<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>
<c:set var="Form" value="${TimeOffAccrualActionForm}" scope="request" />

<tk:tkHeader tabId="leaveAccrual">
	<html:hidden property="methodToCall" value="" />

	<div id="timeOffAccrual">
	<c:choose>
		<c:when test="${fn:length(Form.timeOffAccrualsCalc) == 0}">
		   <div style="text-align: center; margin: 10px auto 10px auto;">No leave accrual information available.</div>
		</c:when>
		<c:otherwise>
			<table>
				<tr class="header">
					<td>Accrual Category</td>
					<td>Yearly Carryover</td>
					<td>Hours Accrued</td>
					<td>Hours Taken</td>
					<td>Hours Adjust</td>
					<td style="border:3px solid black;">Total Hours
						<button style="width: 20px; height: 20px; vertical-align: text-top"
							id="endTimeHelp"
							title="Yearly Carryover + Hours Accrued - Hours Taken + Hours Adjust"
							tabindex="999">help</button>
					</td>
					<td>Effective Date</td> 
				</tr>
				<c:forEach var="list" items="${Form.timeOffAccrualsCalc}">
					<tr>
						<c:forEach var="entry" items="${list}">
							<c:choose>
								<c:when test="${entry.key eq 'effdt'}">
									<td><fmt:formatDate value="${entry.value}" pattern="MM/dd/yyyy"/></td>
								</c:when>
								<c:when test="${entry.key eq 'totalHours'}">
									<td style="border:3px solid black;">${entry.value}</td>
								</c:when>
								<c:otherwise>
									<td>${entry.value}</td>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
    </c:choose>
	</div>

</tk:tkHeader>