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

<c:if test="${!empty UserSession.loggedInUserPrincipalName}">
    <c:set var="systemAdmin" value='<%=org.kuali.kpme.core.util.HrContext.isSystemAdmin()%>' />
    <c:set var="globalViewOnly" value='<%=org.kuali.kpme.core.util.HrContext.isGlobalViewOnly()%>' />

    <c:set var="positionSystemViewOnly" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetPositionSystemViewOnly()%>' />

    <c:set var="locationAdmin" value='<%=org.kuali.kpme.tklm.time.util.TkContext.isLocationAdmin()%>' />
    <c:set var="locationViewOnly" value='<%=org.kuali.kpme.tklm.time.util.TkContext.isLocationViewOnly()%>' />


    <c:set var="KOHRAcademicHrAdmin" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetKOHRAcademicHrAdmin()%>' />

    <c:set var="KOHRInstitutionAdmin" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetKOHRInstitutionAdmin()%>' />

    <c:set var="KOHRInstitutionViewOnly" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetKOHRInstitutionViewOnly()%>' />
    <c:set var="KOHRLocationAdmin" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetKOHRLocationAdmin()%>' />
    <c:set var="KOHRLocationViewOnly" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetKOHRLocationViewOnly()%>' />
    <c:set var="KOHRLocationAdmin" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetKOHRLocationAdmin()%>' />
    <c:set var="KOHROrgAdmin" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetKOHROrgAdmin()%>' />
    <c:set var="KOHROrgViewOnly" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetKOHROrgViewOnly()%>' />
    <c:set var="HRDepartmentAdmin" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetHRDepartmentAdmin()%>' />
    <c:set var="HRDepartmentViewOnly" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetHRDepartmentViewOnly()%>' />
    <c:set var="HRInstitutionApprover" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetHRInstitutionApprover()%>' />
    <c:set var="academicHRInstitutionApprover" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetAcademicHRInstitutionApprover()%>' />
    <c:set var="budgetApprover" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetBudgetApprover()%>' />
    <c:set var="payrollApprover" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetPayrollApprover()%>' />
    <c:set var="HRlocationApprover" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetHRLocationApprover()%>' />
    <c:set var="academicHRLocationApprover" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetAcademicHRLocationApprover()%>' />
    <c:set var="fiscalLocationApprover" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetFiscalLocationApprover()%>' />
    <c:set var="HROrgApprover" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetHROrgApprover()%>' />
    <c:set var="fiscalOrgApprover" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetFiscalOrgApprover()%>' />
    <c:set var="departmentApprover" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetDepartmentApprover()%>' />
    <c:set var="fiscalDeptApprover" value='<%=org.kuali.kpme.core.util.HrContext.isUserOrTargetFiscalDepartmentApprover()%>' />
    <c:set var="locationAdmin" value='<%=org.kuali.kpme.tklm.time.util.TkContext.isLocationAdmin()%>' />
    <c:set var="locationViewOnly" value='<%=org.kuali.kpme.tklm.time.util.TkContext.isLocationViewOnly()%>' />
</c:if>

<c:set var = "setupAndReports" value = "false"/>
<c:if test="${systemAdmin || globalViewOnly  || positionSystemViewOnly || locationAdmin || locationViewOnly || KOHRInstitutionAdmin || KOHRAcademicHrAdmin || KOHRInstitutionViewOnly || KOHRLocationAdmin || KOHRLocationViewOnly ||
    	KOHRLocationAdmin ||  HRInstitutionApprover ||	academicHRInstitutionApprover || budgetApprover || payrollApprover || HRlocationApprover || academicHRLocationApprover || fiscalLocationApprover || HROrgApprover || fiscalOrgApprover || departmentApprover || fiscalDeptApprover || locationAdmin || locationViewOnly}">

    <c:set var = "setupAndReports" value = "true"/>

</c:if>

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
<c:if test="${setupAndReports}">

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
    </c:if>
</div>
<channel:portalChannelBottom />