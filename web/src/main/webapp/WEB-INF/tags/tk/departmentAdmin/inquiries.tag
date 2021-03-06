<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<div class="portlet">
    <div class="header">Lookups</div>
    <div class="body">
        <ul>
            <li>
                <a href="${ConfigProperties.kew.url}/DocumentSearch.do?documentTypeName=TimesheetDocument&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&hideReturnLink=true&docFormKey=88888888&active=Y">
                    Timesheet
                </a>

            </li>
            <li>
                <a href="${ConfigProperties.kew.url}/DocumentSearch.do?documentTypeName=LeaveCalendarDocument&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&hideReturnLink=true&docFormKey=88888888&active=Y">
                    Leave Calendar
                </a>
            </li>
            <li>
                <a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.tklm.time.clocklog.ClockLogBo&showMaintenanceLinks=true&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&hideReturnLink=true&docFormKey=88888888&active=Y">
                    Clock Log
                </a>
            </li>
	        <li>
	            <a href="${ConfigProperties.kew.url}/DocumentSearch.do?documentTypeName=MissedPunchDocumentType&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&hideReturnLink=true&docFormKey=88888888&active=Y">
                    Missed Punch
                </a>
	        </li>
            <li>
                <a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.tklm.time.timeblock.TimeBlockBo&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                    Time Block
                </a>
            </li>
            <li>
                <a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.tklm.time.timeblock.TimeBlockHistory&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                    Time Block History
                </a>
            </li>
             <li>
                <a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.tklm.leave.block.LeaveBlockBo&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                    Leave Block
                </a>
            </li>
             <li>
                <a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.tklm.leave.block.LeaveBlockHistory&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                    Leave Block History
                </a>
            </li>
             

        </ul>
    </div>
</div>