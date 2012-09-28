<%--

    Copyright 2004-2012 The Kuali Foundation

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
<%@include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<c:set var="inquiry" scope="request" value="false" />

<kul:documentPage
	showDocumentInfo="${!inquiry}"
	documentTypeName="WorkAreaMaintenanceDocument" 
	renderMultipart="${inquiry}"
	htmlFormAction="WorkAreaMaintenance">

<c:if test="${!inquiry}">
    <kul:hiddenDocumentFields />
    <kul:documentOverview editingMode="${KualiForm.editingMode}" />
</c:if>

<tk:workArea/>
<tk:workAreaRole roleList="${workArea.roleAssignments}" inquiry="${inquiry}"/>
<tk:workAreaTask taskList="${workArea.tasks}" inquiry="${inquiry}"/>

<c:if test="${!inquiry}">
    <kul:adHocRecipients /> 
    <kul:routeLog />
</c:if>
<kul:panelFooter />

<c:choose>
    <c:when test="${!inquiry}">
        <kul:documentControls transactionalDocument="true" />
    </c:when>
    <c:otherwise>
        <kul:inquiryControls />
    </c:otherwise>
</c:choose>

</kul:documentPage>