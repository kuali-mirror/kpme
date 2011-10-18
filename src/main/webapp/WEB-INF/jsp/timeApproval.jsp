<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>
<c:set var="Form" value="${TimeApprovalActionForm}" scope="request"/>
<jsp:useBean id="tagSupport" class="org.kuali.hr.time.util.TagSupport"/>

<tk:tkHeader tabId="approvals">
<html:form action="/TimeApproval.do" method="POST">
<html:hidden property="methodToCall" value=""/>
<html:hidden styleId="rit" property="rowsInTotal" value="${fn:length(Form.approvalRows)}"/>
<html:hidden styleId="pcid" property="hrPyCalendarId" value="${Form.hrPyCalendarId}"/>
<html:hidden styleId="pceid" property="hrPyCalendarEntriesId" value="${Form.hrPyCalendarEntriesId}"/>

<div class="approvals">
<table id="approvals-filter">
    <tr>
        <td>
            Pay Calendar Group:
            <label for="pay calendar groups">
                <select id="selectedPayCalendarGroup" name="selectedPayCalendarGroup"
                        onchange="this.form.methodToCall.value='selectNewPayCalendarGroup'; this.form.submit();">
                    <c:forEach var="payCalendarGroup" items="${Form.payCalendarGroups}">
                        <c:choose>
                            <c:when test="${Form.selectedPayCalendarGroup eq payCalendarGroup}">
                                <option value="${payCalendarGroup}" selected="selected">${payCalendarGroup}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${payCalendarGroup}">${payCalendarGroup}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </label>
        </td>
        <td>
            Department:
            <select id="selectedDept" name="selectedDept"
                    onchange="this.form.methodToCall.value='selectNewPayCalendarGroup'; this.form.submit();">
                <option value="">-- Select a department --</option>
                <c:forEach var="dept" items="${Form.departments}">
                    <c:choose>
                        <c:when test="${Form.selectedDept eq dept}">
                            <option value="${dept}" selected="selected">${dept}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${dept}">${dept}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
        </td>
        <td>
            Work Area:
            <label for="work areas">
                <select id="selectedWorkArea" name="selectedWorkArea"
                        onchange="this.form.methodToCall.value='selectNewPayCalendarGroup'; this.form.submit();">
                    <option value="">Show All</option>
                    <c:forEach var="deptWorkarea" items="${Form.deptWorkareas}">
                        <c:choose>
                            <c:when test="${Form.selectedWorkArea eq deptWorkarea}">
                                <option value="${deptWorkarea}" selected="selected">${deptWorkarea}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${deptWorkarea}">${deptWorkarea}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </label>
        </td>
    </tr>
</table>
<table class="navigation">
    <c:if test="${Form.selectedDept != null and Form.selectedPayCalendarGroup != null}">
        <tr>
            <td class="left">
                Search By :
                <label for="search field">
                    <select id="searchField" name="searchField">
                        <option value="">-- Select a field --</option>
                        <option value="principalName">Principal Name</option>
                        <option value="documentId">Document Id</option>
                    </select>
                </label>
                Value :
                <label for="search value">
                    <input id="searchValue" name="searchValue" type="text" placeholder="enter at least 3 chars"/>
                    <span id='loading-value' style="display:none;"><img src='images/ajax-loader.gif'></span>
                </label>
            </td>
            <td>
                <div style="text-align: center">
                    <c:if test="${Form.prevPayCalendarId ne null}">
                        <input type="button" class="prev" value="Previous" name="Previous"
                               onclick="this.form.hrPyCalendarEntriesId.value='${Form.prevPayCalendarId}'; this.form.submit();"/>
                    </c:if>
                    <span style="font-size: 1.5em; vertical-align: middle;">
                    <fmt:formatDate value="${Form.payBeginDate}" pattern="MM/dd/yyyy"/> -
                    <fmt:formatDate value="${Form.payEndDate}" pattern="MM/dd/yyyy"/></span>
                    <c:if test="${Form.nextPayCalendarId ne null}">
                        <input type="button" class="next" value="Next" name="Next"
                               onclick="this.form.hrPyCalendarEntriesId.value='${Form.nextPayCalendarId}'; this.form.submit();"/>
                    </c:if>
                </div>
            </td>
            <td></td>
        </tr>
    </c:if>
