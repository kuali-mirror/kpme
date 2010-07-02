<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>


<kul:documentPage 
	showDocumentInfo="true"
	htmlFormAction="${path}"
	documentTypeName="${KualiForm.docTypeName}"
	renderMultipart="true"
    showTabButtons="true" auditCount="0">

    <html:hidden property="document.documentHeader.documentNumber" />
    <kul:hiddenDocumentFields />
    <html:hidden property="methodToCall" />
    
    <tk:payCalendarDates/>
    
	<kul:notes />
	<kul:routeLog />
	<kul:panelFooter />
	<kul:documentControls transactionalDocument="false" />
</kul:documentPage>