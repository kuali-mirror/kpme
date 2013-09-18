<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<%@ attribute name="day" required="true" type="org.kuali.kpme.core.calendar.web.CalendarDay" %>
<%@ attribute name="dayNumberId" required="true" type="java.lang.String" %>

<c:forEach var="leaveBlock" items="${day.leaveBlockRenderers}" varStatus="status">
	<c:set var="editableClass" value="event-title-false"/>
	<c:set var="lbDivId" value="${dayNumberId}_noEditLeave"/>	<%-- id value is used in tk.leaveCalendar.backbone.js --%>
	<c:if test="${day.dayEditable && leaveBlock.editable}">
		<c:set var="editableClass" value="event-title-true"/>
		<c:set var="lbDivId" value="${dayNumberId}"/>
	</c:if>

    <div id="${lbDivId}" class="leaveBlock ${fn:toLowerCase(leaveBlock.requestStatusClass)}" title="${leaveBlock.description}">
    	<div id="leaveblock_${leaveBlock.leaveBlockId}" class="${editableClass}">
            <c:if test="${leaveBlock.deletable and day.dayEditable}">
	            <img id="leaveBlockDelete_${leaveBlock.leaveBlockId}" src='images/delete.png' class="leaveBlock-delete"/>
            </c:if>
            <c:choose>
	            <c:when test="${day.dayEditable && leaveBlock.editable}">
		            <div id="show_${leaveBlock.leaveBlockId}">
			            ${leaveBlock.assignmentTitle}
			        </div>
		        </c:when>
		         <c:otherwise>
	                <div id="${lbDivId}">${leaveBlock.assignmentTitle}</div>
	             </c:otherwise>
             </c:choose>
	    </div>  
	    <div>
	    ${leaveBlock.timeRange}
	    </div>
	    ${leaveBlock.leaveBlockDetails} - ${leaveBlock.earnCode} (${leaveBlock.hours})
    </div>
</c:forEach>