</table>

<display:table name="${Form.approvalRows}" requestURI="TimeApproval.do" excludedParams="*" pagesize="2" id="row"
               class="approvals-table" partialList="true" size="${Form.resultSize}" sort="external" defaultsort="1">
    <display:column title="Principal Name" sortable="true" sortName="principalName">
        <c:if test="${row.periodTotal > 0}">
            <div class="ui-state-default ui-corner-all" style="float:left;">
                    <%--<span id="showDetailButton_${row.count-1}" class="ui-icon ui-icon-plus rowInfo"></span>--%>
            </div>
        </c:if>
        <a href="Admin.do?${row.timesheetUserTargetURLParams}&targetUrl=PersonInfo.do&returnUrl=TimeApproval.do">${row.name}</a> (${row.principalId})
        <br/>${row.clockStatusMessage}
        <br/>
        <%--<c:set var="assignmentRowId" value="assignmentDetails_${row.count-1}"/>--%>
    </display:column>
    <display:column title="Document ID" sortable="true" sortName="documentId">
        <a href="Admin.do?${row.timesheetUserTargetURLParams}&targetUrl=TimeDetail.do%3FdocumentId=${row.documentId}&returnUrl=TimeApproval.do">${row.documentId}</a>
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
                        <tr>
                            <td>${note.noteAuthorWorkflowId}</td>
                            <td style="width: 30px;">${note.noteCreateDate}</td>
                            <td>
                                <div class="warning-note-message">
                                        ${note.noteText}
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </display:column>
    <display:column title="Status">
        <div><span id="approvals-status" class="approvals-status">${row.approvalStatus}</span></div>
        <div id="approvals-status-details" class="approvals-status-details"
             style="display: none; float: right; width: 300px; margin-left: 100px;">
            <table>
                <thead>
                <tr>
                    <th style="font-size: 1.2em; font-weight: bold; text-align: left;">
                        Document Status:
                    </th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>
                        <div style="text-align: left; width: 150px;">${row.approvalStatusMessage}</div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </display:column>

    <c:forEach var="payCalLabel" items="${Form.payCalendarLabels}">
        <display:column title="${payCalLabel}">
            <c:choose>
                <c:when test="${fn:contains(payCalLabel,'Week')}">
                    <span style="font-weight: bold;">${row.hoursToPayLabelMap[payCalLabel]}</span>
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
    <display:column title="Select">
        <%--<html:checkbox property="approvalRows[${row.count-1}].selected" disabled="${!row.approvable}"/>--%>
    </display:column>
