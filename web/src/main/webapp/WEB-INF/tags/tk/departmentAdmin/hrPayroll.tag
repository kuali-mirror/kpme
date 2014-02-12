<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<div class="portlet">
    <div class="header">HR/Payroll</div>
    <div class="body">
        <ul>
            <li>
                <a href="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.assignment.Assignment&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Assignment</a>
            </li>
            <li>
                <a href="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.calendar.entry.CalendarEntry&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Calendar Entry</a>
            </li>
            <li>
                <a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.core.department.Department&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Department</a>
            </li>
            <li>
                <a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.core.departmentaffiliation.DepartmentAffiliation&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>Department Affiliation</a>
            </li>
            <li>
                <a href="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.earncode.EarnCode&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Earn Code</a>
            </li>
            <li>
                <a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.core.earncode.security.EarnCodeSecurity&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Earn Code Security</a>
            </li>
            <li>
                <a href="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.job.Job&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Job</a>
            </li>                        
            <li>
                <a href="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.paytype.PayType&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Pay Type</a>
            </li>
            <li>
                <a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.core.position.PositionBase&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Position Base</a>
            </li>
            <li>
                <a href="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.principal.PrincipalHRAttributes&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Principal HR Attributes</a>
            </li>
            <li>
                <a href="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.workarea.WorkArea&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">Work Area</a>
            </li>               
        </ul>
    </div>
</div>