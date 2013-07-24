<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<jsp:useBean id="tagSupport" class="org.kuali.kpme.tklm.common.WorkflowTagSupport"/>
<%@ attribute name="appRow" required="true" type="org.kuali.kpme.tklm.time.approval.summaryrow.ApprovalTimeSummaryRow"%>


<div id="actions">
        <!-- Need to set the document ID so that TimesheetSubmit can find it -->
        <c:choose>
            <c:when test="${appRow.approvable}">
                <c:if test="${not empty appRow.roleName}">
                    ${appRow.roleName}: <br />
                </c:if>
                <input type="button" id="ts-approve-button" class="button" value="Approve" name="approve" onclick="location.href='changeTargetPerson.do?${appRow.timesheetUserTargetURLParams}&targetUrl=TimesheetSubmit.do%3Faction=${tagSupport.approveAction}%26documentId=${appRow.documentId}%26methodToCall=approveApprovalTab'"/>
            </c:when>
            <c:otherwise>
                <input disabled id="ts-approve-button" type="button" class="button" value="Approve" name="approve"/>
            </c:otherwise>
        </c:choose>
</div>