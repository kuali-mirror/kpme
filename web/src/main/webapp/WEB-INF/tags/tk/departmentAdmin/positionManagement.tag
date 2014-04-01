<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<div class="portlet">
    <div class="header">Position Management</div>
    <div class="body">
        <ul class="chan">
            <li>
                <a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.position.PositionBo&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                    Position</a>
            </li>        
        </ul>
        <strong>Maintenance</strong>
        <ul class="chan">
            <li>
         		<a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.classification.ClassificationBo&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
         		Position Classification</a>
            </li>
            <li>
         		<a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.core.salarygroup.SalaryGroupBo&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
         		Salary Group</a>
            </li>
            <li>
         		<a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.core.paystep.PayStep&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
         		Pay Step</a>
            </li>
            <li>
         		<a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.core.paygrade.PayGradeBo&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
         		Pay Grade</a>
            </li>                                            
        </ul>
        <strong>Setup Documents</strong>
        <ul class="chan">
            <li>
         		<a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.positionappointment.PositionAppointment&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
         		Position Appointment</a>
            </li>
            <li>
         		<a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.positionResponsibilityOption.PositionResponsibilityOption&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
         		Position Responsibility Option</a>
            </li>      
            <!--  
              <li>
         		<a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.hr.pm.positionresponsibility.PositionResponsibility&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
         		Position Responsibility</a>
            </li>
            -->

             <li>
         		<a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.pstncontracttype.PstnContractType&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
         		Position Contract Type</a>
            </li>
            <li>
         		<a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.positionflag.PositionFlag&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
         		Position Flag</a>
            </li>
            <li>
         		<a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.pstnqlfrtype.PstnQlfrType&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
         		Position Qualifier Type</a>
            </li>
<!--             <li> -->
<%--          		<a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.positiondepartment.PositionDepartment&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/> --%>
<!--          		Position Department</a> -->
<!--             </li>                                                                                  -->
        </ul>
        <strong>Reports, etc.</strong>
        <ul class="chan">
            <li>
                <a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.positionreportgroup.PositionReportGroup&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                Position Reporting Group</a>
            </li>
            <li>
         		<a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.pstnrptgrpsubcat.PositionReportGroupSubCategory&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
         		Position Report Group Sub Category</a>
            </li>
             <li>
                <a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.positionreportsubcat.PositionReportSubCategory&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                Position Report Sub Category</a>
            </li>                               
            <li>
                <a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.positionreportcat.PositionReportCategory&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                Position Report Category</a>
            </li>       
            <li>
                <a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.positionreporttype.PositionReportType&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y">
                Position Report Type</a>
            </li>                  
        </ul>
    </div>
</div>