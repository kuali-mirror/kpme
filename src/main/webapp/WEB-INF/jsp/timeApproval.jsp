<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>
<c:set var="Form" value="${TimeApprovalActionForm}" scope="request"/>

<tk:tkHeader tabId="approvals">
    <html:hidden property="methodToCall" value=""/>
    <tk:tkApproval/>

    <div id="documents">
        Search By :
        <label for="search field">
            <select id="searchField" name="searchField">
                <option value="documentId">Document ID</option>
                <option value="principalId">Principal ID</option>
            </select>
        </label>
        Value :
        <label for="search value">
            <input id="searchValue" name="searchValue" type="text" size="" placeholder="enter at least 3 chars"/>
        </label>
        <table id="approval" class="tablesorter">
            <thead>
            <tr>
                <th>Document Id</th>
                <th>Principal Id</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>

            <c:forEach var="docHeaders" items="${Form.docHeaders}" varStatus="row">
                <tr>
                    <td><span id="${docHeaders.documentId}" class="document">${docHeaders.documentId}</span></td>
                    <td>${docHeaders.principalId}</td>
                    <td><label for="checkbox"><input type="checkbox"/></label></td>
                </tr>
            </c:forEach>

            </tbody>

        </table>
    </div>
    <a href="TimeApproval.do">Load first 5</a> |
    <a href="#" id="next">Load next 5</a>

    <div id="loader"></div>
</tk:tkHeader>