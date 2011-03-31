<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<jsp:useBean id="tagSupport" class="org.kuali.hr.time.util.TagSupport"/>

	<div class="approvals">

		<table id="approvals-table">
			<tr>
				<td colspan="22" align="center" style="border:none;">
					<span style="font-weight: bold; font-size: 1.5em;">${Form.name}</span>
				</td>
			</tr>
			<tr class="ui-state-default" style="border:none;">
				<td style="border: none;"></td>
				<td style="border: none;"></td>
				<td style="border: none;"></td>
				<c:forEach var="payCalLabel" items="${Form.payCalendarLabels}" >
					<td>${payCalLabel}</td>
				</c:forEach>
				<td><bean:message key="approval.status"/></td>
				<td>Select</td>
			</tr>			
			<c:forEach var="approveRow" items="${Form.approvalTimeSummaryRows}" varStatus="row">
				<tr>
				<td style="border: none;"><button class="expand" id="fran-button"></button></td>
				<td style="border: none;"><a href="TimeApproval.do?backdoorId=fran">${approveRow.name}<br/>${approveRow.clockStatusMessage}</a></td>
                <td style="border: none;">Worked Hours</td>	
				<c:forEach var="payCalLabel" items="${Form.payCalendarLabels}" >
					<td>${approveRow.hoursToPayLabelMap[payCalLabel]}</td>
				</c:forEach>
				<td>${approveRow.approvalStatus}</td>
				<td align="center"><input type="checkbox" name="selectedEmpl" /></td>         
			    </tr>   
			</c:forEach>
			<tr>
				<td colspan="22" align="center" style="border:none;">
					<input type="button" class="button" value="Approve" name="Approve">
					<input type="button" class="button" value="Disapprove" name="Disapprove">
				</td>
			</tr>
		</table>
	</div>

