<%--

    Copyright 2004-2012 The Kuali Foundation

    Licensed under the Educational Community License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.opensource.org/licenses/ecl2.php

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

--%>
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
					<td>Accrual Rate</td>
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
								<c:when test="${entry.key eq 'accrualName'}">  <!-- do not display Accrual Name -->
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