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
        <td>
        	Approval Type:
        	 <label for="approval types">
                <select id="selectedApprovalType" name="selectedApprovalType"
                        onchange="this.form.methodToCall.value='selectNewApprovalType'; this.form.submit();">
                        <c:choose>
                        	<c:when test="${Form.selectedApprovalType == 'all'}">
                        		<option value="all" selected="selected">Show All</option>
                        	</c:when>
                        	<c:otherwise>
                                <option value="all">Show All</option>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                        	<c:when test="${Form.selectedApprovalType == 'time'}">
                        		<option value="time" selected="selected">Time Sheet Approval</option>
                        	</c:when>
                        	<c:otherwise>
                                <option value="time">Time Sheet Approval</option>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                        	<c:when test="${Form.selectedApprovalType == 'leave'}">
                        		<option value="leave" selected="selected">Leave Calendar Approval</option>
                        	</c:when>
                        	<c:otherwise>
                                <option value="leave">Leave Calendar Approval</option>
                            </c:otherwise>
                        </c:choose>
                </select>
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
                    <span id="beginDate" style="font-size: 1.5em; vertical-align: middle;"><fmt:formatDate
                            value="${Form.payBeginDate}" pattern="MM/dd/yyyy"/></span> -
                    <span id="endDate" style="font-size: 1.5em; vertical-align: middle;"><fmt:formatDate
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
        	<c:if test="${!Form.onCurrentPeriod}" >
	        	<td align="center">
	        		<a href="TimeApproval.do?methodToCall=gotoCurrentPayPeriod"
	                  	 target="_self" id="cppLink">Go to Current Period</a>
	        	</td>
        	</c:if>
        </tr>
    </c:if>
</table>

<c:if test="${Form.selectedApprovalType == 'time' || Form.selectedApprovalType == 'all'}">
	<tk:timeApproval />
</c:if>
<c:if test="${Form.selectedApprovalType == 'leave' || Form.selectedApprovalType == 'all'}">
	<lm:leaveApproval />
</c:if>

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

<%-- Leave Calendar detail template --%>
<script type="text/template" id="leaveDetail-template">
    <tr class="leaveDetailRow_<@= docId @>">
		<th colspan="3"/>
		<@ _.each(section.daysDetail, function() { @>
               <th/>
        <@ }); @>
        <th>Period Usage</th>
		<th>Available</th>
    </tr>
    <tr class="leaveDetailRow_<@= docId @>">
        <td colspan="3" class="<@= section.cssClass @>"><b><@= section.accrualCategory @></b></td>
            <@ _.each(section.daysDetail, function(tot) { @>
                <td><@= tot == 0 ? "" : tot @></td>
        	<@ }); @>
		<td><@= section.periodUsage @></td>
		<td><@= section.availableBalance @></td>
    </tr>
</script>