<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<jsp:useBean id="tagSupport" class="org.kuali.hr.time.util.TagSupport"/>
<%@ attribute name="leaveSummary" required="true" type="org.kuali.hr.lm.leaveSummary.LeaveSummary"%>
 
<div id="leave-summary">
	<c:if test="${not empty leaveSummary.leaveSummaryRows}">
		<div class="summaryTitle" style="clear:both; text-align:center; font-weight: bold; margin-bottom: 5px;">${leaveSummary.pendingDatesString} </div>
			<div id="leave-summary-table">
				<table>
					<thead>
                        <tr class="noborder">
                            <td colspan="9">last approved: ${leaveSummary.ytdDatesString}</td>
                        </tr>
						<%--<tr class="noborder" >
							<td></td>
							<td></td>
							<td colspan="2" class="${empty leaveSummary.ytdDatesString ? '' : 'withborder' }">${leaveSummary.ytdDatesString}</td>
							<td></td>
							<td colspan="2" class="${empty leaveSummary.pendingDatesString ? '' : 'withborder' }">${leaveSummary.pendingDatesString}</td>
							<td></td>
							<td></td>
						</tr>--%>
						<tr class="ui-state-default">
							<td scope="col">Accrual Category</td>
							<td scope="col">Prior Year Carryover</td>
							<td scope="col" title="Accruals through the end of the prior leave calendar">YTD Earned</td>
							<td scope="col" title="Usage through the end of the displayed leave calendar">YTD Usage</td>
							<td scope="col" title="As of current leave calendar/usage limit not considered">Accrued Balance</td>
                            <td scope="col" title="As of current leave calendar/usage limit and future usage considered">Available Balance</td>
							<td scope="col" title="Amount of usage allowed per plan">Usage Limit</td>
                            <td scope="col" title="Total usage on future calendars">Future/Planned Usage</td>
							<td scope="col" title="Total usage of Family Medical Leave codes">YTD FMLA Usage</td>
						</tr>
					</thead>
			    	<tbody>
			    		<c:forEach items="${leaveSummary.leaveSummaryRows}" var="row">
							<tr style="border-bottom-style: double; font-weight: bold;">
								<td>${row.accrualCategory}</td>	
								<td>${row.carryOver}</td>
								<td>${row.ytdAccruedBalance}</td>
								<td>${row.ytdApprovedUsage}</td>
                                <td>${row.accruedBalance}</td>
								<td>${row.leaveBalance}</td>
								<td>${row.usageLimit}</td>
                                <td>${row.pendingLeaveRequests}</td>
								<td>${row.fmlaUsage}</td>								
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
</div>
	