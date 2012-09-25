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