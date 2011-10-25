<%@include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<c:set var="Form" value="${TimeDetailActionForm}" scope="request"/>

<tk:tkHeader tabId="actualTimeDistribute" >
	<html:form action="/TimeDetail.do">

		<p class="actualtime-header">Acutal Time Inquiry</p>
   	 	<div id="actualTime" align="left" class="actualtime">
   	 	    <display:table name="${Form.clockLogTimeBlockList}" class="actualtime-table"
		   		requestURI="/TimeDetail.do?methodToCall=actualTimeInquiry&documentId=${Form.documentId}"
		    	excludedParams="*" pagesize="10" style="border-collapse:collapse;border: 1px solid #000;" 
		    	defaultsort="1" defaultorder="ascending">   
				
				<display:column property="beginTimeDisplayDateOnlyString" sortable="true" title="Date" />
				<display:column property="assignmentDescription" sortable="true" title="Assignment" />
				<display:column property="beginTimeDisplayTimeOnlyString" sortable="true" title="Begin Time" />
				<display:column property="actualBeginTimeString" sortable="true" title="Actual Begin Time" />
				<display:column property="endTimeDisplayTimeOnlyString" sortable="true" title="End Time" />
				<display:column property="actualEndTimeString" sortable="true" title="Actual End Time" />
			</display:table>
			
			<p align="center">
				<input type="submit" name="close" class="button" value="Close" alt="close" onclick="javascript: window.close();return false;">
			</p>
			
		</div>

 	</html:form>

</tk:tkHeader>

