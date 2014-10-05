<%@ tag import="org.kuali.kpme.edo.util.EdoUser" %>
<%--
<div id="identitybar" class="ui-corner-all">
   &nbsp;Login: <strong>${fullName} (${userName})</strong> | Department: <strong>${deptName}</strong>
</div>
--%>
<%@include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>

<c:if test="${!empty UserSession.loggedInUserPrincipalName}">
	<c:set var="employeeName" value="${UserSession.person.name}" />
	<c:set var="principalId" value="${UserSession.person.principalId}" />
	<c:set var="backdoorInUse"
		value="${UserSession.backdoorInUse == 'true'}" />
	<c:set var="targetInUse"
		value='<%=EdoUser.isTargetInUse()%>' />
</c:if>

<c:if test="${UserSession.backdoorInUse eq true}">
	<c:set var="prefix" value="Backdoor" />
	<c:set var="highlight" value="highlight" />
	<c:set var="backdoor" value="backdoor" />
</c:if>
<c:if test="${targetInUse}">
	<c:set var="targetuser" value="targetuser" />
	<c:set var="targetName"
		value='<%=EdoUser.getCurrentTargetPerson().getName()%>' />
</c:if>

<div id="person-info" style="float: right;">
	<%-- <c:if test="${backdoorInUse}">
      <a href="<%=request.getContextPath() %>/backdoorlogin.do?methodToCall=clearBackdoor" style="font-size: .8em;">Remove backdoor</a> |
    </c:if>

  <span style="float: right;"><a href="EdoLogout.do">Logout</a></span>
  <c:if test="${UserSession.backdoorInUse}">
    --%>
	<br /> <br />
	<table>
		<tbody>
		
				<c:if test="${targetInUse}">
					<tr>
					<td>
                    You are working on ${targetName} eDossier.
                    <input type="button" value="remove target user"
							name="return"
							onclick="location.href='<%=request.getContextPath() %>/changeTargetPerson.do?methodToCall=clearTargetPerson'" />
					</td>
					<td></td>
					</tr>
					</c:if>
			
			<tr>
				<td align="right"><c:if test="${backdoorInUse}">
						<a
							href="<%=request.getContextPath()%>/backdoorlogin.do?methodToCall=clearBackdoor">Remove
							backdoor</a> |
                    </c:if> <a href="EdoLogout.do">Logout</a></td>
				<td></td>
			</tr>
			<c:if test="${targetInUse}">
				<tr>
					<td align="right">${prefix} Target Employee Name:</td>
					<td>${targetName}</td>
				</tr>
			</c:if>
			<tr>
				<td align="right">${prefix} Employee Name:</td>
				<td>${employeeName}</td>
			</tr>
			<tr>
				<td align="right">${prefix} Employee ID:</td>
				<td>${principalId}</td>
			</tr>
			<tr>
				<td align="right">${prefix} User Name:</td>
				<td>${UserSession.person.principalName}</td>
			</tr>
			<tr>
				<td align="right">${prefix} User Department:</td>
				<td>${UserSession.person.primaryDepartmentCode}</td>
			</tr>
		</tbody>
	</table>
</div>

