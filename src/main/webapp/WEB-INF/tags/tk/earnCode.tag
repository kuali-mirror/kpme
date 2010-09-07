<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<%@ attribute name="earnCodes" required="true" type="java.util.Map"%>

<select id="earnCode">
	<c:forEach var="earnCode" items="${earnCodes}">
		<c:forEach var="earnCodeStr" items="${earnCode.value}">
			<option value="${earnCode.key}">${earnCodeStr}</option>
		</c:forEach>
	</c:forEach>
</select>

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