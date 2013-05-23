<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<div id="timesheet-routing">
    <c:if test="${kpmefunc:isTimesheetRouteButtonDisplaying(Form.documentId)}">
        <c:choose>
            <c:when test="${kpmefunc:isTimesheetRouteButtonEnabled(Form.documentId)}">
             	<c:choose>
	            	<c:when test="${not empty Form.forfeitures}">
		                <input id="ts-route-button" type="button" class="button" value="Submit for Approval" name="route"/>
		            </c:when>
		            <c:otherwise>
                        <input id="ts-route-button" type="button" class="button" value="Submit for Approval" name="route" onclick="location.href='TimesheetSubmit.do?action=R&documentId=${Form.documentId}&methodToCall=approveTimesheet'"/>
 					</c:otherwise>
				</c:choose>
            
            </c:when>
            <c:otherwise>
                <input disabled id="ts-route-button" type="button" class="button" value="Submit for Approval" name="route"/>
            </c:otherwise>
        </c:choose>
    </c:if>


    <c:if test="${kpmefunc:isTimesheetApprovalButtonsDisplaying(Form.documentId)}">
        <c:choose>
            <c:when test="${kpmefunc:isTimesheetApprovalButtonsEnabled(Form.documentId)}">
                <input type="button" id="ts-approve-button" class="button" value="Approve" name="approve" onclick="location.href='TimesheetSubmit.do?action=A&methodToCall=approveTimesheet&documentId=${Form.documentId}'"/>
            </c:when>
            <c:otherwise>
                <input disabled id="ts-approve-button" type="button" class="button" value="Approve" name="approve"/>
            </c:otherwise>
        </c:choose>
    </c:if>
</div>