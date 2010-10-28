<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>
<c:set var="Form" value="${PersonInfoActionForm}" scope="request"/>

<c:set var="Form" value="${PersonInfoActionForm}" scope="request"/>
<tk:tkHeader tabId="personInfo">

<div class="person-detail">
	<table>
		<tr>
			<td colspan="11" class="header">Jobs Data</td>
		</tr>
		<c:forEach var="assignment" items="${Form.assignments}">
			<tr>
				<td class="header">Job Number</td>
				<td class="header">Effective Date</td>
				<td class="header">Location</td>
				<td class="header">Department</td>
				<td class="header">Position Title</td>
				<td class="header">Pay Type</td>
				<td class="header">Sal Group</td>
				<td class="header">Pay Grade</td>
				<td class="header">Standard Hours</td>
				<td class="header">Pay Rate</td>
			</tr>
			<tr>
				<td>${assignment.job.jobNumber}</td>
				<td>${assignment.job.effectiveDate}</td>
				<td>${assignment.job.location}</td>
				<td>${assignment.job.dept}</td>
				<td></td>
				<td>${assignment.job.hrPayType}</td>
				<td>${assignment.job.tkSalGroup}</td>
				<td>${assignment.job.payGrade}</td>
				<td>${assignment.job.standardHours}</td>
				<td></td>
			</tr>
			<tr>
			</tr>
			</table>
			
			<table>
				<tr>
					<td colspan="11" class="header">Assignment Data</td>
				</tr>
				<tr>
					<td class="header">Assignment Description</td>
					<td class="header">Work Area Description(Work Area)</td>
					<td class="header">Task</td>
					<td class="header">Regular Earn Code</td>
					
				</tr>
				<tr>
					<td></td>
					<td>${assignment.workAreaObj.description}(${assignment.workArea})</td>
					<td>${assignment.task}</td>
					<td>${assignment.job.payType.regEarnCode}</td>
				</tr>
				
				<!-- Put in approver information for each assignment here-->
			</table>
			
			<table>
				<tr>
					<td colspan="11" class="header">Funding Data</td>
				</tr>
				<tr>
					<td class="header">Account</td>
					<td class="header">SubAccount</td>
					<td class="header">Project Code</td>
					<td class="header">Object Code</td>
					<td class="header">Org Ref</td>
					<td class="header">Percent</td>
				</tr>
			</table>
		</c:forEach>		
	


</div>

</tk:tkHeader>
