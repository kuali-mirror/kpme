<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>
<c:set var="Form" value="${LeaveCalendarForm}" scope="request"/>


<tk:tkHeader tabId="leaveCalendar">
    <tk:calendar cal="${Form.leaveCalendar}" docId="${Form.documentId}" calType="leaveCalendar"/>
</tk:tkHeader>