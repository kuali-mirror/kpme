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

