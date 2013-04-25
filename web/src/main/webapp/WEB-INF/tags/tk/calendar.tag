<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<%@ attribute name="cal" required="true" type="org.kuali.hr.core.calendar.CalendarParent" %>
<%@ attribute name="docId" required="true" type="java.lang.String" %>
<%@ attribute name="calType" required="true" type="java.lang.String" %>

<div id="tkCal" class="ui-widget cal ${calType}" style="margin: 0px auto 0px auto; width:100%;">
	<c:choose>
       <c:when test="${calType eq 'payCalendar'}">
           <c:set var="calendarLocation" value="TimeDetail.do"/>
       </c:when>
       <c:when test="${calType eq 'leaveCalendar'}">
           <c:set var="calendarLocation" value="LeaveCalendar.do"/>
       </c:when>
       <c:otherwise>
      		<c:set var="calendarLocation" value=""/>
       </c:otherwise>
     </c:choose>
    <table class="cal-header">
        <tbody>
        <tr>
            <%-- Paging controls for moving between calendars --%>
            <td align="left" width="30%">
                <tk:payCalendarSelect calType="${calType}" />
            </td>
            <%-- Displayed month and prev/next buttons --%>
            <td align="center" width="40%">
                <c:if test="${Form.prevDocumentId ne null || (calType eq 'leaveCalendar' && Form.prevCalEntryId ne null)}">
                    <button id="${calType == 'payCalendar' ? 'nav_prev' : 'nav_prev_lc' }"
                            class="ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only"
                            role="button" title="Previous">
                        <span class="ui-button-text">Previous</span>
                    </button>
                </c:if>
                <span class="header-title-center">${cal.calendarTitle}</span>
                <c:if test="${Form.nextDocumentId ne null || (calType eq 'leaveCalendar' && Form.nextCalEntryId ne null)}">
                    <button id="${calType == 'payCalendar' ? 'nav_next' : 'nav_next_lc' }"
                            class="ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only"
                            role="button" title="Next">
                        <span class="ui-button-text">Next</span>
                    </button>
                </c:if>
            </td>
            <%-- Links to alternate views --%>
            <td align="right" width="30%">
                <table>
                    <tbody>
                        <tr>
                            <td align="right">
                            <%--<c:if test="${!Form.onCurrentPeriod}" >--%>
                                <span class="header-title-right">
	       		                    <a href="${calendarLocation}?methodToCall=gotoCurrentPayPeriod"
                                        target="_self" id="cpplink">
                                        <c:choose>
                                            <c:when test="${calType eq 'payCalendar'}">
                                                Current Pay Period
                                            </c:when>
                                            <c:when test="${calType eq 'leaveCalendar'}">
                                                Current Leave Period
                                            </c:when>
                                        </c:choose>
                                    </a>
                                </span>
                            <%--</c:if>--%>
                            </td>
                        </tr>
                        <tr>
                            <td align="right">
                            <c:if test="${calType eq 'leaveCalendar'}">
                                <span class="header-title-right">
         	                        <a href="LeaveBlockDisplay.do"
                                        target="_self" id="ledger-link">Ledger View</a>
                                </span>
                            </c:if>
                            </td>
                        </tr>
                        <tr>
                            <td align="right">
                            <c:if test="${calType eq 'payCalendar'}">
                                <span class="header-title-right">
                                    <a href="${calendarLocation}?methodToCall=actualTimeInquiry&documentId=${Form.documentId}"
                                        target="_blank" id="atiLink">Actual Time Inquiry</a>
                                </span>
                            </c:if>
                            </td>
                        </tr>
                        <tr>
                            <td align="right">
                            <c:if test="${calType eq 'leaveCalendar'}">
                                <span class="header-title-right">
                                    <a href="LeaveRequest.do"
                                        target="_self" id="lrlink">Leave Request</a>
                                </span>
                            </c:if>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </td>
        </tr>
    </table>

    <div class="messages">
        <c:forEach var="action" items="${Form.actionMessages}">
            <span class="action"> ${action}</span> <br/>
        </c:forEach>
        <c:forEach var="warning" items="${Form.warningMessages}">
            <span class="warning"> ${warning}</span> <br/>
        </c:forEach>
        <c:forEach var="info" items="${Form.infoMessages}">
            <span class="info"> ${info}</span> <br/>
        </c:forEach>
    </div>

    <div id="tkCalContent">
        <table class="cal-table ${calType}-table">
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
                <tr style="height:100px;">
                        <%-- Generate Each Day --%>
                    <c:forEach var="day" items="${week.days}" varStatus="dayS">

                        <c:set var="dayStyle" value="width:14%;padding-bottom:20px;"/>
                        <c:set var="dayId" value="day_${day.dayNumberDelta}"/>
                        <c:set var="dayNumberId" value="dayNumber_${day.dayNumberDelta}"/>
                        <c:set var="dayClass" value="create ui-state-default"/>
                        <c:if test="${day.gray}">
                            <c:set var="dayStyle" value="width:14%; background: rgb(224, 235, 225);"/>
                            <c:set var="dayId" value="gray_day"/>
                            <c:set var="dayNumberId" value="dayNumber"/>
                            <c:set var="dayClass" value="ui-state-default"/>
                        </c:if>
 						<c:if test="${calType eq 'leaveCalendar' and not day.dayEditable}">
 						 	<c:set var="dayId" value="day_readonly"/> 						 	
 							<c:set var="dayClass" value="ui-state-default"/>
 						</c:if>

                        <td id="${dayId}" class="${dayClass}" style="${dayStyle}">
                                <%-- Day Number --%>
                            <div id="${dayNumberId}" class="day-number">${day.dayNumberString}</div>
                                <%-- Render the Time Blocks --%>

                            <div>
                                <c:choose>
                                    <c:when test="${calType eq 'payCalendar'}">
                                        <tk:payCalendar day="${day}"/>
                                    </c:when>
                                    <c:when test="${calType eq 'leaveCalendar'}">
                                        <tk:leaveCalendar day="${day}" dayNumberId="${dayNumberId}" />
                                    </c:when>
                                    <c:otherwise>
                                    </c:otherwise>
                                </c:choose>
                                <%--<div class="create" id="${day.dateString}" style="background-color:#cccccc; height:100%; padding-bottom:20px;"></div>--%>
                            </div>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
