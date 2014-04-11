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

<channel:portalChannelTop channelTitle="HR/Payroll" />
 <c:set var="locationViewOnly" value='<%=org.kuali.kpme.core.util.HrContext.isPositionModuleEnabled()%>' />
<div class="body">
    <strong> </strong>
    <ul class="chan">
        <li>
            <portal:portalLink displayTitle="true" title="Assignment"
                               url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.core.assignment.AssignmentBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
        <li>
            <portal:portalLink displayTitle="true" title="Job"
                               url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.core.job.JobBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
        </li>
        <li>
            <portal:portalLink displayTitle="true" title="Principal HR Attributes"
                               url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.core.principal.PrincipalHRAttributes&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
        </li>
    </ul>
    <strong> </strong>
    <ul class="chan">
        <li>
            <portal:portalLink displayTitle="true" title="Pay Type"
                               url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.core.paytype.PayTypeBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
        <li>
            <portal:portalLink displayTitle="true" title="Earn Code Security"
                               url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.core.earncode.security.EarnCodeSecurity&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
        <li>
            <portal:portalLink displayTitle="true" title="Earn Code Group"
                               url="${ConfigProperties.application.url}/kr-krad/lookup.do?methodToCall=start&dataObjectClassName=org.kuali.kpme.core.earncode.group.EarnCodeGroup&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
        <li>
            <portal:portalLink displayTitle="true" title="Earn Code"
                               url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.core.earncode.EarnCodeBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
        <li>
            <portal:portalLink displayTitle="true" title="Calendar Entry"
                               url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.core.calendar.entry.CalendarEntryBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
        <li>
            <portal:portalLink displayTitle="true" title="Calendar"
                               url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.core.calendar.CalendarBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
    </ul>
    <strong> </strong>
    <ul class="chan">
        <li>
            <portal:portalLink displayTitle="true" title="Group Key"
                               url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.core.groupkey.HrGroupKeyBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
        <li>
            <portal:portalLink displayTitle="true" title="Work Area"
                               url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.core.workarea.WorkAreaBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
        <li>
            <portal:portalLink displayTitle="true" title="Department"
                               url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.core.department.DepartmentBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
        </li>
        <li>
            <portal:portalLink displayTitle="true" title="Department Affiliation"
                               url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.core.departmentaffiliation.DepartmentAffiliation&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
        <li>
            <portal:portalLink displayTitle="true" title="Location"
                               url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.core.location.LocationBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
        </li>
        <li>
            <portal:portalLink displayTitle="true" title="Institution"
                               url="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.institution.InstitutionBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
        <li>
            <portal:portalLink displayTitle="true" title="Organization"
                               url="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.kfs.coa.businessobject.Organization&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
        <c:if test="${not locationViewOnly}">
	        <li>
	            <portal:portalLink displayTitle="true" title="Position Base"
	                               url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.core.position.PositionBaseBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
	        </li>
        </c:if>
    </ul>
</div>
<channel:portalChannelBottom />