<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<%@ attribute name="assignments" required="true" type="java.util.Map"%>

<select id="assignment">
	<c:forEach var="assignment" items="${assignments}">
		<c:forEach var="assignmentStr" items="${assignment.value}">
			<option value="${assignment.key}">${assignmentStr}</option>
		</c:forEach>
	</c:forEach>
</select>