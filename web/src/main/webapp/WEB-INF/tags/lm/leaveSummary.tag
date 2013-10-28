<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<jsp:useBean id="tagSupport" class="org.kuali.kpme.tklm.common.TagSupport"/>
<jsp:useBean id="workflowTagSupport" class="org.kuali.kpme.tklm.common.WorkflowTagSupport"/>

<%@ attribute name="leaveSummary" required="true" type="org.kuali.kpme.tklm.leave.summary.LeaveSummary"%>
<%@ attribute name="principalId" required="false" type="java.lang.String"%>

<c:choose>
<c:when test="${not empty principalId}">
<c:set var="mainDivId" value="leaveApprovalSummary_${principalId}"/>
<c:set var="innerDivId" value="approval-leave-summary-table"/>
<c:set var="displayString" value="none"/>
</c:when>
<c:otherwise>
<c:set var="mainDivId" value="leave-summary"/>
<c:set var="innerDivId" value="leave-summary-table"/>
<c:set var="displayString" value=""/>
</c:otherwise>
</c:choose>
<div id="${mainDivId}" style="display: ${displayString}">
	<c:if test="${not empty leaveSummary.leaveSummaryRows}">
		<div class="summaryTitle" style="clear:both; text-align:center; font-weight: bold; "> </div>
			<div id="${innerDivId}">
				<table>
					<thead>
					 <c:if test="${empty principalId}">
                        <tr class="noborder">
                            <td width="20%" align="left"> ${leaveSummary.note}</td>
                            <td colspan="7">last approved: ${leaveSummary.ytdDatesString}</td>
                            <td width="20%"></td>
                        </tr>
                     </c:if>
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
                            <td scope="col" style="border: 3px double #ccc;font-weight: bold" title="As of current leave calendar/usage limit and future usage considered">Available Balance</td>
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
                                <td>${row.accruedBalance}<c:if test="${not empty Form.documentId}"><c:if test="${row.transferable || row.payoutable}"><br/></c:if>
    							<c:if test="${row.transferable}">
    								<c:choose>
    									<c:when test="${Form.onCurrentPeriod}">
            								<input type="button" id="lm-transfer-button_${row.accrualCategoryRuleId}" class="button" value="Transfer" name="transfer"/>
    									</c:when>
    									<c:otherwise>
    										<input disabled id="lm-transfer-button_${row.accrualCategoryRuleId}" class="button" value="Transfer" name="transfer"/>
    									</c:otherwise>
    								</c:choose>
								</c:if>
    							<c:if test="${row.payoutable}">
     								<c:choose>
    									<c:when test="${Form.onCurrentPeriod}">
	                						<input type="button" id="lm-payout-button_${row.accrualCategoryRuleId}" class="button" value="Payout" name="payout"/>
	        							</c:when>
	        							<c:otherwise>
	        								<input disabled id="lm-payout-button_${row.accrualCategoryRuleId}" class="button" value="Payout" name="payout"/>
	        							</c:otherwise>
	        						</c:choose>
								</c:if>
								</c:if>
        						</td>
								<td style="border: 3px double #ccc;border-bottom-color: gray;">${row.leaveBalance}</td>
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
	