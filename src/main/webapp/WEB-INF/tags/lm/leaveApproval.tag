<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<jsp:useBean id="tagSupport" class="org.kuali.hr.time.util.TagSupport"/>

<div id="leave-approval">
	<span style="font-size: 1.5em; vertical-align: middle;">Leave Calendar Approval</span>
	<display:table name="${Form.leaveApprovalRows}" requestURI="TimeApproval.do?methodToCall=loadApprovalTab" excludedParams="*"
	               pagesize="20" id="row"
	               class="approvals-table" partialList="true" size="${Form.resultSize}" sort="external" defaultsort="0">
	    <display:column title="Name" sortable="true" sortName="principalName" style="${row.moreThanOneCalendar ? 'background-color: #F08080;' : ''}">
	    	<c:if test="${not empty row.documentId }">
	            <div class="ui-state-default ui-corner-all" style="float:left;">
	                <span id="showLeaveDetailButton_${row.documentId}" class="ui-icon ui-icon-plus rowInfo"></span>
	            </div>
            </c:if>
	    
	        <a href="Admin.do?${row.userTargetURLParams}&targetUrl=PersonInfo.do&returnUrl=TimeApproval.do">${row.name}</a> (${row.principalId})
	         <br/>${row.lastApproveMessage}
	    </display:column>
	    <display:column title="Document ID" sortable="true" sortName="documentId">
        	<a href="Admin.do?${row.userTargetURLParams}&targetUrl=TimeDetail.do%3FdocumentId=${row.documentId}&returnUrl=TimeApproval.do">${row.documentId}</a>
        </display:column>
	    <display:column title="Status">
	        <div>
	            <span id="approvals-status" class="approvals-status">${row.approvalStatus}</span>
	        </div>
   		</display:column>
   		
   		<c:forEach var="leaveCalLabel" items="${Form.leaveCalendarLabels}">
        	<display:column title="${leaveCalLabel}">
        		<c:forEach var="earnCodeMap" items="${row.leaveHoursToPayLabelMap[leaveCalLabel]}" >
        			${earnCodeMap.key}<br/>
        			${earnCodeMap.value}<br/>
        		</c:forEach>
        	</display:column>
   		</c:forEach>
   		<display:column title="Action">
        	<lm:lmApprovalRowButtons appRow="${row}"/>
   		</display:column>
   		<display:column title="Select All <input type='checkbox' name='Select' id='checkAllAuto'></input>"
                    class="last_column_${row_rowNum}">
       		<html:checkbox property="approvalRows[${row_rowNum-1}].selected" disabled="${!row.approvable}"
                       styleClass="selectedEmpl"/>
       
        	<div id="leaveDetails_${row.documentId}" style="display: none;"></div>
   		</display:column>
	    
	</display:table>

</div>