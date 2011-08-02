<%@include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<c:set var="Form" value="${MissedPunchForm}" scope="request"/>
<c:set var="KualiForm" value="${MissedPunchForm}" scope="request"/>

<kul:documentPage showDocumentInfo="true"
      documentTypeName="MissedPunchDocumentType"
      htmlFormAction="missedPunch"
      renderMultipart="true"
      showTabButtons="true">

	<kul:documentOverview editingMode="${KualiForm.editingMode}" />

	<tk:missedPunchDoc editingMode="${KualiForm.editingMode}"/>

	<kul:notes />

	<kul:panelFooter />

	<kul:documentControls transactionalDocument="true" />

</kul:documentPage>

