<%@ page language="java"%>
<%@ taglib tagdir="/WEB-INF/tags/shared" prefix="hr"%>
<%@ include file="/jsp/tlds.jsp"%>
<html>
<fmt:message key="javascript.html" bundle="${springMessages}"/>
<fmt:message key="css.html" bundle="${springMessages}"/>

<head>
<title>Incident</title>
<body onload="document.forms[0].submit();">
<form name="IncidentReportForm" method="post" action="https://falcon.iu.edu/perl/forms/incweb">
	<html:hidden name="StrutsActionForm" property="category" value="${StrutsActionForm.incident.category}"/>
	<html:hidden name="StrutsActionForm" property="subcategory" value="${StrutsActionForm.incident.subcategory}"/>
	<html:hidden name="StrutsActionForm" property="product.type" value="${StrutsActionForm.incident.productType}" />
	<html:hidden name="StrutsActionForm" property="problem.type" value="${StrutsActionForm.incident.problemType}" />
	<html:hidden name="StrutsActionForm" property="severity.code" value="${StrutsActionForm.incident.severityCode}" />
	<html:hidden name="StrutsActionForm" property="how.opened" value="${StrutsActionForm.incident.howOpened}" />
	<html:hidden name="StrutsActionForm" property="assignment" value="${StrutsActionForm.incident.assignmentGroup}" />
	<html:hidden name="StrutsActionForm" property="brief.description" value="${StrutsActionForm.incident.errorMessage}" />
	<html:hidden name="StrutsActionForm" property="action"  value="${StrutsActionForm.incident.description}"/>
	<html:hidden name="StrutsActionForm" property="contact.email" value="${StrutsActionForm.incident.emailAddress}" />
	<html:hidden name="StrutsActionForm" property="contact.name" value="${StrutsActionForm.incident.networkId}" />
	</form>
</body>
</html>