<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<c:if test="${!empty UserSession.loggedInUserPrincipalName}">
    <c:set var="systemAdmin" value='<%=org.kuali.kpme.core.util.HrContext.isSystemAdmin()%>' />
    <c:set var="locationAdmin" value='<%=org.kuali.kpme.tklm.time.util.TkContext.isLocationAdmin()%>' />
    <c:set var="departmentAdmin" value='<%=org.kuali.kpme.tklm.time.util.TkContext.isDepartmentAdmin()%>' />
    <c:set var="globalViewOnly" value='<%=org.kuali.kpme.core.util.HrContext.isGlobalViewOnly()%>' />
    <c:set var="targetSystemAdmin" value='<%=org.kuali.kpme.core.util.HrContext.isTargetSystemAdmin()%>' />
    <c:set var="targetTimesheetApprover" value='<%=org.kuali.kpme.core.util.HrContext.isTargetAnyApprover()%>' />
    <c:set var="targetTimesheetReviewer" value='<%=org.kuali.kpme.core.util.HrContext.isTargetReviewer()%>' />   
    <c:set var="targetActiveEmployee" value='<%=org.kuali.kpme.core.util.HrContext.isTargetActiveEmployee()%>' />
    <c:set var="targetSynchronous" value='<%=org.kuali.kpme.tklm.time.util.TkContext.isTargetSynchronous()%>' />
</c:if>

<div id="tab-section">
    <li id="help" class="ui-state-default ui-corner-top"><a href="Help.do">Help</a></li>
    <c:if test="${systemAdmin || locationAdmin || departmentAdmin || globalViewOnly}">
        <li id="departmentAdmin" class="ui-state-default ui-corner-top"><a href="DepartmentAdmin.do">Department Admin</a></li>
    </c:if>
    <li id="personInfo" class="ui-state-default ui-corner-top"><a href="PersonInfo.do">Person Info</a></li>
    <c:if test="${targetTimesheetApprover || targetTimesheetReviewer}">
        <li id="approvals" class="ui-state-default ui-corner-top"><a
                href="TimeApproval.do?methodToCall=loadApprovalTab">Time Approval</a></li>
    </c:if>
    <c:if test="${targetTimesheetApprover || targetTimesheetReviewer}">
    	<li id="leaveApprovals" class="ui-state-default ui-corner-top"><a
            href="LeaveApproval.do?methodToCall=loadApprovalTab">Leave Approval</a></li>
    </c:if>
    <c:if test="${targetActiveEmployee}">
        <c:if test="${Form.leaveEnabled}">
            <li id="leaveCalendar" class="ui-state-default ui-corner-top"><a href="LeaveCalendar.do">Leave Calendar</a></li>
        </c:if>
        <c:if test="${Form.timeEnabled}">
        	<li id="timeDetail" class="ui-state-default ui-corner-top"><a href="TimeDetail.do">Time Detail</a></li>
        </c:if>
        <c:if test="${targetSynchronous and Form.timeEnabled}">
            <li id="clock" class="ui-state-default ui-corner-top"><a href="Clock.do">Clock</a></li>
        </c:if>
    </c:if>
	 
</div>