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

<channel:portalChannelTop channelTitle="Change Target Person" />
<div class="body">
    <div id="content">
        <html:form action="/changeTargetPerson" method="post" style="margin:0; display:inline">
            <html:text property="principalName" size="20" />
            <kul:lookup boClassName="org.kuali.rice.kim.impl.identity.PersonImpl" fieldConversions="principalName:principalName" />
            <html:submit property="methodToCall.changeTargetPerson" value="Submit" />
            <html:submit property="methodToCall.clearTargetPerson" value="Clear" />
        </html:form>
    </div>
</div>
<channel:portalChannelBottom />