<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<jsp:useBean id="tagSupport" class="org.kuali.kpme.tklm.common.TagSupport"/>
<%@ attribute name="selectedIpAdd" required="true" type="java.lang.String" %>
<%@ attribute name="batchJobEntryId" required="true" type="java.lang.String" %>

<c:set var="ipAdds" value="${tagSupport.ipAddresses}" scope="request"/>

<%--<input type="hidden" name="tkBatchJobEntryId" value="${batchJobEntryId}"/>--%>

<select name="ipAddressToChange">
    <c:forEach var="ip" items="${ipAdds}">
        <c:choose>
            <c:when test="${ip eq selectedIpAdd}">
                <option value="${ip}_${batchJobEntryId}" selected="selected">${ip}</option>
            </c:when>
            <c:otherwise>
                <option value="${ip}_${batchJobEntryId}">${ip}</option>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</select>

<%--<input type="image" src="images/edit.png" alt="Edit" style="width: 16px; height: 16px;"/>--%>
<input type="button" value="change" style="font-size: .8em;" class="changeIpAddress"/>