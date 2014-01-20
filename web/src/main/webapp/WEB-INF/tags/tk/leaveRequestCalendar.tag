<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<%@ attribute name="day" required="true" type="org.kuali.kpme.core.calendar.web.CalendarDay" %>
<%@ attribute name="dayNumberId" required="true" type="java.lang.String" %>

<c:forEach var="leaveReqRow" items="${day.leaveReqRows}" varStatus="status">
	<c:set var="editableClass" value="event-title-false"/>
	<c:if test="${not empty leaveReqRow.leaveRequestDocId}">
		<c:set var="editableClass" value="event-title-true"/>
	</c:if>
	<c:set var="lbDivId" value="${dayNumberId}_noEditLeave"/>
	<div id="${lbDivId}" class="leaveBlock ${leaveReqRow.requestStatus}" >
	    <c:if test="${not empty leaveReqRow.leaveRequestDocId}">
	    	<div id="leaveRequest_${leaveReqRow.leaveRequestDocId}" class="${editableClass}">
	    		<input type="checkbox" name="leaveReqDoc_${leaveReqRow.leaveRequestDocId}" id="leaveReqDoc_${leaveReqRow.leaveRequestDocId}" value="${leaveReqRow.selected}" class="selectedEmpl"/>
	    		${leaveReqRow.employeeName} (${leaveReqRow.principalId})
		    </div>  
		</c:if>
	    <c:if test="${empty leaveReqRow.leaveRequestDocId}">
	    	<div>
	    		${leaveReqRow.employeeName} (${leaveReqRow.principalId})
	    	</div>	
		</c:if>
		 <div>
		    ${leaveReqRow.leaveCode} - (${leaveReqRow.requestedHours})
		 </div>
	</div>   
</c:forEach>