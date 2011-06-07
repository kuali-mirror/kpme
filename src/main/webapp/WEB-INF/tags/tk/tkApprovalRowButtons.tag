<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<jsp:useBean id="tagSupport" class="org.kuali.hr.time.workflow.web.WorkflowTagSupport"/>
<%@ attribute name="appRow" required="true" type="org.kuali.hr.time.approval.web.ApprovalTimeSummaryRow"%>


<div class="">

    <div class="">
        <!-- Need to set the document ID so that TimesheetSubmit can find it -->
        <c:choose>
            <c:when test="${appRow.approvable}">
                <input type="button" id="ts-approve-button" class="button" value="Approve" name="approve" onclick="location.href='TimesheetSubmit.do?action=${tagSupport.approveAction}&${appRow.timesheetUserTargetURLParams}'"/>
            </c:when>
            <c:otherwise>
                <input disabled id="ts-approve-button" type="button" class="button" value="Approve" name="approve"/>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="">
        <input type="button" id="ts-ts-view" class="button" value="View Timesheet" name="timesheetView" onclick="location.href='TimeDetail.do?action=${appRow.timesheetUserTargetURLParams}'"/>
    </div>

</div>