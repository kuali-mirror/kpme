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
<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>
<c:set var="Form" value="${PersonInfoActionForm}" scope="request"/>

<tk:tkHeader tabId="personInfo">
    <div class="person-detail">
        <div id="person-detail-accordion">
            <h3><a href="#">Your Person Details:</a></h3>

            <div>
                <table>
                    <tr>
                        <th>Principal Id</th>
                        <th>Principal Name</th>
                        <th>Name</th>
                        <th>Service Date</th>
                    </tr>
                    <tr>
                        <td>${Form.principalId}</td>
                        <td>${Form.principalName}</td>
                        <td>${Form.name}</td>
                        <td>${Form.serviceDate}</td>
                    </tr>
                </table>
                <br>
                <table>
                	<tr>
          				<th>Accrual Category</th>
          				<th>Current Rate</th>
          				<th>Unit of Time</th>
          				<th>Accrual Earn Interval</th>
          			</tr>
          			<c:forEach var="accrualCategory" items="${Form.accrualCategories}">
          				<tr>
          					<td>${accrualCategory.accrualCategory} - ${accrualCategory.descr}</td>
          					<td>${Form.accrualCategoryRates[accrualCategory.accrualCategory]}</td>
          					<td>${Form.unitOfTime[accrualCategory.accrualCategory]}</td>
          					<td>${Form.accrualEarnIntervals[accrualCategory.accrualCategory]}</td>
          				</tr>
          			</c:forEach>
                </table>
            </div>
            
            <h3><a href="#">Your Jobs:</a></h3>

            <div>
                <table>
                    <c:forEach var="job" items="${Form.jobs}">
                        <tr>
                            <th>Job Number</th>
                            <th>Department</th>
                            <th>Salary Group</th>
                            <th>Pay Grade</th>
                            <th>Standard Hours</th>
                            <th>Pay Type</th>
                            <th>Location</th>
                            <th>Compensation Rate</th>
                            <th>Effective Date</th>
                            <th>Leave Eligible</th>
                            <th>FTE</th>
                        </tr>
                        <tr>
                            <td>${job.jobNumber}</td>
                            <td>${job.dept }</td>
                            <td>${job.hrSalGroup }</td>
                            <td>${job.payGrade }</td>
                            <td>${job.standardHours }</td>
                            <td>${job.payTypeObj.descr}</td>
                            <td>${job.location }</td>
                            <td><fmt:formatNumber value="${job.compRate }" type="currency"/></td>
                            <td>${job.effectiveDate }</td>
                            
                            <c:if test="${job.eligibleForLeave == false}"> 
                            	<td>No</td>
                            </c:if>
                            <c:if test="${job.eligibleForLeave == true}"> 
                            	<td>Yes</td>
                            </c:if>
                            <td>${job.fte }</td>
                        </tr>
                        <c:if test="${fn:length(Form.jobNumberToListAssignments) > 0}">
                            <tr>
                                <td colspan="11" align="center">Assignment(s)</td>
                            </tr>
                            <c:forEach var="assignment" items="${Form.jobNumberToListAssignments[job.jobNumber]}">
                                <tr>
                                    <td colspan="11">
                                        <p>
                                            <strong>Work Area / Task</strong> :
                                                ${assignment.workArea} - ${assignment.workAreaObj.description}
                                            / ${assignment.task}
                                        </p>

                                        <p>
                                            <strong>Approver(s)</strong> : <br/>
                                            <c:forEach var="approver"
                                                       items="${Form.workAreaToApproverPerson[assignment.workArea]}">
                                                <a href="mailto:${approver.emailAddress }">${approver.name}</a>
                                                <c:if test="${approver.phoneNumber ne ''}"> / ${approver.phoneNumber }</c:if>
                                                <br/>
                                            </c:forEach>
                                        </p>

                                        <p>
                                            <strong>Department Admin(s)</strong> : <br/>
                                            <c:forEach var="deptAdmin"
                                                       items="${Form.deptToDeptAdminPerson[assignment.dept]}">
                                                <a href="mailto:${deptAdmin.emailAddress }">${deptAdmin.name}</a>
                                                <c:if test="${deptAdmin.phoneNumber ne ''}"> / ${deptAdmin.phoneNumber }</c:if>
                                                <br/>
                                            </c:forEach>
                                        </p>

                                        <p>
                                            <strong>Payroll Processor(s)</strong> : <br/>
                                            <c:forEach var="payrollProcessor"
                                                       items="${Form.deptToPayrollPerson[assignment.dept]}">
                                                <a href="mailto:${payrollProcessor.emailAddress }">${payrollProcessor.name}</a>
                                                <c:if test="${payrollProcessor.phoneNumber ne ''}"> / ${payrollProcessor.phoneNumber }</c:if>
                                                <br/>
                                            </c:forEach>
                                        </p>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>
                    </c:forEach>
                </table>
            </div>
            <h3><a href="#">Your Roles:</a></h3>

            <div>
                <table class="roles">
                    <tr>
                        <th>Approver for Work Area(s) : </th>
                        <td>
                            <c:forEach var="workArea" items="${Form.approverWorkAreas}" varStatus="row">
                                ${workArea}
                                <c:if test="${!row.last}">,</c:if>
                            </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <th>Reviewer for Work Area(s) : </th>
                        <td>
                            <c:forEach var="workArea" items="${Form.reviewerWorkAreas}" varStatus="row">
                                ${workArea}
                                <c:if test="${!row.last}">,</c:if>
                            </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <th>Admin for Department(s) : </th>
                        <td>
                            <c:forEach var="dept" items="${Form.deptAdminDepts}" varStatus="row">
                                ${dept}
                                <c:if test="${!row.last}">,</c:if>
                            </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <th>Admin for Location(s) : </th>
                        <td>
                            <c:forEach var="dept" items="${Form.locationAdminDepts}" varStatus="row">
                                ${dept}
                                <c:if test="${!row.last}">,</c:if>
                            </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <th>View Only for Department(s) : </th>
                        <td>
                            <c:forEach var="dept" items="${Form.deptViewOnlyDepts}" varStatus="row">
                                ${dept}
                                <c:if test="${!row.last}">,</c:if>
                            </c:forEach>
                        </td>
                    </tr>
                </table>

            </div>
        </div>
    </div>
</tk:tkHeader>
