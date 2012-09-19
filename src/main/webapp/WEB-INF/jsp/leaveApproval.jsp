<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>
<c:set var="Form" value="${LeaveApprovalActionForm}" scope="request"/>
<jsp:useBean id="tagSupport" class="org.kuali.hr.time.util.TagSupport"/>

<tk:tkHeader tabId="leaveApprovals">
<html:form action="/LeaveApproval.do" method="POST">
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
<script src="js/lm.approval.backbone.js"></script>

<div class="approvals">

<tk:approvalSearchFields />

<table class="navigation">
    <c:if test="${fn:length(Form.leaveApprovalRows) != 0}">
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
	        		<a href="LeaveApproval.do?methodToCall=gotoCurrentPayPeriod"
	                  	 target="_self" id="cppLink">Go to Current Period</a>
	        	</td>
        	</c:if>
        </tr>
    </c:if>
</table>
<c:if test="${Form.resultSize > 0}">
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