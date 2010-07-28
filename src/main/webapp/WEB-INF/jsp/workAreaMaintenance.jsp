<%@include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<c:set var="roleEmployee" scope="request" value="${roleMap['TK_EMPLOYEE']}" />
<c:set var="roleApprover" scope="request" value="${roleMap['TK_APPROVER']}" />
<c:set var="roleOrgAdmin" scope="request" value="${roleMap['TK_ORG_ADMIN']}" />
<c:set var="roleSysAdmin" scope="request" value="${roleMap['TK_SYS_ADMIN']}" />
<c:set var="inquiry" scope="request" value="false" />

<kul:documentPage
	documentTypeName="WorkAreaMaintenanceDocument" 
	htmlFormAction="WorkAreaMaintenance">

<c:if test="${!inquiry}">
    <kul:hiddenDocumentFields />
    <kul:documentOverview editingMode="${KualiForm.editingMode}" />
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

Work Area:
${workArea}
<br/>
Full Role Map:
${roleMap}
<br/>
<br/>
TK_EMPLOYEE : ${roleEmployee}
<br/>
TK_APPROVER : ${roleApprover}
<br/>
TK_ORG_ADMIN : ${roleOrgAdmin}
<br/>
TK_SYS_ADMIN : ${roleSysAdmin}

</kul:documentPage>