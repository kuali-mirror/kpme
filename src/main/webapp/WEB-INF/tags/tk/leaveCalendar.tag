<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<%@ attribute name="day" required="true" type="org.kuali.hr.time.calendar.CalendarDay" %>

<c:forEach var="leaveBlock" items="${day.leaveBlockRenderers}" varStatus="status">
    <div class="leaveBlock">
        <img id="leaveBlockDelete_${leaveBlock.leaveBlockId}" src='images/delete.png' class="leaveBlock-delete"/>
         ${leaveBlock.assignmentTitle} ${leaveBlock.leaveCode} (${leaveBlock.hours})
    </div>
</c:forEach>