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
<jsp:useBean id="tagSupport" class="org.kuali.kpme.tklm.common.TagSupport"/>

<tk:tkHeader tabId="approvals">
<html:form action="/TimeApproval.do" method="POST">
<html:hidden property="methodToCall" value=""/>
<html:hidden styleId="pceid" property="hrCalendarEntryId" value="${Form.hrCalendarEntryId}"/>
<html:hidden property="prevHrCalendarEntryId" value="${Form.prevHrCalendarEntryId}"/>
<html:hidden property="nextHrCalendarEntryId" value="${Form.nextHrCalendarEntryId}"/>
<html:hidden styleId="outputString" property="outputString" value="${Form.outputString}"/>


<script src="${ConfigProperties.js.dir}/underscore-1.3.1.min.js"></script>
<script src="${ConfigProperties.js.dir}/underscore.string-2.0.0.js"></script>
<script src="${ConfigProperties.js.dir}/backbone-0.9.1.min.js"></script>
<script src="${ConfigProperties.js.dir}/common.calendar.backbone.js"></script>
<script src="${ConfigProperties.js.dir}/tk.approval.backbone.js"></script>
<script src="${ConfigProperties.js.dir}/common.fullcalendar.js"></script>

<div class="approvals">

	<tk:approvalFilter />

	<tk:approvalSearch calType="payCalendar" searchId="searchValue" />

    <tk:timeApproval />
    
    <c:if test="${fn:length(Form.approvalRows) != 0}">
    	<tk:approvalButtons refreshId="refresh" approvable="${Form.anyApprovalRowApprovable}" />
    </c:if>

</div>
</html:form>

<link type="text/css" href='${ConfigProperties.css.dir}/fullcalendar.css' rel='stylesheet' />

</tk:tkHeader>


<%-- Hour detail template --%>

<script type="text/template" id="hourDetail-template">
    <@ _.each(section.earnCodeSections, function(earnCodeSection) { @>
        <tr class="hourDetailRow_<@= docId @>">
            <td colspan="4" class="earnCodeCell"><@= earnCodeSection.earnCode @>: <@= earnCodeSection.desc @></td>
            <td colspan="2" rowspan="<@= section.assignmentSize @>">
                <table border = "0">
                    <tr class="hourDetailRow"><th colspan="2">Week Total</th></tr>
                    <@ _.each(earnCodeSection.weekTotals, function(weekTotal) { @>
                        <tr class="hourDetailRow">
                            <td><@= weekTotal.weekName @></td>
                            <td><b><@= weekTotal.weekTotal == 0 ? "0" : weekTotal.weekTotal.toFixed(2) @></b>
                            <@ _.each(section.flsaWeekTotals, function(flsaWeekTotal) { @>
                                <@ if(flsaWeekTotal.weekName == weekTotal.weekName) {@>
                                    (<i><@= flsaWeekTotal.weekTotal == 0 ? "0" : flsaWeekTotal.weekTotal.toFixed(2) @></i>)
                                <@ } @>
                            <@ }); @>
                            </td>
                        </tr>
                    <@ }); @>
                </table>
            </td>
        </tr>

        <@ _.each(earnCodeSection.assignmentRows, function(assignmentRow) { @>
            <tr class="hourDetailRow_<@= docId @>" style="border-bottom-style: double; font-weight: bold;">
                <td colspan="3" class="<@= assignmentRow.cssClass @>"><b><@= assignmentRow.descr @></b></td>
                <@ if (assignmentRow.isAmountEarnCode) { @>
                    <@ var amount = assignmentRow.assignmentColumns[assignmentRow.assignmentColumns.length - 1].amount @>
                    <td><@= amount == 0 ? "" : amount.toFixed(2) @></td>
                <@ } else { @>
                    <@ var tot = assignmentRow.assignmentColumns[assignmentRow.assignmentColumns.length - 1].total @>
                    <td><@= tot == 0 ? "" : tot.toFixed(2) @></td>
                <@ } @>
            </tr>
        <@ }); @>
    <@ }); @>
    <%--<@ if (isLast) { @>--%>
        <tr class="hourDetailRow_<@= docId @>">
            <td colspan="3" class="earnGroupTotalRow"><@= section.earnGroup @> Totals</td>
    		<@ var total =  section.totals[section.totals.length - 1] @>
            <td class="earnGroupTotalRow"><@= total == 0 ? "" : total.toFixed(2) @></td>
        </tr>
        <tr></tr>
    <%--<@ } @>--%>
</script>
