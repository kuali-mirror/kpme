	

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
     
    <channel:portalChannelTop channelTitle="KOHR" />
    <div class="body">
    	<strong>Position Management</strong>
         <ul class="chan">
            <li>
                <portal:portalLink displayTitle="true" title="Create Position"
                                   url="${ConfigProperties.application.url}/kr-krad/kpme/effectiveDateMaintenance?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.position.PositionBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
            </li>
            <li>
                <portal:portalLink displayTitle="true" title="Edit Position"
                                   url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.pm.position.PositionBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
            </li>    
        </ul>
        <strong>Person Management</strong>
        <ul class="chan">
            <li>
                <portal:portalLink displayTitle="true" title="Create Person"
                                   url="${ConfigProperties.kim.url}/identityManagementPersonDocument.do?returnLocation=${ConfigProperties.application.url}/portal.do&docTypeName=IdentityManagementPersonDocument&methodToCall=docHandler&command=initiate" />
            </li>
            <li>
                <portal:portalLink displayTitle="true" title="Edit Person" 
                				   url="${ConfigProperties.kr.url}/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.rice.kim.api.identity.Person&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" />
            </li>    
        </ul>
        
    </div>
    <channel:portalChannelBottom />

