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
			<html:hidden property="assignKeyList" value="${Form.assignmentKeyList}"/>
			<html:hidden property="originalAssignment" value="${Form.currentAssignmentDescription}"/>
			<html:hidden property="beginTimestamp" value="${Form.currentTimeBlock.beginTimestamp}"/>
			<html:hidden property="endTimestamp" value="${Form.currentTimeBlock.endTimestamp}"/>
			<html:hidden property="hours" value="${Form.currentTimeBlock.hours}"/>
			<html:hidden property="beginDate" value="${Form.currentTimeBlock.beginDate}"/>
			<html:hidden property="endDate" value="${Form.currentTimeBlock.endDate}"/>
			<html:hidden property="beginTime" value="${Form.currentTimeBlock.beginTime}"/>
			<html:hidden property="endTime" value="${Form.currentTimeBlock.endTime}"/>

			<div id="clock" style="border-bottom: 1px solid gray;">
				<table style="border-collapse:collapse;border: 1px solid #000;">
					<thead>
						<tr class="ui-state-default">
							<td>Assignment</td>
							<td>Begin Date/Time</td>
							<td>End Date/Time</td>
							<td>Hours</td>
							<td>Action</td>
						</tr>
					</thead>
					<tbody>
							<tr>
								<td><c:out value="${Form.currentAssignmentDescription}" /></td>
								<td><c:out value="${Form.currentTimeBlock.beginTimestamp}" /></td>
								<td><c:out value="${Form.currentTimeBlock.endTimestamp}" /></td>
								<td><c:out value="${Form.currentTimeBlock.hours}" /></td>
								<td>
									<input class="button" value="Add" type="button" name="addTimeBlock" id="addTimeBlock"
										onclick="javascript: addTimeBlockRow(this.form);" />
								</td>
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
						</tr>
					</thead>
					<tbody>
					</tbody>
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

