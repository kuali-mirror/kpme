<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<div id="tab-section">
    <c:if test="${Form.user.currentTargetRoles.systemAdmin || Form.user.currentTargetRoles.locationAdmin || Form.user.currentTargetRoles.departmentAdmin
    			|| Form.user.currentTargetRoles.globalViewOnly}">
        <li id="admin" class="ui-state-default ui-corner-top"><a href="Admin.do">Admin</a></li>
    </c:if>
    <c:if test="${Form.user.currentTargetRoles.systemAdmin}">
    	<li id="batchJob" class="ui-state-default ui-corner-top"><a href="BatchJob.do">Batch Job</a></li>
    </c:if>
    <li id="personInfo" class="ui-state-default ui-corner-top"><a href="PersonInfo.do">Person Info</a></li>
    <c:if test="${Form.user.currentTargetRoles.timesheetApprover ||  Form.user.currentTargetRoles.systemAdmin }">
        <li id="approvals" class="ui-state-default ui-corner-top"><a href="TimeApproval.do">Approvals</a></li>
    </c:if>
    <c:if test="${Form.user.currentTargetRoles.activeEmployee}">
        <li id="leaveAccrual" class="ui-state-default ui-corner-top"><a href="TimeOffAccrual.do">Leave Accrual</a></li>
        <li id="timeDetail" class="ui-state-default ui-corner-top"><a href="TimeDetail.do">Time Detail</a></li>
        <c:if test="${Form.user.currentTargetRoles.synchronous}">
             <li id="clock" class="ui-state-default ui-corner-top"><a href="Clock.do">Clock</a></li>
        </c:if>
    </c:if>
</div>