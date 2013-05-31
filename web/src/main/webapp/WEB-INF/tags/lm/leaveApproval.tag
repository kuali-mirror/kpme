<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<jsp:useBean id="tagSupport" class="org.kuali.kpme.tklm.common.TagSupport"/>

<br/>
<table width="100%"><tr><td width="80%" valign="top"><div id='calendar'></div>&nbsp;</td>

<td width="20%"  valign="top">&nbsp;
<div id="leave-approval">
	<display:table name="${Form.leaveApprovalRows}" requestURI="LeaveApproval.do" excludedParams="*"
	               pagesize="20" id="row"
	               class="approvals-table"
	               size="${Form.resultSize}" 
	               partialList="true"
	               defaultsort="0"
	               defaultorder="ascending"
	               sort="external">
        <%--<display:caption style="text-align:right; margin-right:205px;">
            <div>approved/usage: <span class="approvals-approved">bold</span></div><div>planned/defered: <span class="approvals-requested">italics</span></div>
        </display:caption>--%>
		 <display:column  style="background-color: ${row.color};width:8px ; vertical-align:middle" >
		 <c:if test="${not empty row.documentId }">
	            <div class="ui-state-default ui-corner-all" >
	                <span id="showLeaveDetailButton_${row.documentId}" class="ui-icon ui-icon-plus rowInfo"></span>
	            </div>
          </c:if>
          </display:column>
        <display:column title="Name" sortable="true" sortName="name" style="${row.moreThanOneCalendar ? 'background-color: #F08080;' : ''}">
	    	
	    
	        <a href="changeTargetPerson.do?${row.userTargetURLParams}&targetUrl=PersonInfo.do&returnUrl=LeaveApproval.do">${row.name}</a> (${row.principalId})
	         <br/>${row.lastApproveMessage}
	    </display:column>
	  
        <display:column title="Document ID <br/>&amp; Status" sortable="true" sortName="documentId">
        <c:choose>
	         <c:when test="${row.exemptEmployee}">
	        	 <a href="changeTargetPerson.do?${row.userTargetURLParams}&targetUrl=LeaveCalendar.do%3FdocumentId=${row.documentId}&returnUrl=LeaveApproval.do">${row.documentId}</a>
	         </c:when>
	         <c:otherwise>
	         	<c:out value="Non-Exempt Employee"/>
	         </c:otherwise>
        </c:choose>
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
   		<%--</display:column>--%>
        </display:column>
       
   		<display:column title="Action">
            <c:if test="${row.exemptEmployee}">
                <lm:lmApprovalRowButtons appRow="${row}"/>
            </c:if>
   		</display:column>
   		<display:column title="Select All <input type='checkbox' name='Select' id='checkAllAuto'></input>"
                    class="last_column_${row_rowNum}">
            <c:if test="${row.exemptEmployee}">
	       		<html:checkbox property="leaveApprovalRows[${row_rowNum-1}].selected" disabled="${!row.approvable}"
	                       styleClass="selectedEmpl"/>
       		</c:if>
        	<div id="leaveDetails_${row.documentId}" style="display: none;"></div>
   		</display:column>

	</display:table>
</div>
</td></tr></table>