<%@include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<c:set var="Form" value="${TimeDetailActionForm}" scope="request"/>


<html>
<head>
     <tk:tkInclude/>
     <style type="text/css">
		<%@ include file="../../kr/css/kuali.css" %>
	 </style>
</head>
<body>
 <br/>
 <br/>
		<p class="actualtime-header">Acutal Time Inquiry</p>
   	 	<div id="actualTime" align="left" class="actualtime">
   	 	    <display:table name="${Form.clockLogTimeBlockList}" class="actualtime-table datatable-100"
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
				<input type="submit" name="close"
				class="button ui-button ui-widget ui-state-default ui-corner-all"
				value="Close" alt="close" onclick="javascript: window.close();return false;">
			</p>

		</div>

 </body>
</html>

