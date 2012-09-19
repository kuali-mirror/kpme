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

<tk:approvalSearchFields />

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

<c:if test="${Form.resultSize > 0}">
<tk:timeApproval />
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
