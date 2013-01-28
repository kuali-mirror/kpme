<%--

    Copyright 2004-2013 The Kuali Foundation

    Licensed under the Educational Community License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.opensource.org/licenses/ecl2.php

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

--%>
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

	<tk:approvalFilter />

	<tk:approvalSearch calType="payCalendar" searchId="searchTerm" />

    <tk:timeApproval />
    
    <c:if test="${fn:length(Form.approvalRows) != 0}">
    	<tk:approvalButtons refreshId="refresh" approvable="${Form.anyApprovalRowApprovable}" />
    </c:if>

</div>
</html:form>

</tk:tkHeader>


<%-- Hour detail template --%>
<script type="text/template" id="hourDetail-template">
    <tr class="hourDetailRow_<@= docId @>">
        <td colspan="2"><@= section.earnCode @>: <@= section.desc @></td>
    </tr>
    <@ _.each(section.assignmentRows, function(assignmentRow) { @>
        <tr class="hourDetailRow_<@= docId @>">
            <td colspan="2" class="<@= assignmentRow.cssClass @>"><b><@= assignmentRow.descr @></b></td>
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
        <td colspan="2"><@= section.earnGroup @></td>
        <@ _.each(section.totals, function(total) { @>
            <td><@= total == 0 ? "" : total.toFixed(2) @></td>
        <@ }); @>
    </tr>
    <@ } @>
</script>
