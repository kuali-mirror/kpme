<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<div class="portlet">
    <div class="header">Time Keeping</div>
    <div class="body">
        <ul>
            <li>
                <a href="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.tklm.time.rules.clocklocation.ClockLocationRule&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Clock Location Rule</a>
            </li>
            <li>
                <a href="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.tklm.time.rules.overtime.daily.DailyOvertimeRule&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Daily OverTime Rule</a>
            </li>
            <li>
                <a href="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.tklm.time.rules.lunch.department.DeptLunchRule&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Department Lunch Deduction Rule</a>
            </li>
            <li>
                <a href="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.tklm.time.rules.graceperiod.GracePeriodRule&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Grace Period Rule</a>
            </li>
            <li>
                <a href="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.tklm.time.rules.shiftdifferential.ShiftDifferentialRule&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Shift Differential Rule</a>
            </li>
            <li>
                <a href="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.tklm.time.rules.lunch.sys.SystemLunchRule&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">System Lunch Rule</a>
            </li>
            <li>
                <a href="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.tklm.time.rules.timecollection.TimeCollectionRule&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Time Collection Rule</a>
            </li>
            <li>
                <a href="${ConfigProperties.application.url}/kr/maintenance.do?businessObjectClassName=org.kuali.kpme.tklm.time.rules.overtime.weekly.WeeklyOvertimeRuleGroup&tkWeeklyOvertimeRuleGroupId=1&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&methodToCall=edit">Weekly Overtime Rule</a>
            </li>
        </ul>
    </div>
</div>