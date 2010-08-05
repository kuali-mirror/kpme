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