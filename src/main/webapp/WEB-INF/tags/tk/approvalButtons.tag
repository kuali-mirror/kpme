<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<%-- for time approval, set refreshId to refresh, for leave Approval, set refreshId to leaveRefresh
the id is used in approval.js--%>
<%@ attribute name="refreshId" required="true" type="java.lang.String" %>

<div id="approvals-approve-button">
    <input type="submit" class="approve" value="Approve" name="Approve"
           onclick="this.form.methodToCall.value='approve'; this.form.submit();"/>
    <input type="button" id='${refreshId}' value="Refresh Status"
           class="ui-button ui-widget ui-state-default ui-corner-all"/>
</div>