<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<jsp:useBean id="tagSupport" class="org.kuali.kpme.tklm.common.TagSupport"/>
<%@ attribute name="assignments" required="true" type="java.util.Map"%>

<c:choose>
	<c:when test="${fn:length(assignments) <= 1}">
		<c:forEach var="assignment" items="${assignments}">
			<span id="assignment-value">${assignment.value}</span>
			<input type="hidden" id="selectedAssignment" name="selectedAssignment" value="${assignment.key}"/>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<select id="selectedAssignment" name="selectedAssignment">
			<option value="" selected="selected">-- select an assignment --</option>
			<c:forEach var="assignment" items="${assignments}">
				<option value="${assignment.key}">${assignment.value}</option>
			</c:forEach>
		</select>
		<!-- bit of a hack to always have access to full assignment list so we can filter if necessary -->
        <select id="selectedAssignmentHidden" name="selectedAssignmentHidden" style="visibility:hidden;">
            <option value="" selected="selected">-- select an assignment --</option>
            <c:forEach var="assignment" items="${assignments}">
                <option value="${assignment.key}">${assignment.value}</option>
            </c:forEach>
        </select>
	</c:otherwise>
</c:choose>
<span id='loading-earnCodes' style='display:none; margin-left:10px;'><img src='images/ajax-loader.gif' alt='Loading' style='vertical-align:middle;'/></span>
