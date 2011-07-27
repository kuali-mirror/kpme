<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<%@ attribute name="cal" required="true" type="org.kuali.hr.time.calendar.TkCalendar"%>
<%@ attribute name="docId" required="true" type="java.lang.String"%>

<div id="tkCal" class="fc ui-widget" style="margin: 20px auto 20px auto; width:95%; font-size:.9em;">
<%-- Add Paging Controls for moving between Calendars --%>
<table class="fc-header">
<tbody>
<tr><td class="fc-header-left"></td></tr>

<tr>
    <td>
        <div class="fc-button-prev ui-state-default ui-corner-left ui-corner-right">
            <a><span  id="nav_prev" class="ui-icon ui-icon-circle-triangle-w"></span></a></div></td><td><span class="fc-header-space"></span></td>
            <td><h2 class="fc-header-title">${cal.calendarTitle}</h2></td>
            <td><span class="fc-header-space"></span></td><td><div class="fc-button-next ui-state-default ui-corner-left ui-corner-right">
                <a><span id="nav_next" class="ui-icon ui-icon-circle-triangle-e"></span></a>
        </div>
    </td>
</tr>

<tr><td class="fc-header-right"></td></tr>
</tbody>
</table>

<div id="tkCalContent" class="fc-content ui-widget-content" style="position: relative; min-height: 1px; ">
<table>
    <thead>
    <%-- Render Day Labels, starting at FLSA Start day --%>
    <tr>
    <c:forEach var="dayString" items="${cal.calendarDayHeadings}">
        <th class="ui-state-default fc-leftmost" style="width: 185px; text-align: center; ">${dayString}</th>
    </c:forEach>
    </tr>
    </thead>

    <tbody>
    <%-- Generate Each Week --%>
    <c:forEach var="week" items="${cal.weeks}" varStatus="rowS">
        <tr class="fc-week1">
            <%-- Generate Each Day --%>
            <c:forEach var="day" items="${week.days}" varStatus="dayS">
                <td id="day_${day.dayNumberDelta}" class="fc-wed ui-state-default fc-day3 fc-not-today" style="height: 120px;">
                    <%-- Day Number --%>
                    <div class="fc-day-number">${day.dayNumberString}</div>
                    <%-- Render the Time Blocks --%>
                    <div class="fc-day-content">
                        <c:forEach var="block" items="${day.blockRenderers}">
                        <div id="block_${block.timeBlock.tkTimeBlockId}" class="fc-event fc-event-hori fc-corner-left fc-corner-right assignment0  timeblock ui-draggable ui-resizable" style="width: 179px;">
                            <div ><img id="delete_${block.timeBlock.tkTimeBlockId}" class='timeblock-delete' src='images/delete.png'/></div><br/>
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