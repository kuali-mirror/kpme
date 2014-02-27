<%--
 Copyright 2007-2009 The Kuali Foundation
 
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
<%@ include file="/rice-portal/jsp/sys/riceTldHeader.jsp"%>

<jsp:useBean id="form" class="org.kuali.kpme.core.web.KPMEForm" />

<c:if test="${!empty UserSession.loggedInUserPrincipalName}">
    <c:set var="systemAdmin" value='<%=org.kuali.kpme.core.util.HrContext.isSystemAdmin()%>' />
    <c:set var="locationAdmin" value='<%=org.kuali.kpme.tklm.time.util.TkContext.isLocationAdmin()%>' />
    <c:set var="departmentAdmin" value='<%=org.kuali.kpme.tklm.time.util.TkContext.isDepartmentAdmin()%>' />
    <c:set var="globalViewOnly" value='<%=org.kuali.kpme.core.util.HrContext.isGlobalViewOnly()%>' />
    <c:set var="locationViewOnly" value='<%=org.kuali.kpme.tklm.time.util.TkContext.isLocationViewOnly()%>' />
    <c:set var="departmentViewOnly" value='<%=org.kuali.kpme.tklm.time.util.TkContext.isDepartmentViewOnly()%>' />
    <c:set var="approver" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetAnyApprover()%>' />
    <c:set var="payrollProcessor" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetAnyPayrollProcessor()%>' />
    <c:set var="reviewer" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetReviewer()%>' />
    <c:set var="targetActiveEmployee" value='<%=org.kuali.kpme.core.util.HrContext.isTargetActiveEmployee()%>' />
    <c:set var="targetSynchronous" value='<%=org.kuali.kpme.tklm.time.util.TkContext.isTargetSynchronous()%>' />
</c:if>

<channel:portalChannelTop channelTitle="Time and Leave Management" />
<div class="body">
    <ul class="chan">
        <c:if test="${targetSynchronous and form.timeEnabled}">
            <li>
                <a href="Clock.do">Clock</a>
            </li>
        </c:if>
        <c:if test="${form.timeEnabled}">
            <li>
                <a href="TimeDetail.do">Time Detail</a>
            </li>
        </c:if>
        <c:if test="${form.leaveEnabled}">
            <li>
                <a href="LeaveCalendar.do">Leave Calendar</a>
            </li>
        </c:if>

        <li>
            <a href="PersonInfo.do">Person Info</a>
        </li>                        
    </ul>
    <ul class="chan">
        <c:if test="${approver || reviewer || payrollProcessor}">
            <li>
                <a href="TimeApproval.do">Time Approval</a>
            </li>
        </c:if>
        <c:if test="${approver || reviewer || payrollProcessor}">
            <li>
                <a href="LeaveApproval.do">Leave Approval</a>
            </li>
        </c:if>
        <c:if test="${systemAdmin || locationAdmin || departmentAdmin || globalViewOnly || locationViewOnly || departmentViewOnly}">
            <li>
                <a href="DepartmentAdmin.do">Department Admin</a>
            </li>
        </c:if>
	</ul>
</div>
<channel:portalChannelBottom />