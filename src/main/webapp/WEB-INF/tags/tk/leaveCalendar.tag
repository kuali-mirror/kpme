<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<%@ attribute name="day" required="true" type="org.kuali.hr.time.calendar.CalendarDay" %>

<c:forEach var="ledger" items="${day.ledgerRenderers}" varStatus="status">
    ${ledger.leaveCode}
</c:forEach>
