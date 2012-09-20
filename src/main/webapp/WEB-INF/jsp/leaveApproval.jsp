<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>
<c:set var="Form" value="${LeaveApprovalActionForm}" scope="request"/>
<jsp:useBean id="tagSupport" class="org.kuali.hr.time.util.TagSupport"/>

<tk:tkHeader tabId="leaveApprovals">
<html:form action="/LeaveApproval.do" method="POST">
<html:hidden property="methodToCall" value=""/>
<html:hidden styleId="rit" property="rowsInTotal" value="${fn:length(Form.leaveApprovalRows)}"/>
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
	<%-- pay calendar group, department and work area filters --%>
	<tk:approvalFilter />
	
	<c:if test="${fn:length(Form.leaveApprovalRows) != 0}">
	
		<tk:approvalSearch searchId="leaveSearchValue" />

		<lm:leaveApproval />

		<tk:approvalButtons refreshId="leaveRefresh" />
	
	</c:if>

</div>
</html:form>

</tk:tkHeader>

<%-- Leave Calendar detail template --%>
<script type="text/template" id="leaveDetail-template">   	
    <tr class="leaveDetailRow_<@= docId @>">
        <td colspan="3" class="<@= section.cssClass @>">
			<b><@= section.accrualCategory @></b>
      	</td>
        <@ _.each(section.daysDetail, function(tot) { @>
             <td><@= tot == 0 ? "" : tot @></td>
        <@ }); @>
		<td><@= section.periodUsage @></td>
		<td><@= section.availableBalance @></td>
    </tr>
</script>

