<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>
<c:set var="Form" value="${TimeApprovalActionForm}" scope="request"/>
<jsp:useBean id="tagSupport" class="org.kuali.hr.time.util.TagSupport"/>

<tk:tkHeader tabId="approvals">
    <html:hidden property="methodToCall" value=""/>

    <div class="approvals">
        <div id="documents">
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
        </div>
        <table id="approvals-table" class="tablesorter">
            <thead>
            <tr>
                <td colspan="22" align="center">
                    <span style="font-weight: bold; font-size: 1.5em;">${Form.name}</span>
                </td>
            </tr>
            <tr>
                <th></th>
                <th>Principal Name</th>
                <th>Document Id</th>
                <c:forEach var="payCalLabel" items="${Form.payCalendarLabels}">
                    <th>${payCalLabel}</th>
                </c:forEach>
                <th><bean:message key="approval.status"/></th>
                <th>Select</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="approveRow" items="${Form.approvalRows}" varStatus="row">
                <tr>
                    <td>
                        <tk:tkApprovalRowButtons appRow="${approveRow}"/>
                    </td>
                    <td>
                        <a href="TimeDetail.do?${approveRow.timesheetUserTargetURLParams}">${approveRow.name}<br/>${approveRow.clockStatusMessage}
                        </a>
                    </td>
                    <td>${approveRow.documentId}</td>
                    <c:forEach var="payCalLabel" items="${Form.payCalendarLabels}">
                        <td>${approveRow.hoursToPayLabelMap[payCalLabel]}</td>
                    </c:forEach>
                    <td>${approveRow.approvalStatus}</td>
                    <td align="center"><input type="checkbox" name="selectedEmpl"/></td>
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

    <a href="TimeApproval.do">Load first 5</a> |
    <a href="#" id="next">Load next 5</a>

    <div id="loader"></div>
</tk:tkHeader>