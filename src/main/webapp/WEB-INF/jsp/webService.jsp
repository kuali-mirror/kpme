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
<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>
<c:set var="Form" value="${TimeDetailWSActionForm}" scope="request"/>
<c:set var="TimeApprovalWSForm" value="${TimeApprovalWSActionForm}" scope="request"/>
<c:set var="LeaveCalendarWSForm" value="${LeaveCalendarWSForm}" scope="request"/>
<c:set var="LeaveApprovalWSForm" value="${LeaveApprovalWSActionForm}" scope="request"/>
<c:set var="LeaveRequestApprovalActionForm" value="${LeaveRequestApprovalActionForm}" scope="request"/>
${Form.outputString}
${TimeApprovalWSForm.outputString}
${LeaveCalendarWSForm.outputString}
${LeaveApprovalWSForm.outputString}
${LeaveRequestApprovalActionForm.outputString}