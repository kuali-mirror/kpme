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
<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>
<c:if test="${!empty UserSession.loggedInUserPrincipalName}">
    <c:set var="systemAdmin" value='<%=org.kuali.kpme.core.util.HrContext.isSystemAdmin()%>' />
    <c:set var="globalViewOnly" value='<%=org.kuali.kpme.core.util.HrContext.isGlobalViewOnly()%>' />
    <c:set var="locationAdmin" value='<%=org.kuali.kpme.tklm.time.util.TkContext.isLocationAdmin()%>' />
    <c:set var="locationViewOnly" value='<%=org.kuali.kpme.tklm.time.util.TkContext.isLocationViewOnly()%>' />
</c:if>
<c:if test="${not systemAdmin && not globalViewOnly && not locationAdmin && not locationViewOnly}">
    <jsp:forward page="portal.do?selectedTab=main"/>
</c:if>
<td class="content" valign="top">
    <c:if test="${systemAdmin || locationAdmin}">
        <admin:adminActions/>
    </c:if>
    <admin:timeKeeping />
    <admin:leaveMaintenance />
    <admin:inquiries/>
</td>
