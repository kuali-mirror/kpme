<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<jsp:useBean id="tagSupport" class="org.kuali.hr.tklm.common.TagSupport"/>
<%@ attribute name="earnCodes" required="true" type="java.util.Map"%>

<c:choose>
	<c:when test="${fn:length(earnCodes) <= 1}">
		<c:forEach var="earnCode" items="${earnCodes}">
			${earnCode.key}
			<input type="hidden" id="earnCode" name="earnCode" value="${earnCode.value}"/>
		</c:forEach>
    </c:when>
    <c:otherwise>
		<select id="earnCode">
			<option value="" selected="selected">-- select one --</option>
			<c:forEach var="earnCode" items="${earnCodes}">
				<option value="${earnCode.key}">${earnCode.value}</option>
			</c:forEach>
		</select>
    </c:otherwise>
</c:choose>

<%--
<select id="earnCode">
	<option value="RGN" selected="selected">RGN: Regular</option>
	<option value="SCK">SCK: Sick</option>
	<option value="VAC">VAC: Vacation</option>
	<option value="WEP">WEP: Emergency Weather</option>
	<option value="HAZ">HAZ: Hazard Pay - 1.50</option>
	<option value="HIP">HIP: Holiday Incentive</option>
	<option value="OC1">OC1: On Call - 1.50</option>
	<option value="OC2">OC2: On Call - 2.00</option>
	<option value="PRM">PRM: Premium</option>
</select>
--%>