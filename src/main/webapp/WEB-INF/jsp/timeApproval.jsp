<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>
<c:set var="Form" value="${TimeApprovalActionForm}" scope="request"/>

<tk:tkHeader tabId="approvals">
	<html:hidden property="methodToCall" value=""/>
	<tk:tkApproval/>
</tk:tkHeader>