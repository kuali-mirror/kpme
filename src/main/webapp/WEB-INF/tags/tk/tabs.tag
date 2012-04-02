<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<div id="tab-section">
    <li id="help" class="ui-state-default ui-corner-top"><a href="Help.do">Help</a></li>
    <c:if test="${Form.user.currentTargetRoles.systemAdmin || Form.user.currentTargetRoles.locationAdmin || Form.user.currentTargetRoles.departmentAdmin
    			|| Form.user.currentTargetRoles.globalViewOnly}">
        <li id="admin" class="ui-state-default ui-corner-top"><a href="Admin.do">Admin</a></li>
    </c:if>
    <c:if test="${Form.user.currentTargetRoles.systemAdmin}">
        <li id="batchJob" class="ui-state-default ui-corner-top"><a href="BatchJob.do">Batch Job</a></li>
    </c:if>
    <li id="personInfo" class="ui-state-default ui-corner-top"><a href="PersonInfo.do">Person Info</a></li>
    <c:if test="${Form.user.currentTargetRoles.timesheetApprover ||  Form.user.currentTargetRoles.systemAdmin || Form.user.currentTargetRoles.timesheetReviewer }">
        <li id="approvals" class="ui-state-default ui-corner-top"><a
                href="TimeApproval.do?methodToCall=loadApprovalTab">Approvals</a></li>
    </c:if>
    <c:if test="${Form.leaveEnabled}">
        <li id="leaveRequest" class="ui-state-default ui-corner-top"><a href="LeaveRequest.do">Leave Request</a></li>
    </c:if>
    <c:if test="${Form.user.currentTargetRoles.activeEmployee}">
        <li id="leaveAccrual" class="ui-state-default ui-corner-top"><a href="TimeOffAccrual.do">Leave Accrual</a></li>
        <c:if test="${Form.leaveEnabled}">
            <li id="leaveCalendar" class="ui-state-default ui-corner-top"><a href="LeaveCalendar.do">Leave Calendar</a>
            </li>
        </c:if>
        <li id="timeDetail" class="ui-state-default ui-corner-top"><a href="TimeDetail.do">Time Detail</a></li>
        <c:if test="${Form.user.currentTargetRoles.synchronous}">
            <li id="clock" class="ui-state-default ui-corner-top"><a href="Clock.do">Clock</a></li>
        </c:if>
    </c:if>
	 <c:if test="${Form.leaveEnabled}">
     	<li id="leaveBlockDisplay" class="ui-state-default ui-corner-top"><a href="LeaveBlockDisplay.do">Leave Block Display</a></li>
     </c:if>
</div>