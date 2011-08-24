<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>
<c:set var="Form" value="${PersonInfoActionForm}" scope="request"/>

<tk:tkHeader tabId="personInfo">
    <div class="person-detail">
        <div id="person-detail-accordion">
            <h3><a href="#">Your Person Details:</a></h3>

            <div>
                <table>
                    <tr>
                        <td>Principal Id:</td>
                        <td>${Form.principalId}</td>
                    </tr>
                    <tr>
                        <td>Principal Name:</td>
                        <td>${Form.principalName}</td>
                    </tr>
                </table>
            </div>
            <h3><a href="#">Your Jobs:</a></h3>

            <div>
                <table>
                    <c:forEach var="job" items="${Form.jobs}">
                        <tr>
                            <td>Job Number</td>
                            <td>Department</td>
                            <td>Salary Group</td>
                            <td>Pay Grade</td>
                            <td>Standard Hours</td>
                            <td>Pay Type</td>
                            <td>Location</td>
                            <td>Compensation Rate</td>
                            <td>Effective Date</td>
                        </tr>
                        <tr>
                            <td>${job.jobNumber}</td>
                            <td>${job.dept }</td>
                            <td>${job.tkSalGroup }</td>
                            <td>${job.payGrade }</td>
                            <td>${job.standardHours }</td>
                            <td>${job.payTypeObj.descr}</td>
                            <td>${job.location }</td>
                            <td>${job.compRate }</td>
                            <td>${job.effectiveDate }</td>
                        </tr>
                        <c:if test="${fn:length(Form.jobNumberToListAssignments) > 0}">
                            <tr>
                                <td colspan="9" align="center">Assignment(s)</td>
                            </tr>
                            <c:forEach var="assignment" items="${Form.jobNumberToListAssignments[job.jobNumber]}">
                                <tr>
                                    <td colspan="9">Work Area / Task :
                                            ${assignment.workArea} - ${assignment.workAreaObj.description}
                                        / ${assignment.task}<br/>
                                        Approver(s) :
                                        <c:forEach var="approver"
                                                   items="${Form.workAreaToApproverPerson[assignment.workArea]}">
                                            Name: <a href="mailto:${approver.emailAddress }">${approver.name}</a> ;
                                            Phone Number: ${approver.phoneNumber }<br/>
                                        </c:forEach>
                                        <br/>
                                        Organization Admin(s) :
                                        <c:forEach var="orgAdmin" items="${Form.deptToOrgAdmin[assignment.dept]}">
                                            Name: <a href="mailto:${orgAdmin.emailAddress }">${orgAdmin.name}</a> ;
                                            Phone Number: ${orgAdmin.phoneNumber }<br/>
                                        </c:forEach>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>
                    </c:forEach>
                </table>
            </div>
            <h3><a href="#">Your Roles:</a></h3>

            <div>
                <table>
                    <tr>
                        <td>Approver for Work Area(s)</td>
                        <td>
                            <c:forEach var="workArea" items="${Form.approverWorkAreas}" varStatus="row">
                                ${workArea}
                                <c:if test="${!row.last}">,</c:if>
                            </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td>Reviewer for Work Area(s)</td>
                        <td>
                            <c:forEach var="workArea" items="${Form.reviewerWorkAreas}" varStatus="row">
                                ${workArea}
                                <c:if test="${!row.last}">,</c:if>
                            </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td>Admin for Department(s):</td>
                        <td>
                            <c:forEach var="dept" items="${Form.deptAdminDepts}" varStatus="row">
                                ${dept}
                                <c:if test="${!row.last}">,</c:if>
                            </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td>Admin for Location(s):</td>
                        <td>
                            <c:forEach var="dept" items="${Form.locationAdminDepts}" varStatus="row">
                                ${dept}
                                <c:if test="${!row.last}">,</c:if>
                            </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td>View Only for Department(s):</td>
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
            <%-- end of person-detail-accordion --%>
    </div>
    <%-- end of person-detail --%>
    <%--
    <c:forEach var="job" items="${Form.jobNumberToListAssignments}">
        <c:forEach var="assignment" items="${job.value}">
            <h3><a href="#">Job Number: ${assignment.jobNumber }</a></h3>
            <div>
                <p class="job">
                <b>Job Details : </b>
                    <ul>
                        <li>Network ID: ${assignment.principalId } </li>
                        <li>Department: ${assignment.job.dept }</li>
                        <li>Salary Group: ${assignment.job.tkSalGroup }</li>
                        <li>Pay Grade: ${assignment.job.payGrade }</li>
                        <li>Standard Hours: ${assignment.job.standardHours }</li>
                        <li>Pay Type: ${assignment.job.payTypeObj.descr}</li>
                        <li>Location: ${assignment.job.location }</li>
                        <li>Compensation Rate: ${assignment.job.compRate }</li>
                        <li>Effective Date: ${assignment.job.effectiveDate }</li>
                    </ul>
                </p>
                <p class="assignment">
                    <b>Assignment : </b>
                    <ul>
                        <li>Work Area - Description: ${assignment.workArea} - ${assignment.workAreaObj.description } </li>
                        <li>Task: ${assignment.task}</li>
                    </ul>
                </p>
                <p class="approver">
                    <b>Approvers : </b>
                    <ul>
                    <c:forEach var="workAreaToApprover" items="${Form.workAreaToApproverPerson }">
                        <c:forEach var="approver" items="${workAreaToApprover.value}">
                                <li>Name: <a href="mailto:${approver.emailAddress }">${approver.name}</a> ; Phone Number: ${approver.phoneNumber }</li>
                        </c:forEach>
                    </c:forEach>
                    </ul>
                </p>
                <p class="orgAdmin">
                    <b>Organization Admin : </b>
                    <ul>
                    <c:forEach var="deptToOrgAdmin" items="${Form.deptToOrgAdmin }">
                        <c:forEach var="orgAdmin" items="${deptToOrgAdmin.value}">
                            <c:set var="person" value="${Form.principalIdToPerson[orgAdmin.principalId]}"/>
                                <li>Name: <a href="mailto:${person.emailAddress }">${person.name }</a> ; Phone Number: ${person.phoneNumber }</li>
                        </c:forEach>
                    </c:forEach>
                    </ul>
                </p>
            </div>
        </c:forEach>
    </c:forEach>
    --%>

</tk:tkHeader>
