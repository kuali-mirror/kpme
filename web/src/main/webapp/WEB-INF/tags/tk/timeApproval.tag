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
	        <c:set var="nameStyle" value="background-color: #F08080;width:20%;"/>
	    </c:if>
	    <display:column title="Name" sortable="true" sortName="name" style="${nameStyle};">
	        <a href="changeTargetPerson.do?${row.timesheetUserTargetURLParams}&targetUrl=PersonInfo.do&returnUrl=TimeApproval.do">${row.name}</a> (${row.principalId})
            <c:if test="${!empty row.clockStatusMessage}">
                <br/>${row.clockStatusMessage}
            </c:if>
	        <br/>
	    </display:column>
	    
	     <display:column title="Time Summary" style="width:60%;">
	        <%-- render time summary --%>
            <c:if test="${row.timeSummary != null}">
            	<tk:timeApprovalSummary timeApprovalSummary="${row.timeSummary}" principalId="${row.principalId}"/>
            </c:if>
            
	    </display:column>
	    <display:column title="Action">
	        <tk:tkApprovalRowButtons appRow="${row}"/>
	    </display:column>
	    <display:column title="Select All <input type='checkbox' name='Select' id='checkAllAuto'></input>"
	                    class="last_column_${row_rowNum}">
	        <html:checkbox property="approvalRows[${row_rowNum-1}].selected" disabled="${!row.approvable}"
	                       styleClass="selectedEmpl"/>
	        <%-- This is where we will insert the hour details --%>
	        <div id="hourDetails_${row.documentId}" style="display: none;"></div>
	    </display:column>
	</display:table>
</div>

</tr></table>