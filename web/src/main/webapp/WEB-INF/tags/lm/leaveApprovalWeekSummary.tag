<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<jsp:useBean id="tagSupport" class="org.kuali.kpme.tklm.common.TagSupport"/>
<jsp:useBean id="workflowTagSupport" class="org.kuali.kpme.tklm.common.WorkflowTagSupport"/>
<%@ attribute name="leaveApprovalWeekSummary" required="true" type="org.kuali.kpme.tklm.leave.approval.web.ApprovalLeaveSummaryRow"%>
<%@ attribute name="principalId" required="true" type="java.lang.String"%>
<%@ attribute name="earnCodeLeaveHours" required="true" type="java.util.Map"%>

<div id="timeapproval-summary">	
    <div id="timesheet-table-basic">
        <table border="1" >
        <thead>
                <tr class="ui-state-default"  >
                	<th width="5%" style="border-right: none"></th>
                    <th width="20%" style="border-left: none">Week</th>
                    <c:forEach items="${leaveApprovalWeekSummary.weeklyDistribution}" var="entry">
                        <th width="10%" scope="col">${entry.value}</th>
                    </c:forEach>
                </tr>
            </thead>
            <tbody>
              <c:forEach items="${leaveApprovalWeekSummary.weekDateList}" var="entry">
              
              <tr>
              	<td style="border-right-style: none">
					            	
	              	<c:set var="weekString" value="${fn:replace(entry.key, ' ', '')}" />
	           	</td>	            
              	<td style="border-left: none"> 
						${entry.key}
						<br/>
						(${leaveApprovalWeekSummary.weekDates[entry.key]})
              	</td> 	
              	<c:forEach var="leaveCalendarDate" items="${entry.value}">
		        	<td style="${not fn:contains(Form.leaveCalendarDates, leaveCalendarDate) ? 'background-color:rgb(224, 235, 225);' : ''}">
		        		<c:forEach var="earnCodeMap" items="${leaveApprovalWeekSummary.earnCodeLeaveHours[leaveCalendarDate]}" >
		                    <c:set var="key" value="${fn:split(earnCodeMap.key, '|')}"/>
		                    <c:set var="ac" value="${key[0]}"/>
		                    <c:set var="styleClass" value="approvals-default"/>
		                    <c:set var="status" value="${key[1]}"/>
		                      <c:choose>
		                        <c:when test="${status == 'A' or status == 'U'}">
		                            <c:set var="styleClass" value="approvals-approved"/>
		                        </c:when>
		                        <c:when test="${status == 'P' or status == 'F'}">
		                            <c:set var="styleClass" value="approvals-requested"/>
		                        </c:when>
		                      </c:choose>
		                    <div class="${styleClass}">
		                      ${ac}<br/>
		                      ${earnCodeMap.value}
		                    </div>
		        		</c:forEach>
		        	</td>
		   		</c:forEach> 
              </tr>
              </c:forEach>
		       <tr>
		       <td colspan="9">
		       <lm:leaveSummary leaveSummary="${leaveApprovalWeekSummary.leaveSummary}" principalId="${leaveApprovalWeekSummary.principalId}"/></td>
		       </tr>
			</tbody>
		</table>
		</div>
	
</div>