<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<div class="portlet">
    <div class="header">Leave Maintenance</div>
    <div class="body">
        <ul>
            <li>
                <a href="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.tklm.leave.adjustment.LeaveAdjustment&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Leave Adjustment</a>
            </li>
            <li>
            	<a href="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.tklm.leave.transfer.BalanceTransfer&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Balance Transfer</a>
        	</li>
            <li>
                <a href="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.tklm.leave.timeoff.SystemScheduledTimeOff&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">System Scheduled Time Off</a>
            </li>
            <li>
                <a href="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.tklm.leave.payout.LeavePayout&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Leave Payout</a>
            </li>
        </ul>
    </div>
</div>