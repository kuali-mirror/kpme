<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<jsp:useBean id="tagSupport" class="org.kuali.hr.time.util.TagSupport"/>
<%@ attribute name="timeSummary" required="true" type="org.kuali.hr.time.timesummary.TimeSummary"%>
testing ${timeSummary}
<div id="timesheet-summary">
	<div style="clear:both; text-align:center; font-weight: bold; margin-top:20px; margin-bottom: 5px;">Summary <%--(<a href="#" id="basic">Basic</a> / <a href="#" id="advance">Advanced</a> ) --%></div>
	<div id="timesheet-table-basic">

	<table style="width:100%;">
	<thead>
		<tr class="ui-state-default">
		<td/>
		<c:forEach items="${timeSummary.dateDescr}" var="entry">
			<td>${entry}</td>
		</c:forEach>
		<td></td>
		<td></td>
		</tr>
	</thead>
	<tbody>
	<tr style="border-bottom-style: double; font-weight: bold;">
		<td>Worked Hours:</td>
	</tr>

		
	</tr>
	<tr style="">
		
	</tr>
	</tbody>
	</table>
	</div>
	</div>
</div>