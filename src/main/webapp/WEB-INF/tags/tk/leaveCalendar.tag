<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<%@ attribute name="day" required="true" type="org.kuali.hr.time.calendar.CalendarDay" %>

<c:forEach var="leaveBlock" items="${day.leaveBlockRenderers}" varStatus="status">

	<c:set var="editableClass" value="event-title-true"/>

    <div class="leaveBlock">
    	<div id="leaveblock_${leaveBlock.leaveBlockId}"
	         class="${editableClass}">
	        <img id="leaveBlockDelete_${leaveBlock.leaveBlockId}" src='images/delete.png' class="leaveBlock-delete"/> 
	        <div id="show_${leaveBlock.leaveBlockId}">${leaveBlock.assignmentTitle}</div>
	       
	    </div>
        ${leaveBlock.earnCode} (${leaveBlock.hours})	
    </div>
</c:forEach>