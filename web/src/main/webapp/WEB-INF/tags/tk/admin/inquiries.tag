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
            <portal:portalLink displayTitle="true" title="Clock Log"
                               url="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.clocklog.ClockLog&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
        <li>
            <portal:portalLink displayTitle="true" title="Missed Punch"
                               url="${ConfigProperties.kew.url}/DocumentSearch.do?documentTypeName=MissedPunchDocumentType" />
        </li>
        <li>
            <portal:portalLink displayTitle="true" title="Time Block History Inquiry"
                               url="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.timeblock.TimeBlockHistoryDetail&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
        <li>
            <portal:portalLink displayTitle="true" title="Time Block Inquiry"
                               url="${ConfigProperties.application.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.timeblock.TimeBlock&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
        </li>
	</ul>
</div>
<channel:portalChannelBottom />