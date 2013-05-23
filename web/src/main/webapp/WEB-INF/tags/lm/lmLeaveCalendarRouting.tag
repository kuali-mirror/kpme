<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<div id="timesheet-routing">
    <c:if test="${kpmefunc:isLeaveCalendarRouteButtonDisplaying(form.documentId)}">
        <c:choose>
            <c:when test="${kpmefunc:isLeaveCalendarRouteButtonEnabled(form.documentId)}">
            	<c:choose>
	            	<c:when test="${not empty Form.forfeitures}">
		                <input id="ts-route-button" type="button" class="button" value="Submit for Approval" name="route"/>
		            </c:when>
		            <c:otherwise>
		                <input id="ts-route-button" type="button" class="button" value="Submit for Approval" name="route" onclick="location.href='LeaveCalendarSubmit.do?action=R&documentId=${form.documentId}&methodToCall=approveLeaveCalendar'"/>
					</c:otherwise>
				</c:choose>
            </c:when>
            <c:otherwise>
                <input disabled id="ts-route-button" type="button" class="button" value="Submit for Approval" name="route"/>
            </c:otherwise>
        </c:choose>
    </c:if>


    <c:if test="${kpmefunc:isLeaveCalendarApprovalButtonsDisplaying(form.documentId)}">
        <c:choose>
            <c:when test="${kpmefunc:isLeaveCalendarApprovalButtonsEnabled(form.documentId)}">
                <input type="button" id="ts-approve-button" class="button" value="Approve" name="approve" onclick="location.href='LeaveCalendarSubmit.do?action=A&methodToCall=approveLeaveCalendar&documentId=${form.documentId}'"/>
            </c:when>
            <c:otherwise>
                <input disabled id="ts-approve-button" type="button" class="button" value="Approve" name="approve"/>
            </c:otherwise>
        </c:choose>
    </c:if>
</div>