<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<div class="portlet">
    <div class="header">Position Management</div>
    <div class="body">
        <ul>
            <li>
         		<a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.core.bo.paystep.PayStep&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
         		Pay Step</a>
            </li>
            <li>
         		<a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.positionflag.PositionFlag&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
         		Position Flag</a>
            </li>
            
            <li>
         		<a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.pstnqlfrtype.PstnQlfrType&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
         		Position Qualifier Type</a>
            </li>
           
            <li>
         		<a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.pstnqlfctnvl.PositionQualificationValue&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
         		Position Qualification Value</a>
            </li>
            <li>
                <a href="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.pm.positionreporttype.PositionReportType&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                Position Report Type</a>
            </li>
            <li>
                <a href="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.pm.positionreportgroup.PositionReportGroup&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                Position Reporting Group</a>
            </li>
            <li>
                <a href="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.pm.positionreportcat.PositionReportCategory&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                Position Report Category</a>
            </li>
            <li>
                <a href="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.pm.positionreportsubcat.PositionReportSubCategory&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                Position Report Sub Category</a>
            </li>
            <li>
         		<a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.pstnrptgrpsubcat.PositionReportGroupSubCategory&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
         		Position Report Group Sub Category</a>
            </li>
            <li>
         		<a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.core.bo.salarygroup.SalGroup&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
         		Salary Group</a>
            </li>
        </ul>
    </div>
</div>