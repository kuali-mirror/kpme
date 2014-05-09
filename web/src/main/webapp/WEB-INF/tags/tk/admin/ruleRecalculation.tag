<%@ include file="/rice-portal/jsp/sys/riceTldHeader.jsp"%>

<c:set var="systemAdmin" value='<%=org.kuali.kpme.core.util.HrContext.isSystemAdmin()%>'/>

<c:if test="${systemAdmin}">
    <div class="portlet">
        <div class="header">Document Rule Recalculate </div>
        <div class="body">
            <html:form action="/ruleRecalculate" method="post">
                <html:text property="ruleRecalculateDocumentId" />
                <html:submit property="methodToCall.recalculateDocument" value="Submit" />
            </html:form>
        </div>
    </div>
</c:if>