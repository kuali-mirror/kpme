<%--

    Copyright 2004-2012 The Kuali Foundation

    Licensed under the Educational Community License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.opensource.org/licenses/ecl2.php

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

--%>
<%@include file="/WEB-INF/jsp/TkTldHeader.jsp"%>
<c:set var="Form" value="${ClockActionForm}" scope="request"/>

<tk:tkHeader tabId="timeDistribute" >
	<html:form action="/Clock.do">
    <div class="timeDistribute">
    	<div style="text-align: center;">
    		<c:if test="${Form.prevDocumentId ne null}">
					<button 
						id="pay_period_prev" 
						role="button" 
						title="Previous"
						onclick="javascript:
								var urlString= extractUrlBase()+'/Clock.do?methodToCall=distributeTimeBlocks&documentId=' + ${Form.prevDocumentId};
								window.location=urlString;
								return false;">Previous</button>
        	 </c:if>
             
         <span class="header-title">
          <fmt:formatDate value="${Form.timesheetDocument.payCalendarEntry.beginPeriodDate}" pattern="MM/dd/yyyy"/> -
          <fmt:formatDate value="${Form.timesheetDocument.payCalendarEntry.endPeriodDate}" pattern="MM/dd/yyyy"/>
         </span>
             
          <c:if test="${Form.nextDocumentId ne null}">
          	<button 
          		id="pay_period_next" 
				role="button" 
				title="Next"
				onclick="javascript:
						var urlString= extractUrlBase()+'/Clock.do?methodToCall=distributeTimeBlocks&documentId=' + ${Form.nextDocumentId};
						window.location=urlString;
						return false;">Next</button>
          </c:if>
	   	</div>
	   
   	 	</p>
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
								<td><c:out value="${tb.beginTimeDisplayDateOnlyString} ${tb.beginTimeDisplayTimeOnlyString}" /></td>
								<td><c:out value="${tb.endTimeDisplayDateOnlyString} ${tb.endTimeDisplayTimeOnlyString}" /></td>
								<td><c:out value="${tb.hours}" /></td>
								<td>
									<input type="button" class="button" value="Edit" name="editTimeBlock" id="editTimeBlock"
									onclick="javascript:
										var urlString= extractUrlBase()+'/Clock.do?methodToCall=editTimeBlock&documentId=' + ${Form.documentId} + '&editTimeBlockId='+ ${tb.tkTimeBlockId};
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

