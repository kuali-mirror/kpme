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
<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<c:set var="Form" value="${TkForm}" scope="request"/>
<c:set var="KualiForm" value="${TkForm}" scope="request"/>
<c:set var="TkForm" value="${TkForm}" scope="request"/>

<tk:tkHeader tabId="departmentAdmin">
    <div id="departmentAdmin">
        <table width="100%">
            <tr>
                <td class="content" valign="top">
                    <departmentAdmin:hrPayroll />
                    <departmentAdmin:timeKeeping />
                    <departmentAdmin:leaveMaintenance />
                    <departmentAdmin:positionManagement />
                    <departmentAdmin:administrative />
                </td>
                <td class="content" valign="top">
                    <departmentAdmin:inquiries />
                    <departmentAdmin:changeTargetPerson />
                </td>
            </tr>
        </table>
    </div>
</tk:tkHeader>