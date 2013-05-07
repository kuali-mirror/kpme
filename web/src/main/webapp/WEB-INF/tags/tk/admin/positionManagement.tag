<%--
 Copyright 2007-2009 The Kuali Foundation
 
 Licensed under the Educational Community License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
 http://www.opensource.org/licenses/ecl2.php
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
--%>
<%@ include file="/rice-portal/jsp/sys/riceTldHeader.jsp"%>

<channel:portalChannelTop channelTitle="Position Management" />
<div class="body">
    <ul class="chan">
        <li>
         	<portal:portalLink displayTitle="true" title="Pay Step" 
         			url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.core.bo.paystep.PayStep&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
        </li> 
        <li>
         	<portal:portalLink displayTitle="true" title="Position Department" 
         			url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.positiondepartment.PositionDepartment&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
        </li>
        <li>
         	<portal:portalLink displayTitle="true" title="Position Department Affiliation" 
         			url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.positiondepartmentaffiliation.PositionDepartmentAffiliation&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
        </li>
        <li>
         	<portal:portalLink displayTitle="true" title="Position Flag" 
         			url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.positionflag.PositionFlag&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
        </li>         
        <li>
         	<portal:portalLink displayTitle="true" title="Position Qualifier Type" 
         			url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.pstnqlfrtype.PstnQlfrType&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
        </li>
        <li>
         	<portal:portalLink displayTitle="true" title="Position Classification" 
         			url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.hr.pm.classification.Classification&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
        </li>	
   <%--
         <li>
         	<portal:portalLink displayTitle="true" title="Position Qualification Value" 
         			url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.pstnqlfctnvl.PositionQualificationValue&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
        </li>
  --%>
		<li>
            <portal:portalLink displayTitle="true" title="Position Report Type"
                   url="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.pm.positionreporttype.PositionReportType&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
        <li>
         	<portal:portalLink displayTitle="true" title="Position Contract Type" 
         			url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.pstncontracttype.PstnContractType&returnLocation=${ConfigProperties.application.url}/DepartmentAdmin.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
        </li>    
            <portal:portalLink displayTitle="true" title="Position Reporting Group"
                   url="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.pm.positionreportgroup.PositionReportGroup&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
        <li>
            <portal:portalLink displayTitle="true" title="Position Report Category"
                   url="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.pm.positionreportcat.PositionReportCategory&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
        <li>
            <portal:portalLink displayTitle="true" title="Position Report Sub Category"
                   url="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.pm.positionreportsubcat.PositionReportSubCategory&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
        <li>
         	<portal:portalLink displayTitle="true" title="Position Reporting Group Sub Category" 
         			url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.pstnrptgrpsubcat.PositionReportGroupSubCategory&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
        </li>
        <li>
         	<portal:portalLink displayTitle="true" title="Position Type" 
         			url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.positiontype.PositionType&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
        </li>        
        <li>
         	<portal:portalLink displayTitle="true" title="Salary Group" 
         			url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.core.bo.salarygroup.SalaryGroup&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
        </li>
        <li>
         	<portal:portalLink displayTitle="true" title="Position Appointment" 
         			url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.positionappointment.PositionAppointment&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
        </li>
	</ul>
</div>
<channel:portalChannelBottom />