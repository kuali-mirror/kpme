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
<c:set var="Form" value="${LeaveBlockDisplayForm}" scope="request" />
<c:set var="KualiForm" value="${LeaveBlockDisplayForm}" scope="request" />

<tk:tkHeader tabId="leaveBlockDisplay">
	<html:form action="/LeaveBlockDisplay.do" method="POST">
		<html:hidden property="navString" styleId="navString" />
		<html:hidden property="year" styleId="year" value="${Form.year}" />
		<table align="center" class="leave-block-display">
			<tbody>
			<tr>
				<td align="center">
					<button id="nav_lb_prev"
						class="ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only"
						role="button" title="Previous"
						onclick="this.form.navString.value='PREV';this.form.submit();">
						<span class="ui-button-text">Previous</span>
					</button>
                    <span class="header">${Form.year}</span>
					<button id="nav_lb_next"
						class="ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only"
						role="button" title="Next"
						onclick="this.form.navString.value='NEXT';this.form.submit();">
						<span class="ui-button-text">Next</span>
					</button>
				</td>
			</tr>
			</tbody>
		</table>

	</html:form>

	<div class="leave-block-display">

		<div id="leave-block-display-accordion">
			<h3>
				<a href="#">Leave Blocks for ${Form.year}</a>
			</h3>
			<div>

				<table class="datatable-100">
				<tr>
					<th>Date of Leave</th>
					<th>Earn Code</th>
					<th>Description</th>
					<th>Status</th>
					<th>Document Status</th>
					<th>Amount</th>
					<th>Timestamp</th>
					<th>Assignment</th>
					<th colspan="2">
						Balances
					</th>
				</tr>
					<tr>
						<th colspan="8"></th>
						<c:forEach var="accCat" items="${Form.accrualCategoires}">
							<th>${accCat}</th>
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="accBal" items="${leaveEntry.accrualBalances}">
							<td><c:out value="${accBal.key}" /></td>
						</c:forEach>
					</tr>

					<c:forEach var="leaveEntry" items="${Form.leaveEntries}">
						<tr>
							<td><a
								href="LeaveCalendar.do?documentId=${leaveEntry.documentId}&calEntryId=${leaveEntry.calendarId}">
									<fmt:formatDate type="date" value="${leaveEntry.leaveDate}"
										pattern="MMM-dd-yyyy" />
							</a></td>
							<td>${leaveEntry.earnCode}</td>
							<td>${leaveEntry.description}</td>
							<td><c:out value="${leaveEntry.requestStatus}"/></td>
							<td><c:out value="${leaveEntry.documentStatus}"/></td>
							<td>${leaveEntry.leaveAmount}</td>
							<td><fmt:formatDate type="both" value="${leaveEntry.timestamp}" pattern="MM/dd/yyyy hh:mm a" /></td>
							<td>${leaveEntry.assignmentTitle}</td>
							<c:forEach var="accBal" items="${leaveEntry.accrualBalances}">
								<td><c:out value="${accBal.value}" /></td>
							</c:forEach>
						</tr>
					</c:forEach>
				</table>
			</div>

			<h3>
				<a href="#">Days removed in correction</a>
			</h3>
			<div>
				<display:table name="${Form.correctedLeaveEntries}"
					class="datatable-100" cellspacing="0" requestURIcontext="false"
					uid="correctedEntries">
					<display:column title="Date of Leave">
						<a
							href="LeaveCalendar.do?documentId=${correctedEntries.documentId}&calEntryId=${correctedEntries.calendarId}">
							<fmt:formatDate type="date" value="${correctedEntries.leaveDate}"
								pattern="MMM-dd-yyyy" />
						</a>
					</display:column>
					<display:column property="earnCode" title="Earn Code" />
					<display:column property="description" title="Description" />
					<display:column property="principalIdModified"
						title="User Deleted/Modified" />
					<display:column title="Timestamp Deleted/Modified">
						<fmt:formatDate type="both" value="${correctedEntries.timestamp}"
							pattern="MM/dd/yyyy hh:mm a" />
					</display:column>
					<display:column property="leaveAmount" title="Amount" />
					<display:column property="assignmentTitle" title="Assignment" />
				</display:table>
			</div>

			<h3>
				<a href="#">Inactive Leave Blocks</a>
			</h3>
			<div>
				<display:table name="${Form.inActiveLeaveEntries}"
					class="datatable-100" cellspacing="0" requestURIcontext="false"
					uid="inActiveEntries">
					<display:column title="Date of Leave">
						<a
							href="LeaveCalendar.do?documentId=${inActiveEntries.documentId}&calEntryId=${inActiveEntries.calendarId}">
							<fmt:formatDate type="date" value="${inActiveEntries.leaveDate}"
								pattern="MMM-dd-yyyy" />
						</a>
					</display:column>
					<display:column property="earnCode" title="Earn Code" />
					<display:column property="requestStatus" title="Planning Status" />
					<display:column property="documentStatus" title="Document Status" />
					<display:column property="description" title="Description" />
					<display:column property="leaveAmount" title="Amount" />
					<display:column title="Timestamp Deleted/Modified">
						<fmt:formatDate type="both" value="${inActiveEntries.timestamp}"
							pattern="MM/dd/yyyy hh:mm a" />
					</display:column>
					<display:column property="principalIdModified"
						title="User Deleted/Modified" />
					<display:column property="assignmentTitle" title="Assignment" />
				</display:table>
			</div>
		</div>
	</div>
</tk:tkHeader>