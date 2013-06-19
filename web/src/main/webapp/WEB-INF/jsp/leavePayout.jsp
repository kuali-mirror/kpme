<%--

    Copyright 2004-2013 The Kuali Foundation

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
<link type="text/css" href="${ConfigProperties.css.dir}/ui-iu/jquery-ui-1.8.5.custom.css" rel="stylesheet" />
<link type="text/css" href="${ConfigProperties.css.dir}/tk.css?v=1.1" rel="stylesheet" />
<kul:documentPage
	  showDocumentInfo="true"
      htmlFormAction="LeavePayout"
      documentTypeName="LeavePayoutDocumentType"
      renderMultipart="false"
      docTitle="Leave Payout"
      showTabButtons="false">
	<lm:leavePayout leavePayout="${Form.leavePayout}"/>
	<kul:documentControls transactionalDocument="false" />
</kul:documentPage>

