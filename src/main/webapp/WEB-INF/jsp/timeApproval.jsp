<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>
<c:set var="Form" value="${TimeApprovalActionForm}" scope="request"/>
<jsp:useBean id="tagSupport" class="org.kuali.hr.time.util.TagSupport"/>

<tk:tkHeader tabId="approvals">
    <html:hidden property="methodToCall" value=""/>
    <html:hidden property="rowsInTotal" value="${fn:length(Form.approvalRows)}"/>

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
                    <span style="font-size: 1.5em; vertical-align: middle;">${Form.payBeginDate} - ${Form.payEndDate}</span>
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
                    <c:forEach var="approveRow" items="${Form.approvalRows}" varStatus="row">
                        <tr>
                            <td>
                                <a href="PersonInfo.do?${approveRow.timesheetUserTargetURLParams}">${approveRow.name}<br/>${approveRow.clockStatusMessage}
                                </a>
                            </td>
                            <td>${approveRow.documentId}
                                <div class="ui-state-default ui-corner-all" style="float:right;">
                                    <span id="approvals-warning" class="ui-icon ui-icon-alert approvals-warning"></span>
                                </div>
                                <div class="ui-state-default ui-corner-all" style="float:right; margin-right: 2px;">
                                    <span id="approvals-note" class="ui-icon ui-icon-note approvals-note"></span>
                                </div>
                                <div id="approvals-note-detail" class="approvals-note-detail" style="display:none;">

                                    <table>
                                        <thead>
                                        <tr>
                                            <th colspan="4" style="text-align: left; font-size: 1.2em; font-weight: bold;">Notes :</th>
                                        </tr>
                                        <tr>
                                            <th>Creator</th>
                                            <th>Created Date</th>
                                            <th>Content</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="note" items="${approveRow.notes}">
                                        <tr>
                                            <td>${note.noteAuthorWorkflowId}</td>
                                            <td>${note.noteCreateDate}</td>
                                            <td>${note.noteText}</td>
                                        </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </td>
                            <td>${approveRow.approvalStatus}</td>
                            <c:forEach var="payCalLabel" items="${Form.payCalendarLabels}">
                                <c:choose>
                                    <c:when test="${fn:contains(payCalLabel,'Week')}">
                                        <td style="background-color: #E5E5E5;">
                                            <span style="font-weight: bold;">${approveRow.hoursToPayLabelMap[payCalLabel]}</span>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>${approveRow.hoursToPayLabelMap[payCalLabel]}</td>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <td>
                                <tk:tkApprovalRowButtons appRow="${approveRow}"/>
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