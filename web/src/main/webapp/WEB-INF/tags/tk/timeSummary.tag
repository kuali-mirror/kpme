<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<jsp:useBean id="tagSupport" class="org.kuali.kpme.tklm.common.TagSupport"/>
<jsp:useBean id="workflowTagSupport" class="org.kuali.kpme.tklm.common.WorkflowTagSupport"/>
<%@ attribute name="timeSummary" required="true" type="org.kuali.kpme.tklm.time.timesummary.TimeSummary"%>

<div id="timesheet-summary">
	<div class="summaryTitle" style="clear:both; text-align:center; font-weight: bold; margin-bottom: 5px;">Summary</div>
    <div id="timesheet-table-basic">
        <table border="1">
            <thead>
                <tr class="ui-state-default">
                	<th width="1%" style="border-right: none"></th>
                    <th style="border-left: none">Week</th>
                    <c:forEach items="${timeSummary.timeSummaryHeader}" var="entry">
                        <th width="7%" scope="col">${entry.value}</th>
                    </c:forEach>
                    <th width="8%">Total</th>
                    <th width="8%">FLSA Total</th>
                </tr>
            </thead>
            <tbody>
                    <c:forEach items="${timeSummary.weeklyWorkedHours}" var="entry">
                    <c:set var="weekString" value="${fn:replace(entry.key, ' ', '')}" />
	                    <tr style="font-weight: bold;">
	                        <td style="border-right-style: none">
	                        	<c:if test="${fn:length(timeSummary.weeklySections[entry.key]) > 0}">
		                        <div class="ui-state-default " style="width:15px;vertical-align: middle;" >
		                			<span id="weekSummary_${weekString}" class="ui-icon ui-icon-minus rowInfo"></span>
		            			</div>
		            			
		            			</c:if>
		            		
	            			</td>
	            			<td style="border-left: none">${entry.key}<br/>${timeSummary.weekDates[entry.key]}</td>
	                        <c:set var="weekHours" value="${entry.value}"/>
	                        <c:forEach items="${timeSummary.timeSummaryHeader}" var="hour">
	                        	<c:if test="${weekHours[hour.key] != null and not empty weekHours[hour.key]}">
	                        		<td>${weekHours[hour.key]}</td>
	                        	</c:if>
	                        	<c:if test="${weekHours[hour.key] == null or empty weekHours[hour.key]}">
	                        		<td style="background: rgb(224, 235, 225)">${weekHours[hour.key]}</td>
	                        	</c:if>
	                        </c:forEach>
	                        <td valign="middle">${timeSummary.weekTotalMap[entry.key]}  </td>
	                        <td valign="middle">${timeSummary.flsaWeekTotalMap[entry.key]}</td>
	                     </tr>
	              <tbody id="weekSummary${weekString}" style="display: table-row-group;">
                  <c:forEach items="${timeSummary.weeklySections[entry.key]}" var="section">
                    <c:forEach items="${section.earnCodeSections}" var="earnCodeSection">
                        <tr class="ui-state-default" style="font-weight: bold;">
                            <td class="earnCodeCell" colspan="2" style="border-right: none">${earnCodeSection.earnCode}: ${earnCodeSection.description}</td>
                            <td colspan="9" style="border-left: none"></td>
						</tr>
						<c:forEach items="${earnCodeSection.assignmentsRows}" var="assignmentRow">
							<c:set var="periodTotal" value="${assignmentRow.periodTotal}"/>
                            <c:choose>
                                <c:when test="${earnCodeSection.isAmountEarnCode}">
                                    <tr>
                                        <td colspan="2"  class="${not empty assignmentRow.descr?assignmentRow.cssClass: ''}">${assignmentRow.descr}</td>
                                        <c:forEach items="${timeSummary.timeSummaryHeader}" var="entry">
                                            <c:set var="assignmentColumn" value="${assignmentRow.assignmentColumns[entry.key]}"/>
                                            <c:choose>
                                                <c:when test="${assignmentColumn.amount ne '0.00' and assignmentColumn.amount != 0}">
                                                     <td class="${assignmentRow.cssClass}">$${assignmentColumn.amount}</td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td></td>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                         <td></td>
                                            <td/>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${periodTotal ne '0.00' and periodTotal != 0}">
                                        <tr>
                                           <td colspan="2" class="${not empty assignmentRow.descr?assignmentRow.cssClass: ''}">${assignmentRow.descr}</td>
                                           <c:forEach items="${timeSummary.timeSummaryHeader}" var="entry">
                                            	<c:set var="assignmentColumn" value="${assignmentRow.assignmentColumns[entry.key]}"/>   
                                               
                                             <c:choose>
                                                <c:when test="${assignmentColumn.total ne '0.00' and assignmentColumn.total != 0}">
                                                      <td class="${assignmentColumn.cssClass}">${assignmentColumn.total}</td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td></td>
                                                </c:otherwise>
                                            </c:choose>
                                            </c:forEach>
                                            <td>${assignmentRow.periodTotal}</td>
                                            <td/>
                                        </tr>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </c:forEach>
                    <tr style="font-weight: bold;border-bottom-style: double;border-top-style: double;">
                        <td class="earnGroupTotalRow" colspan="2">${section.earnGroup} Totals</td>
                        <c:forEach items="${timeSummary.timeSummaryHeader}" var="entry">
                        	 <c:choose>
                                <c:when test="${section.totals[entry.key] ne '0.00' and section.totals[entry.key] != 0}">
                                    <td class="earnGroupTotalRow">${section.totals[entry.key]}</td>
                                </c:when>
                                <c:otherwise>
                                    <td class="earnGroupTotalRow"></td>
                                </c:otherwise>
                             </c:choose>
                        </c:forEach>
                         <td>${section.earnGroupTotal}</td>
                         <td/>
                    </tr>
                </c:forEach>
                </tbody>
              </c:forEach>
              <tr>
              <td colspan="2" style="color: #993333;font-weight: bold;">Pay Period Total</td>
              <td colspan="7"></td>
              <td  colspan="2" style="font-weight: bold;">${timeSummary.grandTotal}</td>
              </tr>

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
							<c:when test="${Form.currentTimesheet}">ClockLocationRuleLookupTest
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