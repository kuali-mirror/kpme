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

<channel:portalChannelTop channelTitle="Time Keeping" />
<div class="body">
    <strong> </strong>
    <ul class="chan">
        <li>
            <portal:portalLink displayTitle="true" title="Time Collection Rule"
                               url="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.tklm.time.rules.timecollection.TimeCollectionRule&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
        <li>
            <portal:portalLink displayTitle="true" title="Clock Location Rule"
                               url="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.tklm.time.rules.clocklocation.ClockLocationRule&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
        <li>
            <portal:portalLink displayTitle="true" title="Department Lunch Deduction Rule"
                               url="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.tklm.time.rules.lunch.department.DeptLunchRule&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
    </ul>
    <strong> </strong>
    <ul class="chan">
        <li>
            <portal:portalLink displayTitle="true" title="Shift Differential Rule"
                               url="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.tklm.time.rules.shiftdifferential.ShiftDifferentialRule&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
        <li>
            <portal:portalLink displayTitle="true" title="Daily Overtime Rule"
                               url="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.tklm.time.rules.overtime.daily.DailyOvertimeRule&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
        <li>
            <portal:portalLink displayTitle="true" title="Weekly Overtime Rule"
                               url="${ConfigProperties.application.url}/kr/maintenance.do?businessObjectClassName=org.kuali.kpme.tklm.time.rules.overtime.weekly.WeeklyOvertimeRuleGroup&tkWeeklyOvertimeRuleGroupId=1&returnLocation=${ConfigProperties.application.url}/portal.do&methodToCall=edit" />
        </li>
    </ul>
    <strong> </strong>
    <ul class="chan">
        <li>
            <portal:portalLink displayTitle="true" title="System Lunch Rule"
                               url="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.tklm.time.rules.lunch.sys.SystemLunchRule&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
        <li>
            <portal:portalLink displayTitle="true" title="Grace Period Rule"
                               url="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.tklm.time.rules.graceperiod.GracePeriodRule&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
	</ul>
</div>
<channel:portalChannelBottom />