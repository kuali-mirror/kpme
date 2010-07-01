<%@ page language="java"%>
<%@ taglib tagdir="/WEB-INF/tags/shared" prefix="hr"%>
<%@ include file="/jsp/tlds.jsp"%>
<html>
<fmt:message key="javascript.html" bundle="${springMessages}"/>
<fmt:message key="css.html" bundle="${springMessages}"/>

<head>
<title>Incident</title>
<body onload="document.forms[0].submit();">
<form name="IncidentReportForm" method="post" action="${StrutsActionForm.url}">
    <html:hidden name="StrutsActionForm" property="description"  value="${StrutsActionForm.incident.description}"/>
    <html:hidden name="StrutsActionForm" property="username" value="${StrutsActionForm.incident.username}" />
    <html:hidden name="StrutsActionForm" property="projectID" value="${StrutsActionForm.incident.projectID}" />
    <html:hidden name="StrutsActionForm" property="status" value="${StrutsActionForm.incident.status}" />
    <html:hidden name="StrutsActionForm" property="priorityNumber" value="${StrutsActionForm.incident.priorityNumber}" />
    <html:hidden name="StrutsActionForm" property="action" value="${StrutsActionForm.incident.action}" />
    <html:hidden name="StrutsActionForm" property="issueArea" value="${StrutsActionForm.incident.issueArea}" />
    <html:hidden name="StrutsActionForm" property="title" value="${StrutsActionForm.incident.title}" />
    <html:hidden name="StrutsActionForm" property="delimiter" value="${StrutsActionForm.incident.delimiter}" />
    <html:hidden name="StrutsActionForm" property="projfields_mappings" value="${StrutsActionForm.incident.projfields_mappings}" />
    <html:hidden name="StrutsActionForm" property="universityId" value="${StrutsActionForm.incident.universityId}" />
    <html:hidden name="StrutsActionForm" property="serviceArea" value="${StrutsActionForm.incident.serviceArea}" />
    <html:hidden name="StrutsActionForm" property="serviceItem" value="${StrutsActionForm.incident.serviceItem}" />
    <html:hidden name="StrutsActionForm" property="documentId" value="${StrutsActionForm.incident.documentId}" />
    <html:hidden name="StrutsActionForm" property="workArea" value="${StrutsActionForm.incident.workArea}" />
    <html:hidden name="StrutsActionForm" property="departmentId" value="${StrutsActionForm.incident.departmentId}" />
    <html:hidden name="StrutsActionForm" property="return_url" value="${StrutsActionForm.incident.returnUrl}" />     
</form>
</body>
</html>