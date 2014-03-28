<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<jsp:useBean id="tagSupport"
	class="org.kuali.kpme.tklm.common.TagSupport" />
<%@ attribute name="calledFrom" required="false" type="java.lang.String"%>
<%@ attribute name="calType" required="false" type="java.lang.String"%>
<fieldset style="width: 97%;">
	<legend>Filters</legend>
	<table id="approvals-filter" style="width: 100%">
		<tr>
			<c:if test="${empty calledFrom}">
				<td style="white-space: nowrap;">Calendar: <%--<label for="selectedPayCalendarGroup">--%>
					<select id="selectedPayCalendarGroup"
					name="selectedPayCalendarGroup"
					onchange="this.form.methodToCall.value='selectNewPayCalendar'; this.form.submit();">
						<c:forEach var="payCalendarGroup"
							items="${Form.payCalendarGroups}">
							<c:choose>
								<c:when
									test="${Form.selectedPayCalendarGroup eq payCalendarGroup}">
									<option value="${payCalendarGroup}" selected="selected">${payCalendarGroup}</option>
								</c:when>
								<c:otherwise>
									<option value="${payCalendarGroup}">${payCalendarGroup}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
				</select> <%--</label>--%>
				</td>
			</c:if>
			<c:if test="${not empty calledFrom}">
				<td style="white-space: nowrap;">Calendar View: <select
					id="selectedCalendarType" name="selectedCalendarType"
					onchange="this.form.methodToCall.value='selectNewCalendarType'; this.form.submit();">
						<c:choose>
							<c:when test="${Form.selectedCalendarType eq 'M'}">
								<option value="M" selected="selected">Month View</option>
							</c:when>
							<c:otherwise>
								<option value="M">Month View</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${Form.selectedCalendarType eq 'W'}">
								<option value="W" selected="selected">Week View</option>
							</c:when>
							<c:otherwise>
								<option value="W">Week View</option>
							</c:otherwise>
						</c:choose>
				</select>
				</td>
			</c:if>

			<td style="white-space: nowrap;">Department: <select
				id="selectedDept" name="selectedDept"
				onchange="this.form.methodToCall.value='selectNewDept'; this.form.submit();">
					<option value="">-- Select a department --</option>
					<c:forEach var="dept" items="${Form.departments}">
						<c:choose>
							<c:when test="${Form.selectedDept eq dept}">
								<option value="${dept}" selected="selected">${dept}</option>
							</c:when>
							<c:otherwise>
								<option value="${dept}">${dept}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
			</select>
			</td>
			<td style="white-space: nowrap;">Work Area: <label
				for="work areas"> <select id="selectedWorkArea"
					name="selectedWorkArea"
					onchange="this.form.methodToCall.value='selectNewWorkArea'; this.form.submit();">
						<option value="">Show All</option>
						<c:forEach var="deptWorkarea" items="${Form.workAreaDescr}">
							<c:choose>
								<c:when test="${Form.selectedWorkArea eq deptWorkarea.key}">
									<option value="${deptWorkarea.key}" selected="selected">${deptWorkarea.value}</option>
								</c:when>
								<c:otherwise>
									<option value="${deptWorkarea.key}">${deptWorkarea.value}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
				</select>
			</label>
			</td>
		</tr>
		<tr>
			<c:if test="${empty calledFrom}">
				<td colspan="2"><tk:payCalendarSelect calType="${calType}" /></td>
			</c:if>
			<td />
		</tr>

	</table>
</fieldset>