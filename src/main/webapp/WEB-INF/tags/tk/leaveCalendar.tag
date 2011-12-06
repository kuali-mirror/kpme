<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<%@ attribute name="day" required="true" type="org.kuali.hr.time.calendar.CalendarDay" %>

<c:forEach var="ledger" items="${day.ledgerRenderers}" varStatus="status">
    <div class="ledger">
        <img id="delete_${ledger.ledgerId}" src='images/delete.png' class="ledger-delete"/>
        ${ledger.leaveCode} (${ledger.hours})
    </div>
</c:forEach>
