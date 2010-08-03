<%@include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<c:set var="inquiry" scope="request" value="false" />

<kul:documentPage
	documentTypeName="WorkAreaMaintenanceDocument" 
	htmlFormAction="WorkAreaMaintenance">

<c:if test="${!inquiry}">
    <kul:hiddenDocumentFields />
    <kul:documentOverview editingMode="${KualiForm.editingMode}" />
</c:if>

<tk:workArea/>
<tk:workAreaRole roleList="${workArea.roleAssignments}" inquiry="${inquiry}"/>

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