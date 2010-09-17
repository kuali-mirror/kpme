<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<jsp:useBean id="tagSupport" class="org.kuali.hr.time.util.TagSupport"/>
<%@ attribute name="assignments" required="true" type="java.util.Map"%>
<jsp:setProperty name="tagSupport" property="assignments" value="${assignments}" />

<c:choose>
	<c:when test="${fn:length(assignments) <= 1}">
		<c:forEach var="assignment" items="${assignments}">
			${assignment.value}
			<input type="hidden" id="assignment" name="assignment" value="${assignment.key}"/>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<select id="assignment">
			<option value="" selected="selected">-- select one --</option>
			<c:forEach var="assignment" items="${assignments}">
				<option value="${assignment.key}">${assignment.value}</option>
			</c:forEach>
		</select>
	</c:otherwise>
</c:choose>

