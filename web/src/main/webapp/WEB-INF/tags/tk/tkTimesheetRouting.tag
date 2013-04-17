<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<jsp:useBean id="tagSupport" class="org.kuali.hr.time.workflow.web.WorkflowTagSupport"/>


<div id="timesheet-routing">

    <c:if test="${tagSupport.displayingTimesheetRouteButton}">
        <c:choose>
            <c:when test="${tagSupport.routeTimesheetButtonEnabled}">
             	<c:choose>
	            	<c:when test="${not empty Form.forfeitures}">
		                <input id="ts-route-button" type="button" class="button" value="Submit for Approval" name="route"/>
		            </c:when>
		            <c:otherwise>
               <input id="ts-route-button" type="button" class="button" value="Submit for Approval" name="route" onclick="location.href='TimesheetSubmit.do?action=${tagSupport.routeAction}&documentId=${tagSupport.timesheetDocumentId}&methodToCall=approveTimesheet'"/>
 					</c:otherwise>
				</c:choose>
            
            </c:when>
            <c:otherwise>
                <input disabled id="ts-route-button" type="button" class="button" value="Submit for Approval" name="route"/>
            </c:otherwise>
        </c:choose>
    </c:if>


    <c:if test="${tagSupport.displayingTimesheetApprovalButtons}">
        <c:choose>
            <c:when test="${tagSupport.approvalTimesheetButtonsEnabled}">
                <input type="button" id="ts-approve-button" class="button" value="Approve" name="approve" onclick="location.href='TimesheetSubmit.do?action=${tagSupport.approveAction}&methodToCall=approveTimesheet&documentId=${tagSupport.timesheetDocumentId}'"/>
                <!--<input type="button" id="ts-disapprove-button" class="button" value="Disapprove" name="disapprove" onclick="location.href='TimesheetSubmit.do?action=${tagSupport.disapproveAction}&documentId=${tagSupport.timesheetDocumentId}'"/>-->
            </c:when>
            <c:otherwise>
                <input disabled id="ts-approve-button" type="button" class="button" value="Approve" name="approve"/>
                <!-- <input disabled id="ts-disapprove-button" type="button" class="button" value="Disapprove" name="disapprove"/> -->
            </c:otherwise>
        </c:choose>
    </c:if>

</div>