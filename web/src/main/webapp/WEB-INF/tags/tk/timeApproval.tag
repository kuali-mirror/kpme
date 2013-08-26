<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<jsp:useBean id="tagSupport" class="org.kuali.kpme.tklm.common.TagSupport"/>


<br/>
<table width="100%"><tr>
<div id="time-approval">
	<display:table name="${Form.approvalRows}" requestURI="TimeApproval.do" excludedParams="*"
	               pagesize="20" id="row"
	               class="approvals-table" partialList="true" size="${Form.resultSize}" sort="external" defaultsort="0">

        <c:set var="nameStyle" value=""/>
	    <c:if test="${row.clockedInOverThreshold}">
	        <c:set var="nameStyle" value="background-color: #F08080;"/>
	    </c:if>
	    <display:column title="Name" sortable="true" sortName="name" style="${nameStyle};width:10%;">
	        <a href="changeTargetPerson.do?${row.timesheetUserTargetURLParams}&targetUrl=PersonInfo.do&returnUrl=TimeApproval.do%3FselectedPayCalendarGroup=${Form.selectedPayCalendarGroup}%26selectedDept=${Form.selectedDept}%26selectedWorkArea=${Form.selectedWorkArea}">${row.name}</a> (${row.principalId})
            <c:if test="${!empty row.clockStatusMessage}">
                <br/>${row.clockStatusMessage}
            </c:if>
	        <br/><br/><br/>
	       <c:if test="${!empty row.documentId}">
	       
	      Doc Id: <a href="changeTargetPerson.do?${row.timesheetUserTargetURLParams}&targetUrl=TimeDetail.do%3FdocumentId=${row.documentId}&returnUrl=TimeApproval.do%3FselectedPayCalendarGroup=${Form.selectedPayCalendarGroup}%26selectedDept=${Form.selectedDept}%26selectedWorkArea=${Form.selectedWorkArea}"> ${row.documentId}</a>
	        <c:if test="${fn:length(row.warnings) > 0 }">
	            <div class="ui-state-default ui-corner-all" style="float:right;">
	                <span id="approvals-warning" class="ui-icon ui-icon-alert approvals-warning"></span>
	            </div>
	            <div id="approvals-warning-details" class="approvals-warning-details"
	                 style="display:none; float:right; width: 600px; margin-left: 200px;">
	                <table>
	                    <thead>
	                    <tr>
	                        <th style="font-size: 1.2em; font-weight: bold; text-align: left;">Warnings:</th>
	                    </tr>
	                    </thead>
	                    <tbody>
	                    <c:forEach var="warning" items="${row.warnings}">
	                        <tr>
	                            <td>
	                                <div class="warning-note-message">
	                                        ${warning}
	                                </div>
	                            </td>
	                        </tr>
	                    </c:forEach>
	                    </tbody>
	                </table>
	            </div>
	        </c:if>
	        <c:if test="${fn:length(row.notes) > 0 }">
	            <div class="ui-state-default ui-corner-all" style="float:right;">
	                <span id="approvals-note" class="ui-icon ui-icon-note approvals-note"></span>
	            </div>
	            <div id="approvals-note-details" class="approvals-note-details"
	                 style="display:none; float:right; margin-left: 150px;">
	                <table>
	                    <thead>
	                    <tr>
	                        <th colspan="3" style="font-size: 1.2em; font-weight: bold; text-align: left;">
	                            Notes :
	                        </th>
	                    </tr>
	                    <tr>
	                        <th>Creator</th>
	                        <th>Created Date</th>
	                        <th>Content</th>
	                    </tr>
	                    </thead>
	                    <tbody>
	                    <c:forEach var="note" items="${row.notes}">
                            <jsp:setProperty name="tagSupport" property="principalId" value="${note.authorPrincipalId}"/>
	                        <tr>
	                            <td>${tagSupport.principalFullName}</td>
	                            <td style="width: 30px;">${note.createDate}</td>
	                            <td>
	                                <div class="warning-note-message">
	                                        ${note.text}
	                                </div>
	                            </td>
	                        </tr>
	                    </c:forEach>
	                    </tbody>
	                </table>
	            </div>
	        </c:if>

	    <%--<display:column title="Status" sortable="true" sortName="status">--%>
	    	<br/>
       		<div>
	            <span id="approvals-status" class="approvals-status">${row.approvalStatus}</span>
	        </div>
	       </c:if>
	    <%--</display:column>--%>
        </display:column>	
<%-- 	    <c:forEach var="payCalLabel" items="${Form.payCalendarLabels}"> --%>
	       
<%-- 	            <c:choose> --%>
<%-- 	                <c:when test="${fn:contains(payCalLabel,'Period')}"> --%>
<%-- 	                 <display:column title="${payCalLabel}"> --%>
<%-- 	                    <span style="font-weight: bold;">${row.hoursToPayLabelMap[payCalLabel]}</span> --%>
<%-- 	                   </display:column> --%>
<%-- 	                </c:when> --%>
<%-- 	            </c:choose> --%>
	        
<%-- 	    </c:forEach> --%>
	     <display:column title="Time Summary" style="width:80%;">
	        <%-- render time summary --%>
            <c:if test="${row.timeSummary != null}">
            	<tk:timeApprovalSummary timeApprovalSummary="${row.timeSummary}" principalId="${row.principalId}"/>
            </c:if>
            
	    </display:column>
	    <display:column title="Action" style="width:5%;">
	        <tk:tkApprovalRowButtons appRow="${row}"/>
	    </display:column>
	    <display:column title="Select All <input type='checkbox' name='Select' id='checkAllAuto'></input>"
	                    class="last_column_${row_rowNum}" style="width:5%;">
	        <html:checkbox property="approvalRows[${row_rowNum-1}].selected" disabled="${!row.approvable}"
	                       styleClass="selectedEmpl"/>
	        <%-- This is where we will insert the hour details --%>
	        <div id="hourDetails_${row.documentId}" style="display: none;"></div>
	    </display:column>
	</display:table>
</div>

</tr></table>