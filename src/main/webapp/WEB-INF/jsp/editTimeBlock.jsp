<%@include file="/WEB-INF/jsp/TkTldHeader.jsp"%>
<c:set var="Form" value="${ClockActionForm}" scope="request"/>

<tk:tkHeader tabId="editTimeBlock" >
	<html:form action="/Clock.do" styleId="editTimeBlockForm">
		<div style="clear:both; text-align:center; font-weight: bold; margin-bottom: 5px;">Distribute Hours</div>
 			<html:hidden property="methodToCall" value="" styleId="methodToCall"/>
 			<html:hidden property="newAssignDesCol" value="" styleId="newAssignDesCol"/>
 			<html:hidden property="newBDCol" value="" styleId="newBDCol"/>
 			<html:hidden property="newBTCol" value="" styleId="newBTCol"/>
 			<html:hidden property="newEDCol" value="" styleId="newEDCol"/>
 			<html:hidden property="newETCol" value="" styleId="newETCol"/>
 			<html:hidden property="newHrsCol" value="" styleId="newHrsCol"/>
 			<html:hidden property="tbId" value="${Form.editTimeBlockId}"  styleId="tbId"/>
 			<html:hidden property="originHrs" value="${Form.currentTimeBlock.hours}"  styleId="originHrs"/>
			<html:hidden property="assignmentList" value="${Form.assignDescriptionsList}"/>
			<html:hidden property="distributeAssignList" value="${Form.distributeAssignList}" />
			<html:hidden property="originalAssignment" value="${Form.currentAssignmentDescription}"/>
			<html:hidden property="beginTimestamp" value="${Form.currentTimeBlock.beginDistributeDate}"/>
			<html:hidden property="endTimestamp" value="${Form.currentTimeBlock.endDistributeDate}"/>
			<html:hidden property="hours" value="${Form.currentTimeBlock.hours}"/>

			<div id="clock" style="border-bottom: 1px solid gray;">
				<table style="border-collapse:collapse;border: 1px solid #000;">
					<thead>
						<tr class="ui-state-default">
							<td>Assignment</td>
							<td>Begin Date/Time</td>
							<td>End Date/Time</td>
							<td>Hours</td>
						</tr>
					</thead>
					<tbody>
							<tr>
								<td><c:out value="${Form.currentAssignmentDescription}" /></td>
								<td><c:out value="${Form.currentTimeBlock.beginTimestamp}" /></td>
								<td><c:out value="${Form.currentTimeBlock.endTimestamp}" /></td>
								<td><c:out value="${Form.currentTimeBlock.hours}" /></td>
							</tr>
					</tbody>
				</table>
		</div>

		<div id="clock">
		 	<p id="validation" class="validation">All form fields are required.</p>
		 	<div id="edit-panel">
				<table id="tblNewTimeBlocks" style="border-collapse:collapse;border: 1px solid #000;">
					<thead>
						<tr class="ui-state-default">
							<td>Count</td>
							<td>Assignment</td>
							<td>Begin Date</td>
							<td>Begin Time</td>
							<td>End Date</td>
							<td>End Time</td>
							<td>Hours</td>
							<td>Action</td>
						</tr>
						</thead>
						<tbody>
						<tr>
							<td>1</td>
							<td>
								 <select name="assignmentRow1" id="assignmentRow1">
									<c:forEach var="assignment" items="${Form.desList}">
										<c:choose>
											<c:when test='${assignment.value == Form.currentAssignmentDescription}'>
												<option value="${assignment.key}" selected>${assignment.value}</option>
											</c:when>
											<c:otherwise>
												<option value="${assignment.key}" >${assignment.value}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</td>
							<td>
								<input type="text" name="bdRow1" id="bdRow1" size="10" readonly="readonly"
                                       value="<fmt:formatDate type="both" dateStyle="full" value="${Form.currentTimeBlock.beginTimeDisplayDate}" pattern="MM/dd/yyyy"/>"/>
							</td>

							<td>
								<input name="btRow1" id="btRow1"
                                       size="10" value="<fmt:formatDate type="both" dateStyle="full" value="${Form.currentTimeBlock.beginTimeDisplayDate}" pattern="hh:mm aa"/>">
								<input type="button" style="width: 20px; height: 23px;" id="beginTimeHelp1" value="?"
									title="Supported formats:<br/>9a, 9 am, 9 a.m.,  9:00a, 9:45a, 3p, 0900, 15:30, 1530">
							</td>
							<td>
								<input type="text" name="edRow1" id="edRow1" size="10" readonly="readonly"
                                       value="<fmt:formatDate type="both" dateStyle="full" value="${Form.currentTimeBlock.endTimeDisplayDate}" pattern="MM/dd/yyyy"/>">
							</td>
							<td>
								<input name="etRow1" id="etRow1" size="10"
                                       value="<fmt:formatDate type="both" dateStyle="full" value="${Form.currentTimeBlock.endTimeDisplayDate}" pattern="hh:mm aa"/>">
								<input type="button" style="width: 20px; height: 23px;" id="endTimeHelp1" value="?"
									title="Supported formats:<br/>9a, 9 am, 9 a.m.,  9:00a, 9:45a, 3p, 0900, 15:30, 1530">
							</td>
							<td>
								<input name="hrRow1" id="hrRow1" size="5" readonly="" value="${Form.currentTimeBlock.hours}">
							</td>
							<td>
								<input class="button" value="Add" type="button" name="addTimeBlock" id="addTimeBlock"
									onclick="javascript: addTimeBlockRow(this.form);" />
							</td>
							</tr>
					</tbody>
					<tfoot>
						<tr>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td>Total Hours:</td>
							<td>
								<input name="hrsTotal" id="hrsTotal" size="5" readonly="" value="${Form.currentTimeBlock.hours}">
								</td>
							<td></td>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>


		<div align="center">
			 <input type="button" class="button" value="Save" name="saveTimeBlock" id="saveTimeBlock"/>
			<input type="submit" class="button" value="Cancel" name="cancel"
			onclick="javascript: window.close();return false;"/>
		</div>

	</html:form>
</tk:tkHeader>

<tk:note/>

