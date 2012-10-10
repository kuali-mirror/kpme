<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<jsp:useBean id="tagSupport" class="org.kuali.hr.time.util.TagSupport"/>

<div id="leave-approval">
	<display:table name="${Form.leaveApprovalRows}" requestURI="LeaveApproval.do?methodToCall=loadApprovalTab" excludedParams="*"
	               pagesize="20" id="row"
	               class="approvals-table" partialList="true" size="${Form.resultSize}" sort="external" defaultsort="0">
	    <display:column title="Name" sortable="true" sortName="name" style="${row.moreThanOneCalendar ? 'background-color: #F08080;' : ''}">
	    	<c:if test="${not empty row.documentId }">
	            <div class="ui-state-default ui-corner-all" style="float:left;">
	                <span id="showLeaveDetailButton_${row.documentId}" class="ui-icon ui-icon-plus rowInfo"></span>
	            </div>
            </c:if>
	    
	        <a href="changeTargetPerson.do?${row.userTargetURLParams}&targetUrl=PersonInfo.do&returnUrl=LeaveApproval.do">${row.name}</a> (${row.principalId})
	         <br/>${row.lastApproveMessage}
	    </display:column>
	    <display:column title="Document ID" sortable="true" sortName="documentId">
        	<a href="changeTargetPerson.do?${row.userTargetURLParams}&targetUrl=LeaveCalendar.do%3FdocumentId=${row.documentId}&returnUrl=LeaveApproval.do">${row.documentId}</a>
        </display:column>
	    <display:column title="Status" sortable="true" sortName="status">
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
        	<lmApprovalRowButtons appRow="${row}"/>
   		</display:column>
   		<display:column title="Select All <input type='checkbox' name='Select' id='checkAllAuto'></input>"
                    class="last_column_${row_rowNum}">
       		<html:checkbox property="leaveApprovalRows[${row_rowNum-1}].selected" disabled="${!row.approvable}"
                       styleClass="selectedEmpl"/>
       
        	<div id="leaveDetails_${row.documentId}" style="display: none;"></div>
   		</display:column>
	    
	</display:table>

</div>