<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<%-- for time approval, set refreshId to refresh, for leave Approval, set refreshId to leaveRefresh
the id is used in approval.js--%>
<%@ attribute name="refreshId" required="true" type="java.lang.String" %>
<%@ attribute name="approvable" required="true" type="java.lang.Boolean" %>
<div id="approvals-approve-selected-button">
    <c:choose>
        <c:when test="${approvable}">
            <input type="submit" class="approve" value="Approve selected" name="ApproveSelected"
                   onclick="this.form.methodToCall.value='approve'; this.form.submit();"/>
        </c:when>
        <c:otherwise>
            <input disabled type="submit" class="approve" value="Approve selected" name="ApproveSelected"/>
        </c:otherwise>
    </c:choose>
</div>