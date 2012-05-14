<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>
<c:set var="Form" value="${TimeApprovalActionForm}" scope="request"/>
<jsp:useBean id="tagSupport" class="org.kuali.hr.time.util.TagSupport"/>

<tk:tkHeader tabId="approvals">
<html:form action="/TimeApproval.do" method="POST">
<html:hidden property="methodToCall" value=""/>
<html:hidden styleId="rit" property="rowsInTotal" value="${fn:length(Form.approvalRows)}"/>
<html:hidden styleId="pcid" property="hrPyCalendarId" value="${Form.hrPyCalendarId}"/>
<html:hidden styleId="pceid" property="hrPyCalendarEntriesId" value="${Form.hrPyCalendarEntriesId}"/>
<html:hidden styleId="payBeginDateForSearch" property="payBeginDateForSearch" value="${Form.payBeginDateForSearch}"/>
<html:hidden styleId="payEndDateForSearch" property="payEndDateForSearch" value="${Form.payEndDateForSearch}"/>
<html:hidden property="prevPayCalendarId" value="${Form.prevPayCalendarId}"/>
<html:hidden property="nextPayCalendarId" value="${Form.nextPayCalendarId}"/>
<html:hidden styleId="roleName" property="roleName" value="${Form.roleName}"/>


<script src="js/underscore-1.3.1.min.js"></script>
<script src="js/underscore.string-2.0.0.js"></script>
<script src="js/backbone-0.9.1.min.js"></script>
<script src="js/common.calendar.backbone.js"></script>
<script src="js/tk.approval.backbone.js"></script>

<div class="approvals">
<table id="approvals-filter">
    <tr>
        <td>
            Pay Calendar Group:
            <label for="pay calendar groups">
                <select id="selectedPayCalendarGroup" name="selectedPayCalendarGroup"
                        onchange="this.form.methodToCall.value='selectNewPayCalendar'; this.form.submit();">
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
                    onchange="this.form.methodToCall.value='selectNewDept'; this.form.submit();">
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
                        onchange="this.form.methodToCall.value='selectNewWorkArea'; this.form.submit();">
                    <option value="">Show All</option>
                    <c:forEach var="deptWorkarea" items="${Form.workAreaDescr}">
                        <c:choose>
                            <c:when test="${Form.selectedWorkArea eq deptWorkarea.key}">
                                <option value="${deptWorkarea.key}" selected="selected">${deptWorkarea.value}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${deptWorkarea.key}">${deptWorkarea.value}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </label>
        </td>
    </tr>
</table>

<table class="navigation">
    <c:if test="${fn:length(Form.approvalRows) != 0}">
        <tr>
            <td class="left">
                Search By :
                <label for="search field">
                    <select id="searchField" name="searchField">
                        <option value="principalName">Principal Id</option>
                        <option value="documentId">Document Id</option>
                    </select>
                </label>
                Value :
                <label for="search value">
           			<input id="searchValue" name="searchValue" type="text" value="${Form.searchTerm}" placeholder="enter at least 3 chars" />
                    <span id='loading-value' style="display:none;"><img src='images/ajax-loader.gif'></span>
                    <input type="button" id='search' value="Search"
                           class="ui-button ui-widget ui-state-default ui-corner-all"/>
                </label>
            </td>
            <td>
                <div style="text-align: center">
                    <c:if test="${Form.prevPayCalendarId ne null}">
                        <input type="button" class="prev" value="Previous" name="Previous"
                               onclick="this.form.hrPyCalendarEntriesId.value='${Form.prevPayCalendarId}'; this.form.methodToCall.value='loadApprovalTab'; this.form.submit();"/>
                    </c:if>
                    <span id="payBeginDate" style="font-size: 1.5em; vertical-align: middle;"><fmt:formatDate
                            value="${Form.payBeginDate}" pattern="MM/dd/yyyy"/></span> -
                    <span id="payEndDate" style="font-size: 1.5em; vertical-align: middle;"><fmt:formatDate
                            value="${Form.payEndDate}" pattern="MM/dd/yyyy"/></span>
                    <c:if test="${Form.nextPayCalendarId ne null}">
                        <input type="button" class="next" value="Next" name="Next"
                               onclick="this.form.hrPyCalendarEntriesId.value='${Form.nextPayCalendarId}'; this.form.methodToCall.value='loadApprovalTab'; this.form.submit();"/>
                    </c:if>
                </div>
            </td>
            <td>
            	<tk:payCalendarSelect />
            </td>
            <td></td>
        </tr>
        <tr>
        	<td></td>
        	<td align="center">
        		<a href="${KualiForm.backLocation}?methodToCall=gotoCurrentPayPeriod"
                  	 target="_self" id="cppLink">Go to Current Pay Period</a>
        	</td>
        </tr>
    </c:if>
