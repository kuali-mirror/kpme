<%@include file="/WEB-INF/jsp/TkTldHeader.jsp"%>
<c:set var="Form" value="${ClockActionForm}" scope="request"/>

<tk:tkHeader tabId="timeDistribute" >
	<html:form action="/Clock.do">
    <div class="timeDistribute">
   	 	<div style="clear:both; text-align:center; font-weight: bold; margin-bottom: 5px;">Time Blocks to Distribute</div>
   	 	<div id="clock">
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
					<c:forEach var="timeBlockMap" items="${Form.timeBlocksMap}">
						<c:forEach var="tb" items="${timeBlockMap.value}">
							<tr>
								<td><c:out value="${timeBlockMap.key}" /></td>
								<td><c:out value="${tb.beginTimestamp}" /></td>
								<td><c:out value="${tb.endTimestamp}" /></td>
								<td><c:out value="${tb.hours}" /></td>
								<td>
									<input type="button" class="button" value="Edit" name="editTimeBlock" id="editTimeBlock"
									onclick="javascript:
										var urlString= extractUrlBase()+'/Clock.do?methodToCall=editTimeBlock&editTimeBlockId='+ ${tb.tkTimeBlockId};
										window.location=urlString;"/>
								</td>
							</tr>
						</c:forEach>
					</c:forEach>
				</tbody>
			</table>
			
			<div align="center">
				<input type="submit" name="close" class="button" value="Close" alt="close" onclick="javascript: window.close();return false;">
			</div>
		</div>
 	</div>

 	</html:form>

</tk:tkHeader>

<tk:note/>

