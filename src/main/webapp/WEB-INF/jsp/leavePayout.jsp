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

<c:set var="Form" value="${LeavePayoutForm}" scope="request"/>
<c:set var="KualiForm" value="${LeavePayoutForm}" scope="request"/>

<kul:documentPage showDocumentInfo="true"
      documentTypeName="LeavePayoutDocumentType"
      htmlFormAction="leavePayout"
      renderMultipart="true"
      showTabButtons="true">
<div class="tab-container" align=center>
	<kul:documentOverview editingMode="${KualiForm.editingMode}" />
	<tk:leavePayoutDoc editingMode="${KualiForm.editingMode}"/>

	<kul:notes />

	<kul:routeLog/>
	<kul:panelFooter />
	

	<kul:documentControls transactionalDocument="true" />

</kul:documentPage>

