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
    <strong>Maintenance</strong>
    <ul class="chan">
        <li>
         	<portal:portalLink displayTitle="true" title="Position Classification" 
         			url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.classification.ClassificationBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
        </li>
        <li>
         	<portal:portalLink displayTitle="true" title="Pay Step" 
         			url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.core.paystep.PayStepBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
        </li>
        <li>
            <portal:portalLink displayTitle="true" title="Pay Grade"
                               url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.core.paygrade.PayGradeBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
        </li>
        <li>
         	<portal:portalLink displayTitle="true" title="Salary Group" 
         			url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.core.salarygroup.SalaryGroupBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
        </li>
<!--         <li> -->
<%--          	<portal:portalLink displayTitle="true" title="Position Department"  --%>
<%--          			url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.positiondepartment.PositionDepartment&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/> --%>
<!--         </li>                                             -->
    </ul>
    <strong>Setup Documents</strong>
    <ul class="chan">
         <li>
         	<portal:portalLink displayTitle="true" title="Position Appointment" 
         			url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.positionappointment.PositionAppointmentBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
        </li>
        <li>
         	<portal:portalLink displayTitle="true" title="Position Responsibility Option" 
         			url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.positionResponsibilityOption.PositionResponsibilityOptionBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
        </li>
        <!--  
          <li>
         	<portal:portalLink displayTitle="true" title="Position Responsibilty" 
         			url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.hr.pm.positionresponsibility.PositionResponsibility&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
        </li>  
        -->         

        <li>
         	<portal:portalLink displayTitle="true" title="Position Contract Type" 
         			url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.pstncontracttype.PstnContractTypeBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
        </li>
        <li>
         	<portal:portalLink displayTitle="true" title="Position Type" 
         			url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.positiontype.PositionTypeBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
        </li>
        <li>
         	<portal:portalLink displayTitle="true" title="Position Flag" 
         			url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.positionflag.PositionFlagBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
        </li>
        <li>
         	<portal:portalLink displayTitle="true" title="Position Qualifier Type" 
         			url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.pstnqlfrtype.PstnQlfrTypeBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
        </li>                                                                
    </ul>
    <strong>Reports, etc.</strong>
    <ul class="chan">
        <li>  
            <portal:portalLink displayTitle="true" title="Position Report Group"
                   url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.positionreportgroup.PositionReportGroupBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
        <li>
         	<portal:portalLink displayTitle="true" title="Position Report Group Sub Category" 
         			url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.pstnrptgrpsubcat.PositionReportGroupSubCategoryBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
        </li>
        <li>
            <portal:portalLink displayTitle="true" title="Position Report Sub Category"
                   url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.positionreportsubcat.PositionReportSubCategoryBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
        <li>
            <portal:portalLink displayTitle="true" title="Position Report Category"
                   url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.positionreportcat.PositionReportCategoryBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
        <li>
            <portal:portalLink displayTitle="true" title="Position Report Type"
                   url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.positionreporttype.PositionReportTypeBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>        
                                    
    </ul>
</div>
<channel:portalChannelBottom />