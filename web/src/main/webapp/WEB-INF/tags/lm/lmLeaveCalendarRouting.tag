<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<jsp:useBean id="tagSupport" class="org.kuali.kpme.tklm.common.WorkflowTagSupport"/>


<div id="timesheet-routing">

    <c:if test="${tagSupport.displayingLeaveRouteButton}">
        <c:choose>
            <c:when test="${tagSupport.routeLeaveButtonEnabled}">
            	<c:choose>
	            	<c:when test="${not empty Form.forfeitures}">
		                <input id="ts-route-button" type="button" class="button" value="Submit for Approval" name="route"/>
		            </c:when>
		            <c:otherwise>
		                <input id="ts-route-button" type="button" class="button" value="Submit for Approval" name="route" onclick="location.href='LeaveCalendarSubmit.do?action=${tagSupport.routeAction}&documentId=${tagSupport.leaveCalendarDocumentId}&methodToCall=approveLeaveCalendar'"/>
					</c:otherwise>
				</c:choose>
            </c:when>
            <c:otherwise>
                <input disabled id="ts-route-button" type="button" class="button" value="Submit for Approval" name="route"/>
            </c:otherwise>
        </c:choose>
    </c:if>


    <c:if test="${tagSupport.displayingLeaveApprovalButtons}">
        <c:choose>
            <c:when test="${tagSupport.approvalLeaveButtonsEnabled}">
                <input type="button" id="ts-approve-button" class="button" value="Approve" name="approve" onclick="location.href='LeaveCalendarSubmit.do?action=${tagSupport.approveAction}&methodToCall=approveLeaveCalendar&documentId=${tagSupport.leaveCalendarDocumentId}'"/>
            </c:when>
            <c:otherwise>
                <input disabled id="ts-approve-button" type="button" class="button" value="Approve" name="approve"/>
                <!-- <input disabled id="ts-disapprove-button" type="button" class="button" value="Disapprove" name="disapprove"/> -->
            </c:otherwise>
        </c:choose>
    </c:if>

</div>