<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>
<c:set var="Form" value="${TimeApprovalActionForm}" scope="request"/>
<jsp:useBean id="tagSupport" class="org.kuali.hr.time.util.TagSupport"/>

<tk:tkHeader tabId="approvals">
    <html:hidden property="methodToCall" value=""/>
    <html:hidden property="rowsInTotal" value="${fn:length(Form.approvalRows)}"/>

    <div class="approvals">
        <div id="searchDocuments">
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

            <div id="payCalendarGroups">
                Switch Pay Calendar Groups:
                <label for="switch pay calendar groups">
                    <select id="payCalendar">
                        <option value="">-- Select a pay calendar --</option>
                    </select>
                </label>
            </div>
        </div>

        <table id="approvals-table" class="tablesorter">
            <thead>
            <tr>
                <td colspan="22" align="center">
                    <span style="font-weight: bold; font-size: 1.5em;">${Form.name}</span>
                </td>
            </tr>
            <tr>
                <th><bean:message key="approval.principalName"/></th>
                <th><bean:message key="approval.documentId"/></th>
                <th><bean:message key="approval.status"/></th>
                <c:forEach var="payCalLabel" items="${Form.payCalendarLabels}">
                    <th>${payCalLabel}</th>
                </c:forEach>
                <th>Select</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="approveRow" items="${Form.approvalRows}" varStatus="row">
                <tr>
                    <td>
                        <a href="TimeDetail.do?${approveRow.timesheetUserTargetURLParams}">${approveRow.name}<br/>${approveRow.clockStatusMessage}
                        </a>
                    </td>
                    <td>${approveRow.documentId}</td>
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
                    <td align="center"><input type="checkbox" name="selectedEmpl" id="selectedEmpl"/></td>
                    <td>
                        <tk:tkApprovalRowButtons appRow="${approveRow}"/>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="22" align="center" style="border:none;">
                    <input type="button" class="button" value="Approve" name="Approve">
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <a href="#" id="next">Load next X records </a> of total ${fn:length(Form.approvalRows)}

    <div id="loader"></div>
</tk:tkHeader>