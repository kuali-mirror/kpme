
<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<%@ attribute name="day" required="true" type="org.kuali.hr.tklm.time.calendar.CalendarDay" %>
<%@ attribute name="dayNumberId" required="true" type="java.lang.String" %>

<c:set var="editableClass" value="event-title-false"/>
<c:if test="${day.dayEditable}">
	<c:set var="editableClass" value="event-title-true"/>
</c:if>

<c:forEach var="leaveBlock" items="${day.leaveBlockRenderers}" varStatus="status">
    <div id="${dayNumberId}" class="leaveBlock ${fn:toLowerCase(leaveBlock.requestStatusClass)}" title="${leaveBlock.description}">
    	<div id="leaveblock_${leaveBlock.leaveBlockId}" class="${editableClass}">
            <c:if test="${leaveBlock.deletable and day.dayEditable}">
	            <img id="leaveBlockDelete_${leaveBlock.leaveBlockId}" src='images/delete.png' class="leaveBlock-delete"/>
            </c:if>
            <c:choose>
	            <c:when test="${day.dayEditable}">
		            <div id="show_${leaveBlock.leaveBlockId}">
			            ${leaveBlock.assignmentTitle}
			        </div>
		        </c:when>
		         <c:otherwise>
	                <div>${leaveBlock.assignmentTitle}</div>
	             </c:otherwise>
             </c:choose>
	    </div>  
	    <div>
	    ${leaveBlock.timeRange}
	    </div>
	    ${leaveBlock.leaveBlockDetails} - ${leaveBlock.earnCode} (${leaveBlock.hours})
    </div>
</c:forEach>