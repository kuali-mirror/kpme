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
    <c:set var="systemAdmin" value='<%=org.kuali.hr.time.roles.TkUserRoles.getUserRoles(org.kuali.rice.krad.util.GlobalVariables.getUserSession().getPrincipalId()).isSystemAdmin()%>' />
    <c:set var="globalViewOnly" value='<%=org.kuali.hr.time.roles.TkUserRoles.getUserRoles(org.kuali.rice.krad.util.GlobalVariables.getUserSession().getPrincipalId()).isGlobalViewOnly()%>' />
    <c:set var="departmentAdmin" value='<%=org.kuali.hr.time.roles.TkUserRoles.getUserRoles(org.kuali.rice.krad.util.GlobalVariables.getUserSession().getPrincipalId()).isDepartmentAdmin()%>' />
    <c:set var="departmentViewOnly" value='<%=org.kuali.hr.time.roles.TkUserRoles.getUserRoles(org.kuali.rice.krad.util.GlobalVariables.getUserSession().getPrincipalId()).isDeptViewOnly()%>' />
    <c:set var="locationAdmin" value='<%=org.kuali.hr.time.roles.TkUserRoles.getUserRoles(org.kuali.rice.krad.util.GlobalVariables.getUserSession().getPrincipalId()).isLocationAdmin()%>' />
    <c:set var="reviewer" value='<%=org.kuali.hr.time.roles.TkUserRoles.getUserRoles(org.kuali.rice.krad.util.GlobalVariables.getUserSession().getPrincipalId()).isReviewer()%>' />
</c:if>

<c:if test="${systemAdmin || globalViewOnly || departmentAdmin || departmentViewOnly || locationAdmin || reviewer}">
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
</c:if>