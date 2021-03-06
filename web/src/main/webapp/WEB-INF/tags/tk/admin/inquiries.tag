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

<channel:portalChannelTop channelTitle="Inquiries" />
<div class="body">
    <ul class="chan">
        <li>
            <portal:portalLink displayTitle="true" title="Timesheet "
                               url="${ConfigProperties.application.url}/portal.do?channelTitle=Document%20Search&channelUrl=${ConfigProperties.kew.url}/DocumentSearch.do?documentTypeName=TimesheetDocument"/>
        </li>
        <li>
            <portal:portalLink displayTitle="true" title="Leave Calendar"
                               url="${ConfigProperties.application.url}/portal.do?channelTitle=Document%20Search&channelUrl=${ConfigProperties.kew.url}/DocumentSearch.do?documentTypeName=LeaveCalendarDocument"/>
        </li>

        <li>
            <portal:portalLink displayTitle="true" title="Clock Log"
                               url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.tklm.time.clocklog.ClockLogBo&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
        <li>
            <portal:portalLink displayTitle="true" title="Missed Punch"
                               url="${ConfigProperties.application.url}/portal.do?channelTitle=Document%20Search&channelUrl=${ConfigProperties.kew.url}/DocumentSearch.do?documentTypeName=MissedPunchDocumentType"/>
        </li>
        <li>
            <portal:portalLink displayTitle="true" title="Time Block"
                               url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.tklm.time.timeblock.TimeBlockBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
        <li>
            <portal:portalLink displayTitle="true" title="Time Block History"
                               url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.tklm.time.timeblock.TimeBlockHistory&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
        <li>
            <portal:portalLink displayTitle="true" title="Leave Block"
                               url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.tklm.leave.block.LeaveBlockBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
        <li>
            <portal:portalLink displayTitle="true" title="Leave Block History"
                               url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.tklm.leave.block.LeaveBlockHistory&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
	</ul>
</div>
<channel:portalChannelBottom />