</table>

<display:table name="${Form.approvalRows}" requestURI="TimeApproval.do?methodToCall=loadApprovalTab" excludedParams="*"
               pagesize="20" id="row"
               class="approvals-table" partialList="true" size="${Form.resultSize}" sort="external" defaultsort="0">
    <c:set var="nameStyle" value=""/>
    <c:if test="${row.clockedInOverThreshold}">
        <c:set var="nameStyle" value="background-color: #F08080;"/>
    </c:if>
    <display:column title="Name" sortable="true" sortName="principalName" style="${nameStyle}">
        <c:if test="${row.periodTotal > 0}">
            <div class="ui-state-default ui-corner-all" style="float:left;">
                    <%--<span id="showDetailButton_${row_rowNum-1}" class="ui-icon ui-icon-plus rowInfo"></span>--%>
                <span id="showDetailButton_${row.documentId}" class="ui-icon ui-icon-plus rowInfo"></span>
            </div>
        </c:if>
        <a href="Admin.do?${row.timesheetUserTargetURLParams}&targetUrl=PersonInfo.do&returnUrl=TimeApproval.do">${row.name}</a> (${row.principalId})
        <br/>${row.clockStatusMessage}
        <br/>
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
                            <td>${note.noteAuthorFullName}</td>
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
        <div>
            <span id="approvals-status" class="approvals-status">${row.approvalStatus}</span>
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
    <display:column title="Select All <input type='checkbox' name='Select' id='checkAllAuto'></input>"
                    class="last_column_${row_rowNum}">
        <html:checkbox property="approvalRows[${row_rowNum-1}].selected" disabled="${!row.approvable}"
                       styleClass="selectedEmpl"/>
        <%-- This is where we will insert the hour details --%>
        <div id="hourDetails_${row.documentId}" style="display: none;"></div>
    </display:column>
</display:table>

<c:if test="${Form.resultSize > 0}">
    <div id="approvals-approve-button">
        <input type="submit" class="approve" value="Approve" name="Approve"
               onclick="this.form.methodToCall.value='approve'; this.form.submit();"/>
        <input type="button" id='refresh' value="Refresh Status"
               class="ui-button ui-widget ui-state-default ui-corner-all"/>
    </div>

</c:if>

</div>
</html:form>

</tk:tkHeader>


<%-- Hour detail template --%>
<script type="text/template" id="hourDetail-template">
    <tr class="hourDetailRow_<@= docId @>">
        <td colspan="3"><@= section.earnCode @>: <@= section.desc @></td>
    </tr>
    <@ _.each(section.assignmentRows, function(assignmentRow) { @>
        <tr class="hourDetailRow_<@= docId @>">
            <td colspan="3" class="<@= assignmentRow.cssClass @>"><b><@= assignmentRow.descr @></b></td>
            <@ if (!assignmentRow.isAmountEarnCode) { @>
                <@ _.each(assignmentRow.total, function(tot) { @>
                    <td><@= tot == 0 ? "" : tot.toFixed(2) @></td>
                <@ }); @>
            <@ } else { @>
                <@ _.each(assignmentRow.amount, function(amount) { @>
                    <td><@= amount == 0 ? "" : amount.toFixed(2) @></td>
                <@ }); @>
            <@ } @>
        </tr>
    <@ }); @>
    <@ if (isLast) { @>
    <tr class="hourDetailRow_<@= docId @>">
        <td colspan="3"><@= section.earnGroup @></td>
        <@ _.each(section.totals, function(total) { @>
            <td><@= total == 0 ? "" : total.toFixed(2) @></td>
        <@ }); @>
    </tr>
    <@ } @>
</script>