</display:table>

    <%--
    <c:choose>
        <c:when test="${fn:length(Form.approvalRows) > 0}">
            <table id="approvals-table" class="approvals-table">
                <thead>
                <tr>
                    <th><bean:message key="approval.principalName"/></th>
                    <th><bean:message key="approval.documentId"/></th>
                    <th><bean:message key="approval.status"/></th>
                    <c:forEach var="payCalLabel" items="${Form.payCalendarLabels}">
                        <th>${payCalLabel}</th>
                    </c:forEach>
                    <th>Action</th>
                    <th>Select <input type="checkbox" name="selectAll" id="selectAll"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="approvalRow" items="${Form.approvalRows}" varStatus="row">
                    <c:set var="nameStyle" value=""/>
                    <c:if test="${approvalRow.clockedInOverThreshold}">
                        <c:set var="nameStyle" value="background-color: #F08080;"/>
                    </c:if>
                    <tr>
                        <td style="${nameStyle}">
                            <c:if test="${approvalRow.periodTotal > 0}">
                                <div class="ui-state-default ui-corner-all" style="float:left;">
                                    <span id="showDetailButton_${row.count-1}" class="ui-icon ui-icon-plus rowInfo"></span>
                                </div>
                            </c:if>
                            <a href="Admin.do?${approvalRow.timesheetUserTargetURLParams}&targetUrl=PersonInfo.do&returnUrl=TimeApproval.do">${approvalRow.name}</a>
                            <br/>${approvalRow.clockStatusMessage}
                            <br/>
                            <c:set var="assignmentRowId" value="assignmentDetails_${row.count-1}"/>

                        </td>
                        <td>
                            <a href="Admin.do?${approvalRow.timesheetUserTargetURLParams}&targetUrl=TimeDetail.do%3FdocumentId=${approvalRow.documentId}&returnUrl=TimeApproval.do">${approvalRow.documentId}</a>

                            <div style="float:right;">
                                <c:if test="${fn:length(approvalRow.warnings) > 0 }">
                                    <div class="ui-state-default ui-corner-all" style="float:right;">
                                                <span id="approvals-warning"
                                                      class="ui-icon ui-icon-alert approvals-warning"></span>
                                    </div>
                                    <div id="approvals-warning-details" class="approvals-warning-details"
                                         style="display:none; float:right; width: 600px; margin-left: 200px;">
                                        <table>
                                            <thead>
                                            <tr>
                                                <th style="font-size: 1.2em; font-weight: bold; text-align: left;">
                                                    Warnings:
                                                </th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="warning" items="${approvalRow.warnings}">
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
                                <c:if test="${fn:length(approvalRow.notes) > 0 }">
                                    <div class="ui-state-default ui-corner-all"
                                         style="float:right; margin-right: 2px;">
                                                <span id="approvals-note"
                                                      class="ui-icon ui-icon-note approvals-note"></span>
                                    </div>
                                    <div id="approvals-note-details" class="approvals-note-details"
                                         style="display:none; float:right; margin-left: 150px;">
                                        <table>
                                            <thead>
                                            <tr>
                                                <th colspan="3"
                                                    style="font-size: 1.2em; font-weight: bold; text-align: left;">
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
                                            <c:forEach var="note" items="${approvalRow.notes}">
                                                <tr>
                                                    <td>${note.noteAuthorWorkflowId}</td>
                                                    <td style="width: 30px;">${note.noteCreateDate}</td>
                                                    <td>
                                                        <div class="warning-note-message">
                                                                ${note.noteText}
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </c:if>
                            </div>
                        </td>
                        <td>
                            <div><span id="approvals-status" class="approvals-status">${approvalRow.approvalStatus}</span>
                            </div>
                            <div id="approvals-status-details"
                                 class="approvals-status-details"
                                 style="display: none; float: right; width: 300px; margin-left: 100px;">
                                <table>
                                    <thead>
                                    <tr>
                                        <th
                                                style="font-size: 1.2em; font-weight: bold; text-align: left;">
                                            Document Status:
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>
                                            <div style="text-align: left; width: 150px;">
                                                    ${approvalRow.approvalStatusMessage}</div>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </td>
                        <c:forEach var="payCalLabel" items="${Form.payCalendarLabels}">
                            <c:choose>
                                <c:when test="${fn:contains(payCalLabel,'Week')}">
                                    <td style="background-color: #E5E5E5;">
                                        <span style="font-weight: bold;">${approvalRow.hoursToPayLabelMap[payCalLabel]}</span>
                                    </td>
                                </c:when>
                                <c:otherwise>
                                    <td>${approvalRow.hoursToPayLabelMap[payCalLabel]}</td>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <td>
                            <tk:tkApprovalRowButtons appRow="${approvalRow}"/>
                        </td>
                        <td align="center"><html:checkbox property="approvalRows[${row.count-1}].selected"
                                                          disabled="${!approvalRow.approvable}"/></td>
                    </tr>

                    <div class="hourDetails">
                        <tr style="display:none;" class="timeSummaryRow_${row.count-1}">
                            <td class="rowCount"><tk:timeSummary timeSummary="${approvalRow.timeSummary}"/></td>
                        </tr>
                    </div>
                </c:forEach>
                </tbody>
            </table>
            <div id="approvals-approve-button">
                <input type="submit" class="approve" value="Approve" name="Approve"
                       onclick="this.form.methodToCall.value='approve'; this.form.submit();"/>
            </div>
            <div id="loader"></div>
        </c:when>
        <c:otherwise>
        </c:otherwise>
    </c:choose>
    --%>
</div>
</html:form>

</tk:tkHeader>
