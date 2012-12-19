
<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<%@ attribute name="day" required="true" type="org.kuali.hr.time.calendar.CalendarDay" %>

<c:set var="editableClass" value="event-title-false"/>
<c:if test="${day.dayEditable}">
	<c:set var="editableClass" value="event-title-true"/>
</c:if>

<c:forEach var="leaveBlock" items="${day.leaveBlockRenderers}" varStatus="status">
    <div class="leaveBlock ${fn:toLowerCase(leaveBlock.requestStatusClass)}">
    	<div id="leaveblock_${leaveBlock.leaveBlockId}"
	         class="${editableClass}">
            <c:if test="${leaveBlock.deletable and day.dayEditable}">
	            <img id="leaveBlockDelete_${leaveBlock.leaveBlockId}" src='images/delete.png' class="leaveBlock-delete"/>
            </c:if>
            <div id="show_${leaveBlock.leaveBlockId}">${leaveBlock.assignmentTitle}</div>
	       
	    </div>
            ${leaveBlock.leaveBlockDetails} - ${leaveBlock.earnCode} (${leaveBlock.hours})


    </div>
</c:forEach>