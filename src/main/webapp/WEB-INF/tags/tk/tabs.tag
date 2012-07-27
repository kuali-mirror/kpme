<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<c:if test="${!empty UserSession.loggedInUserPrincipalName}">
    <c:set var="targetSystemAdmin" value='<%=org.kuali.hr.time.util.TKUser.getCurrentTargetRoles().isSystemAdmin()%>' />
    <c:set var="targetTimesheetApprover" value='<%=org.kuali.hr.time.util.TKUser.getCurrentTargetRoles().isTimesheetApprover()%>' />
    <c:set var="targetTimesheetReviewer" value='<%=org.kuali.hr.time.util.TKUser.getCurrentTargetRoles().isTimesheetReviewer()%>' />
    <c:set var="targetActiveEmployee" value='<%=org.kuali.hr.time.util.TKUser.getCurrentTargetRoles().isActiveEmployee()%>' />
    <c:set var="targetSynchronous" value='<%=org.kuali.hr.time.util.TKUser.getCurrentTargetRoles().isSynchronous()%>' />
</c:if>

<div id="tab-section">
    <li id="help" class="ui-state-default ui-corner-top"><a href="Help.do">Help</a></li>
    <li id="personInfo" class="ui-state-default ui-corner-top"><a href="PersonInfo.do">Person Info</a></li>
    <c:if test="${targetSystemAdmin || targetTimesheetApprover || targetTimesheetReviewer}">
        <li id="approvals" class="ui-state-default ui-corner-top"><a
                href="TimeApproval.do?methodToCall=loadApprovalTab">Approvals</a></li>
    </c:if>
    <c:if test="${Form.leaveEnabled}">
        <li id="leaveBlockDisplay" class="ui-state-default ui-corner-top"><a href="LeaveBlockDisplay.do">Leave Ledger</a></li>
        <li id="leaveRequest" class="ui-state-default ui-corner-top"><a href="LeaveRequest.do">Leave Request</a></li>
    </c:if>
    
    <c:if test="${targetActiveEmployee}">
        <li id="leaveAccrual" class="ui-state-default ui-corner-top"><a href="TimeOffAccrual.do">Leave Accrual</a></li>
        <c:if test="${Form.leaveEnabled}">
            <li id="leaveCalendar" class="ui-state-default ui-corner-top"><a href="LeaveCalendar.do">Leave Calendar</a>
            </li>
        </c:if>
        <c:if test="${Form.timeEnabled}">
        	<li id="timeDetail" class="ui-state-default ui-corner-top"><a href="TimeDetail.do">Time Detail</a></li>
        </c:if>
        <c:if test="${targetSynchronous and Form.timeEnabled}">
            <li id="clock" class="ui-state-default ui-corner-top"><a href="Clock.do">Clock</a></li>
        </c:if>
    </c:if>
	 
</div>