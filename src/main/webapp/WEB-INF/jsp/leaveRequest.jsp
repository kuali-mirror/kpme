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

<c:set var="Form" value="${LeaveRequestForm}" scope="request" />
<c:set var="KualiForm" value="${LeaveRequestForm}" scope="request" />

<tk:tkHeader tabId="leaveRequest">
	<html:form action="/LeaveRequest.do" method="POST">
		<html:hidden property="methodToCall" value="submitForApproval"
			styleId="methodToCall" />
		<br />
		<div class="leave-request">

			<div id="leave-planned-request">
				<h3>
					<a href="#">Planned Leaves</a>
				</h3>
				<div>
					<table>
						<tr>
							<th>Date</th>
							<th>Leave Code</th>
							<th>Hours</th>
							<th>Description</th>
							<th>Submit</th>
						</tr>
						<c:if test="${fn:length(Form.plannedLeaves) > 0}">
							<logic:iterate scope="request" indexId="index" name="Form"
								property="plannedLeaves" id="plannedLeave">
								<tr>
									<td><fmt:formatDate value="${plannedLeave.leaveDate}"
											pattern="MM/dd/yyyy" />
									</td>
									<td>${plannedLeave.earnCodeDescription} (${plannedLeave.earnCode})</td>
									<td>${plannedLeave.leaveAmount}</td>
									<td>${plannedLeave.description}</td>
									<td><html:checkbox property="plannedLeaves[${index}].submit" value="true" name="Form" /></td>
								</tr>
							</logic:iterate>
							<tr>
								<td colspan="5" align="right"><input type="submit"
									class="approve" value="Submit" name="Submit" />
								</td>
							</tr>
						</c:if>
					</table>
				</div>
			</div>
			<div id="leave-pending-request">
				<h3>
					<a href="#">Leave Request Pending Approval</a>
				</h3>
				<div>
					<table>
						<tr>
							<th>Date</th>
							<th>Leave Code</th>
							<th>Hours</th>
							<th>Description</th>
							<th>Date/Time Submitted</th>
						</tr>
						<c:if test="${fn:length(Form.pendingLeaves) > 0}">
							<c:forEach var="pendingLeave" items="${Form.pendingLeaves}">
								<tr>
									<td><fmt:formatDate value="${pendingLeave.leaveDate}"
											pattern="MM/dd/yyyy" />
									</td>
									<td>${pendingLeave.earnCodeDescription} (${pendingLeave.earnCode})</td>
									<td>${pendingLeave.leaveAmount}</td>
									<td>${pendingLeave.description}</td>
									<td><fmt:formatDate type="both"
											value="${pendingLeave.dateAndTime}"
											pattern="MM/dd/yyyy hh:mm a" />
									</td>
								</tr>
							</c:forEach>
						</c:if>
					</table>
				</div>
			</div>
			<div id="leave-approved-request">
				<h3>
					<a href="#">Approved Leave Requests</a>
				</h3>
				<div>
					<table>
						<tr>
							<th>Date</th>
							<th>Leave Code</th>
							<th>Hours</th>
							<th>Description</th>
							<th>Date/Time Approved</th>
						</tr>
						<c:if test="${fn:length(Form.approvedLeaves) > 0}">
							<c:forEach var="approvedLeave" items="${Form.approvedLeaves}">
								<tr>
									<td><fmt:formatDate value="${approvedLeave.leaveDate}"
											pattern="MM/dd/yyyy" />
									</td>
									<td>${approvedLeave.earnCodeDescription} (${approvedLeave.earnCode})</td>
									<td>${approvedLeave.leaveAmount}</td>
									<td>${approvedLeave.description}</td>
									<td><fmt:formatDate type="both"
											value="${approvedLeave.dateAndTime}"
											pattern="MM/dd/yyyy hh:mm a" />
									</td>
								</tr>

							</c:forEach>
						</c:if>
					</table>
				</div>
			</div>
			<div id="leave-disapproved-request">
				<h3>
					<a href="#"> Leave Requests Not Approved</a>
				</h3>
				<div>
					<table>
						<tr>
							<th>Date</th>
							<th>Leave Code</th>
							<th>Hours</th>
							<th>Status</th>
							<th>Reason</th>
							<th>Date/Time Disapproved</th>

						</tr>
						<c:if test="${fn:length(Form.disapprovedLeaves) > 0}">
							<c:forEach var="disaprovedLeave"
								items="${Form.disapprovedLeaves}">
								<tr>
									<td><fmt:formatDate value="${disaprovedLeave.leaveDate}"
											pattern="MM/dd/yyyy" />
									</td>
									<td>${disaprovedLeave.earnCodeDescription} (${disaprovedLeave.earnCode})</td>
									<td>${disaprovedLeave.leaveAmount}</td>
									<td>${disaprovedLeave.requestStatus == 'D' ? 'Disapprove'
										: 'Defer'}</td>
									<td>${disaprovedLeave.reason}</td>
									<td><fmt:formatDate type="both"
											value="${disaprovedLeave.dateAndTime}"
											pattern="MM/dd/yyyy hh:mm a" />
									</td>
								</tr>
							</c:forEach>
						</c:if>
					</table>
				</div>
			</div>
		</div>
		<br />
	</html:form>
</tk:tkHeader>