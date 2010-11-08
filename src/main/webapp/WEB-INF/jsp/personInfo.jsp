<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>
<c:set var="Form" value="${PersonInfoActionForm}" scope="request"/>

<tk:tkHeader tabId="personInfo">
	<div class="person-detail">
		<div id="person-detail-accordion">
			<c:forEach var="job" items="${Form.jobNumberToListAssignments}">
				<c:forEach var="assignment" items="${job.value}">
					<h3><a href="#">Job Number: ${assignment.jobNumber }</a></h3>
					<div>
						<p class="job">  
						<b>Job Details : </b>
							<ul>
								<li>Network ID: ${assignment.principalId } </li> 
								<li>Department: ${assignment.job.dept }</li>
								<li>Salary Group: ${assignment.job.tkSalGroup }</li>
								<li>Pay Grade: ${assignment.job.payGrade }</li>
								<li>Standard Hours: ${assignment.job.standardHours }</li>
								<li>FTE: ${assignment.job.fte }</li>
								<li>Pay Type: ${assignment.job.payType.descr}</li>
								<li>Location: ${assignment.job.location }</li>
								<li>Compensation Rate: ${assignment.job.compRate }</li>
							</ul>
						</p>
						<p class="assignment">
							<b>Assignment : </b>
							<ul>
								<li>Work Area - Description: ${assignment.workArea} - ${assignment.workAreaObj.description } </li> 
								<li>Task: ${assignment.task}</li> 
							</ul>
						</p>
						<p class="approver">
							<b>Approvers : </b>
							<ul>
							<c:forEach var="workAreaToApprover" items="${Form.workAreaToApprover }">
								<c:forEach var="approver" items="${workAreaToApprover.value}">
									<c:set var="person" value="${Form.principalIdToPerson[approver.principalId]}"/>
										<li>Name: <a href="mailto:${person.emailAddress }">${person.name }</a> ; Phone Number: ${person.phoneNumber }</li>
								</c:forEach>
							</c:forEach>
							</ul>
						</p>
						<p class="orgAdmin">
							<b>Organization Admin : </b>
							<ul>
							<c:forEach var="deptToOrgAdmin" items="${Form.deptToOrgAdmin }">
								<c:forEach var="orgAdmin" items="${deptToOrgAdmin.value}">
									<c:set var="person" value="${Form.principalIdToPerson[orgAdmin.principalId]}"/>
										<li>Name: <a href="mailto:${person.emailAddress }">${person.name }</a> ; Phone Number: ${person.phoneNumber }</li>
								</c:forEach>
							</c:forEach>
							</ul>
						</p>
					</div>
				</c:forEach>
			</c:forEach>
		</div>
	</div>
</tk:tkHeader>
