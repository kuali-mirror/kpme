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
	
	<c:if test="${fn:length(Form.approvalRows) != 0}">
		<tk:approvalSearch searchId="searchValue" />
	
		<tk:timeApproval />
	
	   	<tk:approvalButtons refreshId="refresh" />
	
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
