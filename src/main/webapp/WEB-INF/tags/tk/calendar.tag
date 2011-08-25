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
                    
	                   	<c:set var="dayStyle" value="width:14%;"/>
	                   	<c:set var="dayId" value = "day_${day.dayNumberDelta}" />
	                	<c:if test="${day.gray}">
	                		<c:set var="dayStyle" value="width:14%; background: gray;"/>
	                		<c:set var="dayId" value = "gray_day" />
	                	</c:if>
                   	
                     	
                        <td id="${dayId}" class="ui-state-default" style="${dayStyle}">
                                <%-- Day Number --%>
                            <div class="day-number">${day.dayNumberString}</div>
                                <%-- Render the Time Blocks --%>
                            <div>
                                <c:forEach var="block" items="${day.blockRenderers}" varStatus="status">
                              		<c:if test="${block.timeBlock.earnCode ne TkConstants.LUNCH_EARN_CODE}" >
	                                   <c:choose>
	                                       <c:when test="${status.last}">
	                                           <c:set var="last" value="last-event"/>
	                                       </c:when>
	                                       <c:otherwise>
	                                           <c:set var="last" value=""/>
	                                       </c:otherwise>
	                                   </c:choose>
	
									
	                                   <div class="event ${last} ${block.assignmentClass}">
	                                   	   <c:set var="editableClass" value="event-title-false"/>
	                                   	   <c:if test="${Form.docEditable && block.timeBlock.editable}">
	                                   	   		<c:set var="editableClass" value="event-title-true"/>
	                                   	   </c:if>
	                                   	   
	                                        <div id="block_${block.timeBlock.tkTimeBlockId}" class="${editableClass}">
	                                    		<c:choose>
		                                           <c:when test="${Form.docEditable && block.timeBlock.editable}">
			                                           	<div><img id="delete_${block.timeBlock.tkTimeBlockId}" class='event-delete'
			                                                     src='images/delete.png'/>
			                                            </div>
			                                            ${block.title}
		                                           </c:when>
		                                           <c:otherwise>
		                                           		<div class="event-content">
			                                            	${block.title}
			                                            </div>
		                                           </c:otherwise>
		                                        </c:choose>
		                                     </div>
	                                       ${block.timeRange}
	                                       <div>
	                                        <c:if test="${block.earnCodeType ne TkConstants.EARN_CODE_AMOUNT}">
	                                            <c:forEach var="thdr" items="${block.detailRenderers}">
	                                            	<c:if test="${thdr.title ne TkConstants.LUNCH_EARN_CODE}">
		                                                <div id="${thdr.timeHourDetail.tkTimeHourDetailId}" class="event-content">
		                                                        <c:choose>
		                                                            <c:when test="${thdr.hours ne ''}">
	                                                            		<c:if test="${thdr.title eq TkConstants.HOLIDAY_EARN_CODE}">
	                                                            			<div><a id="holidayNameHelp" title="${thdr.holidayName}" style="color:white; cursor:pointer">${thdr.title} - ${thdr.hours} hours</a></div>
	                                                            		</c:if>
	                                                            		<c:if test="${thdr.title ne TkConstants.HOLIDAY_EARN_CODE}">
	                                                                		${thdr.title} - ${thdr.hours} hours
	                                                                	</c:if>
	                                                                </c:when>
		                                                            <c:otherwise>
		                                                            	<c:if test="${thdr.title eq TkConstants.HOLIDAY_EARN_CODE}">
	                                                            			<div><a id="holidayNameHelp" title="${thdr.holidayName}" style="color:white; cursor:pointer">${thdr.title} - $${thdr.hours}</a></div>
	                                                            		</c:if>
	                                                            		<c:if test="${thdr.title ne TkConstants.HOLIDAY_EARN_CODE}">
	                                                                		${thdr.title} - $${thdr.hours}
	                                                                	</c:if>
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