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

<c:set var="systemAdmin" value='<%=org.kuali.hr.time.roles.TkUserRoles.getUserRoles(org.kuali.rice.krad.util.GlobalVariables.getUserSession().getPrincipalId()).isSystemAdmin()%>' />
<c:set var="globalViewOnly" value='<%=org.kuali.hr.time.roles.TkUserRoles.getUserRoles(org.kuali.rice.krad.util.GlobalVariables.getUserSession().getPrincipalId()).isGlobalViewOnly()%>' />

<channel:portalChannelTop channelTitle="HR/Payroll" />
<div class="body">
    <ul class="chan">
        <c:if test="${systemAdmin || globalViewOnly}">
            <li>
                <portal:portalLink displayTitle="true" title="Assignment"
                                   url="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.assignment.Assignment&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
            </li>
            <li>
                <portal:portalLink displayTitle="true" title="Calendar"
                                   url="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.calendar.Calendar&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
            </li>
            <li>
                <portal:portalLink displayTitle="true" title="Calendar Entry"
                                   url="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.calendar.CalendarEntries&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
            </li>
            <li>
                <portal:portalLink displayTitle="true" title="Earn Code"
                                   url="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.earncode.EarnCode&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
            </li>
            <li>
                <portal:portalLink displayTitle="true" title="Earn Code Group"
                                   url="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.earncodegroup.EarnCodeGroup&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
            </li>
            <li>
                <portal:portalLink displayTitle="true" title="Earn Code Security"
                                   url="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.lm.earncodesec.EarnCodeSecurity&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
            </li>
            <li>
                <portal:portalLink displayTitle="true" title="Job"
                                   url="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.job.Job&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
            </li>
            <li>
                <portal:portalLink displayTitle="true" title="Pay Grade"
                                   url="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.paygrade.PayGrade&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
            </li>
            <li>
                <portal:portalLink displayTitle="true" title="Pay Type"
                                   url="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.paytype.PayType&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
            </li>
            <li>
                <portal:portalLink displayTitle="true" title="Position"
                                   url="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.position.Position&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
            </li>
            <li>
                <portal:portalLink displayTitle="true" title="Principal HR Attributes"
                                   url="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.principal.PrincipalHRAttributes&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
            </li>
            <li>
                <portal:portalLink displayTitle="true" title="Salary Group"
                                   url="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.salgroup.SalGroup&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
            </li>
            <li>
                <portal:portalLink displayTitle="true" title="User Role Maintenance"
                                   url="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.roles.TkRoleGroup&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
            </li>
        </c:if>
        <li>
            <portal:portalLink displayTitle="true" title="WorkArea Maintenance"
                               url="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.workarea.WorkArea&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
	</ul>
</div>
<channel:portalChannelBottom />