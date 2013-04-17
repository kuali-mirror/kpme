<%--

    Copyright 2004-2013 The Kuali Foundation

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
        <html:hidden property="navString" styleId="navString" />
        <html:hidden property="year" styleId="year" value="${Form.year}" />
        <div class="cal">
            <table align="center" class="cal-header">
                <tbody>
                <tr>
                    <td align="left" width="30%" />
                    <td align="center" width="40%">
                        <button id="nav_lb_prev" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only"
                                role="button" title="Previous" onclick="this.form.navString.value='PREV';this.form.submit();">
                            <span class="ui-button-text">Previous</span>
                        </button>
                        <span class="header-title-center">${Form.year}</span>
                        <button id="nav_lb_next" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only"
                                role="button" title="Next" onclick="this.form.navString.value='NEXT';this.form.submit();">
                            <span class="ui-button-text">Next</span>
                        </button>
                    </td>
                    <td align="right" width="30%">
                            <span class="header-title-right">
                                <a href="LeaveCalendar.do?methodToCall=gotoCurrentPayPeriod"
                                   target="_self" id="cpplink">Current Leave Period</a>
                            </span>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </html:form>
	<html:form action="/LeaveRequest.do" method="POST">
		<html:hidden property="methodToCall" value="submitForApproval" styleId="methodToCall" />
		<br />
		<div class="leave-request">

			<div id="leave-planned-request">
				<h3>
					<a href="#">Planned Leaves</a>
				</h3>
				<div>
					<table>
						<tr>
							<th>Requested Date</th>
							<th>Earn Code</th>
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
									<td>${plannedLeave.planningDescription}</td>									
									<td><html:checkbox property="plannedLeaves[${index}].submit" value="true" name="Form" /></td>
								</tr>
							</logic:iterate>
							<tr>
								<td colspan="5" align="right"><input type="submit"
									class="approve" value="Submit Request for Approval" name="Submit" />
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
							<th>Requested Date</th>
							<th>Earn Code</th>
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
									<td><joda:format value="${Form.documents[pendingLeave.lmLeaveBlockId].documentHeader.workflowDocument.dateLastModified}"
                                                     pattern="MM/dd/yyyy hh:mm a"  />
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
							<th>Requested Date</th>
							<th>Earn Code</th>
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
									<td><joda:format value="${Form.documents[approvedLeave.lmLeaveBlockId].documentHeader.workflowDocument.dateFinalized}"
                                                     pattern="MM/dd/yyyy hh:mm a"  />
									</td>
								</tr>

							</c:forEach>
						</c:if>
					</table>
				</div>
			</div>
			<div id="leave-disapproved-request">
				<h3>
					<a href="#">Disapproved Leave Requests</a>
				</h3>
				<div>
					<table>
						<tr>
							<th>Requested Date</th>
							<th>Earn Code</th>
							<th>Hours</th>
                            <th>Description</th>
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
                                    <td>${disaprovedLeave.description}</td>
									<td>${Form.documents[disaprovedLeave.lmLeaveBlockId].description}</td>
									<td><joda:format value="${Form.documents[disaprovedLeave.lmLeaveBlockId].documentHeader.workflowDocument.dateFinalized}"
                                                     pattern="MM/dd/yyyy hh:mm a"  />
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