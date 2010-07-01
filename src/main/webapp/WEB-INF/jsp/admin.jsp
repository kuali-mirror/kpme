<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<c:set var="Form" value="${AdminActionForm}" scope="request"/>

<tk:tkHeader tabId="admin">

<html:form action="/Admin" method="post">
<html:hidden property="methodToCall" value=""/>

<%--
	<fieldset style="width:30%">
	    <legend>Backdoor</legend>
			<html:text property="backdoorPrincipalName" size="20" />
			<input type="button" class="button" value="Submit" name="backdoor" onclick="this.form.methodToCall.value='backdoor'; this.form.submit();">
			<input type="button" class="button" value="Clear" name="clearBackdoor" onclick="this.form.methodToCall.value='clearBackdoor'; this.form.submit();">
	</fieldset>
--%>
</html:form>

</tk:tkHeader>