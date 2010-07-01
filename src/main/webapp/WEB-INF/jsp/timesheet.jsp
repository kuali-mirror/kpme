<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<tk:tkHeader tabId="timesheet">
<div id="timesheet-summary">
	<table>
		<thead>
			<%--
			<tr>
				<td class="header" colspan="4">Historical Timesheets</td>
			</tr>
			 --%>
			<tr class="ui-state-default" style="font-size:.9em;">
				<td>Doc Id</td>
				<td>Pay Period</td>
				<td>Assignment</td>
				<td>Status</td>
		    </tr>
		</thead>
		<tbody>
			<tr>
				<td><a href="#">123456</a></td>
				<td>5/2 - 5/16</td>
				<td>HRMS Java developer</td>
				<td>Current</td>
			</tr>
			<tr>
				<td><a href="#">123457</a></td>
				<td>5/2 - 5/16</td>
				<td>HRMS PS developer</td>
				<td>Approved</td>
			</tr>
		</tbody>
	</table>
</div>
</tk:tkHeader>
