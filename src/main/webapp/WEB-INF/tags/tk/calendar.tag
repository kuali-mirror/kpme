<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<%@ attribute name="cal" required="true" type="org.kuali.hr.time.calendar.CalendarParent" %>
<%@ attribute name="docId" required="true" type="java.lang.String" %>
<%@ attribute name="calType" required="true" type="java.lang.String" %>

<span class="cal-header-title">${cal.calendarTitle}</span>
<div id="tkCal" class="ui-widget cal" style="margin: 20px auto 20px auto; width:95%;">
    <%-- Add Paging Controls for moving between Calendars --%>
    <%--<table class="cal-header">--%>
    <%--<tbody>--%>
    <%--<tr>--%>
    <%--<td>--%>
    <%--<c:if test="${Form.prevDocumentId ne null}">--%>
    <%--<button id="nav_prev">Previous</button>--%>
    <%--</c:if>--%>
    <%--<span class="header-title">${cal.calendarTitle}</span>--%>
    <%--<c:if test="${Form.nextDocumentId ne null}">--%>
    <%--<button id="nav_next">Next</button>--%>
    <%--</c:if>--%>
    <%--</td>--%>
    <%--</tr>--%>
    <%--</tbody>--%>
    <%--</table>--%>

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
                        <c:set var="dayId" value="day_${day.dayNumberDelta}"/>
                        <c:if test="${day.gray}">
                            <c:set var="dayStyle" value="width:14%; background: rgb(224, 235, 225);"/>
                            <c:set var="dayId" value="gray_day"/>
                        </c:if>


                        <td id="${dayId}" class="ui-state-default" style="${dayStyle}">
                                <%-- Day Number --%>
                            <div class="day-number">${day.dayNumberString}</div>
                            <div>
                                <c:choose>
                                    <c:when test="${calType eq 'payCalendar'}">
                                        <tk:payCalendar day="${day}"/>
                                    </c:when>
                                    <c:when test="${calType eq 'leaveCalendar'}">
                                        <tk:leaveCalendar/>
                                    </c:when>
                                    <c:otherwise>

                                    </c:otherwise>

                                </c:choose>
                            </div>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>