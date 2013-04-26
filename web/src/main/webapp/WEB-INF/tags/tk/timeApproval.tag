<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<jsp:useBean id="tagSupport" class="org.kuali.hr.tklm.common.TagSupport"/>

<div id="time-approval">
	<display:table name="${Form.approvalRows}" requestURI="TimeApproval.do?methodToCall=loadApprovalTab" excludedParams="*"
	               pagesize="20" id="row"
	               class="approvals-table" partialList="true" size="${Form.resultSize}" sort="external" defaultsort="0">
	    <c:set var="nameStyle" value=""/>
	    <c:if test="${row.clockedInOverThreshold}">
	        <c:set var="nameStyle" value="background-color: #F08080;"/>
	    </c:if>
	    <display:column title="Name" sortable="true" sortName="name" style="${nameStyle}">
	        <c:if test="${row.periodTotal > 0}">
	            <div class="ui-state-default ui-corner-all" style="float:left;">
	                    <%--<span id="showDetailButton_${row_rowNum-1}" class="ui-icon ui-icon-plus rowInfo"></span>--%>
	                <span id="showDetailButton_${row.documentId}" class="ui-icon ui-icon-plus rowInfo"></span>
	            </div>
	        </c:if>
	        <a href="changeTargetPerson.do?${row.timesheetUserTargetURLParams}&targetUrl=PersonInfo.do&returnUrl=TimeApproval.do">${row.name}</a> (${row.principalId})
	        <br/>${row.clockStatusMessage}
	        <br/>
	    </display:column>
	    <display:column title="Document ID <br/>&amp; Status" sortable="true" sortName="documentId">
	        <a href="changeTargetPerson.do?${row.timesheetUserTargetURLParams}&targetUrl=TimeDetail.do%3FdocumentId=${row.documentId}&returnUrl=TimeApproval.do">${row.documentId}</a>
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
	    <c:forEach var="payCalLabel" items="${Form.payCalendarLabels}">
	        <display:column title="${payCalLabel}">
	            <c:choose>
	                <c:when test="${fn:contains(payCalLabel,'Week')}">
	                    <span style="font-weight: bold;">${row.hoursToPayLabelMap[payCalLabel]}</span>
	                    <br/>
	                    <span style="font-style: italic;">(${row.hoursToFlsaPayLabelMap[payCalLabel]})</span>
	                </c:when>
	                <c:otherwise>
	                    ${row.hoursToPayLabelMap[payCalLabel]}
	                </c:otherwise>
	            </c:choose>
	        </display:column>
	    </c:forEach>
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