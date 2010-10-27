<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>
<c:set var="Form" value="${PersonInfoActionForm}" scope="request"/>

<tk:tkHeader tabId="personInfo">

<div class="person-detail">
	<table>
		<tr>
			<td colspan="4" class="header">Person Information</td>
		</tr>
		<tr>
			<td class="sub-header"><bean:message key="person.info.employeeName"/>:</td>
			<td>Tammy Trojan</td>
			<td class="sub-header"><bean:message key="person.info.supervisorName"/>:</td>
			<td>Fabiola Salinas</td>
		</tr>
		<tr>
			<td class="sub-header"><bean:message key="person.info.employeeNumber"/>:</td>
			<td>1234567</td>
			<td class="sub-header"><bean:message key="person.info.homeDeptCoordinator"/>:r</td>
			<td>Frances Clairmont</td>
		</tr>
		<tr>
			<td class="sub-header"><bean:message key="person.info.idNumber"/>:</td>
			<td>1234567</td>
			<td class="sub-header"><bean:message key="person.info.homeDeptPhoneNumber"/>:</td>
			<td>(123)456-789</td>
		</tr>
		<tr>
			<td class="sub-header"><bean:message key="person.info.employeeUserType"/>:</td>
			<td>Non-Exempt / Non-Union</td>
			<td class="sub-header"><bean:message key="person.info.homeDeptEmail"/>:</td>
			<td>email@usc.edu</td>
		</tr>
		<tr>
			<td class="sub-header"><bean:message key="person.info.homeDeptName"/>:</td>
			<td>Payroll Services</td>
			<td class="sub-header"><bean:message key="person.info.startTime"/>:</td>
			<td>Midnight</td>
		</tr>
		<tr>
			<td class="sub-header"><bean:message key="person.info.homeDeptNumber"/>:</td>
			<td>0000-00-0000</td>
			<td class="sub-header"><bean:message key="person.info.timesheetStatus"/>:</td>
			<td>Pending Approval</td>
		</tr>
		<tr>
			<td class="sub-header"><bean:message key="person.info.employeeWorkState"/>:</td>
			<td>CA</td>
			<td class="sub-header"><bean:message key="person.info.periodEndingDate"/>:</td>
			<td>April 14, 2010</td>
		</tr>
	</table>
</div>

</tk:tkHeader>
