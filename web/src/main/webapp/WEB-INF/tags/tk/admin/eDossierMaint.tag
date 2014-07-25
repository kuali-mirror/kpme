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

<channel:portalChannelTop channelTitle="eDossier" />
	<div class="body">
	    <strong>Maintenance</strong>
	    <ul class="chan">
	    	<li>
	            <portal:portalLink displayTitle="true" title="Candidate"															
	                   url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.edo.candidate.EdoCandidateBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
	        </li>
	        
	        <li>
	            <portal:portalLink displayTitle="true" title="Dossier Type"															
	                   url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.edo.dossier.type.EdoDossierTypeBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
	        </li>
	        
	        <li>
	         	<portal:portalLink displayTitle="true" title="Checklist" 
	         			url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.edo.checklist.EdoChecklistBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
	        </li>
	        
	        <li>
	         	<portal:portalLink displayTitle="true" title="Checklist Section" 
	         			url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.edo.checklist.EdoChecklistSectionBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
	        </li>
	        
	    	<li>
	         	<portal:portalLink displayTitle="true" title="Checklist Item" 
	         			url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.edo.checklist.EdoChecklistItemBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
	        </li>
	        
	        <li>
	         	<portal:portalLink displayTitle="true" title="Item Type" 
	         			url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.edo.item.type.EdoItemTypeBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y"/>
	        </li>
	        
	        <li>
	            <portal:portalLink displayTitle="true" title="Review Layer Defination"															
	                   url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinitionBo&returnLocation=${ConfigProperties.application.url}/portal.do&showMaintenanceLinks=true&hideReturnLink=true&docFormKey=88888888&active=Y" />
	        </li>
		</ul>
		
		<ul class="chan">
			<li>
	        	<portal:portalLink displayTitle="true" title="Initiate eDossier"
	        			url="${ConfigProperties.application.url}/kpme/edoDossier?methodToCall=docHandler&command=initiate&viewId=EdoDossier-DocumentView&docTypeName=EdoDossierDocumentType" />
	        </li>
		</ul>	
	</div>
<channel:portalChannelBottom />
