<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<%@ attribute name="cal" required="true" type="org.kuali.hr.time.calendar.TkCalendar" %>
<%@ attribute name="docId" required="true" type="java.lang.String" %>

<div id="tkCal" class="ui-widget cal" style="margin: 20px auto 20px auto; width:95%;">
    <%-- Add Paging Controls for moving between Calendars --%>
    <table class="cal-header">
        <tbody>
        <tr>
            <td>
                <button id="nav_prev">Previous</button>
                <span class="header-title">${cal.calendarTitle}</span>
                <button id="nav_next">Next</button>
            </td>
        </tr>
        </tbody>
    </table>

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
                                <c:forEach var="block" items="${day.blockRenderers}">
                                    <div id="block_${block.timeBlock.tkTimeBlockId}" class="assignment0 event">
                                        <div><img id="delete_${block.timeBlock.tkTimeBlockId}" class='timeblock-delete'
                                                  src='images/delete.png'/></div>
                                        <br/>
                                            ${block.title}
                                            ${block.timeRange}
                                        <div>
                                            <c:forEach var="thdr" items="${block.detailRenderers}">
                                                <div id="${thdr.timeHourDetail.tkTimeHourDetailId}">
                                                        ${thdr.title} - ${thdr.hours}
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
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