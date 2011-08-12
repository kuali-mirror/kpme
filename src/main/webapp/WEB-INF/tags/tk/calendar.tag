<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<%@ attribute name="cal" required="true" type="org.kuali.hr.time.calendar.TkCalendar" %>
<%@ attribute name="docId" required="true" type="java.lang.String" %>

<div id="tkCal" class="ui-widget cal" style="margin: 20px auto 20px auto; width:95%;">
    <%-- Add Paging Controls for moving between Calendars --%>
    <table class="cal-header">
        <tbody>
        <tr>
            <td>
                <c:if test="${Form.prevDocumentId ne null}">
                    <button id="nav_prev">Previous</button>
                </c:if>
                <span class="header-title">${cal.calendarTitle}</span>
                <c:if test="${Form.nextDocumentId ne null}">
                    <button id="nav_next">Next</button>
                </c:if>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="global-error">
        <c:forEach var="warning" items="${Form.warnings}">
            ${warning} <br/>
        </c:forEach>
    </div>

    <div id="tkCalContent">
        <table class="cal-table">
            <thead>
            <%-- Render Day Labels, starting at FLSA Start day --%>
            <tr>
                <c:forEach var="dayString" items="${cal.calendarDayHeadings}">
                    <th class="ui-state-default week-header">${dayString}</th>
                </c:forEach>
            </tr>
            </thead>

            <tbody>
            <%-- Generate Each Week --%>
            <c:forEach var="week" items="${cal.weeks}" varStatus="rowS">
                <tr>
                        <%-- Generate Each Day --%>
                    <c:forEach var="day" items="${week.days}" varStatus="dayS">
                        <td id="day_${day.dayNumberDelta}" class="ui-state-default" style="width:14%;">
                                <%-- Day Number --%>
                            <div class="day-number">${day.dayNumberString}</div>
                                <%-- Render the Time Blocks --%>
                            <div>
                                <c:forEach var="block" items="${day.blockRenderers}" varStatus="status">
                              		<c:if test="${block.timeBlock.earnCode ne TkConstants.LUNCH_EARN_CODE}">
	                                   <c:choose>
	                                       <c:when test="${status.last}">
	                                           <c:set var="last" value="last-event"/>
	                                       </c:when>
	                                       <c:otherwise>
	                                           <c:set var="last" value=""/>
	                                       </c:otherwise>
	                                   </c:choose>
	
	                                   <div class="event ${last} ${block.assignmentClass}">
	                                       <div id="block_${block.timeBlock.tkTimeBlockId}" class="event-title-${Form.docEditable}">
	                                           <c:if test="${Form.docEditable}">
	                                           	<div><img id="delete_${block.timeBlock.tkTimeBlockId}" class='event-delete'
	                                                     src='images/delete.png'/>
	                                                </div>
	                                             </c:if>
	                                               ${block.title}
	                                       </div>
	                                       ${block.timeRange}
	                                       <div>
	                                        <c:if test="${block.earnCodeType ne TkConstants.EARN_CODE_AMOUNT}">
	                                            <c:forEach var="thdr" items="${block.detailRenderers}">
	                                            	<c:if test="${thdr.title ne TkConstants.LUNCH_EARN_CODE}">
		                                                <div id="${thdr.timeHourDetail.tkTimeHourDetailId}" class="event-content">
		                                                        <c:choose>
		                                                            <c:when test="${thdr.hours ne ''}">
		                                                                ${thdr.title} - ${thdr.hours} hours
		                                                            </c:when>
		                                                            <c:otherwise>
		                                                                ${thdr.title} - $${thdr.hours}
		                                                            </c:otherwise>
		                                                        </c:choose>
		
		                                                </div>
	                                                </c:if>
	                                            </c:forEach>
	                                         </c:if>
	                                       </div>
	                                       <div class="lunch">
	                                       	${block.lunchLabel}
	                                       </div>
	                                       ${block.amount}
	                                   </div>
                                   </c:if>
                                </c:forEach>
                            </div>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>