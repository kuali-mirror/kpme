<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<jsp:useBean id="tagSupport" class="org.kuali.kpme.tklm.common.TagSupport"/>
<jsp:useBean id="workflowTagSupport" class="org.kuali.kpme.tklm.common.WorkflowTagSupport"/>
<%@ attribute name="timeSummary" required="true" type="org.kuali.kpme.tklm.time.timesummary.TimeSummary"%>
<c:set var="headerLength" value="${fn:length(timeSummary.summaryHeader)}"/>  
<c:set var="workedHoursLength" value="${fn:length(timeSummary.workedHours)}"/>  
<c:set var="beginPosition" value="${workedHoursLength - headerLength}"/>  

<div id="timesheet-summary">
	<div class="summaryTitle" style="clear:both; text-align:center; font-weight: bold; margin-bottom: 5px;">Summary</div>
    <div id="timesheet-table-basic">
        <table>
            <thead>
                <tr class="ui-state-default">
                    <th></th>
                    <c:forEach items="${timeSummary.summaryHeader}" var="entry">
                        <th scope="col">${entry}</th>
                    </c:forEach>
                </tr>
            </thead>
            <tbody>
                <tr style="border-bottom-style: double; font-weight: bold;">
    				<td>Worked Hours:</td>
                    <c:if test="${beginPosition<0}">
                        <c:set var="beginPosition" value="0" />
                    </c:if>
                    <c:forEach items="${timeSummary.workedHours}" begin="${beginPosition}" var="entry">
                        <td>${entry}</td>
                    </c:forEach>
                </tr>
                <c:forEach items="${timeSummary.sections}" var="section">
                    <c:forEach items="${section.earnCodeSections}" var="earnCodeSection">
                        <tr>
                            <td colspan="${fn:length(timeSummary.workedHours) + 1}" class="earnCodeCell">${earnCodeSection.earnCode}: ${earnCodeSection.description}</td>
						</tr>
						<c:forEach items="${earnCodeSection.assignmentsRows}" var="assignmentRow">
                            <c:set var="periodTotal" value="${assignmentRow.assignmentColumns[fn:length(assignmentRow.assignmentColumns) - 1].total}"/>
                            <c:choose>
                                <c:when test="${earnCodeSection.isAmountEarnCode}">
                                    <tr style="border-bottom-style: double; font-weight: bold;">
                                        <td class="${assignmentRow.cssClass}">${assignmentRow.descr}</td>
                                        <c:forEach items="${assignmentRow.assignmentColumns}" var="assignmentColumn">
                                            <c:choose>
                                                <c:when test="${assignmentColumn.amount ne '0.00' and assignmentColumn.amount != 0}">
                                                    <c:choose>
                                                        <c:when test="${assignmentColumn.weeklyTotal}">
                                                            <td class="${assignmentColumn.cssClass}">$${assignmentColumn.amount}</td>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <td>$${assignmentColumn.amount}</td>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                    <td></td>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${periodTotal ne '0.00' and periodTotal != 0}">
                                        <tr style="border-bottom-style: double; font-weight: bold;">
                                            <td class="${assignmentRow.cssClass}">${assignmentRow.descr}</td>
                                            <c:forEach items="${assignmentRow.assignmentColumns}" var="assignmentColumn">
                                                <c:choose>
                                                    <c:when test="${assignmentColumn.total ne '0.00' and assignmentColumn.total != 0}">
                                                        <c:choose>
                                                            <c:when test="${assignmentColumn.weeklyTotal}">
                                                                <td class="${assignmentColumn.cssClass}">${assignmentColumn.total}</td>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <td>${assignmentColumn.total}</td>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td></td>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </tr>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </c:forEach>
                    <tr>
                        <td class="earnGroupTotalRow">${section.earnGroup} Totals</td>
                        <c:forEach items="${section.totals}" var="entry">
                            <c:choose>
                                <c:when test="${entry ne '0.00' and entry != 0}">
                                    <td class="earnGroupTotalRow">${entry}</td>
                                </c:when>
                                <c:otherwise>
                                    <td class="earnGroupTotalRow"></td>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
	</div>
	<c:if test="${not empty timeSummary.maxedLeaveRows}">
	<div id="maxed-leave-table">
		<table>
			<thead>
				<td scope="col">Accrual Category</td>
				<td scope="col">Prior Year Carryover</td>
				<td scope="col" title="Accruals through the end of the prior leave calendar">YTD Earned</td>
				<td scope="col" title="Usage through the end of the displayed leave calendar">YTD Usage</td>
				<td scope="col" title="As of current leave calendar/usage limit not considered">Accrued Balance</td>
	            <td scope="col" style="border: 3px double #ccc;font-weight: bold" title="As of current leave calendar/usage limit and future usage considered">Available Balance</td>
				<td scope="col" title="Amount of usage allowed per plan">Usage Limit</td>
	            <td scope="col" title="Total usage on future calendars">Future/Planned Usage</td>
				<td scope="col" title="Total usage of Family Medical Leave codes">YTD FMLA Usage</td>
			</thead>
		<tbody>
			<c:forEach items="${timeSummary.maxedLeaveRows}" var="maxedLeaveRow">
				<tr>
					<td>${maxedLeaveRow.accrualCategory}</td>	
					<td>${maxedLeaveRow.carryOver}</td>
					<td>${maxedLeaveRow.ytdAccruedBalance}</td>
					<td>${maxedLeaveRow.ytdApprovedUsage}</td>
		            <td>${maxedLeaveRow.accruedBalance}<c:if test="${not empty Form.documentId}"><c:if test="${maxedLeaveRow.transferable || maxedLeaveRow.payoutable}"><br/></c:if>
					<c:if test="${maxedLeaveRow.transferable}">
						<c:choose>
							<c:when test="${Form.currentTimesheet}">
								<input type="button" id="lm-transfer-button_${maxedLeaveRow.accrualCategoryRuleId}" class="button" value="Transfer" name="transfer"/>
							</c:when>
							<c:otherwise>
								<input disabled id="lm-transfer-button_${maxedLeaveRow.accrualCategoryRuleId}" class="button" value="Transfer" name="transfer"/>
							</c:otherwise>
						</c:choose>
					</c:if>
					<c:if test="${maxedLeaveRow.payoutable}">
						<c:choose>
							<c:when test="${Form.currentTimesheet}">
								<input type="button" id="lm-payout-button_${maxedLeaveRow.accrualCategoryRuleId}" class="button" value="Payout" name="payout"/>
							</c:when>
							<c:otherwise>
								<input disabled id="lm-payout-button_${maxedLeaveRow.accrualCategoryRuleId}" class="button" value="Payout" name="payout"/>
							</c:otherwise>
						</c:choose>
					</c:if>
					</c:if>
					</td>
					<td style="border: 3px double #ccc;border-bottom-color: gray;">${maxedLeaveRow.leaveBalance}</td>
					<td>${maxedLeaveRow.usageLimit}</td>
		            <td>${maxedLeaveRow.pendingLeaveRequests}</td>
					<td>${maxedLeaveRow.fmlaUsage}</td>
				</tr>
			</c:forEach>
		</tbody>
		</table>
		</div>
	</c:if>
</div>