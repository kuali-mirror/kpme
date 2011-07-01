<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>
<c:set var="Form" value="${TimeApprovalActionForm}" scope="request"/>
<jsp:useBean id="tagSupport" class="org.kuali.hr.time.util.TagSupport"/>

<tk:tkHeader tabId="approvals">
    <html:hidden property="methodToCall" value=""/>
    <html:hidden property="rowsInTotal" value="${fn:length(Form.approvalRows)}"/>
    <%--<html:hidden property="documentId" value="${Form.documentId}" styleId="documentId"/>--%>

    <div class="approvals">
        <table id="approvals-filter">
            <tr>
                <td class="left">
                    Search By :
                    <label for="search field">
                        <select id="searchField" name="searchField">
                            <option value="">-- Select a filed --</option>
                            <option value="DocumentId">Document Id</option>
                            <option value="PrincipalName">Principal Name</option>
                        </select>
                    </label>
                    Value :
                    <label for="search value">
                        <input id="searchValue" name="searchValue" type="text" placeholder="enter at least 3 chars"/>
                    </label>
                </td>
                <td class="center">
                    <button class="prev">Previous</button>
                    <span style="font-size: 1.5em; vertical-align: middle;">
                    <fmt:formatDate value="${Form.payBeginDate}" pattern="MM/dd/yyyy"/> -
                    <fmt:formatDate value="${Form.payEndDate}" pattern="MM/dd/yyyy"/></span>
                    <button class="next">Next</button>
                </td>
                <td class="right">
                    Switch Pay Calendar Groups:
                    <label for="switch pay calendar groups">
                        <select id="selectedPayCalendarGroup" name="selectedPayCalendarGroup">
                            <c:forEach var="payCalendarGroup" items="${Form.payCalendarGroups}">
                                <option value="${payCalendarGroup}" selected="selected">${payCalendarGroup}</option>
                            </c:forEach>
                        </select>
                    </label>
                </td>
            </tr>
        </table>

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
                        <tr>
                            <td>
                                <div class="ui-state-default ui-corner-all" style="float:left;">
                                    <span id=""
                                          class="ui-icon ui-icon-plus rowInfo">${approvalRow.documentId}||${approvalRow.principalId}||${fn:join(approvalRow.workAreas, ",")}</span>
                                </div>
                                <a href="PersonInfo.do?${approvalRow.timesheetUserTargetURLParams}">${approvalRow.name}</a>
                                <br/>${approvalRow.clockStatusMessage}
                            </td>
                            <td>${approvalRow.documentId}
                                <div style="float:right;">
                                    <c:if test="${fn:length(approvalRow.warnings) > 0 }">
                                        <div class="ui-state-default ui-corner-all" style="float:right;">
                                            <span id="approvals-warning"
                                                  class="ui-icon ui-icon-alert approvals-warning"></span>
                                        </div>
                                        <div id="approvals-warning-details" class="approvals-warning-details"
                                             style="display:none;">
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
                                                        <td style="text-align: left;">${warning}</td>
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
                                             style="display:none;">
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
                                                        <td>${note.noteCreateDate}</td>
                                                        <td>${note.noteText}</td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </c:if>
                                </div>
                            </td>
                            <td>${approvalRow.approvalStatus}</td>
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
                            <td align="center"><input type="checkbox" name="selectedEmpl" id="selectedEmpl"
                                                      class="selectedEmpl"/></td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="22" align="center" style="border:none;">
                            <input type="button" class="button" value="Approve" name="Approve">
                        </td>
                    </tr>
                    </tbody>
                </table>
                <a href="#" id="next">Load next X records </a> of total ${fn:length(Form.approvalRows)}
                <div id="loader"></div>
            </c:when>
            <c:otherwise>
                <span style="font-size: 1.5em;">No document for approval.</span>
            </c:otherwise>
        </c:choose>

    </div>


</tk:tkHeader>