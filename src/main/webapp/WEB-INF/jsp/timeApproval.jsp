<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>
<c:set var="Form" value="${TimeApprovalActionForm}" scope="request"/>
<jsp:useBean id="tagSupport" class="org.kuali.hr.time.util.TagSupport"/>

<tk:tkHeader tabId="approvals">
    <html:hidden property="methodToCall" value=""/>
    <html:hidden styleId="rit"   property="rowsInTotal"              value="${fn:length(Form.approvalRows)}"/>
    <html:hidden styleId="pcid"  property="payCalendarId"            value="${Form.payCalendarId}"/>
    <html:hidden styleId="pceid" property="payCalendarEntriesId"     value="${Form.payCalendarEntriesId}"/>

    <div class="approvals">
        <table id="approvals-filter">
            <tr>
                <td class="left">
                    Search By :
                    <label for="search field">
                        <select id="searchField" name="searchField">
                            <option value="">-- Select a field --</option>
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
                    <c:set var="rowCount" value="1" />
                    <c:forEach var="approvalRow" items="${Form.approvalRows}" varStatus="row">
                    	<c:set var="assignmentRowId" value="assignmentDetails${rowCount}"/>
                    	<c:set var="rowCount" value="${rowCount+1}" />
                        <tr>
                            <td>
                                <a href="Admin.do?${approvalRow.timesheetUserTargetURLParams}&targetUrl=PersonInfo.do&returnUrl=TimeApproval.do">${approvalRow.name}</a>
                                <br/>${approvalRow.clockStatusMessage}
                                <br/>
                                <input class="button" value="Show Assignments" type="button" name="showDetailButton" id="showDetailButton"
									onclick="javascript: showHideRow('${assignmentRowId}');" />
                            </td>
                            <td><a href="Admin.do?${approvalRow.timesheetUserTargetURLParams}&targetUrl=TimeDetail.do&returnUrl=TimeApproval.do">${approvalRow.documentId}</a>
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
					
                        <%-- Render details of approver's hours by Assignment --%>
                        <c:forEach var="entry" items="${approvalRow.approverHoursByAssignment}">
                            <tr class="${assignmentRowId}" style="display: none">
                                <td></td>
                                <td colspan="2">
                                    ${approvalRow.assignmentDescriptions[entry.key]}
                                </td>
                                <c:forEach var="payCalLabel" items="${Form.payCalendarLabels}">
                                    <c:choose>
                                        <c:when test="${fn:contains(payCalLabel,'Week')}">
                                            <td style="background-color: #E5E5E5;">
                                                <span style="font-weight: bold;">${entry.value[payCalLabel]}</span>
                                            </td>
                                        </c:when>
                                        <c:otherwise>
                                            <td>${entry.value[payCalLabel]}</td>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </tr>
                        </c:forEach>

                        <%-- Render details of non-approvers's hours by Assignment --%>
                        <c:forEach var="entry" items="${approvalRow.otherHoursByAssignment}">
                            <tr class="${assignmentRowId}" style="display: none">
                                <td></td>
                                <td colspan="2">
                                    ${approvalRow.assignmentDescriptions[entry.key]}
                                </td>
                                <c:forEach var="payCalLabel" items="${Form.payCalendarLabels}">
                                    <c:choose>
                                        <c:when test="${fn:contains(payCalLabel,'Week')}">
                                            <td style="background-color: #E5E5E5;">
                                                <span style="font-weight: bold;">${entry.value[payCalLabel]}</span>
                                            </td>
                                        </c:when>
                                        <c:otherwise>
                                            <td>${entry.value[payCalLabel]}</td>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </tr>
                           
                        </c:forEach>
                    

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