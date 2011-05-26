<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>
<c:set var="Form" value="${TimeDetailActionForm}" scope="request"/>
<c:set var="TimeApprovalForm" value="${TimeApprovalActionForm}" scope="request"/>
${Form.outputString}
${TimeApprovalForm.outputString}
