<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<jsp:useBean id="tagSupport" class="org.kuali.hr.time.util.TagSupport"/>
<%@ attribute name="assignments" required="true" type="java.util.Map"%>
<jsp:setProperty name="tagSupport" property="assignments" value="${assignments}" />

<c:choose>
	<c:when test="${tagSupport.assignmentSize <= 1}">
		<c:forEach var="assignment" items="${assignments}">
			<c:forEach var="assignmentStr" items="${assignment.value}">
				${assignmentStr}
				<input type="hidden" id="assignment" name="assignment" value="${assignment.key}"/>
			</c:forEach>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<select id="assignment">
			<c:forEach var="assignment" items="${assignments}">
				<c:forEach var="assignmentStr" items="${assignment.value}">
					<option value="${assignment.key}">${assignmentStr}</option>
				</c:forEach>
			</c:forEach>
		</select>
	</c:otherwise>
</c:choose>

